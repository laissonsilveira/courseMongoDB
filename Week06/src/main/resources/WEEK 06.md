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

