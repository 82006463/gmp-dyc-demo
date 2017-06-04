package com.zhanlu.framework.logic.impl;

import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.logic.AuditLogic;
import com.zhanlu.framework.logic.MongoLogic;
import com.zhanlu.framework.nosql.item.QueryItem;
import com.zhanlu.framework.nosql.service.MongoService;
import com.zhanlu.framework.security.entity.User;
import com.zhanlu.framework.security.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2017/4/29.
 */
@Service
public class MongoLogicImpl implements MongoLogic {

    @Autowired
    private MongoService mongoService;
    @Autowired
    private AuditLogic auditLogic;

    @Override
    public Map<String, Object> saveOrUpdate(String collectionName, String id, Map<String, Object> paramMap) {
        paramMap.putAll(this.getUserMap(id));
        Map<String, Object> oldEntity = (id != null && id.length() > 0) ? this.findOne(collectionName, id) : null;
        Map<String, Object> entity = mongoService.saveOrUpdate(collectionName, id, paramMap);
        auditLogic.insert(oldEntity, paramMap); //任务操作都要记审计追踪
        return entity;
    }

    @Override
    public Map<String, Object> findOne(String collectionName, String id) {
        return this.mongoService.findOne(collectionName, id);
    }

    @Override
    public Map<String, Object> findOne(String collectionName, List<QueryItem> queryItems) {
        return this.mongoService.findOne(collectionName, queryItems);
    }

    @Override
    public List<Map<String, Object>> findAll(String collectionName) {
        return this.mongoService.findAll(collectionName);
    }

    @Override
    public List<Map<String, Object>> findByProp(String collectionName, List<QueryItem> queryItems) {
        return this.mongoService.findByProp(collectionName, queryItems);
    }

    @Override
    public Map<String, Object> findMetaByCode(String cmcode) {
        List<QueryItem> queryItems = new ArrayList<>(2);
        queryItems.add(new QueryItem("Eq_String_code", cmcode));
        return this.mongoService.findOne("config_meta", queryItems);
    }

    @Override
    public List<Map<String, Object>> findByPage(String collectionName, List<QueryItem> queryItems, Page page) {
        return this.mongoService.findByPage(collectionName, queryItems, page);
    }

    @Override
    public long countByProp(String collectionName, List<QueryItem> queryItems) {
        return this.mongoService.countByProp(collectionName, queryItems);
    }

    @Override
    public int removeOne(String collectionName, String id) {
        Map<String, Object> oldEntity = mongoService.findOne(collectionName, id);
        int rowCounnt = this.mongoService.removeOne(collectionName, id);
        Map<String, Object> newEntity = mongoService.findOne(collectionName, id);
        auditLogic.insertForRemove(oldEntity, newEntity, this.getUserMap(id));
        return rowCounnt;
    }

    private Map<String, Object> getUserMap(String entityId) {
        User user = ShiroUtils.getUser();
        Map<String, Object> userMap = new HashMap<>(4);
        if (user != null) {
            userMap.put("sec_updateById", user.getId());
            userMap.put("sec_updateByName", user.getFullname());
            userMap.put("sec_updateDeptId", user.getOrg().getId());
            userMap.put("sec_updateDeptName", user.getOrg().getName());
            userMap.put("sec_updateTime", new Date());
            if (entityId == null || entityId.length() == 0) {
                userMap.put("sec_createById", userMap.get("sec_updateById"));
                userMap.put("sec_createByName", userMap.get("sec_updateByName"));
                userMap.put("sec_createDeptId", userMap.get("sec_updateDeptId"));
                userMap.put("sec_createDeptName", userMap.get("sec_updateDeptName"));
                userMap.put("sec_createTime", userMap.get("sec_updateTime"));
            }
        }
        return userMap;
    }

}
