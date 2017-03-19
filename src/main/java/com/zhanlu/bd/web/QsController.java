package com.zhanlu.bd.web;

import com.zhanlu.bd.entity.Qs;
import com.zhanlu.bd.service.QsService;
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
 * 配置字典管理Controller
 *
 * @author yuqs
 * @since 0.1
 */
@Controller
@RequestMapping(value = "/bd/qs")
public class QsController {

    @Autowired
    private QsService qsService;

    /**
     * 分页查询配置，返回配置字典列表视图
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model, Page<Qs> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = qsService.findPage(page, filters);
        model.addAttribute("page", page);
        return "bd/qsList";
    }

    /**
     * 新建配置字典时，返回配置字典编辑视图
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("entity", new Qs());
        return "bd/qsEdit";
    }

    /**
     * 编辑配置字典时，返回配置字典编辑视图
     */
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {
        Qs entity = qsService.findById(id);
        model.addAttribute("entity", entity);
        return "bd/qsEdit";
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
        Qs entity = qsService.findById(id);
        model.addAttribute("entity", entity);
        return "bd/qsView";
    }

    /**
     * 新增、编辑配置字典页面的提交处理。保存配置字典实体，并返回配置字典列表视图
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(Qs entity) {
        qsService.saveOrUpdate(entity);
        return "redirect:/bd/qs/list";
    }

    /**
     * 根据主键ID删除配置字典实体，并返回配置字典列表视图
     */
    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        qsService.delete(id);
        return "redirect:/bd/qs/list";
    }

}
