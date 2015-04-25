<h1>Homeworkds Week 06 - APPLICATION ENGINEERING</h1>

<ul>
  <li><a href="" target="_blank">Introduction to Week 6</a></li>
</ul>
<hr/>
<ul>
  <li><a href="#">Homework 01</a></li>
  <li><a href="#">Homework 02</a></li>
  <li><a href="#">Homework 03</a></li>
  <li><a href="#">Homework 04</a></li>
  <li><a href="#">Homework 05</a></li>
</ul>

Lecture 02 - Write Concern

	QUIZ:
		Provided you assume that the disk is persistent, what are the w and j settings required to guarantee that an insert or update has been written all the way to disk.

		R:
			w=0, j=0
			w=1, j=1 √
			w=2, j=0
			w=1, j=0
	
Lecture 03 - Network Errors

	QUIZ:
		What are the reasons why an application may receive an error back even if the write was successful. Check all that apply.

		R:
			√ The network TCP connection between the application and the server was reset after the server received a write but before a response could be sent
			√ The MongoDB server terminates between receiving the write and responding to it.
			√ The network fails between the time of the write and the time the client receives a response to the write.
			  The write violates a primary key constraint on the collection.

Lecture 04 - Introduction to Replication

	QUIZ:
		What is the minimum original number of nodes needed to assure the election of a new Primary if a node goes down?

		R:
			1
			2
			3 √
			5

Lecture 05 - Replica Set Elections

	QUIZ:
		Which types of nodes can participate in elections of a new primary?

		R:
			√ Regular replica set members
			√ Hidden Members
			√ Arbiters
			  Lawyers

Lecture 06 - Write Consistency

- Replicação é assíncrona

	QUIZ:
		During the time when failover is occurring, can writes successfully complete?
		R:
				Yes
			√	No

Lecture 07 - Creating a Replica Set

	QUIZ:
		Which command, when issued from the mongo shell, will allow you to read from a secondary?

		R:
				db.isMaster()
				db.adminCommand({'readPreference':'Secondary"})
				rs.setStatus("Primary")
			√	rs.slaveOk()

Lecture 08 - Replica Set Internals

	Quando inserido algo no Mongo Primário, o dados são inseridos no oplog do mesmo, tal que os secundários ficam escutando e assim é replicado as informações. Quando o primário é derrubado, algum outro mongo é escolhido como primário, e caso aquela primário volte a funcionar, este se tornará secundário.

	Secondaries are constantly reading the oplog of the primary. It's true that the oplog entries originally come from the primary, but secondaries can sync from another secondary, as long as at least there is a chain of oplog syncs that lead back to the primary.

	QUIZ:
		Which of the following statements are true about replication. Check all that apply.

		R:
			   You can write to a primary or secondary node and the database will forward the write to the primary.
			√  Replication supports mixed-mode storage engines. For examples, a mmapv1 primary and wiredTiger secondary.
			√  A copy of the oplog is kept on both the primary and secondary servers.
			√  You can read from a primary or secondary, by default.
			   The oplog is implemented as a capped collection.

Lecture 09 - Failover and Rollback

	While it is true that a replica set will never rollback a write if it was performed with w=majority and that write successfully replicated to a majority of nodes, it is possible that a write performed with w=majority gets rolled back. Here is the scenario: you do write with w=majority and a failover over occurs after the write has committed to the primary but before replication completes. You will likely see an exception at the client. An election occurs and a new primary is elected. When the original primary comes back up, it will rollback the committed write. However, from your application's standpoint, that write never completed, so that's ok.

	QUIZ:
		What happens if a node comes back up as a secondary after a period of being offline and the oplog has looped on the primary?

		R:
			√	The entire dataset will be copied from the primary
				A rollback will occur
				The new node stays offline (does not re-join the replica set)
				The new node begins to calculate Pi to a large number of decimal places

Lecture 10 - Connecting to a Replica Set from the Java Driver

	QUIZ:
		If you leave a replica set node out of the seedlist within the driver, what will happen?

		R:
				The missing node will not be used by the application.
			√	The missing node will be discovered as long as you list at least one valid node.
				This missing node will be used for reads, but not for writes.
				The missing node will be used for writes, but not for reads.

Lecture 11 - When bad Things Happen to Good Nodes

	In this case, the insert is idempotent because it contains a specific _id, and that field has a unique index. Inserts that don't involve a uniquely indexed field are not idempotent.

	QUIZ:
		If you use the MongoClient constructor that takes a seed list of replica set members, are you guaranteed to avoid application exceptions during a primary failover?

		R:
				Yes
			√	No

Lecture 12 - Write Concern Revisited

	Write concern (w) value can be set at client, database or collection level within PyMongo. When you call MongoClient, you get a connection to the driver, but behind the scenes, PyMongo connects to multiple nodes of the replica set. The w value can be set at the client level. Andrew says that the w concern can be set at the connection level; he really means client level. It's also important to note that wtimeout is the amount of time that the database will wait for replication before returning an error on the driver, but that even if the database returns an error due to wtimeout, the write will not be unwound at the primary and may complete at the secondaries. Hence, writes that return errors to the client due to wtimeout may in fact succeed, but writes that return success, do in fact succeed. Finally, the video shows the use of an insert command in PyMongo. That call is deprecated and it should have been insert_one.

	QUIZ:


		R:

		