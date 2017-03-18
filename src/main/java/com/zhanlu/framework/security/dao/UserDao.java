package com.zhanlu.framework.security.dao;

import com.zhanlu.framework.common.dao.HibernateDao;
import com.zhanlu.framework.security.entity.User;
import org.springframework.stereotype.Repository;

/**
 * 用户持久化类
 * @author yuqs
 * @since 0.1
 */
@Repository
public class UserDao extends HibernateDao<User, Long> {

}
