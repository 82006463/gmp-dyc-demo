package com.zhanlu.log.entity;

import com.zhanlu.framework.common.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 基础数据日志
 */
@Entity
@Table(name = "log_bd")
public class BdLog extends IdEntity {
}
