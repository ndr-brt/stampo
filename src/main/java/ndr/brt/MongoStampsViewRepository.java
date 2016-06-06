package ndr.brt;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MongoStampsViewRepository implements Repository<Date> {

    private final MongoCollection<Document> stamps;

    public MongoStampsViewRepository() {
        MongoClient client = new MongoClient("localhost", 12345);
        MongoDatabase database = client.getDatabase("stampo");
        stamps = database.getCollection("stamps");
    }

    @Override
    public void insert(Date object) {
        stamps.insertOne(new Document("date", object));
    }

    @Override
    public List<Date> getAll() {
        List<Date> results = new ArrayList<>();
        stamps.find().forEach((Block<? super Document>) d -> results.add((Date) d.get("date")));
        return results;
    }
}
