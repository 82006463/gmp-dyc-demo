package com.zhanlu.framework.config.web;

import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.config.entity.CodeRule;
import com.zhanlu.framework.config.entity.ProcessNo;
import com.zhanlu.framework.config.service.CodeRuleService;
import com.zhanlu.framework.config.service.ProcessNoService;
import com.zhanlu.framework.security.entity.Org;
import com.zhanlu.framework.security.service.OrgService;
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
@RequestMapping(value = "/config/code/rule")
public class CodeRuleController {

    @Autowired
    private CodeRuleService codeRuleService;

    /**
     * 分页查询配置，返回配置字典列表视图
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model, Page<CodeRule> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = codeRuleService.findPage(page, filters);
        model.addAttribute("page", page);
        return "config/codeRuleList";
    }

    /**
     * 新建配置字典时，返回配置字典编辑视图
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("entity", new CodeRule());
        return "config/codeRuleEdit";
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
        CodeRule entity = codeRuleService.findById(id);
        model.addAttribute("entity", entity);
        return "config/codeRuleEdit";
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
        CodeRule entity = codeRuleService.findById(id);
        model.addAttribute("entity", entity);
        return "config/codeRuleView";
    }

    /**
     * 新增、编辑配置字典页面的提交处理。保存配置字典实体，并返回配置字典列表视图
     *
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(CodeRule entity) {
        codeRuleService.saveOrUpdate(entity);
        return "redirect:/config/codeRule/list";
    }

    /**
     * 根据主键ID删除配置字典实体，并返回配置字典列表视图
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        codeRuleService.delete(id);
        return "redirect:/config/codeRule/list";
    }

}
