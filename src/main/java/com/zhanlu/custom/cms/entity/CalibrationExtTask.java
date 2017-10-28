package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.IdEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 月度外校任务
 */
@Entity
@Table(name = "cms_calibration_ext_task")
public class CalibrationExtTask extends IdEntity {

    //租户ID
    private Long tenantId;
    //器具
    private Long equipmentId;
    private Equipment equipment;
    //实际校准时间
    private Date actualDate;
    //校准结果
    private String calibrationResult;
    //审核人
    private String approver;

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

    @Column(name = "approver", length = 20)
    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
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
