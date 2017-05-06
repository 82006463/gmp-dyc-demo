package com.zhanlu.framework.config.web;

import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.nosql.service.MongoService;
import com.zhanlu.framework.nosql.util.QueryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 标签属性管理Controller
 */
@Controller
@RequestMapping(value = "/config/meta/tag")
public class MetaTagController {

    @Autowired
    private MongoService mongoService;
    private String tableName = "config_meta_tag";

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(Page<Object> page, String type, HttpServletRequest req) {
        List<QueryItem> queryItems = QueryItem.buildSearchItems(req.getParameterMap());
        queryItems.add(new QueryItem("Eq_String_type", type));
        List<Map<String, Object>> entityList = mongoService.findByPage(this.tableName, queryItems, page);
        ModelAndView view = new ModelAndView("config/metaTagList");
        view.addObject("type", type);
        view.addObject("page", page);
        view.addObject("entityList", entityList);
        return view;
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView create(String type, String item) {
        Map<String, Object> entity = new LinkedHashMap<>();
        entity.put("type", type);
        ModelAndView view = new ModelAndView("config/metaTagEdit");
        view.addObject("item", item);
        view.addObject("entity", entity);
        return view;
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") String id, String item) throws Exception {
        Map<String, Object> entity = mongoService.findOne(this.tableName, id);
        ModelAndView view = new ModelAndView("config/metaTagEdit");
        view.addObject("item", item);
        view.addObject("entity", entity);
        return view;
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") String id) {
        ModelAndView view = new ModelAndView("config/metaTagView");
        view.addObject("entity", mongoService.findOne(this.tableName, id));
        return view;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ModelAndView update(RedirectAttributes attributes, String id, String item, String[] itemCodes, HttpServletRequest req) {
        Map<String, String[]> paramMap = req.getParameterMap();
        Map<String, Object> entity = null;
        if (id != null && id.trim().length() > 0) {
            entity = mongoService.findOne(this.tableName, id);
        } else {
            entity = new LinkedHashMap<>(itemCodes == null ? 16 : itemCodes.length + 16);
        }
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            if (!entry.getKey().equals("id") && !entry.getKey().startsWith("item")) {
                entity.put(entry.getKey(), entry.getValue()[0]);
            }
        }
        if (itemCodes != null && itemCodes.length > 0) {
            String[] itemNames = paramMap.get("itemNames");
            String[] itemDescs = paramMap.get("itemDescs");
            String[] itemDataTypes = paramMap.get("itemDataTypes");
            List<Map<String, Object>> items = new ArrayList<>(itemCodes == null ? 0 : itemCodes.length);
            if (item.equals("search")) {
                String[] itemCompareTypes = paramMap.get("itemCompareTypes");
                String[] itemTagTypes = paramMap.get("itemTagTypes");
                for (int i = 0; i < itemCodes.length; i++) {
                    Map<String, Object> itemMap = new LinkedHashMap<>(8);
                    itemMap.put("code", itemCodes[i]);
                    itemMap.put("name", itemNames[i]);
                    itemMap.put("desc", itemDescs[i]);
                    itemMap.put("dataType", itemDataTypes[i]);
                    itemMap.put("compareType", itemCompareTypes[i]);
                    itemMap.put("tagType", itemTagTypes[i]);
                    items.add(itemMap);
                }
                entity.put("queryItems", items);
            } else if (item.equals("list")) {
                for (int i = 0; i < itemCodes.length; i++) {
                    Map<String, Object> itemMap = new LinkedHashMap<>(8);
                    itemMap.put("code", itemCodes[i]);
                    itemMap.put("name", itemNames[i]);
                    itemMap.put("desc", itemDescs[i]);
                    itemMap.put("dataType", itemDataTypes[i]);
                    items.add(itemMap);
                }
                entity.put("listItems", items);
            } else if (item.equals("edit")) {
                Map<String, String> itemsMap = new LinkedHashMap<>(itemCodes.length);
                String[] itemRequireds = paramMap.get("itemRequireds");
                String[] itemSubForms = paramMap.get("itemSubForms");
                String[] itemFuzzys = paramMap.get("itemFuzzys");
                String[] itemTagTypes = paramMap.get("itemTagTypes");
                for (int i = 0; i < itemCodes.length; i++) {
                    Map<String, Object> itemMap = new LinkedHashMap<>(8);
                    itemMap.put("code", itemCodes[i]);
                    itemMap.put("name", itemNames[i]);
                    itemMap.put("desc", itemDescs[i]);
                    itemMap.put("required", itemRequireds[i]);
                    itemMap.put("dataType", itemDataTypes[i]);
                    itemMap.put("tagType", itemTagTypes[i]);
                    itemMap.put("fuzzy", itemFuzzys[i]);
                    itemMap.put("subForm", itemSubForms[i]);
                    items.add(itemMap);
                    itemsMap.put(itemCodes[i], itemDescs[i]);
                }
                entity.put("itemsMap", itemsMap);
                entity.put("editItems", items);
            }
        }
        mongoService.saveOrUpdate(this.tableName, id, entity);
        ModelAndView view = new ModelAndView("redirect:/config/meta/tag/list");
        attributes.addAttribute("type", paramMap.get("type")[0]);
        return view;
    }

    @RequestMapping(value = "delete/{id}")
    public ModelAndView delete(RedirectAttributes attributes, @PathVariable("id") String id, String type) {
        mongoService.removeOne(this.tableName, id);
        ModelAndView view = new ModelAndView("redirect:/config/meta/tag/list");
        attributes.addAttribute("type", type);
        return view;
    }

}
