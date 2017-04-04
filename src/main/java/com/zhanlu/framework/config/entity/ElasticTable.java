package com.zhanlu.framework.config.entity;

import com.zhanlu.framework.common.entity.CodeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "wfc_elastic_table")
public class ElasticTable extends CodeEntity {

    //报表的扩展属性：存为JSON
    private String jsonStruct;

    @Lob
    @Column(name = "json_struct")
    public String getJsonStruct() {
        if (jsonStruct == null || jsonStruct.trim().isEmpty()) {
            jsonStruct = "[]";
        }
        return jsonStruct;
    }

    public void setJsonStruct(String jsonStruct) {
        this.jsonStruct = jsonStruct;
    }
}
