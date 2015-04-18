package com.mongodb.laisson;

import static com.mongodb.laisson.util.HelperJson.printJson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.laisson.util.ConnectionBase;

public class SimpleAggregationExample {

    /**
     * resources\simple_aggregation_example\products.js
     */
    private static MongoCollection<Document> products;

    public static void main(String[] args) {
	products = ConnectionBase.connect("lecture02", "products");

	// products.drop();
	// insertProducts();
	// findAllProducts();

	System.out.println("--- GroupBy Manufacturer ---");
	queryCountProdutsGroupByManufacturer();
	System.out.println("--- GroupBy Manufacturer and Category ---");
	queryCountProdutsGroupByManufacturerAndCategory();
    }

    private static void findAllProducts() {
	for (Document product : products.find()) {
	    printJson(product);
	}
    }

    /**
     * db.products.aggregate([ {$group: { _id:"$manufacturer",
     * num_products:{$sum:1} } } ])
     */
    private static void queryCountProdutsGroupByManufacturer() {
	List<Document> results = //
		products.aggregate(//
			Arrays.asList(//
				new Document("$group", //
					new Document("_id", "$manufacturer")//
				.append("num_products", //
					new Document("$sum", 1)//
					)//
					)//
				)//
			).into(new ArrayList<Document>());

	for (Document productAggregate : results) {
	    printJson(productAggregate);
	}
    }

    /**
     * db.products.aggregate([ {$group: { _id:{ "manufacturer":"$manufacturer",
     * "category":"$category" num_products:{$sum:1} } } ]);
     */
    private static void queryCountProdutsGroupByManufacturerAndCategory() {
	List<Document> results = //
		products.aggregate(//
			Arrays.asList(//
				new Document("$group", //
					new Document("_id", //
						new Document("manufacturer", "$manufacturer")//
					.append("category", "$category")//
						)//
				.append("num_products", //
					new Document("$sum", 1)//
					)//
					)//
				)//
			).into(new ArrayList<Document>());

	for (Document productAggregate : results) {
	    printJson(productAggregate);
	}
    }

    private static void insertProducts() {
	for (int i = 0; i < 10; i++) {
	    products.insertOne(new Document("name", "Product" + i).append("manufacturer", "Apple").append("price", new Random().nextDouble() * 10));
	}

	for (int i = 0; i < 5; i++) {
	    products.insertOne(new Document("name", "Product" + (i * 3)).append("manufacturer", "Samsung").append("price",
		    new Random().nextDouble() * 10));
	}
    }

}
