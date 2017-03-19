package com.zhanlu.framework.common.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class TreeEntity extends IdEntity {

    //树根ID
    protected Long rootId;
    //节点父ID、父编号、父名称：以冗余消除Join
    protected Long pid;
    protected String pcode;
    protected String pname;

    //编号
    protected String code;
    //名称
    protected String name;
    //节点在树中的层级
    protected Integer level;
    //节点的层级字符串：便于获取所有子节点
    protected String levelStr;

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

    @Column(name = "pcode", length = 20)
    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    @Column(name = "pname", length = 100)
    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    @Column(name = "code", unique = true, length = 20)
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

    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Column(name = "level_str", length = 500)
    public String getLevelStr() {
        return levelStr;
    }

    public void setLevelStr(String levelStr) {
        this.levelStr = levelStr;
    }
}
