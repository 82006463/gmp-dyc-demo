package com.zhanlu.framework.common.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BaseDao<T, ID extends Serializable> {

    /**
     * 新增/更新对象
     *
     * @param entity
     * @return
     */
    T save(final T entity) throws SQLException;

    /**
     * 新增/更新对象
     *
     * @param entity
     * @return
     */
    T saveOrUpdate(final T entity) throws SQLException;

    /**
     * 根据ID删除对象
     *
     * @param id
     * @return
     */
    boolean delete(final ID id) throws SQLException;

    /**
     * 根据ID删除对象
     *
     * @param entity
     * @return
     */
    boolean delete(final T entity) throws SQLException;

    /**
     * 根据ID查询单个对象
     *
     * @param id
     * @return
     */
    T selectOne(final ID id) throws SQLException;

    /**
     *
     * @param whereMap
     * @return
     * @throws SQLException
     */
    T selectOne(Map<String, Object> whereMap) throws SQLException;

    /**
     * 根据ID集合查询多个对象
     *
     * @param ids
     * @return
     */
    List<T> selectList(final List<ID> ids) throws SQLException;

    /**
     *
     * @param entity
     * @return
     * @throws SQLException
     */
    List<T> selectList(T entity) throws SQLException;

    /**
     *
     * @param whereMap
     * @return
     * @throws SQLException
     */
    List<T> selectList(Map<String, Object> whereMap) throws SQLException;

    /**
     * 查询所有对象
     *
     * @return
     */
    List<T> selectAll();
}
