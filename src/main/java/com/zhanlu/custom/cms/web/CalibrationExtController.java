package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.CalibrationExt;
import com.zhanlu.custom.cms.service.CalibrationExtService;
import com.zhanlu.custom.cms.service.CmsService;
import com.zhanlu.custom.cms.service.MeasureCompService;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.security.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
 * 月度外校
 */
@Slf4j
@Controller
@RequestMapping(value = "/custom/cms/calibrationExt")
public class CalibrationExtController {

    @Autowired
    private MeasureCompService measureCompService;
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
        mv.addObject("type", "calibrationExt");
        if (status != null && status.intValue() == 2) {
            filters.add(new PropertyFilter("EQI_status", status.toString()));
            mv.addObject("status", 2);
            mv.addObject("measureComps", measureCompService.findList(null));
        } else {
            mv.addObject("status", 1);
        }
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.DESC);
        }
        page = calibrationExtService.findPage(page, filters);
        mv.addObject("page", page);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/generateTask", method = RequestMethod.GET)
    public Object generateTask(HttpServletRequest request) {
        User user = cmsService.getUser(request);
        return calibrationExtService.generateTask(user);
    }

    @ResponseBody
    @RequestMapping(value = "/sendTask", method = RequestMethod.GET)
    public Object sendTask(HttpServletRequest req) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("result", 0);
        String measureCompId = req.getParameter("measureCompId");
        String approver = req.getParameter("approver");
        User user = cmsService.getUser(approver);
        if (StringUtils.isBlank(measureCompId)) {
            resultMap.put("msg", "请选择计量公司");
        } else if (StringUtils.isBlank(approver)) {
            resultMap.put("msg", "请输入计量公司负责人");
        } else if (user == null) {
            resultMap.put("msg", "计量公司负责人账号不存在");
        }
        if (resultMap.size() > 1) {
            return resultMap;
        }
        resultMap = calibrationExtService.sendTask(user, Long.parseLong(measureCompId), approver);
        return resultMap;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        CalibrationExt entity = calibrationExtService.findById(id);
        ModelAndView mv = new ModelAndView("cms/calibrationExtView");
        mv.addObject("entity", entity);
        return mv;
    }

}
