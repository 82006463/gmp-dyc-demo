package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.CalibrationInDao;
import com.zhanlu.custom.cms.entity.CalibrationIn;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 通知
 */
@Service
public class CalibrationInService extends CommonService<CalibrationIn, Long> {

    @Autowired
    private CalibrationInDao calibrationInDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = calibrationInDao;
    }
}
