package com.zhanlu.framework.config.entity;

import com.zhanlu.framework.common.entity.CodeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 编号规则
 */
@Entity
@Table(name = "wfc_code_rule")
public class CodeRule extends CodeEntity {

    //部门编号
    private String orgCode;
    //部门编号分隔符
    private String orgSeparator;
    //功能编号
    private String funcCode;
    //功能编号分隔符
    private String funcSeparator;
    //时间模式：yyyy | yyyyMM | yyyyMMdd
    private String timePattern;
    //时间模式分隔符
    private String timeSeparator;
    //流水号长度
    private Integer serialLength;
    //规则值
    private String ruleValue;

    @Column(length = 20)
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Column(length = 2)
    public String getOrgSeparator() {
        return orgSeparator;
    }

    public void setOrgSeparator(String orgSeparator) {
        this.orgSeparator = orgSeparator;
    }

    @Column(length = 20)
    public String getFuncCode() {
        return funcCode;
    }

    public void setFuncCode(String funcCode) {
        this.funcCode = funcCode;
    }

    @Column(length = 2)
    public String getFuncSeparator() {
        return funcSeparator;
    }

    public void setFuncSeparator(String funcSeparator) {
        this.funcSeparator = funcSeparator;
    }

    @Column(length = 10)
    public String getTimePattern() {
        return timePattern;
    }

    public void setTimePattern(String timePattern) {
        this.timePattern = timePattern;
    }

    @Column(length = 2)
    public String getTimeSeparator() {
        return timeSeparator;
    }

    public void setTimeSeparator(String timeSeparator) {
        this.timeSeparator = timeSeparator;
    }

    @Column(length = 10)
    public Integer getSerialLength() {
        return serialLength;
    }

    public void setSerialLength(Integer serialLength) {
        this.serialLength = serialLength;
    }

    @Column(length = 200)
    public String getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }
}
