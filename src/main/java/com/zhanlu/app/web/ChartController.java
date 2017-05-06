package com.zhanlu.app.web;

import com.alibaba.fastjson.JSON;
import com.zhanlu.framework.config.service.DataDictService;
import com.zhanlu.framework.nosql.service.MongoService;
import com.zhanlu.framework.nosql.util.BasicUtils;
import com.zhanlu.framework.nosql.util.QueryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
@RequestMapping(value = "/chart")
public class ChartController {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataDictService dataDictService;

    @Autowired
    private MongoService mongoService;
    private String metaAppTable = "config_meta_chart";

    /**
     * 分页列表
     */
    @RequestMapping(value = "{type}/{field}/list", method = RequestMethod.GET)
    public ModelAndView list(@PathVariable("type") String type, @PathVariable("field") String field, HttpServletRequest req) throws Exception {
        Map<String, String[]> paramMap = req.getParameterMap();
        Map<String, Object> metaApp = this.getMetaTag(type, field);

        List<QueryItem> queryItems = QueryItem.buildSearchItems(paramMap);
        queryItems.add(new QueryItem("Eq_String_type", type));
        String selectSql = metaApp.get("selectSql").toString();
        List<Map<String, Object>> selectItems = null;
        if (selectSql.startsWith("SELECT ")) {
            selectItems = jdbcTemplate.queryForList(selectSql);
        }
        List<Object> dataResult = new ArrayList<>(selectItems.size());
        if (selectItems != null && selectItems.size() > 0) {
            String countSql = metaApp.get("countSql").toString();
            String[] fromArr = countSql.split(" FROM ");
            String[] fieldArr = fromArr[0].split(",");
            String[] whereArr = fromArr[1].split(" WHERE ");
            for (Map<String, Object> items : selectItems) {
                List<QueryItem> tmpItems = queryItems;
                tmpItems.add(new QueryItem(whereArr[1].split("=")[0], items.get("code").toString()));
                List<Object> dateItem = new ArrayList<>(2);
                dateItem.add(items.get("name"));
                long count = mongoService.countByProp(whereArr[0], tmpItems);
                dateItem.add(count);
                dataResult.add(dateItem);
            }
        }
        ModelAndView view = new ModelAndView("meta/chartList");
        view.addObject("jsonSearch", BasicUtils.jsonSearch(dataDictService, jdbcTemplate, metaApp, paramMap));
        view.addObject("metaApp", metaApp);
        view.addObject("data", JSON.toJSONString(dataResult));
        return view;
    }

    public Map<String, Object> getMetaTag(String type, String field) {
        List<QueryItem> queryItems = new ArrayList<>(1);
        queryItems.add(new QueryItem("Eq_String_code", "chart" + "_" + type + "_" + field));
        return mongoService.findOne(metaAppTable, queryItems);
    }

}
