package com.snakerflow.report.web;

import com.snakerflow.framework.orm.Page;
import com.snakerflow.framework.orm.PropertyFilter;
import com.snakerflow.framework.security.entity.Org;
import com.snakerflow.framework.security.entity.Role;
import com.snakerflow.framework.security.entity.User;
import com.snakerflow.framework.security.service.RoleService;
import com.snakerflow.framework.security.service.UserService;
import com.snakerflow.report.entity.DycReport;
import com.snakerflow.report.service.DycReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户管理Controller
 * @author yuqs
 * @since 0.1
 */
@Controller
@RequestMapping(value = "/dyc/report")
public class DycReportController {

	@Autowired
	private DycReportService reportService;
	
	/**
	 * 分页查询用户，返回用户列表视图
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(DycReport entity, Page<DycReport> page, HttpServletRequest request) {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		//设置默认排序方式
		if (!page.isOrderBySetted()) {
			page.setOrderBy("id");
			page.setOrder(Page.ASC);
		}
		page = reportService.findPage(page, filters);

		ModelAndView view = new ModelAndView("report/reportList");
		view.addObject("page", page);
		return view;
}

	/**
	 * 编辑用户时，返回用户查看视图
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") Long id, Model model) {
		model.addAttribute("report", reportService.get(id));
		return "report/reportView";
	}

}
