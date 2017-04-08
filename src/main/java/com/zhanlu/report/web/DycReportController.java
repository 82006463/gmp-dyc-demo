package com.zhanlu.report.web;

import com.alibaba.fastjson.JSON;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.common.utils.ETab2HTMLUtils;
import com.zhanlu.framework.config.entity.DataDict;
import com.zhanlu.framework.config.entity.ElasticTable;
import com.zhanlu.framework.config.service.DataDictService;
import com.zhanlu.framework.config.service.ElastictTableService;
import com.zhanlu.framework.security.service.OrgService;
import com.zhanlu.report.entity.DycReport;
import com.zhanlu.report.service.DycReportService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
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

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DataDictService dataDictService;

    @Autowired
    private DycReportService reportService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private ElastictTableService wfcReportService;

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
    public ModelAndView create(DycReport entity) throws Exception {
        ModelAndView view = new ModelAndView("report/reportEdit");
        view.addObject("entity", entity);
        view.addObject("orgList", orgService.findAll());
        ElasticTable etab = wfcReportService.findByCode(entity.getProcessType());
        view.addObject("extAttr", ETab2HTMLUtils.jsonEdit(applicationContext, etab.getJsonEdit(), entity.getExtraJson()));
        return view;
    }

    /**
     * 编辑页面
     */
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) throws Exception {
        ModelAndView view = new ModelAndView("/report/reportEdit");
        DycReport entity = reportService.findById(id);
        view.addObject("entity", entity);
        view.addObject("orgList", orgService.findAll());
        ElasticTable etab = wfcReportService.findByCode(entity.getProcessType());
        view.addObject("extAttr", ETab2HTMLUtils.jsonEdit(applicationContext, etab.getJsonEdit(), entity.getExtraJson()));
        return view;
    }

    /**
     * 新增、编辑的提交处理。保存实体，并返回列表视图
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ModelAndView update(RedirectAttributes attributes, HttpServletRequest req, DycReport entity) throws Exception {
        Map<String, String[]> paramMap = req.getParameterMap();
        Map<String, Object> dataMap = new LinkedHashMap<>(paramMap.size());
        ElasticTable etab = wfcReportService.findByCode(entity.getProcessType());
        List<Map<String, Object>> structList = JSON.parseObject(etab.getJsonEdit(), List.class);
        for (Map<String, Object> struct : structList) {
            String code = struct.get("code").toString();
            String dataType = struct.get("dataType").toString().replace("dataType_", "");
            String tagType = struct.get("tagType").toString().replace("tagType_", "");
            if (tagType.equalsIgnoreCase("subForm")) {
                DataDict dataDict = dataDictService.findByCode(struct.get("subForm").toString());
                if (dataDict != null && StringUtils.isNotBlank(dataDict.getDataSource())) {
                    String[] fieldArr = dataDict.getDataSource().split(",");
                    for (String field : fieldArr) {
                        String[] tmpField = field.split(":");
                        dataMap.put(tmpField[0], paramMap.get(tmpField[0]));
                    }
                }
            } else if (paramMap.containsKey(code) && StringUtils.isNotBlank(paramMap.get(code)[0])) {
                String tmpData = paramMap.get(code)[0];
                if (dataType.equals("int")) {
                    dataMap.put(code, Integer.parseInt(tmpData));
                } else if (dataType.equals("long")) {
                    dataMap.put(code, Long.parseLong(tmpData));
                } else if (dataType.equals("float")) {
                    dataMap.put(code, Float.parseFloat(tmpData));
                } else if (dataType.equals("double")) {
                    dataMap.put(code, Double.parseDouble(tmpData));
                } else {
                    dataMap.put(code, tmpData);
                }
            }
        }
        entity.setExtraJson(JSON.toJSONString(dataMap));
        reportService.saveOrUpdate(entity);
        ModelAndView view = new ModelAndView("redirect:/dyc/report/list");
        attributes.addAttribute("processType", entity.getProcessType());
        return view;
    }

    /**
     * 查看页面
     */
    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) throws Exception {
        ModelAndView view = new ModelAndView("report/reportView");
        DycReport entity = reportService.findById(id);
        view.addObject("entity", entity);
        ElasticTable etab = wfcReportService.findByCode(entity.getProcessType());
        view.addObject("extAttr", ETab2HTMLUtils.jsonView(applicationContext, etab.getJsonEdit(), entity.getExtraJson()));
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
