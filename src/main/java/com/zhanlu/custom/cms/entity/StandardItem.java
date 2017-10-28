package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.IdEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 标准项
 */
@Entity
@Table(name = "cms_standard_item")
public class StandardItem extends IdEntity {

    //标准项编号
    private String code;
    //标准项名称
    private String name;
    //校准参量
    private String param;
    //测量范围上限、测量范围下限
    private Integer measureRangeMin;
    private Integer measureRangeMax;
    //不确定度
    private String uncertainty;

    //创建者ID、创建时间、修改者ID、修改时间
    private Long createrId;
    private Date createTime;
    private Long updaterId;
    private Date updateTime;

    @Column(name = "code", length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "name", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    @Column(name = "creater_id")
    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "updater_id")
    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
