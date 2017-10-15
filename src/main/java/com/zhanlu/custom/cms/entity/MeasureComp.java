package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.CodeEntity;

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
    private String creditCode;
    //网址
    private String url;
    //地址
    private String addr;
    //审计报告
    private String auditReport;
    //建标数量
    private Integer buildCount;
    //企业简称
    private String enterpriseShort;
    //认可有效期
    private Date expireDate;

}
