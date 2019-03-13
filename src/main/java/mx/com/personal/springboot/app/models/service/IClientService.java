package mx.com.personal.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.com.personal.springboot.app.models.entity.Cliente;
import mx.com.personal.springboot.app.models.entity.Factura;
import mx.com.personal.springboot.app.models.entity.Producto;

public interface IClientService {
	List<Cliente> findAll();
	Page<Cliente> findAll(Pageable pageable);
	void save(Cliente cliente);
	Cliente findOne(Long id);
	Cliente fetchByIdWithFacturas(Long id);
	void delete(Long id);
	List<Producto> findByNombre(String term);
	void saveFactura(Factura factura);
	Producto findProductoById(Long id);
	Factura findFacturaById(Long id);
	void deleteFactura(Long id);
	Factura fetchByIdWithClienteWithLineaFacturaWithProducto(Long id);
}
