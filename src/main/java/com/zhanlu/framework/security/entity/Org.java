package com.zhanlu.framework.security.entity;

import com.zhanlu.framework.common.entity.TreeEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 部门实体类，继承抽象安全实体类
 *
 * @author yuqs
 * @since 0.1
 */
@Entity
@Table(name = "SEC_ORG")
public class Org extends TreeEntity {

    private static final long serialVersionUID = 7297765946510001885L;

    public Org() {

    }

    public Org(Long id) {
        super.id = id;
    }

    //备用字段
    private String strField1;
    private String strField2;
    private String strField3;
    private String strField4;
    private String strField5;
    private Integer intField1;
    private Integer intField2;
    private Date dateField1;
    private Date dateField2;
    private Date timestampField1;
    private Date timestampField2;


    @Column(name = "str_field1", length = 50)
    public String getStrField1() {
        return strField1;
    }

    public void setStrField1(String strField1) {
        this.strField1 = strField1;
    }

    @Column(name = "str_field2", length = 50)
    public String getStrField2() {
        return strField2;
    }

    public void setStrField2(String strField2) {
        this.strField2 = strField2;
    }

    @Column(name = "str_field3", length = 50)
    public String getStrField3() {
        return strField3;
    }

    public void setStrField3(String strField3) {
        this.strField3 = strField3;
    }

    @Column(name = "str_field4", length = 100)
    public String getStrField4() {
        return strField4;
    }

    public void setStrField4(String strField4) {
        this.strField4 = strField4;
    }

    @Column(name = "str_field5", length = 100)
    public String getStrField5() {
        return strField5;
    }

    public void setStrField5(String strField5) {
        this.strField5 = strField5;
    }

    @Column(name = "int_field1")
    public Integer getIntField1() {
        return intField1;
    }

    public void setIntField1(Integer intField1) {
        this.intField1 = intField1;
    }

    @Column(name = "int_field2")
    public Integer getIntField2() {
        return intField2;
    }

    public void setIntField2(Integer intField2) {
        this.intField2 = intField2;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_field1")
    public Date getDateField1() {
        return dateField1;
    }

    public void setDateField1(Date dateField1) {
        this.dateField1 = dateField1;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_field2")
    public Date getDateField2() {
        return dateField2;
    }

    public void setDateField2(Date dateField2) {
        this.dateField2 = dateField2;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "timestamp_field1")
    public Date getTimestampField1() {
        return timestampField1;
    }

    public void setTimestampField1(Date timestampField1) {
        this.timestampField1 = timestampField1;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "timestamp_field2")
    public Date getTimestampField2() {
        return timestampField2;
    }

    public void setTimestampField2(Date timestampField2) {
        this.timestampField2 = timestampField2;
    }

}
