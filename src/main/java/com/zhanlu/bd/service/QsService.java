package com.zhanlu.bd.service;

import com.zhanlu.bd.dao.QsDao;
import com.zhanlu.bd.entity.Qs;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 质量标准服务类
 */
@Service
public class QsService extends CommonService<Qs, Long> {

    @Autowired
    private QsDao qsDao;

    @PostConstruct
    public void initDao() {
        super.commonDao = qsDao;
    }
}
