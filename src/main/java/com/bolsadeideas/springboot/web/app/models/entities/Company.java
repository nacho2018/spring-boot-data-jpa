package com.bolsadeideas.springboot.web.app.models.entities;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;



@Component("company")
public class Company {

	@Autowired
	@Qualifier("compName")
	private String name;
	
	@Autowired
	@Qualifier("compData")
	private List<String> data;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	
	
}
