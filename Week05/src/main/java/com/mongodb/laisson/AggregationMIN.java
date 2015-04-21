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
public class AggregationMIN {

    /**
     * resources\zips.json
     */
    private static MongoCollection<Document> zips;

    public static void main(String[] args) {
	zips = ConnectionBase.connect("week05", "zips");

	queryMIN();
    }

    /**
     * db.zips.aggregate([{$group:{_id:"$state", max_pop:{$min:"$pop"}}}]);
     */
    private static void queryMIN() {

	List<Document> results = //
	zips.aggregate(//
		Arrays.asList(//
		new Document("$group", //
			new Document("_id", "$state")//
				.append("max_pop", //
					new Document("$min", "$pop")//
				)//
		)//
		)//
	).into(new ArrayList<Document>());

	System.out.println("--- Query $min: groupby State, min Population ---");
	System.out.println("db.zips.aggregate([{$group:{_id:'$state', max_pop:{$min:'$pop'}}}]);\n");
	for (Document zipAggregate : results) {
	    printJson(zipAggregate, false);
	}

    }
}
