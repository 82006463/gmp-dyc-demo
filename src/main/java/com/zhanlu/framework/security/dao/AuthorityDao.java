package com.zhanlu.framework.security.dao;

import com.zhanlu.framework.common.dao.CommonDao;
import com.zhanlu.framework.security.entity.Authority;
import org.springframework.stereotype.Repository;

/**
 * 权限持久化类
 *
 * @author yuqs
 * @since 0.1
 */
@Repository
public class AuthorityDao extends CommonDao<Authority, Long> {

}
