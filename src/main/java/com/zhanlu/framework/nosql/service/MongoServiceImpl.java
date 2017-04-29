package com.zhanlu.framework.nosql.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.nosql.dao.MongoDao;
import com.zhanlu.framework.nosql.util.QueryItem;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/29.
 */
@Service
public class MongoServiceImpl implements MongoService {

    @Autowired
    private MongoDao mongoDao;

    @Override
    public Map<String, Object> saveOrUpdate(String collectionName, String id, Map<String, Object> docMap) {
        DBObject doc = new BasicDBObject();
        doc.putAll(docMap);
        if (id != null && id.length() > 0) {
            mongoDao.update(collectionName, id, doc);
        } else {
            DBObject insert = mongoDao.insert(collectionName, doc);
            docMap.put("id", insert.get("id"));
        }
        return docMap;
    }

    @Override
    public Map<String, Object> findOne(String collectionName, String id) {
        DBObject one = mongoDao.findOne(collectionName, id);
        return (Map<String, Object>) one.toMap();
    }

    @Override
    public List<Map<String, Object>> findAll(String collectionName) {
        return this.findByPage(collectionName, null, null);
    }

    @Override
    public List<Map<String, Object>> findByProp(String collectionName, List<QueryItem> queryItems) {
        return this.findByPage(collectionName, queryItems, null);
    }

    @Override
    public List<Map<String, Object>> findByPage(String collectionName, List<QueryItem> queryItems, Page page) {
        DBObject query = new BasicDBObject();
        if (queryItems != null && queryItems.size() > 0) {
            for (QueryItem item : queryItems) {
                if (item.getFieldVal() == null) {
                    continue;
                }
                query.put(item.getFieldName(), new Document("$" + item.getOpsType().toLowerCase(), item.getFieldVal()));
            }
        }
        List<DBObject> docList = mongoDao.findByPage(collectionName, query, page);
        List<Map<String, Object>> resultList = new ArrayList<>(docList.size());
        for (DBObject doc : docList) {
            resultList.add(doc.toMap());
        }
        return resultList;
    }

    @Override
    public int removeOne(String collectionName, String id) {
        return mongoDao.removeOne(collectionName, id).getN();
    }
}
