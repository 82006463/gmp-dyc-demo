package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.IdEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 校准历史
 */
@Entity
@Table(name = "cms_calibration_hist")
public class CalibrationHist extends IdEntity {

    //器具
    private Long equipmentId;
    private Equipment equipment;
    //校准方式：1:内校, 2:外校, 3:临校
    private Integer calibrationMode;
    //待校准时间
    private Date expectDate;
    //实际校准时间
    private Date actualDate;
    //校准结果
    private String calibrationResult;
    //校准证书编号
    private String certCode;

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

    @Column(name = "calibration_mode")
    public Integer getCalibrationMode() {
        return calibrationMode;
    }

    public void setCalibrationMode(Integer calibrationMode) {
        this.calibrationMode = calibrationMode;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "expect_calibration_date")
    public Date getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(Date expectDate) {
        this.expectDate = expectDate;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "actual_calibration_date")
    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    @Column(name = "calibration_result", length = 10)
    public String getCalibrationResult() {
        return calibrationResult;
    }

    public void setCalibrationResult(String calibrationResult) {
        this.calibrationResult = calibrationResult;
    }

    @Column(name = "cert_code", length = 50)
    public String getCertCode() {
        return certCode;
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode;
    }
}
