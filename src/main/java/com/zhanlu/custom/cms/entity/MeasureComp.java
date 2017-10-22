package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.CodeEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 计量公司
 */
@Entity
@Table(name = "cms_measure_comp")
public class MeasureComp extends CodeEntity {

    //信用代码
    private String creditCode;
    //企业简称
    private String enterpriseShort;
    //地址
    private String addr;
    //网址
    private String url;
    //认可有效期
    private Date expireDate;
    //建标数量
    private Integer buildCount;
    //审计报告
    private String auditReport;

    @Column(name = "credit_code", length = 50)
    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
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

    @Column(name = "audit_report", length = 200)
    public String getAuditReport() {
        return auditReport;
    }

    public void setAuditReport(String auditReport) {
        this.auditReport = auditReport;
    }

    @Column(name = "build_count")
    public Integer getBuildCount() {
        return buildCount;
    }

    public void setBuildCount(Integer buildCount) {
        this.buildCount = buildCount;
    }

    @Column(name = "enterprise_short", length = 50)
    public String getEnterpriseShort() {
        return enterpriseShort;
    }

    public void setEnterpriseShort(String enterpriseShort) {
        this.enterpriseShort = enterpriseShort;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "expire_date")
    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}
