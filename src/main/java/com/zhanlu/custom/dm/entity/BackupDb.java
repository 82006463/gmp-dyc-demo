package com.zhanlu.custom.dm.entity;

import com.zhanlu.framework.common.entity.CodeEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 备份数据库
 */
@Entity
@Table(name = "dm_backup_db")
public class BackupDb extends CodeEntity {

    //租户ID
    private Long tenantId;
    //本机MAC地址：自动获取,不可修改
    private String macAddr;
    //本机机器名：自动获取,可修改
    private String host;
    //本机IP：自动获取,可修改
    private String ip;

    //备份源：sqlserver:SQL Server、mysql:MySQL、oracle:Oracle
    private String bakSource;
    //登录方式：1:windows账户验证, 2:数据库账号验证
    private Integer loginMode;
    //用户名
    private String loginUsername;
    //密码
    private String loginPwd;
    //数据库名
    private String dbName;
    //全量备份频次
    private String allCronExpr;
    //增量备份频次
    private String incrCronExpr;

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

    @Column(name = "login_mode")
    public Integer getLoginMode() {
        return loginMode;
    }

    public void setLoginMode(Integer loginMode) {
        this.loginMode = loginMode;
    }

    @Column(name = "login_username", length = 40)
    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    @Column(name = "password", length = 40)
    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    @Column(name = "db_name", length = 30)
    public String getDbName() {
        return dbName;
    }

    public void setDbName(String bakPath) {
        this.dbName = dbName;
    }

    @Column(name = "all_cron_expr", length = 50)
    public String getAllCronExpr() {
        return allCronExpr;
    }

    public void setAllCronExpr(String allCronExpr) {
        this.allCronExpr = allCronExpr;
    }

    @Column(name = "incr_cron_expr", length = 50)
    public String getIncrCronExpr() {
        return incrCronExpr;
    }

    public void setIncrCronExpr(String incrCronExpr) {
        this.incrCronExpr = incrCronExpr;
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
