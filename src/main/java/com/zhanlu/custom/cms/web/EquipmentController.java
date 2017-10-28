package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.Equipment;
import com.zhanlu.custom.cms.service.CmsService;
import com.zhanlu.custom.cms.service.EquipmentService;
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
@RequestMapping(value = "/custom/cms/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private CmsService cmsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<Equipment> page, HttpServletRequest request) {
        User user = cmsService.getUser(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.DESC);
        }
        page = equipmentService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/equipmentList");
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView mv = new ModelAndView("cms/equipmentEdit");
        Equipment entity = new Equipment();
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) {
        Equipment entity = equipmentService.findById(id);
        ModelAndView mv = new ModelAndView("cms/equipmentEdit");
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(Equipment entity, HttpServletRequest req) {
        User user = cmsService.getUser(req);
        if (entity.getId() == null) {
            entity.setCreaterId(user.getId());
            entity.setCreateTime(new Date());
            entity.setTenantId(user.getOrg().getId());
        } else {
            entity.setUpdaterId(user.getId());
            entity.setUpdateTime(new Date());
        }
        equipmentService.saveOrUpdate(entity);
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/equipment");
        return mv;
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id, HttpServletRequest req) {
        Equipment entity = equipmentService.findById(id);
        entity.setStatus(0);
        return this.update(entity, req);
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        Equipment entity = equipmentService.findById(id);
        ModelAndView mv = new ModelAndView("cms/equipmentView");
        mv.addObject("entity", entity);
        return mv;
    }
}
