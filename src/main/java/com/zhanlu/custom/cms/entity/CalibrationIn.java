package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.IdEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 月度内校
 */
@Entity
@Table(name = "cms_calibration_in")
public class CalibrationIn extends IdEntity {

    //租户ID
    private Long tenantId;
    //器具
    private Long equipmentId;
    private Equipment equipment;
    //上次待校准时间
    private Date lastExpectDate;
    //上次实际校准时间
    private Date lastActualDate;
    //待校准时间
    private Date expectDate;
    //实际校准时间
    private Date actualDate;
    //记录/证书编号
    private String certCode;
    //校准结果
    private String calibrationResult;
    //备注
    private String remark;
    //附件路径
    private String filePath;

    //创建者ID、创建时间、修改者ID、修改时间
    private Long createrId;
    private Date createTime;
    private Long updaterId;
    private Date updateTime;
    //任务ID
    private Long taskId;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "actual_date")
    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    @Column(name = "cert_code", length = 20)
    public String getCertCode() {
        return certCode;
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode;
    }

    @Column(name = "calibration_result", length = 10)
    public String getCalibrationResult() {
        return calibrationResult;
    }

    public void setCalibrationResult(String calibrationResult) {
        this.calibrationResult = calibrationResult;
    }

    @Column(name = "remark", length = 50)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "file_path", length = 100)
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    @Column(name = "task_id")
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
