package com.zhanlu.custom.dm.service;

import com.zhanlu.custom.dm.dao.ClientSettingDao;
import com.zhanlu.custom.dm.entity.ClientSetting;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 客户端设置
 */
@Service
public class ClientSettingService extends CommonService<ClientSetting, Long> {

    @Autowired
    private ClientSettingDao clientSettingDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = clientSettingDao;
    }

}
