package com.zhanlu.framework.config.service;

import com.zhanlu.framework.common.service.CommonService;
import com.zhanlu.framework.config.dao.CodeRuleDao;
import com.zhanlu.framework.config.entity.CodeRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 编号规则管理类
 */
@Service
public class CodeRuleService extends CommonService<CodeRule, Long> {

    @Autowired
    private CodeRuleDao codeRuleDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = codeRuleDao;
    }

}
