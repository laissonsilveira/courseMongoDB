In this problem, we want to test that you have a working JDK, that maven is installed and that you can run maven-based projects. Please install JDK for Java version 6 or 7, as well as Maven 3.1. If they are not already installed, you can find them in the courseware.

Download hw1-3.zip from Download Handout link, uncompress it, cd into the hw1-3 directory (there should be a pom.xml file in there), and run Maven as follows:

<code>mvn compile exec:java -Dexec.mainClass=com.mongodb.SparkHomework</code>

It requires Maven to be installed correctly to work. If you run it correctly, you should be able to go to localhost:4567 in your browser, or simply curl it.
If everything is set up correctly, you will see the following:

<code>The answer is:</code>

Type the number that appears after the colon into the box below. No spaces, nothing but digits.

	R:
		523258

<a href="../../../blob/master/README.md">Go back to homepage</a>
