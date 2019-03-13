package mx.com.personal.springboot.app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.web.bind.support.SessionStatus;

import mx.com.personal.springboot.app.Constants;
import mx.com.personal.springboot.app.models.entity.Cliente;
import mx.com.personal.springboot.app.models.entity.Factura;
import mx.com.personal.springboot.app.models.entity.Producto;
import mx.com.personal.springboot.app.models.entity.LineaFactura;
import mx.com.personal.springboot.app.models.service.IClientService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

	private static final String FORM = "factura/form";
	private static final String FACTURA = "factura";
	private static final String CREAR_FACTURA = "Crear Factura";

	@Autowired
	private IClientService clienteService;

	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable(name = "clienteId") Long clienteId, Model model, RedirectAttributes flash) {
		Cliente cliente = clienteService.findOne(clienteId);

		if (cliente == null) {
			flash.addFlashAttribute(Constants.ERROR, "El cliente no existe");
			return Constants.FORM_SAVED;
		}

		Factura factura = new Factura();
		factura.setCliente(cliente);
		model.addAttribute(FACTURA, factura);
		model.addAttribute(Constants.LABEL_TITLE, CREAR_FACTURA);

		return FORM;
	}

	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
		return clienteService.findByNombre(term);
	}

	@PostMapping("/form")
	public String guardar(@Valid Factura factura, BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad, RedirectAttributes flash,
			SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute(Constants.LABEL_TITLE, CREAR_FACTURA);
			return FORM;
		}
		
		if(itemId == null || itemId.length == 0) {
			model.addAttribute(Constants.LABEL_TITLE, CREAR_FACTURA);
			model.addAttribute(Constants.ERROR, "La factura debe contener al menos un producto");
			return FORM;			
		}

		if (factura != null) {
			Producto producto = null;
			LineaFactura lineaFactura = null;
			for (int cont = 0; cont < itemId.length; cont++) {
				producto = clienteService.findProductoById(itemId[cont]);

				lineaFactura = new LineaFactura();
				lineaFactura.setCantidad(cantidad[cont]);
				lineaFactura.setProducto(producto);

				factura.addLineasFactura(lineaFactura);
			}
			clienteService.saveFactura(factura);
			status.setComplete();
			
			flash.addFlashAttribute(Constants.SUCCESS, "Factura creada");
		}
		return "redirect:/client/ver/" + factura.getCliente().getId();
	}
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(name="id") Long id, Model model, RedirectAttributes flash) {
		Factura factura = clienteService.fetchByIdWithClienteWithLineaFacturaWithProducto(id);
		
		if(factura == null) {
			flash.addFlashAttribute(Constants.ERROR,"La factura no existe");
			return "redirect:/client/listar";
		}
		
		model.addAttribute(Constants.LABEL_TITLE, factura.getDescripcion());
		model.addAttribute("factura", factura);
		
		return "factura/ver";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		Factura factura = clienteService.findFacturaById(id);
		if(factura != null) {
			clienteService.deleteFactura(id);
			flash.addFlashAttribute(Constants.SUCCESS, "Factura eliminada");
			return "redirect:/client/ver/"+factura.getCliente().getId();
		}
		flash.addFlashAttribute(Constants.ERROR, "Factura no existe");
		
		return "redirect:/client/listar";
	}
}
