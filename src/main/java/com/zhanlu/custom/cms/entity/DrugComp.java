package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.CodeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
}
