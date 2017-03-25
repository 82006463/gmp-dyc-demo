package com.zhanlu.framework.common.service;


import com.zhanlu.framework.common.dao.CommonDao;
import com.zhanlu.framework.common.entity.IdEntity;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

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
        commonDao.delete(id);
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
}
