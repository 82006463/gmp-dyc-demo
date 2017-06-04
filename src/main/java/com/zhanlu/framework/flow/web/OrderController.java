package com.zhanlu.framework.flow.web;

import com.zhanlu.framework.flow.service.SnakerFacade;
import com.zhanlu.framework.security.shiro.ShiroUtils;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.entity.Process;
import org.snaker.engine.model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuqs
 * @since 2.0
 */
@Controller
@RequestMapping(value = "/flow/order")
public class OrderController {

    @Autowired
    private SnakerFacade facets;

    /**
     * 流程实例查询
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String order(Model model, Page<HistoryOrder> page) {
        facets.getEngine().query().getHistoryOrders(page, new QueryFilter());
        model.addAttribute("page", page);
        return "flow/orderList";
    }

    /**
     * 抄送实例已读
     */
    @RequestMapping(value = "ccread")
    public String ccread(String id, String url) {
        List<String> list = ShiroUtils.getGroups();
        list.add(ShiroUtils.getUsername());
        String[] assignees = new String[list.size()];
        list.toArray(assignees);
        facets.getEngine().order().updateCCStatus(id, assignees);
        return "redirect:" + url;
    }

    /**
     * 节点信息以json格式返回
     * all页面以节点信息构造tab及加载iframe
     */
    @RequestMapping(value = "node")
    @ResponseBody
    public Object node(String processId) {
        Process process = facets.getEngine().process().getProcessById(processId);
        List<TaskModel> models = process.getModel().getModels(TaskModel.class);
        List<TaskModel> viewModels = new ArrayList<TaskModel>();
        for (TaskModel model : models) {
            TaskModel viewModel = new TaskModel();
            viewModel.setName(model.getName());
            viewModel.setDisplayName(model.getDisplayName());
            viewModel.setForm(model.getForm());
            viewModels.add(viewModel);
        }
        return viewModels;
    }

}
