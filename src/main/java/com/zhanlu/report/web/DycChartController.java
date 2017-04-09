package com.zhanlu.report.web;

import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.common.utils.ChartUtils;
import com.zhanlu.framework.config.entity.ElasticTable;
import com.zhanlu.framework.config.service.DataDictService;
import com.zhanlu.framework.config.service.ElastictTableService;
import com.zhanlu.report.entity.DycChart;
import com.zhanlu.report.service.DycChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
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
@RequestMapping(value = "/dyc/chart")
public class DycChartController {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DycChartService chartService;
    @Autowired
    private DataDictService dataDictService;
    @Autowired
    private ElastictTableService elastictTableService;

    /**
     * 分页列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(DycChart entity, Page<DycChart> page, HttpServletRequest req) throws Exception {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
        filters.add(new PropertyFilter("EQS_type", entity.getType()));
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = chartService.findPage(page, filters);
        ModelAndView view = new ModelAndView("report/chartList");
        view.addObject("page", page);

        ElasticTable etab = elastictTableService.findByCode("chartType_" + entity.getType());
        view.addObject("jsonSearch", ChartUtils.jsonSearch(jdbcTemplate, dataDictService, etab, req.getParameterMap()));
        view.addObject("jsonList", ChartUtils.jsonList(jdbcTemplate, dataDictService, etab));
        return view;
    }

    /**
     * 新建页面
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView create(DycChart entity) throws Exception {
        ModelAndView view = new ModelAndView("report/chartEdit");
        view.addObject("entity", entity);
        view.addObject("chartType", dataDictService.findByCode("chartType_" + entity.getType()));
        return view;
    }

    /**
     * 编辑页面
     */
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) throws Exception {
        ModelAndView view = new ModelAndView("report/chartEdit");
        DycChart entity = chartService.findById(id);
        view.addObject("entity", entity);
        view.addObject("chartType", dataDictService.findByCode("chartType_" + entity.getType()));
        return view;
    }

    /**
     * 新增、编辑的提交处理。保存实体，并返回列表视图
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ModelAndView update(RedirectAttributes attributes, DycChart entity) throws Exception {
        chartService.saveOrUpdate(entity);
        attributes.addAttribute("type", entity.getType());
        ModelAndView view = new ModelAndView("redirect:/dyc/chart/list");
        return view;
    }

    /**
     * 查看页面
     */
    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) throws Exception {
        ModelAndView view = new ModelAndView("report/chartView");
        DycChart entity = chartService.findById(id);
        view.addObject("entity", entity);
        view.addObject("chartType", dataDictService.findByCode("chartType_" + entity.getType()));
        return view;
    }

    /**
     * 根据主键ID删除实体，并返回列表视图
     */
    @RequestMapping(value = "delete/{id}")
    public ModelAndView delete(RedirectAttributes attributes, @PathVariable("id") Long id) {
        chartService.delete(id);
        ModelAndView view = new ModelAndView("redirect:/dyc/chart/list");
        return view;
    }

}
