package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.IdEntity;
import com.zhanlu.framework.security.entity.Role;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 通知
 */
@Entity
@Table(name = "cms_comp_notify")
public class CompNotify extends IdEntity {

    //接收人
    private Long roleId;
    private Role role;
    //通知标题
    private String subject;
    //通知内容
    private String content;

    //系统版本
    private String sysVer;
    //模块，模块版本
    private String module;
    private String moduleVer;
    //发送时间
    private Date sendTime;

    //创建者ID、创建时间、修改者ID、修改时间
    private Long createrId;
    private Date createTime;
    private Long updaterId;
    private Date updateTime;

    @Column(name = "role_id")
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Column(name = "subject", length = 100)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column(name = "content", length = 500)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "sys_ver", length = 20)
    public String getSysVer() {
        return sysVer;
    }

    public void setSysVer(String sysVer) {
        this.sysVer = sysVer;
    }

    @Column(name = "module", length = 20)
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Column(name = "module_ver", length = 20)
    public String getModuleVer() {
        return moduleVer;
    }

    public void setModuleVer(String moduleVer) {
        this.moduleVer = moduleVer;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "send_time")
    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
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
