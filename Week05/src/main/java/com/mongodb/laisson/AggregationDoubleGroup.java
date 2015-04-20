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
public class AggregationDoubleGroup {

    /**
     * resources\zips.json
     */
    private static MongoCollection<Document> doubleGroup;

    public static void main(String[] args) {
	doubleGroup = ConnectionBase.connect("week05", "doubleGroup");
	doubleGroup.drop();

	queryInsertDoubleGroup();
	queryDoubleGroup();
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
     */
    private static void queryInsertDoubleGroup() {
	doubleGroup.insertOne(new Document("a", 0).append("b", 0).append("c", 21));
	doubleGroup.insertOne(new Document("a", 0).append("b", 0).append("c", 54));
	doubleGroup.insertOne(new Document("a", 0).append("b", 1).append("c", 52));
	doubleGroup.insertOne(new Document("a", 0).append("b", 1).append("c", 17));
	doubleGroup.insertOne(new Document("a", 1).append("b", 0).append("c", 22));
	doubleGroup.insertOne(new Document("a", 1).append("b", 0).append("c", 5));
	doubleGroup.insertOne(new Document("a", 1).append("b", 1).append("c", 87));
	doubleGroup.insertOne(new Document("a", 1).append("b", 1).append("c", 97));
    }

    /**
     * db.doubleGroup.aggregate([{$group:{_id:{a:"$a", b:"$b"}, c:{$max:"$c"}}}, {$group:{_id:"$_id.a", c:{$min:"$c"}}}]);
     */
    private static void queryDoubleGroup() {

	List<Document> results = //
		doubleGroup.aggregate(//
			Arrays.asList(//
			new Document("$group", //
				new Document("_id", //
					new Document("a", "$a")//
						.append("b", "$b"))//
					.append("c", //
						new Document("$max", "$c")//
					)//
			), new Document("$group", //
				new Document("_id", "$_id.a") //
					.append("c", //
						new Document("$min", "$c")//
					)//
			))//
			)//
			.into(new ArrayList<Document>());

	System.out.println("db.doubleGroup.aggregate([{$group:{_id:{a:'$a', b:'$b'}, c:{$max:'$c'}}}, {$group:{_id:'$_id.a', c:{$min:'$c'}}}]);\n");
	for (Document zipAggregate : results) {
	    printJson(zipAggregate, false);
	}

    }
}
