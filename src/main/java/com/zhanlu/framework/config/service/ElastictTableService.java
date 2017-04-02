package com.zhanlu.framework.config.service;

import com.zhanlu.framework.common.service.CommonService;
import com.zhanlu.framework.config.dao.ElasticTableDao;
import com.zhanlu.framework.config.entity.ElasticTable;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * 配置字典管理类
 *
 * @author yuqs
 * @since 0.1
 */
@Service
public class ElastictTableService extends CommonService<ElasticTable, Long> {

    @Autowired
    private ElasticTableDao elasticTableDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = elasticTableDao;
    }

    public ElasticTable findByCode(String code) throws Exception {
        Map<String, Object> params = new HashedMap(4);
        params.put("code", code);
        List<ElasticTable> list = super.findList(params);
        if (list == null || list.isEmpty()) {
            return new ElasticTable();
        }
        if (list.size() > 1) {
            throw new RuntimeException("result no unique");
        }
        return list.get(0);
    }
}
