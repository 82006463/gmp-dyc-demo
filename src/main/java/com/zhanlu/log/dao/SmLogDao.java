package com.zhanlu.log.dao;

import com.zhanlu.framework.common.dao.CommonDao;
import com.zhanlu.log.entity.SmLog;
import org.springframework.stereotype.Repository;

/**
 * 系统管理日志Dao
 *
 * @author zhanlu
 * @date 2017-03-26
 * @since 0.1
 */
@Repository
public class SmLogDao extends CommonDao<SmLog, Long> {
}
