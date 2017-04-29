package com.zhanlu.framework.nosql.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/29.
 */
public enum DataTypeEnum {

    Byte(byte.class), Char(char.class), Int(int.class), Long(long.class), String(String.class), Float(float.class), Double(float.class),
    Boolean(boolean.class), Date(java.util.Date.class), Timestamp(Timestamp.class);

    private Class<?> clazz;

    DataTypeEnum(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return this.clazz;
    }

    public static List<String> getNames() {
        DataTypeEnum[] values = DataTypeEnum.values();
        List<String> names = new ArrayList<>(values.length);
        for (DataTypeEnum value : values) {
            names.add(value.name());
        }
        return names;
    }

    public static Map<String, Class<?>> getMap() {
        DataTypeEnum[] values = DataTypeEnum.values();
        Map<String, Class<?>> items = new LinkedHashMap<>(values.length);
        for (DataTypeEnum value : values) {
            items.put(value.name(), value.getClazz());
        }
        return items;
    }
}
