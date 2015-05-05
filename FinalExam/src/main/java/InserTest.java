import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.laisson.util.ConnectionBase;
import org.bson.Document;

/**
 * @author Laisson R. Silveira
 *         laisson.r.silveira@gmail.com
 *         5/1/15
 */
public class InserTest {

    public static void main(String[] args) {
	MongoCollection<Document> animals = ConnectionBase.connect("test", "animals");
	animals.drop();

	Document animal = new Document("animal", "monkey");

	try {
	    animals.insertOne(animal);
	    animal.remove("animal");
	    animal.append("animal", "cat");
	    animals.insertOne(animal); //E11000 duplicate key error index: test.animals.$_id_ dup key
	    animal.remove("animal");
	    animal.append("animal", "lion");
	    animals.insertOne(animal);
	} catch (MongoWriteException e) {
	    e.printStackTrace();
	}
	System.out.println(animals.count());
    }

}
