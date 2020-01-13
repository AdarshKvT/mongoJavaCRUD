package com.acc.Utility;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class mongoConnecton {

	public static JSONObject mongoConnecton(String string) {
		
		String uriString = "mongodb+srv://sachin:sachin@cluster0-emxhp.mongodb.net/test?retryWrites=true&w=majority";

		MongoClientURI uri = new MongoClientURI(uriString);
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("testDatabse");
		MongoCollection<Document> collection_name = database.getCollection("collection_name");
		return null;
	
	}
}
