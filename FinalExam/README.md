FINAL EXAM

Question 01
	
	Please download the Enron email dataset enron.zip, unzip it and then restore it using mongorestore. It should restore to a collection called "messages" in a database called "enron". Note that this is an abbreviated version of the full corpus. There should be 120,477 documents after restore. 

	Inspect a few of the documents to get a basic understanding of the structure. Enron was an American corporation that engaged in a widespread accounting fraud and subsequently failed. 

	In this dataset, each document is an email message. Like all Email messages, there is one sender but there can be multiple recipients. 

	Construct a query to calculate the number of messages sent by Andrew Fastow, CFO, to Jeff Skilling, the president. Andrew Fastow's email addess was andrew.fastow@enron.com. Jeff Skilling's email was jeff.skilling@enron.com. 

	For reference, the number of email messages from Andrew Fastow to John Lavorato (john.lavorato@enron.com) was 1. 

	R:
		db.messages.count({"headers.From":"andrew.fastow@enron.com", "headers.To":"jeff.skilling@enron.com"});

			1
		√	3
			5
			7
			9
			12

Question 02

	Please use the Enron dataset you imported for the previous problem. For this question you will use the aggregation framework to figure out pairs of people that tend to communicate a lot. To do this, you will need to unwind the To list for each message. 

	This problem is a little tricky because a recipient may appear more than once in the To list for a message. You will need to fix that in a stage of the aggregation before doing your grouping and counting of (sender, recipient) pairs. 

	Which pair of people have the greatest number of messages in the dataset?

	R:
		db.messages.aggregate([{$unwind:"$headers.To"}, {$group:{"_id":{"from":"$headers.From","to":"$headers.To"},"count":{$sum:1}}},{$sort:{"count":-1}}]);

		√	susan.mara@enron.com to jeff.dasovich@enron.com
			susan.mara@enron.com to richard.shapiro@enron.com
			soblander@carrfut.com to soblander@carrfut.com
			susan.mara@enron.com to james.steffes@enron.com
			evelyn.metoyer@enron.com to kate.symes@enron.com
			susan.mara@enron.com to alan.comnes@enron.com

Question 03

	This is a Hands On problem. In this problem, the database will begin in an initial state, you will manipulate it, and we will verify that the database is in the correct final state when you click 'submit'. If you need to start over at any point, you can click 'reset' to re-initialize the database, but this will not change your answer if you have already clicked 'submit'. If you wish to change your answer, get the database into the correct state, and then click 'submit'. If you leave the question and come back, the database will re-initialize. If you have clicked the 'submit' button at least once, you will see the word "Submitted" below the shell.

	In this problem you will update a document in the messages collection to illustrate your mastery of updating documents from the shell. In fact, we've created a collection with a very similar schema to the Enron dataset, but filled instead with randomly generated data.

	Please add the email address "mrpotatohead@10gen.com" to the list of addresses in the "headers.To" array for the document with "headers.Message-ID" of "<8147308.1075851042335.JavaMail.evans@thyme>"

	This is a fully functional web shell, so please press enter for your query to get passed to the server, just like you would for the command line shell.

	Note: The web shell is currently using MongoDB version 2.4, so it will not acknowledge a properly performed update. Upgrading the shell is on our to-do list, but for now, we recommend checking with a find() query to verify that your update worked properly before submitting.

	R:
		db.messages.update({"headers.Message-ID":"<8147308.1075851042335.JavaMail.evans@thyme>"}, {"$addToSet":{"headers.To":"mrpotatohead@10gen.com"}});

Question 04

	Change in blog. Project /Question04/README.md

	Enhancing the Blog to support viewers liking certain comments
	In this problem, you will be enhancing the blog project to support users liking certain comments and the like counts showing up the in the permalink page. 

	Start by downloading the code in final-problem4.zip and posts.json files from the Download Handout link. Load up the blog dataset posts.json. The user interface has already been implemented for you. It's not fancy. The /post URL shows the like counts next to each comment and displays a Like button that you can click on. That Like button POSTS to the /like URL on the blog, makes the necessary changes to the database state (you are implementing this), and then redirects the browser back to the permalink page. 

	This full round trip and redisplay of the entire web page is not how you would implement liking in a modern web app, but it makes it easier for us to reason about, so we will go with it. 

	Your job is to search the code for the string "XXX work here" and make the necessary changes. You can choose whatever schema you want, but you should note that the entry_template makes some assumptions about the how the like value will be encoded and if you go with a different convention than it assumes, you will need to make some adjustments. 

	MongoProc will fetch your blog, go to the first post's permalink page and attempt to increment the vote count. Remember that the blog needs to be running as well as MongoDB.

	Once you have tested your code using MongoProc, submit it (also using MongoProc).

	R:
		Java:
			postsCollection.updateOne(eq("permalink", permalink),
				new Document("$inc", new Document("comments.".concat(String.valueOf(ordinal).concat(".num_likes")), 1)));

		MongoDB:
			db.post.update({permalink:"CODE_PERMALINK"},{$inc:{"coments.NUMERO_ARRAY.num_likes":1}});

Question 05

	Suppose your have a collection fubar with the following indexes created:

	[
		{
			"v" : 1,
			"key" : {
				"_id" : 1
			},
			"ns" : "test.fubar",
			"name" : "_id_"
		},
		{
			"v" : 1,
			"key" : {
				"a" : 1,
				"b" : 1
			},
			"ns" : "test.fubar",
			"name" : "a_1_b_1"
		},
		{
			"v" : 1,
			"key" : {
				"a" : 1,
				"c" : 1
			},
			"ns" : "test.fubar",
			"name" : "a_1_c_1"
		},
		{
			"v" : 1,
			"key" : {
				"c" : 1
			},
			"ns" : "test.fubar",
			"name" : "c_1"
		},
		{
			"v" : 1,
			"key" : {
				"a" : 1,
				"b" : 1,
				"c" : -1
			},
			"ns" : "test.fubar",
			"name" : "a_1_b_1_c_-1"
		}
	]
	
	Now suppose you want to run the following query against the collection.

	db.fubar.find({'a':{'$lt':10000}, 'b':{'$gt': 5000}}, {'a':1, 'c':1}).sort({'c':-1})
	Which of the following indexes could be used by MongoDB to assist in answering the query? Check all that apply.

	R:

		Teste de mesa:

			db.question5.insert({a:1,b:2,c:3});
			db.question5.createIndex(a:1,b:1);
			db.question5.createIndex(a:1,c:1);
			db.question5.createIndex(c:1);
			db.question5.createIndex(a:1,b:1,c:-1);
			db.question5.find({'a':{'$lt':10000}, 'b':{'$gt': 5000}}, {'a':1, 'c':1}).sort({'c':-1})


			_id_
		√	a_1_b_1
		√	a_1_c_1
		√	c_1
		√	a_1_b_1_c_-1

Question 06

	Suppose you have a collection of students of the following form:
	{
		"_id" : ObjectId("50c598f582094fb5f92efb96"),
		"first_name" : "John",
		"last_name" : "Doe",
		"date_of_admission" : ISODate("2010-02-21T05:00:00Z"),
		"residence_hall" : "Fairweather",
		"has_car" : true,
		"student_id" : "2348023902",
		"current_classes" : [
			"His343",
			"Math234",
			"Phy123",
			"Art232"
		]
	}

	Now suppose that basic inserts into the collection, which only include the last name, first name and student_id, are too slow (we can't do enough of them per second from our program). What could potentially improve the speed of inserts. Check all that apply.

	R:
			Add an index on last_name, first_name if one does not already exist.
		√	Remove all indexes from the collection, leaving only the index on _id in place
			Provide a hint to MongoDB that it should not use an index for the inserts
		v	Set w=0, j=0 on writes
			Build a replica set and insert data into the secondary nodes to free up the primary nodes.

Question 07

	You have been tasked to cleanup a photo-sharing database. The database consists of two collections, albums, and images. Every image is supposed to be in an album, but there are orphan images that appear in no album. Here are some example documents (not from the collections you will be downloading). 

	> db.albums.findOne()
	{
		"_id" : 67
		"images" : [
			4745,
			7651,
			15247,
			17517,
			17853,
			20529,
			22640,
			27299,
			27997,
			32930,
			35591,
			48969,
			52901,
			57320,
			96342,
			99705
		]
	}

	> db.images.findOne()
	{
		"_id" : 99705,
		"height" : 480,
		"width" : 640,
		"tags" : [
			"cats",
			"sunrises",
			"kittens",
			"travel",
			"work"
		]
	}

	From the above, you can conclude that the image with _id = 99705 is in album 67. It is not an orphan. 

	Your task is to write a program to remove every image from the images collection that appears in no album. Or put another way, if an image does not appear in at least one album, it's an orphan and should be removed from the images collection. 

	Download final7.zip from Download Handout link and use mongoimport to import the collections in albums.json and images.json. 

	When you are done removing the orphan images from the collection, there should be 89,737 documents in the images collection. To prove you did it correctly, what are the total number of images with the tag 'sunrises" after the removal of orphans? As as a sanity check, there are 49,887 images that are tagged 'sunrises' before you remove the images. 

	Hint: you might consider creating an index or two or your program will take a long time to run. 

	R:
		Projeto <a href="Question07"></a>

			49,932
			47,678
			38,934
		√	44,787
			45,911

Question 08

	Supposed we executed the following Java code. How many animals will be inserted into the "animals" collection?

	import com.mongodb.MongoClient;
	import com.mongodb.client.MongoCollection;
	import com.mongodb.client.MongoDatabase;
	import org.bson.Document;

	public class InsertTest { 
	        public static void main(String[] args) {
	            MongoClient c =  new MongoClient();
	            MongoDatabase db = c.getDatabase("test");
	            MongoCollection<Document> animals = db.getCollection("animals");

	           Document animal = new Document("animal", "monkey");

	            animals.insertOne(animal);
	            animal.remove("animal");
	            animal.append("animal", "cat");
	            animals.insertOne(animal);
	            animal.remove("animal");
	            animal.append("animal", "lion");
	            animals.insertOne(animal);
	        }
	}

	R:
		Projeto <a href="Question07"></a>
			0
		√	1
			2
			3

Question 09

	Imagine an electronic medical record database designed to hold the medical records of every individual in the United States. Because each person has more than 16MB of medical history and records, it's not feasible to have a single document for every patient. Instead, there is a patient collection that contains basic information on each person and maps the person to a patient_id, and a record collection that contains one document for each test or procedure. One patient may have dozens or even hundreds of documents in the record collection.

	We need to decide on a shard key to shard the record collection. What's the best shard key for the record collection, provided that we are willing to run inefficient scatter-gather operations to do infrequent research and run studies on various diseases and cohorts? That is, think mostly about the operational aspects of such a system. And by operational, we mean, think about what the most common operations that this systems needs to perform day in and day out.

	R:
		√	patient_id
			_id
			Primary care physician (your principal doctor that handles everyday problems)
			Date and time when medical record was created
			Patient first name
			Patient last name

Question 10

	Understanding the output of explain

	We perform the following query on the enron dataset:

	var exp = db.messages.explain('executionStats')
	 
	exp.find( { 'headers.Date' : { '$gt' : new Date(2001,3,1) } }, { 'headers.From' : 1, '_id' : 0 } ).sort( { 'headers.From' : 1 } )
	and get the following explain output.
	{
	  "queryPlanner" : {
	    "plannerVersion" : 1,
	    "namespace" : "enron.messages",
	    "indexFilterSet" : false,
	    "parsedQuery" : {
	      "headers.Date" : {
	        "$gt" : ISODate("2001-04-01T05:00:00Z")
	      }
	    },
	    "winningPlan" : {
	      "stage" : "PROJECTION",
	      "transformBy" : {
	        "headers.From" : 1,
	        "_id" : 0
	      },
	      "inputStage" : {
	        "stage" : "FETCH",
	        "filter" : {
	          "headers.Date" : {
	            "$gt" : ISODate("2001-04-01T05:00:00Z")
	          }
	        },
	        "inputStage" : {
	          "stage" : "IXSCAN",
	          "keyPattern" : {
	            "headers.From" : 1
	          },
	          "indexName" : "headers.From_1",
	          "isMultiKey" : false,
	          "direction" : "forward",
	          "indexBounds" : {
	            "headers.From" : [
	              "[MinKey, MaxKey]"
	            ]
	          }
	        }
	      }
	    },
	    "rejectedPlans" : [ ]
	  },
	  "executionStats" : {
	    "executionSuccess" : true,
	    "nReturned" : 83057,
	    "executionTimeMillis" : 726,
	    "totalKeysExamined" : 120477,
	    "totalDocsExamined" : 120477,
	    "executionStages" : {
	      "stage" : "PROJECTION",
	      "nReturned" : 83057,
	      "executionTimeMillisEstimate" : 690,
	      "works" : 120478,
	      "advanced" : 83057,
	      "needTime" : 37420,
	      "needFetch" : 0,
	      "saveState" : 941,
	      "restoreState" : 941,
	      "isEOF" : 1,
	      "invalidates" : 0,
	      "transformBy" : {
	        "headers.From" : 1,
	        "_id" : 0
	      },
	      "inputStage" : {
	        "stage" : "FETCH",
	        "filter" : {
	          "headers.Date" : {
	            "$gt" : ISODate("2001-04-01T05:00:00Z")
	          }
	        },
	        "nReturned" : 83057,
	        "executionTimeMillisEstimate" : 350,
	        "works" : 120478,
	        "advanced" : 83057,
	        "needTime" : 37420,
	        "needFetch" : 0,
	        "saveState" : 941,
	        "restoreState" : 941,
	        "isEOF" : 1,
	        "invalidates" : 0,
	        "docsExamined" : 120477,
	        "alreadyHasObj" : 0,
	        "inputStage" : {
	          "stage" : "IXSCAN",
	          "nReturned" : 120477,
	          "executionTimeMillisEstimate" : 60,
	          "works" : 120477,
	          "advanced" : 120477,
	          "needTime" : 0,
	          "needFetch" : 0,
	          "saveState" : 941,
	          "restoreState" : 941,
	          "isEOF" : 1,
	          "invalidates" : 0,
	          "keyPattern" : {
	            "headers.From" : 1
	          },
	          "indexName" : "headers.From_1",
	          "isMultiKey" : false,
	          "direction" : "forward",
	          "indexBounds" : {
	            "headers.From" : [
	              "[MinKey, MaxKey]"
	            ]
	          },
	          "keysExamined" : 120477,
	          "dupsTested" : 0,
	          "dupsDropped" : 0,
	          "seenInvalidated" : 0,
	          "matchTested" : 0
	        }
	      }
	    }
	  },
	  "serverInfo" : {
	    "host" : "dpercy-mac-air.local",
	    "port" : 27017,
	    "version" : "3.0.1",
	    "gitVersion" : "534b5a3f9d10f00cd27737fbcd951032248b5952"
	  },
	  "ok" : 1
	}
	Check below all the statements that are true about the way MongoDB handled this query.

	R:
			The query used an index to figure out which documents match the find criteria.
		√	The query scanned every document in the collection.
		√	The query avoided sorting the documents because it was able to use an index's ordering.
			The query returned 120,477 documents.