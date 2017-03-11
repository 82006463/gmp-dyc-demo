package com.snakerflow.framework.security.dao;

import com.snakerflow.common.dao.HibernateDao;
import com.snakerflow.framework.security.entity.Org;
import org.springframework.stereotype.Repository;

/**
 * 部门持久化类
 * @author yuqs
 * @since 0.1
 */
@Repository
public class OrgDao extends HibernateDao<Org, Long> {

}
