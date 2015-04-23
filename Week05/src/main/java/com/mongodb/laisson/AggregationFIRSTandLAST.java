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
 * @author Laisson R. Silveira
 *         laisson.r.silveira@gmail.com
 *         Apr 22, 2015
 */
public class AggregationFIRSTandLAST {

    /**
     * resources\zips.json
     */
    private static MongoCollection<Document> zips;

    public static void main(String[] args) {
	zips = ConnectionBase.connect("week05", "zips");

	queryFIRST_LAST();
	// queryQUIZ();
    }

    /**
     * { "_id" : 0, "a" : 0, "b" : 0, "c" : 21 }
     * { "_id" : 1, "a" : 0, "b" : 0, "c" : 54 }
     * { "_id" : 2, "a" : 0, "b" : 1, "c" : 52 }
     * { "_id" : 3, "a" : 0, "b" : 1, "c" : 17 }
     * { "_id" : 4, "a" : 1, "b" : 0, "c" : 22 }
     * { "_id" : 5, "a" : 1, "b" : 0, "c" : 5 }
     * { "_id" : 6, "a" : 1, "b" : 1, "c" : 87 }
     * { "_id" : 7, "a" : 1, "b" : 1, "c" : 97 }
     * 
     * db.fun.aggregate([{$match:{a:0}},{$sort:{c:-1}},{$group:{_id:"$a",c:{$first:"$c"}}}]);
     */
    private static void queryQUIZ() {
	MongoCollection<Document> quiz = ConnectionBase.connect("week05", "quizFirstLast");

	quiz.drop();
	quiz.insertOne(new Document("_id", 0).append("a", 0).append("b", 0).append("c", 21));
	quiz.insertOne(new Document("_id", 1).append("a", 0).append("b", 0).append("c", 54));
	quiz.insertOne(new Document("_id", 2).append("a", 0).append("b", 1).append("c", 52));
	quiz.insertOne(new Document("_id", 3).append("a", 0).append("b", 1).append("c", 17));
	quiz.insertOne(new Document("_id", 4).append("a", 1).append("b", 0).append("c", 22));
	quiz.insertOne(new Document("_id", 5).append("a", 1).append("b", 0).append("c", 5));
	quiz.insertOne(new Document("_id", 6).append("a", 1).append("b", 1).append("c", 87));
	quiz.insertOne(new Document("_id", 7).append("a", 1).append("b", 1).append("c", 97));

	Document match = new Document("$match", new Document("a", 0));
	Document sort = new Document("$sort", Sorts.descending("c"));
	Document group = new Document("$group", new Document("_id", "a").append("c", new Document("$first", "$c")));

	// db.fun.aggregate([{$match:{a:0}},{$sort:{c:-1}},{$group:{_id:"$a",c:{$first:"$c"}}}]);
	ArrayList<Document> result = quiz.aggregate(Arrays.asList(match, sort, group)).into(new ArrayList<Document>());

	for (Document document : result) {
	    printJson(document, true);
	}
    }

    /**
     * db.zips.aggregate([
     * {$group:{_id:{state:'$state',city:'$city'},population:{$sum:'$pop'},}},
     * {$sort:{'_id.state':1,'population':-1}},
     * {$group:{_id:'$_id.state',city:{$first:'$_id.city'},population:{$first:'$population'}}},
     * {$sort:{'_id':1}}])
     * ;
     */
    private static void queryFIRST_LAST() {

	Document group01 = new Document("$group", //
		new Document("_id", //
			new Document("state", "$state")//
		.append("city", "$city")//
			).append("population", //
				new Document("$sum", "$pop")//
				)//
		);//

	Document sort01 = new Document("$sort", new Document("_id.state", 1).append("population", -1));

	Document group02 = new Document("$group", //
		new Document("_id", "$_id.state") //
	.append("city", //
		new Document("$first", "$_id.city")//
		).append("population", //
			new Document("$first", "$population")//
			)//
		);//

	Document sort02 = new Document("$sort", Sorts.ascending("_id"));

	List<Document> results = zips.aggregate(Arrays.asList(group01, sort01, group02, sort02)).into(new ArrayList<Document>());

	System.out.println("--- Query $first ---");
	System.out.println(" db.zips.aggregate([" + //
		" \n{$group:{_id:{state:'$state',city:'$city'},population:{$sum:'$pop'},}}," + //
		" \n{$sort:{'_id.state':1,'population':-1}}," + //
		" \n{$group:{_id:'$_id.state',city:{$first:'$_id.city'},population:{$first:'$population'}}}," + //
		" \n{$sort:{'_id':1}}])\n");
	for (Document zipAggregate : results) {
	    printJson(zipAggregate, false);
	}

    }
}
