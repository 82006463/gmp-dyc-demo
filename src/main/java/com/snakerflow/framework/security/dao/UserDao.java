package com.snakerflow.framework.security.dao;

import com.snakerflow.common.dao.HibernateDao;
import com.snakerflow.framework.security.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * 用户持久化类
 * @author yuqs
 * @since 0.1
 */
@Repository
public class UserDao extends HibernateDao<User, Long> {

}
