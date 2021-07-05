package com.cc21.spe.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc21.spe.repo.S3Repo;

@Service
public class S3ServiceImpl implements S3Service {
	
	@Autowired
	private S3Repo s3Repo;
	
	
	@Override
    public List<String> getResponseResults() {
		return s3Repo.getResponseResults();
	}
	
	@Override
	public void uploadFileToS3Bucket(String bucketName, File file)
	{
		s3Repo.uploadFileToS3Bucket(bucketName, file);
	}
	
}