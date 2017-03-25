package com.zhanlu.framework.common.service;


import com.zhanlu.framework.common.dao.CommonDao;
import com.zhanlu.framework.common.entity.TreeEntity;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.common.utils.ReflectionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

public abstract class CommonTreeService<T extends TreeEntity, PK extends Serializable> extends CommonService<T, PK> {

    protected CommonDao<T, PK> commonDao;

    public abstract void initDao();

    /**
     * 新增实体对象
     *
     * @param entity
     * @return
     */
    @Transactional
    @Override
    public T save(T entity) {
        commonDao.save(getEntity(entity));
        return entity;
    }

    /**
     * 新增或修改实体对象
     *
     * @param entity
     * @return
     */
    @Transactional
    @Override
    public T saveOrUpdate(T entity) {
        commonDao.saveOrUpdate(getEntity(entity));
        return entity;
    }

    /**
     * 根据对象主键删除实体对象
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
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

    public List<T> findItems(Long pid) {
        String entityName = ReflectionUtils.getSuperClassGenricType(getClass()).getName();
        return commonDao.find("FROM " + entityName + " WHERE pid=?", pid == null ? 0 : pid);
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

    private T getEntity(T entity) {
        T parent = null;
        if (entity.getPid() != null && entity.getPid() > 0) {
            parent = commonDao.get((PK) entity.getPid());
        }
        entity.setRootId(parent == null ? entity.getId() : parent.getRootId());
        entity.setPid(parent == null ? 0 : parent.getId());
        entity.setLevel(parent == null ? 1 : parent.getLevel() + 1);
        String levelNo = parent == null ? "" : parent.getLevelNo();
        for (int i = 0; i < 5 - entity.getId().toString().length(); i++) {
            levelNo += "0";
        }
        levelNo += entity.getId();
        entity.setLevelNo(levelNo);
        return entity;
    }
}
