package com.bolsadeideas.springboot.web.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.web.app.models.entities.Producto;

public interface IProductoDao extends CrudRepository<Producto, Long>{

	
	List<Producto> findByNombreLikeIgnoreCase(String term);
}
