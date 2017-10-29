package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.CalibrationTask;
import com.zhanlu.custom.cms.service.CalibrationTaskService;
import com.zhanlu.custom.cms.service.CmsService;
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
import java.util.List;

/**
 * 月度外校任务
 */
@Controller
@RequestMapping(value = "/custom/cms/calibrationTask")
public class CalibrationTaskController {

    @Autowired
    private MeasureCompService measureCompService;
    @Autowired
    private CalibrationTaskService calibrationTaskService;
    @Autowired
    private CmsService cmsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<CalibrationTask> page, HttpServletRequest request) {
        User user = cmsService.getUser(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("createTime");
            page.setOrder(Page.DESC);
        }
        page = calibrationTaskService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/calibrationTaskList");
        mv.addObject("page", page);
        mv.addObject("measureComps", measureCompService.findList(null));
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        CalibrationTask entity = calibrationTaskService.findById(id);
        ModelAndView mv = new ModelAndView("cms/calibrationTaskView");
        mv.addObject("entity", entity);
        return mv;
    }

}
