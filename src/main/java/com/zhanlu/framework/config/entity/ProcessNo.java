package com.zhanlu.framework.config.entity;

import com.zhanlu.framework.common.entity.CodeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "wfc_process_no")
public class ProcessNo extends CodeEntity {

    //流程前缀
    private String prefix;
    //时间模式：yyyy | yyyyMM | yyyyMMdd
    private String timePattern;
    //时间模式下的当前时间值
    private Integer timeValue;
    //流水号长度
    private Integer indexLength;
    //当前流水号
    private Integer indexValue;
    //当前值的编码规则：年+月+日+部门编号+流水号
    private String currentValue;
    //是否使用部门编号：1：使用,0：不使用
    private Integer orgState;
    //部门ID、编号
    private Long orgId;
    private String orgCode;

    @Column(length = 5)
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Column(length = 10)
    public Integer getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(Integer indexValue) {
        this.indexValue = indexValue;
    }

    @Column(length = 6)
    public Integer getIndexLength() {
        return indexLength;
    }

    public void setIndexLength(Integer indexLength) {
        this.indexLength = indexLength;
    }


    @Column(length = 10)
    public String getTimePattern() {
        return timePattern;
    }

    public void setTimePattern(String timePattern) {
        this.timePattern = timePattern;
    }

    public Integer getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(Integer timeValue) {
        this.timeValue = timeValue;
    }

    @Column(length = 30)
    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    @Column(name = "org_id")
    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    @Column(name = "org_code", length = 20)
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Column(name = "org_state", length = 2)
    public Integer getOrgState() {
        return orgState;
    }

    public void setOrgState(Integer orgState) {
        this.orgState = orgState;
    }
}
