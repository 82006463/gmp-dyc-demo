package com.zhanlu.framework.common.service;

import java.util.List;
import java.util.Map;

public interface BaseService<T, ID> {

    /**
     *
     * @param entity
     * @return
     */
    T save(T entity);

    /**
     *
     * @param entity
     * @return
     */
    T saveOrUpdate(T entity);

    /**
     *
     * @param id
     * @return
     */
    boolean delete(ID id);

    T findById(ID id);

    List<T> findList(T entity);

    List<T> findList(T entity, Map<String, Object> whereMap);

    List<T> findPage(T entity, Map<String, Object> whereMap);

    List<T> findAll();
}
