package com.zhanlu.report.entity;

import com.zhanlu.framework.common.entity.IdEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 报表
 *
 * @author zhanlu
 * @date 2017-03-11
 * @since 0.1
 */
@Entity
@Table(name = "dyc_report")
public class DycReport extends IdEntity {

    private String processType; //流程类型
    private String processNo; //流程编号
    private String processName; //流程名称
    private String processDesc;//描述

    private Long orgId; //部门
    private String orgName;
    private String level; //级别
    private Date occurTime;//发现时间
    private Date closeTime; //流程关闭时间
    private String occurPerson;//发现人
    private Date startTime; //流程开始时间
    private Date endTime; //流程结束时间

    private String materielCode;//物料编号
    private String materielName;//物料名称
    private String extraJson; //流程额外的非键数据

    @Column(name = "process_type", length = 10)
    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    @Column(name = "process_no", length = 20)
    public String getProcessNo() {
        return processNo;
    }

    public void setProcessNo(String processNo) {
        this.processNo = processNo;
    }

    @Column(name = "process_name", length = 50)
    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    @Column(name = "level", length = 20)
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Column(name = "occur_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    @Column(name = "start_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name = "extra_json", length = 500)
    public String getExtraJson() {
        return extraJson;
    }

    public void setExtraJson(String extraJson) {
        this.extraJson = extraJson;
    }

    @Column(name = "process_desc", length = 200)
    public String getProcessDesc() {
        return processDesc;
    }

    public void setProcessDesc(String processDesc) {
        this.processDesc = processDesc;
    }

    @Column(name = "occur_person", length = 20)
    public String getOccurPerson() {
        return occurPerson;
    }

    public void setOccurPerson(String occurPerson) {
        this.occurPerson = occurPerson;
    }

    @Column(name = "materiel_code", length = 50)
    public String getMaterielCode() {
        return materielCode;
    }

    public void setMaterielCode(String materielCode) {
        this.materielCode = materielCode;
    }

    @Column(name = "materiel_name", length = 100)
    public String getMaterielName() {
        return materielName;
    }

    public void setMaterielName(String materielName) {
        this.materielName = materielName;
    }

    @Column(name = "close_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    @Column(name = "org_id")
    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    @Column(name = "org_name", length = 100)
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
