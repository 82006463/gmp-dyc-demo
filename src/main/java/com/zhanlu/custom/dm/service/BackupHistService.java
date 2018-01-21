package com.zhanlu.custom.dm.service;

import com.zhanlu.custom.dm.dao.BackupHistDao;
import com.zhanlu.custom.dm.entity.BackupHist;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 备份历史
 */
@Service
public class BackupHistService extends CommonService<BackupHist, Long> {

    @Autowired
    private BackupHistDao backupHistDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = backupHistDao;
    }

}
