package com.zhanlu.custom.dm.dao;

import com.zhanlu.custom.dm.entity.BackupDb;
import com.zhanlu.framework.common.dao.CommonDao;
import org.springframework.stereotype.Repository;

/**
 * 备份数据库
 */
@Repository
public class BackupDbDao extends CommonDao<BackupDb, Long> {
}
