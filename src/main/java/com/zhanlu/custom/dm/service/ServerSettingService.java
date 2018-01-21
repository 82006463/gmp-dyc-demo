package com.zhanlu.custom.dm.service;

import com.zhanlu.custom.dm.dao.ServerSettingDao;
import com.zhanlu.custom.dm.entity.ServerSetting;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 服务端设置
 */
@Service
public class ServerSettingService extends CommonService<ServerSetting, Long> {

    @Autowired
    private ServerSettingDao serverSettingDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = serverSettingDao;
    }

}
