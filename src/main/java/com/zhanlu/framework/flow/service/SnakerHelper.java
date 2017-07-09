package com.zhanlu.framework.flow.service;

import com.zhanlu.framework.flow.strategy.ActorStrategyRouter;
import com.zhanlu.framework.security.entity.Org;
import com.zhanlu.framework.security.entity.User;
import com.zhanlu.framework.security.service.OrgService;
import com.zhanlu.framework.security.service.UserService;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.Task;
import org.snaker.engine.model.ProcessModel;
import org.snaker.engine.model.TaskModel;
import org.snaker.engine.model.TransitionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * SnakerFacade助手Service
 */
@Component
public class SnakerHelper {

    @Autowired
    private SnakerEngine snakerEngine;
    @Autowired
    private ActorStrategyRouter actorStrategyRouter;

    @Autowired
    private OrgService orgService;
    @Autowired
    private UserService userService;

    /**
     * 流程启动前的数据准备
     */
    public Map<String, Object> preStart(String operator, String processId, Map<String, Object> args) {
        User initUser = userService.findUserByName(operator); // 申请用户
        Org initOrg = orgService.findById(initUser.getOrg().getId()); // 申请部门
        int deptLevel = initOrg.getLevel().intValue();
        args.put("creator", operator);
        args.put("v_creator", operator);
        args.put("v_username", initUser.getUsername());
        args.put("v_name", initUser.getFullname());
        args.put("v_deptCode", initOrg.getCode()); //本部门编号
        args.put("v_deptName", initOrg.getName()); //本部门名称
        args.put("v_deptLevel", deptLevel); //本部门层级
        for (int i = deptLevel; i > 0; i--) {
            args.put("v_deptCode" + i, initOrg.getCode()); //本部门编号
            args.put("v_deptName" + i, initOrg.getName()); //本部门名称
            if (initOrg.getPid() == null || initOrg.getPid().longValue() <= 0)
                break;
            initOrg = orgService.findById(initOrg.getPid());
        }
        return args;
    }

    /**
     * 添加流程审批人员
     *
     * @param operator
     * @param newTasks
     */
    public List<Task> addActors(String operator, List<Task> newTasks) {
        for (Task newTask : newTasks) {
            TaskModel newModel = newTask.getModel();
            if (newModel.getOutputs().isEmpty()) {
                continue;
            }

            Set<String> resultSet = new HashSet<>();
            Map<String, Object> taskMap = newTask.getVariableMap();
            String assigneeStr = newModel.getAssignee();
            if (assigneeStr.equals("creator")) {
                String creator = taskMap.containsKey("creator") ? taskMap.get("creator").toString() : snakerEngine.query().getOrder(newTask.getOrderId()).getCreator();
                resultSet.add(creator);
            } else {
                resultSet = actorStrategyRouter.findActors(operator, assigneeStr, taskMap);
            }
            for (String username : resultSet) {
                snakerEngine.task().addTaskActor(newTask.getId(), username);
            }
        }
        return newTasks;
    }

    public Map<String, String> getButtons(Task task) {
        Map<String, String> buttons = new LinkedHashMap<>(task == null ? 4 : 8);
        if (task == null) {
            //buttons.put("submit", "保存");
            buttons.put("submit", "提交");
        } else {
            List<TransitionModel> outputs = task.getModel() == null ? snakerEngine.task().getTaskModel(task.getId()).getOutputs() : task.getModel().getOutputs();
            for (TransitionModel output : outputs)
                buttons.put(output.getName(), output.getDisplayName());
        }
        return buttons;
    }

    public Map<String, String> getButtonsOfReject(Process process, Task task) {
        Map<String, String> buttons = new LinkedHashMap<>(task == null ? 4 : 8);
        if (task == null)
            return buttons;
        ProcessModel processModel = process.getModel();
        List<TaskModel> taskModels = processModel.getTaskModels();
        for (TaskModel taskModel : taskModels) {
            if (taskModel.getName().equals(task.getTaskName()))
                break;
            buttons.put(taskModel.getName(), taskModel.getDisplayName());
        }
        return buttons;
    }
}
