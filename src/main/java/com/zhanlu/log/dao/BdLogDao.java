package com.zhanlu.log.dao;

import com.zhanlu.framework.common.dao.CommonDao;
import com.zhanlu.framework.common.dao.HibernateDao;
import com.zhanlu.log.entity.BdLog;
import org.springframework.stereotype.Repository;

/**
 * 基础数据日志Dao
 *
 * @author zhanlu
 * @date 2017-03-26
 * @since 0.1
 */
@Repository
public class BdLogDao extends CommonDao<BdLog, Long> {
}
