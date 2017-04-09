package com.zhanlu.framework.config.entity;

import com.zhanlu.framework.common.entity.CodeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "wfc_elastic_table")
public class ElasticTable extends CodeEntity {

    private String jsonSearch; //列表页面的搜索属性：JSON
    private String jsonList; //列表页面的显示属性：JSON
    private String jsonEdit; //编辑页面：JSON

    @Column(name = "json_search", length = 600)
    public String getJsonSearch() {
        if (jsonSearch == null || jsonSearch.trim().isEmpty()) {
            jsonSearch = "[]";
        }
        return jsonSearch;
    }

    public void setJsonSearch(String jsonSearch) {
        this.jsonSearch = jsonSearch;
    }

    @Column(name = "json_list", length = 800)
    public String getJsonList() {
        if (jsonList == null || jsonList.trim().isEmpty()) {
            jsonList = "[]";
        }
        return jsonList;
    }

    public void setJsonList(String jsonList) {
        this.jsonList = jsonList;
    }

    @Lob
    @Column(name = "json_edit")
    public String getJsonEdit() {
        if (jsonEdit == null || jsonEdit.trim().isEmpty()) {
            jsonEdit = "[]";
        }
        return jsonEdit;
    }

    public void setJsonEdit(String jsonEdit) {
        this.jsonEdit = jsonEdit;
    }

}
