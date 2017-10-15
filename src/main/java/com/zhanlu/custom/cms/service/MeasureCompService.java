package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.MeasureCompDao;
import com.zhanlu.custom.cms.entity.MeasureComp;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 计量公司
 */
@Service
public class MeasureCompService extends CommonService<MeasureComp, Long> {

    @Autowired
    private MeasureCompDao measureCompDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = measureCompDao;
    }
}
