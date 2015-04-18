package com.mongodb.laisson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import com.mongodb.client.MongoCollection;

public class AggregationSUM {

    /**
     * resources\zips.json
     */
    private static MongoCollection<Document> zips;

    public static void main(String[] args) {
	zips = ConnectionBase.connect("week05", "zips");

	System.out.println("--- Query SUM: groupby State, sum Pop ---");
	querySUM();
    }

    /**
     * db.zips.aggregate([{$group:{'_id':'$state',
     * population:{'$sum':'$pop'}}}]);;
     */
    private static void querySUM() {
	List<Document> results = //
	zips.aggregate(//
		Arrays.asList(//
		new Document("$group", //
			new Document("_id", "$state")//
				.append("population", //
					new Document("$sum", "$pop")//
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
