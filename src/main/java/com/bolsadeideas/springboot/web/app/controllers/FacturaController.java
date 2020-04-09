package com.bolsadeideas.springboot.web.app.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.bolsadeideas.springboot.web.app.view.pdf.FacturaPdfView;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;

@Secured({"ROLE_ADMIN"})
@Controller
@RequestMapping(value="/factura")
@SessionAttributes("factura")
public class FacturaController {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private FacturaPdfView facturaPdfView;
	
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
	public String guardar(@Valid Factura factura,BindingResult result,
		@RequestParam(name="item_id[]", required=false) Long[] itemId,
		@RequestParam(name="cantidad[]", required=false) Integer[] cantidad,
		RedirectAttributes flash,
		SessionStatus status, Model model) {
			
	
		if (result.hasErrors()) {
			//no pasa las restricciones de entidad, vuelta al formulario
			
			model.addAttribute("titulo", "Crear factura");
			return "factura/form";
			
		}
		
		if (itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "Crear factura");
			model.addAttribute("error", "La factura No puede no tener líneas");
			return "factura/form";
		}
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
	
	@GetMapping(value="/ver/{id}")
	public String getLineasFactura(@PathVariable(value="id") Long id,
			Model model, RedirectAttributes flash, HttpServletRequest request,
			HttpServletResponse response){
		

		Factura factura = clienteService.findFacturaById(id);
		
		if (factura == null) {
			flash.addFlashAttribute("error",  "La factura no existe");
			return "redirect:/listar";
		}
		if(factura.getItems() == null || factura.getItems().isEmpty()) {
			flash.addFlashAttribute("error",  "La factura está vacía");
			return "redirect:/listar";
		}
		model.addAttribute("titulo", "Detalle de la factura");
		model.addAttribute("factura", factura);
		
	
		return "factura/ver";
		
	}
	
	@GetMapping(value="/eliminar/{id}/")
	public String eliminarFactura(@PathVariable(value="id") Long id,
			Model model, RedirectAttributes flash) {
		
		Factura factura = clienteService.findFacturaById(id);
		
			if (factura == null) {
				flash.addFlashAttribute("error", "La factura no existe!");
				
			}else {
				clienteService.eliminarFacturaById(id);
				flash.addFlashAttribute("success", "Factura eliminada!");
			}
		
		return "redirect:/ver/" + factura.getCliente().getId();
		}
	
}
