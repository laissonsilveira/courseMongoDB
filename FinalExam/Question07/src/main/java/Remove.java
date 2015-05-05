import com.mongodb.client.MongoCollection;
import com.mongodb.laisson.util.ConnectionBase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Laisson R. Silveira
 *         laisson.r.silveira@gmail.com
 *         5/1/15
 */
public class Remove {

    /**
     * resources\albunsCollection.json
     * resources\images.json
     */
    private static MongoCollection<Document> albunsCollection;
    private static MongoCollection<Document> imagesCollection;

    public static void main(String[] args) {
	albunsCollection = ConnectionBase.connect("test", "albuns");
	imagesCollection = ConnectionBase.connect("test", "images");

	remove();
    }

    /**
     * Remove every image from the images collection that appears in no album.
     * Or put another way, if an image does not appear in at least one album, it's an orphan and should be removed from the images collection.
     */
    private static void remove() {

	List<Document> docAlbuns = albunsCollection.find().into(new ArrayList<Document>());
	List<Document> docImages = imagesCollection.find().into(new ArrayList<Document>());
	List<Double> idsToRemove = new ArrayList<Double>();

	for (Document image : docImages) {
	    Double idImage = image.getDouble("_id");
	    boolean remove = true;
	    if (idImage == null) {
		return;
	    }
	    for (Document album : docAlbuns) {
		if (album.get("images", ArrayList.class).contains(idImage)) {
		    remove = false;
		    continue;
		}
	    }
	    if (remove) {
		idsToRemove.add(idImage);
	    }
	}

	for (Double id : idsToRemove) {
	    imagesCollection.deleteOne(eq("_id", id));
	}

	System.out.println(imagesCollection.count(eq("tags", "sunrises")));
    }
}
