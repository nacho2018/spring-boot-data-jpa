package com.bolsadeideas.springboot.web.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.web.app.models.entities.Cliente;

@Repository("clienteDaoJPA")
public class ClienteDaoImpl  implements IClienteDao{

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> findAll() {
		
		return em.createQuery("from Cliente").getResultList();
	}

	@Override
	public void save(Cliente cliente) {
		
		em.persist(cliente);
		
	}

}
