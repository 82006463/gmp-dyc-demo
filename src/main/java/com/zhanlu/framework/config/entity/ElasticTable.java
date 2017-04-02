package com.zhanlu.framework.config.entity;

import com.zhanlu.framework.common.entity.CodeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "wfc_elastic_table")
public class ElasticTable extends CodeEntity {

    //报表的扩展属性：存为JSON
    private String extAttr;

    @Column(name = "ext_attr", length = 1500)
    public String getExtAttr() {
        return extAttr;
    }

    public void setExtAttr(String extAttr) {
        this.extAttr = extAttr;
    }
}
