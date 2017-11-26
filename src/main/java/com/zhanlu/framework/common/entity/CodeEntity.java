package com.zhanlu.framework.common.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 还编号属性的实体类
 */
@MappedSuperclass
public class CodeEntity extends IdEntity {

    //编号
    protected String code;
    //名称
    protected String name;
    //描述
    protected String remark;

    @Column(name = "code", length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "name", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "remark", length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
