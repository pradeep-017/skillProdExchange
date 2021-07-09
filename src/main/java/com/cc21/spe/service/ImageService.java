package com.cc21.spe.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	String uploadFiles(MultipartFile multipartFile) throws IOException;

}
