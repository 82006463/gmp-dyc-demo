package com.zhanlu.framework.security.entity;


import com.zhanlu.framework.common.entity.CodeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限实体类，继承抽象安全实体类
 *
 * @author yuqs
 * @since 0.1
 */
@Entity
@Table(name = "SEC_AUTHORITY")
public class Authority extends CodeEntity {

    private static final long serialVersionUID = -8349705525996917628L;

    //是否选择，该字段不需要持久化，仅仅是方便页面控制选择状态
    private Integer selected;
    //权限管辖的资源列表（多对多关联）
    private List<Resource> resources = new ArrayList<>();

    public Authority() {
    }

    public Authority(Long id) {
        this.id = id;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SEC_AUTHORITY_RESOURCE", joinColumns = {@JoinColumn(name = "AUTHORITY_ID")}, inverseJoinColumns = {@JoinColumn(name = "RESOURCE_ID")})
    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    @Transient
    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }
}
