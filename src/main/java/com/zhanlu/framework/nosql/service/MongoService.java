package com.zhanlu.framework.nosql.service;

import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.nosql.util.QueryItem;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/18.
 */
public interface MongoService {

    /**
     * 保存或更新文档
     *
     * @param collectionName 集合名称
     * @param docMap         文档
     * @return 是否成功
     */
    Map<String, Object> saveOrUpdate(String collectionName, String id, Map<String, Object> docMap);

    /**
     * @param collectionName 集合名称
     * @param id             对象ID
     * @return 单个文档
     */
    Map<String, Object> findOne(String collectionName, String id);

    /**
     * @param collectionName 集合名称
     * @param queryItems     对象ID
     * @return 单个文档
     */
    Map<String, Object> findOne(String collectionName, List<QueryItem> queryItems);

    /**
     * @param collectionName 集合名称
     * @return 所有文档
     */
    List<Map<String, Object>> findAll(String collectionName);

    /**
     * @param collectionName 集合名称
     * @return 所有文档
     */
    List<Map<String, Object>> findByProp(String collectionName, List<QueryItem> queryItems);

    /**
     * @param collectionName 集合名称
     * @return 所有文档
     */
    List<Map<String, Object>> findByProp(String collectionName, Map<String, Object> paramMap);

    /**
     * @param collectionName 集合名称
     * @return 分页文档
     */
    List<Map<String, Object>> findByPage(String collectionName, List<QueryItem> queryItems, Page page);

    /**
     * @param collectionName 集合名称
     * @param id             对象ID
     * @return 是否成功
     */
    int removeOne(String collectionName, String id);

}
