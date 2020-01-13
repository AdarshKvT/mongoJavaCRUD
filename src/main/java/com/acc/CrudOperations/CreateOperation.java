package com.acc.CrudOperations;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bson.Document;

import com.acc.Utility.*;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.mongodb.InsertOptions;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class CreateOperation implements RequestStreamHandler {
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

		// for logging the console logs
		LambdaLogger logger = context.getLogger();

		String uriString = "mongodb+srv://sachin:sachin@cluster0-emxhp.mongodb.net/test?retryWrites=true&w=majority";

		MongoClientURI uri = new MongoClientURI(uriString);
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("testDatabse");
		MongoCollection<Document> collection = database.getCollection("collection");

		// Create a document consist of keys and values
		Document solarSystem = new Document();
		solarSystem.append("Mercury", 1);
		solarSystem.append("Venus", 2);
		solarSystem.append("Earth", "Habitable Planet");
		solarSystem.append("Mars", 4);

		try {

			// insert Operation
			collection.insertOne(solarSystem);

			System.out.println("\nDocument inserted successfully!!");

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(e.getMessage());
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}

	}
}
