package com.zhanlu.framework.flow.service;

import org.apache.commons.lang.StringUtils;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.Surrogate;
import org.snaker.engine.entity.Task;
import org.snaker.engine.helper.StreamHelper;
import org.snaker.engine.model.TaskModel.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SnakerFacade {

    @Autowired
    private SnakerEngine snakerEngine;
    @Autowired
    private SnakerHelper snakerHelper;

    public void initFlows() {
        snakerEngine.process().deploy(StreamHelper.getStreamFromClasspath("flows/leave.snaker"));
        snakerEngine.process().deploy(StreamHelper.getStreamFromClasspath("flows/borrow.snaker"));
    }

    public SnakerEngine getEngine() {
        return snakerEngine;
    }

    public List<String> getAllProcessNames() {
        List<String> processNames = new ArrayList<>();
        List<Process> processes = snakerEngine.process().getProcesss(new QueryFilter());
        for (Process process : processes) {
            if (processNames.contains(process.getName()))
                continue;
            processNames.add(process.getName());
        }
        return processNames;
    }

    @Transactional
    public Order startAndExecute(String processId, String operator, Map<String, Object> args) {
        snakerHelper.preStart(operator, processId, args);
        Order order = snakerEngine.startInstanceById(processId, operator, args);
        args.put("wf_processId", processId);
        args.put("wf_orderId", order.getId());
        List<Task> tasks = snakerEngine.query().getActiveTasks(new QueryFilter().setOrderId(order.getId()));
        List<Task> newTasks = new ArrayList<>();
        if (tasks != null && tasks.size() > 0) {
            for (Task task : tasks) {
                newTasks.addAll(snakerEngine.executeTask(task.getId(), operator, args));
            }
        }
        snakerHelper.addActors(operator, newTasks);
        return order;
    }

    //单条执行流程
    @Transactional
    public List<Task> execute(String taskId, String operator, Map<String, Object> args) {
        Task task = snakerEngine.query().getTask(taskId);
        if (args == null) {
            args = task.getVariableMap();
        }
        if (!args.containsKey("wf_orderId")) {
            Order order = snakerEngine.query().getOrder(task.getOrderId());
            args.put("wf_orderId", order.getId());
            args.put("v_creator", order.getCreator());
        }
        List<Task> newTasks = snakerEngine.executeTask(taskId, operator, args);
        snakerHelper.addActors(operator, newTasks);
        return newTasks;
    }

    @Transactional
    public List<Task> executeAndJump(String taskId, String operator, Map<String, Object> args, String nodeName) {
        nodeName = StringUtils.isEmpty(nodeName) ? "申请人" : nodeName;
        return snakerEngine.executeAndJumpTask(taskId, operator, args, nodeName);
    }

    //转办
    @Transactional
    public List<Task> transferMajor(String taskId, String operator, String... actors) {
        List<Task> tasks = snakerEngine.task().createNewTask(taskId, TaskType.Major.ordinal(), actors);
        snakerEngine.task().complete(taskId, operator);
        return tasks;
    }

    //协办
    @Transactional
    public List<Task> transferAidant(String taskId, String operator, String... actors) {
        List<Task> tasks = snakerEngine.task().createNewTask(taskId, TaskType.Aidant.ordinal(), actors);
        snakerEngine.task().complete(taskId, operator);
        return tasks;
    }

    public void addSurrogate(Surrogate entity) {
        if (entity.getState() == null) {
            entity.setState(1);
        }
        snakerEngine.manager().saveOrUpdate(entity);
    }

    public void deleteSurrogate(String id) {
        snakerEngine.manager().deleteSurrogate(id);
    }

    public Surrogate getSurrogate(String id) {
        return snakerEngine.manager().getSurrogate(id);
    }

    public List<Surrogate> searchSurrogate(Page<Surrogate> page, QueryFilter filter) {
        return snakerEngine.manager().getSurrogate(page, filter);
    }
}
