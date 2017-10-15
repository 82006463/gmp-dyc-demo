package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.CodeEntity;

import javax.persistence.*;

/**
 * 用户
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "measure_comp_id", insertable = false, updatable = false)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_comp_id", insertable = false, updatable = false)
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
