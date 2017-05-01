import com.alibaba.fastjson.JSON;
import com.mongodb.*;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/1.
 */
public class Test {
    public static void main(String[] args) {
        String sqlStr = "{'type':'chart'}";
        Map<String, Object> paramMap = JSON.parseObject(sqlStr, Map.class);

        MongoClient client = new MongoClient("101.200.36.213", 27017);
        MongoDbFactory factory = new SimpleMongoDbFactory(client, "dyc_uc");
        DBCollection app = factory.getDb().getCollection("config_meta_app");

        DBObject query = new BasicDBObject();
        query.putAll(paramMap);
        DBCursor cursor = app.find(query);
        List<DBObject> list = cursor.toArray();
        cursor.close();

        System.err.println(JSON.toJSONString(list));

        client.close();
    }
}
