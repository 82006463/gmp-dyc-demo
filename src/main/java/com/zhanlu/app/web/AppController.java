package com.zhanlu.app.web;

import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.config.service.DataDictService;
import com.zhanlu.framework.nosql.service.MongoService;
import com.zhanlu.framework.nosql.util.BasicUtils;
import com.zhanlu.framework.nosql.util.EditItem;
import com.zhanlu.framework.nosql.util.QueryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
@RequestMapping(value = "/app")
public class AppController {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataDictService dataDictService;

    @Autowired
    private MongoService mongoService;
    private String metaAppTable = "config_meta_app";

    /**
     * 分页列表
     */
    @RequestMapping(value = "{metaType}/{type}/list", method = RequestMethod.GET)
    public ModelAndView list(Page<?> page, @PathVariable("metaType") String metaType, @PathVariable("type") String type, HttpServletRequest req) throws Exception {
        Map<String, String[]> paramMap = req.getParameterMap();
        Map<String, Object> metaApp = this.getMetaTag(metaType, type);

        ModelAndView view = new ModelAndView("meta/appList");
        view.addObject("jsonSearch", BasicUtils.jsonSearch(dataDictService, jdbcTemplate, metaApp, paramMap));
        view.addObject("page", page);
        view.addObject("metaApp", metaApp);
        return view;
    }

    public Map<String, Object> getMetaTag(String metaType, String type) {
        List<QueryItem> queryItems = new ArrayList<>(1);
        queryItems.add(new QueryItem("Eq_String_code", metaType + "_" + type));
        return mongoService.findOne(metaAppTable, queryItems);
    }

}
