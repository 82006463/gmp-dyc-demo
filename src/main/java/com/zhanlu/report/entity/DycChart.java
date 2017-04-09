package com.zhanlu.report.entity;

import com.zhanlu.framework.common.entity.CodeEntity;
import com.zhanlu.framework.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 图表
 *
 * @author zhanlu
 * @date 2017-03-11
 * @since 0.1
 */
@Entity
@Table(name = "dyc_chart")
public class DycChart extends CodeEntity {

    private String type; //流程类型

    //显示类型
    private String view;

    @Column(name = "type_", length = 10)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Transient
    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
