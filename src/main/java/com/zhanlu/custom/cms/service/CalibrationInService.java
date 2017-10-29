package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.CalibrationInDao;
import com.zhanlu.custom.cms.entity.CalibrationIn;
import com.zhanlu.custom.cms.entity.CalibrationInTask;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.common.service.CommonService;
import com.zhanlu.framework.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 月度内校
 */
@Service
public class CalibrationInService extends CommonService<CalibrationIn, Long> {

    @Autowired
    private CalibrationInDao calibrationInDao;
    @Autowired
    private CalibrationInTaskService calibrationInTaskService;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = calibrationInDao;
    }

    @Transactional
    public Map<String, Object> generateTask(User user) {
        Page<CalibrationIn> page = new Page<>(Integer.MAX_VALUE);
        List<PropertyFilter> filters = new ArrayList<>();
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        filters.add(new PropertyFilter("EQI_status", "1"));
        page = this.findPage(page, filters);

        Map<String, Object> resultMap = new LinkedHashMap<>();
        if (page != null && page.getResult().size() > 0) {
            for (CalibrationIn entity : page.getResult()) {
                CalibrationInTask task = new CalibrationInTask();
                task.setTenantId(entity.getTenantId());
                task.setCreaterId(entity.getCreaterId());
                task.setCreateTime(new Date());
                task.setStatus(1);
                task.setEquipmentId(entity.getEquipmentId());
                calibrationInTaskService.save(task);
                entity.setStatus(2);
            }
            resultMap.put("result", 1);
            resultMap.put("msg", "任务生成成功");
        } else {
            resultMap.put("result", 0);
            resultMap.put("msg", "暂时没有要生成的任务");
        }
        return resultMap;
    }
}
