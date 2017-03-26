package com.zhanlu.log.dao;

import com.zhanlu.framework.common.dao.CommonDao;
import com.zhanlu.log.entity.LoginLog;
import org.springframework.stereotype.Repository;

/**
 * 登录日志Dao
 *
 * @author zhanlu
 * @date 2017-03-26
 * @since 0.1
 */
@Repository
public class LoginLogDao extends CommonDao<LoginLog, Long> {
}
