package com.zhanlu.framework.config.web;

import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.config.entity.DataDict;
import com.zhanlu.framework.config.service.DataDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置字典管理Controller
 *
 * @author yuqs
 * @since 0.1
 */
@Controller
@RequestMapping(value = "/config/dictionary")
public class DataDictController {

    /*@Autowired
    private DictionaryService dictionaryService;*/
    @Autowired
    private DataDictService dataDictService;

    /**
     * 分页查询配置，返回配置字典列表视图
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, Page<DataDict> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQL_pid", "0"));
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = dataDictService.findPage(page, filters);
        model.addAttribute("page", page);
        return "config/dataDictList";
    }

    /**
     * 新建配置字典时，返回配置字典编辑视图
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("entity", new DataDict());
        return "config/dataDictEdit";
    }

    /**
     * 编辑配置字典时，返回配置字典编辑视图
     */
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {
        DataDict entity = dataDictService.findById(id);
        entity.setItems(dataDictService.findItems(entity.getId()));
        model.addAttribute("entity", entity);
        return "config/dataDictEdit";
    }

    /**
     * 编辑配置字典时，返回配置字典查看视图
     */
    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id, Model model) {
        DataDict entity = dataDictService.findById(id);
        entity.setItems(dataDictService.findItems(entity.getId()));
        model.addAttribute("entity", entity);
        return "config/dataDictView";
    }

    /**
     * 新增、编辑配置字典页面的提交处理。保存配置字典实体，并返回配置字典列表视图
     *
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(DataDict dataDict, Long[] itemIds, String[] itemCodes, String[] itemNames, Integer[] itemStatuses, String[] itemDataSources) {
        List<DataDict> items = new ArrayList<>();
        if (itemCodes != null && itemCodes.length > 0) {
            for (int i = 0; i < itemCodes.length; i++) {
                DataDict item = new DataDict();
                item.setId(itemIds.length == 0 ? null : itemIds[i]);
                item.setCode(itemCodes[i]);
                item.setName(itemNames[i]);
                item.setStatus(itemStatuses == null || itemStatuses[i] == null ? 1 : itemStatuses[i]);
                item.setDataSource(itemDataSources.length == 0 ? null : itemDataSources[i]);
                items.add(item);
            }
        }
        dataDictService.saveOrUpdate(dataDict, items);
        return "redirect:/config/dictionary";
    }

    /**
     * 根据主键ID删除配置字典实体，并返回配置字典列表视图
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        List<DataDict> items = dataDictService.findItems(id);
        if (items == null || items.isEmpty()) {
            dataDictService.delete(id);
        }
        return "redirect:/config/dictionary";
    }

}
