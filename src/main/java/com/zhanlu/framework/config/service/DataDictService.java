package com.zhanlu.framework.config.service;

import com.zhanlu.common.page.Page;
import com.zhanlu.common.page.PropertyFilter;
import com.zhanlu.framework.config.dao.DataDictDao;
import com.zhanlu.framework.config.entity.DataDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置字典管理类
 *
 * @author yuqs
 * @since 0.1
 */
@Service
public class DataDictService {

    @Autowired
    private DataDictDao dataDictDao;

    /**
     * 保存配置字典实体
     *
     * @param entity
     */
    public void save(DataDict entity) {
        dataDictDao.save(entity);
    }

    /**
     * 保存配置字典实体，更新时先删除配置字典选项列表，再添加选项列表
     *
     * @param entity
     */
    public void save(DataDict entity, List<DataDict> items) {
        if (entity.getId() != null && entity.getId() > 0) {
            dataDictDao.batchExecute("delete DataDict where pid=" + entity.getId());
        }
        dataDictDao.save(entity);
        for (DataDict item : items) {
            item.setPid(entity.getId());
            item.setPcode(entity.getCode());
            dataDictDao.getSession().save(item);
        }
    }

    /**
     * 根据主键ID获取配置字典实体
     *
     * @param id
     * @return
     */
    public DataDict get(Long id) {
        return dataDictDao.get(id);
    }

    /**
     * 获取所有的字典实体
     *
     * @return
     */
    public List<DataDict> getAll() {
        return dataDictDao.getAll();
    }

    /**
     * 根据主键ID删除对应的配置字典实体
     *
     * @param id
     */
    public void delete(Long id) {
        dataDictDao.delete(id);
    }

    /**
     * 根据分页对象、过滤集合参数，分页查询配置字典列表
     *
     * @param page
     * @param filters
     * @return
     */
    public Page<DataDict> findPage(final Page<DataDict> page, final List<PropertyFilter> filters) {
        return dataDictDao.findPage(page, filters);
    }

    public List<DataDict> findItems(final String pcode) {
        PropertyFilter filter = new PropertyFilter("EQS_pcode", pcode);
        List<PropertyFilter> filters = new ArrayList<>(1);
        filters.add(filter);
        return dataDictDao.find(filters);
    }
}
