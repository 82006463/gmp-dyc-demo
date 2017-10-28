package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.CalibrationHist;
import com.zhanlu.custom.cms.service.CalibrationHistService;
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
 * 校准历史
 */
@Controller
@RequestMapping(value = "/custom/cms/calibrationHist")
public class CalibrationHistController {

    @Autowired
    private CalibrationHistService calibrationHistService;
    @Autowired
    private CmsService cmsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<CalibrationHist> page, HttpServletRequest request) {
        User user = cmsService.getUser(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("createTime");
            page.setOrder(Page.DESC);
        }
        page = calibrationHistService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/calibrationHistList");
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        CalibrationHist entity = calibrationHistService.findById(id);
        ModelAndView mv = new ModelAndView("cms/calibrationHistView");
        mv.addObject("entity", entity);
        return mv;
    }

}
