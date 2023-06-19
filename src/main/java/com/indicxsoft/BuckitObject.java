package com.indicxsoft;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class BuckitObject {
	
	
	public void getObject() {
	
		AmazonS3 s3 = AmazonS3ClientBuilder
              .standard()
              .withRegion(Regions.AP_SOUTH_1)
              .withCredentials(new InstanceProfileCredentialsProvider(false))
              .build();
	
	try {
	    s3.copyObject("bkp-odfss", "test.txt", "bkp-odfss", "main.txt");
	} catch (AmazonServiceException e) {
	    System.err.println(e.getErrorMessage());
	    System.exit(1);
	}
	System.out.println("Done!");
	}
}
