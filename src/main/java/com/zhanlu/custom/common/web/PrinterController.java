package com.zhanlu.custom.common.web;

import com.zhanlu.custom.common.entity.Printer;
import com.zhanlu.custom.common.service.CustomService;
import com.zhanlu.custom.common.service.PrinterService;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.security.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 打印机
 */
@Slf4j
@Controller
@RequestMapping(value = "/custom/printer")
public class PrinterController {

    @Autowired
    private PrinterService printerService;
    @Autowired
    private CustomService customService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<Printer> page, HttpServletRequest req) {
        User user = customService.getUser(req);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        ModelAndView mv = new ModelAndView("custom/printerList");
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.DESC);
        }
        page = printerService.findPage(page, filters);
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable("id") Long id, Integer status) {
        Printer entity = printerService.findById(id);
        entity.setStatus(-1);
        printerService.saveOrUpdate(entity);
        ModelAndView mv = new ModelAndView("redirect:/custom/printer");
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        Printer entity = printerService.findById(id);
        ModelAndView mv = new ModelAndView("custom/printerView");
        mv.addObject("entity", entity);
        return mv;
    }

}
