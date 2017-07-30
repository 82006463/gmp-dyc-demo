package com.zhanlu.framework.config.entity;

import com.zhanlu.framework.common.entity.CodeEntity;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;

/**
 * 由编号规则生成的值
 */
@Entity
@Table(name = "wfc_code_value")
public class CodeValue extends CodeEntity {

    //规则ID、编号
    private Long ruleId;
    private CodeRule rule;

    //部门编号
    private String orgCode;
    //功能编号
    private String funcCode;
    //时间模式下的当前时间值
    private Integer timeValue;
    //当前流水号
    private Integer serialValue;
    //当前值的编码规则：年+月+日+部门编号+流水号
    private String currentValue;

    @Column(name = "rule_id")
    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    @ManyToOne
    @JoinColumn(name = "rule_id")
    public CodeRule getRule() {
        return rule;
    }

    public void setRule(CodeRule rule) {
        this.rule = rule;
    }

    @Column(length = 20)
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Column(length = 20)
    public String getFuncCode() {
        return funcCode;
    }

    public void setFuncCode(String funcCode) {
        this.funcCode = funcCode;
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

    @Column(length = 100)
    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }
}
