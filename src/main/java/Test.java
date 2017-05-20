import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/1.
 */
public class Test {
    public static void main(String[] args) {
        /*String sqlStr = "Like_String_type OR Like_String_name";

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

        client.close();*/

        String json = "[{code:'code',name:'name',dataType:'String',tagType:'Input',required:true},{code:'code',name:'name',dataType:'String',tagType:'Input',required:true}]";
        List<Map<String, Object>> list = JSON.parseObject(json, List.class);
        for (Map<String,Object> map:list) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.err.println(entry.getKey() + ":" + entry.getValue());
            }
        }
    }
}
