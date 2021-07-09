package com.cc21.spe.config;

import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.cc21.spe.constants.Constants;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AwsConfiguration {

	public BasicAWSCredentials basicAWSCredentials() {
		return new BasicAWSCredentials(Constants.AWS_ACCESS_KEY, Constants.AWS_SECRET_KEY);
	}

	public AmazonS3 awsS3() {
		AmazonS3 awsS3Client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials()))
				.withRegion(Constants.AWS_REGION)
				.build();
		return awsS3Client;
	}

}
