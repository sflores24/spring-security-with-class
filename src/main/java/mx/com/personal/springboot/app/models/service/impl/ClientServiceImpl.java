package mx.com.personal.springboot.app.models.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.personal.springboot.app.models.dao.IClientDAO;
import mx.com.personal.springboot.app.models.dao.IInvoiceDAO;
import mx.com.personal.springboot.app.models.dao.IProductDAO;
import mx.com.personal.springboot.app.models.entity.Cliente;
import mx.com.personal.springboot.app.models.entity.Factura;
import mx.com.personal.springboot.app.models.entity.Producto;
import mx.com.personal.springboot.app.models.service.IClientService;

@Service
public class ClientServiceImpl implements IClientService {

	@Autowired
	private IClientDAO clientDAO;
	
	@Autowired
	private IProductDAO productDAO;
	
	@Autowired
	private IInvoiceDAO facturaDAO;
	
	@Override
	public List<Cliente> findAll() {
		return (List<Cliente>)clientDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clientDAO.save(cliente);
	}

	@Override
	public Cliente findOne(Long id) {
		Optional<Cliente> optCliente = clientDAO.findById(id);
		return optCliente.orElse(null);
	}
	
	@Override
	public Cliente fetchByIdWithFacturas(Long id) {
		return clientDAO.fetchByIdWithFacturas(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clientDAO.deleteById(id);
	}

	@Override
	public Page<Cliente> findAll(Pageable pageable) {
		return clientDAO.findAll(pageable);
	}

	@Override
	public List<Producto> findByNombre(String term) {
		//return productDAO.findByNombre(term);
		return productDAO.findByNombreLikeIgnoreCase("%"+term+"%");
	}

	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		facturaDAO.save(factura);
	}

	@Override
	public Producto findProductoById(Long id) {
		return productDAO.findById(id).get();
	}

	@Override
	public Factura findFacturaById(Long id) {
		return facturaDAO.findById(id).get();
	}

	@Override
	@Transactional
	public void deleteFactura(Long id) {
		facturaDAO.deleteById(id);
	}

	@Override
	public Factura fetchByIdWithClienteWithLineaFacturaWithProducto(Long id) {
		return facturaDAO.fetchByIdWithClienteWithLineaFacturaWithProducto(id);
	}
	
}
