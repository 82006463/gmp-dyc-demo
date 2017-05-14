package com.zhanlu.framework.security.web;

import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.common.utils.Digests;
import com.zhanlu.framework.common.utils.EncodeUtils;
import com.zhanlu.framework.security.entity.Role;
import com.zhanlu.framework.security.entity.User;
import com.zhanlu.framework.security.service.OrgService;
import com.zhanlu.framework.security.service.RoleService;
import com.zhanlu.framework.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理Controller
 *
 * @author yuqs
 * @since 0.1
 */
@Controller
@RequestMapping(value = "/security/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private OrgService orgService;

    /**
     * 分页查询用户，返回用户列表视图
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<User> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = userService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("security/userList");
        mv.addObject("page", page);
        return mv;
    }

    /**
     * 新建用户时，返回用户编辑视图
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView mv = new ModelAndView("security/userEdit");
        mv.addObject("entity", new User());
        mv.addObject("roles", roleService.findAll());
        return mv;
    }

    /**
     * 编辑用户时，返回用户编辑视图
     */
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) {
        User entity = userService.findById(id);
        List<Role> roles = roleService.findAll();
        List<Role> roless = entity.getRoles();
        for (Role role : roles) {
            for (Role selRole : roless) {
                role.setSelected(role.getId().longValue() == selRole.getId().longValue() ? 1 : 0);
            }
        }
        ModelAndView mv = new ModelAndView("security/userEdit");
        mv.addObject("entity", userService.findById(id));
        mv.addObject("roles", roles);
        return mv;
    }

    /**
     * 编辑用户时，返回用户查看视图
     */
    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("security/userView");
        mv.addObject("entity", userService.findById(id));
        return mv;
    }

    /**
     * 新增、编辑用户页面的提交处理。保存用户实体，并返回用户列表视图
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ModelAndView update(User user, Long[] orderIndexs, Long orgId) {
        if (orderIndexs != null) {
            for (Long order : orderIndexs) {
                Role role = new Role();
                role.setId(order);
                user.getRoles().add(role);
            }
        }
        if (orgId != null && orgId.longValue() > 0) {
            user.setOrg(orgService.findById(orgId));
        }
        userService.saveOrUpdate(user);
        ModelAndView mv = new ModelAndView("redirect:/security/user");
        return mv;
    }

    /**
     * 根据主键ID删除用户实体，并返回用户列表视图
     */
    @RequestMapping(value = "delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        userService.delete(id);
        ModelAndView mv = new ModelAndView("redirect:/security/user");
        return mv;
    }

    @RequestMapping(value = "uppwd/{username}", method = RequestMethod.GET)
    public ModelAndView uppwdGet(@PathVariable("username") String username) {
        User userByName = userService.findUserByName(username);
        ModelAndView mv = new ModelAndView("security/userPwd");
        mv.addObject("entity", userByName);
        return mv;
    }

    @RequestMapping(value = "uppwd", method = RequestMethod.POST)
    public ModelAndView uppwdPost(User entity) {
        User oldUser = userService.findById(entity.getId());
        oldUser.setPlainPassword(entity.getPlainPassword());
        userService.saveOrUpdate(oldUser);
        ModelAndView mv = new ModelAndView("redirect:/logout");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "checkpwd", method = RequestMethod.GET)
    private Map<String, Object> checkpwd(String password, String plainPassword) {
        Map<String, Object> resultMap = new HashMap<>();
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), Digests.generateSalt(8), UserService.HASH_INTERATIONS);
        plainPassword = EncodeUtils.hexEncode(hashPassword);
        if(password.equals(plainPassword)) {
            resultMap.put("result", 1);
        } else {
            resultMap.put("result", 0);
            resultMap.put("msg", "旧密码不正确");
        }
        return resultMap;
    }
}
