package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 标准项
 */
@Entity
@Table(name = "cms_standard_item")
public class StandardItem extends IdEntity {

    //校准参量
    private String param;
    //测量范围
    private Integer measureRangeMin;
    private Integer measureRangeMax;
    //不确定度
    private String uncertainty;

    @Column(name = "param", length = 50)
    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Column(name = "measure_range_min")
    public Integer getMeasureRangeMin() {
        return measureRangeMin;
    }

    public void setMeasureRangeMin(Integer measureRangeMin) {
        this.measureRangeMin = measureRangeMin;
    }

    @Column(name = "measure_range_max")
    public Integer getMeasureRangeMax() {
        return measureRangeMax;
    }

    public void setMeasureRangeMax(Integer measureRangeMax) {
        this.measureRangeMax = measureRangeMax;
    }

    @Column(name = "uncertainty", length = 50)
    public String getUncertainty() {
        return uncertainty;
    }

    public void setUncertainty(String uncertainty) {
        this.uncertainty = uncertainty;
    }

}
