package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.CodeEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户
 */
@Entity
@Table(name = "cms_comp_user")
public class CompUser extends CodeEntity {

    //公司类型：1:计量公司, 2:药企
    private Integer compType;
    private Long measureCompId;
    private MeasureComp measureComp;
    private Long drugCompId;
    private DrugComp drugComp;
    //岗位
    private String post;
    //邮箱
    private String email;

    //创建者ID、创建时间、修改者ID、修改时间
    private Long createrId;
    private Date createTime;
    private Long updaterId;
    private Date updateTime;

    @Column(name = "comp_type")
    public Integer getCompType() {
        return compType;
    }

    public void setCompType(Integer compType) {
        this.compType = compType;
    }

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

    @Column(name = "creater_id")
    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "updater_id")
    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
