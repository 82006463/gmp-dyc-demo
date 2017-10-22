package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.CalibrationTmpDao;
import com.zhanlu.custom.cms.entity.CalibrationTmp;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 月度临校
 */
@Service
public class CalibrationTmpService extends CommonService<CalibrationTmp, Long> {

    @Autowired
    private CalibrationTmpDao calibrationTmpDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = calibrationTmpDao;
    }
}
