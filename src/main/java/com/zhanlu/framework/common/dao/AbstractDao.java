package com.zhanlu.framework.common.dao;

import com.zhanlu.framework.common.utils.ReflectionUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class AbstractDao<T, ID extends Serializable> implements BaseDao<T, ID> {

    @Resource(name = "hibernateTemplate")
    private HibernateTemplate firstHibernateTemplate;
    private HibernateTemplate secondHibernateTemplate;

    public void setSecondHibernateTemplate(HibernateTemplate hibernateTemplate) {
        secondHibernateTemplate = hibernateTemplate;
    }

    @Override
    public T save(T entity) throws SQLException {
        if (secondHibernateTemplate != null) {
            secondHibernateTemplate.save(entity);
        } else {
            firstHibernateTemplate.save(entity);
        }
        return entity;
    }

    @Override
    public T saveOrUpdate(T entity) throws SQLException {
        if (secondHibernateTemplate != null) {
            secondHibernateTemplate.saveOrUpdate(entity);
        } else {
            firstHibernateTemplate.saveOrUpdate(entity);
        }
        return entity;
    }

    @Override
    public boolean delete(ID id) throws SQLException {
        return this.delete(selectOne(id));
    }

    @Override
    public boolean delete(T entity) throws SQLException {
        if (secondHibernateTemplate != null) {
            secondHibernateTemplate.delete(entity);
        } else {
            firstHibernateTemplate.delete(entity);
        }
        return true;
    }

    @Override
    public T selectOne(ID id) throws SQLException {
        return (T) (secondHibernateTemplate != null ? secondHibernateTemplate.load(ReflectionUtils.getSuperClassGenricType(getClass()), id) : firstHibernateTemplate.load(ReflectionUtils.getSuperClassGenricType(getClass()), id));
    }

    @Override
    public T selectOne(Map<String, Object> whereMap) throws SQLException {
        List<T> entities = this.selectListByMap(whereMap);
        return entities == null ? null : entities.get(0);
    }

    @Override
    public List<T> selectList(List<ID> ids) throws SQLException {
        List<T> entities = new ArrayList<>(ids.size());
        for (ID id : ids)
            entities.add(selectOne(id));
        return entities;
    }

    @Override
    public List<T> selectList(T entity) throws SQLException {
        return null;
    }

    @Override
    public List<T> selectList(Map<String, Object> whereMap) throws SQLException {
        return this.selectListByMap(whereMap);
    }

    @Override
    public List<T> selectAll() {
        return this.selectListByMap(null);
    }

    private List<T> selectListByMap(Map<String, Object> whereMap) {
        StringBuilder hql = new StringBuilder("SELECT t FROM " + ReflectionUtils.getSuperClassGenricType(getClass()).getName() + " t WHERE 1=1");
        if (whereMap != null && whereMap.size() > 0) {
            List<Object> params = new ArrayList<>(whereMap.size());
            for (Map.Entry<String, Object> entry : whereMap.entrySet()) {
                hql.append(" t." + entry.getKey() + " = ?");
                params.add(entry.getValue());
            }
            return secondHibernateTemplate != null ? secondHibernateTemplate.find(hql.toString(), params.toArray()) : firstHibernateTemplate.find(hql.toString(), params.toArray());
        }
        return secondHibernateTemplate != null ? secondHibernateTemplate.find(hql.toString()) : firstHibernateTemplate.find(hql.toString());
    }
}
