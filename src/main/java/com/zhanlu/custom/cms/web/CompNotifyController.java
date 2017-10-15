package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.CompNotify;
import com.zhanlu.custom.cms.service.CompNotifyService;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView list(Page<CompNotify> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = compNotifyService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/compNotifyList");
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView mv = new ModelAndView("cms/compNotifyEdit");
        mv.addObject("entity", new CompNotify());
        return mv;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) {
        CompNotify entity = compNotifyService.findById(id);
        ModelAndView mv = new ModelAndView("cms/compNotifyEdit");
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        CompNotify entity = compNotifyService.findById(id);
        ModelAndView mv = new ModelAndView("cms/compNotifyView");
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(CompNotify entity) {
        compNotifyService.saveOrUpdate(entity);
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/compNotify");
        return mv;
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        compNotifyService.delete(id);
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/compNotify");
        return mv;
    }

}
