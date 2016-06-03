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
    @Override
    public void insert(Date object) {
        MongoClient client = new MongoClient("localhost", 12345);
        MongoDatabase database = client.getDatabase("stampo");
        MongoCollection<Document> stamps = database.getCollection("stamps");
        stamps.insertOne(new Document("date", object));
        client.close();
    }

    @Override
    public List<Date> getAll() {
        MongoClient client = new MongoClient("localhost", 12345);
        MongoDatabase database = client.getDatabase("stampo");
        MongoCollection<Document> stamps = database.getCollection("stamps");
        List<Date> results = new ArrayList<>();
        stamps.find().forEach((Block<? super Document>) d -> results.add((Date) d.get("date")));
        return results;
    }
}
