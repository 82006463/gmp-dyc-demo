package com.snakerflow.framework.security.dao;

import com.snakerflow.common.dao.HibernateDao;
import com.snakerflow.framework.security.entity.Authority;
import org.springframework.stereotype.Component;

/**
 * 权限持久化类
 * @author yuqs
 * @since 0.1
 */
@Component
public class AuthorityDao extends HibernateDao<Authority, Long> {

}
