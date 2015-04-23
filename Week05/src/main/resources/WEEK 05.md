WEEK 05

Lecture 02 - Simple Aggregation Example

			PRODUCTS

NAME | CATEGORY | MANUFACTURER | PRICE
--------------------------------------
ipad | 	tablet	|	apple	   | 499
nexus| cellphone|	samsung	   | 350

SQL:

	SELECT manufacturer, COUNT(*) FROM PRODUCTS GROUP BY manufecturer;

MONGODB:

	db.products.aggregate([
		{$group:
			{
				_id:"$manufacturer",
				num_products:{$sum:1}
			}
		}
	]);

-----------------------------------------------------------------------------------

Lecture 03 - The Aggregation Pipeline

Stages in the aggregation pipeline:

Match
Group
Skip
Limit
Sort
Project
Unwind

-----------------------------------------------------------------------------------

Lecture 04 - Simple Example Expanded

QUIZ:  If you have the following collection of stuff:

	> db.stuff.find()
		{ "_id" : ObjectId("50b26f9d80a78af03b5163c8"), "a" : 1, "b" : 1, "c" : 1 }
		{ "_id" : ObjectId("50b26fb480a78af03b5163c9"), "a" : 2, "b" : 2, "c" : 1 }
		{ "_id" : ObjectId("50b26fbf80a78af03b5163ca"), "a" : 3, "b" : 3, "c" : 1 }
		{ "_id" : ObjectId("50b26fcd80a78af03b5163cb"), "a" : 3, "b" : 3, "c" : 2 }
		{ "_id" : ObjectId("50b26fd380a78af03b5163cc"), "a" : 3, "b" : 5, "c" : 3 }

and you perform the following aggregation:
	> db.stuff.aggregate([{$group:{_id:'$c'}}]);

How many documents will be in the result set from aggregate?

1
2
3 √ - pq os 3 primeiros registro (1) estaram no mesmo grupo
4
5

-----------------------------------------------------------------------------------

Lecture 05 - Compound Groupings

SQL:
	SELECT manufacturer, category, COUNT(*) FROM PRODUCTS GROUP BY manufacturer, category;

MONGODB:
	db.products.aggregate([
		{$group:
			{
				_id:{
					"manufacturer":"$manufacturer", 
					"category":"$category"
				num_products:{$sum:1}
			}
		}
	]);

QUIZ: Given the following collection:
	> db.stuff.find()
		{ "_id" : ObjectId("50b26f9d80a78af03b5163c8"), "a" : 1, "b" : 1, "c" : 1 }
		{ "_id" : ObjectId("50b26fb480a78af03b5163c9"), "a" : 2, "b" : 2, "c" : 1 }
		{ "_id" : ObjectId("50b26fbf80a78af03b5163ca"), "a" : 3, "b" : 3, "c" : 1 }
		{ "_id" : ObjectId("50b26fcd80a78af03b5163cb"), "a" : 3, "b" : 3, "c" : 2 }
		{ "_id" : ObjectId("50b26fd380a78af03b5163cc"), "a" : 3, "b" : 5, "c" : 3 }
		{ "_id" : ObjectId("50b27f7080a78af03b5163cd"), "a" : 3, "b" : 3, "c" : 2 }

And the following aggregation query:
	> db.stuff.aggregate([{$group:
		{_id:
			{'moe':'$a', 
				'larry':'$b',
				'curly':'$c'
			}
		}
	}])

How many documents will be in the result set?

2
3
4
5 √ - O quinto registro será agrupado na qtdade de 2
6

-----------------------------------------------------------------------------------

Lecture 06 - Using a document for _id

ID pode ser composto, UNIQUE - db.collection.insert({_id:{name:"Laisson",class:"m101"}, hometown:"SC"});
error se add outro registro como mesmo ID

-----------------------------------------------------------------------------------

Lecture 07 - Aggregation Expressions

$group
	- $sum: soma dos valores de uma chave me todos os documentos
	- $avg: média dos valores de uma chave me todos os documentos
	- $min: encontrar o valor mínimo de uma determinada chave
	- $max: encontrar o valor máximo de uma determinada chave
	 		|$push: adiciona valor ao array
	--array-|
	 		|$addToSet: adiciona se não existir
$sort
	- $first
	- $last

-----------------------------------------------------------------------------------

Lecture 08 - $sum

	SQL:
		SELECT manufacturer, SUM(price) FROM PRODUCTS GROUP BY manufacturer;

	MONGODB: 
		db.products.aggregate([
			{ $group:{
					_id: {
							"maker":"$manufacturer"
						},
					sum_prices:{
							$sum:"$price"
						}
				}
			}
		]);

	QUIZ: 
		Write an aggregation query to sum up the population (pop) by state and put the result in a field called population. Don't use a compound _id key (you don't need one and the quiz checker is not expecting one). The collection name is zips. so something along the lines of db.zips.aggregate...

		R: db.zips.aggregate([{$group:{'_id':'$state', population:{'$sum':'$pop'}}}]);

-----------------------------------------------------------------------------------

Lecture 09 - $avg

	SQL:
		SELECT category, AVG(price) FROM PRODUCTS GROUP BY category;

	MONGODB:
		db.products.aggregate([
			{ $group:{
					_id: {
							"category":"$category"
						},
					avg_price:{
							$avg:"$price"
						}
				}
			}
		]);

	QUIZ:
		Given population data by zip code (postal code), write an aggregation expression to calculate the average population of a zip code (postal code) by state. This dataset only contains four states, and only 50 zip codes per state, because some browsers have trouble working with large data sets.

		R: db.zips.aggregate([{$group:{'_id':'$state', population:{'$avg':'$pop'}}}]);

-----------------------------------------------------------------------------------

Lecture 10 - $addToSet

	MONGO: 
		db.products.aggregate([
			{ $group:{
					_id: {
							"marker":"$manufacturer"
						},
					categories:{
							$addToSet:"$category"
						}
				}
			}
		])

	QUIZ: 
		Again the collection will be called zips. You can deduce what your result column names should be from the above output. (ignore the issue that a city may have the same name in two different states and is in fact two different cities in that case - for eg Springfield, MO and Springfield, MA)

		R: db.zips.aggregate([{$group:{"_id":"$city", "postal_codes":{"$addToSet":"$_id"}}}]);

-----------------------------------------------------------------------------------

Lecture 11 - $push

	MONGO:
		 db.products.aggregate([
			{ $group:{
					_id: {
							"marker":"$manufacturer"
						},
					categories:{
							$push:"$category"
						}
				}
			}
		]);

	QUIZ: 
		Given the zipcode dataset (explained more fully in the using $sum quiz) that has documents that look like this:
		> db.zips.findOne()
			{
				"city" : "ACMAR",
				"loc" : [
					-86.51557,
					33.584132
				],
				"pop" : 6055,
				"state" : "AL",
				"_id" : "35004"
			}

		would you expect the following two queries to produce the same result or different results?
			db.zips.aggregate([{"$group":{"_id":"$city", "postal_codes":{"$push":"$_id"}}}])
			db.zips.aggregate([{"$group":{"_id":"$city", "postal_codes":{"$addToSet":"$_id"}}}])

		- Same result √ - porque o _id é único, não vai repetir
		- Different Result

-----------------------------------------------------------------------------------

Lecture 12 - $max and $min

	MONGO:
		 db.products.aggregate([
			{ $group:{
					_id: {
							"marker":"$manufacturer"
						},
					maxprice:{
							$max:"$price"
						}
				}
			}
		]);

	SQL:
		SELECT manufacturer, MAX(price) FROM PRODUCTS GROUP BY manufacturer;

	MONGO:
		 db.products.aggregate([
			{ $group:{
					_id: {
							"marker":"$manufacturer"
						},
					maxprice:{
							$min:"$price"
						}
				}
			}
		]);

	SQL:
		SELECT manufacturer, MIN(price) FROM PRODUCTS GROUP BY manufacturer;

-----------------------------------------------------------------------------------

Lecture 13 - $group stages

	Single group

		MONGO:
			db.grades.aggregate([  
			    {  
			        '$group':{  
			            _id:{  
			                class_id:"$class_id",
			                student_id:"$student_id"
			            },
			            'average':{  
			                "$avg":"$score"
			            }
			        }
			    }
			])

	Double group

		MONGO:
			db.grades.aggregate([  
			    {  
			        '$group':{  
			            _id:{  
			                class_id:"$class_id",
			                student_id:"$student_id"
			            },
			            'average':{  
			                "$avg":"$score"
			            }
			        }
			    },
			    {  
			        '$group':{  
			            _id:"$_id.class_id",
			            'average':{  
			                "$avg":"$average"
			            }
			        }
			    }
			]);

	QUIZ:
		Given the following collection:
		> db.fun.find()
			{ "_id" : 0, "a" : 0, "b" : 0, "c" : 21 }
			{ "_id" : 1, "a" : 0, "b" : 0, "c" : 54 }
			{ "_id" : 2, "a" : 0, "b" : 1, "c" : 52 }
			{ "_id" : 3, "a" : 0, "b" : 1, "c" : 17 }
			{ "_id" : 4, "a" : 1, "b" : 0, "c" : 22 }
			{ "_id" : 5, "a" : 1, "b" : 0, "c" : 5 }
			{ "_id" : 6, "a" : 1, "b" : 1, "c" : 87 }
			{ "_id" : 7, "a" : 1, "b" : 1, "c" : 97 }

		And the following aggregation query
		db.fun.aggregate([{$group:{_id:{a:"$a", b:"$b"}, c:{$max:"$c"}}}, {$group:{_id:"$_id.a", c:{$min:"$c"}}}]);
		What values are returned?

		R:
			17 and 54
			97 and 21
			54 and 5
			52 and 22 - √: no primeiro agrupamento vai pegar o maior de 'C' (0,0=54),(0,1=52),(1,0=22),(1,1=97)
						   no segundo agrupamento vai pegar o menor de cada grupo (0=52,54) e (1=22,97)
						   pegando o menor de 0=52 e 1=22
						   { "_id" : 0, "c" : 52 }
						   { "_id" : 1, "c" : 22 }

-----------------------------------------------------------------------------------

Lecture 14 - $project

- remove keys
- add new keys
- restore keys
- use some simple functions on keys
	- $toUpper
	- $toLower
	- $add
	- $multiply

	MONGO:
		db.products.aggregate([  
		    {  
		        $project:{  
		            _id:0,
		            'maker':{  
		                $toLower:"$manufacturer"
		            },
		            'details':{  
		                'category':"$category",
		                'price':{  
		                    "$multiply":[  
		                        "$price",
		                        10
		                    ]
		                }
		            },
		            'item':'$name'
		        }
		    }
		]);

	QUIZ:
		Write an aggregation query with a single projection stage that will transform the documents in the zips collection from this:
			{
				"city" : "ACMAR",
				"loc" : [
					-86.51557,
					33.584132
				],
				"pop" : 6055,
				"state" : "AL",
				"_id" : "35004"
			}

		to documents in the result set that look like this:
			{
				"city" : "acmar",
				"pop" : 6055,
				"state" : "AL",
				"zip" : "35004"
			}
		So that the checker works properly, please specify what you want to do with the _id key as the first item. The other items should be ordered as above. As before, assume the collection is called zips. You are running only the projection part of the pipeline for this quiz.

		R:
			db.zips.aggregate([  
			    {  
			        $project:{  
			            _id:0,
			            'city':{  
			                $toLower:'$city'
			            },
			            pop:1,
			            state:1,
			            'zip':'$_id'
			        }
			    }
			]);

-----------------------------------------------------------------------------------

Lecture 15 - $match

	MONGO:
		db.zips.aggregate([  
		    {  
		        $match:{  
		            state:"NY"
		        }
		    },
		    {  
		        $group:{  
		            _id:"$city",
		            population:{  
		                $sum:"$pop"
		            },
		            zip_codes:{  
		                $addToSet:"$_id"
		            }
		        }
		    },
		    {  
		        $project:{  
		            _id:0,
		            city:"$_id",
		            population:1,
		            zip_codes:1
		        }
		    }
		]);

	QUIZ:
		Again, thinking about the zipcode collection, write an aggregation query with a single match phase that filters for zipcodes with greater than 100,000 people.

		R:
			db.zips.aggregate([  
			    {  
			        $match:{  
			            pop:{  
			                $gt:100000
			            }
			        }
			    }
			]);

-----------------------------------------------------------------------------------

Lecture 16 - $sort

- disk and memory based: 100mb
- before or after the grouping stage
	
	MONGO:
		db.zips.aggregate([
		    {
		        $match:{
		            state:"NY"
		        }
		    },
		    {
		        $group:{
		            _id:"$city",
		            population:{
		                $sum:"$pop"
		            },

		        }
		    },
		    {
		        $project:{
		            _id:0,
		            city:"$_id",
		            population:1,

		        }
		    },
		    {
		        $sort:{
		            population:-1
		        }
		    }
		]);

	QUIZ:
		Again, considering the zipcode collection, which has documents that look like this,
		{
			"city" : "ACMAR",
			"loc" : [
				-86.51557,
				33.584132
			],
			"pop" : 6055,
			"state" : "AL",
			"_id" : "35004"
		}

		Write an aggregation query with just a sort stage to sort by (state, city), both ascending. Assume the collection is called zips.

		R:
			db.zips.aggregate([
			    {
			        $sort:{
			            state:1,
			            city:1
			        }
			    }
			]);

-----------------------------------------------------------------------------------

Lecture 17 - $limit and $skip

first $skip and second $limit, but after of $sort

	MONGO:
		db.zips.aggregate([
		    {
		        $match:{
		            state:"NY"
		        }
		    },
		    {
		        $group:{
		            _id:"$city",
		            population:{
		                $sum:"$pop"
		            },

		        }
		    },
		    {
		        $project:{
		            _id:0,
		            city:"$_id",
		            population:1,

		        }
		    },
		    {
		        $sort:{
		            population:-1
		        }
		    },
		    {
		        $skip:10
		    },
		    {
		        $limit:5
		    }
		]);

	QUIZ:
		Suppose you change the order of skip and limit in the query shown in the lesson, to look like this:
			
			db.zips.aggregate([
			    {
			        $match:{
			            state:"NY"
			        }
			    },
			    {
			        $group:{
			            _id:"$city",
			            population:{
			                $sum:"$pop"
			            },

			        }
			    },
			    {
			        $project:{
			            _id:0,
			            city:"$_id",
			            population:1,

			        }
			    },
			    {
			        $sort:{
			            population:-1
			        }
			    },
			    {
			        $limit:5
			    },
			    {
			        $skip:10
			    }
			]);

			How many documents do you think will be in the result set?

				10
				5
				0 √: pq trouxe somente 5 registro e depois pulou 10 registro, chegando ao 0
				100

-----------------------------------------------------------------------------------

Lecture 18 - $first and $last

	MONGO:
		db.zips.aggregate([
		    /* get the population of every city in every state */    {
		        $group:{
		            _id:{
		                state:"$state",
		                city:"$city"
		            },
		            population:{
		                $sum:"$pop"
		            },

		        }
		    },
		    /* sort by state,
		    population */    {
		        $sort:{
		            "_id.state":1,
		            "population":-1
		        }
		    },
		    /* group by state,
		    get the first item in each group */    {
		        $group:{
		            _id:"$_id.state",
		            city:{
		                $first:"$_id.city"
		            },
		            population:{
		                $first:"$population"
		            }
		        }
		    },
		    /* now sort by state again */    {
		        $sort:{
		            "_id":1
		        }
		    }
		]);

	QUIZ:
		Given the following collection:
			> db.fun.find()
			{ "_id" : 0, "a" : 0, "b" : 0, "c" : 21 }
			{ "_id" : 1, "a" : 0, "b" : 0, "c" : 54 }
			{ "_id" : 2, "a" : 0, "b" : 1, "c" : 52 }
			{ "_id" : 3, "a" : 0, "b" : 1, "c" : 17 }
			{ "_id" : 4, "a" : 1, "b" : 0, "c" : 22 }
			{ "_id" : 5, "a" : 1, "b" : 0, "c" : 5 }
			{ "_id" : 6, "a" : 1, "b" : 1, "c" : 87 }
			{ "_id" : 7, "a" : 1, "b" : 1, "c" : 97 }
		
		What would be the value of c in the result from this aggregation query
		
		db.fun.aggregate([
		    {
		        $match:{
		            a:0
		        }
		    },
		    {
		        $sort:{
		            c:-1
		        }
		    },
		    {
		        $group:{
		            _id:"$a",
		            c:{
		                $first:"$c"
		            }
		        }
		    }
		]);

		R:
			21
			54 √
			97
			5

-----------------------------------------------------------------------------------

Lecture 19 and 20 - $unwind

- Separa em novos documentos para cada item de um array

{a:1, b:2, c:['aaa','bbb','ccc']}

$unwid:"$c":
	{a:1, b:2, c:'aaa'}
	{a:1, b:2, c:'bbb'}
	{a:1, b:2, c:'ccc'}

----
Other Example:
	db.items.insert({_id:'nail', 'attributes':['hard', 'shiny', 'pointy', 'thin']});
	db.items.insert({_id:'hammer', 'attributes':['heavy', 'black', 'blunt']});
	db.items.insert({_id:'screwdriver', 'attributes':['long', 'black', 'flat']});
	db.items.insert({_id:'rock', 'attributes':['heavy', 'rough', 'roundish']});
	
	db.items.aggregate([{$unwind:"$attributes"}]);
----

	MONGO:
		db.posts.aggregate([
		    /* unwind by tags */    {
		        "$unwind":"$tags"
		    },
		    /* now group by tags,
		    counting each tag */    {
		        "$group":{
		            "_id":"$tags",
		            "count":{
		                $sum:1
		            }
		        }
		    },
		    /* sort by popularity */    {
		        "$sort":{
		            "count":-1
		        }
		    },
		    /* show me the top 10 */    {
		        "$limit":10
		    },
		    /* change the name of _id to be tag */    {
		        "$project":{
		            _id:0,
		            'tag':'$_id',
		            'count':1
		        }
		    }
		]);

		db.posts.aggregate([{"$unwind":"$tags"},{"$group":{"_id":"$tags","count":{$sum:1}}},{"$sort":{"count":-1}},{"$limit":10},{"$project":{_id:0,'tag':'$_id','count':1}}]);

	QUIZ:
		Suppose you have the following collection:
			db.people.find()
			{ "_id" : "Barack Obama", "likes" : [ "social justice", "health care", "taxes" ] }
			{ "_id" : "Mitt Romney", "likes" : [ "a balanced budget", "corporations", "binders full of women" ] }
		
		And you unwind the "likes" array of each document. How many documents will you wind up with?

		R:
			2
			4
			6 √
			9

		Which grouping operator will enable to you to reverse the effects of an unwind?

		R:
			$sum
			$addToSet
			$push √
			$first

-----------------------------------------------------------------------------------

Lecture 21 - Double $unwind

db.inventory.insert({'name':"Polo Shirt", 'sizes':["Small", "Medium", "Large"], 'colors':['navy', 'white', 'orange', 'red']})
db.inventory.insert({'name':"T-Shirt", 'sizes':["Small", "Medium", "Large", "X-Large"], 'colors':['navy', "black",  'orange', 'red']})
db.inventory.insert({'name':"Chino Pants", 'sizes':["32x32", "31x30", "36x32"], 'colors':['navy', 'white', 'orange', 'violet']})

MONGO:
	db.inventory.aggregate([
	    {$unwind: "$sizes"},
	    {$unwind: "$colors"},
	    {$group: 
	     {
		'_id': {'size':'$sizes', 'color':'$colors'},
		'count' : {'$sum':1}
	     }
	    }
	]);

	Reversing double unwind 01:
		db.inventory.aggregate([
		    {$unwind: "$sizes"},
		    {$unwind: "$colors"},
		    {$group: 
		     {
			'_id': "$name",
			 'sizes': {$addToSet: "$sizes"},
			 'colors': {$addToSet: "$colors"},
		     }
		    }
		]);

	Reversing double unwind 02:
		db.inventory.aggregate([
		    {$unwind: "$sizes"},
		    {$unwind: "$colors"},
		    /* create the color array */
		    {$group: 
		     {
			'_id': {name:"$name",size:"$sizes"},
			 'colors': {$push: "$colors"},
		     }
		    },
		    /* create the size array */
		    {$group: 
		     {
			'_id': {'name':"$_id.name",
				'colors' : "$colors"},
			 'sizes': {$push: "$_id.size"}
		     }
		    },
		    /* reshape for beauty */
		    {$project: 
		     {
			 _id:0,
			 "name":"$_id.name",
			 "sizes":1,
			 "colors": "$_id.colors"
		     }
		    }
		]);

	QUIZ:
		Can you reverse the effects of a double unwind (2 unwinds in a row) in our inventory collection (shown in the lesson ) with the $push operator?
		R:
			Yes √
			No

-----------------------------------------------------------------------------------

Lecture 22 - Mapping between SQL and Aggregation 

SQL Terms, Functions, and Concepts	MongoDB Aggregation Operators

WHERE		$match
GROUP BY	$group
HAVING		$match
SELECT		$project
ORDER BY	$sort
LIMIT		$limit
SUM()		$sum
COUNT()		$sum
join		No direct corresponding operator; 
			however, the $unwind operator allows for somewhat similar functionality, 
			but with fields embedded within the document.

Examples: http://docs.mongodb.org/manual/reference/sql-aggregation-comparison/#examples

-----------------------------------------------------------------------------------

Lecture 24 - Limitations of the Aggregation Framework 

- 100mb limit for pipeline stages
- 16mb limit by default in python
- Sharded: group by, sort, ou quaer coisa que olhe todos os resultados serão trazido para o primeiro fragmento
	map/reduce don't recommend

-----------------------------------------------------------------------------------

HOMEWORK 5.1

Finding the most frequent author of comments on your blog
In this assignment you will use the aggregation framework to find the most frequent author of comments on your blog. 

R:
	Kayce Kenyon
	Devorah Smartt
	Gisela Levin √
	Brittny Warwick
	Tamika Schildgen
	Mariette Batdorf

	db.posts.aggregate([{"$unwind":"$comments"},{"$group":{"_id":"$comments.author", "count":{"$sum":1}}},{"$sort":{"count":-1}},{"$limit":5}]);

-----------------------------------------------------------------------------------

HOMEWORK 5.2

Please calculate the average population of cities in California (abbreviation CA) and New York (NY) (taken together) with populations over 25,000. 

For this problem, assume that a city name that appears in more than one state represents two separate cities. 

Please round the answer to a whole number. 
Hint: The answer for CT and NJ (using this data set) is 38177. 

Please note:
Different states might have the same city name.
A city might have multiple zip codes.

R:
	44805 √
	55921
	67935
	71819
	82426
	93777

-----------------------------------------------------------------------------------

HOMEWORK 5.3

Who's the easiest grader on campus?
A set of grades are loaded into the grades collection. 

The documents look like this:
{
	"_id" : ObjectId("50b59cd75bed76f46522c392"),
	"student_id" : 10,
	"class_id" : 5,
	"scores" : [
		{
			"type" : "exam",
			"score" : 69.17634380939022
		},
		{
			"type" : "quiz",
			"score" : 61.20182926719762
		},
		{
			"type" : "homework",
			"score" : 73.3293624199466
		},
		{
			"type" : "homework",
			"score" : 15.206314042622903
		},
		{
			"type" : "homework",
			"score" : 36.75297723087603
		},
		{
			"type" : "homework",
			"score" : 64.42913107330241
		}
	]
}
There are documents for each student (student_id) across a variety of classes (class_id). Note that not all students in the same class have the same exact number of assessments. Some students have three homework assignments, etc. 

Your task is to calculate the class with the best average student performance. This involves calculating an average for each student in each class of all non-quiz assessments and then averaging those numbers to get a class average. To be clear, each student's average includes only exams and homework grades. Don't include their quiz scores in the calculation. 

What is the class_id which has the highest average student performance? 

Hint/Strategy: You need to group twice to solve this problem. You must figure out the GPA that each student has achieved in a class and then average those numbers to get a class average. After that, you just need to sort. The class with the lowest average is the class with class_id=2. Those students achieved a class average of 37.6 

R:
	8
	9
	1 √
	5
	7
	0
	6

-----------------------------------------------------------------------------------

HOMEWORK 5.4

Removing Rural Residents

In this problem you will calculate the number of people who live in a zip code in the US where the city starts with a digit. We will take that to mean they don't really live in a city. Once again, you will be using the zip code collection, which you will find in the 'handouts' link in this page. Import it into your mongod using the following command from the command line:

> mongoimport -d test -c zips --drop zips.json
If you imported it correctly, you can go to the test database in the mongo shell and conform that

> db.zips.count()
yields 29,467 documents.

The project operator can extract the first digit from any field. For example, to extract the first digit from the city field, you could write this query:

db.zips.aggregate([
    {$project: 
     {
	first_char: {$substr : ["$city",0,1]},
     }	 
   }
])
Using the aggregation framework, calculate the sum total of people who are living in a zip code where the city starts with a digit. Choose the answer below.

You will need to probably change your projection to send more info through than just that first character. Also, you will need a filtering step to get rid of all documents where the city does not start with a digital (0-9).

Note: When you mongoimport the data, you will probably see a few duplicate key errors; this is to be expected, and will not prevent the mongoimport from working. There is also an issue with MongoDB 3.0.2 where it claims that 0 documents were mongoimported, when in fact there were 29,467 documents imported. You can verify this for yourself by going into the shell and counting the documents in the "test.zips" collection.

R:

	298015 √
	345232
	245987
	312893
	158249
	543282


-----------------------------------------------------------------------------------