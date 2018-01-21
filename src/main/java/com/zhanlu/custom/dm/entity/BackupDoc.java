package com.zhanlu.custom.dm.entity;

import com.zhanlu.framework.common.entity.CodeEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 备份文档
 */
@Entity
@Table(name = "dm_backup_doc")
public class BackupDoc extends CodeEntity {

    //租户ID
    private Long tenantId;
    //本机MAC地址：自动获取，不可修改
    private String macAddr;
    //本机机器名：自动获取，可修改
    private String host;
    //本机IP：自动获取，可修改
    private String ip;

    //备份源：选择文件夹
    private String bakSource;
    //备份格式
    private String includeFormat;
    //排除备份格式
    private String excludeFormat;
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
    private Integer opMode;

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

    @Column(name = "mac_addr", length = 40)
    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    @Column(name = "host_", length = 30)
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Column(name = "ip_", length = 150)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Column(name = "bak_source", length = 120)
    public String getBakSource() {
        return bakSource;
    }

    public void setBakSource(String bakSource) {
        this.bakSource = bakSource;
    }

    @Column(name = "include_format", length = 40)
    public String getIncludeFormat() {
        return includeFormat;
    }

    public void setIncludeFormat(String includeFormat) {
        this.includeFormat = includeFormat;
    }

    @Column(name = "exclude_format", length = 40)
    public String getExcludeFormat() {
        return excludeFormat;
    }

    public void setExcludeFormat(String excludeFormat) {
        this.excludeFormat = excludeFormat;
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

    @Column(name = "op_mode")
    public Integer getOpMode() {
        return opMode;
    }

    public void setOpMode(Integer opMode) {
        this.opMode = opMode;
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
