package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 通知
 */
@Entity
@Table(name = "cms_comp_notify")
public class CompNotify extends IdEntity {

    //接收人
    private Integer notifyType;
    //通知标题
    private String subject;
    //通知内容
    private String content;

    @Column(name = "notify_type")
    public Integer getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(Integer notifyType) {
        this.notifyType = notifyType;
    }

    @Column(name = "subject", length = 100)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column(name = "content", length = 500)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
