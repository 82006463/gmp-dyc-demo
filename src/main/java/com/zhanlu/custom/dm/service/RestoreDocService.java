package com.zhanlu.custom.dm.service;

import com.zhanlu.custom.dm.dao.RestoreDocDao;
import com.zhanlu.custom.dm.entity.RestoreDoc;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 还原文档
 */
@Service
public class RestoreDocService extends CommonService<RestoreDoc, Long> {

    @Autowired
    private RestoreDocDao restoreDocDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = restoreDocDao;
    }

}
