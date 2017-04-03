package com.zhanlu.framework.config.service;

import com.zhanlu.framework.common.service.CommonTreeService;
import com.zhanlu.framework.config.dao.DataDictDao;
import com.zhanlu.framework.config.entity.DataDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置字典管理类
 *
 * @author yuqs
 * @since 0.1
 */
@Service
public class DataDictService extends CommonTreeService<DataDict, Long> {

    @Autowired
    private DataDictDao dataDictDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = dataDictDao;
    }

    public DataDict findByCode(String code) {
        Map<String, Object> params = new LinkedHashMap<>(4);
        params.put("code", code);
        List<DataDict> list = super.findList(params);
        if (list == null || list.isEmpty()) {
            return new DataDict();
        }
        return list.get(0);
    }

}
