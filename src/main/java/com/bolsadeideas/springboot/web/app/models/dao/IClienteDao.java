package com.bolsadeideas.springboot.web.app.models.dao;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolsadeideas.springboot.web.app.models.entities.Cliente;

public interface IClienteDao  extends PagingAndSortingRepository<Cliente, Long>{
	
	@Query("select cl  from Cliente cl where cl.email like %?1%")
	List<Cliente>  findSomeClientes(String dominio);
	
	List<Cliente> findByNombreAndCreateAt(String nom, Date fecha);
	

}
