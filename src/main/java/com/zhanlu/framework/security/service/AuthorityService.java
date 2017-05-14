package com.zhanlu.framework.security.service;

import com.zhanlu.framework.common.service.CommonService;
import com.zhanlu.framework.security.dao.AuthorityDao;
import com.zhanlu.framework.security.entity.Authority;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 权限管理类
 *
 * @author yuqs
 * @since 0.1
 */
@Service
public class AuthorityService extends CommonService<Authority, Long> {

    @Autowired
    private AuthorityDao authorityDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = authorityDao;
    }

    @Transactional
    public void updateByMenuId(Long menuId) {
        String hql = "FROM " + Authority.class.getName() + " AS r WHERE r.menu.id=?";
        Authority entity = authorityDao.findUnique(hql, menuId);
        if (entity != null) {
            entity.setMenu(null);
            authorityDao.save(entity);
        }
    }

    /**
     * 根据用户ID查询该用户具有权限访问的资源与不需要授权的资源
     */
    @SuppressWarnings("unchecked")
    public List<Authority> getAuthorizedResource(Long userId) {
        String sql = "SELECT a.id, a.code, a.name, a.source, a.menu_id, a.remark FROM sec_user u " +
                " LEFT OUTER JOIN sec_role_user ru ON u.id = ru.user_id " +
                " LEFT OUTER JOIN sec_role r ON ru.role_id = r.id " +
                " LEFT OUTER JOIN sec_role_authority ra ON r.id = ra.role_id " +
                " LEFT OUTER JOIN sec_authority a ON ra.authority_id = a.id " +
                " WHERE a.menu_id IS NOT NULL AND u.id=?";
        SQLQuery query = authorityDao.createSQLQuery(sql, userId);
        query.addEntity(Authority.class);
        return query.list();
    }

    public List<Authority> findItems(Long pid) {
        return authorityDao.find("FROM " + Authority.class.getName() + " WHERE pid=?", pid);
    }

}
