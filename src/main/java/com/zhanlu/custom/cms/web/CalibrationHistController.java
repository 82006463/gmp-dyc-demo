package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.CalibrationHist;
import com.zhanlu.custom.cms.service.CalibrationHistService;
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
@RequestMapping(value = "/custom/cms/calibrationHist")
public class CalibrationHistController {

    @Autowired
    private CalibrationHistService calibrationHistService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<CalibrationHist> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
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
