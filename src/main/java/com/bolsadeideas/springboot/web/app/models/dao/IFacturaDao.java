package com.bolsadeideas.springboot.web.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.web.app.models.entities.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long>{
	

}
