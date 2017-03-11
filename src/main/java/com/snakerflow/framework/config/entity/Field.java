package com.snakerflow.framework.config.entity;

import com.snakerflow.common.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 表单字段实体类
 *
 * @author yuqs
 * @since 0.1
 */
@Entity
@Table(name = "DF_FIELD")
public class Field extends IdEntity {

    private static final long serialVersionUID = -1;

    public static final String FLOW = "1";
    private String name;
    private String plugins;
    private String title;
    private String type;
    private String flow;
    private String tableName;
    private long formId = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlugins() {
        return plugins;
    }

    public void setPlugins(String plugins) {
        this.plugins = plugins;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public long getFormId() {
        return formId;
    }

    public void setFormId(long formId) {
        this.formId = formId;
    }
}
