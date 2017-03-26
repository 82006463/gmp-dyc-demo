package com.zhanlu.log.dao;

import com.zhanlu.framework.common.dao.CommonDao;
import com.zhanlu.log.entity.OpLog;
import org.springframework.stereotype.Repository;

/**
 * 操作日志Dao
 *
 * @author zhanlu
 * @date 2017-03-26
 * @since 0.1
 */
@Repository
public class OpLogDao extends CommonDao<OpLog, Long> {
}
