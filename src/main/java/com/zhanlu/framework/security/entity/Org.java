package com.zhanlu.framework.security.entity;

import com.zhanlu.framework.common.entity.TreeEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 部门实体类，继承抽象安全实体类
 *
 * @author yuqs
 * @since 0.1
 */
@Entity
@Table(name = "SEC_ORG")
public class Org extends TreeEntity {
    private static final long serialVersionUID = 7297765946510001885L;


    public Org() {

    }

    public Org(Long pid) {
        super.pid = pid;
    }

}
