package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.CalibrationYear;
import com.zhanlu.custom.cms.service.CalibrationYearService;
import com.zhanlu.custom.cms.service.CmsService;
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
import java.util.List;

/**
 * 年校
 */
@Controller
@RequestMapping(value = "/custom/cms/calibrationYear")
public class CalibrationYearController {

    @Autowired
    private CalibrationYearService calibrationYearService;
    @Autowired
    private CmsService cmsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<CalibrationYear> page, HttpServletRequest request) {
        User user = cmsService.getUser(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("createTime");
            page.setOrder(Page.DESC);
        }
        page = calibrationYearService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/calibrationYearList");
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        CalibrationYear entity = calibrationYearService.findById(id);
        ModelAndView mv = new ModelAndView("cms/calibrationYearView");
        mv.addObject("entity", entity);
        return mv;
    }

}
