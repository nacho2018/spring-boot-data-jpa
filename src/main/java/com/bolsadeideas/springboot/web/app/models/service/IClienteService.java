package com.bolsadeideas.springboot.web.app.models.service;

import java.text.ParseException;
import java.util.List;

import com.bolsadeideas.springboot.web.app.models.entities.Cliente;

public interface IClienteService {

	public List<Cliente> findAll();
	
	public void save(Cliente cliente);
	
	public Cliente findOne(Long id);
	
	public void delete(Long id);
	
	public List<Cliente> findSomeClientes(String dominio);
	
	public List<Cliente> findByNombreAndFecha(String nom, String fecha) throws ParseException ;
}
