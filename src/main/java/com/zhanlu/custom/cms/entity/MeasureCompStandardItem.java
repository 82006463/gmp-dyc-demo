package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 计量公司
 */
@Entity
@Table(name = "cms_measure_comp_standrard_item")
public class MeasureCompStandardItem extends IdEntity {

    private Long measureCompId;
    private Long standrardItemId;

    @Column(name = "measure_comp_id")
    public Long getMeasureCompId() {
        return measureCompId;
    }

    public void setMeasureCompId(Long measureCompId) {
        this.measureCompId = measureCompId;
    }

    @Column(name = "standrard_item_id")
    public Long getStandrardItemId() {
        return standrardItemId;
    }

    public void setStandrardItemId(Long standrardItemId) {
        this.standrardItemId = standrardItemId;
    }
}
