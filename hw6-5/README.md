In this homework you will build a small replica set on your own computer. We will check that it works with MongoProc.

Create three directories for the three mongod processes. On unix, this could be done as follows:

<code>
    mkdir -p /data/rs1 /data/rs2 /data/rs3
</code>

Now start three mongo instances as follows. Note that are three commands. The browser is probably wrapping them visually.

Linux and Mac users:

<code>
    mongod --replSet m101 --logpath "1.log" --dbpath /data/rs1 --port 27017 --smallfiles --oplogSize 64 --fork
    mongod --replSet m101 --logpath "2.log" --dbpath /data/rs2 --port 27018 --smallfiles --oplogSize 64 --fork
    mongod --replSet m101 --logpath "3.log" --dbpath /data/rs3 --port 27019 --smallfiles --oplogSize 64 --fork
</code>

Now connect to a mongo shell and make sure it comes up.

<code>
    mongo --port 27017
</code>

Now you will create the replica set. Type the following commands into the mongo shell:

<code>
    config = 
        { _id: "m101", members:[
            { _id : 0, host : "localhost:27017"},
            { _id : 1, host : "localhost:27018"},
            { _id : 2, host : "localhost:27019"} ]
        };
    rs.initiate(config);
</code>

At this point, the replica set should be coming up. You can type

<code>
    rs.status()
<code>

to see the state of replication.

Now go to MongoProc to ensure that it works. Make sure to configure MongoProc to have mongod1 set to the hostname and port of one of the replica set members.

If you need to reinstall it, click here to download mongoProc.

You can also look back to the lesson on using mongoProc from chapter 2 if you have trouble using it.

R:
    MongoDB shell version: 3.0.1
    connecting to: 127.0.0.1:27017/test
    m101:PRIMARY> rs.status()
    {
            "set" : "m101",
            "date" : ISODate("2015-04-26T19:15:45.459Z"),
            "myState" : 1,
            "members" : [
                    {
                            "_id" : 0,
                            "name" : "localhost:27017",
                            "health" : 1,
                            "state" : 1,
                            "stateStr" : "PRIMARY",
                            "uptime" : 139,
                            "optime" : Timestamp(1430075683, 1),
                            "optimeDate" : ISODate("2015-04-26T19:14:43Z"),
                            "electionTime" : Timestamp(1430075686, 1),
                            "electionDate" : ISODate("2015-04-26T19:14:46Z"),
                            "configVersion" : 1,
                            "self" : true
                    },
                    {
                            "_id" : 1,
                            "name" : "localhost:27018",
                            "health" : 1,
                            "state" : 2,
                            "stateStr" : "SECONDARY",
                            "uptime" : 62,
                            "optime" : Timestamp(1430075683, 1),
                            "optimeDate" : ISODate("2015-04-26T19:14:43Z"),
                            "lastHeartbeat" : ISODate("2015-04-26T19:15:44.651Z"),
                            "lastHeartbeatRecv" : ISODate("2015-04-26T19:15:44.651Z"),
                            "pingMs" : 0,
                            "configVersion" : 1
                    },
                    {
                            "_id" : 2,
                            "name" : "localhost:27019",
                            "health" : 1,
                            "state" : 2,
                            "stateStr" : "SECONDARY",
                            "uptime" : 62,
                            "optime" : Timestamp(1430075683, 1),
                            "optimeDate" : ISODate("2015-04-26T19:14:43Z"),
                            "lastHeartbeat" : ISODate("2015-04-26T19:15:44.651Z"),
                            "lastHeartbeatRecv" : ISODate("2015-04-26T19:15:44.651Z"),
                            "pingMs" : 0,
                            "configVersion" : 1
                    }
            ],
            "ok" : 1
    }
