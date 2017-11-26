package com.zhanlu.custom.dms.web;

import com.zhanlu.custom.cms.service.CmsService;
import com.zhanlu.custom.dms.entity.DmsFile;
import com.zhanlu.custom.dms.service.DmsFileService;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.security.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 月度外校
 */
@Slf4j
@Controller
@RequestMapping(value = "/custom/dms/file")
public class DmsFileController {

    @Autowired
    private DmsFileService dmsFileService;
    @Autowired
    private CmsService cmsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<DmsFile> page, HttpServletRequest req) {
        User user = cmsService.getUser(req);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        ModelAndView mv = new ModelAndView("dms/fileList");
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("updateTime");
            page.setOrder(Page.DESC);
        }
        page = dmsFileService.findPage(page, filters);
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable("id") Long id, Integer status) {
        DmsFile entity = dmsFileService.findById(id);
        entity.setStatus(-1);
        dmsFileService.saveOrUpdate(entity);
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/calibrationExt/" + status);
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        DmsFile entity = dmsFileService.findById(id);
        ModelAndView mv = new ModelAndView("cms/calibrationExtView");
        mv.addObject("entity", entity);
        return mv;
    }

}
