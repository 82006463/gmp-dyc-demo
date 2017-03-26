package com.zhanlu.log.entity;

import com.zhanlu.framework.common.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 登录日志
 */
@Entity
@Table(name = "log_login")
public class LoginLog extends IdEntity {
}
