package com.zhanlu.framework.config.dao;

import com.zhanlu.common.dao.HibernateDao;
import com.zhanlu.framework.config.entity.DataDict;
import org.springframework.stereotype.Repository;

@Repository
public class DataDictDao extends HibernateDao<DataDict, Long> {

}
