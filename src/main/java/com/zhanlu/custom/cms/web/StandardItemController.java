package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.StandardItem;
import com.zhanlu.custom.cms.service.CmsService;
import com.zhanlu.custom.cms.service.StandardItemService;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 通知
 */
@Controller
@RequestMapping(value = "/custom/cms/standardItem")
public class StandardItemController {

    @Autowired
    private StandardItemService standardItemService;
    @Autowired
    private CmsService cmsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<StandardItem> page, HttpServletRequest request) {
        User user = cmsService.getUser(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = standardItemService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/standardItemList");
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("cms/standardItemEdit");
        StandardItem entity = new StandardItem();
        User user = cmsService.getUser(request);
        entity.setCreaterId(user.getId());
        entity.setCreateTime(new Date());
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) {
        StandardItem entity = standardItemService.findById(id);
        ModelAndView mv = new ModelAndView("cms/standardItemEdit");
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        StandardItem entity = standardItemService.findById(id);
        ModelAndView mv = new ModelAndView("cms/standardItemView");
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(StandardItem entity) {
        standardItemService.saveOrUpdate(entity);
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/standardItem");
        return mv;
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        standardItemService.delete(id);
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/standardItem");
        return mv;
    }

}
