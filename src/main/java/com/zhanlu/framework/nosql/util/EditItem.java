package com.zhanlu.framework.nosql.util;

import com.zhanlu.framework.common.utils.ConvertUtils;
import com.zhanlu.framework.config.entity.DataDict;
import com.zhanlu.framework.config.service.DataDictService;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 编辑页面项
 */
public class EditItem {

    private String dataType;
    private String fieldName;
    private Object fieldVal;

    public EditItem(String dataType, String tagType, String attrName, String[] attrValArr) {
        this.fieldName = attrName;
        this.dataType = dataType;
        if (attrValArr != null) {
            if (tagType.equals("subForm")) {
                fieldVal = attrValArr;
            } else {
                try {
                    fieldVal = ConvertUtils.convertStringToObject(attrValArr[0], PageEnum.DataType.valueOf(dataType).getClazz());
                } catch (Exception e) {
                    fieldVal = attrValArr;
                }
            }
        } else if (dataType.equalsIgnoreCase("String")) {
            //fieldVal = "";
        }
    }

    public static EditItem buildEditItem(String dataType, String tagType, String attrName, String[] attrValArr) {
        if (attrValArr == null || attrValArr[0] == null) {
            return null;
        }
        return new EditItem(dataType, tagType, attrName, attrValArr);
    }

    public static List<EditItem> buildEditItems(DataDictService dataDictService, List<Map<String, String>> structList, Map<String, String[]> paramMap) {
        Map<String, String> dataTypeMap = new LinkedHashMap<>(paramMap.size());
        Map<String, String> tagTypeMap = new LinkedHashMap<>(paramMap.size());
        for (Map<String, String> struct : structList) {
            String tagType = struct.get("tagType").replace("tagType_", "");
            if (tagType.equals("subForm")) {
                DataDict dataDict = dataDictService.findByCode(struct.get("subForm"));
                if (dataDict != null && StringUtils.isNotBlank(dataDict.getDataSource())) {
                    String[] fieldArr = dataDict.getDataSource().split(",");
                    for (String field : fieldArr) {
                        String[] tmpField = field.split(":");
                        tagTypeMap.put(tmpField[0], "subForm");
                        dataTypeMap.put(tmpField[0], "String");
                    }
                }
            } else {
                tagTypeMap.put(struct.get("code"), tagType);
                dataTypeMap.put(struct.get("code"), struct.get("dataType").replace("dataType_", ""));
                if (tagType.startsWith("Select")) {
                    tagTypeMap.put(struct.get("code") + "_val", "Input");
                    dataTypeMap.put(struct.get("code") + "_val", "String");
                }
            }
        }
        List<EditItem> items = new ArrayList<>(paramMap.size());
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            if (!dataTypeMap.containsKey(entry.getKey())) {
                continue;
            }
            String tagType = tagTypeMap.get(entry.getKey());
            String dataType = dataTypeMap.get(entry.getKey());
            EditItem editItem = EditItem.buildEditItem(dataType, tagType, entry.getKey(), entry.getValue());
            if (editItem == null) {
                continue;
            }
            items.add(editItem);
        }
        return items;
    }

    public static Map<String, Object> toMap(DataDictService dataDictService, List<Map<String, String>> structList, Map<String, String[]> paramMap) {
        List<EditItem> editItems = EditItem.buildEditItems(dataDictService, structList, paramMap);
        Map<String, Object> resultMap = new LinkedHashMap<>(editItems.size());
        for (EditItem item : editItems) {
            resultMap.put(item.getFieldName(), item.getFieldVal());
        }
        return resultMap;
    }

    public String getDataType() {
        return dataType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldVal() {
        return fieldVal;
    }

}
