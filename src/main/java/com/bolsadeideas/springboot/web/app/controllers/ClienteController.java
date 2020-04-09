package com.bolsadeideas.springboot.web.app.controllers;


import java.net.MalformedURLException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.web.app.models.entities.Cliente;
import com.bolsadeideas.springboot.web.app.models.entities.Company;
import com.bolsadeideas.springboot.web.app.models.service.IClienteService;
import com.bolsadeideas.springboot.web.app.models.service.IUploadFileService;
import com.bolsadeideas.springboot.web.app.paginator.PageRender;


@SessionAttributes("cliente")
@Controller
public class ClienteController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private IUploadFileService uploadFileService;
	
	@Autowired
	private MessageSource messageSource;
	
	
	@Autowired
	@Qualifier("company")
	private Company company;
	
	@Secured({"ROLE_USER"})
	@GetMapping(value="/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename){
		
		Resource recurso = null;
		
		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
		
		return new ResponseEntity<Resource>(recurso, headers, HttpStatus.ACCEPTED);
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping(value="/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		Cliente cliente = clienteService.findOne(id);
		if (cliente == null) {
			flash.addAttribute("error", "El cliente no existe en la base de datos.");
			return "redirect:listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Detalle del cliente: " + cliente.getNombre());
		
		return "ver";
		
	}
	
	@GetMapping(value = "/listar-rest")
	public @ResponseBody List<Cliente> listarRest() {
		return this.clienteService.findAll();
	}
	
	@RequestMapping(value = {"/listar", "/"}, method=RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue="0") int page, Model model,
			Authentication authentication, HttpServletRequest request, Locale locale) {
		
		if (authentication != null) {
			logger.info("Hola usuario autenticado, tu username es ".concat(authentication.getName()));
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			logger.info("Usando SecurityContextHolder : hola ".concat(auth.getName().concat(" estás logado.")));
		}
		
		if (this.hasRole("ROLE_ADMIN")) {
			logger.info("Usuario ".concat(auth.getName()).concat(" tienes permisos de administrador."));
		}else {
			logger.info("Usuario ".concat(auth.getName()).concat(" NO tienes permisos de administrador."));
		}
		
		SecurityContextHolderAwareRequestWrapper securityContext = 
				new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
				
		if (securityContext.isUserInRole("ADMIN"))
			logger.info("Usando SecurityContextHolderAwareRequestWrapper Usuario ".concat(auth.getName()).concat(" tienes permisos de administrador."));
		else
			logger.info("Usando SecurityContextHolderAwareRequestWrapper  Usuario ".concat(auth.getName()).concat(" NO tienes permisos de administrador."));
				
		
		Pageable pageRequest = PageRequest.of(page, 100);
		Page<Cliente> clientes = this.clienteService.findAll(pageRequest);
		
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
		model.addAttribute("titulo",messageSource.getMessage("text.cliente.listar.listado", null, locale));
		model.addAttribute("clientes",clientes);
		model.addAttribute("page", pageRender);
		
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
			Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status, HttpServletRequest request) {
		if (result.hasErrors()) {
			model.addAttribute("titulo","Formulario de Cliente");
			return "form";
		}
		
		String mensajeFlash = cliente.getId() != null ? "Cliente editado con éxito" : "Cliente creado con éxito";
		
		if (!foto.isEmpty()) {
			//controlar si hay que actualizar la foto
			if (cliente.getId() != null && 
					cliente.getId() > 0 && 
						!StringUtils.isEmpty(cliente.getFoto())) {
				if (uploadFileService.deleteFoto(cliente.getFoto())) {
					logger.info("Operacion de borrado de imagen correcta.");
					cliente.setFoto(null);
				}
				
			}
			
			String fileName = uploadFileService.insertFoto(foto, foto.getOriginalFilename());
			if(!StringUtils.isEmpty(fileName)) {
				logger.info("Foto " + fileName + " insertada correctamente.");
				cliente.setFoto(fileName);
				flash.addFlashAttribute("info", "Foto " + fileName + " insertada con éxito.");
			}else {
				logger.info("Error insertando la foto.");
			}
			
		}
		
		clienteService.save(cliente);
		flash.addFlashAttribute("success", mensajeFlash);
		
		return "redirect:listar";
	}

	@Secured({"ROLE_USER"})
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		Cliente cliente = null;
		
		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "No existe cliente con ese id.");
				return "redirect:/listar";
			}
		}else {
			flash.addFlashAttribute("error", "El id del cliente no puede ser 0.");
			return "redirect:/listar";
		}
		model.put("cliente" , cliente);
		model.put("titulo", "Editar Cliente");
		
		return "form";
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		//borramos la foto original si existe
		Cliente cliente = clienteService.findOne(id);
		
		if (!StringUtils.isEmpty(cliente.getFoto())){
			if(uploadFileService.deleteFoto(cliente.getFoto())) {
				logger.info("Operación de borrado de imagen correcta.");
			}
		}
		
		clienteService.delete(id);
		flash.addFlashAttribute("success", "Cliente eliminado con éxito");
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
	
	private boolean hasRole(String role) {
		
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null)
			return false;
		
		Authentication auth = context.getAuthentication();
		if (auth == null)
			return false;
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
//		for (GrantedAuthority authority : authorities) {
//			if (authority.getAuthority().equalsIgnoreCase(role)) {
//				logger.info("El rol es ".concat(authority.getAuthority()));
//				return true;
//			}
//				
//		}
		
		return authorities.contains(new SimpleGrantedAuthority(role));
		
//		return false;
	}
	
	
}
