package com.zhanlu.meta.service;

import com.zhanlu.framework.logic.MongoLogic;
import com.zhanlu.framework.nosql.item.QueryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MetaAppService {

    @Autowired
    private MongoLogic mongoLogic;

    public Map<String, Object> getMetaByCode(String cmcode) {
        List<QueryItem> queryItems = new ArrayList<>(2);
        queryItems.add(new QueryItem("Eq_String_code", cmcode));
        return mongoLogic.findOne("config_meta", queryItems);
    }
}