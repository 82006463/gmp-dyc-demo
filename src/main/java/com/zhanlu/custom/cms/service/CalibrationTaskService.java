package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.CalibrationTaskDao;
import com.zhanlu.custom.cms.entity.CalibrationTask;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 月度外校任务
 */
@Service
public class CalibrationTaskService extends CommonService<CalibrationTask, Long> {

    @Autowired
    private CalibrationTaskDao calibrationTaskDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = calibrationTaskDao;
    }
}
