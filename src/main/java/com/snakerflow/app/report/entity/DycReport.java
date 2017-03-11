package com.snakerflow.app.report.entity;

import com.snakerflow.common.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/11.
 */
@Entity
@Table(name = "dyc_report")
public class DycReport extends IdEntity {

    private String processType; //流程类型
    private String processNo; //流程编号
    private String processName; //流程名称

    private Long deptId; //部门
    private String level; //级别
    private Date occurTime;//发现时间
    private Date startTime; //流程开始时间
    private Date endTime; //流程关闭时间

    //备用的字段
    private String backup1;
    private String backup2;
    private String extraJson; //流程额外的非键数据

}
