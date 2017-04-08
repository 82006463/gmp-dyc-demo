package com.zhanlu.framework.config.web;

import com.alibaba.fastjson.JSON;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.config.entity.ElasticTable;
import com.zhanlu.framework.config.service.ElastictTableService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置字典管理Controller
 *
 * @author yuqs
 * @since 0.1
 */
@Controller
@RequestMapping(value = "/wfc/etab")
public class ElasticTableController {

    @Autowired
    private ElastictTableService elastictTableService;

    /**
     * 分页查询配置，返回配置字典列表视图
     *
     * @param model
     * @param page
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model, Page<ElasticTable> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = elastictTableService.findPage(page, filters);
        model.addAttribute("page", page);
        return "config/etabList";
    }

    /**
     * 新建配置字典时，返回配置字典编辑视图
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Model model) {
        ElasticTable entity = new ElasticTable();
        model.addAttribute("entity", entity);
        return "config/etabEdit";
    }

    /**
     * 编辑配置字典时，返回配置字典编辑视图
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {
        ElasticTable entity = elastictTableService.findById(id);
        model.addAttribute("extAttrMap", StringUtils.isBlank(entity.getJsonEdit()) ? null : JSON.parseObject(entity.getJsonEdit(), List.class));
        model.addAttribute("entity", entity);
        return "config/etabEdit";
    }

    /**
     * 编辑配置字典时，返回配置字典查看视图
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id, Model model) {
        ElasticTable entity = elastictTableService.findById(id);
        model.addAttribute("entity", entity);
        return "config/etabView";
    }

    /**
     * 新增、编辑配置字典页面的提交处理。保存配置字典实体，并返回配置字典列表视图
     *
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(ElasticTable entity, String[] itemCodes, String[] itemNames, String[] itemRequireds, String[] itemDataTypes,
                         String[] itemTagTypes, String[] itemListAttrs, String[] itemFuzzys, String[] itemSubForms) {
        List<Map<String, Object>> extAttr = new ArrayList<>(itemCodes == null ? 0 : itemCodes.length);
        if (itemCodes != null) {
            for (int i = 0; i < itemCodes.length; i++) {
                Map<String, Object> itemMap = new LinkedHashMap<>(8);
                itemMap.put("code", itemCodes[i]);
                itemMap.put("name", itemNames[i]);
                itemMap.put("required", itemRequireds[i]);
                itemMap.put("dataType", itemDataTypes[i]);
                itemMap.put("tagType", itemTagTypes[i]);
                itemMap.put("listAttr", itemListAttrs[i]);
                itemMap.put("fuzzy", itemFuzzys[i]);
                itemMap.put("subForm", itemSubForms[i]);
                extAttr.add(itemMap);
            }
            entity.setJsonEdit(JSON.toJSONString(extAttr));
        }
        elastictTableService.saveOrUpdate(entity);
        return "redirect:/wfc/etab/list";
    }

    /**
     * 根据主键ID删除配置字典实体，并返回配置字典列表视图
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        elastictTableService.delete(id);
        return "redirect:/wfc/etab/list";
    }

}
