package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.CompNotify;
import com.zhanlu.custom.cms.service.CompNotifyService;
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
 * 通知
 */
@Controller
@RequestMapping(value = "/custom/cms/compNotify")
public class CompNotifyController {

    @Autowired
    private CompNotifyService compNotifyService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, Page<CompNotify> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = compNotifyService.findPage(page, filters);
        model.addAttribute("page", page);
        return "cms/compNotifyList";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("entity", new CompNotify());
        return "cms/compNotifyEdit";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {
        CompNotify entity = compNotifyService.findById(id);
        model.addAttribute("entity", entity);
        return "cms/compNotifyEdit";
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id, Model model) {
        CompNotify entity = compNotifyService.findById(id);
        model.addAttribute("entity", entity);
        return "cms/compNotifyView";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(CompNotify entity) {
        compNotifyService.saveOrUpdate(entity);
        return "redirect:/cms/compNotify/list";
    }

    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        compNotifyService.delete(id);
        return "redirect:/cms/compNotify/list";
    }

}
