package com.snakerflow.framework.config.dao;


import com.snakerflow.framework.config.entity.Field;
import com.snakerflow.common.dao.HibernateDao;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * 表单字段持久化类
 * @author yuqs
 * @since 0.1
 */
@Repository
public class FieldDao extends HibernateDao<Field, Long> {
}
