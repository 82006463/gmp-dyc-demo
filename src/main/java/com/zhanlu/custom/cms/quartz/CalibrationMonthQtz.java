package com.zhanlu.custom.cms.quartz;

import com.zhanlu.custom.cms.entity.CalibrationExt;
import com.zhanlu.custom.cms.entity.CalibrationIn;
import com.zhanlu.custom.cms.entity.Equipment;
import com.zhanlu.custom.cms.service.CalibrationExtService;
import com.zhanlu.custom.cms.service.CalibrationInService;
import com.zhanlu.custom.cms.service.EquipmentService;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 月度校准定时器
 */
@Service("calibrationMonthQtz")
public class CalibrationMonthQtz {

    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private CalibrationExtService calibrationExtService;
    @Autowired
    private CalibrationInService calibrationInService;

    public void generateMonth() {
        Page<Equipment> pageEq = new Page<>(Integer.MAX_VALUE);
        List<PropertyFilter> filters = new ArrayList<>();
        String year = DateFormatUtils.format(new Date(), "yyyy");
        filters.add(new PropertyFilter("GED_expectCalibrationDate", year + "-01-01 00:00:00"));
        filters.add(new PropertyFilter("LED_expectCalibrationDate", year + "-12-31 23:59:59"));
        pageEq = equipmentService.findPage(pageEq, filters);
        List<Equipment> equipments = pageEq.getResult();
        if (equipments == null || equipments.isEmpty()) {
            return;
        }

        Page<CalibrationExt> pageExt = new Page<>(Integer.MAX_VALUE);
        Page<CalibrationIn> pageIn = new Page<>(Integer.MAX_VALUE);
        for (Equipment eq : equipments) {
            if (eq.getCalibrationMode() == null)
                continue;
            filters.clear();
            filters.add(new PropertyFilter("GED_expectDate", DateFormatUtils.format(eq.getExpectCalibrationDate(), "yyyy-MM-dd") + " 00:00:00"));
            filters.add(new PropertyFilter("LED_expectDate", DateFormatUtils.format(eq.getExpectCalibrationDate(), "yyyy-MM-dd") + " 23:59:59"));

            if (eq.getCalibrationMode().intValue() == 1) {
                saveIn(eq, pageIn, filters);
            } else if (eq.getCalibrationMode().intValue() == 2) {
                saveExt(eq, pageExt, filters);
            }
        }
    }

    private CalibrationExt saveExt(Equipment eq, Page<CalibrationExt> page, List<PropertyFilter> filters) {
        page = calibrationExtService.findPage(page, filters);
        if (page.getResult() != null && page.getResult().size() > 0)
            return null;
        CalibrationExt entity = new CalibrationExt();
        entity.setTenantId(eq.getTenantId());
        entity.setCreaterId(eq.getCreaterId());
        entity.setCreateTime(new Date());
        entity.setStatus(1);
        entity.setEquipmentId(eq.getId());
        entity.setExpectDate(eq.getExpectCalibrationDate());
        calibrationExtService.save(entity);
        return entity;
    }

    private CalibrationIn saveIn(Equipment eq, Page<CalibrationIn> page, List<PropertyFilter> filters) {
        page = calibrationInService.findPage(page, filters);
        if (page.getResult() != null && page.getResult().size() > 0)
            return null;
        CalibrationIn entity = new CalibrationIn();
        entity.setTenantId(eq.getTenantId());
        entity.setCreaterId(eq.getCreaterId());
        entity.setCreateTime(new Date());
        entity.setStatus(1);
        entity.setEquipmentId(eq.getId());
        entity.setExpectDate(eq.getExpectCalibrationDate());
        calibrationInService.save(entity);
        return entity;
    }
}
