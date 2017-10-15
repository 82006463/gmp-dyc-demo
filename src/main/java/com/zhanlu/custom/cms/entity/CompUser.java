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

    private Long measureCompId;
    private MeasureComp measureComp;
    private Long customerCompId;
    private CustomerComp customerComp;

    //岗位
    private String post;
    private String email;

    @Column(name = "measure_comp_id")
    public Long getMeasureCompId() {
        return measureCompId;
    }

    public void setMeasureCompId(Long measureCompId) {
        this.measureCompId = measureCompId;
    }

    @Transient
    public MeasureComp getMeasureComp() {
        return measureComp;
    }

    public void setMeasureComp(MeasureComp measureComp) {
        this.measureComp = measureComp;
    }

    @Column(name = "customer_comp_id")
    public Long getCustomerCompId() {
        return customerCompId;
    }

    public void setCustomerCompId(Long customerCompId) {
        this.customerCompId = customerCompId;
    }

    @Transient
    public CustomerComp getCustomerComp() {
        return customerComp;
    }

    public void setCustomerComp(CustomerComp customerComp) {
        this.customerComp = customerComp;
    }

    @Column(name = "post", length = 50)
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Column(name = "email", length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
