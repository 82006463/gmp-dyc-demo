package com.zhanlu.framework.config.web;

import com.alibaba.fastjson.JSON;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.config.entity.ElasticTable;
import com.zhanlu.framework.config.service.ElastictTableService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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
        model.addAttribute("type", "edit");
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
    public String edit(@PathVariable("id") Long id, String type, Model model) throws Exception {
        model.addAttribute("type", type);
        ElasticTable entity = elastictTableService.findById(id);
        if (type.equals("search")) {
            model.addAttribute("jsonSearchMap", StringUtils.isBlank(entity.getJsonSearch()) ? null : JSON.parseObject(entity.getJsonSearch(), List.class));
        } else if (type.equals("list")) {
            model.addAttribute("jsonListMap", StringUtils.isBlank(entity.getJsonList()) ? null : JSON.parseObject(entity.getJsonList(), List.class));
        } else {
            model.addAttribute("jsonEditMap", StringUtils.isBlank(entity.getJsonEdit()) ? null : JSON.parseObject(entity.getJsonEdit(), List.class));
        }
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
        model.addAttribute("jsonEditMap", StringUtils.isBlank(entity.getJsonEdit()) ? null : JSON.parseObject(entity.getJsonEdit(), List.class));
        model.addAttribute("jsonSearchMap", StringUtils.isBlank(entity.getJsonSearch()) ? null : JSON.parseObject(entity.getJsonSearch(), List.class));
        model.addAttribute("jsonListMap", StringUtils.isBlank(entity.getJsonList()) ? null : JSON.parseObject(entity.getJsonList(), List.class));
        model.addAttribute("entity", entity);
        return "config/etabView";
    }

    /**
     * 新增、编辑配置字典页面的提交处理。保存配置字典实体，并返回配置字典列表视图
     *
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(ElasticTable entity, HttpServletRequest req, String type, String[] itemCodes, String[] itemNames, String[] itemDescs, String[] itemDataTypes) {
        ElasticTable tmp = entity.getId() == null ? new ElasticTable() : elastictTableService.findById(entity.getId());
        tmp.setCode(entity.getCode());
        tmp.setName(entity.getName());
        tmp.setRemark(entity.getRemark());
        if (itemCodes != null && itemCodes.length > 0) {
            List<Map<String, Object>> items = new ArrayList<>(itemCodes == null ? 0 : itemCodes.length);
            Map<String, String[]> paramMap = req.getParameterMap();
            if (type.equals("search")) {
                String[] itemCompares = paramMap.get("itemCompares");
                String[] itemTagTypes = paramMap.get("itemTagTypes");
                for (int i = 0; i < itemCodes.length; i++) {
                    Map<String, Object> itemMap = new LinkedHashMap<>(8);
                    itemMap.put("code", itemCodes[i]);
                    itemMap.put("name", itemNames[i]);
                    itemMap.put("desc", itemDescs[i]);
                    itemMap.put("dataType", itemDataTypes[i]);
                    itemMap.put("compare", itemCompares[i]);
                    itemMap.put("tagType", itemTagTypes[i]);
                    items.add(itemMap);
                }
                tmp.setJsonSearch(JSON.toJSONString(items));
            } else if (type.equals("list")) {
                for (int i = 0; i < itemCodes.length; i++) {
                    Map<String, Object> itemMap = new LinkedHashMap<>(8);
                    itemMap.put("code", itemCodes[i]);
                    itemMap.put("name", itemNames[i]);
                    itemMap.put("desc", itemDescs[i]);
                    itemMap.put("dataType", itemDataTypes[i]);
                    items.add(itemMap);
                }
                tmp.setJsonList(JSON.toJSONString(items));
            } else if (type.equals("edit")) {
                String[] itemRequireds = paramMap.get("itemRequireds");
                String[] itemSubForms = paramMap.get("itemSubForms");
                String[] itemFuzzys = paramMap.get("itemFuzzys");
                String[] itemTagTypes = paramMap.get("itemTagTypes");
                for (int i = 0; i < itemCodes.length; i++) {
                    Map<String, Object> itemMap = new LinkedHashMap<>(8);
                    itemMap.put("code", itemCodes[i]);
                    itemMap.put("name", itemNames[i]);
                    itemMap.put("desc", itemDescs[i]);
                    itemMap.put("required", itemRequireds[i]);
                    itemMap.put("dataType", itemDataTypes[i]);
                    itemMap.put("tagType", itemTagTypes[i]);
                    itemMap.put("fuzzy", itemFuzzys[i]);
                    itemMap.put("subForm", itemSubForms[i]);
                    items.add(itemMap);
                }
                tmp.setJsonEdit(JSON.toJSONString(items));
            }
        }
        elastictTableService.saveOrUpdate(tmp);
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
