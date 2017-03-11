package com.snakerflow.framework.security.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.snakerflow.common.page.Page;
import com.snakerflow.common.page.PropertyFilter;
import com.snakerflow.framework.security.entity.Org;
import com.snakerflow.framework.security.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 部门管理Controller
 * @author yuqs
 * @since 0.1
 */
@Controller
@RequestMapping(value = "/security/org")
public class OrgController {

	@Autowired
	private OrgService orgService;
	
	/**
	 * 分页查询部门，返回部门列表视图
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, Page<Org> page, HttpServletRequest request) {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("id");
			page.setOrder(Page.ASC);
		}
		page = orgService.findPage(page, filters);
		model.addAttribute("page", page);
		model.addAttribute("lookup", request.getParameter("lookup"));
		return "security/orgList";
	}
	
	/**
	 * 新建部门时，返回部门编辑视图
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("org", new Org(null));
		return "security/orgEdit";
	}

	/**
	 * 编辑部门时，返回部门编辑视图
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Long id, Model model) {
		model.addAttribute("org", orgService.get(id));
		return "security/orgEdit";
	}
	
	/**
	 * 编辑部门时，返回部门查看视图
	 */
	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") Long id, Model model) {
		model.addAttribute("org", orgService.get(id));
		return "security/orgView";
	}
	
	/**
	 * 新增、编辑部门页面的提交处理。保存部门实体，并返回部门列表视图
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(Org org, Long parentOrgId) {
		if(parentOrgId != null && parentOrgId.longValue() > 0) {
			Org parent = new Org(parentOrgId);
			org.setParentOrg(parent);
		}
		orgService.save(org);
		return "redirect:/security/org";
	}
	
	/**
	 * 根据主键ID删除部门实体，并返回部门列表视图
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		orgService.delete(id);
		return "redirect:/security/org";
	}
}
