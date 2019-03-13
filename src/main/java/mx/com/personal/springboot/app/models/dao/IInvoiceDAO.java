package mx.com.personal.springboot.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import mx.com.personal.springboot.app.models.entity.Factura;

public interface IInvoiceDAO extends CrudRepository<Factura, Long> {

	@Query("SELECT f FROM Factura f join fetch f.cliente fc join fetch f.lineasFactura fl join fetch fl.producto WHERE f.id=?1")
	Factura fetchByIdWithClienteWithLineaFacturaWithProducto(Long id);
}
