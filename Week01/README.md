<h1>Homeworkds Week 01 - INTRODUCTION</h1>

<ul>
  <li><a href="https://youtu.be/kTIqocKMItU" target="_blank"> Welcome to M101J</a></li>
  <li><a href="https://youtu.be/Lfl8hdQOi6Y" target="_blank">What is MongoDB?</a></li>
  <li><a href="https://youtu.be/-KIC1LXxcGM" target="_blank">MongoDB Relative to Relational</a></li>
  <li><a href="https://youtu.be/swhH4q_2Ttc" target="_blank">Overview of Building an App with MongoDB</a></li>
  <li><a href="https://youtu.be/e18vCIdQKp4" target="_blank">Quick Introduction to the Mongo Shell</a></li>
  <li><a href="https://youtu.be/PTATjNSjbJ0" target="_blank">Introduction to JSON</a></li>
  <li><a href="https://youtu.be/_vYz3CZwyK0" target="_blank">System Requirements</a></li> 
  <li><a href="https://youtu.be/_WJ8m5QHvwc" target="_blank">Installing MongoDB (mac)</a></li> 
  <li><a href="https://youtu.be/sBdaRlgb4N8" target="_blank">Installing MongoDB (windows)</a></li> 
  <li><a href="https://youtu.be/ZxRRA0MsXqs" target="_blank">Installing and Using Maven</a></li> 
  <li><a href="https://youtu.be/UH-VD_ypal8" target="_blank">Intro to the Spark Web Application Framework</a></li> 
  <li><a href="https://youtu.be/_8-3K2Ds-Ok" target="_blank">Intro to the Freemarker Templating Engine</a></li> 
  <li><a href="https://youtu.be/7fdtf9aLc2w" target="_blank">Spark and Freemarker Together</a></li> 
  <li><a href="https://youtu.be/7t1IafamuVs" target="_blank">Spark: Handling GET requests</a></li> 
  <li><a href="https://youtu.be/jZDuxesy5cc" target="_blank">Spark: Handling POST requests</a></li> 
  <li><a href="https://youtu.be/uKB-Hoqs6zI" target="_blank">MongoDB is Schemaless</a></li> 
  <li><a href="https://youtu.be/CTffxoSSLqg" target="_blank">JSON Revisited</a></li>
  <li><a href="https://youtu.be/vrYAEH3g13M" target="_blank">JSON Subdocuments</a></li>
  <li><a href="https://youtu.be/kOrsT94-A28" target="_blank">JSON Spec</a></li> 
  <li><a href="https://youtu.be/ePi3kDoexoM" target="_blank">Introduction to Our Class Project, The Blog</a></li> 
  <li><a href="https://youtu.be/boR2y9MHCa0" target="_blank">Blog in Relational Table</a></li>
  <li><a href="https://youtu.be/ZjwCzyqKVdY" target="_blank">Blog in Documents</a></li>
  <li><a href="https://youtu.be/6XE3wZCPiZ8" target="_blank">Introduction to Schema Design</a></li>
</ul>
<hr/>
<ul>
  <li>Homework 01</li>
  
    Install MongoDB on your computer and run it on the standard port.
  
    Download the HW1-1 from the Download Handout link and uncompress it.
    
    Use mongorestore to restore the dump into your running mongod. Do this by opening a terminal window (mac) or cmd window (windows) and navigating to the directory so that you are in the parent directory of the dump directory (if you used the default extraction method, it should be hw1/). 
    
    Now type:
       <code>mongorestore dump</code>
    
    Note you will need to have your path setup correctly to find mongorestore.
    
    Next, go into the Mongo shell, perform a findOne on the collection called hw1 in the database m101. That will return one document. Please provide the value corresponding to the "answer" key from the document returned.
  
    R:
        42
        
  <li>Homework 02</li>
  
    Which of the following are valid JSON documents? Please choose all that apply.

    R:
        { "name" : "Fred Flinstone" ; "occupation": "Miner" ; "wife" : "Wilma" }
        { "city" = "New York", "population" = 7999034, "boroughs" = ["queens", "manhattan", "staten island", "the bronx", "brooklyn"] }
      √  { "title" : "Star Wars", "quotes" : [ "Use the Force", "These are not the droids you are looking for" ], "director" : "George Lucas" }
      √  { "a" : 1, "b" : { "b" : 1, "c" : "foo", "d" : "bar", "e" : [1, 2, 4] } }
      √  {}
  
  <li><a href="/hw1-3">Homework 03</a></li>
</ul>
