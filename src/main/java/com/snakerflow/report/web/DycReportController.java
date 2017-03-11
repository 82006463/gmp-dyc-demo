package com.snakerflow.report.web;

import com.snakerflow.framework.page.Page;
import com.snakerflow.framework.page.PropertyFilter;
import com.snakerflow.report.entity.DycReport;
import com.snakerflow.report.service.DycReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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

    /**
     * 分页查询用户，返回用户列表视图
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
        return view;
    }

    /**
     * 编辑用户时，返回用户查看视图
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id, Model model) {
        model.addAttribute("report", reportService.get(id));
        return "report/reportView";
    }

}
