package com.zhanlu.custom.dm.entity;

import com.zhanlu.framework.common.entity.CodeEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 备份详情
 */
@Entity
@Table(name = "dm_backup_detail")
public class BackupDetail extends CodeEntity {

    //租户ID
    private Long tenantId;
    //备份类型：1:file, 2:db
    private Integer bakType;
    //备份类型ID、名称
    private Long bakTypeId;
    private String bakTypeName;
    //文件源路径
    private String sourcePath;
    //备份路径
    private String targetPath;
    //源文件MD5
    private String sourceMd5;
    //备份件MD5
    private String targetMd5;
    //备份开始时间
    private Date beginTime;
    //备份结束时间
    private Date endTime;
    //Cron表达式
    private String cronExpr;
    //操作方式：1:自动备份, 2:手动备份
    private Integer backupMode;

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

    @Column(name = "bak_type_id")
    public Long getBakTypeId() {
        return bakTypeId;
    }

    public void setBakTypeId(Long bakTypeId) {
        this.bakTypeId = bakTypeId;
    }

    @Column(name = "bak_type_name", length = 50)
    public String getBakTypeName() {
        return bakTypeName;
    }

    public void setBakTypeName(String bakTypeName) {
        this.bakTypeName = bakTypeName;
    }

    @Column(name = "source_path", length = 260)
    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    @Column(name = "target_path", length = 260)
    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    @Column(name = "source_md5", length = 130)
    public String getSourceMd5() {
        return sourceMd5;
    }

    public void setSourceMd5(String sourceMd5) {
        this.sourceMd5 = sourceMd5;
    }

    @Column(name = "target_md5", length = 130)
    public String getTargetMd5() {
        return targetMd5;
    }

    public void setTargetMd5(String targetMd5) {
        this.targetMd5 = targetMd5;
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

    @Column(name = "cron_expr", length = 50)
    public String getCronExpr() {
        return cronExpr;
    }

    public void setCronExpr(String cronExpr) {
        this.cronExpr = cronExpr;
    }

    @Column(name = "backup_mode")
    public Integer getBackupMode() {
        return backupMode;
    }

    public void setBackupMode(Integer backupMode) {
        this.backupMode = backupMode;
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
