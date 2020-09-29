package com.gisonwin.util;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/21 14:07
 */
public class MongoUtils {

    private static MongoClient mongoCleint = new MongoClient("192.168.1.113", 27017);

    public static Document findoneby(String tablename, String database, String yearbasetype) {
        MongoDatabase mongoDatabase = mongoCleint.getDatabase(database);
        MongoCollection<Document> collection = mongoDatabase.getCollection(tablename);
        Document doc = new Document();
        doc.put("yearbasetype", yearbasetype);
        FindIterable<Document> iterable = collection.find(doc);
        MongoCursor<Document> cursor = iterable.iterator();
        if (cursor.hasNext()) {
            return cursor.next();
        }

        return null;
    }

    public static void saveorupdatemongo(String tablename,String database, Document doc){
        MongoDatabase mongoDatabase = mongoCleint.getDatabase(database);
        MongoCollection<Document> collection = mongoDatabase.getCollection(tablename);
        if (!doc.containsKey("_id")) { //如果不存在先创建.
            ObjectId objectId = new ObjectId();
            doc.put("_id",objectId);
            collection.insertOne(doc);
            return;
        }
        Document matchDoc = new Document();
        String objid = doc.get("_id").toString();
        matchDoc.put("_id",new ObjectId(objid));
        FindIterable<Document> iterable = collection.find(matchDoc);
        if (iterable.iterator().hasNext()) { //更新
            collection.updateOne(matchDoc,new Document("$set",doc));

        }else{
            collection.insertOne(doc);
        }
    }

}
