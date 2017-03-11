package com.snakerflow.framework.security.dao;

import com.snakerflow.common.dao.HibernateDao;
import com.snakerflow.framework.security.entity.Role;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * 角色持久化类
 * @author yuqs
 * @since 0.1
 */
@Repository
public class RoleDao extends HibernateDao<Role, Long> {

}
