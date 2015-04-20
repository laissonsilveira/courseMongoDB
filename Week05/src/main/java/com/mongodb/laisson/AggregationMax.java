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
 *         Apr 19, 2015
 */
public class AggregationMax {

    /**
     * resources\zips.json
     */
    private static MongoCollection<Document> zips;

    public static void main(String[] args) {
	zips = ConnectionBase.connect("week05", "zips");

	queryMin();
    }

    /**
     * db.zips.aggregate([{$group:{_id:"$state", max_pop:{$max:"$pop"}}}]);
     */
    private static void queryMin() {

	List<Document> results = //
	zips.aggregate(//
		Arrays.asList(//
		new Document("$group", //
			new Document("_id", "$state")//
				.append("max_pop", //
					new Document("$max", "$pop")//
				)//
		)//
		)//
	).into(new ArrayList<Document>());

	System.out.println("--- Query Push: groupby State, máx Population ---");
	System.out.println("db.zips.aggregate([{$group:{_id:'$state', max_pop:{$max:'$pop'}}}]);\n");
	for (Document zipAggregate : results) {
	    printJson(zipAggregate, false);
	}

    }
}
