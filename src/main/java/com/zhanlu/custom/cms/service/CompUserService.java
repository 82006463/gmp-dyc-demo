package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.CompUserDao;
import com.zhanlu.custom.cms.entity.CompUser;
import com.zhanlu.framework.common.service.CommonService;
import com.zhanlu.framework.config.dao.CodeRuleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户
 */
@Service
public class CompUserService extends CommonService<CompUser, Long> {

    @Autowired
    private CompUserDao compUserDao;

    @Override
    public void initDao() {
        super.commonDao = compUserDao;
    }
}
