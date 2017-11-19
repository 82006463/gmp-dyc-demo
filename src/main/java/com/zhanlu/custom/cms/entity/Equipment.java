package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.IdEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

/**
 * 器具
 */
@Entity
@Table(name = "cms_equipment")
public class Equipment extends IdEntity {

    //租户ID
    private Long tenantId;
    //器具编号
    private String code;
    //器具名称
    private String name;
    //型号
    private String model;
    //生产厂家
    private String factory;
    //出厂编号
    private String factoryCode;
    //所在房间
    private String room;
    //所属设备
    private String equipment;
    //功能
    private String func;
    //精度
    private String precision;

    //校准
    private String calibration;
    //校准名称
    private String calibrationName;
    //校准方式：1:内校, 2:外校
    private Integer calibrationMode;
    //临时校准的辅助状态
    private Integer tmpStatus;
    //校准周期：单位月
    private Integer calibrationCycle;
    //测量范围上限、测量范围下限
    private Integer measureRangeMin;
    private Integer measureRangeMax;
    //上次校准时间
    private Date lastExpectDate;
    //上次校准时间
    private Date lastActualDate;
    //待校准时间
    private Date expectDate;

    //使用方式:常规，替换, 如果是替换，那他的待校准日期就是加双倍的周期
    private Integer usageMode;
    //级别
    private String level;
    //所属部门
    private String deptName;
    //车间
    private String workshop;
    //厂区
    private String factoryArea;
    //校准公司
    private String calibrationComp;
    //校准联系人
    private String calibrationOwner;

    //创建者ID、创建时间、修改者ID、修改时间
    private Long createrId;
    private Date createTime;
    private Long updaterId;
    private Date updateTime;

    @Column(name = "tenant_id")
    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

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

    @Column(name = "model", length = 50)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(name = "factory_code", length = 50)
    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    @Column(name = "room", length = 50)
    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Column(name = "equipment", length = 50)
    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    @Column(name = "func", length = 50)
    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    @Column(name = "precision_", length = 20)
    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    @Column(name = "calibration", length = 50)
    public String getCalibration() {
        return calibration;
    }

    public void setCalibration(String calibration) {
        this.calibration = calibration;
    }

    @Column(name = "calibration_name", length = 50)
    public String getCalibrationName() {
        return calibrationName;
    }

    public void setCalibrationName(String calibrationName) {
        this.calibrationName = calibrationName;
    }

    @Column(name = "calibration_mode")
    public Integer getCalibrationMode() {
        return calibrationMode;
    }

    public void setCalibrationMode(Integer calibrationMode) {
        this.calibrationMode = calibrationMode;
    }

    @Column(name = "tmp_status")
    public Integer getTmpStatus() {
        return tmpStatus;
    }

    public void setTmpStatus(Integer tmpStatus) {
        this.tmpStatus = tmpStatus;
    }

    @Column(name = "calibration_cycle")
    public Integer getCalibrationCycle() {
        return calibrationCycle;
    }

    public void setCalibrationCycle(Integer calibrationCycle) {
        this.calibrationCycle = calibrationCycle;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "last_expect_date")
    public Date getLastExpectDate() {
        return lastExpectDate;
    }

    public void setLastExpectDate(Date lastExpectDate) {
        this.lastExpectDate = lastExpectDate;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "last_actual_date")
    public Date getLastActualDate() {
        return lastActualDate;
    }

    public void setLastActualDate(Date lastActualDate) {
        this.lastActualDate = lastActualDate;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "expect_date")
    public Date getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(Date expectDate) {
        this.expectDate = expectDate;
    }

    @Column(name = "factory", length = 50)
    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    @Column(name = "usage_mode", length = 50)
    public Integer getUsageMode() {
        return usageMode;
    }

    public void setUsageMode(Integer usageMode) {
        this.usageMode = usageMode;
    }

    @Column(name = "level", length = 10)
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Column(name = "dept_name", length = 50)
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Column(name = "workshop", length = 50)
    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    @Column(name = "factory_area", length = 50)
    public String getFactoryArea() {
        return factoryArea;
    }

    public void setFactoryArea(String factoryArea) {
        this.factoryArea = factoryArea;
    }

    @Column(name = "calibration_comp", length = 50)
    public String getCalibrationComp() {
        return calibrationComp;
    }

    public void setCalibrationComp(String calibrationComp) {
        this.calibrationComp = calibrationComp;
    }

    @Column(name = "calibration_owner", length = 10)
    public String getCalibrationOwner() {
        return calibrationOwner;
    }

    public void setCalibrationOwner(String calibrationOwner) {
        this.calibrationOwner = calibrationOwner;
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
