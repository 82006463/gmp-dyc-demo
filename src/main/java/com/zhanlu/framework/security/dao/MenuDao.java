package com.zhanlu.framework.security.dao;

import com.zhanlu.framework.common.dao.HibernateDao;
import com.zhanlu.framework.security.entity.Menu;
import org.springframework.stereotype.Repository;

/**
 * 菜单持久化类
 * @author yuqs
 * @since 0.1
 */
@Repository
public class MenuDao extends HibernateDao<Menu, Long> {

}
