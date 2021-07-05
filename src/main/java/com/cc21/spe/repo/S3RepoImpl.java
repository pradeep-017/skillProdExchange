package com.cc21.spe.repo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.cc21.spe.config.AwsConfiguration;
import com.cc21.spe.constants.Constants;

@Repository
public class S3RepoImpl implements S3Repo {

	private static final Logger logger = LoggerFactory.getLogger(Ec2RepoImpl.class);

	@Autowired
	private AwsConfiguration awsConfiguration;

	@Override
	public void uploadFileToS3Bucket(final String bucketName, final File file) {
		createBucket(bucketName);
		final String fileName = file.getName();
		logger.info("Uploading file with name:" + fileName);
		final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
		awsConfiguration.awsS3().putObject(putObjectRequest);
	}

	@Override
	public Bucket createBucket(String bucketName) {
		Bucket s3Bucket = null;
		if (awsConfiguration.awsS3().doesBucketExistV2(bucketName)) {
			logger.info(Constants.GET_BUCKET + bucketName);
			s3Bucket = getBucket(bucketName);
		} else {
			logger.info(Constants.CREATE_BUCKET + bucketName);
			s3Bucket = awsConfiguration.awsS3().createBucket(bucketName);
		}

		return s3Bucket;
	}

	@Override
	public Bucket getBucket(String bucketName) {
		Bucket s3Bucket = null;
		List<Bucket> buckets = awsConfiguration.awsS3().listBuckets();
		for (Bucket b : buckets) {
			if (b.getName().equals(bucketName))
				s3Bucket = b;
		}

		return s3Bucket;
	}

	@Override
	public List<String> getResponseResults() {
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(Constants.OUTPUT_S3);

		List<String> keys = new ArrayList<>();

		ObjectListing objects = awsConfiguration.awsS3().listObjects(listObjectsRequest);

		for (S3ObjectSummary item : objects.getObjectSummaries()) {
			keys.add(item.getKey());
		}

		return keys;
	}

}
