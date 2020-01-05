package com.bolsadeideas.springboot.web.app.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService{

	private final static String UPLOADS_FOLDER = "uploads";
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean deleteFoto(String rutaFoto) {
		
		Path pathToFoto = Paths.get(UPLOADS_FOLDER).resolve(rutaFoto);
		File fichero = pathToFoto.toFile();
		
		if (fichero != null && fichero.exists() && fichero.delete()) {
			logger.info("Se ha borrado la foto " + rutaFoto);
			return true;
		}else
			return false;
	}

	@Override
	public String insertFoto(MultipartFile foto,String rutaFoto) {
		
		String uniqueFileName = UUID.randomUUID().toString() + "_" + rutaFoto;
		Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(uniqueFileName);
		Path rootAbsolutePath = rootPath.toAbsolutePath();
		logger.info("rootPath: " + rootPath);
		logger.info("rootAbsolutePath: " + rootAbsolutePath);
		
		try {
			Files.copy(foto.getInputStream(), rootAbsolutePath);
			logger.info("Has subido correctamente " + rutaFoto);
			return uniqueFileName;
		}catch(IOException e) {
			e.printStackTrace();
			return "";
		}
		
	}

	@Override
	public Resource load(String filename) throws MalformedURLException, RuntimeException {
		
		Path pathFoto = Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
		logger.info("pathFoto: " + pathFoto);
		Resource recurso = null;
		recurso = new UrlResource(pathFoto.toUri());
		
		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("Error, no se puede cargar la imagen " + pathFoto.toString());
		}
		
		return recurso;
	}

	@Override
	public void deleteAll(){
		
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
		
	}

	@Override
	public void init() throws IOException {
		
		Files.createDirectory(Paths.get(UPLOADS_FOLDER));
		
	}
	
	

}
