package com.zhanlu.framework.common.dao;

import com.mongodb.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/4/15.
 */
@Repository
public class MongoDao {

    @Autowired
    private MongoDbFactory mongoDbFactory;

    /**
     * 插入文档
     *
     * @param collectionName 集合名称
     * @param document       文档
     * @return 是否成功
     */
    public DBObject insert(String collectionName, DBObject document) {
        mongoDbFactory.getDb().getCollection(collectionName).save(document);
        return document;
    }

    /**
     * 更新文档
     *
     * @param collectionName 集合名称
     * @param document       文档
     * @return 是否成功
     */
    public DBObject update(String collectionName, DBObject document) {
        DBObject query = new BasicDBObject();
        query.put("id", document.get("id"));
        mongoDbFactory.getDb().getCollection(collectionName).update(query, document);
        return document;
    }

    /**
     * @param collectionName 集合名称
     * @param id             对象ID
     * @return 单个文档
     */
    public DBObject findOne(String collectionName, String id) {
        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        return mongoDbFactory.getDb().getCollection(collectionName).findOne(query);
    }

    /**
     * @param collectionName 集合名称
     * @return 所有文档
     */
    public List<DBObject> findAll(String collectionName) {
        DBCollection collection = mongoDbFactory.getDb().getCollection(collectionName);
        DBCursor dbObjects = collection.find();
        return dbObjects.toArray();
    }

    /**
     * @param collectionName 集合名称
     * @return 所有文档
     */
    public List<DBObject> findByProp(String collectionName, DBObject query) {
        DBCollection collection = mongoDbFactory.getDb().getCollection(collectionName);
        DBCursor dbObjects = collection.find(query);
        return dbObjects.toArray();
    }

    /**
     * @param collectionName 集合名称
     * @param regex          正则表达式
     * @return 分页文档
     */
    public List<DBObject> findByPage(String collectionName, String regex) {
        DBCollection collection = mongoDbFactory.getDb().getCollection(collectionName);
        collection.findOne();
        return null;
    }

    /**
     * @param collectionName 集合名称
     * @param id             对象ID
     * @return 是否成功
     */
    public WriteResult removeOne(String collectionName, String id) {
        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        return mongoDbFactory.getDb().getCollection(collectionName).remove(query);
    }
}
