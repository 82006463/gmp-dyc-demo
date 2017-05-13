package com.zhanlu.framework.security.web;

import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.security.entity.Authority;
import com.zhanlu.framework.security.entity.Menu;
import com.zhanlu.framework.security.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 权限管理Controller
 *
 * @author yuqs
 * @since 0.1
 */
@Controller
@RequestMapping(value = "/security/authority")
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    /**
     * 分页查询权限，返回权限列表视图
     *
     * @param model
     * @param page
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, Page<Authority> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = authorityService.findPage(page, filters);
        model.addAttribute("page", page);
        return "security/authorityList";
    }

    /**
     * 新建权限时，返回权限编辑视图
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("entity", new Authority());
        return "security/authorityEdit";
    }

    /**
     * 编辑权限时，返回权限编辑视图
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {
        Authority entity = authorityService.findById(id);
        model.addAttribute("entity", entity);
        return "security/authorityEdit";
    }

    /**
     * 编辑权限时，返回权限查看视图
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id, Model model) {
        model.addAttribute("entity", authorityService.findById(id));
        return "security/authorityView";
    }

    /**
     * 新增、编辑权限页面的提交处理。保存权限实体，并返回权限列表视图
     *
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(Authority entity, Long menuId) {
        if (menuId != null && menuId.longValue() > 0) {
            Menu menu = new Menu(menuId);
            entity.setMenu(menu);
        }
        authorityService.saveOrUpdate(entity);
        return "redirect:/security/authority";
    }

    /**
     * 根据主键ID删除权限实体，并返回权限列表视图
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        authorityService.delete(id);
        return "redirect:/security/authority";
    }
}
