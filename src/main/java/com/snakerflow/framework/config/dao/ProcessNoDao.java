package com.snakerflow.framework.config.dao;

import com.snakerflow.common.dao.HibernateDao;
import com.snakerflow.framework.config.entity.DataDict;
import com.snakerflow.framework.config.entity.ProcessNo;
import org.springframework.stereotype.Repository;

@Repository
public class ProcessNoDao extends HibernateDao<ProcessNo, Long> {

}
