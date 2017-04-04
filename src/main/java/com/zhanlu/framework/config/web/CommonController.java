package com.zhanlu.framework.config.web;

import com.zhanlu.framework.config.entity.DataDict;
import com.zhanlu.framework.config.service.DataDictService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
    @RequestMapping(value = "select/{code}")
    public Object data(@PathVariable("code") String code, String paramVal) {
        DataDict dataDict = dataDictService.findByCode(paramVal);
        if (dataDict == null || StringUtils.isBlank(dataDict.getDataSource()) || StringUtils.isBlank(paramVal)) {
            return null;
        }
        List<Map<String, Object>> tmpMaps = jdbcTemplate.queryForList(dataDict.getDataSource(), paramVal);
        List<Map<String, Object>> resultList = new ArrayList<>(tmpMaps.size());
        resultList.addAll(tmpMaps);
        Map<String, Object> resultMap = new HashedMap();
        resultMap.put("length", resultList == null ? -1 : resultList.size());
        resultMap.put("data", resultList);
        return resultMap;
    }

}
