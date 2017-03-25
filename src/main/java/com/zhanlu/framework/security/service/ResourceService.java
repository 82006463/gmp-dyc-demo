package com.zhanlu.framework.security.service;

import com.zhanlu.framework.common.service.CommonService;
import com.zhanlu.framework.security.dao.ResourceDao;
import com.zhanlu.framework.security.entity.Authority;
import com.zhanlu.framework.security.entity.Resource;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 资源管理类
 *
 * @author yuqs
 * @since 0.1
 */
@Service
public class ResourceService extends CommonService<Resource, Long> {

    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private AuthorityService authorityService;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = resourceDao;
    }

    /**
     * 根据主键ID删除资源实体
     *
     * @param id
     */
    @Transactional
    @Override
    public boolean delete(Long id) {
        List<Authority> authorities = authorityService.findAll();
        for (Authority auth : authorities) {
            for (Resource resource : auth.getResources()) {
                if (resource.getId().longValue() == id.longValue()) {
                    auth.getResources().remove(resource);
                    authorityService.save(auth);
                    break;
                }
            }
        }
        resourceDao.delete(id);
        return true;
    }

    @Transactional
    public void updateByMenuId(Long menuId) {
        String hql = "from Resource as r where r.menu.id=?";
        Resource resource = resourceDao.findUnique(hql, menuId);
        if (resource != null) {
            resource.setMenu(null);
            resourceDao.save(resource);
        }
    }

    /**
     * 根据用户ID查询该用户具有权限访问的资源与不需要授权的资源
     *
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Resource> getAuthorizedResource(Long userId) {
        String sql = " select re.id, re.name, re.source, re.menu from sec_user u " +
                " left outer join sec_role_user ru on u.id=ru.user_id " +
                " left outer join sec_role r on ru.role_id=r.id " +
                " left outer join sec_role_authority ra on r.id = ra.role_id " +
                " left outer join sec_authority a on ra.authority_id = a.id " +
                " left outer join sec_authority_resource ar on a.id = ar.authority_id " +
                " left outer join sec_resource re on ar.resource_id = re.id " +
                " where u.id=? and re.menu is not null " +
                " union all " +
                " select re.id, re.name, re.source, re.menu from sec_resource re " +
                " where re.menu is not null and not exists (select ar.authority_id from sec_authority_resource ar where ar.resource_id = re.id)";
        SQLQuery query = resourceDao.createSQLQuery(sql, userId);
        query.addEntity(Resource.class);
        return query.list();
    }
}
