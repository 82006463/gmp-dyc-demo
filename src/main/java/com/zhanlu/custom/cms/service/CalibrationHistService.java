package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.CalibrationHistDao;
import com.zhanlu.custom.cms.entity.CalibrationHist;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 通知
 */
@Service
public class CalibrationHistService extends CommonService<CalibrationHist, Long> {

    @Autowired
    private CalibrationHistDao calibrationHistDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = calibrationHistDao;
    }
}
