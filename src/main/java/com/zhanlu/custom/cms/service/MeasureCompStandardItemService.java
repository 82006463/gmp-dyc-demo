package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.MeasureCompStandardItemDao;
import com.zhanlu.custom.cms.entity.MeasureCompStandardItem;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 计量公司
 */
@Service
public class MeasureCompStandardItemService extends CommonService<MeasureCompStandardItem, Long> {

    @Autowired
    private MeasureCompStandardItemDao standardItemDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = standardItemDao;
    }
}
