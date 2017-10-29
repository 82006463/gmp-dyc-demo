package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.CalibrationIn;
import com.zhanlu.custom.cms.service.CalibrationInService;
import com.zhanlu.custom.cms.service.CmsService;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 月度内校
 */
@Controller
@RequestMapping(value = "/custom/cms/calibrationIn")
public class CalibrationInController {

    @Autowired
    private CalibrationInService calibrationInService;
    @Autowired
    private CmsService cmsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<CalibrationIn> page, HttpServletRequest request) {
        User user = cmsService.getUser(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("createTime");
            page.setOrder(Page.DESC);
        }
        page = calibrationInService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/calibrationPlanList");
        mv.addObject("page", page);
        mv.addObject("type", "calibrationIn");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/generateTask", method = RequestMethod.GET)
    public Object generateTask(HttpServletRequest request) {
        User user = cmsService.getUser(request);
        return calibrationInService.generateTask(user);
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        CalibrationIn entity = calibrationInService.findById(id);
        ModelAndView mv = new ModelAndView("cms/calibrationInView");
        mv.addObject("entity", entity);
        return mv;
    }

}
