package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.DrugCompDao;
import com.zhanlu.custom.cms.entity.DrugComp;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 药企
 */
@Service
public class DrugCompService extends CommonService<DrugComp, Long> {

    @Autowired
    private DrugCompDao drugCompDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = drugCompDao;
    }
}
