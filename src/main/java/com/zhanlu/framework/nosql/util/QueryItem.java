package com.zhanlu.framework.nosql.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/29.
 */
public class QueryItem {

    private static Map<String, Class<?>> oteMap = OpsTypeEnum.getMap();
    private static Map<String, Class<?>> dteMap = DataTypeEnum.getMap();

    private String dataType;
    private String opsType;
    private String fieldName;
    private Object fieldVal;

    public QueryItem(String attrName, String attrVal) {
        String[] attrNameArr = attrName.split("_");
        fieldName = attrName.replace(attrNameArr[0] + "_" + attrNameArr[1] + "_", "");
        opsType = attrNameArr[0];
        dataType = attrNameArr[1];
        if (dataType.equals("Byte")) {
            fieldVal = Byte.parseByte(attrVal);
        } else if (dataType.equals("Int")) {
            fieldVal = Integer.parseInt(attrVal);
        } else if (dataType.equals("Long")) {
            fieldVal = Long.parseLong(attrVal);
        } else if (dataType.equals("Float")) {
            fieldVal = Float.parseFloat(attrVal);
        } else if (dataType.equals("Double")) {
            fieldVal = Double.parseDouble(attrVal);
        } else if (dataType.equals("Boolean")) {
            fieldVal = Boolean.parseBoolean(attrVal);
        } else {
            fieldVal = attrVal;
        }
    }

    public static QueryItem buildSearchItem(String attrName, String attrVal) {
        String[] attrNameArr = attrName.split("_");
        if (!dteMap.containsKey(attrNameArr[0]) || !oteMap.containsKey(attrNameArr[1])) {
            return null;
        }
        return new QueryItem(attrName, attrVal);
    }

    public static List<QueryItem> buildSearchItems(Map<String, String[]> paramMap) {
        List<QueryItem> items = new ArrayList<>(2);
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            QueryItem searchItem = QueryItem.buildSearchItem(entry.getKey(), entry.getValue()[0]);
            if (searchItem == null) {
                continue;
            }
            items.add(searchItem);
        }
        return items;
    }

    public String getDataType() {
        return dataType;
    }

    public String getOpsType() {
        return opsType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldVal() {
        return fieldVal;
    }
}
