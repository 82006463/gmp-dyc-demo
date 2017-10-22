package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.StandardItemDao;
import com.zhanlu.custom.cms.entity.StandardItem;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 标准项
 */
@Service
public class StandardItemService extends CommonService<StandardItem, Long> {

    @Autowired
    private StandardItemDao standardItemDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = standardItemDao;
    }
}
