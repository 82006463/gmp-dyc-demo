package com.zhanlu.custom.dms.web;

import com.zhanlu.custom.common.service.CustomService;
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
@RequestMapping(value = "/custom/dms/file/req")
public class FileReqController {

    @Autowired
    private DmsFileService dmsFileService;
    @Autowired
    private CustomService customService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<DmsFile> page, HttpServletRequest req) {
        User user = customService.getUser(req);
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
        mv.addObject("type", "req");
        return mv;
    }

}
