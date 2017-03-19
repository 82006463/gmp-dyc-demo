package com.zhanlu.framework.common.dao;

import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class CommonDao<T, PK extends Serializable> extends HibernateDao<T, PK> {

}
