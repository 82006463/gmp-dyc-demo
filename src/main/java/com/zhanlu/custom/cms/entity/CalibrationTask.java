package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.IdEntity;
import com.zhanlu.framework.security.entity.Org;
import com.zhanlu.framework.security.entity.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 待办任务
 */
@Entity
@Table(name = "cms_calibration_task")
public class CalibrationTask extends IdEntity {

    //租户ID
    private Long tenantId;
    //任务编号：企业编号+yyMMddHHmmss
    private String taskCode;
    //计量公司
    private Long measureCompId;
    private MeasureComp measureComp;
    //药企
    private Long drugCompId;
    private Org drugComp;
    //校准方式：1:内校, 2:外校, 3:临校
    private Integer calibrationMode;
    //审核人
    private String approver;
    private User user;
    //复核人
    private String reviewer;
    //当前处理人
    private String current;

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

    @Column(name = "task_code", length = 30)
    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    @Column(name = "measure_comp_id")
    public Long getMeasureCompId() {
        return measureCompId;
    }

    public void setMeasureCompId(Long measureCompId) {
        this.measureCompId = measureCompId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "measure_comp_id", insertable = false, updatable = false)
    public MeasureComp getMeasureComp() {
        return measureComp;
    }

    public void setMeasureComp(MeasureComp measureComp) {
        this.measureComp = measureComp;
    }

    @Column(name = "drug_comp_id")
    public Long getDrugCompId() {
        return drugCompId;
    }

    public void setDrugCompId(Long drugCompId) {
        this.drugCompId = drugCompId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_comp_id", insertable = false, updatable = false)
    public Org getDrugComp() {
        return drugComp;
    }

    public void setDrugComp(Org drugComp) {
        this.drugComp = drugComp;
    }

    @Column(name = "calibration_mode")
    public Integer getCalibrationMode() {
        return calibrationMode;
    }

    public void setCalibrationMode(Integer calibrationMode) {
        this.calibrationMode = calibrationMode;
    }

    @Column(name = "approver", length = 20)
    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver", referencedColumnName = "username", insertable = false, updatable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "reviewer", length = 20)
    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    @Column(name = "current", length = 20)
    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
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
