package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.CodeEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 药企
 */
@Entity
@Table(name = "cms_drug_comp")
public class DrugComp extends CodeEntity {

    //信用代码
    private String creditCode;
    //企业简称
    private String enterpriseShort;
    //地址
    private String addr;
    //网址
    private String url;

    //创建者ID、创建时间、修改者ID、修改时间
    private Long createrId;
    private Date createTime;
    private Long updaterId;
    private Date updateTime;

    @Column(name = "credit_code", length = 50)
    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    @Column(name = "enterprise_short", length = 50)
    public String getEnterpriseShort() {
        return enterpriseShort;
    }

    public void setEnterpriseShort(String enterpriseShort) {
        this.enterpriseShort = enterpriseShort;
    }

    @Column(name = "url", length = 50)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "addr", length = 100)
    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
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
