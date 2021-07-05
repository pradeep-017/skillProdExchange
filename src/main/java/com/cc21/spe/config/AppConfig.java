package com.cc21.spe.config;

import org.springframework.context.annotation.Bean;

import com.cc21.spe.repo.Ec2Repo;
import com.cc21.spe.repo.Ec2RepoImpl;
import com.cc21.spe.repo.S3Repo;
import com.cc21.spe.repo.S3RepoImpl;
import com.cc21.spe.repo.SqsRepo;
import com.cc21.spe.repo.SqsRepoImpl;
import com.cc21.spe.service.Ec2Service;
import com.cc21.spe.service.Ec2ServiceImpl;
import com.cc21.spe.service.LoadBalanceService;
import com.cc21.spe.service.LoadBalanceServiceImpl;
import com.cc21.spe.service.S3Service;
import com.cc21.spe.service.S3ServiceImpl;
import com.cc21.spe.service.SqsService;
import com.cc21.spe.service.SqsServiceImpl;

public class AppConfig {
	
	@Bean
	public LoadBalanceService loadBalanceService() {
		return new LoadBalanceServiceImpl();
	}
	
	@Bean
	public SqsService sqsService() {
		return new SqsServiceImpl();
	}

	@Bean
	public SqsRepo sqsRepo() {
		return new SqsRepoImpl();
	}
	
	@Bean
	public S3Repo s3Repo() {
		return new S3RepoImpl();
	}

	@Bean
	public S3Service s3Service() {
		return new S3ServiceImpl();
	}
	
	@Bean
	public Ec2Repo ec2Repo() {
		return new Ec2RepoImpl();
	}

	@Bean
	public Ec2Service ec2Service() {
		return new Ec2ServiceImpl();
	}
	
	@Bean
	public AwsConfiguration awsConfiguration() {
		return new AwsConfiguration();
	}
	
}
