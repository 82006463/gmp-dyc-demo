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
    public ModelAndView create(@PathVariable("type") String type) {
        Map<String, Object> entity = new LinkedHashMap<>();
        entity.put("type", type);

        String jspPage = type.contains("chart") ? "metaChartEdit" : "metaTagEdit";
        ModelAndView view = new ModelAndView("config/" + jspPage);
        view.addObject("entity", entity);
        return view;
    }

    @RequestMapping(value = "{type}/update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("type") String type, @PathVariable("id") String id) throws Exception {
        Map<String, Object> entity = mongoLogic.findOne(this.metaTable, id);
        String jspPage = type.contains("chart") ? "metaChartEdit" : "metaTagEdit";
        ModelAndView view = new ModelAndView("config/" + jspPage);
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
    public ModelAndView update(@PathVariable("type") String type, String id, HttpServletRequest req) {
        Map<String, String[]> paramMap = req.getParameterMap();
        String[] editCodes = paramMap.get("editCodes");
        String[] queryCodes = paramMap.get("queryCodes");
        String[] listCodes = paramMap.get("listCodes");

        Map<String, Object> entity = (id != null && id.trim().length() > 0) ? mongoLogic.findOne(this.metaTable, id) : new LinkedHashMap<String, Object>(editCodes == null ? 16 : editCodes.length + 16);
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            if (!entry.getKey().equals("id") && !entry.getKey().startsWith("item")) {
                entity.put(entry.getKey(), entry.getValue()[0]);
            }
        }
        if (editCodes != null && editCodes.length > 0) {
            String[] names = paramMap.get("editNames");
            String[] dataTypes = paramMap.get("editDataTypes");
            String[] tagTypes = paramMap.get("editTagTypes");
            String[] requireds = paramMap.get("editRequireds");
            String[] subForms = paramMap.get("editSubForms");
            String[] fuzzys = paramMap.get("editFuzzys");
            String[] arrays = paramMap.get("editArrays");
            String[] hiddens = paramMap.get("editHiddens");

            List<Map<String, Object>> items = new ArrayList<>(listCodes.length);
            Map<String, Object> itemsMap = new LinkedHashMap<>(8);
            for (int i = 0; i < editCodes.length; i++) {
                Map<String, Object> itemMap = new LinkedHashMap<>(8);
                itemMap.put("code", editCodes[i]);
                itemMap.put("name", names[i]);
                itemMap.put("required", requireds[i]);
                itemMap.put("dataType", dataTypes[i]);
                itemMap.put("tagType", tagTypes[i]);
                itemMap.put("fuzzy", fuzzys[i]);
                itemMap.put("subForm", subForms[i]);
                itemMap.put("array", arrays[i]);
                itemMap.put("hidden", hiddens[i]);
                items.add(itemMap);
                itemsMap.put(editCodes[i], names[i]);
            }
            entity.put("itemsMap", itemsMap);
            entity.put("editItems", items);
        }
        if (queryCodes != null && queryCodes.length > 0) {
            String[] names = paramMap.get("queryNames");
            String[] dataTypes = paramMap.get("queryDataTypes");
            String[] compareTypes = paramMap.get("queryCompareTypes");
            String[] tagTypes = paramMap.get("queryTagTypes");
            List<Map<String, Object>> items = new ArrayList<>(listCodes.length);
            for (int i = 0; i < queryCodes.length; i++) {
                Map<String, Object> itemMap = new LinkedHashMap<>(8);
                itemMap.put("code", queryCodes[i]);
                itemMap.put("name", names[i]);
                itemMap.put("dataType", dataTypes[i]);
                itemMap.put("compareType", compareTypes[i]);
                itemMap.put("tagType", tagTypes[i]);
                items.add(itemMap);
            }
            entity.put("queryItems", items);
        }
        if (listCodes != null && listCodes.length > 0) {
            String[] names = paramMap.get("listNames");
            String[] dataTypes = paramMap.get("listDataTypes");
            List<Map<String, Object>> items = new ArrayList<>(listCodes.length);
            for (int i = 0; i < listCodes.length; i++) {
                Map<String, Object> itemMap = new LinkedHashMap<>(8);
                itemMap.put("code", listCodes[i]);
                itemMap.put("name", names[i]);
                itemMap.put("dataType", dataTypes[i]);
                items.add(itemMap);
            }
            entity.put("listItems", items);
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
