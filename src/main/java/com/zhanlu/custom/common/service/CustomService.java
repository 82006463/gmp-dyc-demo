package com.zhanlu.custom.common.service;

import com.zhanlu.framework.security.entity.User;
import com.zhanlu.framework.security.service.OrgService;
import com.zhanlu.framework.security.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/10/28.
 */
@Service
public class CustomService {

    @Autowired
    private UserService userService;
    @Autowired
    private OrgService orgService;

    public User getUser(HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("username");
        if (StringUtils.isBlank(username)) {
            throw new NullPointerException("username is null");
        }
        User user = getUser(username);
        if (user == null) {
            throw new NullPointerException("user is null");
        }
        return user;
    }

    public User getUser(String username) {
        if (StringUtils.isBlank(username))
            return null;
        return userService.findUserByName(username);
    }
}
