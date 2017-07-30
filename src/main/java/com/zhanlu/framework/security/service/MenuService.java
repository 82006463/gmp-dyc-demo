package com.zhanlu.framework.security.service;

import com.zhanlu.framework.common.service.CommonService;
import com.zhanlu.framework.security.dao.MenuDao;
import com.zhanlu.framework.security.entity.Menu;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 菜单管理类
 *
 * @author yuqs
 * @since 0.1
 */
@Service
public class MenuService extends CommonService<Menu, Long> {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private AuthorityService authorityService;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = menuDao;
    }

    /**
     * 根据主键ID删除菜单实体
     *
     * @param id
     */
    @Transactional
    @Override
    public boolean delete(Long id) {
        authorityService.updateByMenuId(id);
        menuDao.delete(id);
        return true;
    }

    /**
     * 根据用户ID查询该用户允许访问的所有菜单列表
     *
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Menu> getAllowedAccessMenu(Long userId) {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("SELECT * FROM (");
        //获取Sec_Menu表中定义且未关联资源表Sec_Resource的所有菜单列表
        sqlBuffer.append(" SELECT m.id,m.name,m.parent_menu,m.description,m.orderby,m.status FROM sec_menu m ");
        sqlBuffer.append(" WHERE NOT EXISTS (SELECT a.id from sec_authority a WHERE a.menu_id = m.id)");
        sqlBuffer.append(" UNION ALL ");
        //获取Sec_Resource表中已关联且未设置权限的菜单列表
        /*sqlBuffer.append(" SELECT m.id,m.name,m.parent_menu,re.source as description,m.orderby FROM sec_resource re ");
        sqlBuffer.append(" LEFT OUTER JOIN sec_menu m ON re.menu = m.id  ");
        sqlBuffer.append(" WHERE re.menu IS NOT NULL AND NOT EXISTS (SELECT ar.authority_id FROM sec_authority_resource ar WHERE ar.resource_id = re.id)");
        sqlBuffer.append(" UNION ALL ");*/
        //获取Sec_Resource表中已关联且设置权限，并根据当前登录账号拥有相应权限的菜单列表
        sqlBuffer.append(" SELECT m.id,m.name,m.parent_menu,a.source as description,m.orderby,m.status from sec_user u ");
        sqlBuffer.append(" LEFT OUTER JOIN sec_role_user ru ON u.id=ru.user_id ");
        sqlBuffer.append(" LEFT OUTER JOIN sec_role r ON ru.role_id=r.id ");
        sqlBuffer.append(" LEFT OUTER JOIN sec_role_authority ra ON r.id = ra.role_id ");
        sqlBuffer.append(" LEFT OUTER JOIN sec_authority a ON ra.authority_id = a.id ");
        sqlBuffer.append(" LEFT OUTER JOIN sec_menu m ON a.menu_id = m.id ");
        sqlBuffer.append(" WHERE a.menu_id IS NOT NULL AND u.id=?");
        sqlBuffer.append(") tbl ORDER BY orderby");
        SQLQuery query = menuDao.createSQLQuery(sqlBuffer.toString(), userId);
        query.addEntity(Menu.class);
        return query.list();
    }
}
