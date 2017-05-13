package com.zhanlu.framework.security.entity;

import com.zhanlu.framework.common.entity.CodeEntity;

import javax.persistence.*;

/**
 * 资源实体类，继承抽象安全实体类
 *
 * @author yuqs
 * @since 0.1
 */
@Entity
@Table(name = "SEC_RESOURCE")
public class Resource extends CodeEntity {

    private static final long serialVersionUID = 5503383469393219319L;

    //资源值（此处主要作为url资源，及链接路径）
    private String source;
    //是否选择，该字段不需要持久化，仅仅是方便页面控制选择状态
    private Integer selected;
    //资源所属菜单
    private Menu menu;

    public Resource() {
    }
    public Resource(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "menu", nullable = true)
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

    @Transient
    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }
}
