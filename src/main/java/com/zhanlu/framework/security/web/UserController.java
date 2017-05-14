package com.zhanlu.framework.security.web;

import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.security.entity.Role;
import com.zhanlu.framework.security.entity.User;
import com.zhanlu.framework.security.service.OrgService;
import com.zhanlu.framework.security.service.RoleService;
import com.zhanlu.framework.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public String list(Model model, Page<User> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = userService.findPage(page, filters);
        model.addAttribute("page", page);
        return "security/userList";
    }

    /**
     * 新建用户时，返回用户编辑视图
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("entity", new User());
        model.addAttribute("roles", roleService.findAll());
        return "security/userEdit";
    }

    /**
     * 编辑用户时，返回用户编辑视图
     */
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {
        User entity = userService.findById(id);
        List<Role> roles = roleService.findAll();
        List<Role> roless = entity.getRoles();
        for (Role role : roles) {
            for (Role selRole : roless) {
                role.setSelected(role.getId().longValue() == selRole.getId().longValue() ? 1 : 0);
            }
        }
        model.addAttribute("entity", userService.findById(id));
        model.addAttribute("roles", roles);
        return "security/userEdit";
    }

    /**
     * 编辑用户时，返回用户查看视图
     */
    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id, Model model) {
        model.addAttribute("entity", userService.findById(id));
        return "security/userView";
    }

    /**
     * 新增、编辑用户页面的提交处理。保存用户实体，并返回用户列表视图
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(User user, Long[] orderIndexs, Long orgId) {
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
        return "redirect:/security/user";
    }

    /**
     * 根据主键ID删除用户实体，并返回用户列表视图
     */
    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/security/user";
    }
}
