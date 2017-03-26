package com.zhanlu.framework.common.service;


import com.zhanlu.framework.common.entity.TreeEntity;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.common.utils.ReflectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class CommonTreeService<T extends TreeEntity, PK extends Serializable> extends CommonService<T, PK> {

    @Transactional
    @Override
    public T save(T entity) {
        commonDao.save(entity);
        processTreeEntity(entity);
        return entity;
    }

    @Transactional
    @Override
    public T saveOrUpdate(T entity) {
        commonDao.saveOrUpdate(entity);
        processTreeEntity(entity);
        return entity;
    }

    @Transactional
    public T saveOrUpdate(T entity, List<T> items) {
        if (entity.getId() != null && entity.getId() > 0) {
            commonDao.batchExecute("DELETE FROM " + entity.getClass().getName() + " WHERE pid=" + entity.getId());
        }
        this.saveOrUpdate(entity);
        for (T item : items) {
            item.setPid(entity.getId());
            this.save(item);
        }
        return entity;
    }

    @Override
    public T findById(PK id) {
        T entity = super.findById(id);
        if (entity.getPid() != null && entity.getPid() > 0) {
            entity.setParent(findById((PK) entity.getPid()));
        }
        return entity;
    }

    @Override
    public Page<T> findPage(Page<T> page, List<PropertyFilter> filters) {
        Page<T> pageResult = super.findPage(page, filters);
        for (T entity : pageResult.getResult()) {
            if (entity.getPid() != null && entity.getPid() > 0) {
                entity.setParent(findById((PK) entity.getPid()));
            }
        }
        return pageResult;
    }

    /**
     * 根据父ID查询对象列表
     *
     * @param pid 父ID
     * @return
     */
    public List<T> findItems(Long pid) {
        Map<String, Object> params = new HashedMap(4);
        params.put("pid", pid);
        return super.findList(params);
    }

    /**
     * 根据父编号查询对象列表
     *
     * @param pcode 父编号
     * @return
     */
    public List<T> findItems(String pcode) {
        Table table = ReflectionUtils.getSuperClassGenricType(getClass()).getAnnotation(Table.class);
        String tableName = table.name();
        String hql = "SELECT tb1.* FROM " + tableName + " tb1 LEFT JOIN " + tableName + " as tb2 ON tb1.pid=tb2.id WHERE tb2.code=?";
        return super.findListBySQL(hql, pcode);
    }

    private T processTreeEntity(T entity) {
        T parent = null;
        if (entity.getPid() != null && entity.getPid() > 0) {
            parent = commonDao.get((PK) entity.getPid());
        }
        entity.setRootId(parent == null ? entity.getId() : parent.getRootId());
        entity.setPid(parent == null ? 0 : parent.getId());
        entity.setLevel(parent == null ? 1 : parent.getLevel() + 1);
        String levelNo = parent == null || parent.getLevelNo() == null ? "" : parent.getLevelNo();
        for (int i = 0; i < 5 - entity.getId().toString().length(); i++) {
            levelNo += "0";
        }
        levelNo += entity.getId();
        entity.setLevelNo(levelNo);
        if (StringUtils.isBlank(entity.getCode())) {
            entity.setCode(entity.getName());
        } else if (StringUtils.isBlank(entity.getName())) {
            entity.setName(entity.getCode());
        }
        return entity;
    }
}
