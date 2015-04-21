package com.mongodb.laisson;

import static com.mongodb.laisson.util.HelperJson.printJson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.laisson.util.ConnectionBase;

/**
 * 
 * @author Laisson R. Silveira
 *         laisson.r.silveira@gmail.com
 *         Apr 20, 2015
 */
public class AggregationPROJECT {

    /**
     * resources\zips.json
     */
    private static MongoCollection<Document> zips;

    public static void main(String[] args) {
	zips = ConnectionBase.connect("week05", "zips");

	queryProject();
    }

    /**
     * db.zips.aggregate([{$project:{_id:0,'city':{$toLower:'$city'},pop:1,state:1,'zip':'$_id'}}]);
     */
    private static void queryProject() {

	List<Document> results = //
	zips.aggregate(//
		Arrays.asList(//
		new Document("$project", //
			new Document("_id", 0)//
				.append("city", //
					new Document("$toLower", "$city")//
				)//
				.append("pop", 1)//
				.append("state", 1)//
				.append("zip", "$_id")//
		)//
		)//
	).into(new ArrayList<Document>());

	System.out.println("--- Query $project ---");
	System.out.println("db.zips.aggregate([{$project:{_id:0,'city':{$toLower:'$city'},pop:1,state:1,'zip':'$_id'}}]);\n");
	for (Document zipAggregate : results) {
	    printJson(zipAggregate, false);
	}

    }
}
