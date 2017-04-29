package com.zhanlu.framework.nosql.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/29.
 */
public class EditItem {

    private static Map<String, Class<?>> DATA_TYPE_MAP = DataTypeEnum.getMap();

    private String dataType;
    private String fieldName;
    private Object fieldVal;

    public EditItem(String dataType, String attrName, String attrVal) {
        this.fieldName = attrName;
        this.dataType = dataType;
        if (attrVal != null && attrVal.trim().length() > 0) {
            if (dataType.equalsIgnoreCase("Byte")) {
                fieldVal = Byte.parseByte(attrVal);
            } else if (dataType.equalsIgnoreCase("Int")) {
                fieldVal = Integer.parseInt(attrVal);
            } else if (dataType.equalsIgnoreCase("Long")) {
                fieldVal = Long.parseLong(attrVal);
            } else if (dataType.equalsIgnoreCase("Float")) {
                fieldVal = Float.parseFloat(attrVal);
            } else if (dataType.equalsIgnoreCase("Double")) {
                fieldVal = Double.parseDouble(attrVal);
            } else if (dataType.equalsIgnoreCase("Boolean")) {
                fieldVal = Boolean.parseBoolean(attrVal);
            } else {
                fieldVal = attrVal;
            }
        }
    }

    public static EditItem buildEditItem(String dataType, String attrName, String attrVal) {
        return new EditItem(dataType, attrName, attrVal);
    }

    public static List<EditItem> buildEditItems(List<Map<String, String>> structList, Map<String, String[]> paramMap) {
        Map<String, String> fieldMap = new LinkedHashMap<>(paramMap.size());
        for (Map<String, String> struct : structList) {
            fieldMap.put(struct.get("code"), struct.get("dataType").replace("dataType_", ""));
        }
        List<EditItem> items = new ArrayList<>(paramMap.size());
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            if (!fieldMap.containsKey(entry.getKey())) {
                continue;
            }
            String dataType = fieldMap.get(entry.getKey()).replace("varchar", "String");
            dataType = dataType.replace("int", "Int").replace("date", "Date");
            EditItem editItem = EditItem.buildEditItem(dataType, entry.getKey(), entry.getValue()[0]);
            if (editItem == null) {
                continue;
            }
            items.add(editItem);
        }
        return items;
    }

    public static Map<String, Object> toMap(List<Map<String, String>> structList, Map<String, String[]> paramMap) {
        List<EditItem> editItems = EditItem.buildEditItems(structList, paramMap);
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
