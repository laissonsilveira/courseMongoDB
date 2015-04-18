package com.mongodb.laisson;

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
public class AggregationPush {

    /**
     * resources\zips.json
     */
    private static MongoCollection<Document> zips;

    public static void main(String[] args) {
	zips = ConnectionBase.connect("week05", "zips");

	queryPush();
    }

    /**
     * db.zips.aggregate([{$group:{"_id":"$state", "cityes":{"$push":"$_city"}}}]);
     */
    @SuppressWarnings("unchecked")
    private static void queryPush() {

	List<Document> results = //
		zips.aggregate(//
			Arrays.asList(//
				new Document("$group", //
					new Document("_id", "$state")//
				.append("cityes", //
					new Document("$push", "$city")//
					)//
					)//
				)//
			).into(new ArrayList<Document>());

	System.out.println("--- Query Push: groupby State, push city ---");
	System.out.println("db.zips.aggregate([{$group:{'_id':'$state', 'cityes':{'$push':'$_city'}}}]);\n");
	for (Document zipAggregate : results) {
	    List<String> cityes = (List<String>) zipAggregate.get("cityes");
	    System.out.println("State: [" + zipAggregate.getString("_id") + "]: " + cityes.size());

	    // printJson(zipAggregate, false);
	}

    }
}
