package com.zhanlu.framework.config.entity;

import com.zhanlu.framework.common.entity.CodeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 编号规则
 */
@Entity
@Table(name = "wfc_code_rule")
public class CodeRule extends CodeEntity {

    //部门编号
    private String orgCode;
    //部门编号分隔符
    private String orgSeparator;
    //功能编号
    private String funcCode;
    //功能编号分隔符
    private String funcSeparator;
    //时间模式：yyyy | yyyyMM | yyyyMMdd
    private String timePattern;
    //时间模式分隔符
    private String timeSeparator;
    //流水号长度
    private Integer serialLength;
    //规则值
    private String ruleValue;

}
