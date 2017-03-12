package com.snakerflow.report.service;

import com.snakerflow.common.page.Page;
import com.snakerflow.common.page.PropertyFilter;
import com.snakerflow.report.dao.DycReportDao;
import com.snakerflow.report.entity.DycReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
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

    /**
     * 保存权限实体
     *
     * @param entity
     */
    @Transactional
    public void save(DycReport entity) {
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
     * @param id
     */
    @Transactional
    public void delete(Long id) {
        reportDao.delete(id);
    }
}
