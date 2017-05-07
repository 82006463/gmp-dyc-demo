package com.zhanlu.framework.nosql.service;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.nosql.dao.MongoDao;
import com.zhanlu.framework.nosql.util.QueryItem;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
    public Map<String, Object> findOne(String collectionName, List<QueryItem> queryItems) {
        List<Map<String, Object>> docList = this.findByProp(collectionName, queryItems);
        if (docList == null || docList.isEmpty()) {
            return new HashMap<>();
        }
        return docList.get(0);
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
        List<DBObject> docList = mongoDao.findByPage(collectionName, this.parseQuery(queryItems, query), page);
        List<Map<String, Object>> resultList = new ArrayList<>(docList.size());
        for (DBObject doc : docList) {
            resultList.add(doc.toMap());
        }
        if (page != null) {
            page.setResult(resultList);
        }
        return resultList;
    }

    @Override
    public long countByProp(String collectionName, List<QueryItem> queryItems) {
        DBObject query = new BasicDBObject();
        return mongoDao.countByProp(collectionName, this.parseQuery(queryItems, query));
    }

    @Override
    public int removeOne(String collectionName, String id) {
        return mongoDao.removeOne(collectionName, id).getN();
    }

    private DBObject parseQuery(List<QueryItem> queryItems, DBObject query) {
        if (queryItems != null && queryItems.size() > 0) {
            for (QueryItem item : queryItems) {
                if (item.getFieldVal() == null) {
                    continue;
                }
                if (item.getCompareType().startsWith("$")) {
                    BasicDBList values = new BasicDBList();
                    List<QueryItem> subItems = item.getSubItems();
                    for (QueryItem subItem : subItems) {
                        DBObject queryTmp = new BasicDBObject();
                        if (subItem.getCompareType().equalsIgnoreCase("like")) {
                            Pattern pattern = Pattern.compile("^.*" + item.getFieldVal() + ".*$", Pattern.CASE_INSENSITIVE);
                            queryTmp.put(subItem.getFieldName(), pattern);
                        } else {
                            queryTmp.put(subItem.getFieldName(), new Document("$" + subItem.getCompareType().toLowerCase(), item.getFieldVal()));
                        }
                        values.add(queryTmp);
                    }
                    query.put(item.getCompareType(), values);
                } else {
                    if (item.getCompareType().equalsIgnoreCase("like")) {
                        Pattern pattern = Pattern.compile("^.*" + item.getFieldVal() + ".*$", Pattern.CASE_INSENSITIVE);
                        query.put(item.getFieldName(), pattern);
                    } else {
                        query.put(item.getFieldName(), new Document("$" + item.getCompareType().toLowerCase(), item.getFieldVal()));
                    }
                }
            }
        }
        return query;
    }
}
