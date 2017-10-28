package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.MeasureComp;
import com.zhanlu.custom.cms.entity.StandardItem;
import com.zhanlu.custom.cms.service.CmsService;
import com.zhanlu.custom.cms.service.MeasureCompService;
import com.zhanlu.custom.cms.service.MeasureCompStandardItemService;
import com.zhanlu.custom.cms.service.StandardItemService;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.security.entity.User;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 计量公司
 */
@Controller
@RequestMapping(value = "/custom/cms/measureComp")
public class MeasureCompController {

    @Autowired
    private MeasureCompService measureCompService;
    @Autowired
    private StandardItemService standardItemService;
    @Autowired
    private MeasureCompStandardItemService measureCompStandardItemService;
    @Autowired
    private CmsService cmsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<MeasureComp> page, HttpServletRequest request) {
        User user = cmsService.getUser(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = measureCompService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/measureCompList");
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("cms/measureCompEdit");
        MeasureComp entity = new MeasureComp();
        User user = cmsService.getUser(request);
        entity.setCreaterId(user.getId());
        entity.setCreateTime(new Date());
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) {
        MeasureComp entity = measureCompService.findById(id);
        ModelAndView mv = new ModelAndView("cms/measureCompEdit");
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        MeasureComp entity = measureCompService.findById(id);
        ModelAndView mv = new ModelAndView("cms/measureCompView");
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(MeasureComp entity) {
        measureCompService.saveOrUpdate(entity);
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/measureComp");
        return mv;
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        measureCompService.delete(id);
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/measureComp");
        return mv;
    }

    @RequestMapping(value = "/standardItem", method = RequestMethod.GET)
    public ModelAndView standardItem(Page<StandardItem> page, HttpServletRequest request) {
        String measureCompIdStr = request.getParameter("measureCompId");
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        filters.add(new PropertyFilter("EQI_status", "1"));
        page = standardItemService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/measureCompStandardItemList");
        mv.addObject("page", page);
        mv.addObject("measureCompId", measureCompIdStr);
        Map<String, Object> params = new HashedMap();
        params.put("measureCompId", Long.parseLong(measureCompIdStr));
        mv.addObject("standardItems", measureCompStandardItemService.findAll());
        return mv;
    }

}
