package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.CalibrationTmpTaskDao;
import com.zhanlu.custom.cms.entity.CalibrationTmpTask;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 通知
 */
@Service
public class CalibrationTmpTaskService extends CommonService<CalibrationTmpTask, Long> {

    @Autowired
    private CalibrationTmpTaskDao calibrationTmpTaskDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = calibrationTmpTaskDao;
    }
}
