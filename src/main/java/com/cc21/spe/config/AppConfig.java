package com.cc21.spe.config;

import org.springframework.context.annotation.Bean;

import com.cc21.spe.repo.S3Repo;
import com.cc21.spe.repo.S3RepoImpl;
import com.cc21.spe.service.S3Service;
import com.cc21.spe.service.S3ServiceImpl;

public class AppConfig {
	
	@Bean
	public S3Repo s3Repo() {
		return new S3RepoImpl();
	}

	@Bean
	public S3Service s3Service() {
		return new S3ServiceImpl();
	}
	
	@Bean
	public AwsConfiguration awsConfiguration() {
		return new AwsConfiguration();
	}
	
}
