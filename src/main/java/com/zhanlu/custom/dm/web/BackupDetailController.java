package com.zhanlu.custom.dm.web;

import com.zhanlu.custom.common.service.CustomService;
import com.zhanlu.custom.dm.entity.BackupDetail;
import com.zhanlu.custom.dm.service.BackupDetailService;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 备份文档
 */
@Controller
@RequestMapping(value = "/custom/dm/backupDoc")
public class BackupDetailController {

    @Autowired
    private BackupDetailService backupDetailService;
    @Autowired
    private CustomService customService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<BackupDetail> page, HttpServletRequest request) {
        User user = customService.getUser(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.DESC);
        }
        page = backupDetailService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("dm/backupDetailList");
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView view(Integer bakType, Long id) {
        BackupDetail entity = new BackupDetail();
        if (bakType != null && bakType.intValue() > 0) {
            List<PropertyFilter> filters = new ArrayList<>();
            filters.add(new PropertyFilter("EQI_bakType", bakType.toString()));
            filters.add(new PropertyFilter("EQL_bakTypeId", id.toString()));
            Page<BackupDetail> page = new Page<>(10);
            page = backupDetailService.findPage(page, filters);
            if (page.getResult() != null && page.getResult().size() > 0) {
                entity = page.getResult().get(0);
            }
        } else {
            entity = backupDetailService.findById(id);
        }
        ModelAndView mv = new ModelAndView("dm/backupDetailView");
        mv.addObject("entity", entity);
        return mv;
    }

}
