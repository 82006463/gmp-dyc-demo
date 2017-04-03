package com.zhanlu.framework.common.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/3.
 */
public class HTMLUtils {

    public static String json2HTML(String jsonStruct, String jsonData) {
        if (jsonStruct == null || jsonStruct.trim().isEmpty()) {
            jsonStruct = "[]";
        }
        if (jsonData == null || jsonData.trim().isEmpty()) {
            jsonData = "{}";
        }
        List<Map<String, Object>> structList = JSON.parseObject(jsonStruct, List.class);
        Map<String, Object> dataMap = JSON.parseObject(jsonData, Map.class);
        int tmpIndex = 0;
        int itemIndex = 0;
        String html = "<tr>";
        for (Map<String, Object> entry : structList) {
            String tagType = entry.get("tagType").toString();
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
                html += "<textarea name='" + code + "' class='input_textarea_600" + (entry.get("required").equals("yes") ? " validate[required]" : "") + "'>" + val + "</textarea>";
            } else if (tagType.equals("select")) {

            } else {
                html += "<input type='text' name='" + code + "' value='" + val + "' class='input_240" + (entry.get("required").equals("yes") ? " validate[required]" : "") + "'/>";
            }
            html += "</td>";

            if (tagType.equals("textarea")) {
                html = html.replace("${" + (itemIndex - 1) + "}", " colspan='3'");
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
