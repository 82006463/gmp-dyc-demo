package com.zhanlu.meta.dms.service;

import com.mongodb.BasicDBObject;
import com.zhanlu.framework.nosql.item.QueryItem;
import com.zhanlu.framework.nosql.service.MongoService;
import com.zhanlu.meta.service.FlowService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("flowDms")
public class DmsService implements FlowService {

    @Autowired
    private MongoService mongoService;
    private String metaDmsFile = "meta_dms_file";

    @Override
    public Map<String, Object> saveOrUpdate(Map<String, Object> tableStruct, String rowId, Map<String, Object> newEntity) {
        if (tableStruct == null)
            return newEntity;

        String metaDmsTab = tableStruct.get("oneTable").equals("1") ? "meta_flowDms_" + newEntity.get("cmcode") : "meta_flowDms";
        Map<String, Object> entity = (rowId == null || rowId.trim().length() == 0) ? newEntity : mongoService.findOne(metaDmsTab, rowId);
        mongoService.saveOrUpdate(metaDmsTab, rowId, new BasicDBObject(entity));
        this.updateDmsFile(newEntity);
        return newEntity;
    }

    private Map<String, Object> updateDmsFile(Map<String, Object> newEntity) {
        List<QueryItem> queryItems = new ArrayList<>(2);
        queryItems.add(new QueryItem("Eq_String_fileCode", newEntity.get("fileCode")));
        Map<String, Object> dmsFile = mongoService.findOne(metaDmsFile, queryItems);
        if (dmsFile == null) {
            dmsFile = new HashedMap(newEntity.size());
        }
        dmsFile.putAll(newEntity);
        String _id = dmsFile.get("_id") == null ? null : dmsFile.get("_id").toString();
        mongoService.saveOrUpdate(metaDmsFile, _id, new BasicDBObject(newEntity));
        return newEntity;
    }
}
