package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.DrugComp;
import com.zhanlu.custom.cms.service.DrugCompService;
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
 * 药企
 */
@Controller
@RequestMapping(value = "/custom/cms/drugComp")
public class DrugCompController {

    @Autowired
    private DrugCompService drugCompService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<DrugComp> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = drugCompService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/drugCompList");
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(Model model) {
        model.addAttribute("entity", new DrugComp());
        ModelAndView mv = new ModelAndView("cms/drugCompEdit");
        return mv;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) {
        DrugComp entity = drugCompService.findById(id);
        ModelAndView mv = new ModelAndView("cms/drugCompEdit");
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        DrugComp entity = drugCompService.findById(id);
        ModelAndView mv = new ModelAndView("cms/drugCompView");
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(DrugComp entity) {
        drugCompService.saveOrUpdate(entity);
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/drugComp");
        return mv;
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        drugCompService.delete(id);
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/drugComp");
        return mv;
    }

}
