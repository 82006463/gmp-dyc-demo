package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.CalibrationExt;
import com.zhanlu.custom.cms.entity.CalibrationIn;
import com.zhanlu.custom.cms.entity.CalibrationTask;
import com.zhanlu.custom.cms.entity.CalibrationTmp;
import com.zhanlu.custom.cms.service.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 月度外校任务
 */
@Controller
@RequestMapping(value = "/custom/cms/calibrationTask")
public class CalibrationTaskController {

    @Autowired
    private MeasureCompService measureCompService;
    @Autowired
    private CalibrationInService calibrationInService;
    @Autowired
    private CalibrationExtService calibrationExtService;
    @Autowired
    private CalibrationTmpService calibrationTmpService;
    @Autowired
    private CalibrationTaskService calibrationTaskService;
    @Autowired
    private CmsService cmsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<CalibrationTask> page, HttpServletRequest req) {
        User user = cmsService.getUser(req);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.DESC);
        }
        page = calibrationTaskService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/calibrationTaskList");
        mv.addObject("page", page);
        mv.addObject("measureComps", measureCompService.findList(null));
        return mv;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, HttpServletRequest req) {
        ModelAndView mv = new ModelAndView("cms/calibrationTaskEdit");
        CalibrationTask entity = calibrationTaskService.findById(id);
        mv.addObject("entity", entity);

        User user = cmsService.getUser(req);
        List<PropertyFilter> filters = new ArrayList<>();
        filters.add(new PropertyFilter("EQL_taskId", id.toString()));
        if (entity.getCalibrationMode().intValue() == 1) {
            Page<CalibrationIn> page = new Page<>(Integer.MAX_VALUE);
            calibrationInService.findPage(page, filters);
            mv.addObject("children", page.getResult());
        } else if (entity.getCalibrationMode().intValue() == 2) {
            Page<CalibrationExt> page = new Page<>(Integer.MAX_VALUE);
            calibrationExtService.findPage(page, filters);
            mv.addObject("children", page.getResult());
        } else if (entity.getCalibrationMode().intValue() == 3) {
            Page<CalibrationTmp> page = new Page<>(Integer.MAX_VALUE);
            calibrationTmpService.findPage(page, filters);
            mv.addObject("children", page.getResult());
        }
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(Long id, HttpServletRequest req) {
        CalibrationTask entity = calibrationTaskService.findById(id);
        Map<String, String[]> paramMap = req.getParameterMap();
        String[] ids = paramMap.get("ids");
        return null;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("cms/calibrationTaskView");
        CalibrationTask entity = calibrationTaskService.findById(id);
        List<PropertyFilter> filters = new ArrayList<>();
        filters.add(new PropertyFilter("EQL_taskId", id.toString()));
        if (entity.getCalibrationMode().intValue() == 1) {
            Page<CalibrationIn> page = new Page<>(Integer.MAX_VALUE);
            calibrationInService.findPage(page, filters);
            mv.addObject("children", page.getResult());
        } else if (entity.getCalibrationMode().intValue() == 2) {
            Page<CalibrationExt> page = new Page<>(Integer.MAX_VALUE);
            calibrationExtService.findPage(page, filters);
            mv.addObject("children", page.getResult());
        } else if (entity.getCalibrationMode().intValue() == 3) {
            Page<CalibrationTmp> page = new Page<>(Integer.MAX_VALUE);
            calibrationTmpService.findPage(page, filters);
            mv.addObject("children", page.getResult());
        }
        return mv;
    }

}
