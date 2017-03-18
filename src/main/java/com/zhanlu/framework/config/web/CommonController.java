package com.zhanlu.framework.config.web;

import org.apache.commons.collections.map.HashedMap;
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
@RequestMapping(value = "/common")
public class CommonController {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    /**
     * 分页查询配置，返回配置字典列表视图
     */
    @ResponseBody
    @RequestMapping(value = "check")
    public Object check(HttpServletRequest req) {
        Map<String, Object> dataMap = new HashedMap();
        Map<String, Object> paramMap = req.getParameterMap();

        List<Object> paramList = new ArrayList(paramMap.size());
        StringBuilder sql = new StringBuilder("SELECT * FROM " + paramMap.get("tb") + " WHERE 1=1");
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if (entry.getKey().equals("tb")) {
                continue;
            }
            sql.append(" " + entry.getKey() + "=?");
            paramList.add(entry.getValue());
        }
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
        dataMap.put("length", resultList == null ? 0 : resultList.size());
        dataMap.put("data", resultList);
        return dataMap;
    }

}
