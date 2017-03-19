package com.zhanlu.framework.common.entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity extends IdEntity {

    //编号
    protected String code;
    //名称
    protected String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
