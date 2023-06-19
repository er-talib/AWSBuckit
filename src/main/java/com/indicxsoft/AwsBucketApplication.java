package com.indicxsoft;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@SpringBootApplication
public class AwsBucketApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsBucketApplication.class, args);
		
		AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.AP_SOUTH_1)
                .withCredentials(new AWSStaticCredentialsProvider(
                                      new BasicAWSCredentials("access key ","secret key")))
                .build();
		
//		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
//	              .build();
		
//		AmazonS3 s3 = AmazonS3ClientBuilder
//                .standard()
//                .withRegion(Regions.AP_SOUTH_1)
//                .withCredentials(new InstanceProfileCredentialsProvider(false))
//                .build();
//         
        try {   
            /* Get first batch of objects in a given bucket */
            ObjectListing objects = s3.listObjects("bkp-odfss");
//            ObjectListing objects = s3.listObjects("lb-aws-learning");

            List<Bucket> listBuckets ;
            /* Recursively get all the objects inside given bucket */
            if(objects != null && objects.getObjectSummaries() != null) {               
                while (true) {                  
                    for(S3ObjectSummary summary : objects.getObjectSummaries()) {
                        System.out.println("Object Id :-" + summary.getKey());
                    }
                    
                     
                    if (objects.isTruncated()) {
                        objects = s3.listNextBatchOfObjects(objects);
                          listBuckets = s3.listBuckets();
                    } else {
                        break;
                    }                   
                }
            }
             
        } catch (AmazonServiceException e) {
             
            System.out.println(e.getErrorMessage());
            e.printStackTrace();
             
        } finally {
             
            if(s3 != null) {
                s3.shutdown();
            }           
        }
		
//		BuckitObject object = new BuckitObject();
//		object.getObject();
	}

}
