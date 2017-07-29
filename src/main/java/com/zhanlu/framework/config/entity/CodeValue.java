package com.zhanlu.framework.config.entity;

import com.zhanlu.framework.common.entity.CodeEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 由编号规则生成的值
 */
@Entity
@Table(name = "wfc_code_value")
public class CodeValue extends CodeEntity {

    //规则ID、编号
    private Long ruleId;
    private String ruleCode;

    //部门编号
    private String orgValue;
    //功能编号
    private String funcValue;
    //时间模式下的当前时间值
    private Integer timeValue;
    //当前流水号
    private Integer serialValue;
    //当前值的编码规则：年+月+日+部门编号+流水号
    private String currentValue;


}
