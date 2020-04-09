package com.bolsadeideas.springboot.web.app.view.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.bolsadeideas.springboot.web.app.models.entities.Cliente;

@XmlRootElement(name="clientes")
public class ClienteList {
	
	@XmlElement(name="cliente")
	public List<Cliente> clientes;

	public ClienteList() {};
	
	

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public ClienteList(List<Cliente> clientes) {
		super();
		this.clientes = clientes;
	}
	

}
