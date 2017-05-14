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

    private Long pid; //父ID：无父时为0

    //资源值（此处主要作为url资源，及链接路径）
    private String source;
    //资源所属菜单
    private Menu menu;
    //是否选择，该字段不需要持久化，仅仅是方便页面控制选择状态
    private Integer selected;
    private List<Authority> items = new ArrayList<>();

    @Transient
    public List<Authority> getItems() {
        return items;
    }

    public void setItems(List<Authority> items) {
        this.items = items;
    }

    public Authority() {
    }

    public Authority(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "menu_id")
    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Column(name = "source", length = 200)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "pid")
    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    @Transient
    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }
}
