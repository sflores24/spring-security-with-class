package mx.com.personal.springboot.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import mx.com.personal.springboot.app.models.entity.Cliente;

public interface IClientDAO extends PagingAndSortingRepository<Cliente, Long> {
	
	@Query("SELECT c FROM Cliente c left join fetch c.facturas cf WHERE c.id=?1")
	Cliente fetchByIdWithFacturas(Long id);
}
