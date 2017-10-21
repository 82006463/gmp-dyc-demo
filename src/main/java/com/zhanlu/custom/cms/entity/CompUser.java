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
    private Long drugCompId;
    private DrugComp drugComp;

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

    @Column(name = "drug_comp_id")
    public Long getDrugCompId() {
        return drugCompId;
    }

    public void setDrugCompId(Long drugCompId) {
        this.drugCompId = drugCompId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_comp_id", insertable = false, updatable = false)
    public DrugComp getDrugComp() {
        return drugComp;
    }

    public void setDrugComp(DrugComp drugComp) {
        this.drugComp = drugComp;
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
