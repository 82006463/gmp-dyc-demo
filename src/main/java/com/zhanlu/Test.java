package com.zhanlu;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * Created by Administrator on 2017/4/16.
 */
public class Test {

    public static void main(String[] args) {
        MongoClient client = new MongoClient("101.200.36.213", 27017);
        MongoDbFactory dbFactory = new SimpleMongoDbFactory(client, "dyc_uc");
        DBCollection report = dbFactory.getDb().getCollection("dyc_report");

        DBObject doc = new BasicDBObject();
        doc.put("processType", "dev");
        report.insert(doc);
    }
}
