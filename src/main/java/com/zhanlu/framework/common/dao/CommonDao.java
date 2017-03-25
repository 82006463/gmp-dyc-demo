package com.zhanlu.framework.common.dao;

import java.io.Serializable;

/**
 * 通用Dao层封装
 *
 * @param <T>  实体对象
 * @param <PK> 对象主键
 */
public class CommonDao<T, PK extends Serializable> extends HibernateDao<T, PK> {

}
