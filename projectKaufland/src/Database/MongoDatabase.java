package Database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import Bill.Bill;
import Items.Item;
import Items.Piece;
import Items.DraftInterface;
import Items.Fruit;
import Main.Globals;

import java.text.SimpleDateFormat;

public class MongoDatabase {

    private static MongoDatabase mongodb = new MongoDatabase();

    private MongoDatabase(){
    }

    public static MongoDatabase getInstanceMongoDB(){
        return mongodb;
    }

    public MongoDatabase getConnectionToMongoDB(){
        try {
       
            MongoClient client = new MongoClient(new MongoClientURI(Globals.uri));
            MongoCredential credential = MongoCredential.createCredential(Globals.mongoUser,Globals.nameDatabase,Globals.password.toCharArray());

            MongoDatabase database = client.getDatabase(Globals.nameDatabase);

            return database;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
    public MongoCollection<Document> bill(){
        MongoDatabase database = getConnectionToMongoDB();

        try {
            MongoCollection<Document>  collection = database.getCollection("bill");
            return collection;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }

    }

    public MongoCollection<Document> item(){
        MongoDatabase database = getConnectionToMongoDB();
        try {
            MongoCollection<Document> collection = database.getCollection("item");
            return collection;
        }catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void addBillToMongoDB(Bill bill){
        MongoCollection<Document> collectionBill = bill();
        MongoCollection<Document> collectionItem = item();


        SimpleDateFormat formattime = new SimpleDateFormat("HH:mm:ss");
        String time = formattime.format(bill.getDate());

        SimpleDateFormat formatdate = new SimpleDateFormat("dd:MM:YYYY");
        String date = formatdate.format(bill.getDate());

        Document documentBill = new Document("Date", date).append("Time", time).append("TotalPrice", bill.getFinalPrice());
        collectionBill.insertOne(documentBill);
        ObjectId id = documentBill.getObjectId("_id");

        for (Item item:bill.getList()) {
            Document documentItem = new Document("name",item.getName())
                    .append("idBill",id)
                    .append("price",item.getPrice());
            if (item instanceof DraftInterface){
                documentItem.append("count",((DraftInterface) item).getVolume()).append("unit","l");
            }else if(item instanceof Fruit){
                documentItem.append("count",((Fruit) item).getWeight()).append("unit","kg");
            }else if(item instanceof Piece){
                documentItem.append("count",((Piece) item).getAmount()).append("unit","pcs");
            }
            collectionItem.insertOne(documentItem);
        }
    }


}