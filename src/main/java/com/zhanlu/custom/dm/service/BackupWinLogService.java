package com.zhanlu.custom.dm.service;

import com.zhanlu.custom.dm.dao.BackupWinLogDao;
import com.zhanlu.custom.dm.entity.BackupWinLog;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 备份窗口日志
 */
@Service
public class BackupWinLogService extends CommonService<BackupWinLog, Long> {

    @Autowired
    private BackupWinLogDao backupWinLogDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = backupWinLogDao;
    }

}
