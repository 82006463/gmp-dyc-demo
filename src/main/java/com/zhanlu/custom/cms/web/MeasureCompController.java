package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.MeasureComp;
import com.zhanlu.custom.cms.entity.MeasureCompStandardItem;
import com.zhanlu.custom.cms.entity.StandardItem;
import com.zhanlu.custom.common.service.CustomService;
import com.zhanlu.custom.cms.service.MeasureCompService;
import com.zhanlu.custom.cms.service.MeasureCompStandardItemService;
import com.zhanlu.custom.cms.service.StandardItemService;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.security.entity.User;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 计量公司
 */
@Controller
@RequestMapping(value = "/custom/cms/measureComp")
public class MeasureCompController {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MeasureCompService measureCompService;
    @Autowired
    private StandardItemService standardItemService;
    @Autowired
    private MeasureCompStandardItemService measureCompStandardItemService;
    @Autowired
    private CustomService customService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<MeasureComp> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = measureCompService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/measureCompList");
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView mv = new ModelAndView("cms/measureCompEdit");
        MeasureComp entity = new MeasureComp();
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, String op) {
        MeasureComp entity = measureCompService.findById(id);
        ModelAndView mv = new ModelAndView("cms/measureCompEdit");
        mv.addObject("entity", entity);
        mv.addObject("op", op);
        return mv;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(MeasureComp entity, HttpServletRequest req) {
        User user = customService.getUser(req);
        if (entity.getId() == null) {
            entity.setCreaterId(user.getId());
            entity.setCreateTime(new Date());
        } else {
            entity.setUpdaterId(user.getId());
            entity.setUpdateTime(new Date());
        }
        measureCompService.saveOrUpdate(entity);
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/measureComp");
        return mv;
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id, HttpServletRequest req) {
        MeasureComp entity = measureCompService.findById(id);
        entity.setStatus(0);
        return this.update(entity, req);
    }

    @RequestMapping(value = "/standardItem", method = RequestMethod.GET)
    public ModelAndView standardItem(Page<StandardItem> page, HttpServletRequest request) {
        String measureCompIdStr = request.getParameter("measureCompId");
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        filters.add(new PropertyFilter("EQI_status", "2"));
        page = standardItemService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/measureCompStandardItemList");
        mv.addObject("page", page);
        mv.addObject("measureCompId", measureCompIdStr);
        Map<String, Object> params = new HashedMap();
        params.put("measureCompId", Long.parseLong(measureCompIdStr));
        mv.addObject("standardItems", measureCompStandardItemService.findList(params));
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/editStandardItem", method = RequestMethod.GET)
    public Object editStandardItem(HttpServletRequest req) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("result", 1);
        String standardItemId = req.getParameter("standardItemId");
        String measureCompId = req.getParameter("measureCompId");
        String checked = req.getParameter("checked");
        if (StringUtils.isBlank(standardItemId) || StringUtils.isBlank(measureCompId) || StringUtils.isBlank(checked)) {
            resultMap.put("result", 0);
            resultMap.put("msg", "standardItemId OR measureCompId OR checked is null");
            return resultMap;
        }
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("standardItemId", Long.parseLong(standardItemId));
        params.put("measureCompId", Long.parseLong(measureCompId));
        List<MeasureCompStandardItem> items = measureCompStandardItemService.findList(params);
        if (items == null || items.isEmpty()) {
            if (checked.equals("true")) {
                MeasureCompStandardItem entity = new MeasureCompStandardItem();
                entity.setStatus(1);
                entity.setStandardItemId(Long.parseLong(standardItemId));
                entity.setMeasureCompId(Long.parseLong(measureCompId));
                measureCompStandardItemService.saveOrUpdate(entity);
            }
        } else {
            if (checked.equals("false")) {
                measureCompStandardItemService.delete(items.get(0));
            }
        }
        return resultMap;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        MeasureComp entity = measureCompService.findById(id);
        ModelAndView mv = new ModelAndView("cms/measureCompView");
        mv.addObject("entity", entity);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public Object check(MeasureComp entity) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("result", 1);

        StringBuilder sqlBuf = new StringBuilder("SELECT id FROM cms_measure_comp WHERE 1=1");
        List<Map<String, Object>> maps = null;
        if (entity.getId() == null || entity.getId().longValue() < 1) {
            if (!resultMap.containsKey("msg") && StringUtils.isNotBlank(entity.getCode())) {
                maps = jdbcTemplate.queryForList(sqlBuf.toString() + " AND code='" + entity.getCode() + "'");
                if (maps != null && maps.size() >= 1) {
                    resultMap.put("result", 0);
                    resultMap.put("msg", "药企编号【" + entity.getCode() + "】已存在");
                }
            }
            if (!resultMap.containsKey("msg") && StringUtils.isNotBlank(entity.getName())) {
                maps = jdbcTemplate.queryForList(sqlBuf.toString() + " AND name='" + entity.getName() + "'");
                if (maps != null && maps.size() >= 1) {
                    resultMap.put("result", 0);
                    resultMap.put("msg", "药企名称【" + entity.getCode() + "】已存在");
                }
            }
            if (!resultMap.containsKey("msg") && StringUtils.isNotBlank(entity.getCreditCode())) {
                maps = jdbcTemplate.queryForList(sqlBuf.toString() + " AND credit_code='" + entity.getCreditCode() + "'");
                if (maps != null && maps.size() >= 1) {
                    resultMap.put("result", 0);
                    resultMap.put("msg", "药企信用代码【" + entity.getCode() + "】已存在");
                }
            }
        } else {
            MeasureComp tmp = measureCompService.findById(entity.getId());
            if (!resultMap.containsKey("msg") && StringUtils.isNotBlank(entity.getCode())) {
                maps = jdbcTemplate.queryForList(sqlBuf.toString() + " AND code='" + entity.getCode() + "'");
                if (maps != null && maps.size() >= (entity.getCode().equals(tmp.getCode()) ? 2 : 1)) {
                    resultMap.put("result", 0);
                    resultMap.put("msg", "器具编号【" + entity.getCode() + "】已存在");
                }
            }
            if (!resultMap.containsKey("msg") && StringUtils.isNotBlank(entity.getName())) {
                maps = jdbcTemplate.queryForList(sqlBuf.toString() + " AND name='" + entity.getName() + "'");
                if (maps != null && maps.size() >= (entity.getName().equals(tmp.getName()) ? 2 : 1)) {
                    resultMap.put("result", 0);
                    resultMap.put("msg", "器具名称【" + entity.getCode() + "】已存在");
                }
            }
            if (!resultMap.containsKey("msg") && StringUtils.isNotBlank(entity.getCreditCode())) {
                maps = jdbcTemplate.queryForList(sqlBuf.toString() + " AND credit_code='" + entity.getCreditCode() + "'");
                if (maps != null && maps.size() >= (entity.getName().equals(tmp.getCreditCode()) ? 2 : 1)) {
                    resultMap.put("result", 0);
                    resultMap.put("msg", "器具信用代码【" + entity.getCode() + "】已存在");
                }
            }
        }
        return resultMap;
    }
}
