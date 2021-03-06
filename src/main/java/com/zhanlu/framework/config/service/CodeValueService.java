package com.zhanlu.framework.config.service;

import com.zhanlu.framework.common.service.CommonService;
import com.zhanlu.framework.config.dao.CodeValueDao;
import com.zhanlu.framework.config.entity.CodeRule;
import com.zhanlu.framework.config.entity.CodeValue;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 编号规则管理类
 */
@Service
public class CodeValueService extends CommonService<CodeValue, Long> {

    @Autowired
    private CodeValueDao codeValueDao;
    @Autowired
    private CodeRuleService codeRuleService;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = codeValueDao;
    }

    @Transactional
    public String getCodeValue(CodeValue codeValueParam) {
        if (codeValueParam.getRuleId() == null) {
            throw new RuntimeException("CodeRule.id is empty.");
        }

        Map<String, Object> params = new LinkedHashMap<>(8);
        if (codeValueParam.getRuleId() != null) {
            params.put("ruleId", codeValueParam.getRuleId());
        }
        List<CodeRule> rules = codeRuleService.findList(params);
        if (rules == null || rules.size() == 0) {
            throw new RuntimeException("Code Rule is empty.");
        }

        CodeRule rule = rules.get(0);
        if (StringUtils.isNotBlank(codeValueParam.getOrgCode())) {
            params.put("orgValue", codeValueParam.getOrgCode());
        }
        if (StringUtils.isNotBlank(codeValueParam.getFuncCode())) {
            params.put("funcValue", codeValueParam.getFuncCode());
        }
        List<CodeValue> list = super.findList(params);
        CodeValue codeValue = list == null || list.size() == 0 ? null : list.get(0);
        if (codeValue == null) {
            codeValue = new CodeValue();
            BeanUtils.copyProperties(codeValueParam, codeValue);
            codeValue.setRuleId(rule.getId());
        }

        DateFormat dateFormat = new SimpleDateFormat(rule.getTimePattern());
        String timeVal = dateFormat.format(new Date());
        String curValue = "";
        String[] ruleValueArr = rule.getRuleValue().split("\\&\\&");
        if (codeValue.getId() == null) {
            for (int i = 0; i < ruleValueArr.length; i++) {
                String ruleItem = ruleValueArr[0];
                if (ruleItem.startsWith("orgCode")) {
                    curValue += codeValueParam.getOrgCode();
                    if (ruleItem.contains("&"))
                        curValue += ruleItem.split("\\&")[1];
                } else if (ruleItem.startsWith("funcCode")) {
                    curValue += codeValueParam.getFuncCode();
                    if (ruleItem.contains("&"))
                        curValue += ruleItem.split("\\&")[1];
                } else if (ruleItem.startsWith("timePattern")) {
                    curValue += timeVal;
                    if (ruleItem.contains("&"))
                        curValue += ruleItem.split("\\&")[1];
                    codeValue.setTimeValue(timeVal);
                } else {
                    codeValue.setSerialValue(1);
                    for (int j = 1; j < rule.getSerialLength(); j++)
                        curValue += "0";
                    curValue += codeValue.getSerialValue();
                }
            }
        } else {
            boolean timeChanged = false;
            for (int i = 0; i < ruleValueArr.length; i++) {
                String ruleItem = ruleValueArr[0];
                if (ruleItem.startsWith("orgCode")) {
                    curValue += codeValueParam.getOrgCode();
                    if (ruleItem.contains("&"))
                        curValue += ruleItem.split("\\&")[1];
                } else if (ruleItem.startsWith("funcCode")) {
                    curValue += codeValueParam.getFuncCode();
                    if (ruleItem.contains("&"))
                        curValue += ruleItem.split("\\&")[1];
                } else if (ruleItem.startsWith("timePattern")) {
                    curValue += timeVal;
                    if (!timeVal.equals(codeValue.getTimeValue())) {
                        timeChanged = true;
                        for (int j = 1; j < rule.getSerialLength(); j++)
                            curValue += "0";
                        curValue += "1";
                        codeValue.setTimeValue(timeVal);
                    }
                    if (ruleItem.contains("&"))
                        curValue += ruleItem.split("\\&")[1];
                    if (timeChanged) {
                        codeValue.setSerialValue(1);
                        for (int j = 1; j < rule.getSerialLength(); j++)
                            curValue += "0";
                        curValue += codeValue.getSerialValue();
                    }
                }
                if (ruleItem.contains("${serialLength}") && !timeChanged) {
                    codeValue.setSerialValue(codeValue.getSerialValue() + 1);
                    curValue += codeValue.getSerialValue();
                }
            }
        }
        codeValue.setCurrentValue(curValue);
        super.saveOrUpdate(codeValue);
        return curValue;
    }
}
