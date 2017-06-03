package com.zhanlu.app.modules.web;

import com.zhanlu.framework.common.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhanlu.app.modules.entity.Borrow;
import com.zhanlu.app.modules.service.BorrowManager;

@Controller
@RequestMapping(value = "/flow/borrow")
public class BorrowController {
	@Autowired
	private BorrowManager manager;
	
	@RequestMapping(value = "apply", method= RequestMethod.GET)
	public String apply(Model model, String processId, String orderId, String taskId) {
		model.addAttribute("processId", processId);
		model.addAttribute("orderId", orderId);
		model.addAttribute("taskId", taskId);
		if(StringUtils.isNotEmpty(orderId)) {
			model.addAttribute("borrow", manager.findByOrderId(orderId));
		}
		if(StringUtils.isEmpty(orderId) || StringUtils.isNotEmpty(taskId)) {
			model.addAttribute("operateTime", DateUtils.getCurrentDay());
			return "flow/borrow/apply";
		} else {
			return "flow/borrow/applyView";
		}
	}
	
	@RequestMapping(value = "applySave", method= RequestMethod.POST)
	public String applySave(String processId, String orderId, String taskId, Borrow model) {
        manager.save(processId, orderId, taskId, model);
		/** 业务数据处理结束 */
		return "redirect:/flow/task/list";
	}
}
