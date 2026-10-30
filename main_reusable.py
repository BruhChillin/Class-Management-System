from PIL import Image, ImageOps
import numpy as np
import cv2
import time
import sys
import argparse
import os
os.environ["TF_CPP_MIN_LOG_LEVEL"] = "3"


np.set_printoptions(suppress=True)

IMG_SIZE = (224, 224)


# -- Load model (CALLED ONLY ONCE, at startup) --------------------------------

def load_model_compat(path: str):
    """Same compatibility patch logic as the original main_faces.py."""
    try:
        from tf_keras.models import load_model
        print("  [using tf_keras]")
        return load_model(path, compile=False)
    except ImportError:
        pass

    try:
        from keras.models import load_model
        from keras.layers import DepthwiseConv2D as _Base

        class DepthwiseConv2DCompat(_Base):
            def __init__(self, *args, **kwargs):
                kwargs.pop("groups", None)
                super().__init__(*args, **kwargs)

        print("  [using keras + DepthwiseConv2D patch]")
        return load_model(path, compile=False,
                          custom_objects={"DepthwiseConv2D": DepthwiseConv2DCompat})
    except Exception as e:
        sys.exit(f"Failed to load model: {e}\nTry: pip install tf-keras")


def load_labels(path: str):
    with open(path, "r", encoding="utf-8") as f:
        return [line.strip()[2:] if len(line.strip()) > 2 else line.strip()
                for line in f.readlines()]


# -- Preprocess + predict (runs every loop iteration, NO model reload) -------

def preprocess(frame_bgr) -> np.ndarray:
    rgb = cv2.cvtColor(frame_bgr, cv2.COLOR_BGR2RGB)
    image = Image.fromarray(rgb)
    image = ImageOps.fit(image, IMG_SIZE, Image.Resampling.LANCZOS)
    arr = np.asarray(image).astype(np.float32)
    normalized = (arr / 127.5) - 1
    return np.expand_dims(normalized, axis=0)


def classify(model, labels, frame_bgr):
    data = preprocess(frame_bgr)
    prediction = model.predict(data, verbose=0)
    index = int(np.argmax(prediction))
    class_name = labels[index] if index < len(labels) else f"Label_{index}"
    confidence = float(prediction[0][index])
    return class_name, confidence


def print_result(class_name, confidence, elapsed):
    print("=" * 40)
    print("  CLASSIFICATION RESULT")
    print("=" * 40)
    print(f"  Class      : {class_name}")
    print(f"  Confidence : {confidence:.2%}  {'#' * int(confidence * 30)}")
    print(f"  Time       : {elapsed*1000:.1f} ms")
    print("=" * 40)


# -- Camera capture (same logic as the original main_classify.py) -----------

def open_camera(camera_index: int = 0):
    """
    Open the camera once. The caller is responsible for releasing it
    when the loop ends.
    """
    cap = cv2.VideoCapture(camera_index, cv2.CAP_DSHOW)
    if not cap.isOpened():
        cap = cv2.VideoCapture(camera_index)
    if not cap.isOpened():
        sys.exit(f"Error: Cannot open camera {camera_index}.")
    return cap


# -- Main ----------------------------------------------------------------------

def main():
    parser = argparse.ArgumentParser(
        description="Realtime face attendance (model loaded once)")
    parser.add_argument("--image", type=str, default=None,
                        help="Classify a single existing image then exit (no webcam needed)")
    parser.add_argument("--camera", type=int, default=0)
    parser.add_argument("--model", type=str, default="keras_model.h5")
    parser.add_argument("--labels", type=str, default="labels.txt")
    parser.add_argument("--save", type=str, default="face_id.jpg",
                        help="Where to save the image on each capture")
    args = parser.parse_args()

    # ---- The only slow step: load the model once ----
    print("Loading model... (this is the only time it will take a while)")
    t0 = time.time()
    model = load_model_compat(args.model)
    labels = load_labels(args.labels)
    print(f"Model loaded in {time.time() - t0:.2f}s\n")

    # ---- Quick test mode: classify a single existing image ----
    if args.image:
        frame = cv2.imread(args.image)
        if frame is None:
            sys.exit(f"Error: Could not read image '{args.image}'")
        t0 = time.time()
        class_name, confidence = classify(model, labels, frame)
        print_result(class_name, confidence, time.time() - t0)
        return

    # ---- Realtime loop: open camera once, capture + classify many times ----
    cap = open_camera(camera_index=args.camera)
    window_name = "SPACE: Capture & Classify  |  ESC/q: Quit"
    print("Live preview opened. Press SPACE to capture & classify, ESC to quit.")

    last_result = None  # (class_name, confidence) shown as overlay on preview
    try:
        while True:
            ret, frame = cap.read()
            if not ret or frame is None:
                continue

            display = frame.copy()
            cv2.putText(display, "Press SPACE to capture & classify", (10, 30),
                        cv2.FONT_HERSHEY_SIMPLEX, 0.7, (0, 255, 0), 2)
            if last_result is not None:
                name, conf = last_result
                cv2.putText(display, f"{name} ({conf:.2%})", (10, 60),
                            cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0, 255, 255), 2)

            cv2.imshow(window_name, display)
            key = cv2.waitKey(1) & 0xFF

            if key == ord(' ') or key == ord('c'):
                cv2.imwrite(args.save, frame)
                t0 = time.time()
                class_name, confidence = classify(model, labels, frame)
                elapsed = time.time() - t0
                print_result(class_name, confidence, elapsed)
                last_result = (class_name, confidence)
            elif key == 27 or key == ord('q'):
                break
    finally:
        cap.release()
        cv2.destroyAllWindows()


if __name__ == "__main__":
    main()
