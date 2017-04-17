package com.zhanlu;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.Map;

/**
 * Created by Administrator on 2017/4/16.
 */
public class Test {

    public static void main(String[] args) {
        MongoClient client = new MongoClient("101.200.36.213", 27017);
        MongoDbFactory dbFactory = new SimpleMongoDbFactory(client, "dyc_uc");
        DBCollection qs = dbFactory.getDb().getCollection("bd_qs");

        ObjectId objectId = new ObjectId("58f3623c0b412c19ecb42e5e");
        DBObject query = new BasicDBObject();
        query.put("_id", objectId);
        DBObject one = qs.findOne(query);

        Map<String, Object> objectMap = JSON.parseObject(JSON.toJSONString(one), Map.class);
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            System.err.println(entry.getKey() + ":" + entry.getValue() + ":" + (entry.getValue() instanceof Map));
        }
    }
}
