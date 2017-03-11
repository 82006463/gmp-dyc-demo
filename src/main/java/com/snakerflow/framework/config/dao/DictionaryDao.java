package com.snakerflow.framework.config.dao;

import com.snakerflow.framework.config.entity.Dictionary;
import com.snakerflow.common.dao.HibernateDao;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * 配置字典持久化类
 * @author yuqs
 * @since 0.1
 */
@Repository
public class DictionaryDao extends HibernateDao<Dictionary, Long> {

}
