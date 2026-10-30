package businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public interface GenericService<E, K> {
	List<E> retrieveAll();
	
	E findByID(K id);
	
	boolean add(E entity);
	
	boolean redesign(E entity);
	
	boolean remove(K id);
}
