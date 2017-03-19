package com.zhanlu.framework.security.service;

import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.security.dao.OrgDao;
import com.zhanlu.framework.security.entity.Org;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 部门管理类
 *
 * @author yuqs
 * @since 0.1
 */
@Service
public class OrgService {

    @Autowired
    private OrgDao orgDao;

    /**
     * 保存部门实体
     *
     * @param entity
     */
    @Transactional
    public void save(Org entity) {
        Org parent = null;
        if (entity.getPid() != null && entity.getPid() > 0) {
            parent = orgDao.get(entity.getPid());
            entity.setPcode(parent.getCode());
            entity.setPname(parent.getName());
        }
        orgDao.save(entity);
        entity.setRootId(parent == null ? entity.getId() : parent.getRootId());
        entity.setPid(parent == null ? 0 : parent.getId());
        entity.setLevel(parent == null ? 1 : parent.getLevel() + 1);
        String levelStr = parent == null ? "" : parent.getLevelStr();
        for (int i = 0; i < 5 - entity.getId().toString().length(); i++) {
            levelStr += "0";
        }
        levelStr += entity.getId();
        entity.setLevelStr(levelStr);
    }

    /**
     * 根据主键ID删除对应的部门
     *
     * @param id
     */
    @Transactional
    public void delete(Long id) {
        orgDao.delete(id);
    }

    /**
     * 根据主键ID获取部门实体
     *
     * @param id
     * @return
     */
    public Org get(Long id) {
        return orgDao.get(id);
    }

    /**
     * 根据分页对象、过滤集合参数，分页查询部门列表
     *
     * @param page
     * @param filters
     * @return
     */
    public Page<Org> findPage(final Page<Org> page, final List<PropertyFilter> filters) {
        return orgDao.findPage(page, filters);
    }

    public List<Org> findAll() {
        return orgDao.getAll();
    }

    /**
     * 获取所有部门记录
     *
     * @return
     */
    public List<Org> getAll() {
        return orgDao.getAll();
    }

    /**
     * 根据parentId获取所有下级部门
     *
     * @param parentId
     * @return
     */
    public List<Org> getByParent(Long parentId) {
        if (parentId == null || parentId == 0) {
            return getAll();
        }
        return orgDao.find("from Org org where org.pid=?", parentId);
    }
}
