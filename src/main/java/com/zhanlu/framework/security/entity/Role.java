package com.zhanlu.framework.security.entity;

import com.zhanlu.framework.common.entity.CodeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色实体类，继承抽象安全实体类
 *
 * @author yuqs
 * @since 0.1
 */
@Entity
@Table(name = "SEC_ROLE")
public class Role extends CodeEntity {
    private static final long serialVersionUID = 2041148498753846675L;
    //是否选择，该字段不需要持久化，仅仅是方便页面控制选择状态
    private Integer selected;
    //角色拥有的权限列表（多对多关联）
    private List<Authority> authorities = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SEC_ROLE_AUTHORITY", joinColumns = {@JoinColumn(name = "ROLE_ID")}, inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID")})
    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    @Transient
    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }
}
