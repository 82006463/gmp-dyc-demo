package com.zhanlu.log.entity;

import com.zhanlu.framework.common.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系统管理日志
 */
@Entity
@Table(name = "log_sm")
public class SmLog extends IdEntity {
}
