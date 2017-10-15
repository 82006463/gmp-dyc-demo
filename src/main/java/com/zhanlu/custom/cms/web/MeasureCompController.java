package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.MeasureComp;
import com.zhanlu.custom.cms.service.MeasureCompService;
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
 * 计量公司
 */
@Controller
@RequestMapping(value = "/custom/cms/measureComp")
public class MeasureCompController {

    @Autowired
    private MeasureCompService measureCompService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, Page<MeasureComp> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = measureCompService.findPage(page, filters);
        model.addAttribute("page", page);
        return "cms/measureCompList";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("entity", new MeasureComp());
        return "cms/measureCompEdit";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {
        MeasureComp entity = measureCompService.findById(id);
        model.addAttribute("entity", entity);
        return "cms/measureCompEdit";
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id, Model model) {
        MeasureComp entity = measureCompService.findById(id);
        model.addAttribute("entity", entity);
        return "cms/measureCompView";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(MeasureComp entity) {
        measureCompService.saveOrUpdate(entity);
        return "redirect:/cms/measureComp/list";
    }

    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        measureCompService.delete(id);
        return "redirect:/cms/measureComp/list";
    }

}
