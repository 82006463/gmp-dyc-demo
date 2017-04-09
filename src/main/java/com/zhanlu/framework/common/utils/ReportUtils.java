package com.zhanlu.framework.common.utils;

import com.alibaba.fastjson.JSON;
import com.zhanlu.framework.config.entity.DataDict;
import com.zhanlu.framework.config.entity.ElasticTable;
import com.zhanlu.framework.config.service.DataDictService;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/3.
 */
public class ReportUtils {

    /**
     * 将JSON转成HTML串
     */
    public static String jsonSearch(JdbcTemplate jdbcTemplate, DataDictService dataDictService, ElasticTable etab, Map<String, String[]> paramMap) {
        String jsonStruct = etab.getJsonSearch();
        if (jsonStruct == null || jsonStruct.trim().isEmpty()) {
            return "";
        }
        List<Map<String, Object>> structList = JSON.parseObject(jsonStruct, List.class);
        int tmpIndex = 0;
        int itemIndex = 0;
        String html = "<table width='100%' border='0' align='center' cellpadding='0' class='table_all_border' cellspacing='0' style='margin-bottom: 0px;border-bottom: 0px'>";
        html += "<tr><td class='td_table_top' align='center'>图表管理-" + etab.getName() + "</td></tr></table>";
        html += "<table class='table_all' align='center' border='0' cellpadding='0' cellspacing='0' style='margin-top: 0px'><tr>";
        for (Map<String, Object> entry : structList) {
            String tagType = entry.get("tagType").toString().replace("tagType_", "");
            String dataType = entry.get("dataType").toString().replace("dataType_", "");
            String compare = entry.get("compare").toString().replace("compare_", "filter_");
            String code = entry.get("code").toString();
            String name = entry.get("name").toString();
            String desc = entry.get("desc").toString();
            code = compare + (dataType.equals("int") ? "I" : dataType.equals("long") ? "L" : dataType.equals("date") ? "D" : dataType.equals("double") ? "N" : "S") + "_" + code;
            String val = paramMap.get(code) == null ? "" : paramMap.get(code)[0];

            itemIndex++;
            tmpIndex++;
            if (tmpIndex == 3) {
                html += "<tr>";
            }
            html += "<td class='td_table_1'>" + desc + "：</td><td class='td_table_2'>";
            if (tagType.equals("date")) {
                html += "<input type='text' name='" + code + "' value='" + val + "' class='input_240' onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'});\" readonly='readonly'/>";
            } else if (tagType.equals("timestamp")) {
                html += "<input type='text' name='" + code + "' value='" + val + "' class='input_240' onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});\" readonly='readonly'/>";
            } else if (tagType.startsWith("select_")) {
                DataDict dataDict = dataDictService.findByCode("tagType_" + tagType);
                if (dataDict != null && StringUtils.isNotBlank(dataDict.getDataSource()) && dataDict.getDataSource().trim().toUpperCase().startsWith("SELECT ")) {
                    List<Map<String, Object>> itemList = jdbcTemplate.queryForList(dataDict.getDataSource());
                    html += "<select name='" + code + "' class='input_select'>";
                    html += "<option value='' selected>--请选择--</option>";
                    for (Map<String, Object> item : itemList) {
                        html += "<option value='" + item.get("code") + "'" + (item.get("code").toString().equals(val) ? " selected='selected'" : "") + ">" + item.get("name") + "</option>";
                    }
                    html += "</select>";
                }
            } else {
                html += "<input type='text' name='" + code + "' value='" + val + "' class='input_240'/>";
            }
            html += "</td>";
            if (tmpIndex == 2 || itemIndex == structList.size()) {
                html += "</tr>";
                tmpIndex = 0;
            }
        }
        html += "</table>";
        return html;
    }

    /**
     * 将JSON转成HTML串
     */
    public static String jsonList(JdbcTemplate jdbcTemplate, DataDictService dataDictService, ElasticTable etab) {
        String jsonStruct = etab.getJsonList();
        if (jsonStruct == null || jsonStruct.trim().isEmpty()) {
            return "";
        }

        String html = "<table class='table_all' align='center' border='0' cellpadding='0' cellspacing='0' style='margin-top: 0px'><tr>";
        html += "</table>";
        return html;
    }

    /**
     * 将JSON转成HTML串
     */
    public static String jsonEdit(JdbcTemplate jdbcTemplate, DataDictService dataDictService, ElasticTable etab, String jsonData) {
        String jsonStruct = etab.getJsonEdit();
        if (jsonStruct == null || jsonStruct.trim().isEmpty()) {
            return "";
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
            String tagType = entry.get("tagType").toString().replace("tagType_", "");
            String required = entry.get("required").toString();
            String fuzzy = entry.get("fuzzy").toString();
            String code = entry.get("code").toString();
            String name = entry.get("name").toString();
            String desc = entry.get("desc").toString();
            String val = dataMap.get(code) == null ? "" : dataMap.get(code).toString();

            itemIndex++;
            tmpIndex++;
            if (tmpIndex == 3 || (tmpIndex == 1 && (tagType.equals("textarea") || tagType.equals("subForm")))) {
                html += "<tr>";
            } else if (tmpIndex == 2 && (tagType.equals("textarea") || tagType.equals("subForm"))) {
                html += "</tr><tr>";
            }
            html += "<td class='td_table_1'>" + desc + "</td><td class='td_table_2' ${" + itemIndex + "}>";
            if (tagType.equals("textarea")) {
                html += "<textarea name='" + code + "' class='input_textarea_600" + (required.equals("yes") ? " validate[required]" : "") + "'>" + val + "</textarea>";
            } else if (tagType.equals("date")) {
                html += "<input type='text' name='" + code + "' value='" + val + "' class='input_240" + (required.equals("yes") ? " validate[required]" : "") + "' onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'});\" readonly='readonly'/>";
            } else if (tagType.equals("timestamp")) {
                html += "<input type='text' name='" + code + "' value='" + val + "' class='input_240" + (required.equals("yes") ? " validate[required]" : "") + "' onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});\" readonly='readonly'/>";
            } else if (tagType.equals("subForm")) {
                DataDict dataDict = dataDictService.findByCode(entry.get("subForm").toString());
                if (dataDict != null && StringUtils.isNotBlank(dataDict.getDataSource())) {
                    String[] fieldArr = dataDict.getDataSource().split(",");
                    List<String> titles = new ArrayList<>(fieldArr.length);
                    List<String> tagTypes = new ArrayList<>(fieldArr.length);
                    List<String> tagNames = new ArrayList<>(fieldArr.length);
                    for (String field : fieldArr) {
                        String[] tmpField = field.split(":");
                        tagNames.add(tmpField[0]);
                        tagTypes.add(tmpField[1]);
                        titles.add(tmpField[2]);
                    }
                    Map<String, List<String>> valMap = new HashMap<>();
                    int rowCount = 0;
                    for (String tagName : tagNames) {
                        List<String> tmpVals = null;
                        if (dataMap.get(tagName) == null) {
                            tmpVals = new ArrayList<>(1);
                            tmpVals.add("");
                        } else {
                            tmpVals = (List) dataMap.get(tagName);
                        }
                        rowCount = rowCount == 0 ? tmpVals.size() : rowCount;
                        valMap.put(tagName, tmpVals);
                    }
                    html += "<table class='table_all' align='center' border='0' cellpadding='0' cellspacing='0' id='" + code + "' style='margin: 0'>";
                    html += "<tr>";
                    for (String title : titles) {
                        html += "<td align='center' class='td_list_1'>" + title + "</td>";
                    }
                    html += "<td align='center' width='6%' class='td_list_1'><a class='btnAdd' onclick='return Ops.addTr(this,1);'></a></td>";
                    html += "</tr>";

                    for (int row = 0; row < rowCount; row++) {
                        html += "<tr>";
                        for (int i = 0; i < tagNames.size(); i++) {
                            String tmpName = tagNames.get(i);
                            String tmpType = tagTypes.get(i);
                            if (tmpType.equals("date")) {
                                html += "<td class='td_list_2'><input type='text' name='" + tmpName + "' value='" + valMap.get(tmpName).get(row) + "' class='input_240 validate[required]' onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'});\" readonly='readonly'/></td>";
                            } else if (tmpType.equals("timestamp")) {
                                html += "<td class='td_list_2'><input type='text' name='" + tmpName + "' value='" + valMap.get(tmpName).get(row) + "' class='input_240 validate[required]' onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});\" readonly='readonly'/></td>";
                            } else if (tmpType.equals("textarea")) {
                                html += "<td class='td_list_2'><textarea name='" + tmpName + "' class='input_textarea_600 validate[required]'>" + valMap.get(tmpName).get(row) + "</textarea></td>";
                            } else {
                                html += "<td class='td_list_2'><input type='text' name='" + tmpName + "' value='" + valMap.get(tmpName).get(row) + "' class='input_240 validate[required]'/></td>";
                            }
                        }
                        html += "<td class='td_list_2'>" + (row > 0 ? "<a class='btnDel' onclick='return Ops.removeTr(this,1);'></a><a onclick='return Ops.up(this);' title='上移'>上</a><a onclick='return Ops.down(this);' title='下移'>下</a>" : "") + "</td>";
                        html += "</tr>";
                    }
                    html += "</table>";
                } else {
                    html += "<input type='text' name='" + code + "' value='" + val + "' class='input_240" + (required.equals("yes") ? " validate[required]" : "") + "' fuzzy='" + fuzzy + "'/>";
                }
            } else if (tagType.startsWith("select_") || tagType.startsWith("radio_") || tagType.startsWith("checkbox_")) {
                DataDict dataDict = dataDictService.findByCode("tagType_" + tagType);
                if (dataDict != null && StringUtils.isNotBlank(dataDict.getDataSource()) && dataDict.getDataSource().trim().toUpperCase().startsWith("SELECT ")) {
                    List<Map<String, Object>> itemList = ResultSetUtils.convertList(jdbcTemplate.queryForList(dataDict.getDataSource()));
                    if (tagType.startsWith("select_")) {
                        html += "<select name='" + code + "' class='input_select" + (required.equals("yes") ? " validate[required]" : "") + "' fuzzy='" + fuzzy + "'>";
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
                    html += "<input type='text' name='" + code + "' value='" + val + "' class='input_240" + (required.equals("yes") ? " validate[required]" : "") + "' fuzzy='" + fuzzy + "'/>";
                }
            } else {
                html += "<input type='text' name='" + code + "' value='" + val + "' class='input_240" + (required.equals("yes") ? " validate[required]" : "") + "' fuzzy='" + fuzzy + "'/>";
            }
            html += "</td>";

            if (tagType.equals("textarea") || tagType.equals("subForm")) {
                if (tmpIndex == 1) {
                    html = html.replace("${" + itemIndex + "}", " colspan='3'");
                } else if (tmpIndex == 2) {
                    html = html.replace("${" + (itemIndex - 1) + "}", " colspan='3'");
                    html = html.replace("${" + itemIndex + "}", " colspan='3'");
                }
            } else if (tmpIndex == 1 && itemIndex == structList.size()) {
                html = html.replace("${" + itemIndex + "}", " colspan='3'");
            }
            if (tmpIndex == 2 || itemIndex == structList.size() ||
                    (tmpIndex == 1 && (tagType.equals("textarea") || tagType.equals("subForm")))) {
                html += "</tr>";
                tmpIndex = 0;
            }
        }
        return html;
    }

    /**
     * 将JSON转成HTML串
     */
    public static String jsonView(DataDictService dataDictService, ElasticTable etab, String jsonData) {
        String jsonStruct = etab.getJsonEdit();
        if (jsonStruct == null || jsonStruct.trim().isEmpty()) {
            return "";
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
            String tagType = entry.get("tagType").toString().replace("tagType_", "");
            String code = entry.get("code").toString();
            String name = entry.get("name").toString();
            String desc = entry.get("desc").toString();
            String val = dataMap.get(code) == null ? "" : dataMap.get(code).toString();

            itemIndex++;
            tmpIndex++;
            if (tmpIndex == 3 || (tmpIndex == 1 && (tagType.equals("textarea") || tagType.equals("subForm")))) {
                html += "<tr>";
            } else if (tmpIndex == 2 && (tagType.equals("textarea") || tagType.equals("subForm"))) {
                html += "</tr><tr>";
            }
            html += "<td class='td_table_1'>" + desc + "</td><td class='td_table_2' ${" + itemIndex + "}>";
            if (tagType.equals("subForm")) {
                html += "<table class='table_all' align='center' border='0' cellpadding='0' cellspacing='0' style='margin: 0'>";
                html += "<tr>";
                DataDict dataDict = dataDictService.findByCode(entry.get("subForm").toString());
                if (dataDict != null && StringUtils.isNotBlank(dataDict.getDataSource())) {

                }
                html += "</tr>";
                html += "</table>";
            } else {
                html += val;
            }
            html += "</td>";

            if (tagType.equals("textarea") || tagType.equals("subForm")) {
                if (tmpIndex == 1) {
                    html = html.replace("${" + itemIndex + "}", " colspan='3'");
                } else if (tmpIndex == 2) {
                    html = html.replace("${" + (itemIndex - 1) + "}", " colspan='3'");
                    html = html.replace("${" + itemIndex + "}", " colspan='3'");
                }
            } else if (tmpIndex == 1 && itemIndex == structList.size()) {
                html = html.replace("${" + itemIndex + "}", " colspan='3'");
            }
            if (tmpIndex == 2 || itemIndex == structList.size() ||
                    (tmpIndex == 1 && (tagType.equals("textarea") || tagType.equals("subForm")))) {
                html += "</tr>";
                tmpIndex = 0;
            }
        }
        return html;
    }

}
