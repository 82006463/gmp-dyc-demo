package com.zhanlu.custom.cms.quartz;

import com.zhanlu.custom.cms.entity.CalibrationExt;
import com.zhanlu.custom.cms.entity.CalibrationIn;
import com.zhanlu.custom.cms.entity.CalibrationYear;
import com.zhanlu.custom.cms.entity.Equipment;
import com.zhanlu.custom.cms.service.CalibrationExtService;
import com.zhanlu.custom.cms.service.CalibrationInService;
import com.zhanlu.custom.cms.service.CalibrationYearService;
import com.zhanlu.custom.cms.service.EquipmentService;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
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
    @Autowired
    private CalibrationYearService calibrationYearService;

    @Transactional
    public void generateMonth() {
        Page<Equipment> pageEq = new Page<>(Integer.MAX_VALUE);
        List<PropertyFilter> filters = new ArrayList<>(4);
        String year = DateFormatUtils.format(new Date(), "yyyy");
        filters.add(new PropertyFilter("GED_expectDate", year + "-01-01 00:00:00"));
        filters.add(new PropertyFilter("LED_expectDate", year + "-12-31 23:59:59"));
        pageEq = equipmentService.findPage(pageEq, filters);
        List<Equipment> equipments = pageEq.getResult();
        if (equipments == null || equipments.isEmpty()) {
            return;
        }

        Calendar cal = Calendar.getInstance();
        Page<CalibrationExt> pageExt = new Page<>(Integer.MAX_VALUE);
        Page<CalibrationIn> pageIn = new Page<>(Integer.MAX_VALUE);
        for (Equipment eq : equipments) {
            if (eq.getCalibrationMode() == null)
                continue;
            eq.setTmpStatus(2);
            cal.setTime(eq.getExpectDate());
            filters.clear();
            filters.add(new PropertyFilter("EQL_equipmentId", eq.getId().toString()));
            filters.add(new PropertyFilter("GED_expectDate", DateFormatUtils.format(cal.getTime(), "yyyy-MM") + "-01 00:00:00"));
            filters.add(new PropertyFilter("LED_expectDate", DateFormatUtils.format(cal.getTime(), "yyyy-MM") + "-" + cal.getActualMaximum(Calendar.DATE) + " 23:59:59"));
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
        entity.setLastExpectDate(eq.getLastExpectDate());
        entity.setLastActualDate(eq.getLastActualDate());
        entity.setExpectDate(eq.getExpectDate());
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
        entity.setLastExpectDate(eq.getLastExpectDate());
        entity.setLastActualDate(eq.getLastActualDate());
        entity.setExpectDate(eq.getExpectDate());
        calibrationInService.save(entity);
        return entity;
    }

    @Transactional
    public void generateYear() {
        Page<Equipment> pageEq = new Page<>(Integer.MAX_VALUE);
        List<PropertyFilter> filters = new ArrayList<>(4);
        Calendar cal = Calendar.getInstance();
        String year = DateFormatUtils.format(cal.getTime(), "yyyy");
        String nextYear = (Integer.parseInt(year) + 1) + "";
        filters.add(new PropertyFilter("GED_expectDate", year + "-01-01 00:00:00"));
        filters.add(new PropertyFilter("LED_expectDate", year + "-12-31 23:59:59"));
        pageEq = equipmentService.findPage(pageEq, filters);
        List<Equipment> equipments = pageEq.getResult();
        if (equipments == null || equipments.isEmpty()) {
            return;
        }

        Page<CalibrationYear> pageYear = new Page<>(Integer.MAX_VALUE);
        for (Equipment eq : equipments) {
            if (eq.getCalibrationMode() == null)
                continue;
            cal.setTime(eq.getExpectDate());
            cal.add(Calendar.YEAR, 1);
            for (int i = 1; i <= 12; i++) {
                cal.add(Calendar.MONTH, -eq.getCalibrationCycle());
                if (year.equals(DateFormatUtils.format(cal.getTime(), "yyyy"))) {
                    cal.add(Calendar.MONTH, eq.getCalibrationCycle());
                    break;
                }
            }
            for (int i = 1; i <= 12; i++) {
                if (!nextYear.equals(DateFormatUtils.format(cal.getTime(), "yyyy"))) {
                    break;
                }
                filters.clear();
                filters.add(new PropertyFilter("EQL_equipmentId", eq.getId().toString()));
                filters.add(new PropertyFilter("GED_expectDate", DateFormatUtils.format(cal.getTime(), "yyyy-MM") + "-01 00:00:00"));
                filters.add(new PropertyFilter("LED_expectDate", DateFormatUtils.format(cal.getTime(), "yyyy-MM") + "-" + cal.getActualMaximum(Calendar.DATE) + " 23:59:59"));
                saveYear(eq, cal.getTime(), pageYear, filters);
                cal.add(Calendar.MONTH, eq.getCalibrationCycle());
            }
        }
    }

    private CalibrationYear saveYear(Equipment eq, Date expectDate, Page<CalibrationYear> page, List<PropertyFilter> filters) {
        page = calibrationYearService.findPage(page, filters);
        if (page.getResult() != null && page.getResult().size() > 0)
            return null;
        CalibrationYear entity = new CalibrationYear();
        entity.setTenantId(eq.getTenantId());
        entity.setCreaterId(eq.getCreaterId());
        entity.setCreateTime(new Date());
        entity.setStatus(1);
        entity.setEquipmentId(eq.getId());
        entity.setExpectDate(expectDate);
        entity.setCalibrationMode(eq.getCalibrationMode());
        calibrationYearService.save(entity);
        return entity;
    }
}
