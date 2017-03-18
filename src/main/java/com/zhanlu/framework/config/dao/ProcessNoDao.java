package com.zhanlu.framework.config.dao;

import com.zhanlu.common.dao.HibernateDao;
import com.zhanlu.framework.config.entity.ProcessNo;
import org.springframework.stereotype.Repository;

@Repository
public class ProcessNoDao extends HibernateDao<ProcessNo, Long> {

}
