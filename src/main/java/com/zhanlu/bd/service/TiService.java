package com.zhanlu.bd.service;

import com.zhanlu.bd.dao.TiDao;
import com.zhanlu.bd.entity.Ti;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 测试项服务类
 */
@Service
public class TiService extends CommonService<Ti, Long> {

    @Autowired
    private TiDao tiDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = tiDao;
    }

}
