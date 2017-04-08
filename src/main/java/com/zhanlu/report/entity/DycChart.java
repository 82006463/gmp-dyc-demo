package com.zhanlu.report.entity;

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
public class DycChart extends IdEntity {

    private String type; //流程类型
    private String chartNo; //流程编号
    private String chartName; //流程名称
    private String chartDesc;//描述

    //显示类型
    private String view;

    @Column(name = "type_", length = 10)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "chart_no", length = 50)
    public String getChartNo() {
        return chartNo;
    }

    public void setChartNo(String chartNo) {
        this.chartNo = chartNo;
    }

    @Column(name = "chart_name", length = 100)
    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    @Column(name = "chart_desc", length = 200)
    public String getChartDesc() {
        return chartDesc;
    }

    public void setChartDesc(String chartDesc) {
        this.chartDesc = chartDesc;
    }

    @Transient
    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
