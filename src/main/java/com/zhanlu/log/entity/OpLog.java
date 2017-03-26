package com.zhanlu.log.entity;

import com.zhanlu.framework.common.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 操作日志
 */
@Entity
@Table(name = "log_op")
public class OpLog extends IdEntity {
}
