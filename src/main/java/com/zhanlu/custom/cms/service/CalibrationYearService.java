package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.CalibrationYearDao;
import com.zhanlu.custom.cms.entity.CalibrationYear;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 通知
 */
@Service
public class CalibrationYearService extends CommonService<CalibrationYear, Long> {

    @Autowired
    private CalibrationYearDao calibrationYearDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = calibrationYearDao;
    }
}
