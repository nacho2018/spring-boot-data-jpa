package com.bolsadeideas.springboot.web.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

	@Bean("compName")
	public String compName() {
		return "SATA S.A";
	}
	@Bean("compData")
	public List<String> compData(){
		List<String> compData = new ArrayList<String>();
		compData.add(0, "2010");
		compData.add(1, "Álvaro Mejide Rubio");
		compData.add(2, "3596");
		compData.add(3, "Bolivia Salvador UK England Nueva Zelanda");
		
		return compData;
		
	}
	
	
}
