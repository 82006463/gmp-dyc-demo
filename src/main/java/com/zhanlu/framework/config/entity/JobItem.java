package com.zhanlu.framework.config.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2017/10/28.
 */
@Getter
@Setter
public class JobItem {

    private String beanName;
    private String methodName;
    private String cron;

}
