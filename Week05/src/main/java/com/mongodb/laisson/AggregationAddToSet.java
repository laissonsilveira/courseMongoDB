package com.mongodb.laisson;

import static com.mongodb.laisson.util.HelperJson.printJson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.laisson.util.ConnectionBase;

/**
 * @author Laisson R. Silveira
 *         laisson.r.silveira@gmail.com
 *         Apr 18, 2015
 */
public class AggregationAddToSet {

    /**
     * resources\zips.json
     */
    private static MongoCollection<Document> zips;

    public static void main(String[] args) {
	zips = ConnectionBase.connect("week05", "zips");

	queryAddToSet();
    }

    /**
     * db.zips.aggregate([{$group:{"_id":"$city", "postal_codes":{"$addToSet":"$_id"}}}]);
     */
    private static void queryAddToSet() {
	System.out.println("--- Query AddToSet: groupby City, addToSet zip-codes ---");

	List<Document> results = //
		zips.aggregate(//
			Arrays.asList(//
				new Document("$group", //
					new Document("_id", "$city")//
				.append("postal_codes", //
					new Document("$addToSet", "$_id")//
				)//
					)//
				)//
			).into(new ArrayList<Document>());

	for (Document zipAggregate : results) {
	    printJson(zipAggregate, false);
	}
    }

}
