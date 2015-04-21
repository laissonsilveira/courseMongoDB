package com.mongodb.laisson;

import static com.mongodb.laisson.util.HelperJson.printJson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import com.mongodb.laisson.util.ConnectionBase;

/**
 * 
 * @author Laisson R. Silveira
 *         laisson.r.silveira@gmail.com
 *         Apr 20, 2015
 */
public class AggregationSORT {

    /**
     * resources\zips.json
     */
    private static MongoCollection<Document> zips;

    public static void main(String[] args) {
	zips = ConnectionBase.connect("week05", "zips");

	querySORT();
    }

    /**
     * db.zips.aggregate([
     * {$match:{state:"NY"}},
     * {$group:{_id:"$city",population:{$sum:"$pop"},zip_codes:{$addToSet:"$_id"}}},
     * {$project:{_id:0,city:"$_id",population:1,zip_codes:1}}]);
     */
    private static void querySORT() {

	Document match = new Document("$match", //
		new Document("state", "NY")//
	);//

	Document group = new Document("$group", //
		new Document("_id", "$city")//
			.append("population", //
				new Document("$sum", "$pop")//
			)//
			.append("zip_codes", //
				new Document("$addToSet", "$_id")//
			)//
	);//

	Document project = new Document("$project", //
		new Document("_id", 0)//
			.append("city", "$_id")//
	.append("population", 1)//
	.append("zip_codes", 1));//

	Document sort = new Document("$sort", Sorts.descending("population"));

	List<Document> results = zips.aggregate(Arrays.asList(match, group, project, sort)).into(new ArrayList<Document>());

	System.out.println("--- Query $match, $group, $project and $sort ---");
	System.out.println("db.zips.aggregate(["//
		+ "{$match:{state:'NY'}},"
		+ "{$group:{_id:'$city',population:{$sum:'$pop'},zip_codes:{$addToSet:'$_id'}}},"
		+ "{$project:{_id:0,city:'$_id',population:1,zip_codes:1}}]);\n");
	for (Document zipAggregate : results) {
	    printJson(zipAggregate, false);
	}

    }
}
