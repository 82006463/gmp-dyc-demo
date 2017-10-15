package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/10/15.
 */
@Entity
@Table(name = "cms_comp_notify")
public class CompNotify extends IdEntity {

    @Column(name = "notify_type")
    private Integer notifyType;
    @Column(name = "content", length = 200)
    private String content;

}
