package com.zhanlu.report.entity;

import com.zhanlu.framework.common.entity.CodeEntity;
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
public class DycReport extends CodeEntity {

    //流程通用属性
    private String processType; //流程类型
    private Long orgId; //部门
    private String orgName;
    private String level; //级别
    private String occurPerson;//发现人
    private Date occurTime;//发现时间
    private Date startTime; //流程开始时间
    private Date closeTime; //流程关闭时间
    private Date endTime; //流程结束时间

    //备用属性：具体语义在伸缩表配置时定义
    private String type1;//备用类型
    private String type2;
    private String str21;
    private String str22;
    private String str23;
    private String str24;
    private String str25;
    private String str51;
    private String str52;
    private String str53;
    private String str54;
    private String str55;

    private Integer num1;
    private Integer num2;
    private Integer num3;
    private Integer num4;
    private Integer num5;
    private Long num21;
    private Long num22;

    private Date date1;
    private Date date2;
    private Date date3;
    private Date date4;
    private Date date5;
    private Date time1;
    private Date time2;
    private Date time3;

    private String extraJson; //流程额外的非键数据

    @Column(name = "process_type", length = 10)
    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
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

    @Column(name = "extra_json", length = 2000)
    public String getExtraJson() {
        return extraJson;
    }

    public void setExtraJson(String extraJson) {
        this.extraJson = extraJson;
    }

    @Column(name = "occur_person", length = 20)
    public String getOccurPerson() {
        return occurPerson;
    }

    public void setOccurPerson(String occurPerson) {
        this.occurPerson = occurPerson;
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

    @Column(name = "type1", length = 20)
    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    @Column(name = "type2", length = 20)
    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    @Column(name = "date1")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    @Column(name = "date2")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    @Column(name = "date3")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getDate3() {
        return date3;
    }

    public void setDate3(Date date3) {
        this.date3 = date3;
    }

    @Column(name = "date4")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getDate4() {
        return date4;
    }

    public void setDate4(Date date4) {
        this.date4 = date4;
    }

    @Column(name = "date5")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getDate5() {
        return date5;
    }

    public void setDate5(Date date5) {
        this.date5 = date5;
    }

    @Column(name = "time1")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getTime1() {
        return time1;
    }

    public void setTime1(Date time1) {
        this.time1 = time1;
    }

    @Column(name = "time2")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getTime2() {
        return time2;
    }

    public void setTime2(Date time2) {
        this.time2 = time2;
    }

    @Column(name = "time3")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getTime3() {
        return time3;
    }

    public void setTime3(Date time3) {
        this.time3 = time3;
    }

    @Column(name = "str21",length = 20)
    public String getStr21() {
        return str21;
    }

    public void setStr21(String str21) {
        this.str21 = str21;
    }

    @Column(name = "str22",length = 20)
    public String getStr22() {
        return str22;
    }

    public void setStr22(String str22) {
        this.str22 = str22;
    }

    @Column(name = "str23",length = 20)
    public String getStr23() {
        return str23;
    }

    public void setStr23(String str23) {
        this.str23 = str23;
    }

    @Column(name = "str24",length = 20)
    public String getStr24() {
        return str24;
    }

    public void setStr24(String str24) {
        this.str24 = str24;
    }

    @Column(name = "str25",length = 20)
    public String getStr25() {
        return str25;
    }

    public void setStr25(String str25) {
        this.str25 = str25;
    }

    @Column(name = "str51", length = 50)
    public String getStr51() {
        return str51;
    }

    public void setStr51(String str51) {
        this.str51 = str51;
    }

    @Column(name = "str52",length = 50)
    public String getStr52() {
        return str52;
    }

    public void setStr52(String str52) {
        this.str52 = str52;
    }

    @Column(name = "str53",length = 50)
    public String getStr53() {
        return str53;
    }

    public void setStr53(String str53) {
        this.str53 = str53;
    }

    @Column(name = "str54",length = 50)
    public String getStr54() {
        return str54;
    }

    public void setStr54(String str54) {
        this.str54 = str54;
    }

    @Column(name = "str55",length = 50)
    public String getStr55() {
        return str55;
    }

    public void setStr55(String str55) {
        this.str55 = str55;
    }

    @Column(name = "num1")
    public Integer getNum1() {
        return num1;
    }

    public void setNum1(Integer num1) {
        this.num1 = num1;
    }

    @Column(name = "num2")
    public Integer getNum2() {
        return num2;
    }

    public void setNum2(Integer num2) {
        this.num2 = num2;
    }

    @Column(name = "num3")
    public Integer getNum3() {
        return num3;
    }

    public void setNum3(Integer num3) {
        this.num3 = num3;
    }

    @Column(name = "num4")
    public Integer getNum4() {
        return num4;
    }

    public void setNum4(Integer num4) {
        this.num4 = num4;
    }

    @Column(name = "num5")
    public Integer getNum5() {
        return num5;
    }

    public void setNum5(Integer num5) {
        this.num5 = num5;
    }

    @Column(name = "num21")
    public Long getNum21() {
        return num21;
    }

    public void setNum21(Long num21) {
        this.num21 = num21;
    }

    @Column(name = "num22")
    public Long getNum22() {
        return num22;
    }

    public void setNum22(Long num22) {
        this.num22 = num22;
    }
}
