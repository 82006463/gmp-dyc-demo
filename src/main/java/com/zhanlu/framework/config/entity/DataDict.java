package com.zhanlu.framework.config.entity;

import com.zhanlu.framework.common.entity.TreeEntity;

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
public class DataDict extends TreeEntity {

    private List<DataDict> items = new ArrayList<>();

    @Transient
    public List<DataDict> getItems() {
        return items;
    }

    public void setItems(List<DataDict> items) {
        this.items = items;
    }
}
