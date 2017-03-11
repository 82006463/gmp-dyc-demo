package com.snakerflow.framework.security.dao;

import com.snakerflow.common.dao.HibernateDao;
import com.snakerflow.framework.security.entity.Authority;
import org.springframework.stereotype.Repository;

/**
 * 权限持久化类
 * @author yuqs
 * @since 0.1
 */
@Repository
public class AuthorityDao extends HibernateDao<Authority, Long> {

}
