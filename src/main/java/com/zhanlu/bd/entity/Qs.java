package com.zhanlu.bd.entity;

import com.zhanlu.framework.common.entity.CodeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 质量标准实体
 */
@Entity
@Table(name = "bd_qs")
public class Qs extends CodeEntity {

    //质量标准版本
    private String ver;
    //物料产品名称
    private String materielCode;
    //物料产品代码
    private String materielName;

    @Column(name = "ver_", length = 10)
    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    @Column(name = "materiel_code", length = 20)
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
}
