package com.zhanlu.framework.config.web;

import com.zhanlu.framework.config.entity.DataDict;
import com.zhanlu.framework.config.service.DataDictService;
import com.zhanlu.framework.nosql.service.MongoService;
import com.zhanlu.framework.nosql.item.QueryItem;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 配置字典管理Controller
 *
 * @author yuqs
 * @since 0.1
 */
@Controller
@RequestMapping(value = "/wfc/common")
public class CommonController {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MongoService mongoService;
    @Autowired
    private DataDictService dataDictService;

    /**
     * 分页查询配置，返回配置字典列表视图
     */
    @ResponseBody
    @RequestMapping(value = "check")
    public Object check(HttpServletRequest req) {
        Map<String, String[]> paramMap = req.getParameterMap();
        String fieldId = paramMap.get("fieldId")[0];
        String fieldValue = paramMap.get("fieldValue")[0];
        String[] dtArr = paramMap.get("dt")[0].split("/");
        String tabName = dtArr[0] + "_" + dtArr[1];
        boolean exist = false;
        List<?> list = dataDictService.findBySQL("SELECT id FROM " + tabName + " WHERE code=?", fieldValue);
        if (list != null && list.size() > 0) {
            exist = list.size() != 1 || !list.get(0).toString().equals(dtArr[2]);
        }
        List resultList = new ArrayList<>(3);
        if (!exist) {
            resultList.add(fieldId);
            resultList.add(!exist);
        }
        return resultList;
    }

    /**
     * 分页查询配置，返回配置字典列表视图
     */
    @ResponseBody
    @RequestMapping(value = "select")
    public Object data(String code, String keyword) {
        List<String> dataList = new ArrayList<>();
        if (StringUtils.isBlank(code) || StringUtils.isBlank(keyword)) {
            return dataList;
        }
        DataDict dataDict = dataDictService.findByCode(code);
        if (dataDict == null || StringUtils.isBlank(dataDict.getDataSource())) {
            return dataList;
        }
        String sql = dataDict.getDataSource();
        if (sql.trim().toUpperCase().startsWith("SELECT ")) {
            sql = sql.replace("LIKE ?", "LIKE '%" + keyword + "%'").replace("like ?", "like '%" + keyword + "%'");
            String[] orArr = sql.toUpperCase().split("WHERE")[1].trim().toUpperCase().split("OR");
            List<String> params = new ArrayList<>();
            for (String or : orArr) {
                if (or.contains("=?") || or.contains("= ?"))
                    params.add(keyword);
            }
            List<Map<String, Object>> tmpMaps = jdbcTemplate.queryForList(sql, params.toArray());
            for (Map<String, Object> map : tmpMaps) {
                String tmpVal = "";
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    tmpVal += tmpVal.length() == 0 ? entry.getValue() : ":" + entry.getValue();
                }
                dataList.add(tmpVal);
            }
        } else {
            String[] fromArr = sql.split(" FROM ");
            String[] fieldArr = fromArr[0].split(",");
            String[] whereArr = fromArr[1].split(" WHERE ");
            List<QueryItem> queryItems = new ArrayList<>(2);
            queryItems.add(new QueryItem(whereArr[1], keyword));
            List<Map<String, Object>> items = mongoService.findByProp(whereArr[0], queryItems);
            for (Map<String, Object> item : items) {
                String tmpVal = item.get(fieldArr[0]).toString();
                for (int i = 1; i < fieldArr.length; i++) {
                    tmpVal += ":" + item.get(fieldArr[i]).toString();
                }
                dataList.add(tmpVal);
            }
        }
        return dataList;
    }

}
