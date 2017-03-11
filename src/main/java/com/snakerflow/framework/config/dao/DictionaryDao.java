package com.snakerflow.framework.config.dao;

import com.snakerflow.common.dao.HibernateDao;
import com.snakerflow.framework.config.entity.Dictionary;
import org.springframework.stereotype.Repository;

@Repository
public class DictionaryDao extends HibernateDao<Dictionary, Long> {

}
