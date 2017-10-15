package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.CodeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Administrator on 2017/10/15.
 */
@Entity
@Table(name = "cms_measure_comp")
public class MeasureComp extends CodeEntity {

    //信用代码
    @Column(name = "credit_code", length = 50)
    private String creditCode;
    //网址
    @Column(name = "url", length = 50)
    private String url;
    //地址
    @Column(name = "addr", length = 100)
    private String addr;
    //审计报告
    @Column(name = "audit_report", length = 200)
    private String auditReport;
    //建标数量
    @Column(name = "build_count")
    private Integer buildCount;
    //企业简称
    @Column(name = "enterprise_short", length = 50)
    private String enterpriseShort;
    //认可有效期
    @Column(name = "expire_date")
    private Date expireDate;

}
