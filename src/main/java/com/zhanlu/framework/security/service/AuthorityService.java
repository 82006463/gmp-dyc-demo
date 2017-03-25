package com.zhanlu.framework.security.service;

import com.zhanlu.framework.common.service.CommonService;
import com.zhanlu.framework.security.dao.AuthorityDao;
import com.zhanlu.framework.security.entity.Authority;
import com.zhanlu.framework.security.entity.Role;
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
    @Autowired
    private RoleService roleService;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = authorityDao;
    }

    /**
     * 根据主键ID删除对应的实体
     *
     * @param id
     */
    @Transactional
    @Override
    public boolean delete(Long id) {
        List<Role> roles = roleService.findAll();
        for (Role role : roles) {
            for (Authority auth : role.getAuthorities()) {
                if (auth.getId().longValue() == id.longValue()) {
                    role.getAuthorities().remove(auth);
                    roleService.save(role);
                    break;
                }
            }
        }
        authorityDao.delete(id);
        return true;
    }

}
