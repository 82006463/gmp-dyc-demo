package com.zhanlu.app.web;

import com.alibaba.fastjson.JSON;
import com.zhanlu.framework.config.service.DataDictService;
import com.zhanlu.framework.nosql.service.MongoService;
import com.zhanlu.framework.nosql.util.BasicUtils;
import com.zhanlu.framework.nosql.util.QueryItem;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
        ModelAndView view = new ModelAndView("meta/chartList");

        List<QueryItem> queryItems = QueryItem.buildSearchItems(paramMap);
        queryItems.add(new QueryItem("Eq_String_type", type));
        for (QueryItem item : queryItems) {
            if (item.getCompareType().equalsIgnoreCase("In") && item.getFieldName().toLowerCase().contains("dep")) {
                List<Object> tmpList = (List) item.getFieldVal();
                String levelNo = jdbcTemplate.queryForObject("SELECT level_no FROM sec_org WHERE code=?", String.class, tmpList.get(0));
                List<?> codeList = jdbcTemplate.queryForList("SELECT code FROM sec_org WHERE level_no LIKE '" + levelNo + "%'", String.class);
                if (codeList != null && codeList.size() > 0) {
                    tmpList.addAll(codeList);
                }
            }
        }

        List<Object> dataResult = new ArrayList<>(1);
        List<String> categories = new ArrayList<>(10);

        String field1Name = null;
        String field1Val = null;
        String field2Name = null;
        String field2Val = null;
        String selectSql = metaApp.get("selectSql").toString();
        List<Map<String, Object>> selectItems = null;
        if (selectSql.startsWith("SELECT ")) {
            selectItems = jdbcTemplate.queryForList(selectSql);
        } else if (!selectSql.contains(" FROM ")) {
            selectItems = new ArrayList<>(12);
            String[] fieldArr = selectSql.split(",");
            field1Name = fieldArr[0];
            field2Name = fieldArr[1];
            Date curDate = new Date();
            field1Val = paramMap.get(field1Name) == null || paramMap.get(field1Name)[0].length() == 0 ? DateFormatUtils.format(curDate, "yyyy") + "-01-01" : paramMap.get(field1Name)[0];
            field2Val = paramMap.get(field2Name) == null || paramMap.get(field2Name)[0].length() == 0 ? DateFormatUtils.format(curDate, "yyyy-MM-dd") : paramMap.get(field2Name)[0];
            String[] field1Arr = field1Val.split("-");
            int year = Integer.parseInt(field1Arr[0]);
            int start = Integer.parseInt(field1Arr[1]);
            int end = Integer.parseInt(field2Val.split("-")[1]);
            for (int i = start; i <= end; i++) {
                Map<String, Object> itemMap = new LinkedHashMap<>(4);
                String month = i < 10 ? "0" + i : "" + 10;
                int day = i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12 ? 31 : i == 4 || i == 6 || i == 9 || i == 11 ? 30 : year % 4 == 0 && year % 100 != 0 ? 29 : 28;
                itemMap.put("start", field1Arr[0] + "-" + month + "-01");
                itemMap.put("end", field1Arr[0] + "-" + month + "-" + day);
                itemMap.put("name", month + "月");
                selectItems.add(itemMap);
            }
        }
        if (selectItems != null && selectItems.size() > 0) {
            String countSql = metaApp.get("countSql").toString();
            String[] fromArr = countSql.split(" FROM ");
            String[] fieldArr = fromArr[0].split(",");
            String[] whereArr = fromArr[1].split(" WHERE ");

            String inSql = (String) metaApp.get("inSql");
            if (StringUtils.isNotBlank(inSql)) {
                Map<String, Object> dataMap = new LinkedHashMap<>(4);
                List<Object> dataList = new ArrayList<>(selectItems.size());
                for (Map<String, Object> item : selectItems) {
                    categories.add((String) item.get("name"));
                    List<QueryItem> tmpItems = queryItems;
                    tmpItems.add(new QueryItem(whereArr[1].split("=")[0], item.get("code")));
                    dataList.add(mongoService.countByProp(whereArr[0], tmpItems));
                }
                dataMap.put("name", " ");
                dataMap.put("data", dataList);
                dataResult.add(dataMap);
            } else if (!selectSql.contains(" FROM ")) {
                Map<String, Object> dataMap = new LinkedHashMap<>(4);
                List<Object> dataList = new ArrayList<>(selectItems.size());
                for (Map<String, Object> item : selectItems) {
                    categories.add((String) item.get("name"));
                    List<QueryItem> tmpItems = queryItems;
                    tmpItems.add(new QueryItem(field1Name, item.get("start")));
                    tmpItems.add(new QueryItem(field2Name, item.get("end")));
                    dataList.add(mongoService.countByProp(whereArr[0], tmpItems));
                }
                dataMap.put("name", field1Val.split("-")[0] + "年");
                dataMap.put("data", dataList);
                dataResult.add(dataMap);
            } else {
                for (Map<String, Object> item : selectItems) {
                    categories.add((String) item.get("name"));
                    List<QueryItem> tmpItems = queryItems;
                    String[] tmpArr = whereArr[1].split("=");
                    tmpItems.add(new QueryItem(tmpArr[0], item.get(StringUtils.isBlank(tmpArr[1]) ? "code" : tmpArr[1])));

                    List<Object> dataItem = new ArrayList<>(2);
                    dataItem.add(item.get("name"));
                    dataItem.add(mongoService.countByProp(whereArr[0], tmpItems));
                    dataResult.add(dataItem);
                }
            }
        }
        view.addObject("jsonSearch", BasicUtils.jsonSearch(dataDictService, jdbcTemplate, metaApp, paramMap));
        view.addObject("metaApp", metaApp);
        view.addObject("categories", JSON.toJSONString(categories));
        view.addObject("data", JSON.toJSONString(dataResult));
        return view;
    }

    public Map<String, Object> getMetaTag(String type, String field) {
        List<QueryItem> queryItems = new ArrayList<>(1);
        queryItems.add(new QueryItem("Eq_String_code", "chart" + "_" + type + "_" + field));
        return mongoService.findOne(metaAppTable, queryItems);
    }

}
