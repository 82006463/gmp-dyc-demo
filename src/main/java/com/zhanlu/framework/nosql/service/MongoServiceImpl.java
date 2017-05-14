package com.zhanlu.framework.nosql.service;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.nosql.dao.MongoDao;
import com.zhanlu.framework.nosql.util.QueryItem;
import com.zhanlu.framework.security.entity.User;
import com.zhanlu.framework.security.shiro.ShiroUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/4/29.
 */
@Service
public class MongoServiceImpl implements MongoService {

    @Autowired
    private MongoDao mongoDao;
    @Autowired
    private AuditService auditService;

    @Override
    public Map<String, Object> saveOrUpdate(String collectionName, String id, Map<String, Object> docMap) {
        DBObject doc = new BasicDBObject();
        doc.putAll(docMap);
        User user = ShiroUtils.getUser();
        Map<String, Object> userMap = new HashMap<>(4);
        if (user != null) {
            userMap.put("sec_updateById", user.getId());
            userMap.put("sec_updateByName", user.getFullname());
            userMap.put("sec_updateDeptId", user.getOrg().getId());
            userMap.put("sec_updateDeptName", user.getOrg().getName());
            userMap.put("sec_updateTime", new Date());
        }
        Map<String, Object> oldEntity = null;
        if (id != null && id.length() > 0) {
            if (userMap.size() > 0) {
                doc.putAll(userMap);
            }
            oldEntity = this.findOne(collectionName, id);
            mongoDao.update(collectionName, id, doc);
        } else {
            if (userMap.size() > 0) {
                userMap.put("sec_createById", userMap.get("sec_updateById"));
                userMap.put("sec_createByName", userMap.get("sec_updateByName"));
                userMap.put("sec_createDeptId", userMap.get("sec_updateDeptId"));
                userMap.put("sec_createDeptName", userMap.get("sec_updateDeptName"));
                userMap.put("sec_createTime", userMap.get("sec_updateTime"));
                doc.putAll(userMap);
            }
            DBObject insert = mongoDao.insert(collectionName, doc);
            docMap.put("_id", insert.get("id"));
        }

        //任务操作都要记审计追踪
        auditService.insert(oldEntity, docMap);
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
            Map<String, Document> documentMap = new HashMap<>(4);
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
                        if ((item.getCompareType().startsWith("Gt") || item.getCompareType().startsWith("Lt")) && documentMap.get(item.getFieldName()) != null) { //范围查询
                            Document document = documentMap.get(item.getFieldName());
                            document.put("$" + item.getCompareType().toLowerCase(), item.getFieldVal());
                        } else {
                            documentMap.put(item.getFieldName(), new Document("$" + item.getCompareType().toLowerCase(), item.getFieldVal()));
                        }
                        query.put(item.getFieldName(), documentMap.get(item.getFieldName()));
                    }
                }
            }
        }
        return query;
    }
}
