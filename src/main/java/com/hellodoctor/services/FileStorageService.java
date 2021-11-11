package com.hellodoctor.services;

import javax.annotation.Resource;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
	
	public String storeFile(MultipartFile file);
	public Resource loadFileAsResource(String fileName);

}
