package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.EquipmentDao;
import com.zhanlu.custom.cms.entity.Equipment;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 器具
 */
@Service
public class EquipmentService extends CommonService<Equipment, Long> {

    @Autowired
    private EquipmentDao equipmentDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = equipmentDao;
    }
}
