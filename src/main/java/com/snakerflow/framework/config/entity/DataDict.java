package com.snakerflow.framework.config.entity;

import com.snakerflow.common.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 配置字典实体类
 *
 * @author yuqs
 * @since 0.1
 */
@Entity
@Table(name = "wfc_data_dict")
public class DataDict extends IdEntity {

    private static final long serialVersionUID = -7610108657592274423L;

    //编号
    private String code;
    //名称
    private String name;
    //描述
    private String desc;
    //父ID
    private Long pid;
    //父编号
    private String pcode;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }
}
