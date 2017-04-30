package com.zhanlu.meta.web;

import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.config.service.DataDictService;
import com.zhanlu.framework.nosql.service.MongoService;
import com.zhanlu.framework.nosql.util.BasicUtils;
import com.zhanlu.framework.nosql.util.EditItem;
import com.zhanlu.framework.nosql.util.QueryItem;
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
import java.util.ArrayList;
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
@RequestMapping(value = "/meta")
public class MetaController {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataDictService dataDictService;

    @Autowired
    private MongoService mongoService;
    private String metaTagTable = "config_meta_tag";

    /**
     * 分页列表
     */
    @RequestMapping(value = "{metaType}/{type}/list", method = RequestMethod.GET)
    public ModelAndView list(Page<?> page, @PathVariable("metaType") String metaType, @PathVariable("type") String type, HttpServletRequest req) throws Exception {
        Map<String, String[]> paramMap = req.getParameterMap();
        List<QueryItem> queryItems = new ArrayList<>(1);
        queryItems.add(new QueryItem("Eq_String_code", metaType + "_" + type));
        Map<String, Object> metaTag = mongoService.findOne(metaTagTable, queryItems);

        queryItems = QueryItem.buildSearchItems(paramMap);
        queryItems.add(new QueryItem("Eq_String_type", type));
        List<Map<String, Object>> entityList = mongoService.findByPage("meta_" + metaType, queryItems, page);

        ModelAndView view = new ModelAndView("meta/metaList");
        view.addObject("jsonSearch", BasicUtils.jsonSearch(dataDictService, jdbcTemplate, metaTag, paramMap));
        view.addObject("jsonList", BasicUtils.jsonList(req.getContextPath(), metaTag, entityList));
        view.addObject("page", page);
        view.addObject("metaTag", metaTag);
        return view;
    }

    /**
     * 新建页面
     */
    @RequestMapping(value = "{metaType}/{type}/create", method = RequestMethod.GET)
    public ModelAndView create(@PathVariable("metaType") String metaType, @PathVariable("type") String type) throws Exception {
        List<QueryItem> queryItems = new ArrayList<>(1);
        queryItems.add(new QueryItem("Eq_String_code", metaType + "_" + type));
        Map<String, Object> metaTag = mongoService.findOne(metaTagTable, queryItems);

        ModelAndView view = new ModelAndView("meta/metaEdit");
        Map<String, Object> entity = new HashMap<>();
        entity.put("type", type);
        view.addObject("jsonEdit", BasicUtils.jsonEdit(jdbcTemplate, dataDictService, metaTag, entity));
        view.addObject("entity", entity);
        view.addObject("metaTag", metaTag);
        return view;
    }

    /**
     * 编辑页面
     */
    @RequestMapping(value = "{metaType}/{type}/update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("metaType") String metaType, @PathVariable("type") String type, @PathVariable("id") String id) throws Exception {
        List<QueryItem> queryItems = new ArrayList<>(1);
        queryItems.add(new QueryItem("Eq_String_code", metaType + "_" + type));
        Map<String, Object> metaTag = mongoService.findOne(metaTagTable, queryItems);

        ModelAndView view = new ModelAndView("meta/metaEdit");
        Map<String, Object> entity = mongoService.findOne("meta_" + metaType, id);
        view.addObject("jsonEdit", BasicUtils.jsonEdit(jdbcTemplate, dataDictService, metaTag, entity));
        view.addObject("entity", entity);
        view.addObject("metaTag", metaTag);
        return view;
    }

    /**
     * 新增、编辑的提交处理。保存实体，并返回列表视图
     */
    @RequestMapping(value = "{metaType}/{type}/update", method = RequestMethod.POST)
    public ModelAndView update(RedirectAttributes attributes, @PathVariable("metaType") String metaType, @PathVariable("type") String type, String id, HttpServletRequest req) throws Exception {
        List<QueryItem> queryItems = new ArrayList<>(1);
        queryItems.add(new QueryItem("Eq_String_code", metaType + "_" + type));
        Map<String, Object> metaTag = mongoService.findOne(metaTagTable, queryItems);

        Map<String, Object> entity = EditItem.toMap(dataDictService, (List<Map<String, String>>) metaTag.get("editItems"), req.getParameterMap());
        entity.put("type", type);
        mongoService.saveOrUpdate("meta_" + metaType, id, entity);
        ModelAndView view = new ModelAndView("redirect:/meta/logBook/" + type + "/list");
        attributes.addAttribute("type", type);
        return view;
    }

    /**
     * 查看页面
     */
    @RequestMapping(value = "{metaType}/{type}/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("metaType") String metaType, @PathVariable("type") String type, @PathVariable("id") String id) throws Exception {
        List<QueryItem> queryItems = new ArrayList<>(1);
        queryItems.add(new QueryItem("Eq_String_code", metaType + "_" + type));
        Map<String, Object> metaTag = mongoService.findOne(metaTagTable, queryItems);

        ModelAndView view = new ModelAndView("meta/metaView");
        Map<String, Object> entity = mongoService.findOne("meta_" + metaType, id);
        view.addObject("entity", entity);
        view.addObject("jsonEdit", BasicUtils.jsonView(dataDictService, metaTag, entity));
        view.addObject("metaTag", metaTag);
        return view;
    }

    /**
     * 根据主键ID删除实体，并返回列表视图
     */
    @RequestMapping(value = "{metaType}/{type}/delete/{id}")
    public ModelAndView delete(RedirectAttributes attributes, @PathVariable("metaType") String metaType, @PathVariable("type") String type, @PathVariable("id") String id) {
        mongoService.removeOne("meta_" + metaType, id);
        ModelAndView view = new ModelAndView("redirect:/meta/" + metaType + "/" + type + "/list");
        attributes.addAttribute("type", type);
        return view;
    }

}
