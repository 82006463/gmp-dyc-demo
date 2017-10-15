package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.CodeEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/10/15.
 */
@Entity
@Table(name = "cms_customer_comp")
public class CustomerComp extends CodeEntity {

    //信用代码
    private String creditCode;
    //网址
    private String url;
    //地址
    private String addr;
}
