package com.bolsadeideas.springboot.web.app.models.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.web.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.web.app.models.entities.Cliente;

@Service
public class ClienteServiceImpl  implements IClienteService{

	@Autowired
	private IClienteDao clienteDao;
	
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

	

}
