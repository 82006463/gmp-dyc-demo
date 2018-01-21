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
    //文件版本
    private String fileVer;
    private String fileName;
    private String filePath;

    //创建者ID、创建时间、修改者ID、修改时间
    private Long createrId;
    private User creater;
    private Date createTime;
    private Long updaterId;
    private User updater;
    private Date updateTime;

    @Column(name = "tenant_id")
    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    @Column(name = "file_ver", length = 10)
    public String getFileVer() {
        return fileVer;
    }

    public void setFileVer(String fileVer) {
        this.fileVer = fileVer;
    }

    @Column(name = "file_name", length = 50)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name = "file_path", length = 150)
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

    @JoinColumn(name = "creater_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public User getCreater() {
        return creater;
    }

    public void setCreater(User creater) {
        this.creater = creater;
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

    @JoinColumn(name = "updater_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public User getUpdater() {
        return updater;
    }

    public void setUpdater(User updater) {
        this.updater = updater;
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
