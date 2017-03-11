package com.snakerflow.app.modules.dao;

import org.springframework.stereotype.Component;

import com.snakerflow.app.modules.entity.Borrow;
import com.snakerflow.common.dao.HibernateDao;

@Component
public class BorrowDao extends HibernateDao<Borrow, Long> {

}
