package com.zhanlu.framework.config.entity;

import com.zhanlu.framework.common.entity.CodeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 由编号规则生成的值
 */
@Entity
@Table(name = "wfc_code_value")
public class CodeValue extends CodeEntity {

    //规则ID、编号
    private Long ruleId;
    private String ruleCode;

    //部门编号
    private String orgValue;
    //功能编号
    private String funcValue;
    //时间模式下的当前时间值
    private Integer timeValue;
    //当前流水号
    private Integer serialValue;
    //当前值的编码规则：年+月+日+部门编号+流水号
    private String currentValue;

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    @Column(length = 20)
    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    @Column(length = 50)
    public String getOrgValue() {
        return orgValue;
    }

    public void setOrgValue(String orgValue) {
        this.orgValue = orgValue;
    }

    @Column(length = 50)
    public String getFuncValue() {
        return funcValue;
    }

    public void setFuncValue(String funcValue) {
        this.funcValue = funcValue;
    }

    @Column(length = 20)
    public Integer getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(Integer timeValue) {
        this.timeValue = timeValue;
    }

    @Column(length = 10)
    public Integer getSerialValue() {
        return serialValue;
    }

    public void setSerialValue(Integer serialValue) {
        this.serialValue = serialValue;
    }

    @Column(length = 200)
    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }
}
