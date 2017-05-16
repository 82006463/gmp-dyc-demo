package com.zhanlu.framework.nosql.service;

import com.mongodb.BasicDBObject;
import com.zhanlu.framework.nosql.dao.MongoDao;
import com.zhanlu.framework.nosql.util.QueryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 审计追踪服务实现
 */
@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    private MongoDao mongoDao;
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
            queryItems.add(new QueryItem("Eq_String_code", newEntity.get("cmcode")));
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
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, Object> entry : newEntity.entrySet()) {
                if (entry.getKey().startsWith("sec_create") || entry.getKey().startsWith("sec_update")) {
                    docMap.put(entry.getKey(), entry.getValue());
                    continue;
                }
                if (itemsMap.get(entry.getKey()) == null) {
                    continue;
                }
                if (oldEntity == null) {
                    buf.append(itemsMap.get(entry.getKey()) + "：【" + entry.getValue() + "】被添加<br/>");
                } else {
                    if (!entry.getValue().equals(oldEntity.get(entry.getKey()))) {
                        buf.append(itemsMap.get(entry.getKey()) + "：由【" + oldEntity.get(entry.getKey()) + "】修改为【" + entry.getValue() + "】<br/>");
                    }
                }
            }
            if (buf.length() > 0) {
                docMap.put("content", buf.toString());
                mongoDao.insert(metaLogsAudit, new BasicDBObject(docMap));
            }
        }
        return newEntity;
    }

}
