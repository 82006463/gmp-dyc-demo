package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.CustomerCompDao;
import com.zhanlu.custom.cms.entity.CustomerComp;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 客户公司
 */
@Service
public class CustomerCompService extends CommonService<CustomerComp, Long> {

    @Autowired
    private CustomerCompDao customerCompDao;

    @Override
    public void initDao() {
        super.commonDao = customerCompDao;
    }
}
