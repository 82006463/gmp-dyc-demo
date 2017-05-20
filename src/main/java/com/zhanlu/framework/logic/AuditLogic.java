package com.zhanlu.framework.logic;

import java.util.Map;

/**
 * 审计追踪服务接口
 */
public interface AuditLogic {
    Map<String, Object> insert(Map<String, Object> oldEntity, Map<String, Object> newEntity);

    Map<String, Object> insertForRemove(Map<String, Object> oldEntity, Map<String, Object> newEntity);
}
