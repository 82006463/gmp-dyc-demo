package com.zhanlu.bd.web;

import com.zhanlu.bd.entity.Trt;
import com.zhanlu.bd.service.TrtService;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 检验报告模板的Controller
 */
@Controller
@RequestMapping(value = "/bd/trt")
public class TrtController {

    @Autowired
    private TrtService trtService;

    /**
     * 分页查询配置，返回配置字典列表视图
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model, Page<Trt> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = trtService.findPage(page, filters);
        model.addAttribute("page", page);
        return "bd/trtList";
    }

    /**
     * 新建配置字典时，返回配置字典编辑视图
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("entity", new Trt());
        return "bd/trtEdit";
    }

    /**
     * 编辑配置字典时，返回配置字典编辑视图
     */
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {
        Trt entity = trtService.findById(id);
        model.addAttribute("entity", entity);
        return "bd/trtEdit";
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
        Trt entity = trtService.findById(id);
        model.addAttribute("entity", entity);
        return "bd/trtView";
    }

    /**
     * 新增、编辑配置字典页面的提交处理。保存配置字典实体，并返回配置字典列表视图
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(Trt entity) {
        if (entity.getName() == null) {
            entity.setName(entity.getCode());
        }
        trtService.saveOrUpdate(entity);
        return "redirect:/bd/trt/list";
    }

    /**
     * 根据主键ID删除配置字典实体，并返回配置字典列表视图
     */
    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        trtService.delete(id);
        return "redirect:/bd/trt/list";
    }

}