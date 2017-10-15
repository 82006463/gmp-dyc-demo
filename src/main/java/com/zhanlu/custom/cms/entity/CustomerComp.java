package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.CodeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/10/15.
 */
@Entity
@Table(name = "cms_customer_comp")
public class CustomerComp extends CodeEntity {

    //信用代码
    @Column(name = "credit_code", length = 50)
    private String creditCode;
    //网址
    @Column(name = "url", length = 50)
    private String url;
    //地址
    @Column(name = "addr", length = 100)
    private String addr;
}
