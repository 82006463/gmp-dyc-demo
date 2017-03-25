package com.zhanlu.framework.common.entity;

import javax.persistence.*;

/**
 * 树相关实体类
 */
@MappedSuperclass
public class TreeEntity extends CodeEntity {

    //树根ID
    protected Long rootId;
    //节点父ID、父编号、父名称：以冗余消除Join
    protected Long pid;
    private TreeEntity parent;

    //节点在树中的层级
    protected Integer level;
    //节点的层级编号：便于用Like获取所有子节点
    protected String levelNo;

    @Column(name = "rootId")
    public Long getRootId() {
        return rootId;
    }

    public void setRootId(Long rootId) {
        this.rootId = rootId;
    }

    @Column(name = "pid")
    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Column(name = "level_no", length = 500)
    public String getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(String levelNo) {
        this.levelNo = levelNo;
    }

    @Transient
    public TreeEntity getParent() {
        return parent;
    }

    public void setParent(TreeEntity parent) {
        this.parent = parent;
    }
}
