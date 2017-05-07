package com.zhanlu.framework.nosql.util;

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
            html += "<td class='td_table_1'>" + entry.get("desc") + "：</td><td class='td_table_2' " + (itemIndex % 2 == 1 && itemIndex == queryList.size() ? "colspan='3'" : "") + ">";
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
            html += "<td align=center class='td_list_1'>" + entry.get("desc") + "</td>";
            fieldMap.put(entry.get("code"), entry.get("desc"));
        }
        for (Map<String, String> entry : editList) {
            tagTypeMap.put(entry.get("code"), entry.get("tagType"));
        }
        html += "<td align=center width=10% class='td_list_1'>操作</td></tr>";
        for (Map<String, Object> entry : entityList) {
            html += "<tr>";
            for (Map.Entry<String, String> field : fieldMap.entrySet()) {
                html += "<td class='td_list_2' align=left>";
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
                html += "</td>";
            }
            html += "<td class='td_list_2' align=left>";
            if (metaTag.get("list_delete_url") != null) {
                html += "<a href='" + contextPath + metaTag.get("list_delete_url") + "/" + entry.get("_id") + "' class='btnDel' title='删除' onclick='return confirmDel();'>删除</a>";
            }
            if (metaTag.get("list_update_url") != null) {
                html += "<a href='" + contextPath + metaTag.get("list_update_url") + "/" + entry.get("_id") + "' class='btnEdit'' title='编辑'>编辑</a>";
            }
            if (metaTag.get("list_view_url") != null) {
                html += "<a href='" + contextPath + metaTag.get("list_view_url") + "/" + entry.get("_id") + "' class='btnView'' title='查看'>查看</a>";
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
            String required = entry.get("required");
            String fuzzy = entry.get("fuzzy");
            String code = entry.get("code");
            String val = dataMap.get(code) == null ? "" : dataMap.get(code).toString();

            itemIndex++;
            tmpIndex++;
            if (tmpIndex == 3 || (tmpIndex == 1 && (tagType.equalsIgnoreCase("textarea") || tagType.equalsIgnoreCase("subForm")))) {
                html += "<tr>";
            } else if (tmpIndex == 2 && (tagType.equalsIgnoreCase("textarea") || tagType.equalsIgnoreCase("subForm"))) {
                html += "</tr><tr>";
            }
            html += "<td class='td_table_1'>" + entry.get("desc") + "</td><td class='td_table_2' ${" + itemIndex + "}>";
            if (tagType.equalsIgnoreCase("textarea")) {
                html += "<textarea name='" + code + "' class='input_textarea_600" + (required.equals("yes") ? " validate[required]" : "") + "'>" + val + "</textarea>";
            } else if (tagType.equalsIgnoreCase("date")) {
                if (dataMap.get(code) instanceof Date) {
                    html += "<input type='text' name='" + code + "' value='" + DateFormatUtils.format((Date) dataMap.get(code), "yyyy-MM-dd");
                } else {
                    html += "<input type='text' name='" + code + "' value='" + val;
                }
                html += "' class='input_240" + (required.equals("yes") ? " validate[required]" : "") + "' onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'});\" readonly='readonly'/>";
            } else if (tagType.equalsIgnoreCase("timestamp")) {
                if (dataMap.get(code) instanceof Timestamp) {
                    html += "<input type='text' name='" + code + "' value='" + DateFormatUtils.format((Timestamp) dataMap.get(code), "yyyy-MM-dd HH:mm:ss");
                } else {
                    html += "<input type='text' name='" + code + "' value='" + val;
                }
                html += "' class='input_240" + (required.equals("yes") ? " validate[required]" : "") + "' onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});\" readonly='readonly'/>";
            } else if (tagType.equalsIgnoreCase("subForm")) {
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
                    html += "<td align='center' width='6%' class='td_list_1'><a class='btnAdd' onclick='$('[name=" + code + "_val]').val($(this).find('option:selected').text());'></a></td>";
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
            } else if (tagType.startsWith("Select_") || tagType.startsWith("Radio_") || tagType.startsWith("Checkbox_")) {
                DataDict dataDict = dataDictService.findByCode("tagType_" + tagType);
                if (dataDict != null && StringUtils.isNotBlank(dataDict.getDataSource()) && dataDict.getDataSource().trim().toUpperCase().startsWith("SELECT ")) {
                    List<Map<String, Object>> itemList = ResultSetUtils.convertList(jdbcTemplate.queryForList(dataDict.getDataSource()));
                    if (tagType.startsWith("Select_")) {
                        html += "<select name='" + code + "' class='input_select" + (required.equals("yes") ? " validate[required]" : "") + "' onchange=\"$('[name=" + code + "_val]').val($(this).find('option:selected').text());\">";
                        html += "<option value='' selected>--请选择--</option>";
                        for (Map<String, Object> item : itemList) {
                            html += "<option value='" + item.get("code") + "'" + (item.get("code").toString().equals(val) ? " selected='selected'" : "") + ">" + item.get("name") + "</option>";
                        }
                        html += "</select>";
                        html += "<input type='hidden' name='" + code + "_val' value='" + (dataMap.get(code + "_val") == null ? "" : dataMap.get(code + "_val")) + "' class='input_240'/>";
                    } else if (tagType.startsWith("Radio_")) {
                        for (Map<String, Object> item : itemList) {
                            html += "<input type='radio' name='" + code + "' value='" + item.get("code") + "' class='input_radio" + (required.equals("yes") ? " validate[required]" : "") + "' " + (item.get("code").toString().equals(val) ? "checked='checked'" : "") + "/>" + item.get("name");
                        }
                    } else if (tagType.startsWith("Checkbox_")) {
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
            if (tmpIndex == 2 || itemIndex == editList.size() ||
                    (tmpIndex == 1 && (tagType.equalsIgnoreCase("textarea") || tagType.equalsIgnoreCase("subForm")))) {
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
            String desc = entry.get("desc");
            String val = dataMap.get(code) == null ? "" : dataMap.get(code).toString();

            itemIndex++;
            tmpIndex++;
            if (tmpIndex == 3 || (tmpIndex == 1 && (tagType.equalsIgnoreCase("textarea") || tagType.equalsIgnoreCase("subForm")))) {
                html += "<tr>";
            } else if (tmpIndex == 2 && (tagType.equalsIgnoreCase("textarea") || tagType.equalsIgnoreCase("subForm"))) {
                html += "</tr><tr>";
            }
            html += "<td class='td_table_1'>" + desc + "</td><td class='td_table_2' ${" + itemIndex + "}>";
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
