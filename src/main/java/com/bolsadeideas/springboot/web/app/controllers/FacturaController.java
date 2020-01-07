package com.bolsadeideas.springboot.web.app.controllers;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.bolsadeideas.springboot.web.app.models.entities.Cliente;
import com.bolsadeideas.springboot.web.app.models.entities.Factura;
import com.bolsadeideas.springboot.web.app.models.entities.ItemFactura;
import com.bolsadeideas.springboot.web.app.models.entities.Producto;
import com.bolsadeideas.springboot.web.app.models.service.IClienteService;

@Controller
@RequestMapping(value="/factura")
@SessionAttributes("factura")
public class FacturaController {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable Long clienteId, Model model, RedirectAttributes flash) {
		
		Cliente cliente = clienteService.findOne(clienteId);
		
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe.");
			return "redirect:/listar";
		}
		Factura factura = new Factura();
		factura.setCliente(cliente);
		
		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Crear una factura");
		
		return "factura/form";
				
	}
	
	@GetMapping(value="/cargar-productos/{term}", produces= {"application/json"})
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term){
		
		return clienteService.findByNombre(term);
		
	}
	
	@PostMapping(value="/form")
	public String guardar(Factura factura,
		@RequestParam(name="item_id[]", required=false) Long[] itemId,
		@RequestParam(name="cantidad[]", required=false) Integer[] cantidad,
		RedirectAttributes flash,
		SessionStatus status) {
			
			for(int i = 0; i < itemId.length; i++) {
				log.info("itemId " + itemId[i].toString());
				log.info("cantidad " + cantidad[i].toString());
				
				Producto producto = this.clienteService.findProductoById(itemId[i]);
				ItemFactura linea = new ItemFactura();
				linea.setCantidad(cantidad[i]);
				linea.setProducto(producto);
				factura.addItemFactura(linea);
			}
			
			clienteService.saveFactura(factura);
			status.setComplete(); //elimina la factura de la sesión
			flash.addFlashAttribute("success", "Factura creada con éxito");
			
			return "redirect:/ver/" + factura.getCliente().getId();
		
	}
	
}
