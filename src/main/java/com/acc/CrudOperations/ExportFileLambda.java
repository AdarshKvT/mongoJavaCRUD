package com.acc.CrudOperations;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Base64;

import org.json.JSONObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;

public class ExportFileLambda implements RequestStreamHandler {

	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

		//for logging the messages
		LambdaLogger logger = context.getLogger();
		JSONObject response = new JSONObject();
		//read the input file with Buffered reader 
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));

		try {
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			//use JSONObject for performing further JSON parsing
			JSONObject jObject = new JSONObject(sb);
			
			// get header from input JSON
			JSONObject header = new JSONObject();
			String contentType = header.getString("content-type");

			// get fileName and type from input JSON header
			String fileName = "";
			String type = header.getString("type");

			String file = fileName + type;

			// change the contentType according to the file uploading need
			if (contentType.equals("application/pdf")) {

				// fileName
				String objectKey = file + ".pdf"; // fileName + .filextention
				String bucketName = "BucketName/Resume";

				// get the "content" from JObject and perform base64 decoding (input file is
				// base64 encoded by API Gateway)
				InputStream stream = null;  //ini
				byte[] base64decodedBytes = Base64.getDecoder().decode(jObject.getString("content"));
				stream = new ByteArrayInputStream(base64decodedBytes);

				// create metadata which contains addition info about the file needs to be
				ObjectMetadata meta = null;
				meta = new ObjectMetadata();
				meta.setContentLength(base64decodedBytes.length);
				meta.setContentType(contentType);

				// build a connection with s3Client
				AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();

				// final upload operaton
				s3Client.putObject(bucketName, objectKey, stream, meta);
				System.out.println("File has been uploaded successfully");
				
				//get url and dwonload the uploading object
				System.out.println("Generating document URL for down....");
				URL s3Url = s3Client.getUrl(bucketName, objectKey);
				System.out.println("Object URl:" + s3Url.toExternalForm());
				response.put("s3url", s3Url);
				logger.log("save s3url successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(e.getMessage());
		}

	}
}
