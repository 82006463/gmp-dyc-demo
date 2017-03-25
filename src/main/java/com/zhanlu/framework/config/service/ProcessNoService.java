package com.zhanlu.framework.config.service;

import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.common.service.CommonService;
import com.zhanlu.framework.config.dao.ProcessNoDao;
import com.zhanlu.framework.config.entity.ProcessNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 配置字典管理类
 *
 * @author yuqs
 * @since 0.1
 */
@Service
public class ProcessNoService extends CommonService<ProcessNo, Long> {

    @Autowired
    private ProcessNoDao processNoDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = processNoDao;
    }

    /**
     * 获取下一个编号
     *
     * @param code
     * @return
     */
    public String getNextVal(String code, Long orgId) {
        List<PropertyFilter> filters = new ArrayList<>();
        filters.add(new PropertyFilter("EQS_code", code));
        filters.add(new PropertyFilter("EQL_orgId", orgId.toString()));
        List<ProcessNo> processNos = processNoDao.find(filters);
        if (processNos == null || processNos.size() == 0) {
            throw new RuntimeException("ProcessNo对象为空");
        }

        ProcessNo entity = processNos.get(0);
        DateFormat format = new SimpleDateFormat(entity.getTimePattern());
        String timeVal = format.format(new Date());
        if (!StringUtils.hasLength(entity.getCurrentValue())) {
            entity.setTimeValue(Integer.parseInt(timeVal));
            entity.setIndexValue(1);
        } else if (!entity.getTimeValue().equals(Integer.parseInt(timeVal))) {
            entity.setTimeValue(Integer.parseInt(timeVal));
            entity.setIndexValue(1);
        } else {
            entity.setIndexValue(entity.getIndexValue() + 1);
        }
        String nextVal = entity.getPrefix() == null ? "" : entity.getPrefix();
        nextVal += entity.getTimeValue();
        if (entity.getOrgState() != null && entity.getOrgState().equals(1)) {
            nextVal += entity.getOrgCode();
        }
        int len = entity.getIndexLength() - entity.getIndexValue().toString().length();
        for (int i = 0; i < len; i++) {
            nextVal += "0";
        }
        nextVal += entity.getIndexValue();
        entity.setCurrentValue(nextVal);
        return entity.getCurrentValue();
    }

}
