package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.CalibrationInTaskDao;
import com.zhanlu.custom.cms.entity.CalibrationInTask;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 通知
 */
@Service
public class CalibrationInTaskService extends CommonService<CalibrationInTask, Long> {

    @Autowired
    private CalibrationInTaskDao calibrationInTaskDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = calibrationInTaskDao;
    }
}
