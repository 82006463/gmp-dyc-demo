package com.zhanlu.framework.config.service;

import com.zhanlu.framework.common.service.CommonTreeService;
import com.zhanlu.framework.config.dao.DataDictDao;
import com.zhanlu.framework.config.entity.DataDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

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

}
