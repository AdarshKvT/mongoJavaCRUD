package com.acc.CrudOperations;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.json.JSONObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class ImportFileLambda implements RequestStreamHandler {
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

		// for logging the messages
		LambdaLogger logger = context.getLogger();
		JSONObject response = new JSONObject();
		response.put("message", "Generating url for downloading s3object... ");
		String bucketName = null;
		String key = null;
		try {
			
			// build a connection with s3Client
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();
			
			URL s3Url = s3Client.getUrl(bucketName, key);
			
			System.out.println("Object URl:" + s3Url.toExternalForm());
			
			response.put("s3url", s3Url);
			logger.log("save s3url successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
