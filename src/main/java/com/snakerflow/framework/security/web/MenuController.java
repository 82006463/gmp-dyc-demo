package com.snakerflow.framework.security.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.snakerflow.framework.page.Page;
import com.snakerflow.framework.page.PropertyFilter;
import com.snakerflow.framework.security.entity.Menu;
import com.snakerflow.framework.security.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 菜单管理Controller
 * @author yuqs
 * @since 0.1
 */
@Controller
@RequestMapping(value = "/security/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;
	
	/**
	 * 分页查询菜单，返回菜单列表视图
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, Page<Menu> page, HttpServletRequest request) {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("id");
			page.setOrder(Page.ASC);
		}
		page = menuService.findPage(page, filters);
		model.addAttribute("page", page);
		model.addAttribute("lookup", request.getParameter("lookup"));
		return "security/menuList";
	}
	
	/**
	 * 新建菜单时，返回菜单编辑视图
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("menu", new Menu(null));
		return "security/menuEdit";
	}

	/**
	 * 编辑菜单时，返回菜单编辑视图
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Long id, Model model) {
		model.addAttribute("menu", menuService.get(id));
		return "security/menuEdit";
	}
	
	/**
	 * 编辑菜单时，返回菜单查看视图
	 */
	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") Long id, Model model) {
		model.addAttribute("menu", menuService.get(id));
		return "security/menuView";
	}
	
	/**
	 * 新增、编辑菜单页面的提交处理。保存菜单实体，并返回菜单列表视图
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(Menu menu, Long parentMenuId) {
		if(parentMenuId != null && parentMenuId.longValue() > 0) {
			Menu parent = new Menu(parentMenuId);
			menu.setParentMenu(parent);
		}
		menuService.save(menu);
		return "redirect:/security/menu";
	}
	
	/**
	 * 根据主键ID删除菜单实体，并返回菜单列表视图
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		menuService.delete(id);
		return "redirect:/security/menu";
	}
}
