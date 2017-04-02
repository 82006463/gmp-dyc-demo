package com.zhanlu.report.service;

import com.zhanlu.framework.common.service.CommonService;
import com.zhanlu.framework.config.service.ProcessNoService;
import com.zhanlu.report.dao.DycReportDao;
import com.zhanlu.report.entity.DycReport;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * 报表Service
 *
 * @author zhanlu
 * @date 2017-03-11
 * @since 0.1
 */
@Service
public class DycReportService extends CommonService<DycReport, Long> {

    @Autowired
    private DycReportDao reportDao;
    @Autowired
    private ProcessNoService processNoService;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = reportDao;
    }

    /**
     * 保存权限实体
     *
     * @param entity
     */
    @Transactional
    @Override
    public DycReport saveOrUpdate(DycReport entity) {
       /* if (entity.getId() == null && StringUtils.isBlank(entity.getProcessNo())) {
            entity.setProcessNo(processNoService.getNextVal(entity.getProcessType(), entity.getOrgId()));
        }*/
        return super.saveOrUpdate(entity);
    }
}
