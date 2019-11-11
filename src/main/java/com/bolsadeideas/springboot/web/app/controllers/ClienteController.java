package com.bolsadeideas.springboot.web.app.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bolsadeideas.springboot.web.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.web.app.models.entities.Cliente;
import com.bolsadeideas.springboot.web.app.models.entities.Company;

@Controller
public class ClienteController {

	@Autowired
	@Qualifier("clienteDaoJPA")
	private IClienteDao clienteDao;
	
	@Autowired
	@Qualifier("company")
	private Company company;
	
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public String listar(Model model) {
		
		
		model.addAttribute("titulo","Listado de Clientes");
		model.addAttribute("clientes",clienteDao.findAll());
		
		return "listar";
	}

	
	
	@GetMapping("/company")
	public String compania(Model model) {
		
		model.addAttribute("titulo","Datos de la compa&iacute;a");
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
	public String guardar(Cliente cliente) {
		clienteDao.save(cliente);
		return "redirect:listar";
	}
}
