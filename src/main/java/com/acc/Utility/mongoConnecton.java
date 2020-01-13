package com.acc.Utility;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class mongoConnecton {

	public static JSONObject mongoConnecton(String string) {
		
		String uriString = "connectionUrl";

		//build a connection with mongo cluster
		MongoClientURI uri = new MongoClientURI(uriString);
		MongoClient mongoClient = new MongoClient(uri);
		
		//get database and collection
		MongoDatabase database = mongoClient.getDatabase("testDatabase");
		MongoCollection<Document> collection_name = database.getCollection("collection_name");
		return null;
	
	}
}
