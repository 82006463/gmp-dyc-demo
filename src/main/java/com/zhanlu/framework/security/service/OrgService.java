package com.zhanlu.framework.security.service;

import com.zhanlu.framework.common.service.CommonTreeService;
import com.zhanlu.framework.security.dao.OrgDao;
import com.zhanlu.framework.security.entity.Org;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * 部门管理类
 *
 * @author yuqs
 * @since 0.1
 */
@Service
public class OrgService extends CommonTreeService<Org, Long> {

    @Autowired
    private OrgDao orgDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = orgDao;
    }

    /**
     * 保存部门实体
     *
     * @param entity
     */
    @Transactional
    @Override
    public Org saveOrUpdate(Org entity) {
        Org parent = null;
        if (entity.getPid() != null && entity.getPid() > 0) {
            parent = orgDao.get(entity.getPid());
            entity.setPcode(parent.getCode());
            entity.setPname(parent.getName());
        }
        orgDao.saveOrUpdate(entity);
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
