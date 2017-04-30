package com.zhanlu.framework.nosql.dao;

import com.mongodb.*;
import com.zhanlu.framework.common.page.Page;
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
    public DBObject update(String collectionName, String id, DBObject document) {
        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        mongoDbFactory.getDb().getCollection(collectionName).update(query, document);
        return document;
    }

    /**
     * @param collectionName 集合名称
     * @param id             对象ID
     * @return 单个文档
     */
    public DBObject findOne(String collectionName, String id) {
        DBCollection collection = mongoDbFactory.getDb().getCollection(collectionName);
        return collection.findOne(new ObjectId(id));
    }

    /**
     * @param collectionName 集合名称
     * @return 所有文档
     */
    public List<DBObject> findAll(String collectionName) {
        return this.findByPage(collectionName, null, null);
    }

    /**
     * @param collectionName 集合名称
     * @return 所有文档
     */
    public List<DBObject> findByProp(String collectionName, DBObject query) {
        return this.findByPage(collectionName, query, null);
    }

    /**
     * @param collectionName 集合名称
     * @return 分页文档
     */
    public List<DBObject> findByPage(String collectionName, DBObject query, Page page) {
        DBCollection collection = mongoDbFactory.getDb().getCollection(collectionName);
        DBCursor cursor = null;
        if (page != null) {
            page.setTotalCount(collection.count(query));
            cursor = collection.find(query).skip((page.getPageNo() - 1) * page.getPageSize()).limit(page.getPageSize());
        } else if (query != null) {
            cursor = collection.find(query);
        } else {
            cursor = collection.find();
        }
        return cursor.toArray();
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