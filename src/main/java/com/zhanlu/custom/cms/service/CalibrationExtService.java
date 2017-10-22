package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.CalibrationExtDao;
import com.zhanlu.custom.cms.entity.CalibrationExt;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 通知
 */
@Service
public class CalibrationExtService extends CommonService<CalibrationExt, Long> {

    @Autowired
    private CalibrationExtDao calibrationExtDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = calibrationExtDao;
    }
}
