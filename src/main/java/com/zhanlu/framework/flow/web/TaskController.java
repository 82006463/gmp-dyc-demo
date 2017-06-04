package com.zhanlu.framework.flow.web;

import com.zhanlu.framework.config.service.DataDictService;
import com.zhanlu.framework.flow.service.SnakerEngineFacets;
import com.zhanlu.framework.logic.MongoLogic;
import com.zhanlu.framework.nosql.item.QueryItem;
import com.zhanlu.framework.security.shiro.ShiroUtils;
import com.zhanlu.framework.util.MetaTagUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.Task;
import org.snaker.engine.entity.WorkItem;
import org.snaker.engine.model.TaskModel.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Snaker流程引擎常用Controller
 *
 * @author yuqs
 * @since 0.1
 */
@Controller
@RequestMapping(value = "/flow/task")
public class TaskController {
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataDictService dataDictService;
    @Autowired
    private MongoLogic mongoLogic;
    private String metaTable = "config_meta";

    @Autowired
    private SnakerEngineFacets facets;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String homeTaskList(Model model) {
        List<String> list = ShiroUtils.getGroups();
        list.add(ShiroUtils.getUsername());
        log.info(list.toString());
        String[] assignees = new String[list.size()];
        list.toArray(assignees);

        Page<WorkItem> majorPage = new Page<WorkItem>(5);
        Page<WorkItem> aidantPage = new Page<WorkItem>(3);
        Page<HistoryOrder> ccorderPage = new Page<HistoryOrder>(3);
        List<WorkItem> majorWorks = facets.getEngine().query().getWorkItems(majorPage, new QueryFilter().setOperators(assignees).setTaskType(TaskType.Major.ordinal()));
        List<WorkItem> aidantWorks = facets.getEngine().query().getWorkItems(aidantPage, new QueryFilter().setOperators(assignees).setTaskType(TaskType.Aidant.ordinal()));
        List<HistoryOrder> ccWorks = facets.getEngine().query().getCCWorks(ccorderPage, new QueryFilter().setOperators(assignees).setState(1));

        model.addAttribute("majorWorks", majorWorks);
        model.addAttribute("majorTotal", majorPage.getTotalCount());
        model.addAttribute("aidantWorks", aidantWorks);
        model.addAttribute("aidantTotal", aidantPage.getTotalCount());
        model.addAttribute("ccorderWorks", ccWorks);
        model.addAttribute("ccorderTotal", ccorderPage.getTotalCount());
        return "flow/taskList";
    }

    /**
     * 根据当前用户查询待办任务列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public String userTaskList(Model model, Page<WorkItem> page) {
        facets.getEngine().query().getWorkItems(page, new QueryFilter().setOperator(ShiroUtils.getUsername()));
        model.addAttribute("page", page);
        return "flow/userTask";
    }

    @RequestMapping(value = "actor/add", method = RequestMethod.GET)
    public String addTaskActor(Model model, String orderId, String taskName) {
        model.addAttribute("orderId", orderId);
        model.addAttribute("taskName", taskName);
        return "flow/actor";
    }

    @RequestMapping(value = "actor/add", method = RequestMethod.POST)
    @ResponseBody
    public String addTaskActor(Model model, String orderId, String taskName, String operator) {
        List<Task> tasks = facets.getEngine().query().getActiveTasks(new QueryFilter().setOrderId(orderId));
        for (Task task : tasks) {
            if (task.getTaskName().equalsIgnoreCase(taskName) && StringUtils.isNotEmpty(operator)) {
                facets.getEngine().task().addTaskActor(task.getId(), operator);
            }
        }
        return "success";
    }

    @RequestMapping(value = "tip", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> addTaskActor(String orderId, String taskName) {
        List<Task> tasks = facets.getEngine().query().getActiveTasks(new QueryFilter().setOrderId(orderId));
        StringBuilder builder = new StringBuilder();
        String createTime = "";
        for (Task task : tasks) {
            if (task.getTaskName().equalsIgnoreCase(taskName)) {
                String[] actors = facets.getEngine().query().getTaskActorsByTaskId(task.getId());
                for (String actor : actors) {
                    builder.append(actor).append(",");
                }
                createTime = task.getCreateTime();
            }
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        Map<String, String> data = new HashMap<String, String>();
        data.put("actors", builder.toString());
        data.put("createTime", createTime);
        return data;
    }

    /**
     * 活动任务查询列表
     */
    @RequestMapping(value = "more", method = RequestMethod.GET)
    public String taskMore(Model model, Page<WorkItem> page, Integer taskType) {
        List<String> list = ShiroUtils.getGroups();
        list.add(ShiroUtils.getUsername());
        log.info(list.toString());
        String[] assignees = new String[list.size()];
        list.toArray(assignees);
        facets.getEngine().query().getWorkItems(page, new QueryFilter().setOperators(assignees).setTaskType(taskType));
        model.addAttribute("page", page);
        model.addAttribute("taskType", taskType);
        return "flow/taskMore";
    }

    /**
     * 活动任务查询列表
     */
    @RequestMapping(value = "ccmore", method = RequestMethod.GET)
    public String activeCCList(Model model, Page<HistoryOrder> page) {
        List<String> list = ShiroUtils.getGroups();
        list.add(ShiroUtils.getUsername());
        log.info(list.toString());
        String[] assignees = new String[list.size()];
        list.toArray(assignees);
        facets.getEngine().query().getCCWorks(page, new QueryFilter().setOperators(assignees).setState(1));
        model.addAttribute("page", page);
        return "flow/taskCCMore";
    }

    /**
     * 流程审批页面
     */
    @RequestMapping(value = "approval", method = RequestMethod.GET)
    public ModelAndView approval(HttpServletRequest req, String id) {
        String processId = req.getParameter("processId");
        String orderId = req.getParameter("orderId");
        String taskId = req.getParameter("taskId");
        Process process = facets.getEngine().process().getProcessById(processId);
        Task task = StringUtils.isNotEmpty(taskId) ? facets.getEngine().query().getTask(taskId) : null;

        ModelAndView mv = new ModelAndView("flow/taskApproval");
        String formPage = task != null ? task.getActionUrl() : process.getInstanceUrl();
        Map<String, Object> entity = task != null ? task.getVariableMap() : new HashMap<String, Object>();
        if (formPage.contains("/")) {
            mv.addObject("includeFile", formPage);
        } else {
            List<QueryItem> queryItems = new ArrayList<>(2);
            queryItems.add(new QueryItem("Eq_String_code", formPage));
            Map<String, Object> metaTag = mongoLogic.findOne(metaTable, queryItems);
            mv.addObject("jsonEdit", MetaTagUtils.edit(jdbcTemplate, dataDictService, metaTag, entity));
        }
        mv.addObject("process", process);
        mv.addObject("orderId", orderId);
        mv.addObject("task", task);
        mv.addObject("entity", entity);
        return mv;
    }

    /**
     * 测试任务的执行
     */
    @RequestMapping(value = "exec", method = RequestMethod.GET)
    public String activeTaskExec(Model model, String taskId) {
        facets.execute(taskId, ShiroUtils.getUsername(), null);
        return "redirect:/flow/task/list";
    }

    /**
     * 活动任务的驳回
     */
    @RequestMapping(value = "reject", method = RequestMethod.GET)
    public String activeTaskReject(Model model, String taskId) {
        String error = "";
        try {
            facets.executeAndJump(taskId, ShiroUtils.getUsername(), null, null);
        } catch (Exception e) {
            error = "?error=1";
        }
        return "redirect:/flow/task/list" + error;
    }

    /**
     * 历史完成任务查询列表
     */
    @RequestMapping(value = "history", method = RequestMethod.GET)
    public String historyList(Model model, Page<WorkItem> page) {
        facets.getEngine().query().getHistoryWorkItems(page, new QueryFilter().setOperator(ShiroUtils.getUsername()));
        model.addAttribute("page", page);
        return "flow/historyList";
    }

    /**
     * 历史任务撤回
     */
    @RequestMapping(value = "undo", method = RequestMethod.GET)
    public String historyTaskUndo(Model model, String taskId) {
        String returnMessage = "";
        try {
            facets.getEngine().task().withdrawTask(taskId, ShiroUtils.getUsername());
            returnMessage = "任务撤回成功.";
        } catch (Exception e) {
            returnMessage = e.getMessage();
        }
        model.addAttribute("returnMessage", returnMessage);
        return "redirect:/flow/task/history";
    }
}
