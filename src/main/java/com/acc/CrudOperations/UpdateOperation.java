package com.acc.CrudOperations;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bson.Document;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class UpdateOperation implements RequestStreamHandler {
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

		// for logging the console logs
		LambdaLogger logger = context.getLogger();
		
		// update a document by using query
		String uriString = "mongodb+srv://sachin:sachin@cluster0-emxhp.mongodb.net/test?retryWrites=true&w=majority";
		MongoClientURI uri = new MongoClientURI(uriString);
		MongoClient mongoClient = new MongoClient(uri);

		try {

			// get database and collection
			MongoDatabase database = mongoClient.getDatabase("testDatabse");
			MongoCollection<Document> collname = database.getCollection("collection");

			// filter the collection
			Document query = new Document();
			query.append("name", "solarSystem");

			// Document to be uploaded into mongo Collection
			Document update = new Document();
			update.append("Mars", "Red Planet. It's red because of rusty iron in the ground");
			update.append("Venus", "Venus, the second-brightest natural object in the night sky after the Moon");
			update.append("Earth", "Earth, the only astronomical object known to harbor life");

			// Final Update operation
			Document updateDoc = new Document("$set", update);
			collname.updateOne(query, updateDoc);

			System.out.println("\nDocument has been succesfully updated");

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(e.getMessage());
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}

	}

}
