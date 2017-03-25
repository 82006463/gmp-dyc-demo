package com.zhanlu.bd.service;

import com.zhanlu.bd.dao.QsDao;
import com.zhanlu.bd.dao.TestItemDao;
import com.zhanlu.bd.entity.TestItem;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 测试项服务类
 *
 */
@Service
public class TestItemService extends CommonService<TestItem, Long> {

    @Autowired
    private TestItemDao testItemDao;

    @PostConstruct
    public void initDao() {
        super.commonDao = testItemDao;
    }

}
