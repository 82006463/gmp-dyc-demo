package com.zhanlu.custom.dm.web;

import com.zhanlu.custom.common.service.CustomService;
import com.zhanlu.custom.dm.entity.BackupHist;
import com.zhanlu.custom.dm.service.BackupHistService;
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
 * 备份历史
 */
@Controller
@RequestMapping(value = "/custom/dm/backupHist")
public class BackupHistController {

    @Autowired
    private BackupHistService backupHistService;
    @Autowired
    private CustomService customService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<BackupHist> page, HttpServletRequest request) {
        User user = customService.getUser(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.DESC);
        }
        page = backupHistService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("dm/backupHistList");
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        BackupHist entity = backupHistService.findById(id);
        ModelAndView mv = new ModelAndView("dm/backupHistView");
        mv.addObject("entity", entity);
        return mv;
    }

}
