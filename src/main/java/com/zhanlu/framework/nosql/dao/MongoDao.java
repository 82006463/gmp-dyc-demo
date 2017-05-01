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
    public DBObject insert(String dbName, String collectionName, DBObject document) {
        DB db = dbName == null || dbName.trim().length() == 0 ? mongoDbFactory.getDb() : mongoDbFactory.getDb(dbName);
        db.getCollection(collectionName).save(document);
        return document;
    }

    /**
     * 更新文档
     *
     * @param collectionName 集合名称
     * @param id             对象ID
     * @param document       文档
     * @return 是否成功
     */
    public DBObject update(String dbName, String collectionName, String id, DBObject document) {
        DB db = dbName == null || dbName.trim().length() == 0 ? mongoDbFactory.getDb() : mongoDbFactory.getDb(dbName);
        DBObject query = new BasicDBObject("_id", new ObjectId(id));
        db.getCollection(collectionName).update(query, document);
        return document;
    }

    /**
     * @param collectionName 集合名称
     * @param id             对象ID
     * @return 单个文档
     */
    public DBObject findOne(String dbName, String collectionName, String id) {
        DB db = dbName == null || dbName.trim().length() == 0 ? mongoDbFactory.getDb() : mongoDbFactory.getDb(dbName);
        DBCollection collection = db.getCollection(collectionName);
        return collection.findOne(new ObjectId(id));
    }

    /**
     * @param collectionName 集合名称
     * @return 所有文档
     */
    public List<DBObject> findAll(String dbName, String collectionName) {
        return this.findByPage(dbName, collectionName, null, null);
    }

    /**
     * @param collectionName 集合名称
     * @param query          查询条件
     * @return 所有文档
     */
    public List<DBObject> findByProp(String dbName, String collectionName, DBObject query) {
        return this.findByPage(dbName, collectionName, query, null);
    }

    /**
     * @param collectionName 集合名称
     * @param query          查询条件
     * @return 分页文档
     */
    public List<DBObject> findByPage(String dbName, String collectionName, DBObject query, Page page) {
        DB db = dbName == null || dbName.trim().length() == 0 ? mongoDbFactory.getDb() : mongoDbFactory.getDb(dbName);
        DBCollection collection = db.getCollection(collectionName);
        DBCursor cursor = null;
        if (page != null) {
            page.setTotalCount(collection.count(query));
            cursor = collection.find(query).skip((page.getPageNo() - 1) * page.getPageSize()).limit(page.getPageSize());
        } else if (query != null) {
            cursor = collection.find(query);
        } else {
            cursor = collection.find();
        }
        List<DBObject> list = cursor.toArray();
        cursor.close();
        return list;
    }

    /**
     * @param collectionName 集合名称
     * @param id             对象ID
     * @return 是否成功
     */
    public WriteResult removeOne(String dbName, String collectionName, String id) {
        DB db = dbName == null || dbName.trim().length() == 0 ? mongoDbFactory.getDb() : mongoDbFactory.getDb(dbName);
        DBObject query = new BasicDBObject("_id", new ObjectId(id));
        return db.getCollection(collectionName).remove(query);
    }

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
     * @param id             对象ID
     * @param document       文档
     * @return 是否成功
     */
    public DBObject update(String collectionName, String id, DBObject document) {
        DBObject query = new BasicDBObject("_id", new ObjectId(id));
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
     * @param query          查询条件
     * @return 所有文档
     */
    public List<DBObject> findByProp(String collectionName, DBObject query) {
        return this.findByPage(collectionName, query, null);
    }

    /**
     * @param collectionName 集合名称
     * @param query          查询条件
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
        List<DBObject> list = cursor.toArray();
        cursor.close();
        return list;
    }

    /**
     * @param collectionName 集合名称
     * @param id             对象ID
     * @return 是否成功
     */
    public WriteResult removeOne(String collectionName, String id) {
        DBObject query = new BasicDBObject("_id", new ObjectId(id));
        return mongoDbFactory.getDb().getCollection(collectionName).remove(query);
    }
}
