package com.zhanlu.bd.service;

import com.zhanlu.bd.dao.TrtDao;
import com.zhanlu.bd.entity.Trt;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 检验报告模板服务类
 */
@Service
public class TrtService extends CommonService<Trt, Long> {

    @Autowired
    private TrtDao trtDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = trtDao;
    }
}
