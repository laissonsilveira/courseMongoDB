package course.homework;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Homework {
    public static void main(String[] args) {

	MongoClient client = new MongoClient();
	MongoDatabase database = client.getDatabase("students");
	final MongoCollection<Document> collection = database
		.getCollection("grades");

	List<Document> results = collection.find(eq("type", "homework"))
		.sort(ascending("student_id", "score"))
		.into(new ArrayList<Document>());

	List<Document> studentsRemove = new ArrayList<Document>();
	for (Document result : results) {
	    if (!studentsRemove.isEmpty()) {
		boolean contains = false;
		for (Document student : studentsRemove) {
		    if (student.getDouble("student_id").equals(
			    result.getDouble("student_id"))) {
			contains = true;
			break;
		    }
		}
		if (!contains) {
		    studentsRemove.add(result);
		}
	    } else {
		studentsRemove.add(result);
	    }
	}

	for (Document document : studentsRemove) {
	    collection.deleteOne(eq("_id", document.get("_id")));
	}

	System.out.println("Done: " + (collection.count() == 600l));
    }
}
