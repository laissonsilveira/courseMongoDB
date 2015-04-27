package course;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class BlogPostDAO {
    MongoCollection<Document> postsCollection;

    public BlogPostDAO(final MongoDatabase blogDatabase) {
	postsCollection = blogDatabase.getCollection("posts");
    }

    // Return a single post corresponding to a permalink
    public Document findByPermalink(String permalink) {

	// XXX HW 3.2, Work Here
	Document post = postsCollection.find(eq("permalink", permalink))
		.first();

	return post;
    }

    // Return a list of posts in descending order. Limit determines
    // how many posts are returned.
    public List<Document> findByDateDescending(int limit) {

	// XXX HW 3.2, Work Here
	// Return a list of DBObjects, each one a post from the posts collection
	List<Document> posts = postsCollection.find().sort(descending("date"))
		.limit(limit).into(new ArrayList<Document>());

	return posts;
    }

    public String addPost(String title, String body, List tags, String username) {

	System.out.println("inserting blog entry " + title + " " + body);

	String permalink = title.replaceAll("\\s", "_"); // whitespace becomes _
	permalink = permalink.replaceAll("\\W", ""); // get rid of non
	// alphanumeric
	permalink = permalink.toLowerCase();

	// XXX HW 3.2, Work Here
	// Remember that a valid post has the following keys:
	// author, body, permalink, tags, comments, date
	//
	// A few hints:
	// - Don't forget to create an empty list of comments
	// - for the value of the date key, today's datetime is fine.
	// - tags are already in list form that implements suitable interface.
	// - we created the permalink for you above.

	if (validateField(username, body, permalink)
		|| ((tags != null) && tags.isEmpty())) {
	    return "Preencha todos os campos";
	}

	// Build the post object and insert it
	Document postDoc = new Document("title", title);
	postDoc.put("author", username);
	postDoc.put("body", body);
	postDoc.put("comments", new ArrayList<Document>());
	postDoc.put("date", new Date());
	postDoc.put("permalink", permalink);
	postDoc.put("tags", tags);

	postsCollection.insertOne(postDoc);

	return permalink;
    }

    private boolean validateField(String... fields) {
	boolean valid = true;
	for (String field : fields) {
	    valid &= (field != null) && field.isEmpty();
	}
	return valid;
    }

    // White space to protect the innocent

    // Append a comment to a blog post
    public void addPostComment(final String name, final String email,
	    final String body, final String permalink) {

	// XXX HW 3.3, Work Here
	// Hints:
	// - email is optional and may come in NULL. Check for that.
	// - best solution uses an update command to the database and a suitable
	// operator to append the comment on to any existing list of comments
    }
}
