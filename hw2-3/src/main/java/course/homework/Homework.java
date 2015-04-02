package course.homework;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class Homework {
    public static void main(String[] args)  {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Homework.class, "/freemarker");

        MongoClient client = new MongoClient();

        MongoDatabase database = client.getDatabase("m101");
        final MongoCollection<Document> collection = database.getCollection("funnynumbers");

        Spark.get(new Route("/") {
            @Override
            public Object handle(final Request request,
                                 final Response response) {
                StringWriter writer = new StringWriter();
                try {
                    Template template = configuration.getTemplate("answer.ftl");

                    // Not necessary yet to understand this.  It's just to prove that you
                    // are able to run a command on a mongod server
                    List<Document> results =
                    collection.aggregate(asList(new Document("$group", new Document("_id", "$value")
                                                                       .append("count", new Document("$sum", 1))),
                                                new Document("$match", new Document("count", new Document("$lte", 2))),
                                                new Document("$sort", new Document("_id", 1))))
                              .into(new ArrayList<Document>());

                    int answer = 0;
                    for (Document cur : results) {
                        answer += (Double) cur.get("_id");
                    }

                    Map<String, String> answerMap = new HashMap<String, String>();
                    answerMap.put("answer", Integer.toString(answer));

                    template.process(answerMap, writer);
                } catch (Exception e) {
                    e.printStackTrace();
                    halt(500);
                }
                return writer;
            }
        });
    }
}
