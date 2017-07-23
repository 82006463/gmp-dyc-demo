package com.zhanlu.framework.logic.impl;

import com.mongodb.BasicDBObject;
import com.zhanlu.framework.logic.AuditLogic;
import com.zhanlu.framework.nosql.item.QueryItem;
import com.zhanlu.framework.nosql.service.MongoService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * 审计追踪服务实现
 */
@Service
public class AuditLogicImpl implements AuditLogic {

    @Autowired
    private MongoService mongoService;
    private String configMeta = "config_meta";
    private String metaLogsAudit = "meta_logs_audit";

    @Override
    public Map<String, Object> insert(Map<String, Object> oldEntity, Map<String, Object> newEntity) {
        Map<String, Object> tableStruct = null;
        if (newEntity.get("metaType") != null && newEntity.get("cmcode") != null) {
            List<QueryItem> queryItems = new ArrayList<>(2);
            queryItems.add(new QueryItem("Eq_String_type", newEntity.get("metaType")));
            queryItems.add(new QueryItem("Eq_String_code", newEntity.get("metaType") + "_" + newEntity.get("cmcode")));
            tableStruct = mongoService.findOne(configMeta, queryItems);
        }
        if (tableStruct != null) {
            Map<String, String> itemsMap = (Map) tableStruct.get("itemsMap");
            Map<String, Object> docMap = new HashMap<>();
            docMap.put("fk_id", oldEntity == null ? newEntity.get("_id") : oldEntity.get("_id"));
            docMap.put("fk_metaType", newEntity.get("metaType"));
            docMap.put("fk_cmcode", newEntity.get("cmcode"));
            docMap.put("metaType", "logs");
            docMap.put("cmcode", "audit");
            docMap.put("moduleCode", tableStruct.get("code"));
            docMap.put("moduleName", tableStruct.get("name"));
            docMap.put("moduleType", tableStruct.get("type"));
            StringBuilder buf = new StringBuilder();
            if (oldEntity == null) {
                buf.append(itemsMap.get("unq_name") + "：【" + newEntity.get(itemsMap.get("unq_code")) + "】被添加");
            } else {
                for (Map.Entry<String, Object> entry : newEntity.entrySet()) {
                    if (entry.getKey().startsWith("sec_create") || entry.getKey().startsWith("sec_update")) {
                        docMap.put(entry.getKey(), entry.getValue());
                        continue;
                    }
                    if (itemsMap.get(entry.getKey()) == null) {
                        continue;
                    }
                    String oldVal = null;
                    String newVal = null;
                    if (entry.getValue() != null && entry.getValue() instanceof Date) {
                        oldVal = oldEntity == null || oldEntity.get(entry.getKey()) == null ? "" : DateFormatUtils.format((Date) oldEntity.get(entry.getKey()), "yyyy-MM-dd");
                        newVal = DateFormatUtils.format((Date) entry.getValue(), "yyyy-MM-dd");
                    } else if (entry.getValue() != null && entry.getValue() instanceof Timestamp) {
                        oldVal = oldEntity == null || oldEntity.get(entry.getKey()) == null ? "" : DateFormatUtils.format((Date) oldEntity.get(entry.getKey()), "yyyy-MM-dd HH:mm:ss");
                        newVal = DateFormatUtils.format((Date) entry.getValue(), "yyyy-MM-dd HH:mm:ss");
                    } else {
                        oldVal = oldEntity == null || oldEntity.get(entry.getKey()) == null ? "" : oldEntity.get(entry.getKey()).toString().trim();
                        newVal = entry.getValue() == null || entry.getValue() == null ? "" : entry.getValue().toString().trim();
                    }
                    if (!oldVal.equals(newVal)) {
                        buf.append((itemsMap.get(entry.getKey()) + "：由【" + (oldVal.length() == 0 ? "N.A." : oldVal) + "】修改为【" + (newVal.length() == 0 ? "N.A." : newVal) + "】<br/>"));
                    }
                }
            }
            if (buf.length() > 0) {
                docMap.put("content", buf.toString());
                mongoService.saveOrUpdate(metaLogsAudit, null, new BasicDBObject(docMap));
            }
            if (newEntity.get("electron_sign_username") != null && newEntity.get("electron_sign_reason") != null) {
                docMap.put("content", "审批意见：" + newEntity.get("electron_sign_reason"));
                mongoService.saveOrUpdate(metaLogsAudit, null, new BasicDBObject(docMap));
            }
        }
        return newEntity;
    }

    @Override
    public Map<String, Object> insertForRemove(Map<String, Object> oldEntity, Map<String, Object> newEntity, Map<String, Object> extMap) {
        Map<String, Object> tableStruct = null;
        if (oldEntity.get("metaType") != null && oldEntity.get("cmcode") != null) {
            List<QueryItem> queryItems = new ArrayList<>(2);
            queryItems.add(new QueryItem("Eq_String_type", oldEntity.get("metaType")));
            queryItems.add(new QueryItem("Eq_String_code", oldEntity.get("cmcode")));
            tableStruct = mongoService.findOne(configMeta, queryItems);
        }
        if (newEntity == null && tableStruct != null) {
            Map<String, String> itemsMap = (Map) tableStruct.get("itemsMap");
            Map<String, Object> docMap = new HashMap<>();
            docMap.put("fk_id", oldEntity.get("_id"));
            docMap.put("fk_metaType", oldEntity.get("metaType"));
            docMap.put("fk_cmcode", oldEntity.get("cmcode"));
            docMap.put("metaType", "logs");
            docMap.put("cmcode", "audit");
            docMap.put("moduleCode", tableStruct.get("code"));
            docMap.put("moduleName", tableStruct.get("name"));
            docMap.put("moduleType", tableStruct.get("type"));
            docMap.putAll(extMap);
            docMap.put("content", itemsMap.get("code") + "：【" + oldEntity.get("code") + "】," + itemsMap.get("name") + "：【" + oldEntity.get("name") + "】被删除");
            mongoService.saveOrUpdate(metaLogsAudit, null, new BasicDBObject(docMap));
        }
        return oldEntity;
    }

}
