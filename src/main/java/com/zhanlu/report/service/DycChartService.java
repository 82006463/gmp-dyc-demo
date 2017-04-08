package com.zhanlu.report.service;

import com.zhanlu.framework.common.service.CommonService;
import com.zhanlu.framework.config.entity.DataDict;
import com.zhanlu.framework.config.service.DataDictService;
import com.zhanlu.report.dao.DycChartDao;
import com.zhanlu.report.entity.DycChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 图表Service
 *
 * @author zhanlu
 * @date 2017-03-11
 * @since 0.1
 */
@Service
public class DycChartService extends CommonService<DycChart, Long> {

    @Autowired
    private DycChartDao chartDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = chartDao;
    }

}
