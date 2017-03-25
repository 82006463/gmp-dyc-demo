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
        commonDao.save(getTreeEntity(entity));
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
        commonDao.saveOrUpdate(getTreeEntity(entity));
        return entity;
    }

    public List<T> findItems(Long pid) {
        String entityName = ReflectionUtils.getSuperClassGenricType(getClass()).getName();
        return commonDao.find("FROM " + entityName + " WHERE pid=?", pid == null ? 0 : pid);
    }

    private T getTreeEntity(T entity) {
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
