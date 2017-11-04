package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.CalibrationTmpDao;
import com.zhanlu.custom.cms.entity.CalibrationTask;
import com.zhanlu.custom.cms.entity.CalibrationTmp;
import com.zhanlu.custom.cms.entity.Equipment;
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
 * 月度临校
 */
@Service
public class CalibrationTmpService extends CommonService<CalibrationTmp, Long> {

    @Autowired
    private CalibrationTmpDao calibrationTmpDao;
    @Autowired
    private CalibrationTaskService calibrationTaskService;
    @Autowired
    private EquipmentService equipmentService;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = calibrationTmpDao;
    }

    @Transactional
    public boolean init(User user) {
        Page<Equipment> page = new Page<>(Integer.MAX_VALUE);
        List<PropertyFilter> filters = new ArrayList<>();
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        filters.add(new PropertyFilter("EQI_tmpStatus", "1"));
        equipmentService.findPage(page, filters);
        if (page != null && page.getResult().size() > 0) {
            for (Equipment eq : page.getResult()) {
                eq.setTmpStatus(2);

                CalibrationTmp entity = new CalibrationTmp();
                entity.setTenantId(eq.getTenantId());
                entity.setCreaterId(user.getId());
                entity.setCreateTime(new Date());
                entity.setStatus(1);
                entity.setEquipmentId(eq.getId());
                entity.setLastExpectDate(eq.getLastExpectDate());
                entity.setLastActualDate(eq.getLastActualDate());
                entity.setExpectDate(eq.getExpectDate());
                this.save(entity);
            }
        }
        return true;
    }

    @Transactional
    public Map<String, Object> generateTask(User user) {
        Page<CalibrationTmp> page = new Page<>(Integer.MAX_VALUE);
        List<PropertyFilter> filters = new ArrayList<>();
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        filters.add(new PropertyFilter("EQI_status", "1"));
        page = this.findPage(page, filters);

        Map<String, Object> resultMap = new LinkedHashMap<>();
        if (page != null && page.getResult().size() > 0) {
            for (CalibrationTmp entity : page.getResult()) {
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

    @Transactional
    public Map<String, Object> sendTask(User user, Long measureCompId, String approver) {
        Page<CalibrationTmp> page = new Page<>(Integer.MAX_VALUE);
        List<PropertyFilter> filters = new ArrayList<>();
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        filters.add(new PropertyFilter("EQI_status", "2"));
        page = this.findPage(page, filters);

        Map<String, Object> resultMap = new LinkedHashMap<>();
        if (page != null && page.getResult().size() > 0) {
            CalibrationTask task = new CalibrationTask();
            task.setTenantId(user.getOrg().getId());
            task.setCreaterId(user.getId());
            task.setCreateTime(new Date());
            task.setStatus(1);
            task.setMeasureCompId(measureCompId);
            task.setApprover(approver);
            task.setReviewer(user.getUsername());
            task.setCurrent(task.getApprover());
            task.setCalibrationMode(3);
            calibrationTaskService.save(task);
            for (CalibrationTmp entity : page.getResult()) {
                entity.setStatus(3);
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
