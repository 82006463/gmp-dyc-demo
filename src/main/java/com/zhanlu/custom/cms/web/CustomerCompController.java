package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.CustomerComp;
import com.zhanlu.custom.cms.service.CustomerCompService;
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
 * 客户公司
 */
@Controller
@RequestMapping(value = "/custom/cms/customerComp")
public class CustomerCompController {

    @Autowired
    private CustomerCompService customerCompService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, Page<CustomerComp> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = customerCompService.findPage(page, filters);
        model.addAttribute("page", page);
        return "cms/customerCompList";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("entity", new CustomerComp());
        return "cms/customerCompEdit";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {
        CustomerComp entity = customerCompService.findById(id);
        model.addAttribute("entity", entity);
        return "cms/customerCompEdit";
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id, Model model) {
        CustomerComp entity = customerCompService.findById(id);
        model.addAttribute("entity", entity);
        return "cms/customerCompView";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(CustomerComp entity) {
        customerCompService.saveOrUpdate(entity);
        return "redirect:/cms/customerComp/list";
    }

    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        customerCompService.delete(id);
        return "redirect:/cms/customerComp/list";
    }

}
