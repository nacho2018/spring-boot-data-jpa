package com.bolsadeideas.springboot.web.app.models.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsadeideas.springboot.web.app.models.entities.Cliente;
import com.bolsadeideas.springboot.web.app.models.entities.Factura;
import com.bolsadeideas.springboot.web.app.models.entities.Producto;

public interface IClienteService {

	public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable pageable);
	
	public void save(Cliente cliente);
	
	public Cliente findOne(Long id);
	
	public void delete(Long id);
	
	public List<Cliente> findSomeClientes(String dominio);
	
	public List<Cliente> findByNombreAndFecha(String nom, String fecha) throws ParseException ;
	
	public List<Producto> findByNombre(String term);
	
	public void saveFactura(Factura factura);
	
	public Producto findProductoById(Long id);
	
	public Factura findFacturaById(Long id);
	
	public void eliminarFacturaById(Long id);
	
}
