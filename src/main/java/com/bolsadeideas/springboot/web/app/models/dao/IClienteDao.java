package com.bolsadeideas.springboot.web.app.models.dao;

import java.util.List;

import com.bolsadeideas.springboot.web.app.models.entities.Cliente;

public interface IClienteDao {
	
	public List<Cliente> findAll();
		
	public void save(Cliente cliente);


}
