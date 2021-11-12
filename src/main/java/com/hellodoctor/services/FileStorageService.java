package com.hellodoctor.services;



import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javassist.NotFoundException;
public interface FileStorageService {
	
	public String storeFile(MultipartFile file);
	 public Resource loadFileAsResource(String fileName) throws MalformedURLException, NotFoundException;

}
