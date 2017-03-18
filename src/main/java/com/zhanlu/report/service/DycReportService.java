package com.zhanlu.report.service;

import com.zhanlu.common.page.Page;
import com.zhanlu.common.page.PropertyFilter;
import com.zhanlu.framework.config.service.ProcessNoService;
import com.zhanlu.report.dao.DycReportDao;
import com.zhanlu.report.entity.DycReport;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 报表Service
 *
 * @author zhanlu
 * @date 2017-03-11
 * @since 0.1
 */
@Service
public class DycReportService {

    @Autowired
    private DycReportDao reportDao;
    @Autowired
    private ProcessNoService processNoService;

    /**
     * 保存权限实体
     *
     * @param entity
     */
    @Transactional
    public void save(DycReport entity) {
        if (entity.getId() == null && StringUtils.isBlank(entity.getProcessNo())) {
            entity.setProcessNo(processNoService.getNextVal(entity.getProcessType()));
        }
        reportDao.save(entity);
    }

    /**
     * 根据主键ID获取权限实体
     *
     * @param id
     * @return
     */
    public DycReport get(Long id) {
        return reportDao.get(id);
    }

    /**
     * 根据分页对象、过滤集合参数，分页查询权限列表
     *
     * @param page
     * @param filters
     * @return
     */
    public Page<DycReport> findPage(final Page<DycReport> page, final List<PropertyFilter> filters) {
        return reportDao.findPage(page, filters);
    }

    /**
     * 根据主键ID删除对应的实体
     *
     * @param id
     */
    @Transactional
    public void delete(Long id) {
        reportDao.delete(id);
    }
}
