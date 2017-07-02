/* Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    private SnakerEngine engine;
    @Autowired
    private SnakerAssistService assistService;

    public void initFlows() {
        engine.process().deploy(StreamHelper.getStreamFromClasspath("flows/leave.snaker"));
        engine.process().deploy(StreamHelper.getStreamFromClasspath("flows/borrow.snaker"));
    }

    public SnakerEngine getEngine() {
        return engine;
    }

    public List<String> getAllProcessNames() {
        List<Process> list = engine.process().getProcesss(new QueryFilter());
        List<String> names = new ArrayList<>();
        for (Process entity : list) {
            if (names.contains(entity.getName()))
                continue;
            names.add(entity.getName());
        }
        return names;
    }

    @Transactional
    public Order startAndExecute(String processId, String operator, Map<String, Object> args) {
        assistService.preStart(operator, processId, args);
        Order order = engine.startInstanceById(processId, operator, args);
        args.put("wf_processId", processId);
        args.put("wf_orderId", order.getId());
        List<Task> tasks = engine.query().getActiveTasks(new QueryFilter().setOrderId(order.getId()));
        List<Task> newTasks = new ArrayList<>();
        if (tasks != null && tasks.size() > 0) {
            for (Task task : tasks) {
                newTasks.addAll(engine.executeTask(task.getId(), operator, args));
            }
        }
        assistService.addActors(operator, newTasks);
        return order;
    }

    public Order startAndExecute(String name, Integer version, String operator, Map<String, Object> args) {
        Process process = engine.process().getProcessByVersion(name, version);
        return this.startAndExecute(process.getId(), operator, args);
    }

    //批量执行流程
    @Transactional
    public List<Task> execute(String[] taskIds, String operator) {
        for (String taskId : taskIds) {
            execute(taskId, operator, null);
        }
        return null;
    }

    //单条执行流程
    @Transactional
    public List<Task> execute(String taskId, String operator, Map<String, Object> args) {
        Task task = engine.query().getTask(taskId);
        if (args == null) {
            args = task.getVariableMap();
        }
        if (!args.containsKey("wf_orderId")) {
            Order order = engine.query().getOrder(task.getOrderId());
            args.put("wf_orderId", order.getId());
            args.put("v_creator", order.getCreator());
        }
        List<Task> newTasks = engine.executeTask(taskId, operator, args);
        assistService.addActors(operator, newTasks);
        return newTasks;
    }

    @Transactional
    public List<Task> executeAndJump(String taskId, String operator, Map<String, Object> args, String nodeName) {
        if (StringUtils.isEmpty(nodeName)) {
            nodeName = "申请人";
        }
        List<Task> newTasks = engine.executeAndJumpTask(taskId, operator, args, nodeName);
        return newTasks;
    }

    //转办
    @Transactional
    public List<Task> transferMajor(String taskId, String operator, String... actors) {
        List<Task> tasks = engine.task().createNewTask(taskId, TaskType.Major.ordinal(), actors);
        engine.task().complete(taskId, operator);
        return tasks;
    }

    //协办
    @Transactional
    public List<Task> transferAidant(String taskId, String operator, String... actors) {
        List<Task> tasks = engine.task().createNewTask(taskId, TaskType.Aidant.ordinal(), actors);
        engine.task().complete(taskId, operator);
        return tasks;
    }

    public void addSurrogate(Surrogate entity) {
        if (entity.getState() == null) {
            entity.setState(1);
        }
        engine.manager().saveOrUpdate(entity);
    }

    public void deleteSurrogate(String id) {
        engine.manager().deleteSurrogate(id);
    }

    public Surrogate getSurrogate(String id) {
        return engine.manager().getSurrogate(id);
    }

    public List<Surrogate> searchSurrogate(Page<Surrogate> page, QueryFilter filter) {
        return engine.manager().getSurrogate(page, filter);
    }
}
