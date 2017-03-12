package com.snakerflow.common.service;

import java.util.List;
import java.util.Map;

public interface BaseService<T, ID> {

    T saveOrUpdate(T entity);

    boolean remove(ID id);

    T findById(ID id);

    List<T> findList(T entity, Map<String, Object> whereMap);

    List<T> findByPage(T entity, Map<String, Object> whereMap);

    List<T> findAll();
}
