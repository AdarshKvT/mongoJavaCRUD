package com.acc.CrudOperations;

import java.io.InputStream;
import java.io.OutputStream;

import org.bson.Document;
import org.omg.CORBA.PUBLIC_MEMBER;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class ReadOperation implements RequestStreamHandler {
	public void handleRequest(InputStream input, OutputStream output, Context context) {
		String uriString = "connectionUrl";

		MongoClientURI uri = new MongoClientURI(uriString);
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("testDatabase");
		MongoCollection<Document> collname = database.getCollection("collection");

		// =======( Read entire collection from database-->findAll)

		// iterating through entire collection
		MongoCursor<Document> cursor = collname.find().iterator();
		try {
			while (cursor.hasNext()) {
				System.out.println("Documents in collection: " + cursor.next().toJson());
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		// =======( Read queried document only from the collection-->findOne)

		// filter
		Document query = new Document();
		query.append("name", "solarSystem");

		// iterating through collection using query
		MongoCursor<Document> cursor2 = collname.find(query).iterator();
		try {
			while (cursor2.hasNext()) {
				System.out.println("Filtered document: " + cursor2.next().toJson());

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

	}

}
