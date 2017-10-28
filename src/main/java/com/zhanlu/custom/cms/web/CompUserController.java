package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.CompUser;
import com.zhanlu.custom.cms.service.CmsService;
import com.zhanlu.custom.cms.service.CompUserService;
import com.zhanlu.custom.cms.service.DrugCompService;
import com.zhanlu.custom.cms.service.MeasureCompService;
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
 * 用户
 */
@Controller
@RequestMapping(value = "/custom/cms/compUser")
public class CompUserController {

    @Autowired
    private CompUserService compUserService;
    @Autowired
    private MeasureCompService measureCompService;
    @Autowired
    private DrugCompService drugCompService;
    @Autowired
    private CmsService cmsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<CompUser> page, HttpServletRequest request) {
        User user = cmsService.getUser(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = compUserService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/compUserList");
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("cms/compUserEdit");
        CompUser entity = new CompUser();
        User user = cmsService.getUser(request);
        entity.setCreaterId(user.getId());
        entity.setCreateTime(new Date());
        mv.addObject("entity", entity);

        mv.addObject("measureComps", measureCompService.findAll());
        mv.addObject("drugComps", drugCompService.findAll());
        return mv;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) {
        CompUser entity = compUserService.findById(id);
        ModelAndView mv = new ModelAndView("cms/compUserEdit");
        mv.addObject("entity", entity);
        mv.addObject("measureComps", measureCompService.findAll());
        mv.addObject("drugComps", drugCompService.findAll());
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        CompUser entity = compUserService.findById(id);
        ModelAndView mv = new ModelAndView("cms/compUserView");
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(CompUser entity) {
        if (entity.getCompType() != null) {
            if (entity.getCompType().intValue() == 2) {
                entity.setMeasureCompId(null);
            } else {
                entity.setDrugCompId(null);
            }
        } else {
            entity.setDrugCompId(null);
        }
        compUserService.saveOrUpdate(entity);
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/compUser");
        return mv;
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        compUserService.delete(id);
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/compUser");
        return mv;
    }

}
