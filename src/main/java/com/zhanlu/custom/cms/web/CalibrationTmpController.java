package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.CalibrationTmp;
import com.zhanlu.custom.cms.service.CalibrationTmpService;
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
 * 月度临校
 */
@Controller
@RequestMapping(value = "/custom/cms/calibrationTmp")
public class CalibrationTmpController {

    @Autowired
    private CalibrationTmpService calibrationTmpService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<CalibrationTmp> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = calibrationTmpService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/calibrationTmpList");
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        CalibrationTmp entity = calibrationTmpService.findById(id);
        ModelAndView mv = new ModelAndView("cms/calibrationTmpView");
        mv.addObject("entity", entity);
        return mv;
    }

}
