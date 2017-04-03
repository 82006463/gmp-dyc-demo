package com.zhanlu.framework.config.entity;

import com.zhanlu.framework.common.entity.CodeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "wfc_elastic_table")
public class ElasticTable extends CodeEntity {

    //报表的扩展属性：存为JSON
    private String jsonStruct;

    @Column(name = "json_struct", length = 1500)
    public String getJsonStruct() {
        if (jsonStruct == null || jsonStruct.trim().isEmpty()) {
            jsonStruct = "{}";
        }
        return jsonStruct;
    }

    public void setJsonStruct(String jsonStruct) {
        this.jsonStruct = jsonStruct;
    }
}
