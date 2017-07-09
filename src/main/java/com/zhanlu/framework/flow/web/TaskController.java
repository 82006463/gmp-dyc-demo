package com.zhanlu.framework.flow.web;

import com.zhanlu.framework.config.service.DataDictService;
import com.zhanlu.framework.flow.service.SnakerFacade;
import com.zhanlu.framework.logic.MongoLogic;
import com.zhanlu.framework.nosql.item.EditItem;
import com.zhanlu.framework.nosql.item.QueryItem;
import com.zhanlu.framework.security.shiro.ShiroUtils;
import com.zhanlu.framework.util.MetaTagUtils;
import com.zhanlu.meta.service.FlowService;
import org.apache.commons.io.FileUtils;
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
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

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

    @Autowired
    private ApplicationContext applicationContext;

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataDictService dataDictService;
    @Autowired
    private MongoLogic mongoLogic;
    private String metaTable = "config_meta";

    @Autowired
    private SnakerFacade facets;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String homeTaskList(Model model) {
        List<String> list = ShiroUtils.getGroups();
        list.add(ShiroUtils.getUsername());
        log.info(list.toString());
        String[] assignees = new String[list.size()];
        list.toArray(assignees);

        Page<WorkItem> majorPage = new Page<>(5);
        Page<WorkItem> aidantPage = new Page<>(3);
        Page<HistoryOrder> ccorderPage = new Page<>(3);
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
    public ModelAndView approvalGet(HttpServletRequest req, String processId, String processName, Integer processVersion) {
        String orderId = req.getParameter("orderId");
        String taskId = req.getParameter("taskId");
        Process process = null;
        if (processId != null && processId.trim().length() > 0) {
            process = facets.getEngine().process().getProcessById(processId);
        } else {
            process = facets.getEngine().process().getProcessByVersion(processName, processVersion);
        }
        Task task = StringUtils.isNotEmpty(taskId) ? facets.getEngine().query().getTask(taskId) : null;

        ModelAndView mav = new ModelAndView("flow/taskApproval");
        String formPage = task != null ? task.getActionUrl() : process.getInstanceUrl();
        Map<String, Object> entity = task != null ? task.getVariableMap() : new HashMap<String, Object>();
        mav.addObject("jsonEdit", MetaTagUtils.edit(jdbcTemplate, dataDictService, this.getMetaTag(formPage), entity));
        mav.addObject("orderId", orderId);
        mav.addObject("process", process);
        mav.addObject("task", task);
        mav.addObject("entity", entity);
        mav.addObject("buttons", facets.getButtons(process, task));
        mav.addObject("buttonsOfReject", facets.getButtonsOfReject(process, task));
        return mav;
    }

    /**
     * 测试任务的执行
     */
    @RequestMapping(value = "approval", method = RequestMethod.POST)
    public ModelAndView approvalPost(@RequestParam(value = "file", required = false) CommonsMultipartFile[] files, HttpServletRequest req) throws Exception {
        List<Map<String, String>> uploadFiles = new ArrayList<>();
        if (files != null && files.length > 0) {
            String path = req.getSession().getServletContext().getRealPath("/") + "/upload/";
            for (CommonsMultipartFile file : files) {
                File destFile = new File(path + file.getOriginalFilename());
                if (!destFile.exists()) {
                    destFile.mkdirs();
                }
                file.transferTo(destFile);
                Map<String, String> fileMap = new LinkedHashMap<>(4);
                fileMap.put("fileName", destFile.getName());
                fileMap.put("filePath", destFile.getPath());
                uploadFiles.add(fileMap);
            }
        }
        String processId = req.getParameter("processId");
        //String orderId = req.getParameter("orderId");
        String taskId = req.getParameter("taskId");

        Task task = StringUtils.isNotEmpty(taskId) ? facets.getEngine().query().getTask(taskId) : null;
        Process process = task == null ? facets.getEngine().process().getProcessById(processId) : null;
        Map<String, Object> taskMap = task != null ? task.getVariableMap() : new HashMap<String, Object>();
        //页面的处理
        String pagePath = task != null ? task.getActionUrl() : process.getInstanceUrl();
        String[] pagePathArr = pagePath.split("_");
        String metaType = pagePathArr[0];
        String cmcode = pagePathArr[1].replace(metaType + "_", "");
        Map<String, Object> metaTag = this.getMetaTag(pagePath);

        Map<String, String[]> paramMap = req.getParameterMap();
        Map<String, Object> entity = EditItem.toMap(dataDictService, (List<Map<String, String>>) metaTag.get("editItems"), paramMap);
        entity.put("metaType", metaType);
        entity.put("cmcode", cmcode);
        if (uploadFiles.size() > 0) {
            entity.put("uploadFiles", uploadFiles);
        }

        String submitBtn = paramMap.containsKey("submit") ? paramMap.get("submit")[0] : "";
        if (task != null) {
            if (submitBtn.equals("保存")) {
                taskMap.putAll(entity);
            } else if (submitBtn.contains("提交") || submitBtn.contains("审核")) {
                taskMap.putAll(entity);
                facets.execute(taskId, ShiroUtils.getUsername(), taskMap);
            } else if (submitBtn.contains("拒绝")) {
                facets.executeAndJump(taskId, ShiroUtils.getUsername(), taskMap, req.getParameter("nodeName"));
            }
        } else if (submitBtn.contains("提交") || submitBtn.contains("审核")) {
            facets.startAndExecute(processId, ShiroUtils.getUsername(), entity);
        }

        List<QueryItem> queryItems = new ArrayList<>(2);
        queryItems.add(new QueryItem("Eq_String_type", metaType));
        queryItems.add(new QueryItem("Eq_String_code", metaType + "_" + cmcode));
        FlowService flowService = applicationContext.getBean(pagePathArr[0], FlowService.class);
        Map<String, Object> tableStruct = mongoLogic.findOne("config_meta", queryItems);
        flowService.saveOrUpdate(tableStruct, req.getParameter("id"), entity);

        ModelAndView mav = new ModelAndView("redirect:/flow/task/list");
        return mav;
    }

    /**
     * 历史完成任务查询列表
     */
    @RequestMapping(value = "history", method = RequestMethod.GET)
    public String historyList(Model model, Page<WorkItem> page) {
        facets.getEngine().query().getHistoryWorkItems(page, new QueryFilter().setOperator(ShiroUtils.getUsername()));
        model.addAttribute("page", page);
        return "flow/taskHistory";
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

    private Map<String, Object> getMetaTag(String code) {
        List<QueryItem> queryItems = new ArrayList<>(2);
        queryItems.add(new QueryItem("Eq_String_code", code));
        return mongoLogic.findOne(metaTable, queryItems);
    }
}
