package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.CompNotifyDao;
import com.zhanlu.custom.cms.entity.CompNotify;
import com.zhanlu.framework.common.service.CommonService;
import com.zhanlu.framework.config.dao.CodeRuleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 通知
 */
@Service
public class CompNotifyService extends CommonService<CompNotify, Long> {

    @Autowired
    private CompNotifyDao compNotifyDao;

    @Override
    public void initDao() {
        super.commonDao = compNotifyDao;
    }
}
