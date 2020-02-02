package com.bolsadeideas.springboot.web.app.models.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.web.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.web.app.models.dao.IFacturaDao;
import com.bolsadeideas.springboot.web.app.models.dao.IProductoDao;
import com.bolsadeideas.springboot.web.app.models.entities.Cliente;
import com.bolsadeideas.springboot.web.app.models.entities.Factura;
import com.bolsadeideas.springboot.web.app.models.entities.Producto;

@Service
public class ClienteServiceImpl  implements IClienteService{

	@Autowired
	private IClienteDao clienteDao;
	
	@Autowired
	private IProductoDao productoDao;
	
	@Autowired
	private IFacturaDao facturaDao;
	
	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		 this.clienteDao.save(cliente);
		
	}

	@Override
	@Transactional(readOnly=true)
	public Cliente findOne(Long id) {
		return this.clienteDao.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		this.clienteDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly=true)
	public List<Cliente> findSomeClientes(String dominio) {
		return this.clienteDao.findSomeClientes(dominio);
	}

	@Override
	public List<Cliente> findByNombreAndFecha(String nom, String fecha) throws ParseException {
		
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		Date  date = formater.parse(fecha);
		return this.clienteDao.findByNombreAndCreateAt(nom, date);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Cliente> findAll(Pageable pageable) {
		
		return clienteDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Producto> findByNombre(String term) {
		
		return productoDao.findByNombreLikeIgnoreCase("%" + term + "%");
	}

	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		
		Factura savedFactura = facturaDao.save(factura);
		
		if (savedFactura.getId() == null) {
			throw new RuntimeException("No se grabó correctamente la factura, sin id");
			
		}
		
	}

	@Override
	@Transactional(readOnly=true)
	public Producto findProductoById(Long id) {
		
		return productoDao.findById(id).orElse(null);
				
	}

	@Override
	@Transactional(readOnly=true)
	public Factura findFacturaById(Long id) {
		
		 return facturaDao.findById(id).orElse(null);
		 
		
	}

	@Override
	@Transactional
	public void eliminarFacturaById(Long id) {
		facturaDao.deleteById(id);
		
	}

	

}
