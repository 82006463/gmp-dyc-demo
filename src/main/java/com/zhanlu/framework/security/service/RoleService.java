package com.zhanlu.framework.security.service;

import com.zhanlu.framework.common.service.CommonService;
import com.zhanlu.framework.security.dao.RoleDao;
import com.zhanlu.framework.security.entity.Role;
import com.zhanlu.framework.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 角色管理类
 *
 * @author yuqs
 * @since 0.1
 */
@Service
public class RoleService extends CommonService<Role, Long> {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserService userService;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = roleDao;
    }

    /**
     * 根据主键ID删除对应的角色
     *
     * @param id
     */
    @Transactional
    public boolean delete(Long id) {
        List<User> users = userService.findAll();
        for (User user : users) {
            for (Role role : user.getRoles()) {
                if (role.getId().longValue() == id.longValue()) {
                    user.getRoles().remove(role);
                    userService.save(user);
                    break;
                }
            }
        }
        roleDao.delete(id);
        return true;
    }

}
