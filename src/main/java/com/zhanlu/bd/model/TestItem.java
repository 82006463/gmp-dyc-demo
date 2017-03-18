package com.zhanlu.bd.model;

import com.zhanlu.framework.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 测试项
 */
@Entity
@Table(name = "bd_test_item")
public class TestItem extends IdEntity {

    private String code; //测试项编号：00001开始，自动+1
    private String name; //测试项名称
    private String ver; //测试项版本
    private String remark; //描述
    private String dataType; //数据类型
    private String operator; //比较符
    private String innerQs; //内控标准
    private String lawQs; //法定标准

    @Column(length = 20)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 10)
    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    @Column(length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(length = 10)
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Column(length = 5)
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Column(length = 20)
    public String getInnerQs() {
        return innerQs;
    }

    public void setInnerQs(String innerQs) {
        this.innerQs = innerQs;
    }

    @Column(length = 20)
    public String getLawQs() {
        return lawQs;
    }

    public void setLawQs(String lawQs) {
        this.lawQs = lawQs;
    }
}
