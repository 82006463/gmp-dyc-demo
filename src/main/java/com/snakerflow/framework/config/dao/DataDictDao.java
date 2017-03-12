package com.snakerflow.framework.config.dao;

import com.snakerflow.common.dao.HibernateDao;
import com.snakerflow.framework.config.entity.DataDict;
import org.springframework.stereotype.Repository;

@Repository
public class DataDictDao extends HibernateDao<DataDict, Long> {

}
