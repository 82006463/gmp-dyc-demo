package com.zhanlu.report.web;

import com.alibaba.fastjson.JSON;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.config.entity.ElasticTable;
import com.zhanlu.framework.config.service.DataDictService;
import com.zhanlu.framework.config.service.ElastictTableService;
import com.zhanlu.framework.nosql.service.MongoService;
import com.zhanlu.framework.nosql.util.BasicUtils;
import com.zhanlu.framework.nosql.util.EditItem;
import com.zhanlu.framework.nosql.util.QueryItem;
import com.zhanlu.report.entity.DycReport;
import com.zhanlu.report.service.DycReportService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataDictService dataDictService;

    @Autowired
    private DycReportService reportService;
    @Autowired
    private ElastictTableService wfcReportService;

    @Autowired
    private MongoService mongoService;

    /**
     * 分页列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(Page<DycReport> page, HttpServletRequest req) throws Exception {
        String processType = req.getParameter("processType");
        Map<String, String[]> paramMap = req.getParameterMap();

        ElasticTable etab = wfcReportService.findByCode("reportType_" + processType);
        List<Map<String, String>> searchItems = JSON.parseObject(etab.getJsonSearch(), List.class);
        List<Map<String, String>> listItems = JSON.parseObject(etab.getJsonList(), List.class);

        List<QueryItem> queryItems = QueryItem.buildSearchItems(paramMap);
        queryItems.add(new QueryItem("Eq_String_processType", processType));
        List<Map<String, Object>> entityList = mongoService.findByPage("dyc_report", queryItems, page);
        ModelAndView view = new ModelAndView("report/reportList");
        view.addObject("jsonSearch", BasicUtils.jsonSearch(dataDictService, jdbcTemplate, searchItems, paramMap));
        view.addObject("jsonList", BasicUtils.jsonList(listItems, entityList));
        view.addObject("page", page);
        return view;
    }

    /**
     * 新建页面
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView create(String processType) throws Exception {
        ModelAndView view = new ModelAndView("report/reportEdit");
        Map<String, Object> entity = new HashMap<>();
        ElasticTable etab = wfcReportService.findByCode("reportType_" + processType);
        entity.put("processType", processType);
        view.addObject("jsonEdit", BasicUtils.jsonEdit(jdbcTemplate, dataDictService, etab, entity));
        view.addObject("entity", entity);
        return view;
    }

    /**
     * 编辑页面
     */
    @RequestMapping(value = "update/{_id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("_id") String _id) throws Exception {
        ModelAndView view = new ModelAndView("/report/reportEdit");
        Map<String, Object> entity = mongoService.findOne("dyc_report", _id);
        ElasticTable etab = wfcReportService.findByCode("reportType_" + entity.get("processType"));
        view.addObject("jsonEdit", BasicUtils.jsonEdit(jdbcTemplate, dataDictService, etab, entity));
        view.addObject("entity", entity);
        return view;
    }

    /**
     * 新增、编辑的提交处理。保存实体，并返回列表视图
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ModelAndView update(RedirectAttributes attributes, HttpServletRequest req) throws Exception {
        String processType = req.getParameter("processType");
        ElasticTable etab = wfcReportService.findByCode("reportType_" + processType);
        Map<String, Object> entity = EditItem.toMap(JSON.parseObject(etab.getJsonEdit(), List.class), req.getParameterMap());
        entity.put("processType", processType);
        mongoService.saveOrUpdate("dyc_report", req.getParameter("_id"), entity);
        ModelAndView view = new ModelAndView("redirect:/dyc/report/list");
        attributes.addAttribute("processType", processType);
        return view;
    }

    /**
     * 查看页面
     */
    @RequestMapping(value = "view/{_id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("_id") String _id) throws Exception {
        ModelAndView view = new ModelAndView("report/reportView");
        Map<String, Object> report = mongoService.findOne("dyc_report", _id);
        view.addObject("entity", report);
        ElasticTable etab = wfcReportService.findByCode("reportType_" + report.get("processType"));
        view.addObject("jsonEdit", BasicUtils.jsonView(dataDictService, etab, report));
        view.addObject("etab", etab);
        return view;
    }

    /**
     * 根据主键ID删除实体，并返回列表视图
     */
    @RequestMapping(value = "delete/{id}")
    public ModelAndView delete(RedirectAttributes attributes, @PathVariable("id") Long id) {
        String processType = reportService.findById(id).getProcessType();
        reportService.delete(id);
        ModelAndView view = new ModelAndView("redirect:/dyc/report/list");
        attributes.addAttribute("processType", processType);
        return view;
    }

}
