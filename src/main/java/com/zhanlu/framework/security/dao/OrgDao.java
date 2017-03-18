package com.zhanlu.framework.security.dao;

import com.zhanlu.common.dao.HibernateDao;
import com.zhanlu.framework.security.entity.Org;
import org.springframework.stereotype.Repository;

/**
 * 部门持久化类
 * @author yuqs
 * @since 0.1
 */
@Repository
public class OrgDao extends HibernateDao<Org, Long> {

}
