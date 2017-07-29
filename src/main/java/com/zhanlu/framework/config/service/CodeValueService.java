package com.zhanlu.framework.config.service;

import com.zhanlu.framework.common.service.CommonService;
import com.zhanlu.framework.config.dao.CodeRuleDao;
import com.zhanlu.framework.config.dao.CodeValueDao;
import com.zhanlu.framework.config.entity.CodeRule;
import com.zhanlu.framework.config.entity.CodeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * 编号规则管理类
 */
@Service
public class CodeValueService extends CommonService<CodeValue, Long> {

    @Autowired
    private CodeValueDao codeValueDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = codeValueDao;
    }

    @Transactional
    public String getCodeValue(CodeValue codeValue) {
        return null;
    }
}
