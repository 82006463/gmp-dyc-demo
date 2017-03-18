package com.zhanlu.framework.security.dao;

import com.zhanlu.framework.common.dao.HibernateDao;
import com.zhanlu.framework.security.entity.Resource;
import org.springframework.stereotype.Repository;

/**
 * 资源持久化类
 * @author yuqs
 * @since 0.1
 */
@Repository
public class ResourceDao extends HibernateDao<Resource, Long> {

}
