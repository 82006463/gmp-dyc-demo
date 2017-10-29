package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.CalibrationExt;
import com.zhanlu.custom.cms.service.CalibrationExtService;
import com.zhanlu.custom.cms.service.CmsService;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.security.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 月度外校
 */
@Slf4j
@Controller
@RequestMapping(value = "/custom/cms/calibrationExt")
public class CalibrationExtController {

    @Autowired
    private CalibrationExtService calibrationExtService;
    @Autowired
    private CmsService cmsService;

    @RequestMapping(value = "/{status}", method = RequestMethod.GET)
    public ModelAndView list(Page<CalibrationExt> page, @PathVariable Integer status, HttpServletRequest request) {
        User user = cmsService.getUser(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        ModelAndView mv = new ModelAndView("cms/calibrationPlanList");
        if (status != null && status.intValue() == 2) {
            filters.add(new PropertyFilter("EQI_status", status.toString()));
            mv.addObject("type", "calibrationExt2");
            mv.addObject("status", 2);
        } else {
            mv.addObject("type", "calibrationExt");
            mv.addObject("status", 1);
        }
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("createTime");
            page.setOrder(Page.DESC);
        }
        page = calibrationExtService.findPage(page, filters);
        mv.addObject("page", page);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/{status}/generateTask", method = RequestMethod.GET)
    public Object generateTask(@PathVariable Integer status, HttpServletRequest request) {
        User user = cmsService.getUser(request);
        return calibrationExtService.generateTask(user);
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        CalibrationExt entity = calibrationExtService.findById(id);
        ModelAndView mv = new ModelAndView("cms/calibrationExtView");
        mv.addObject("entity", entity);
        return mv;
    }

}
