package com.zhanlu.custom.dms.service;

import com.zhanlu.custom.dms.dao.DmsFileDao;
import com.zhanlu.custom.dms.entity.DmsFile;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 文件
 */
@Service
public class DmsFileService extends CommonService<DmsFile, Long> {

    @Autowired
    private DmsFileDao dmsFileDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = dmsFileDao;
    }

}
