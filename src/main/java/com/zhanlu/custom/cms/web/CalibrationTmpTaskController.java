package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.CalibrationTmpTask;
import com.zhanlu.custom.cms.entity.CompNotify;
import com.zhanlu.custom.cms.service.CalibrationTmpTaskService;
import com.zhanlu.custom.cms.service.CompNotifyService;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 通知
 */
@Controller
@RequestMapping(value = "/custom/cms/calibrationTmpTask")
public class CalibrationTmpTaskController {

    @Autowired
    private CalibrationTmpTaskService calibrationTmpTaskService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<CalibrationTmpTask> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = calibrationTmpTaskService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/calibrationTmpTaskList");
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        CalibrationTmpTask entity = calibrationTmpTaskService.findById(id);
        ModelAndView mv = new ModelAndView("cms/calibrationTmpTaskView");
        mv.addObject("entity", entity);
        return mv;
    }

}
