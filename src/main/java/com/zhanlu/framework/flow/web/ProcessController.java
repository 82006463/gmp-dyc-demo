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
package com.zhanlu.framework.flow.web;

import com.zhanlu.framework.common.utils.SnakerHelper;
import com.zhanlu.framework.flow.service.SnakerEngineFacets;
import com.zhanlu.framework.security.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.entity.HistoryTask;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.Task;
import org.snaker.engine.helper.AssertHelper;
import org.snaker.engine.helper.StreamHelper;
import org.snaker.engine.helper.StringHelper;
import org.snaker.engine.helper.XmlHelper;
import org.snaker.engine.model.ProcessModel;
import org.snaker.engine.parser.NodeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 流程定义
 *
 * @author yuqs
 * @since 0.1
 */
@Controller
@RequestMapping(value = "/flow/process")
public class ProcessController {

    private static Log log = LogFactory.getLog(ProcessController.class);

    @Autowired
    private SnakerEngineFacets facets;

    /**
     * 流程定义查询列表
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView processList(Page<Process> page, String name, String displayName) {
        QueryFilter filter = new QueryFilter();
        if (StringHelper.isNotEmpty(displayName))
            filter.setDisplayName(displayName);
        if (StringHelper.isNotEmpty(name))
            filter.setNames(new String[]{name});

        ModelAndView view = new ModelAndView("flow/processList");
        facets.getEngine().process().getProcesss(page, filter);
        view.addObject("page", page);
        return view;
    }

    /**
     * 初始化流程定义
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public ModelAndView processInit() {
        facets.initFlows();
        return new ModelAndView("redirect:/flow/process/list");
    }

    /**
     * 新建流程定义
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ModelAndView processAdd() {
        return new ModelAndView("flow/processAdd");
    }

    /**
     * 新建流程定义[web流程设计器]
     */
    @RequestMapping(value = "conf", method = RequestMethod.GET)
    public ModelAndView processConf(String processId, String type) {
        ModelAndView mv = new ModelAndView("flow/processConf");
        if (StringUtils.isNotEmpty(processId)) {
            Process process = facets.getEngine().process().getProcessById(processId);
            AssertHelper.notNull(process);
            ProcessModel processModel = process.getModel();
            if (processModel != null) {
                Map<String, Object> extraMap = new HashMap<>();
                extraMap.put("type", process.getType());
                String json = SnakerHelper.getModelJson(processModel, extraMap);
                mv.addObject("process", json);
            }
            mv.addObject("processId", processId);
            mv.addObject("type", type);
        }
        return mv;
    }

    /**
     * 保存流程定义[web流程设计器]
     */
    @ResponseBody
    @RequestMapping(value = "deployXml", method = RequestMethod.POST)
    public boolean processDeploy(String id, String type, String model) {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" + SnakerHelper.convertXml(model);
        try (InputStream inputStream = StreamHelper.getStreamFromString(xml);
             InputStream inputStream2 = StreamHelper.getStreamFromString(xml);
             ByteArrayInputStream bais = new ByteArrayInputStream(StreamHelper.readBytes(inputStream2));) {
            // System.out.println("model xml=\n" + xml);
            if (type.equals("new") || type.equals("copy")) { //新建|复制
                id = facets.getEngine().process().deploy(inputStream);
            } else if (type.equals("upgrade")) { //升级
                facets.getEngine().process().undeploy(id); //卸载老版本
                id = facets.getEngine().process().deploy(inputStream);
            } else if (StringUtils.isNotEmpty(id)) { //修改
                facets.getEngine().process().redeploy(id, inputStream);
            }
            Process p = facets.getEngine().process().getProcessById(id);
            DocumentBuilder docBuilder = XmlHelper.createDocumentBuilder();

            Document doc = docBuilder.parse(bais);
            Element root = doc.getDocumentElement();
            p.setType(root.getAttribute(NodeParser.ATTR_TYPE));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 编辑流程定义
     */
    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public ModelAndView processEdit(@PathVariable("id") String id) {
        ModelAndView view = new ModelAndView("flow/processEdit");
        Process process = facets.getEngine().process().getProcessById(id);
        view.addObject("process", process);
        if (process.getDBContent() != null) {
            try {
                view.addObject("content", StringHelper.textXML(new String(process.getDBContent(), "UTF-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    /**
     * 根据流程定义ID，删除流程定义
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public ModelAndView processDelete(@PathVariable("id") String id) {
        //facets.getEngine().process().undeploy(id);
        facets.getEngine().process().cascadeRemove(id);
        return new ModelAndView("redirect:/flow/process/list");
    }

    @RequestMapping(value = "start", method = RequestMethod.GET)
    public String processStart(Model model, String processName) {
        facets.startInstanceByName(processName, null, ShiroUtils.getUsername(), null);
        return "redirect:/flow/process/list";
    }

    @RequestMapping(value = "json", method = RequestMethod.GET)
    @ResponseBody
    public Object json(String processId, String orderId) {
        Process process = facets.getEngine().process().getProcessById(processId);
        AssertHelper.notNull(process);
        ProcessModel processModel = process.getModel();
        Map<String, String> jsonMap = new HashMap<String, String>();
        if (processModel != null) {
            Map<String, Object> extraMap = new HashMap<>();
            extraMap.put("type", process.getType());
            jsonMap.put("process", SnakerHelper.getModelJson(processModel, extraMap));
        }

        if (StringUtils.isNotEmpty(orderId)) {
            List<Task> tasks = facets.getEngine().query().getActiveTasks(new QueryFilter().setOrderId(orderId));
            List<HistoryTask> historyTasks = facets.getEngine().query().getHistoryTasks(new QueryFilter().setOrderId(orderId));
            jsonMap.put("state", SnakerHelper.getStateJson(processModel, tasks, historyTasks));
        }
        log.info(jsonMap.get("state"));
        return jsonMap;
    }

    @RequestMapping(value = "display", method = RequestMethod.GET)
    public String display(Model model, String processId, String orderId) {
        model.addAttribute("processId", processId);
        HistoryOrder order = facets.getEngine().query().getHistOrder(orderId);
        model.addAttribute("order", order);
        List<HistoryTask> tasks = facets.getEngine().query().getHistoryTasks(new QueryFilter().setOrderId(orderId));
        model.addAttribute("tasks", tasks);
        return "flow/processView";
    }

    /**
     * 显示独立的流程图
     */
    @RequestMapping(value = "diagram", method = RequestMethod.GET)
    public String diagram(Model model, String processId, String orderId) {
        model.addAttribute("processId", processId);
        model.addAttribute("orderId", orderId);
        return "flow/diagram";
    }
}
