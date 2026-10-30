package dataaccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public interface GenericDAO<E, K> {
	List<E> getAll();
	
	E getByID(K id);
	
	boolean insert(E entity);
	
	boolean update(E entity);
	
	boolean delete(K id);
}
