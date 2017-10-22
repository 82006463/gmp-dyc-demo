package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.CalibrationExtTaskDao;
import com.zhanlu.custom.cms.entity.CalibrationExtTask;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 月度外校任务
 */
@Service
public class CalibrationExtTaskService extends CommonService<CalibrationExtTask, Long> {

    @Autowired
    private CalibrationExtTaskDao calibrationExtTaskDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = calibrationExtTaskDao;
    }
}
