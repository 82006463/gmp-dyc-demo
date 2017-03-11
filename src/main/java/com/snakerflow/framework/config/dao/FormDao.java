package com.snakerflow.framework.config.dao;

import com.snakerflow.common.dao.HibernateDao;
import com.snakerflow.framework.config.entity.Form;
import org.springframework.stereotype.Repository;

/**
 * 表单持久化类
 *
 * @author yuqs
 * @since 0.1
 */
@Repository
public class FormDao extends HibernateDao<Form, Long> {
}
