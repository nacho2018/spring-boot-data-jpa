package com.bolsadeideas.springboot.web.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.web.app.models.entities.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{
	
//	public Usuario findByUsername(String userName);
	
	@Query("select u from Usuario u where u.username=?1")
	public Usuario fetchByUsername(String username);
	
	
}
