package com.bolsadeideas.springboot.web.app.models.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {
	
	
	
	public boolean deleteFoto(String rutaFoto);
	
	public String insertFoto(MultipartFile foto, String rutaFoto);
	
	public Resource load(String filename) throws MalformedURLException, RuntimeException;
	
	public void deleteAll();
	
	public void init() throws IOException;
}
