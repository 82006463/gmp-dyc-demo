package com.zhanlu.meta.service;

import java.util.Map;

public interface FlowService {

    Map<String, Object> saveOrUpdate(Map<String, Object> tableStruct, Map<String, Object> newEntity);

}
