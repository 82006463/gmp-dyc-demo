package com.zhanlu.custom.dm.entity;

import com.zhanlu.framework.common.entity.CodeEntity;
import com.zhanlu.framework.security.entity.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 服务端设置
 */
@Entity
@Table(name = "server_setting")
public class ServerSetting extends CodeEntity {

    //租户ID
    private Long tenantId;
    //自动对时状态：0:关闭, 1:开启
    private Integer autoTimeStatus;
    //自动对时频次：每天、每周一、每月一号
    private String autoTimeCron;

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

    @Column(name = "auto_time_status")
    public Integer getAutoTimeStatus() {
        return autoTimeStatus;
    }

    public void setAutoTimeCron(String autoTimeCron) {
        this.autoTimeCron = autoTimeCron;
    }

    @Column(name = "auto_time_cron", length = 50)
    public String getAutoTimeCron() {
        return autoTimeCron;
    }

    public void setAutoTimeStatus(Integer autoTimeStatus) {
        this.autoTimeStatus = autoTimeStatus;
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
