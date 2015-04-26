<h1>Week 06 - APPLICATION ENGINEERING</h1>

<ul>
  <li><a href="" target="_blank">Introduction to Week 6</a></li>
</ul>
<hr/>

Lecture 02 - Write Concern

	Journal is a log of every single thing that the database processes.
	And when you do a write to the database, an update, it also writes to this journal.
	Journal in memory as well, when does the journal get written back to disk? Because that is when the data is really considered to be persistent.
	w=1 by default, it's 1 - mean wait for this server
	j=false by default, which stands for journal, represents whether or not we wait for this journal to be written to disk before we continue.

	<table>
		<thead>
			<tr>
				<th>writer <code>w</code></th>
				<th>journal <code>j</code></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>w=1</td>
				<td>j=false</td>
				<td>but we're not going to wait for the journal to sync. fast, small windows of vulnerability</td>
			</tr>
			<tr>
				<td>w=1</td>
				<td>j=true</td>
				<td>slow, window of vulnerability is removed</td>
			</tr>
				<tr>
				<td>w=0</td>
				<td>unacknowledged write</td>
				<td></td>
			</tr>
		</tbody>
	</table>
	
	Important: w=0 We don't recommend this as the way you use the database.

	QUIZ:
		Provided you assume that the disk is persistent, what are the w and j settings required to guarantee that an insert or update has been written all the way to disk.

		R:
			w=0, j=0
			w=1, j=1 √
			w=2, j=0
			w=1, j=0

<hr/>

Lecture 03 - Network Errors

	QUIZ:
		What are the reasons why an application may receive an error back even if the write was successful. Check all that apply.

		R:
			√ The network TCP connection between the application and the server was reset after the server received a write but before a response could be sent
			√ The MongoDB server terminates between receiving the write and responding to it.
			√ The network fails between the time of the write and the time the client receives a response to the write.
			  The write violates a primary key constraint on the collection.

<hr/>

Lecture 04 - Introduction to Replication

	QUIZ:
		What is the minimum original number of nodes needed to assure the election of a new Primary if a node goes down?

		R:
			1
			2
			3 √
			5

<hr/>

Lecture 05 - Replica Set Elections

	QUIZ:
		Which types of nodes can participate in elections of a new primary?

		R:
			√ Regular replica set members
			√ Hidden Members
			√ Arbiters
			  Lawyers

<hr/>

Lecture 06 - Write Consistency

- Replicação é assíncrona

	QUIZ:
		During the time when failover is occurring, can writes successfully complete?
		R:
				Yes
			√	No

<hr/>

Lecture 07 - Creating a Replica Set

	QUIZ:
		Which command, when issued from the mongo shell, will allow you to read from a secondary?

		R:
				db.isMaster()
				db.adminCommand({'readPreference':'Secondary"})
				rs.setStatus("Primary")
			√	rs.slaveOk()

<hr/>

Lecture 08 - Replica Set Internals

	Quando inserido algo no Mongo Primário, o dados são inseridos no oplog do mesmo, tal que os secundários ficam escutando e assim é replicado as informações. Quando o 
	primário é derrubado, algum outro mongo é escolhido como primário, e caso aquela primário volte a funcionar, este se tornará secundário.

	Secondaries are constantly reading the oplog of the primary. It's true that the oplog entries originally come from the primary, but secondaries can sync from another 
	secondary, as long as at least there is a chain of oplog syncs that lead back to the primary.

	QUIZ:
		Which of the following statements are true about replication. Check all that apply.

		R:
			   You can write to a primary or secondary node and the database will forward the write to the primary.
			√  Replication supports mixed-mode storage engines. For examples, a mmapv1 primary and wiredTiger secondary.
			√  A copy of the oplog is kept on both the primary and secondary servers.
			√  You can read from a primary or secondary, by default.
			   The oplog is implemented as a capped collection.

<hr/>

Lecture 09 - Failover and Rollback

	While it is true that a replica set will never rollback a write if it was performed with w=majority and that write successfully replicated to a majority of nodes, it 
	is possible that a write performed with w=majority gets rolled back. Here is the scenario: you do write with w=majority and a failover over occurs after the write has 
	committed to the primary but before replication completes. You will likely see an exception at the client. An election occurs and a new primary is elected. When the 
	original primary comes back up, it will rollback the committed write. However, from your application's standpoint, that write never completed, so that's ok.

	QUIZ:
		What happens if a node comes back up as a secondary after a period of being offline and the oplog has looped on the primary?

		R:
			√	The entire dataset will be copied from the primary
				A rollback will occur
				The new node stays offline (does not re-join the replica set)
				The new node begins to calculate Pi to a large number of decimal places

<hr/>

Lecture 10 - Connecting to a Replica Set from the Java Driver

	QUIZ:
		If you leave a replica set node out of the seedlist within the driver, what will happen?

		R:
				The missing node will not be used by the application.
			√	The missing node will be discovered as long as you list at least one valid node.
				This missing node will be used for reads, but not for writes.
				The missing node will be used for writes, but not for reads.

<hr/>

Lecture 11 - When bad Things Happen to Good Nodes

	In this case, the insert is idempotent because it contains a specific _id, and that field has a unique index. Inserts that don't involve a uniquely indexed field are not idempotent.

	QUIZ:
		If you use the MongoClient constructor that takes a seed list of replica set members, are you guaranteed to avoid application exceptions during a primary failover?

		R:
				Yes
			√	No

<hr/>

Lecture 12 - Write Concern Revisited

	Write concern (w) value can be set at client, database or collection level within PyMongo. When you call MongoClient, you get a connection to the driver, but behind
	the scenes, PyMongo connects to multiple nodes of the replica set. The w value can be set at the client level. Andrew says that the w concern can be set at the 
	connection level; he really means client level. It's also important to note that wtimeout is the amount of time that the database will wait for replication before 
	returning an error on the driver, but that even if the database returns an error due to wtimeout, the write will not be unwound at the primary and may complete at the 
	secondaries. Hence, writes that return errors to the client due to wtimeout may in fact succeed, but writes that return success, do in fact succeed. Finally, the video 
	shows the use of an insert command in PyMongo. That call is deprecated and it should have been insert_one.

	QUIZ:
		If you set w=1 and j=1, is it possible to wind up rolling back a committed write to the primary on failover?

		R:
			√	Yes
				No

<hr/>

Lecture 13 - Red Preferences

	The newest version of the pymongo API docs can be found here (http://api.mongodb.org/python/current/api/pymongo/?_ga=1.218119897.1358759779.1429485672).

	QUIZ:
		You can configure your applications via the drivers to read from secondary nodes within a replica set. What are the reasons that you might not want to do that? Check all that apply.

		R:

			√	If your write traffic is significantly greater than your read traffic, you may overwhelm the secondary, which must process all the writes as well as the 
				reads. Replication lag can result.
			√	You may not read what you previously wrote to MongoDB.
			√	If the secondary hardware has insufficient memory to keep the read working set in memory, directing reads to it will likely slow it down.
				Reading from a secondary prevents it from being promoted to primary.

<hr/>

Lecture 14 - Review Implications of Replication

	Quatro coisa que o desenvolvedor precisa saber sobre a replicação no mongo:
	- Seedlist: precisa entender que existe um seedlist (uma lista de servidores p/ replicação), que precisa enxergar um outro mongo para replicação
	- Write Concern: ter a idéia que é preciso esperar por algum numero de nós confirmar seus scripts através de parâmetro para garantir que o nó primário tenha escrito no 
	  disco. Tamém levar em conta o wtimeout (o tempo de espera para replicação dos dados no secundário)
	- Read Preferences: que o desenvolvedor poder configurar se poderá ler ou não em um nó secundário
	- Errors can happen: os erros podem ocorrer em qualquer momento, e isso deve se tratado, gerando as exceções e configurando da forma correta para que o secundário 
	  assuma sua responsabilidade sem precisar para a aplicação.

	QUIZ:
		If you set w=4 on a connection and there are only three nodes in the replica set, how long will you wait in PyMongo for a response from an insert if you don't set a timeout?

		R:
				Comes back immediately
				About a minute
				About 10 seconds
			√	More than five minutes

<hr/>

Lecture 15 - Introduction to Sharding

	QUIZ:
		If the shard key is not include in a find operation and there are 4 shards, each one a replica set with 3 nodes, how many nodes will see the find operation?

		R:
				1
				3
			√	4: The answer is 4. Since the shard key is not included in the find operation, mongos has to send the query to all 4 of the shards. Each shard has 3 
			       replica-set members, but only one member of each replica set (the primary, by default) is required to handle the find.
				12

<hr/>

Lecture 16 - Building a Sharded Environment

	QUIZ:
		If you want to build a production system with two shards, each one a replica set with three nodes, how may mongod processes must you start?

		R:
				2
				6
				7
			√	9

<hr/>

Lecture 17 - Implications of Sharding

	QUIZ:
		Suppose you wanted to shard the zip code collection after importing it. You want to shard on zip code. What index would be required to allow MongoDB to shard on zip code?

		R:
			√	An index on zip or a non-multi-key index that starts with zip.
				No index is required to use zip as the shard key.
				A unique index on the zip code.
				Any index that that includes the zip code.

<hr/>

Lecture 18 - Sharding + Replications

	QUIZ:
		Suppose you want to run multiple mongos routers for redundancy. What level of the stack will assure that you can failover to a different mongos from within your application?

		R:
				mongod
				mongos
			√	drivers
				sharding config servers

<hr/>

Lecture 19 - Choosing a Shard Key

	QUIZ:
		You are building a facebook competitor called footbook that will be a mobile social network of feet. You have decided that your primary data structure for posts to the wall will look like this:
			{
			    'username':'toeguy',
			    'posttime':    ISODate("2012-12-02T23:12:23    Z"),
			    "randomthought":"I am looking at my feet right now",
			    'visible_to':[
			        'friends',
			        'family',
			        'walkers'
			    ]
			}
		Thinking about the tradeoffs of shard key selection, select the true statements below.

		R:
			√	Choosing posttime as the shard key will cause hotspotting as time progresses.
			√	Choosing username as the shard key will distribute posts to the wall well across the shards.
			√	Choosing visible_to as a shard key is illegal.
				Choosing posttime as the shard key suffers from low cardinality.

<hr/>

<ul>
	<li>Homework 01</li>

	Which of the following statements are true about replication in MongoDB? Check all that apply.

	R:
		√	The minimum sensible number of voting nodes to a replica set is three.
			MongoDB replication is synchronous.
			By default, using the new MongoClient connection class, w=1 and j=1.
		√	The oplog utilizes a capped collection.

<hr/>
	
	<li>Homework 02</li>

	Let's suppose you have a five member replica set and want to assure that writes are committed to the journal and are acknowledged by at least 3 nodes before you proceed forward. What would be the appropriate settings for w and j?

	R:
			w=1, j=1
		√	w="majority", j=1
			w=3, j=0
			w=5, j=1
			w=1, j=3


<hr/>

	<li>Homework 03</li>

	Which of the following statements are true about choosing and using a shard key?

	R:
		√	Any update that does not contain the shard key will be sent to all shards.
			The shard key must be unique
			You can change the shard key on a collection if you desire.
		√	There must be a index on the collection that starts with the shard key.
		√	MongoDB can not enforce unique indexes on a sharded collection other than the shard key itself, or indexes prefixed by the shard key.

<hr/>

	<li>Homework 04</li>

	You have a sharded system with three shards and have sharded the collections "students" in the "school" database across those shards. The output of sh.status() when connected to mongos looks like this:

	<code>mongos> sh.status()
	--- Sharding Status --- 
	sharding version: {
		"_id" : 1,
		"minCompatibleVersion" : 5,
		"currentVersion" : 6,
		"clusterId" : ObjectId("5531512ac723271f602db407")
	}
	shards:
			{  "_id" : "s0",  "host" : "s0/localhost:37017,localhost:37018,localhost:37019" }
			{  "_id" : "s1",  "host" : "s1/localhost:47017,localhost:47018,localhost:47019" }
			{  "_id" : "s2",  "host" : "s2/localhost:57017,localhost:57018,localhost:57019" }
	balancer:
			Currently enabled:  yes
			Currently running:  yes
			Balancer lock taken at Fri Apr 17 2015 14:32:02 GMT-0400 (EDT) by education-iMac-2.local:27017:1429295401:16807:Balancer:1622650073
			Collections with active migrations: 
					school.students started at Fri Apr 17 2015 14:32:03 GMT-0400 (EDT)
			Failed balancer rounds in last 5 attempts:  0
			Migration Results for the last 24 hours: 
					2 : Success
					1 : Failed with error 'migration already in progress', from s0 to s1
	databases:
			{  "_id" : "admin",  "partitioned" : false,  "primary" : "config" }
			{  "_id" : "school",  "partitioned" : true,  "primary" : "s0" }
					school.students
							shard key: { "student_id" : 1 }
							chunks:
									s0	1
									s1	3
									s2	1
			{ "student_id" : { "$minKey" : 1 } } -->> { "student_id" : 0 } on : s2 Timestamp(3, 0) 
			{ "student_id" : 0 } -->> { "student_id" : 2 } on : s0 Timestamp(3, 1) 
			{ "student_id" : 2 } -->> { "student_id" : 3497 } on : s1 Timestamp(3, 2) 
			{ "student_id" : 3497 } -->> { "student_id" : 7778 } on : s1 Timestamp(3, 3) 
			{ "student_id" : 7778 } -->> { "student_id" : { "$maxKey" : 1 } } on : s1 Timestamp(3, 4)</code>

	If you ran the query
		<code>use school
		db.students.find({'student_id':2000})</code>
	Which shards would be involved in answering the query?

	R:
			s0, s1, and s2
			s0
		√	s1
			s2

<hr/>
	<li><a href="#">Homework 05</a></li>
</ul>

<hr/>