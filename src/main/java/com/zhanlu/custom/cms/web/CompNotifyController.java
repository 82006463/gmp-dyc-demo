package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.CompNotify;
import com.zhanlu.custom.cms.service.CmsService;
import com.zhanlu.custom.cms.service.CompNotifyService;
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
@RequestMapping(value = "/custom/cms/compNotify")
public class CompNotifyController {

    @Autowired
    private CompNotifyService compNotifyService;
    @Autowired
    private CmsService cmsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<CompNotify> page, HttpServletRequest request) {
        User user = cmsService.getUser(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
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
        CompNotify entity = new CompNotify();
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) {
        CompNotify entity = compNotifyService.findById(id);
        ModelAndView mv = new ModelAndView("cms/compNotifyEdit");
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(CompNotify entity, HttpServletRequest req) {
        User user = cmsService.getUser(req);
        if (entity.getId() == null) {
            entity.setCreaterId(user.getId());
            entity.setCreateTime(new Date());
            entity.setTenantId(user.getOrg().getId());
        } else {
            entity.setUpdaterId(user.getId());
            entity.setUpdateTime(new Date());
        }
        compNotifyService.saveOrUpdate(entity);
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/compNotify");
        return mv;
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id, HttpServletRequest req) {
        CompNotify entity = compNotifyService.findById(id);
        entity.setStatus(0);
        return this.update(entity, req);
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        CompNotify entity = compNotifyService.findById(id);
        ModelAndView mv = new ModelAndView("cms/compNotifyView");
        mv.addObject("entity", entity);
        return mv;
    }
}
