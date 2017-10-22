package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.CalibrationInTask;
import com.zhanlu.custom.cms.service.CalibrationInTaskService;
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
 * 月度内校任务
 */
@Controller
@RequestMapping(value = "/custom/cms/calibrationInTask")
public class CalibrationInTaskController {

    @Autowired
    private CalibrationInTaskService calibrationInTaskService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<CalibrationInTask> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = calibrationInTaskService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/calibrationInTaskList");
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        CalibrationInTask entity = calibrationInTaskService.findById(id);
        ModelAndView mv = new ModelAndView("cms/calibrationInTaskView");
        mv.addObject("entity", entity);
        return mv;
    }

}
