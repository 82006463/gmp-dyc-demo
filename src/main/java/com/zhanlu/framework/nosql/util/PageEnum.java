package com.zhanlu.framework.nosql.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 页面标签的枚举类
 */
public class PageEnum {

    /**
     * 数据类型
     */
    public enum DataType {

        Byte(byte.class), Char(char.class), Int(int.class), Long(long.class), String(String.class), Float(float.class), Double(float.class),
        Boolean(boolean.class), Date(java.util.Date.class), Timestamp(java.sql.Timestamp.class);

        private Class<?> clazz;

        DataType(Class<?> clazz) {
            this.clazz = clazz;
        }

        public Class<?> getClazz() {
            return this.clazz;
        }

        public static List<String> names() {
            DataType[] values = DataType.values();
            List<String> names = new ArrayList<>(values.length);
            for (DataType value : values) {
                names.add(value.name());
            }
            return names;
        }

        public static Map<String, Class<?>> namesMap() {
            DataType[] values = DataType.values();
            Map<String, Class<?>> items = new LinkedHashMap<>(values.length);
            for (DataType value : values) {
                items.put(value.name(), value.getClazz());
            }
            return items;
        }
    }

    /**
     * 数据比较类型
     */
    public enum CompareType {

        Eq("eq"), Like("like"), Gt("gt"), Gte("gte"), Lt("lt"), Lte("lte"), Ne("ne"), In("in"), And("and"), Or("or");

        private String compareType;

        CompareType(String compareType) {
            this.compareType = compareType;
        }

        public String getCompareType() {
            return compareType;
        }

        public static List<String> names() {
            CompareType[] values = CompareType.values();
            List<String> names = new ArrayList<>(values.length);
            for (CompareType value : values) {
                names.add(value.name());
            }
            return names;
        }

        public static List<String> compareTypes() {
            CompareType[] values = CompareType.values();
            List<String> compareTypes = new ArrayList<>(values.length);
            for (CompareType value : values) {
                compareTypes.add(value.getCompareType());
            }
            return compareTypes;
        }

        public static Map<String, String> namesMap() {
            CompareType[] values = CompareType.values();
            Map<String, String> items = new LinkedHashMap<>(values.length);
            for (CompareType value : values) {
                items.put(value.name(), value.getCompareType());
            }
            return items;
        }

        public static Map<String, String> compareTypesMap() {
            CompareType[] values = CompareType.values();
            Map<String, String> items = new LinkedHashMap<>(values.length);
            for (CompareType value : values) {
                items.put(value.getCompareType(), value.name());
            }
            return items;
        }
    }

    /**
     * 标签类型
     */
    public enum TagType {

        Input("input"), Date("date"), Timestamp("timestamp"), Textarea("textarea"), Select("select"), Radio("radio"), Checkbox("checkbox");

        private String tag;

        TagType(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return this.tag;
        }

        public static List<String> names() {
            TagType[] values = TagType.values();
            List<String> names = new ArrayList<>(values.length);
            for (TagType value : values) {
                names.add(value.name());
            }
            return names;
        }

        public static List<String> tags() {
            TagType[] values = TagType.values();
            List<String> tags = new ArrayList<>(values.length);
            for (TagType value : values) {
                tags.add(value.getTag());
            }
            return tags;
        }

        public static Map<String, String> namesMap() {
            TagType[] values = TagType.values();
            Map<String, String> items = new LinkedHashMap<>(values.length);
            for (TagType value : values) {
                items.put(value.name(), value.getTag());
            }
            return items;
        }

        public static Map<String, String> tagsMap() {
            TagType[] values = TagType.values();
            Map<String, String> items = new LinkedHashMap<>(values.length);
            for (TagType value : values) {
                items.put(value.getTag(), value.name());
            }
            return items;
        }
    }

}
