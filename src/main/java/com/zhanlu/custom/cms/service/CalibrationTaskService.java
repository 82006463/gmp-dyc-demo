package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.CalibrationTaskDao;
import com.zhanlu.custom.cms.entity.*;
import com.zhanlu.framework.common.service.CommonService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Calendar;
import java.util.Map;

/**
 * 月度外校任务
 */
@Service
public class CalibrationTaskService extends CommonService<CalibrationTask, Long> {

    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private CalibrationInService calibrationInService;
    @Autowired
    private CalibrationExtService calibrationExtService;
    @Autowired
    private CalibrationTmpService calibrationTmpService;
    @Autowired
    private CalibrationHistService calibrationHistService;

    @Value("${upload.file.path}")
    private String filePath;
    @Autowired
    private CalibrationTaskDao calibrationTaskDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = calibrationTaskDao;
    }

    @Transactional
    public CalibrationTask updateTask(CalibrationTask entity, CommonsMultipartFile[] files, Map<String, String[]> paramMap) throws Exception {
        String[] planIds = paramMap.get("planId");
        String[] certCodes = paramMap.get("certCode");
        String[] calibrationResults = paramMap.get("calibrationResult");
        String[] actualDates = paramMap.get("actualDate");
        String[] remark = paramMap.get("remark");
        for (int i = 0; i < planIds.length; i++) {
            String planId = planIds[i];
            String tmpPath = this.filePath + "calibrationTask/" + entity.getCalibrationMode();
            File dir = new File(tmpPath);
            if (!dir.exists())
                dir.mkdirs();
            if (files != null && !files[i].isEmpty()) {
                tmpPath = tmpPath + "/" + files[i].getOriginalFilename();
                FileUtils.writeByteArrayToFile(new File(tmpPath), files[i].getBytes());
            } else {
                tmpPath = null;
            }
            CalibrationHist hist = null;
            //复核完成进入校准历史
            if (entity.getStatus().intValue() == 4) {
                hist = new CalibrationHist();
                hist.setTenantId(entity.getTenantId());
                hist.setCreaterId(entity.getCreaterId());
                hist.setCreateTime(entity.getCreateTime());
                hist.setUpdaterId(entity.getUpdaterId());
                hist.setUpdateTime(entity.getUpdateTime());
                hist.setCalibrationMode(entity.getCalibrationMode());
                hist.setTaskCode(entity.getTaskCode());
            }
            if (entity.getCalibrationMode().intValue() == 1) {
                CalibrationIn plan = calibrationInService.findById(Long.parseLong(planId));
                plan.setCertCode(certCodes == null ? null : certCodes[i]);
                plan.setCalibrationResult(calibrationResults == null ? null : calibrationResults[i]);
                plan.setActualDate(actualDates == null ? null : DateUtils.parseDate(actualDates[i], new String[]{"yyyy-MM-dd"}));
                plan.setRemark(remark == null ? null : remark[i]);
                if (StringUtils.isNotBlank(tmpPath))
                    plan.setFilePath(tmpPath);
                if (calibrationResults != null && calibrationResults[i].equals("0")) {
                    plan.getEquipment().setTmpStatus(1);
                }

                Equipment eq = equipmentService.findById(plan.getEquipmentId());
                eq.setLastExpectDate(eq.getExpectDate());
                eq.setLastActualDate(plan.getActualDate());
                Calendar cal = Calendar.getInstance();
                cal.setTime(eq.getLastActualDate());
                cal.add(Calendar.MONTH, eq.getCalibrationCycle());
                cal.add(Calendar.DAY_OF_YEAR, -1);
                eq.setExpectDate(cal.getTime());

                if (hist != null) {
                    hist.setEquipmentCode(eq.getCode());
                    hist.setEquipmentId(plan.getEquipmentId());
                    hist.setLastExpectDate(plan.getLastExpectDate());
                    hist.setLastActualDate(plan.getLastActualDate());
                    hist.setExpectDate(plan.getExpectDate());
                    hist.setActualDate(plan.getActualDate());
                    hist.setCertCode(plan.getCertCode());
                    hist.setCalibrationResult(plan.getCalibrationResult());
                    hist.setCalibrationStatus(plan.getExpectDate() == null ? 1 : plan.getExpectDate().before(plan.getActualDate()) ? -1 : 1);
                    hist.setRemark(plan.getRemark());
                }
            } else if (entity.getCalibrationMode().intValue() == 2) {
                CalibrationExt plan = calibrationExtService.findById(Long.parseLong(planId));
                plan.setCertCode(certCodes == null ? null : certCodes[i]);
                plan.setCalibrationResult(calibrationResults == null ? null : calibrationResults[i]);
                plan.setActualDate(actualDates == null ? null : DateUtils.parseDate(actualDates[i], new String[]{"yyyy-MM-dd"}));
                plan.setRemark(remark == null ? null : remark[i]);
                if (StringUtils.isNotBlank(tmpPath))
                    plan.setFilePath(tmpPath);
                if (calibrationResults != null && calibrationResults[i].equals("0")) {
                    plan.getEquipment().setTmpStatus(1);
                }

                Equipment eq = equipmentService.findById(plan.getEquipmentId());
                eq.setLastExpectDate(eq.getExpectDate());
                eq.setLastActualDate(plan.getActualDate());
                Calendar cal = Calendar.getInstance();
                cal.setTime(eq.getLastActualDate());
                cal.add(Calendar.MONTH, eq.getCalibrationCycle());
                cal.add(Calendar.DAY_OF_YEAR, -1);
                eq.setExpectDate(cal.getTime());

                if (hist != null) {
                    hist.setEquipmentCode(eq.getCode());
                    hist.setEquipmentId(plan.getEquipmentId());
                    hist.setLastExpectDate(plan.getLastExpectDate());
                    hist.setLastActualDate(plan.getLastActualDate());
                    hist.setExpectDate(plan.getExpectDate());
                    hist.setActualDate(plan.getActualDate());
                    hist.setCertCode(plan.getCertCode());
                    hist.setCalibrationResult(plan.getCalibrationResult());
                    hist.setCalibrationStatus(plan.getExpectDate() == null ? 1 : plan.getExpectDate().before(plan.getActualDate()) ? -1 : 1);
                    hist.setRemark(plan.getRemark());
                }
            } else if (entity.getCalibrationMode().intValue() == 3) {
                CalibrationTmp plan = calibrationTmpService.findById(Long.parseLong(planId));
                plan.setCertCode(certCodes == null ? null : certCodes[i]);
                plan.setCalibrationResult(calibrationResults == null ? null : calibrationResults[i]);
                plan.setActualDate(actualDates == null ? null : DateUtils.parseDate(actualDates[i], new String[]{"yyyy-MM-dd"}));
                plan.setRemark(remark == null ? null : remark[i]);
                if (StringUtils.isNotBlank(tmpPath))
                    plan.setFilePath(tmpPath);
                if (calibrationResults != null && calibrationResults[i].equals("0")) {
                    plan.getEquipment().setTmpStatus(1);
                }

                Equipment eq = equipmentService.findById(plan.getEquipmentId());
                eq.setLastExpectDate(eq.getExpectDate());
                eq.setLastActualDate(plan.getActualDate());
                Calendar cal = Calendar.getInstance();
                cal.setTime(eq.getLastActualDate());
                cal.add(Calendar.MONTH, eq.getCalibrationCycle());
                cal.add(Calendar.DAY_OF_YEAR, -1);
                eq.setExpectDate(cal.getTime());

                if (hist != null) {
                    hist.setEquipmentCode(eq.getCode());
                    hist.setEquipmentId(plan.getEquipmentId());
                    hist.setLastExpectDate(plan.getLastExpectDate());
                    hist.setLastActualDate(plan.getLastActualDate());
                    hist.setExpectDate(plan.getExpectDate());
                    hist.setActualDate(plan.getActualDate());
                    hist.setCertCode(plan.getCertCode());
                    hist.setCalibrationResult(plan.getCalibrationResult());
                    hist.setCalibrationStatus(plan.getExpectDate() == null ? 1 : plan.getExpectDate().before(plan.getActualDate()) ? -1 : 1);
                    hist.setRemark(plan.getRemark());
                }
            }

            if (hist != null) {
                calibrationHistService.save(hist);
            }
        }
        return entity;
    }
}
