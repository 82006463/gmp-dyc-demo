package com.zhanlu.bd.entity;

import com.zhanlu.framework.common.entity.CodeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 测试项实体
 */
@Entity
@Table(name = "bd_ti")
public class Ti extends CodeEntity {

    //测试项编号：00001开始，自动+1
    //测试项版本
    private String ver;
    //数据类型
    private String dataType;
    //比较符
    private String operator;
    //内控标准
    private String innerQs;
    //法定标准
    private String lawQs;

    @Column(name = "ver_", length = 10)
    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    @Column(name = "data_type", length = 10)
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Column(name = "operator", length = 5)
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Column(name = "inner_qs", length = 20)
    public String getInnerQs() {
        return innerQs;
    }

    public void setInnerQs(String innerQs) {
        this.innerQs = innerQs;
    }

    @Column(name = "law_qs", length = 20)
    public String getLawQs() {
        return lawQs;
    }

    public void setLawQs(String lawQs) {
        this.lawQs = lawQs;
    }
}
