package com.zhanlu.report.web;

import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.security.entity.Org;
import com.zhanlu.framework.security.service.OrgService;
import com.zhanlu.report.entity.DycReport;
import com.zhanlu.report.service.DycReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 报表Controller
 *
 * @author zhanlu
 * @date 2017-03-11
 * @since 0.1
 */
@Controller
@RequestMapping(value = "/dyc/report")
public class DycReportController {

    @Autowired
    private DycReportService reportService;
    @Autowired
    private OrgService orgService;

    /**
     * 分页列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(DycReport entity, Page<DycReport> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQS_processType", entity.getProcessType()));
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = reportService.findPage(page, filters);

        ModelAndView view = new ModelAndView("report/reportList");
        view.addObject("page", page);
        view.addObject("orgList", orgService.findAll());
        return view;
    }

    /**
     * 新建页面
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView create(DycReport entity) {
        ModelAndView view = new ModelAndView("report/reportEdit");
        view.addObject("entity", entity);
        view.addObject("orgList", orgService.findAll());
        return view;
    }

    /**
     * 编辑页面
     */
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) {
        ModelAndView view = new ModelAndView("/report/reportEdit");
        view.addObject("entity", reportService.get(id));
        view.addObject("orgList", orgService.findAll());
        return view;
    }

    /**
     * 新增、编辑的提交处理。保存实体，并返回列表视图
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ModelAndView update(RedirectAttributes attributes, DycReport entity) {
        reportService.save(entity);
        ModelAndView view = new ModelAndView("redirect:/dyc/report/list");
        attributes.addAttribute("processType", entity.getProcessType());
        return view;
    }

    /**
     * 查看页面
     */
    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        ModelAndView view = new ModelAndView("report/reportView");
        view.addObject("entity", reportService.get(id));
        return view;
    }

    /**
     * 根据主键ID删除实体，并返回列表视图
     */
    @RequestMapping(value = "delete/{id}")
    public ModelAndView delete(RedirectAttributes attributes, @PathVariable("id") Long id) {
        String processType = reportService.get(id).getProcessType();
        reportService.delete(id);
        ModelAndView view = new ModelAndView("redirect:/dyc/report/list");
        attributes.addAttribute("processType", processType);
        return view;
    }

}
