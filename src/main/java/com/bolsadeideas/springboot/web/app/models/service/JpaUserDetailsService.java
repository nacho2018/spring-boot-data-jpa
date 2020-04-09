package com.bolsadeideas.springboot.web.app.models.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.web.app.models.dao.IUsuarioDao;
import com.bolsadeideas.springboot.web.app.models.entities.Role;
import com.bolsadeideas.springboot.web.app.models.entities.Usuario;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService{

	private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);
	@Autowired
	IUsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioDao.fetchByUsername(username);
		
		if (usuario == null) {
			logger.error("usuario no existe el usuario ".concat(username));
			throw new UsernameNotFoundException("el usuario ".concat(username).concat(" no existe"));
		}
		
		List<Role> roles = usuario.getRoles();
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		for (Role role : roles) {
			logger.info("role : ".concat(role.getAuthority()));
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		
		if (authorities.isEmpty()) {
			logger.error("el usuario ".concat(username).concat(" no tiene roles."));
			throw new UsernameNotFoundException("el usuario ".concat(username).concat(" no tiene roles."));
		}
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

}
