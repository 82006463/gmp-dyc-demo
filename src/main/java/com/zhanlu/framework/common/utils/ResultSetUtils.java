package com.zhanlu.framework.common.utils;

import org.apache.commons.collections.map.LinkedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultSetUtils {

    public static List<Map<String, Object>> convertList(List<Map<String, Object>> resultList) {
        List<Map<String, Object>> tmpList = new ArrayList<>(resultList.size());
        for (Map<String, Object> map : resultList) {
            tmpList.add(lowerKeyForMap(map));
        }
        return tmpList;
    }

    public static Map<String, Object> lowerKeyForMap(Map<String, Object> map) {
        Map<String, Object> tmpMap = new LinkedMap((int) (map.size() / 0.75));
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            tmpMap.put(entry.getKey().toLowerCase(), entry.getValue());
        }
        return tmpMap;
    }
}
