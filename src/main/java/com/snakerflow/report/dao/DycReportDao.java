package com.snakerflow.report.dao;

import com.snakerflow.report.entity.DycReport;
import com.snakerflow.common.dao.HibernateDao;
import org.springframework.stereotype.Repository;

/**
 * 报表Dao
 *
 * @author zhanlu
 * @date 2017-03-11
 * @since 0.1
 */
@Repository
public class DycReportDao extends HibernateDao<DycReport, Long> {
}
