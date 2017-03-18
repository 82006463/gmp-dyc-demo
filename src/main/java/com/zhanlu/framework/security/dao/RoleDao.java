package com.zhanlu.framework.security.dao;

import com.zhanlu.common.dao.HibernateDao;
import com.zhanlu.framework.security.entity.Role;
import org.springframework.stereotype.Repository;

/**
 * 角色持久化类
 * @author yuqs
 * @since 0.1
 */
@Repository
public class RoleDao extends HibernateDao<Role, Long> {

}
