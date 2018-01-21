package com.zhanlu.custom.dm.service;

import com.zhanlu.custom.dm.dao.BackupDbDao;
import com.zhanlu.custom.dm.entity.BackupDb;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 备份数据库
 */
@Service
public class BackupDbService extends CommonService<BackupDb, Long> {

    @Autowired
    private BackupDbDao backupDbDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = backupDbDao;
    }

}
