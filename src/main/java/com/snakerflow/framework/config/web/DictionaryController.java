package com.snakerflow.framework.config.web;

import com.snakerflow.common.page.Page;
import com.snakerflow.common.page.PropertyFilter;
import com.snakerflow.framework.config.entity.DataDict;
import com.snakerflow.framework.config.service.DataDictService;
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
public class DictionaryController {

    /*@Autowired
    private DictionaryService dictionaryService;*/
    @Autowired
    private DataDictService dataDictService;

    /**
     * 分页查询配置，返回配置字典列表视图
     *
     * @param model
     * @param page
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, Page<DataDict> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        PropertyFilter filter = new PropertyFilter("EQL_pid", "0");
        filters.add(filter);
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
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("entity", new DataDict());
        return "config/dataDictEdit";
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
        DataDict entity = dataDictService.get(id);
        entity.setItems(dataDictService.findItems(entity.getId()));
        model.addAttribute("entity", entity);
        return "config/dataDictEdit";
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
        DataDict entity = dataDictService.get(id);
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
    public String update(DataDict dataDict, Long[] itemIds, String[] itemCodes, String[] itemNames) {
        List<DataDict> items = new ArrayList<>();
        for (int i = 0; i < itemNames.length; i++) {
            DataDict item = new DataDict();
            item.setId(itemIds[i]);
            item.setCode(itemCodes[i]);
            item.setName(itemNames[i]);
            items.add(item);
        }
        dataDictService.save(dataDict, items);
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
        dataDictService.delete(id);
        return "redirect:/config/dictionary";
    }

}
