package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.CalibrationExtDao;
import com.zhanlu.custom.cms.entity.CalibrationExt;
import com.zhanlu.custom.cms.entity.CalibrationTask;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.common.service.CommonService;
import com.zhanlu.framework.security.entity.User;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 月度外校
 */
@Service
public class CalibrationExtService extends CommonService<CalibrationExt, Long> {

    @Autowired
    private CalibrationExtDao calibrationExtDao;
    @Autowired
    private CalibrationTaskService calibrationTaskService;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = calibrationExtDao;
    }

    @Transactional
    public Map<String, Object> generateTask(User user) {
        Page<CalibrationExt> page = new Page<>(Integer.MAX_VALUE);
        List<PropertyFilter> filters = new ArrayList<>();
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        filters.add(new PropertyFilter("GEI_status", "1"));
        filters.add(new PropertyFilter("LEI_status", "2"));
        page = this.findPage(page, filters);

        Map<String, Object> resultMap = new LinkedHashMap<>();
        if (page != null && page.getResult().size() > 0) {
            for (CalibrationExt entity : page.getResult()) {
                entity.setStatus(3);
            }
            resultMap.put("result", 1);
            resultMap.put("msg", "任务生成成功");
        } else {
            resultMap.put("result", 0);
            resultMap.put("msg", "暂时没有要生成的任务");
        }
        return resultMap;
    }

    @Transactional
    public Map<String, Object> sendTask(User user, Long measureCompId, String approver) {
        Page<CalibrationExt> page = new Page<>(Integer.MAX_VALUE);
        List<PropertyFilter> filters = new ArrayList<>();
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        filters.add(new PropertyFilter("EQI_status", "3"));
        page = this.findPage(page, filters);

        Map<String, Object> resultMap = new LinkedHashMap<>();
        if (page != null && page.getResult().size() > 0) {
            CalibrationTask task = new CalibrationTask();
            task.setTenantId(user.getOrg().getId());
            task.setCreaterId(user.getId());
            task.setCreateTime(new Date());
            task.setStatus(1);
            task.setTaskCode(user.getOrg().getCode() + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
            task.setDrugCompId(user.getOrg().getId());
            task.setMeasureCompId(measureCompId);
            task.setApprover(approver);
            task.setCalibrationMode(2);
            calibrationTaskService.save(task);
            for (CalibrationExt entity : page.getResult()) {
                entity.setStatus(4);
                entity.setTaskId(task.getId());
            }
            resultMap.put("result", 1);
            resultMap.put("msg", "任务发送成功");
        } else {
            resultMap.put("result", 0);
            resultMap.put("msg", "暂时没有要发送的任务");
        }
        return resultMap;
    }
}
