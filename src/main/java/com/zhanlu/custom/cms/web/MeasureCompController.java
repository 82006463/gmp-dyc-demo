package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.MeasureComp;
import com.zhanlu.custom.cms.service.MeasureCompService;
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
 * 计量公司
 */
@Controller
@RequestMapping(value = "/custom/cms/measureComp")
public class MeasureCompController {

    @Autowired
    private MeasureCompService measureCompService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<MeasureComp> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = measureCompService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/measureCompList");
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView mv = new ModelAndView("cms/measureCompEdit");
        mv.addObject("entity", new MeasureComp());
        return mv;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) {
        MeasureComp entity = measureCompService.findById(id);
        ModelAndView mv = new ModelAndView("cms/measureCompEdit");
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        MeasureComp entity = measureCompService.findById(id);
        ModelAndView mv = new ModelAndView("cms/measureCompView");
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(MeasureComp entity) {
        measureCompService.saveOrUpdate(entity);
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/measureComp");
        return mv;
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        measureCompService.delete(id);
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/measureComp");
        return mv;
    }

}
