package com.zhanlu.app.modules.dao;

import com.zhanlu.framework.common.dao.HibernateDao;
import org.springframework.stereotype.Component;

import com.zhanlu.app.modules.entity.Borrow;

@Component
public class BorrowDao extends HibernateDao<Borrow, Long> {

}
