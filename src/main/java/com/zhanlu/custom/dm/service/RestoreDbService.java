package com.zhanlu.custom.dm.service;

import com.zhanlu.custom.dm.dao.RestoreDbDao;
import com.zhanlu.custom.dm.entity.RestoreDb;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 还原数据库
 */
@Service
public class RestoreDbService extends CommonService<RestoreDb, Long> {

    @Autowired
    private RestoreDbDao restoreDbDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = restoreDbDao;
    }

}
