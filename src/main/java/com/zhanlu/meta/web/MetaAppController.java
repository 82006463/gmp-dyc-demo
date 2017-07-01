package com.zhanlu.meta.web;

import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.config.service.DataDictService;
import com.zhanlu.framework.logic.MongoLogic;
import com.zhanlu.framework.nosql.item.EditItem;
import com.zhanlu.framework.nosql.item.QueryItem;
import com.zhanlu.framework.util.MetaTagUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表Controller
 *
 * @author zhanlu
 * @date 2017-03-11
 * @since 0.1
 */
@Controller
@RequestMapping(value = "/meta/app")
public class MetaAppController {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataDictService dataDictService;

    @Autowired
    private MongoLogic mongoLogic;

    /**
     * 分页列表
     */
    @RequestMapping(value = "{metaType}/{cmcode}/list", method = RequestMethod.GET)
    public ModelAndView list(@PathVariable("metaType") String metaType, @PathVariable("cmcode") String cmcode, Page<Map<String, Object>> page, HttpServletRequest req) throws Exception {
        Map<String, String[]> paramMap = req.getParameterMap();
        cmcode = cmcode.startsWith(metaType + "_") ? cmcode : metaType + "_" + cmcode;
        Map<String, Object> metaTag = mongoLogic.findMetaByCode(cmcode);

        List<QueryItem> queryItems = QueryItem.buildSearchItems(paramMap);
        queryItems.add(new QueryItem("Eq_String_cmcode", cmcode));
        String tableName = metaType.equals("logBook") ? ("meta_" + metaType) : ("meta_" + metaType + "_" + cmcode);
        mongoLogic.findByPage(tableName, queryItems, page);

        ModelAndView view = new ModelAndView("meta/metaAppList");
        view.addObject("jsonSearch", MetaTagUtils.search(dataDictService, jdbcTemplate, metaTag, paramMap));
        view.addObject("jsonList", MetaTagUtils.list(req.getContextPath(), metaTag, page.getResult()));
        view.addObject("page", page);
        view.addObject("metaTag", metaTag);
        view.addObject("metaType", metaType);
        view.addObject("cmcode", cmcode);
        return view;
    }

    /**
     * 新建页面
     */
    @RequestMapping(value = "{metaType}/{cmcode}/create", method = RequestMethod.GET)
    public ModelAndView create(@PathVariable("metaType") String metaType, @PathVariable("cmcode") String cmcode) throws Exception {
        cmcode = cmcode.startsWith(metaType + "_") ? cmcode : metaType + "_" + cmcode;
        Map<String, Object> metaTag = mongoLogic.findMetaByCode(cmcode);
        ModelAndView view = new ModelAndView("meta/metaAppEdit");
        Map<String, Object> entity = new HashMap<>();
        view.addObject("jsonEdit", MetaTagUtils.edit(jdbcTemplate, dataDictService, metaTag, entity));
        entity.put("metaType", metaType);
        entity.put("cmcode", cmcode);
        view.addObject("entity", entity);
        view.addObject("metaTag", metaTag);
        return view;
    }

    /**
     * 编辑页面
     */
    @RequestMapping(value = "{metaType}/{cmcode}/update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("metaType") String metaType, @PathVariable("cmcode") String cmcode, @PathVariable("id") String id) throws Exception {
        cmcode = cmcode.startsWith(metaType + "_") ? cmcode : metaType + "_" + cmcode;
        Map<String, Object> metaTag = mongoLogic.findMetaByCode(cmcode);
        ModelAndView view = new ModelAndView("meta/metaAppEdit");
        String tableName = metaTag.get("oneTable").equals("1") ? ("meta_" + metaType + "_" + cmcode) : ("meta_" + metaType);
        Map<String, Object> entity = mongoLogic.findOne(tableName, id);
        view.addObject("jsonEdit", MetaTagUtils.edit(jdbcTemplate, dataDictService, metaTag, entity));
        view.addObject("entity", entity);
        view.addObject("metaTag", metaTag);
        return view;
    }

    /**
     * 新增、编辑的提交处理。保存实体，并返回列表视图
     */
    @RequestMapping(value = "{metaType}/{cmcode}/update", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable("metaType") String metaType, @PathVariable("cmcode") String cmcode, String id, HttpServletRequest req) throws Exception {
        cmcode = cmcode.startsWith(metaType + "_") ? cmcode : metaType + "_" + cmcode;
        Map<String, Object> metaTag = mongoLogic.findMetaByCode(cmcode);
        Map<String, Object> entity = EditItem.toMap(dataDictService, (List<Map<String, String>>) metaTag.get("editItems"), req.getParameterMap());
        entity.put("metaType", metaType);
        entity.put("cmcode", cmcode);
        String tableName = metaTag.get("oneTable").equals("1") ? ("meta_" + metaType + "_" + cmcode) : ("meta_" + metaType);
        mongoLogic.saveOrUpdate(tableName, id, entity);
        ModelAndView view = new ModelAndView("redirect:/meta/app/" + metaType + "/" + cmcode + "/list");
        return view;
    }

    /**
     * 查看页面
     */
    @RequestMapping(value = "{metaType}/{cmcode}/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("metaType") String metaType, @PathVariable("cmcode") String cmcode, @PathVariable("id") String id) throws Exception {
        cmcode = cmcode.startsWith(metaType + "_") ? cmcode : metaType + "_" + cmcode;
        Map<String, Object> metaTag = mongoLogic.findMetaByCode(cmcode);
        ModelAndView view = new ModelAndView("meta/metaAppView");
        String tableName = metaTag.get("oneTable").equals("1") ? ("meta_" + metaType + "_" + cmcode) : ("meta_" + metaType);
        Map<String, Object> entity = mongoLogic.findOne(tableName, id);
        view.addObject("entity", entity);
        view.addObject("jsonEdit", MetaTagUtils.view(dataDictService, metaTag, entity));
        view.addObject("metaTag", metaTag);
        return view;
    }

    /**
     * 根据主键ID删除实体，并返回列表视图
     */
    @RequestMapping(value = "{metaType}/{cmcode}/delete/{id}")
    public ModelAndView delete(@PathVariable("metaType") String metaType, @PathVariable("cmcode") String cmcode, @PathVariable("id") String id) {
        Map<String, Object> metaTag = mongoLogic.findMetaByCode(cmcode);
        String tableName = metaTag.get("oneTable").equals("1") ? ("meta_" + metaType + "_" + cmcode) : ("meta_" + metaType);
        mongoLogic.removeOne(tableName, id);
        ModelAndView view = new ModelAndView("redirect:/meta/app/" + metaType + "/" + cmcode + "/list");
        return view;
    }

}
