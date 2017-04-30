package com.zhanlu.framework.nosql.util;

import com.zhanlu.framework.common.utils.ConvertUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 列表页面搜索项
 */
public class QueryItem {

    private static Map<String, String> COMPARE_TYPE_MAP = PageEnum.CompareType.namesMap();
    private static Map<String, Class<?>> DATA_TYPE_MAP = PageEnum.DataType.namesMap();

    private String dataType;
    private String compareType;
    private String fieldName;
    private Object fieldVal;

    public QueryItem(String attrName, String attrVal) {
        String[] attrNameArr = attrName.split("_");
        fieldName = attrName.contains("_OR_") ? attrName : attrName.replace(attrNameArr[0] + "_" + attrNameArr[1] + "_", "");
        compareType = attrName.contains("_OR_") ? "or" : attrNameArr[0];
        dataType = attrName.contains("_OR_") ? "String" : attrNameArr[1];
        if (attrName.contains("_OR_")) {
            fieldVal = attrVal;
        } else {
            if (attrVal != null && attrVal.trim().length() > 0) {
                fieldVal = ConvertUtils.convertStringToObject(attrVal, PageEnum.DataType.valueOf(dataType).getClazz());
            }
        }
    }

    public static QueryItem buildSearchItem(String attrName, String attrVal) {
        String[] attrNameArr = attrName.split("_");
        if (!COMPARE_TYPE_MAP.containsKey(attrNameArr[0]) || !DATA_TYPE_MAP.containsKey(attrNameArr[1])) {
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

    public String getCompareType() {
        return compareType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldVal() {
        return fieldVal;
    }
}
