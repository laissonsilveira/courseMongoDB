package com.mongodb.laisson;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.laisson.util.Helpers.printJson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Homework {
    public static void main(String[] args) {

	MongoClient client = new MongoClient();
	MongoDatabase db = client.getDatabase("school");
	MongoCollection<Document> collection = db.getCollection("students");

	List<Document> students = collection.find().sort(ascending("_id"))
		.into(new ArrayList<Document>());
	Map<Double, Double> scoreToDelete = new HashMap<Double, Double>();

	for (Document student : students) {
	    List<Document> scores = (List<Document>) student.get("scores");
	    final Double idStudent = student.getDouble("_id");
	    for (Document score : scores) {
		if ("homework".equals(score.getString("type"))) {
		    final Double scoreValue = score.getDouble("score");
		    if (scoreToDelete.containsKey(idStudent)) {
			if (scoreToDelete.get(idStudent).doubleValue() > scoreValue
				.doubleValue()) {
			    scoreToDelete.remove(idStudent);
			    scoreToDelete.put(idStudent, scoreValue);
			}
		    } else {
			scoreToDelete.put(idStudent, scoreValue);
		    }
		}
	    }
	}

	for (Entry<Double, Double> score : scoreToDelete.entrySet()) {
	    final Bson query = eq("_id", score.getKey());
	    final Document fields = new Document("scores", new Document(
		    "score", score.getValue()));
	    final Document update = new Document("$pull", fields);
	    collection.updateOne(query, update);
	}

	System.out.println("db.students.count() == 200: "
		+ (collection.count() == 200l));
	System.out.println("db.students.find({_id:137}) is: ");
	printJson(collection.find(eq("_id", 137)).first());

	resultHW(collection);
    }

    private static void resultHW(MongoCollection<Document> collection) {
	System.out.println("\nResposta HW3-1: ");
	Document unwind = new Document("$unwind", "$scores");

	Document groupFields = new Document("_id", "$_id");
	groupFields.put("average", new Document("$avg", "$scores.score"));
	Document group = new Document("$group", groupFields);

	Document sort = new Document("$sort", new Document("average", -1));

	Document limit = new Document("$limit", 1);

	List<Document> pipeline = Arrays.asList(unwind, group, sort, limit);
	printJson(collection.aggregate(pipeline).first());
    }
}