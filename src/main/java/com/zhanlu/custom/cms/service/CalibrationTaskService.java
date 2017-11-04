package com.zhanlu.custom.cms.service;

import com.zhanlu.custom.cms.dao.CalibrationTaskDao;
import com.zhanlu.custom.cms.entity.CalibrationExt;
import com.zhanlu.custom.cms.entity.CalibrationIn;
import com.zhanlu.custom.cms.entity.CalibrationTask;
import com.zhanlu.custom.cms.entity.CalibrationTmp;
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
import java.util.Map;

/**
 * 月度外校任务
 */
@Service
public class CalibrationTaskService extends CommonService<CalibrationTask, Long> {

    @Autowired
    private CalibrationInService calibrationInService;
    @Autowired
    private CalibrationExtService calibrationExtService;
    @Autowired
    private CalibrationTmpService calibrationTmpService;

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
        String[] calibrationResults = paramMap.get("calibrationResult");
        String[] actualDates = paramMap.get("actualDate");
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
            if (entity.getCalibrationMode().intValue() == 1) {
                CalibrationIn plan = calibrationInService.findById(Long.parseLong(planId));
                plan.setCalibrationResult(calibrationResults == null ? null : calibrationResults[i]);
                plan.setActualDate(actualDates == null ? null : DateUtils.parseDate(actualDates[i], new String[]{"yyyy-MM-dd"}));
                if (StringUtils.isNotBlank(tmpPath))
                    plan.setFilePath(tmpPath);
                if (calibrationResults != null && calibrationResults[i].equals("0")) {
                    plan.getEquipment().setTmpStatus(1);
                }
            } else if (entity.getCalibrationMode().intValue() == 2) {
                CalibrationExt plan = calibrationExtService.findById(Long.parseLong(planId));
                plan.setCalibrationResult(calibrationResults == null ? null : calibrationResults[i]);
                plan.setActualDate(actualDates == null ? null : DateUtils.parseDate(actualDates[i], new String[]{"yyyy-MM-dd"}));
                if (StringUtils.isNotBlank(tmpPath))
                    plan.setFilePath(tmpPath);
                if (calibrationResults != null && calibrationResults[i].equals("0")) {
                    plan.getEquipment().setTmpStatus(1);
                }
            } else if (entity.getCalibrationMode().intValue() == 3) {
                CalibrationTmp plan = calibrationTmpService.findById(Long.parseLong(planId));
                plan.setCalibrationResult(calibrationResults == null ? null : calibrationResults[i]);
                plan.setActualDate(actualDates == null ? null : DateUtils.parseDate(actualDates[i], new String[]{"yyyy-MM-dd"}));
                if (StringUtils.isNotBlank(tmpPath))
                    plan.setFilePath(tmpPath);
                if (calibrationResults != null && calibrationResults[i].equals("0")) {
                    plan.getEquipment().setTmpStatus(1);
                }
            }
        }
        return entity;
    }
}
