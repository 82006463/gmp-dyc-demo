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
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView list(Page<CustomerComp> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = customerCompService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/customerCompList");
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(Model model) {
        model.addAttribute("entity", new CustomerComp());
        ModelAndView mv = new ModelAndView("cms/customerCompEdit");
        return mv;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) {
        CustomerComp entity = customerCompService.findById(id);
        ModelAndView mv = new ModelAndView("cms/customerCompEdit");
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        CustomerComp entity = customerCompService.findById(id);
        ModelAndView mv = new ModelAndView("cms/customerCompView");
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(CustomerComp entity) {
        customerCompService.saveOrUpdate(entity);
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/customerComp");
        return mv;
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        customerCompService.delete(id);
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/customerComp");
        return mv;
    }

}
