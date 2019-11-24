package com.bolsadeideas.springboot.web.app.controllers;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.bolsadeideas.springboot.web.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.web.app.models.entities.Cliente;
import com.bolsadeideas.springboot.web.app.models.entities.Company;
import com.bolsadeideas.springboot.web.app.models.service.IClienteService;


@SessionAttributes("cliente")
@Controller
public class ClienteController {

	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	@Qualifier("company")
	private Company company;
	
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public String listar(Model model) {
		
		
		model.addAttribute("titulo","Listado de Clientes");
		model.addAttribute("clientes",clienteService.findAll());
		
		return "listar";
	}

	
	
	@GetMapping("/company")
	public String compania(Model model) {
		
		model.addAttribute("titulo","Datos de la compañía");
		model.addAttribute("nombre", (String)company.getName());
		
		model.addAttribute("anio", (String)company.getData().get(0));
		model.addAttribute("propietario", (String)company.getData().get(1));
		model.addAttribute("empleados", (String)company.getData().get(2));
		model.addAttribute("localizaciones", (String)company.getData().get(3));
		
		return "company";
	}
	
	@GetMapping("/form")
	public String crear(Map<String, Object> model) {
		
		Cliente cliente = new Cliente();
		model.put("cliente",cliente);
		model.put("titulo","Formulario de Cliente");
		
		return "form";
	}
	
	@PostMapping("/form")
	public String guardar(@Valid  @ModelAttribute("cliente") Cliente cliente, BindingResult result, 
			Model model, SessionStatus status, HttpServletRequest request) {
		
		if (result.hasErrors()) {
			model.addAttribute("titulo","Formulario de Cliente");
			return "form";
		}
		HttpSession session = request.getSession();
		Cliente clienteSession = (Cliente)session.getAttribute("cliente"); 
		System.out.println("Datos del cliente en sesión: " +  clienteSession.toString());
		
		clienteService.save(cliente);
//		session.invalidate();
//		try {
//			Cliente clienteSession2 = (Cliente)session.getAttribute("cliente"); 
//			System.out.println("Datos del cliente en sesión: " +  clienteSession2.toString());
//		}catch(Exception ex) {
//			System.out.println("Error de sesión: " + ex.getMessage());
//		}
		session.removeAttribute("cliente");
		
		return "redirect:listar";
	}

	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model) {
		
		Cliente cliente = null;
		
		if (id > 0) {
			cliente = clienteService.findOne(id);
		}else {
			return "redirect:/listar";
		}
		model.put("cliente" , cliente);
		model.put("titulo", "Editar Cliente");
		
		return "form";
	}
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id) {
		clienteService.delete(id);
		return "redirect:/listar";
	}
	
	@RequestMapping(value="/listar/{dominio}")
	public ModelAndView findSomeClientes(@PathVariable("dominio") String dominio, ModelAndView model) {
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		if (!StringUtils.isEmpty(dominio)) {
			clientes = clienteService.findSomeClientes(dominio);
		}
		
		model.setViewName("listar");
		model.addObject("titulo","Listado de Clientes del Dominio " + dominio);
		model.addObject("clientes",clientes);
		
		return  model;
	}
	
	@RequestMapping(value="/listar/{nomb}/{fecha}")
	public String  findSomeClientesPorNombreYFecha(@PathVariable String nomb, @PathVariable String fecha, Model model){
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		if (!StringUtils.isEmpty(nomb) || !StringUtils.isEmpty(fecha) ) {
			try {
				clientes = clienteService.findByNombreAndFecha(nomb, fecha);
			} catch (ParseException e) {
				System.out.println("Error al obtener la fecha: " + e.getMessage());
			}
		}
		model.addAttribute("titulo", "Listado de Clientes con Parámetros");
		model.addAttribute("clientes",clientes);
		
		return "listar";
	}
	
	
}
