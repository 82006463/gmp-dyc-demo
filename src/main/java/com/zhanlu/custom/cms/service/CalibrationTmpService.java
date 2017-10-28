package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.CalibrationTmpDao;
import com.zhanlu.custom.cms.entity.CalibrationExt;
import com.zhanlu.custom.cms.entity.CalibrationExtTask;
import com.zhanlu.custom.cms.entity.CalibrationTmp;
import com.zhanlu.custom.cms.entity.CalibrationTmpTask;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.common.service.CommonService;
import com.zhanlu.framework.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 月度临校
 */
@Service
public class CalibrationTmpService extends CommonService<CalibrationTmp, Long> {

    @Autowired
    private CalibrationTmpDao calibrationTmpDao;
    @Autowired
    private CalibrationTmpTaskService calibrationTmpTaskService;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = calibrationTmpDao;
    }

    @Transactional
    public boolean generateTask(User user) {
        Page<CalibrationTmp> page = new Page<>(Integer.MAX_VALUE);
        List<PropertyFilter> filters = new ArrayList<>();
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        filters.add(new PropertyFilter("EQI_status", "1"));
        page = this.findPage(page, filters);
        if (page != null && page.getResult().size() > 0) {
            for (CalibrationTmp entity : page.getResult()) {
                CalibrationTmpTask task = new CalibrationTmpTask();
                task.setTenantId(entity.getTenantId());
                task.setCreaterId(entity.getCreaterId());
                task.setCreateTime(new Date());
                task.setStatus(1);
                task.setEquipmentId(entity.getEquipmentId());
                calibrationTmpTaskService.save(task);
                entity.setStatus(2);
            }
        }
        return true;
    }
}
