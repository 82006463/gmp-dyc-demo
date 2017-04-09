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

    private Integer int1;
    private Integer int2;
    private Integer int3;
    private Integer int4;
    private Integer int5;

    private Long long1;
    private Long long2;
    private Long long3;

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

    @Column(name = "int1")
    public Integer getInt1() {
        return int1;
    }

    public void setInt1(Integer int1) {
        this.int1 = int1;
    }

    @Column(name = "int2")
    public Integer getInt2() {
        return int2;
    }

    public void setInt2(Integer int2) {
        this.int2 = int2;
    }

    @Column(name = "int3")
    public Integer getInt3() {
        return int3;
    }

    public void setInt3(Integer int3) {
        this.int3 = int3;
    }

    @Column(name = "int4")
    public Integer getInt4() {
        return int4;
    }

    public void setInt4(Integer int4) {
        this.int4 = int4;
    }

    @Column(name = "int5")
    public Integer getInt5() {
        return int5;
    }

    public void setInt5(Integer int5) {
        this.int5 = int5;
    }

    @Column(name = "long1")
    public Long getLong1() {
        return long1;
    }

    public void setLong1(Long long1) {
        this.long1 = long1;
    }

    @Column(name = "long2")
    public Long getLong2() {
        return long2;
    }

    public void setLong2(Long long2) {
        this.long2 = long2;
    }

    @Column(name = "long3")
    public Long getLong3() {
        return long3;
    }

    public void setLong3(Long long3) {
        this.long3 = long3;
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
}
