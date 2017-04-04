package com.zhanlu.framework.common.utils;

import com.alibaba.fastjson.JSON;
import com.zhanlu.framework.config.entity.DataDict;
import com.zhanlu.framework.config.service.DataDictService;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/3.
 */
public class HTMLUtils {

    public static String json2HTML(ApplicationContext applicationContext, String jsonStruct, String jsonData) {
        if (jsonStruct == null || jsonStruct.trim().isEmpty()) {
            jsonStruct = "[]";
        }
        if (jsonData == null || jsonData.trim().isEmpty()) {
            jsonData = "{}";
        }
        JdbcTemplate jdbcTemplate = null;
        DataDictService dataDictService = null;
        if (applicationContext.containsBean("jdbcTemplate")) {
            jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
            dataDictService = applicationContext.getBean(DataDictService.class);
        }
        List<Map<String, Object>> structList = JSON.parseObject(jsonStruct, List.class);
        Map<String, Object> dataMap = JSON.parseObject(jsonData, Map.class);
        int tmpIndex = 0;
        int itemIndex = 0;
        String html = "<tr>";
        for (Map<String, Object> entry : structList) {
            String tagType = entry.get("tagType").toString();
            String required = entry.get("required").toString();
            String code = entry.get("code").toString();
            String name = entry.get("name").toString();
            String val = dataMap.get(code) == null ? "" : dataMap.get(code).toString();

            itemIndex++;
            tmpIndex++;
            if (tmpIndex == 3 || (tmpIndex == 1 && tagType.equals("textarea"))) {
                html += "<tr>";
            } else if (tmpIndex == 2 && tagType.equals("textarea")) {
                html += "</tr><tr>";
            }
            html += "<td class='td_table_1'>" + name + "</td><td class='td_table_2' ${" + itemIndex + "}>";
            if (tagType.equals("textarea")) {
                html += "<textarea name='" + code + "' class='input_textarea_600" + (required.equals("yes") ? " validate[required]" : "") + "'>" + val + "</textarea>";
            } else if (tagType.startsWith("select_") || tagType.startsWith("radio_") || tagType.startsWith("checkbox_")) {
                DataDict dataDict = dataDictService.findByCode(tagType);
                if (dataDict != null) {
                    List<Map<String, Object>> itemList = jdbcTemplate.queryForList(dataDict.getDataSource());
                    if (tagType.startsWith("select_")) {
                        html += "<select name='" + code + "' class='input_select" + (required.equals("yes") ? " validate[required]" : "") + "'>";
                        html += "<option value='' selected>--请选择--</option>";
                        for (Map<String, Object> item : itemList) {
                            html += "<option value='" + item.get("code") + "'" + (item.get("code").toString().equals(val) ? " selected='selected'" : "") + ">" + item.get("name") + "</option>";
                        }
                        html += "</select>";
                    } else if (tagType.startsWith("radio_")) {
                        for (Map<String, Object> item : itemList) {
                            html += "<input type='radio' name='" + code + "' value='" + item.get("code") + "' class='input_radio" + (required.equals("yes") ? " validate[required]" : "") + "' " + (item.get("code").toString().equals(val) ? "checked='checked'" : "") + "/>" + item.get("name");
                        }
                    } else if (tagType.startsWith("checkbox_")) {
                        for (Map<String, Object> item : itemList) {
                            html += "<input type='text' name='" + item.get("code") + "' value='" + val + "' class='input_checkbox" + (required.equals("yes") ? " validate[required]" : "") + "' " + (item.get("code").toString().equals(val) ? "checked='checked'" : "") + "/>" + item.get("name");
                        }
                    }
                } else {
                    html += "<input type='text' name='" + code + "' value='" + val + "' class='input_240" + (required.equals("yes") ? " validate[required]" : "") + "'/>";
                }
            } else if (tagType.equals("date")) {
                html += "<input type='text' name='" + code + "' value='" + val + "' class='input_240" + (required.equals("yes") ? " validate[required]" : "") + "' onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'});\" readonly='readonly'/>";
            } else if (tagType.equals("timestamp")) {
                html += "<input type='text' name='" + code + "' value='" + val + "' class='input_240" + (required.equals("yes") ? " validate[required]" : "") + "' onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});\" readonly='readonly'/>";
            } else {
                html += "<input type='text' name='" + code + "' value='" + val + "' class='input_240" + (required.equals("yes") ? " validate[required]" : "") + "'/>";
            }
            html += "</td>";

            if (tagType.equals("textarea")) {
                if (tmpIndex == 1) {
                    html = html.replace("${" + itemIndex + "}", " colspan='3'");
                } else if (tmpIndex == 2) {
                    html = html.replace("${" + (itemIndex - 1) + "}", " colspan='3'");
                    html = html.replace("${" + itemIndex + "}", " colspan='3'");
                }
            } else if (tmpIndex == 1 && itemIndex == structList.size()) {
                html = html.replace("${" + itemIndex + "}", " colspan='3'");
            }
            if (tmpIndex == 2 || (tmpIndex == 1 && tagType.equals("textarea")) || itemIndex == structList.size()) {
                html += "</tr>";
                tmpIndex = 0;
            }
        }
        return html.toString();
    }

}
