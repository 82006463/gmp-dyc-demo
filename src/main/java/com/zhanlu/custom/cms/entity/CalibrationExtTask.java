package com.zhanlu.custom.cms.entity;

import com.zhanlu.framework.common.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 月度外校任务
 */
@Entity
@Table(name = "cms_calibration_ext_task")
public class CalibrationExtTask extends IdEntity {

    //器具
    private Long equipmentId;
    private Equipment equipment;
    //实际校准时间
    private Date actualCalibrationDate;
    //校准结果
    private String calibrationResult;
    //审核人
    private String approver;

}
