package com.zhanlu.framework.nosql.item;

import com.zhanlu.framework.common.utils.ConvertUtils;
import com.zhanlu.framework.config.entity.DataDict;
import com.zhanlu.framework.config.service.DataDictService;
import com.zhanlu.framework.util.PageEnum;
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

    public EditItem(String dataType, String tagType, String isArray, String attrName, String[] attrValArr) {
        this.fieldName = attrName;
        this.dataType = dataType;
        if (attrValArr != null) {
            if (tagType.equals("subForm")) {
                fieldVal = attrValArr;
            } else {
                if (dataType.equalsIgnoreCase("String")) {
                    fieldVal = isArray.equals("1") ? attrValArr : attrValArr[0];
                } else if (isArray.equals("1")) {
                    Object[] tmpArr = new Object[attrValArr.length];
                    for (int i = 0; i < tmpArr.length; i++) {
                        if (attrValArr[i] == null || attrValArr[i].trim().length() == 0) {
                            continue;
                        }
                        tmpArr[i] = ConvertUtils.convertStringToObject(attrValArr[i], PageEnum.DataType.valueOf(dataType).getClazz());
                    }
                    fieldVal = tmpArr;
                } else {
                    if (attrValArr[0] != null && attrValArr[0].trim().length() > 0) {
                        try {
                            fieldVal = ConvertUtils.convertStringToObject(attrValArr[0], PageEnum.DataType.valueOf(dataType).getClazz());
                        } catch (Exception e) {
                            fieldVal = attrValArr[0];
                        }
                    }
                }
            }
        } else if (dataType.equalsIgnoreCase("String")) {
            //fieldVal = "";
        }
    }

    public static EditItem buildEditItem(String dataType, String tagType, String isArray, String attrName, String[] attrValArr) {
        if (attrValArr == null || attrValArr[0] == null) {
            return null;
        }
        return new EditItem(dataType, tagType, isArray, attrName, attrValArr);
    }

    public static List<EditItem> buildEditItems(DataDictService dataDictService, List<Map<String, String>> structList, Map<String, String[]> paramMap) {
        Map<String, String> dataTypeMap = new LinkedHashMap<>(paramMap.size());
        Map<String, String> tagTypeMap = new LinkedHashMap<>(paramMap.size());
        Map<String, String> arrayMap = new LinkedHashMap<>(paramMap.size());
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
                        arrayMap.put(tmpField[0], "1");
                    }
                }
            } else {
                tagTypeMap.put(struct.get("code"), tagType);
                dataTypeMap.put(struct.get("code"), struct.get("dataType").replace("dataType_", ""));
                arrayMap.put(struct.get("code"), struct.get("array").replace("yesNo_", ""));
                if (tagType.startsWith("Select")) {
                    tagTypeMap.put(struct.get("code") + "_val", "Input");
                    dataTypeMap.put(struct.get("code") + "_val", "String");
                    arrayMap.put(struct.get("code") + "_val", arrayMap.get(struct.get("code")));
                }
            }
        }
        List<EditItem> items = new ArrayList<>(paramMap.size());
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            if (!dataTypeMap.containsKey(entry.getKey())) {
                continue;
            }
            String key = entry.getKey();
            EditItem editItem = EditItem.buildEditItem(dataTypeMap.get(key), tagTypeMap.get(key), arrayMap.get(key), key, entry.getValue());
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
