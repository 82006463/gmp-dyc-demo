package com.zhanlu.app.modules.dao;

import org.springframework.stereotype.Component;

import com.zhanlu.app.modules.entity.Borrow;
import com.zhanlu.common.dao.HibernateDao;

@Component
public class BorrowDao extends HibernateDao<Borrow, Long> {

}
