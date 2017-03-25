package com.zhanlu.framework.config.service;

import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.common.service.CommonService;
import com.zhanlu.framework.config.dao.DataDictDao;
import com.zhanlu.framework.config.entity.DataDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置字典管理类
 *
 * @author yuqs
 * @since 0.1
 */
@Service
public class DataDictService extends CommonService<DataDict, Long> {

    @Autowired
    private DataDictDao dataDictDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = dataDictDao;
    }

    /**
     * 保存配置字典实体，更新时先删除配置字典选项列表，再添加选项列表
     *
     * @param entity
     */
    @Transactional
    public DataDict saveOrUpdate(DataDict entity, List<DataDict> items) {
        if (entity.getId() != null && entity.getId() > 0) {
            dataDictDao.batchExecute("DELETE FROM " + DataDict.class.getName() + " WHERE pid=" + entity.getId());
        }
        dataDictDao.saveOrUpdate(entity);
        for (DataDict item : items) {
            item.setPid(entity.getId());
            item.setPcode(entity.getCode());
            dataDictDao.save(item);
        }
        return entity;
    }

    public List<DataDict> findItems(final String pcode) {
        PropertyFilter filter = new PropertyFilter("EQS_pcode", pcode);
        List<PropertyFilter> filters = new ArrayList<>(1);
        filters.add(filter);
        return dataDictDao.find(filters);
    }
}
