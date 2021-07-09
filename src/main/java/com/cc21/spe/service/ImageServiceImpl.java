package com.cc21.spe.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.cc21.spe.constants.Constants;

@Service
public class ImageServiceImpl implements ImageService {

	private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

	private static final String FILE_NAME_DOES_NOT_EXISTS = "The image doesn't has a name attached";

	@Autowired
	private S3Service s3Service;

	@Async
	@Override
	public String uploadFiles(final MultipartFile multipartFile) throws IOException {
		logger.info("File upload in progress.");
		String imageName = "";
		logger.info("Received multipartFile: " + multipartFile);
		try {
			File file = convertMultiPartFileToFile(multipartFile);
            logger.info("Converted File: " + file);
            imageName = file.getName();
			s3Service.uploadFileToS3Bucket(Constants.INPUT_S3, file);
			logger.info("File upload is completed." + multipartFile.getName());
			
		} catch (final AmazonServiceException ex) {
			logger.info("File upload is failed.");
			logger.error("Error= {} while uploading file.", ex.getMessage());
		}
		// Files upload is completed!
		return imageName;
	}


	private File convertMultiPartFileToFile(MultipartFile multipartFile) throws IOException {
		if (Objects.isNull(multipartFile.getOriginalFilename())) {
			throw new RuntimeException(FILE_NAME_DOES_NOT_EXISTS);
		}
		File file = new File(multipartFile.getOriginalFilename());
		FileOutputStream outputStream = new FileOutputStream(file);
		try {
			outputStream.write(multipartFile.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			outputStream.close();
		}
		return file;
	}

}
