package com.zhanlu.bd.model;

import com.zhanlu.framework.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 质量标准
 */
@Entity
@Table(name = "bd_qs")
public class Qs extends IdEntity {

    private String code; //质量标准编号
    private String name; //质量标准版本
    private String ver; //质量标准版本
    private String materielCode; //物料产品名称
    private String materielName; //物料产品代码

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

    @Column(length = 20)
    public String getMaterielCode() {
        return materielCode;
    }

    public void setMaterielCode(String materielCode) {
        this.materielCode = materielCode;
    }

    @Column(length = 50)
    public String getMaterielName() {
        return materielName;
    }

    public void setMaterielName(String materielName) {
        this.materielName = materielName;
    }
}
