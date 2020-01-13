package com.acc.CrudOperations;

import java.io.InputStream;
import java.io.OutputStream;

import org.bson.Document;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DeleteOperation implements RequestStreamHandler {
	public void handleRequest(InputStream input, OutputStream output, Context context) {

		// for logging the console logs
		LambdaLogger logger = context.getLogger();

		String uriString = "connectionUrl";
		// building a connection with mongo cluster
		MongoClientURI uri = new MongoClientURI(uriString);
		MongoClient mongoClient = new MongoClient(uri);

		// get database and colletion
		MongoDatabase database = mongoClient.getDatabase("testDatabase");
		MongoCollection<Document> collection_name = database.getCollection("collection_name");

		// Filter query
		Document query = new Document();
		query.append("name", "solarSystem");

		try {

			// Delete operation
			collection_name.deleteOne(query);
			System.out.println("\nDocument deleted successfully");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(e.getMessage());
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}

	}

}
