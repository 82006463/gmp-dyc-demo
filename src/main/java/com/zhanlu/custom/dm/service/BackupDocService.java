package com.zhanlu.custom.dm.service;

import com.zhanlu.custom.dm.dao.BackupDocDao;
import com.zhanlu.custom.dm.entity.BackupDoc;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 备份文档
 */
@Service
public class BackupDocService extends CommonService<BackupDoc, Long> {

    @Autowired
    private BackupDocDao backupDocDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = backupDocDao;
    }

}
