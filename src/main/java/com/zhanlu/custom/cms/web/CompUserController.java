package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.CompUser;
import com.zhanlu.custom.cms.service.CompUserService;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户
 */
@Controller
@RequestMapping(value = "/custom/cms/compUser")
public class CompUserController {

    @Autowired
    private CompUserService compUserService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, Page<CompUser> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = compUserService.findPage(page, filters);
        model.addAttribute("page", page);
        return "cms/compUserList";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("entity", new CompUser());
        return "cms/compUserEdit";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {
        CompUser entity = compUserService.findById(id);
        model.addAttribute("entity", entity);
        return "cms/compUserEdit";
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id, Model model) {
        CompUser entity = compUserService.findById(id);
        model.addAttribute("entity", entity);
        return "cms/compUserView";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(CompUser entity) {
        compUserService.saveOrUpdate(entity);
        return "redirect:/cms/compUser/list";
    }

    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        compUserService.delete(id);
        return "redirect:/cms/compUser/list";
    }

}
