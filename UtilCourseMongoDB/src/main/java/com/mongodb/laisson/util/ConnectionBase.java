package com.mongodb.laisson.util;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ConnectionBase {

    public static MongoCollection<Document> connect(String dataBaseName,
	    String collectionName) {
	MongoClient client = new MongoClient();
	MongoDatabase database = client.getDatabase(dataBaseName);
	final MongoCollection<Document> collection = database
		.getCollection(collectionName);
	return collection;
    }

}
