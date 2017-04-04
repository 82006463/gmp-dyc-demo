package com.zhanlu.framework.config.service;

import com.zhanlu.framework.common.service.CommonTreeService;
import com.zhanlu.framework.config.dao.DataDictDao;
import com.zhanlu.framework.config.entity.DataDict;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置字典管理类
 *
 * @author yuqs
 * @since 0.1
 */
@Service
public class DataDictService extends CommonTreeService<DataDict, Long> {

    @Autowired
    private DataDictDao dataDictDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = dataDictDao;
    }

    @Transactional
    @Override
    public DataDict saveOrUpdate(DataDict entity, List<DataDict> items) {
        this.saveOrUpdate(entity);
        Map<Long, DataDict> oldMap = new LinkedHashMap<>();
        List<DataDict> oldList = super.findItems(entity.getId());
        if (oldList != null && oldList.size() > 0) {
            for (DataDict item : oldList)
                oldMap.put(item.getId(), item);
        }
        if (items != null && items.size() > 0) {
            for (DataDict item : items) {
                item.setPid(entity.getId());
                item.setPcode(entity.getCode());
                if (oldMap.containsKey(item.getId())) {
                    DataDict old = oldMap.get(item.getId());
                    BeanUtils.copyProperties(item, old);
                    oldMap.remove(item.getId());
                }
                if (item.getId() == null || item.getId() <= 0)
                    this.save(item);
            }
        }
        for (Map.Entry<Long, DataDict> item : oldMap.entrySet())
            commonDao.delete(item.getValue());
        return entity;
    }

    public DataDict findByCode(String code) {
        Map<String, Object> params = new LinkedHashMap<>(4);
        params.put("code", code);
        List<DataDict> list = super.findList(params);
        if (list == null || list.isEmpty()) {
            return new DataDict();
        }
        return list.get(0);
    }

}
