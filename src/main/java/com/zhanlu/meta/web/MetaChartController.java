package com.zhanlu.meta.web;

import com.alibaba.fastjson.JSON;
import com.zhanlu.framework.config.service.DataDictService;
import com.zhanlu.framework.logic.MongoLogic;
import com.zhanlu.framework.nosql.item.QueryItem;
import com.zhanlu.framework.util.MetaTagUtils;
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
@RequestMapping(value = "/meta/chart")
public class MetaChartController {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataDictService dataDictService;

    @Autowired
    private MongoLogic mongoLogic;

    /**
     * 分页列表
     */
    @RequestMapping(value = "{type}/{cmcode}/list", method = RequestMethod.GET)
    public ModelAndView list(@PathVariable("type") String type, @PathVariable("cmcode") String cmcode, HttpServletRequest req) throws Exception {
        Map<String, String[]> paramMap = req.getParameterMap();
        Map<String, Object> metaApp = mongoLogic.findMetaByCode(cmcode.startsWith("chart_") ? cmcode : "chart_" + type + "_" + cmcode);
        ModelAndView view = new ModelAndView("meta/metaChartList");
        view.addObject("type", type);
        view.addObject("cmcode", cmcode);

        List<QueryItem> queryItems = QueryItem.buildSearchItems(paramMap);
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
        String field2Name = null;
        String selectSql = metaApp.get("selectSql").toString();
        List<Map<String, Object>> selectItems = null;
        if (selectSql.startsWith("SELECT ")) { //分类统计
            selectItems = jdbcTemplate.queryForList(selectSql);
        } else if (!selectSql.contains(" FROM ")) {
            selectItems = new ArrayList<>(12);
            String[] fieldArr = selectSql.split(",");
            field1Name = fieldArr[0];
            field2Name = fieldArr[1];
            Date curDate = new Date();
            String field1Val = paramMap.get(field1Name) == null || paramMap.get(field1Name)[0].length() == 0 ? DateFormatUtils.format(curDate, "yyyy") + "-01-01" : paramMap.get(field1Name)[0];
            String field2Val = paramMap.get(field2Name) == null || paramMap.get(field2Name)[0].length() == 0 ? DateFormatUtils.format(curDate, "yyyy-MM-dd") : paramMap.get(field2Name)[0];
            String[] field1Arr = field1Val.split("-");
            String[] field2Arr = field2Val.split("-");
            int start = Integer.parseInt(field1Arr[0] + field1Arr[1]);
            int end = Integer.parseInt(field2Arr[0] + field2Arr[1]);
            for (int i = start; i <= end; i++) {
                Map<String, Object> itemMap = new LinkedHashMap<>(4);
                int year = i / 100;
                int month = i % 100;
                if (month == 13) {
                    year++;
                    month = 1;
                    i = Integer.parseInt(year + "01");
                }
                int day = 0;
                if (month == 4 || month == 6 || month == 9 || month == 11) {
                    day = 30;
                } else if (month == 2) {
                    day = year % 4 == 0 && year % 100 != 0 ? 29 : 28;
                } else {
                    day = 31;
                }
                itemMap.put("start", year + "-" + (month < 10 ? "0" + month : month) + "-01");
                itemMap.put("end", year + "-" + (month < 10 ? "0" + month : month) + "-" + day);
                itemMap.put("name", i + "");
                selectItems.add(itemMap);
            }
        } else {

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
                    String[] andArr = whereArr[1].split(" AND ");
                    for (String and : andArr) {
                        String[] tmpArr = and.split("=");
                        if (tmpArr[1].startsWith("$$")) { //引用InSQL字段
                            List<Object> codeList = jdbcTemplate.queryForList(inSql.replace("$level_no", "'" + item.get("level_no") + "%'"), Object.class);
                            if (codeList == null)
                                codeList = new ArrayList<>(1);
                            codeList.add(item.get("level_no"));
                            tmpItems.add(new QueryItem(tmpArr[0], codeList));
                        } else if (tmpArr[1].startsWith("$")) { //引用SelectSQL字段
                            tmpItems.add(new QueryItem(tmpArr[0], item.get(tmpArr[1].replace("$", ""))));
                        } else {
                            tmpItems.add(new QueryItem(tmpArr[0], tmpArr[1]));
                        }
                    }
                    dataList.add(mongoLogic.countByProp(whereArr[0], tmpItems));
                }
                dataMap.put("name", metaApp.get("xtitle") == null ? " " : metaApp.get("xtitle"));
                dataMap.put("data", dataList);
                dataResult.add(dataMap);
            } else if (!selectSql.contains(" FROM ")) {
                Map<String, Object> dataMap = new LinkedHashMap<>(4);
                List<Object> dataList = new ArrayList<>(selectItems.size());
                for (Map<String, Object> item : selectItems) {
                    categories.add((String) item.get("name"));
                    List<QueryItem> tmpItems = queryItems;
                    String[] andArr = whereArr[1].split(" AND ");
                    for (String and : andArr) {
                        String[] tmpArr = and.split("=");
                        tmpItems.add(new QueryItem(tmpArr[0], tmpArr[1].startsWith("$") ? item.get(tmpArr[1].replace("$", "")) : tmpArr[1]));
                    }
                    dataList.add(mongoLogic.countByProp(whereArr[0], tmpItems));
                }
                dataMap.put("name", metaApp.get("xtitle") == null ? " " : metaApp.get("xtitle"));
                dataMap.put("data", dataList);
                dataResult.add(dataMap);
            } else {
                for (Map<String, Object> item : selectItems) {
                    categories.add((String) item.get("name"));
                    List<QueryItem> tmpItems = queryItems;
                    String[] andArr = whereArr[1].split(" AND ");
                    for (String and : andArr) {
                        String[] tmpArr = and.split("=");
                        tmpItems.add(new QueryItem(tmpArr[0], tmpArr[1].startsWith("$") ? item.get(tmpArr[1].replace("$", "")) : tmpArr[1]));
                    }
                    List<Object> dataItem = new ArrayList<>(2);
                    dataItem.add(item.get("name"));
                    dataItem.add(mongoLogic.countByProp(whereArr[0], tmpItems));
                    dataResult.add(dataItem);
                }
            }
        }
        view.addObject("jsonSearch", MetaTagUtils.search(dataDictService, jdbcTemplate, metaApp, paramMap));
        view.addObject("metaApp", metaApp);
        view.addObject("categories", JSON.toJSONString(categories));
        view.addObject("data", JSON.toJSONString(dataResult));
        return view;
    }

}
