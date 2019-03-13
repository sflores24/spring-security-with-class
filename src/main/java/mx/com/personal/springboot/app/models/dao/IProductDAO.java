package mx.com.personal.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import mx.com.personal.springboot.app.models.entity.Producto;

public interface IProductDAO extends CrudRepository<Producto, Long> {
	@Query("SELECT p FROM Producto p WHERE p.nombre like %?1%")
	List<Producto> findByNombre(String term);
	
	//Hace la busqueda por query creation por nombre de metodo
	List<Producto> findByNombreLikeIgnoreCase(String term);
}
