package com.zhanlu.framework.util;

import com.alibaba.fastjson.JSON;
import com.zhanlu.framework.common.utils.ResultSetUtils;
import com.zhanlu.framework.config.entity.DataDict;
import com.zhanlu.framework.config.service.DataDictService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Administrator on 2017/4/3.
 */
public class MetaTagUtils {

    /**
     * 将JSON转成HTML串
     */
    public static String search(DataDictService dataDictService, JdbcTemplate jdbcTemplate, Map<String, Object> metaTag, Map<String, String[]> paramMap) {
        List<Map<String, String>> queryList = (List<Map<String, String>>) metaTag.get("queryItems");
        int itemIndex = 0;
        String html = "<table class='table_all' align='center' border='0' cellpadding='0' cellspacing='0' style='margin-top: 0px'><tr>";
        for (Map<String, String> entry : queryList) {
            String tagType = entry.get("tagType").replace("tagType_", "");
            String compareType = entry.get("compareType").replace("compareType_", "");
            String dataType = entry.get("dataType").replace("dataType_", "");
            String code = compareType + "_" + dataType + "_" + entry.get("code");
            String val = paramMap.get(code) == null ? "" : paramMap.get(code)[0];

            itemIndex++;
            if (itemIndex > 1 && itemIndex % 2 == 1) {
                html += "<tr>";
            }
            html += "<td class='td_table_1'>" + entry.get("name") + "：</td><td class='td_table_2' " + (itemIndex % 2 == 1 && itemIndex == queryList.size() ? "colspan='3'" : "") + ">";
            if (tagType.equalsIgnoreCase("date")) {
                html += "<input type='text' name='" + code + "' value='" + val + "' class='input_240' onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'});\" readonly='readonly'/>";
            } else if (tagType.equalsIgnoreCase("timestamp")) {
                html += "<input type='text' name='" + code + "' value='" + val + "' class='input_240' onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});\" readonly='readonly'/>";
            } else if (tagType.startsWith("Select_")) {
                DataDict dataDict = dataDictService.findByCode("tagType_" + tagType);
                if (dataDict != null && StringUtils.isNotBlank(dataDict.getDataSource()) && dataDict.getDataSource().trim().toUpperCase().startsWith("SELECT ")) {
                    List<Map<String, Object>> itemList = jdbcTemplate.queryForList(dataDict.getDataSource());
                    html += "<select name='" + code + "' class='input_select'><option value='' selected>--请选择--</option>";
                    for (Map<String, Object> item : itemList) {
                        html += "<option value='" + item.get("code") + "'" + (item.get("code").toString().equals(val) ? " selected='selected'" : "") + ">" + item.get("name") + "</option>";
                    }
                    html += "</select>";
                }
            } else {
                html += "<input type='text' name='" + code + "' value='" + val + "' class='input_240'/>";
            }
            html += "</td>";
            if ((itemIndex > 0 && itemIndex % 2 == 0) || itemIndex == queryList.size()) {
                html += "</tr>";
            }
        }
        html += "</table>";
        return html;
    }

    /**
     * 将JSON转成HTML串
     */
    public static String list(String contextPath, Map<String, Object> metaTag, List<Map<String, Object>> entityList) {
        List<Map<String, String>> listList = (List<Map<String, String>>) metaTag.get("listItems");
        List<Map<String, String>> editList = (List<Map<String, String>>) metaTag.get("editItems");
        Map<String, String> fieldMap = new LinkedHashMap<>(listList.size());
        Map<String, String> tagTypeMap = new LinkedHashMap<>(listList.size());
        String html = "<tr>";
        for (Map<String, String> entry : listList) {
            html += "<td align=center class='td_list_1'>" + entry.get("name") + "</td>";
            fieldMap.put(entry.get("code"), entry.get("name"));
        }
        for (Map<String, String> entry : editList) {
            tagTypeMap.put(entry.get("code"), entry.get("tagType"));
        }
        html += "<td align=center width=10% class='td_list_1'>操作</td></tr>";
        for (Map<String, Object> entry : entityList) {
            html += "<tr>";
            for (Map.Entry<String, String> field : fieldMap.entrySet()) {
                html += "<td class='td_list_2' align=left>";
                if (entry.get(field.getKey()) != null) {
                    if (entry.get(field.getKey()) instanceof Date) {
                        html += DateFormatUtils.format((Date) entry.get(field.getKey()), "yyyy-MM-dd");
                    } else if (entry.get(field.getKey()) instanceof Timestamp) {
                        html += DateFormatUtils.format((Timestamp) entry.get(field.getKey()), "yyyy-MM-dd HH:mm:ss");
                    } else {
                        if (tagTypeMap.get(field.getKey()).startsWith("tagType_Select") && entry.get(field.getKey() + "_val") != null) {
                            html += entry.get(field.getKey() + "_val");
                        } else {
                            html += entry.get(field.getKey());
                        }
                    }
                }
                html += "</td>";
            }
            html += "<td class='td_list_2' align=left>";
            if (metaTag.get("list_delete_url") != null) {
                html += "<a href='" + contextPath + "/meta/app/" + metaTag.get("type") + "/" + metaTag.get("code") + "/delete/" + entry.get("_id") + "' class='btnDel' title='删除' onclick='return confirmDel();'>删除</a>";
            }
            if (metaTag.get("list_update_url") != null) {
                html += "<a href='" + contextPath + "/meta/app/" + metaTag.get("type") + "/" + metaTag.get("code") + "/update/" + entry.get("_id") + "' class='btnEdit'' title='编辑'>编辑</a>";
            }
            if (metaTag.get("list_view_url") != null) {
                html += "<a href='" + contextPath + "/meta/app/" + metaTag.get("type") + "/" + metaTag.get("code") + "/view/" + entry.get("_id") + "' class='btnView'' title='查看'>查看</a>";
            }
            html += "</td></tr>";
        }
        return html;
    }

    /**
     * 将JSON转成HTML串
     */
    public static String edit(JdbcTemplate jdbcTemplate, DataDictService dataDictService, Map<String, Object> metaTag, Map<String, Object> dataMap) {
        List<Map<String, String>> editList = (List<Map<String, String>>) metaTag.get("editItems");
        int tmpIndex = 0;
        int itemIndex = 0;
        String html = "<tr>";
        for (Map<String, String> entry : editList) {
            String tagType = entry.get("tagType").replace("tagType_", "");
            String required = entry.get("required").replace("yesNo_", "");
            String displayMode = entry.get("displayMode").replace("displayMode_", "");
            String fuzzy = entry.get("fuzzy");
            String code = entry.get("code");
            String val = dataMap.get(code) == null ? "" : dataMap.get(code).toString();

            itemIndex++;
            tmpIndex++;
            if (tmpIndex == 3 || (tmpIndex == 1 && (tagType.equalsIgnoreCase("textarea") || tagType.equalsIgnoreCase("file") || tagType.equalsIgnoreCase("subForm")))) {
                html += "<tr>";
            } else if (tmpIndex == 2 && (tagType.equalsIgnoreCase("textarea") || tagType.equalsIgnoreCase("file") || tagType.equalsIgnoreCase("subForm"))) {
                html += "</tr><tr>";
            }
            String classAttr = required.equals("yes") ? " validate[required]" : "";
            String readOnly = displayMode.equalsIgnoreCase("readonly") ? "readonly='readonly'" : "";
            html += "<td class='td_table_1'>" + entry.get("name") + (required.equals("yes") ? "<b class='requiredWarn'>*</b>" : "") + "</td><td class='td_table_2' ${" + itemIndex + "}>";
            if (displayMode.equalsIgnoreCase("text")) {
                html += val;
            } else if (tagType.equalsIgnoreCase("textarea")) {
                html += "<textarea name='" + code + "' class='input_textarea_600" + classAttr + "' " + readOnly + ">" + val + "</textarea>";
            } else if (tagType.equalsIgnoreCase("date")) {
                if (dataMap.get(code) instanceof Long || dataMap.get(code) instanceof Date) {
                    Date date = dataMap.get(code) instanceof Long ? new Date((Long) dataMap.get(code)) : (Date) dataMap.get(code);
                    html += "<input type='text' name='" + code + "' value='" + DateFormatUtils.format(date, "yyyy-MM-dd");
                } else {
                    html += "<input type='text' name='" + code + "' value='" + val;
                }
                html += "' class='input_240" + classAttr + "' onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'});\" readonly='readonly'/>";
            } else if (tagType.equalsIgnoreCase("timestamp")) {
                if (dataMap.get(code) instanceof Long || dataMap.get(code) instanceof Timestamp) {
                    Date date = dataMap.get(code) instanceof Long ? new Date((Long) dataMap.get(code)) : (Timestamp) dataMap.get(code);
                    html += "<input type='text' name='" + code + "' value='" + DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
                } else {
                    html += "<input type='text' name='" + code + "' value='" + val;
                }
                html += "' class='input_240" + classAttr + "' onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});\" readonly='readonly'/>";
            } else if (tagType.equalsIgnoreCase("subForm")) {
                DataDict dataDict = dataDictService.findByCode(entry.get("subForm").toString());
                if (dataDict != null && StringUtils.isNotBlank(dataDict.getDataSource())) {
                    html += "<table class='table_all' align='center' border='0' cellpadding='0' cellspacing='0' id='" + code + "' style='margin: 0'><tr>";
                    List<Map<String, Object>> tagList = JSON.parseObject(dataDict.getDataSource(), List.class);
                    Map<String, List<Object>> tagValMap = new HashMap<>();
                    int rowCount = 0;
                    for (Map<String, Object> map : tagList) {
                        html += "<td align='center' class='td_list_1'>" + map.get("name") + (map.get("required").toString().equals("true") ? "<b class='requiredWarn'>*</b>" : "") + "</td>";
                        String tmpCode = map.get("code").toString();
                        List<Object> tmpVals = new ArrayList<>(1);
                        if (dataMap.get(tmpCode) == null || !(dataMap.get(tmpCode) instanceof List || dataMap.get(tmpCode) instanceof String)) {
                            tmpVals.add("");
                        } else {
                            if(dataMap.get(tmpCode) instanceof List) {
                                tmpVals = (List) dataMap.get(tmpCode);
                            } else {
                                tmpVals.add(dataMap.get(tmpCode));
                            }
                        }
                        rowCount = rowCount == 0 ? tmpVals.size() : rowCount;
                        tagValMap.put(tmpCode, tmpVals);
                    }
                    html += "<td align='center' width='6%' class='td_list_1'><a class='btnAdd' onclick='return Ops.addTr(this);'></a></td></tr>";
                    for (int row = 0; row < rowCount; row++) {
                        html += "<tr>";
                        for (Map<String, Object> map : tagList) {
                            String tmpCode = map.get("code").toString();
                            String tmpTagType = map.get("tagType").toString();
                            String tmpClassAttr = map.get("required").toString().equals("yes") ? " validate[required]" : "";
                            html += "<td class='td_list_2'>";
                            if (tmpTagType.equalsIgnoreCase("date")) {
                                html += "<input type='text' name='" + tmpCode + "' value='" + tagValMap.get(tmpCode).get(row) + "' class='input_240" + tmpClassAttr + "' onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'});\" readonly='readonly'/>";
                            } else if (tmpTagType.equalsIgnoreCase("timestamp")) {
                                html += "<input type='text' name='" + tmpCode + "' value='" + tagValMap.get(tmpCode).get(row) + "' class='input_240" + tmpClassAttr + "' onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});\" readonly='readonly'/>";
                            } else if (tmpTagType.equalsIgnoreCase("textarea")) {
                                html += "<textarea name='" + tmpCode + "' class='input_textarea_600" + tmpClassAttr + "'>" + tagValMap.get(tmpCode).get(row) + "</textarea>";
                            } else {
                                html += "<input type='text' name='" + tmpCode + "' value='" + tagValMap.get(tmpCode).get(row) + "' class='input_240" + tmpClassAttr + "'/>";
                            }
                        }
                        html += "</td><td class='td_list_2'>";
                        html += (row > 0 ? "<a class='btnDel' onclick='return Ops.removeTr(this,1);'></a><a onclick='return Ops.up(this);' title='上移'>上</a><a onclick='return Ops.down(this);' title='下移'>下</a>" : "");
                        html += "</td></tr>";
                    }
                    html += "</table>";
                } else {
                    html += "<input type='text' name='" + code + "' value='" + val + "' class='input_240" + (required.equals("yes") ? " validate[required]" : "") + "' fuzzy='" + fuzzy + "'/>";
                }
            } else if (tagType.startsWith("Select_") || tagType.startsWith("Radio_") || tagType.startsWith("Checkbox_")) {
                DataDict dataDict = dataDictService.findByCode("tagType_" + tagType);
                if (dataDict != null && StringUtils.isNotBlank(dataDict.getDataSource()) && dataDict.getDataSource().trim().toUpperCase().startsWith("SELECT ")) {
                    List<Map<String, Object>> itemList = ResultSetUtils.convertList(jdbcTemplate.queryForList(dataDict.getDataSource()));
                    if (tagType.startsWith("Select_")) {
                        html += "<select name='" + code + "' class='input_select" + classAttr + "' onchange=\"$('[name=" + code + "_val]').val($(this).find('option:selected').text());\">";
                        html += "<option value='' selected>--请选择--</option>";
                        for (Map<String, Object> item : itemList) {
                            html += "<option value='" + item.get("code") + "'" + (item.get("code").toString().equals(val) ? " selected='selected'" : "") + ">" + item.get("name") + "</option>";
                        }
                        html += "</select>";
                        html += "<input type='hidden' name='" + code + "_val' value='" + (dataMap.get(code + "_val") == null ? "" : dataMap.get(code + "_val")) + "' class='input_240'/>";
                    } else if (tagType.startsWith("Radio_")) {
                        for (Map<String, Object> item : itemList) {
                            html += "<input type='radio' name='" + code + "' value='" + item.get("code") + "' class='input_radio" + classAttr + "' " + (item.get("code").toString().equals(val) ? "checked='checked'" : "") + "/>" + item.get("name");
                        }
                    } else if (tagType.startsWith("Checkbox_")) {
                        for (Map<String, Object> item : itemList) {
                            html += "<input type='text' name='" + item.get("code") + "' value='" + val + "' class='input_checkbox" + classAttr + "' " + (item.get("code").toString().equals(val) ? "checked='checked'" : "") + "/>" + item.get("name");
                        }
                    }
                } else {
                    html += "<input type='text' name='" + code + "' value='" + val + "' class='input_240" + classAttr + "' fuzzy='" + fuzzy + "' displayMode='" + displayMode + "' " + readOnly + "/>";
                }
            } else if (tagType.equalsIgnoreCase("file")) {
                html += "<input type='file' name='file' class='input_240'/>";
            } else {
                html += "<input type='text' name='" + code + "' value='" + val + "' class='input_240" + classAttr + "' fuzzy='" + fuzzy + "' displayMode='" + displayMode + "' " + readOnly + "/>";
            }
            html += "</td>";

            if (tagType.equalsIgnoreCase("textarea") || tagType.equalsIgnoreCase("file") || tagType.equalsIgnoreCase("subForm")) {
                if (tmpIndex == 1) {
                    html = html.replace("${" + itemIndex + "}", " colspan='3'");
                } else if (tmpIndex == 2) {
                    html = html.replace("${" + (itemIndex - 1) + "}", " colspan='3'");
                    html = html.replace("${" + itemIndex + "}", " colspan='3'");
                }
            } else if (tmpIndex == 1 && itemIndex == editList.size()) {
                html = html.replace("${" + itemIndex + "}", " colspan='3'");
            }
            if (tmpIndex == 2 || itemIndex == editList.size() || (tmpIndex == 1 && (tagType.equalsIgnoreCase("textarea") || tagType.equalsIgnoreCase("file") || tagType.equalsIgnoreCase("subForm")))) {
                html += "</tr>";
                tmpIndex = 0;
            }
        }
        return html;
    }

    /**
     * 将JSON转成HTML串
     */
    public static String view(DataDictService dataDictService, Map<String, Object> metaTag, Map<String, Object> dataMap) {
        List<Map<String, String>> editList = (List<Map<String, String>>) metaTag.get("editItems");
        int tmpIndex = 0;
        int itemIndex = 0;
        String html = "<tr>";
        for (Map<String, String> entry : editList) {
            String tagType = entry.get("tagType").replace("tagType_", "");
            String code = tagType.startsWith("Select") ? entry.get("code") + "_val" : entry.get("code");
            String val = dataMap.get(code) == null ? "" : dataMap.get(code).toString();

            itemIndex++;
            tmpIndex++;
            if (tmpIndex == 3 || (tmpIndex == 1 && (tagType.equalsIgnoreCase("textarea") || tagType.equalsIgnoreCase("subForm")))) {
                html += "<tr>";
            } else if (tmpIndex == 2 && (tagType.equalsIgnoreCase("textarea") || tagType.equalsIgnoreCase("subForm"))) {
                html += "</tr><tr>";
            }
            html += "<td class='td_table_1'>" + entry.get("name") + "</td><td class='td_table_2' ${" + itemIndex + "}>";
            if (tagType.equals("subForm")) {
                DataDict dataDict = dataDictService.findByCode(entry.get("subForm").toString());
                if (dataDict != null && StringUtils.isNotBlank(dataDict.getDataSource())) {
                    String[] fieldArr = dataDict.getDataSource().split(",");
                    List<String> titles = new ArrayList<>(fieldArr.length);
                    List<String> tagNames = new ArrayList<>(fieldArr.length);
                    for (String field : fieldArr) {
                        String[] tmpField = field.split(":");
                        tagNames.add(tmpField[0]);
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
                    html += "<table class='table_all' align='center' border='0' cellpadding='0' cellspacing='0' style='margin: 0'>";
                    html += "<tr>";
                    for (String title : titles) {
                        html += "<td align='center' class='td_list_1'>" + title + "</td>";
                    }
                    html += "</tr>";
                    for (int row = 0; row < rowCount; row++) {
                        html += "<tr>";
                        for (int i = 0; i < tagNames.size(); i++) {
                            String tmpName = tagNames.get(i);
                            html += "<td class='td_list_2'>" + valMap.get(tmpName).get(row) + "</td>";
                        }
                        html += "</tr>";
                    }
                    html += "</table>";
                }
            } else {
                if (dataMap.get(code) instanceof Date) {
                    html += DateFormatUtils.format((Date) dataMap.get(code), "yyyy-MM-dd");
                } else if (dataMap.get(code) instanceof Timestamp) {
                    html += DateFormatUtils.format((Timestamp) dataMap.get(code), "yyyy-MM-dd HH:mm:ss");
                } else {
                    html += val;
                }
            }
            html += "</td>";
            if (tagType.equalsIgnoreCase("textarea") || tagType.equalsIgnoreCase("subForm")) {
                if (tmpIndex == 1) {
                    html = html.replace("${" + itemIndex + "}", " colspan='3'");
                } else if (tmpIndex == 2) {
                    html = html.replace("${" + (itemIndex - 1) + "}", " colspan='3'");
                    html = html.replace("${" + itemIndex + "}", " colspan='3'");
                }
            } else if (tmpIndex == 1 && itemIndex == editList.size()) {
                html = html.replace("${" + itemIndex + "}", " colspan='3'");
            }
            if (tmpIndex == 2 || itemIndex == editList.size() || (tmpIndex == 1 && (tagType.equalsIgnoreCase("textarea") || tagType.equalsIgnoreCase("subForm")))) {
                html += "</tr>";
                tmpIndex = 0;
            }
        }
        return html;
    }

}
