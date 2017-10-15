package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.CodeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by Administrator on 2017/10/15.
 */
@Entity
@Table(name = "cms_comp_user")
public class CompUser extends CodeEntity {

    @Column(name = "measure_comp_id")
    private Long measureCompId;
    @Transient
    private MeasureComp measureComp;
    @Column(name = "customer_comp_id")
    private Long customerCompId;
    @Transient
    private CustomerComp customerComp;

    //岗位
    @Column(name = "post", length = 50)
    private String post;
    @Column(name = "email", length = 50)
    private String email;
}
