package com.zhanlu.framework.common.service;


import com.zhanlu.framework.common.dao.CommonDao;
import com.zhanlu.framework.common.entity.IdEntity;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.common.utils.ReflectionUtils;
import org.hibernate.SQLQuery;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class CommonService<T extends IdEntity, PK extends Serializable> {

    protected CommonDao<T, PK> commonDao;

    public abstract void initDao();

    /**
     * 新增实体对象
     *
     * @param entity
     * @return
     */
    @Transactional
    public T save(T entity) {
        entity.setStatus(1);
        return commonDao.save(entity);
    }

    /**
     * 新增或修改实体对象
     *
     * @param entity
     * @return
     */
    @Transactional
    public T saveOrUpdate(T entity) {
        if (entity.getId() == null) {
            entity.setStatus(1);
        }
        return commonDao.saveOrUpdate(entity);
    }

    /**
     * 根据对象主键删除实体对象
     *
     * @param id
     * @return
     */
    @Transactional
    public boolean delete(PK id) {
        T entity = this.findById(id);
        entity.setStatus(0);
        this.saveOrUpdate(entity);
        //commonDao.delete(id);
        return true;
    }

    @Transactional
    public boolean delete(T entity) {
        commonDao.delete(entity);
        return true;
    }

    /**
     * 根据对象主键查询实体对象
     *
     * @param id
     * @return
     */
    public T findById(PK id) {
        return commonDao.get(id);
    }

    /**
     * 获取所有实体对象
     *
     * @return
     */
    public List<T> findAll() {
        return commonDao.getAll();
    }

    /**
     * 根据分页对象、过滤集合参数，分页查询配置字典列表
     *
     * @param page
     * @param filters
     * @return
     */
    public Page<T> findPage(final Page<T> page, final List<PropertyFilter> filters) {
        return commonDao.findPage(page, filters);
    }

    /**
     * @param params 参数列表
     * @return
     */
    public List<T> findList(Map<String, Object> params) {
        Class<?> entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
        String hql = "FROM " + entityClass.getName() + " WHERE 1=1";
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                hql += " AND " + entry.getKey() + "=:" + entry.getKey();
            }
        }
        if (params == null || !params.containsKey("status")) {
            hql += " AND status IS NOT NULL AND status > 0";
        }
        return this.commonDao.find(hql, params);
    }

    /**
     * 获取entity列表
     *
     * @param sql    SQL语句
     * @param params 参数列表
     * @return
     */
    public List<T> findListBySQL(String sql, Object... params) {
        SQLQuery sqlQuery = commonDao.createSQLQuery(sql, params);
        sqlQuery.addEntity(ReflectionUtils.getSuperClassGenricType(getClass()));
        return sqlQuery.list();
    }

    /**
     * 根据SQL语句查询数据
     *
     * @param sql
     * @param params
     * @return
     */
    public List<?> findBySQL(String sql, Object... params) {
        SQLQuery sqlQuery = commonDao.createSQLQuery(sql, params);
        return sqlQuery.list();
    }
}
