package com.zhanlu.framework.config.web;

import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.logic.MongoLogic;
import com.zhanlu.framework.nosql.item.QueryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 标签属性管理Controller
 */
@Controller
@RequestMapping(value = "/config/meta")
public class ConfigMetaController {

    @Autowired
    private MongoLogic mongoLogic;
    private String metaTable = "config_meta";

    @RequestMapping(value = "{type}/list", method = RequestMethod.GET)
    public ModelAndView list(@PathVariable("type") String type, Page<Map<String, Object>> page, HttpServletRequest req) {
        List<QueryItem> queryItems = QueryItem.buildSearchItems(req.getParameterMap());
        queryItems.add(new QueryItem("Eq_String_type", type));
        mongoLogic.findByPage(this.metaTable, queryItems, page);

        String jspPage = type.contains("chart") ? "metaChartList" : "metaTagList";
        ModelAndView view = new ModelAndView("config/" + jspPage);
        view.addObject("type", type);
        view.addObject("page", page);
        return view;
    }

    @RequestMapping(value = "{type}/create", method = RequestMethod.GET)
    public ModelAndView create(@PathVariable("type") String type, String item) {
        Map<String, Object> entity = new LinkedHashMap<>();
        entity.put("type", type);

        String jspPage = type.contains("chart") ? "metaChartEdit" : "metaTagEdit";
        ModelAndView view = new ModelAndView("config/" + jspPage);
        view.addObject("item", item);
        view.addObject("entity", entity);
        return view;
    }

    @RequestMapping(value = "{type}/update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("type") String type, @PathVariable("id") String id, String item) throws Exception {
        Map<String, Object> entity = mongoLogic.findOne(this.metaTable, id);

        String jspPage = type.contains("chart") ? "metaChartEdit" : "metaTagEdit";
        ModelAndView view = new ModelAndView("config/" + jspPage);
        view.addObject("item", item);
        view.addObject("entity", entity);
        return view;
    }

    @RequestMapping(value = "{type}/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("type") String type, @PathVariable("id") String id) {
        String jspPage = type.contains("chart") ? "metaChartView" : "metaTagView";
        ModelAndView view = new ModelAndView("config/" + jspPage);
        view.addObject("entity", mongoLogic.findOne(this.metaTable, id));
        return view;
    }

    @RequestMapping(value = "{type}/update", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable("type") String type, String id, String item, String[] itemCodes, HttpServletRequest req) {
        Map<String, String[]> paramMap = req.getParameterMap();
        Map<String, Object> entity = null;
        if (id != null && id.trim().length() > 0) {
            entity = mongoLogic.findOne(this.metaTable, id);
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
            String[] itemDataTypes = paramMap.get("itemDataTypes");
            List<Map<String, Object>> items = new ArrayList<>(itemCodes == null ? 0 : itemCodes.length);
            if (item.equals("search")) {
                String[] itemCompareTypes = paramMap.get("itemCompareTypes");
                String[] itemTagTypes = paramMap.get("itemTagTypes");
                for (int i = 0; i < itemCodes.length; i++) {
                    Map<String, Object> itemMap = new LinkedHashMap<>(8);
                    itemMap.put("code", itemCodes[i]);
                    itemMap.put("name", itemNames[i]);
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
                    itemMap.put("required", itemRequireds[i]);
                    itemMap.put("dataType", itemDataTypes[i]);
                    itemMap.put("tagType", itemTagTypes[i]);
                    itemMap.put("fuzzy", itemFuzzys[i]);
                    itemMap.put("subForm", itemSubForms[i]);
                    items.add(itemMap);
                    itemsMap.put(itemCodes[i], itemNames[i]);
                }
                entity.put("itemsMap", itemsMap);
                entity.put("editItems", items);
            }
        }
        mongoLogic.saveOrUpdate(this.metaTable, id, entity);
        ModelAndView view = new ModelAndView("redirect:/config/meta/" + type + "/list");
        return view;
    }

    @RequestMapping(value = "{type}/delete/{id}")
    public ModelAndView delete(@PathVariable("type") String type, @PathVariable("id") String id) {
        mongoLogic.removeOne(this.metaTable, id);
        ModelAndView view = new ModelAndView("redirect:/config/meta/" + type + "/list");
        return view;
    }

}
