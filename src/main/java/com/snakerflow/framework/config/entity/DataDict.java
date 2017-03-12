package com.snakerflow.framework.config.entity;

import com.snakerflow.common.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

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
    //父ID
    private Long pid = 0L;
    //父编号
    private String pcode;

    private List<DataDict> items = new ArrayList<>();

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

    @Transient
    public List<DataDict> getItems() {
        return items;
    }

    public void setItems(List<DataDict> items) {
        this.items = items;
    }
}
