package com.zhanlu.custom.dm.service;

import com.zhanlu.custom.dm.dao.BackupDetailDao;
import com.zhanlu.custom.dm.entity.BackupDetail;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 备份详情
 */
@Service
public class BackupDetailService extends CommonService<BackupDetail, Long> {

    @Autowired
    private BackupDetailDao backupDetailDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = backupDetailDao;
    }

}
