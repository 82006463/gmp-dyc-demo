package com.zhanlu.framework.nosql.service;

import java.util.Map;

/**
 * 审计追踪服务接口
 */
public interface AuditService {
    Map<String, Object> insert(Map<String, Object> oldEntity, Map<String, Object> newEntity);
}
