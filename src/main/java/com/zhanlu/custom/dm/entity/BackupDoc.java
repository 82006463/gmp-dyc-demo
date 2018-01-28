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
    //Cron表达式
    private String cron;
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

    @Column(name = "mac_addr", length = 50)
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

    @Column(name = "cron_", length = 50)
    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
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
