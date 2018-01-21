package com.zhanlu.custom.dm.entity;

import com.zhanlu.framework.common.entity.CodeEntity;
import com.zhanlu.framework.security.entity.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 备份历史
 */
@Entity
@Table(name = "dm_backup_hist")
public class BackupHist extends CodeEntity {

    //租户ID
    private Long tenantId;
    //备份类型：1:文档备份, 2:数据库备份
    private Integer bakType;
    //备份开始时间
    private Date beginTime;
    //备份结束时间
    private Date endTime;
    //操作方式：1:自动备份, 2:手动备份
    private Integer opMode;
    //有无更新：1:有更新, 0:无更新
    private Integer updateStatus;
    //成功数量
    private Integer successCount;
    //失败数量
    private Integer failCount;
    private Long bakObjId;

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

    @Column(name = "bak_type")
    public Integer getBakType() {
        return bakType;
    }

    public void setBakType(Integer bakType) {
        this.bakType = bakType;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "begin_time")
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name = "op_mode")
    public Integer getOpMode() {
        return opMode;
    }

    public void setOpMode(Integer opMode) {
        this.opMode = opMode;
    }

    @Column(name = "update_status")
    public Integer getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Integer updateStatus) {
        this.updateStatus = updateStatus;
    }

    @Column(name = "success_count")
    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    @Column(name = "fail_count")
    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    @Column(name = "bak_obj_id")
    public Long getBakObjId() {
        return bakObjId;
    }

    public void setBakObjId(Long bakObjId) {
        this.bakObjId = bakObjId;
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
