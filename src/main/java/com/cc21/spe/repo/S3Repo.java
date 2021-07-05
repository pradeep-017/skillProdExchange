package com.cc21.spe.repo;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.amazonaws.services.s3.model.Bucket;

@Repository
public interface S3Repo {

	List<String> getResponseResults();

	Bucket getBucket(String bucketName);

	Bucket createBucket(String bucketName);

	void uploadFileToS3Bucket(String bucketName, File file);
}
