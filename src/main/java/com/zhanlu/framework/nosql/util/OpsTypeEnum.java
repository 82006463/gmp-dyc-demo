package com.zhanlu.framework.nosql.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/29.
 */
public enum OpsTypeEnum {

    Eq, Like, Gt, Gte, Lt, Lte;

    public static List<String> getNames() {
        OpsTypeEnum[] values = OpsTypeEnum.values();
        List<String> names = new ArrayList<>(values.length);
        for (OpsTypeEnum value : values) {
            names.add(value.name());
        }
        return names;
    }

    public static Map<String, Class<?>> getMap() {
        OpsTypeEnum[] values = OpsTypeEnum.values();
        Map<String, Class<?>> items = new LinkedHashMap<>(values.length);
        for (OpsTypeEnum value : values) {
            items.put(value.name(), value.getClass());
        }
        return items;
    }
}
