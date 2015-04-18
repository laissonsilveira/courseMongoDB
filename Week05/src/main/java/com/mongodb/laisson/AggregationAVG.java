package com.mongodb.laisson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import com.mongodb.client.MongoCollection;

public class AggregationAVG {

    /**
     * resources\zips.json
     */
    private static MongoCollection<Document> zips;

    public static void main(String[] args) {
	zips = ConnectionBase.connect("week05", "zips");

	System.out.println("--- Query AVG: groupby State, avg Pop ---");
	querySUM();
    }

    /**
     * db.zips.aggregate([{$group:{'_id':'$state',
     * population:{'$avg':'$pop'}}}]);
     */
    private static void querySUM() {
	List<Document> results = //
	zips.aggregate(//
		Arrays.asList(//
		new Document("$group", //
			new Document("_id", "$state")//
				.append("avg_population", //
					new Document("$avg", "$pop")//
					)//
		)//
		)//
	).into(new ArrayList<Document>());

	for (Document productAggregate : results) {
	    System.out.println(productAggregate.toJson(new JsonWriterSettings(
		    true)));
	}
    }

}
