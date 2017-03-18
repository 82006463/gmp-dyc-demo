package com.zhanlu.framework.security.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhanlu.common.page.Page;
import com.zhanlu.common.page.PropertyFilter;
import com.zhanlu.framework.security.entity.Authority;
import com.zhanlu.framework.security.entity.Role;
import com.zhanlu.framework.security.service.AuthorityService;
import com.zhanlu.framework.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 角色管理Controller
 * @author yuqs
 * @since 0.1
 */
@Controller
@RequestMapping(value = "/security/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private AuthorityService authorityService;
	
	/**
	 * 分页查询角色，返回角色列表视图
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, Page<Role> page, HttpServletRequest request) {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("id");
			page.setOrder(Page.ASC);
		}
		page = roleService.findPage(page, filters);
		model.addAttribute("page", page);
		return "security/roleList";
	}
	
	/**
	 * 新建角色时，返回角色编辑视图
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("role", new Role());
		model.addAttribute("authorities", authorityService.getAll());
		return "security/roleEdit";
	}

	/**
	 * 编辑角色时，返回角色编辑视图
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Long id, Model model) {
		Role entity  = roleService.get(id);
		List<Authority> authorities = authorityService.getAll();
		List<Authority> auths = entity.getAuthorities();
		for(Authority auth : authorities) {
			for(Authority selAuth : auths) {
				if(auth.getId().longValue() == selAuth.getId().longValue())
				{
					auth.setSelected(1);
				}
				if(auth.getSelected() == null)
				{
					auth.setSelected(0);
				}
			}
		}
		model.addAttribute("role", entity);
		model.addAttribute("authorities", authorities);
		return "security/roleEdit";
	}
	
	/**
	 * 编辑角色时，返回角色查看视图
	 */
	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") Long id, Model model) {
		model.addAttribute("role", roleService.get(id));
		return "security/roleView";
	}
	
	/**
	 * 新增、编辑角色页面的提交处理。保存角色实体，并返回角色列表视图
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(Role role, Long[] orderIndexs) {
		if(orderIndexs != null) {
			for(Long order : orderIndexs) {
				Authority auth = new Authority(order);
				role.getAuthorities().add(auth);
			}
		}
		roleService.save(role);
		return "redirect:/security/role";
	}
	
	/**
	 * 根据主键ID删除角色实体，并返回角色列表视图
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		roleService.delete(id);
		return "redirect:/security/role";
	}
}
