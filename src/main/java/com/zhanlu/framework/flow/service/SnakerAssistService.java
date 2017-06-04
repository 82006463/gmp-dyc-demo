package com.zhanlu.framework.flow.service;

import com.zhanlu.framework.flow.strategy.ActorStrategyRouter;
import com.zhanlu.framework.security.entity.Org;
import com.zhanlu.framework.security.entity.User;
import com.zhanlu.framework.security.service.OrgService;
import com.zhanlu.framework.security.service.UserService;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Task;
import org.snaker.engine.model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SnakerFacade助手Service
 */
@Component
public class SnakerAssistService {

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
        args.put("creator", operator);
        args.put("v_creator", operator);
        args.put("v_username", initUser.getUsername());
        args.put("v_name", initUser.getFullname());
        args.put("v_deptCode", initOrg.getCode()); //本部门编号
        args.put("v_deptName", initOrg.getName()); //本部门名称
        args.put("v_deptLevel", initOrg.getLevel()); //本部门层级
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

            Set<String> resultSet = null;
            Map<String, Object> taskMap = newTask.getVariableMap();
            String assigneeStr = newModel.getAssignee();
            if (assigneeStr.equals("creator")) {
                if (taskMap.containsKey("creator")) {
                    resultSet.add(taskMap.get("creator").toString());
                } else {
                    Order order = snakerEngine.query().getOrder(newTask.getOrderId());
                    resultSet.add(order.getCreator());
                }
            } else {
                resultSet = actorStrategyRouter.findActors(operator, assigneeStr, taskMap);
            }
            for (String username : resultSet) {
                snakerEngine.task().addTaskActor(newTask.getId(), username);
            }
        }
        return newTasks;
    }


}
