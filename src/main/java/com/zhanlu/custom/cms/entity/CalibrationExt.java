package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.IdEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 月度外校
 */
@Entity
@Table(name = "cms_calibration_ext")
public class CalibrationExt extends IdEntity {

    //器具
    private Long equipmentId;
    private Equipment equipment;
    //待校准时间
    private Date expectCalibrationDate;
    //实际校准时间
    private Date actualCalibrationDate;
    //校准结果
    private String calibrationResult;

    @Column(name = "equipment_id")
    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", insertable = false, updatable = false)
    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "expect_calibration_date")
    public Date getExpectCalibrationDate() {
        return expectCalibrationDate;
    }

    public void setExpectCalibrationDate(Date expectCalibrationDate) {
        this.expectCalibrationDate = expectCalibrationDate;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "actual_calibration_date")
    public Date getActualCalibrationDate() {
        return actualCalibrationDate;
    }

    public void setActualCalibrationDate(Date actualCalibrationDate) {
        this.actualCalibrationDate = actualCalibrationDate;
    }

    @Column(name = "calibration_result", length = 10)
    public String getCalibrationResult() {
        return calibrationResult;
    }

    public void setCalibrationResult(String calibrationResult) {
        this.calibrationResult = calibrationResult;
    }
}
