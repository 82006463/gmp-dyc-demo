package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.StandardItem;
import com.zhanlu.custom.cms.service.CmsService;
import com.zhanlu.custom.cms.service.StandardItemService;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.security.entity.User;
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
 * 通知
 */
@Controller
@RequestMapping(value = "/custom/cms/standardItem")
public class StandardItemController {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StandardItemService standardItemService;
    @Autowired
    private CmsService cmsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<StandardItem> page, HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.ASC);
        }
        page = standardItemService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/standardItemList");
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView mv = new ModelAndView("cms/standardItemEdit");
        StandardItem entity = new StandardItem();
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, String op) {
        StandardItem entity = standardItemService.findById(id);
        ModelAndView mv = new ModelAndView("cms/standardItemEdit");
        mv.addObject("entity", entity);
        mv.addObject("op", op);
        return mv;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(StandardItem entity, HttpServletRequest req) {
        User user = cmsService.getUser(req);
        if (entity.getId() == null) {
            entity.setCreaterId(user.getId());
            entity.setCreateTime(new Date());
        } else {
            entity.setUpdaterId(user.getId());
            entity.setUpdateTime(new Date());
        }
        standardItemService.saveOrUpdate(entity);
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/standardItem");
        return mv;
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id, HttpServletRequest req) {
        StandardItem entity = standardItemService.findById(id);
        entity.setStatus(0);
        return this.update(entity, req);
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        StandardItem entity = standardItemService.findById(id);
        ModelAndView mv = new ModelAndView("cms/standardItemView");
        mv.addObject("entity", entity);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public Object check(StandardItem entity) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("result", 1);

        StringBuilder sqlBuf = new StringBuilder("SELECT id FROM cms_equipment WHERE 1=1");
        List<Map<String, Object>> maps = null;
        if (entity.getId() == null || entity.getId().longValue() < 1) {
            if (!resultMap.containsKey("msg") && StringUtils.isNotBlank(entity.getCode())) {
                maps = jdbcTemplate.queryForList(sqlBuf.toString() + " AND code='" + entity.getCode() + "'");
                if (maps != null && maps.size() >= 1) {
                    resultMap.put("result", 0);
                    resultMap.put("msg", "标准项编号【" + entity.getCode() + "】已存在");
                }
            }
            if (!resultMap.containsKey("msg") && StringUtils.isNotBlank(entity.getName())) {
                maps = jdbcTemplate.queryForList(sqlBuf.toString() + " AND name='" + entity.getName() + "'");
                if (maps != null && maps.size() >= 1) {
                    resultMap.put("result", 0);
                    resultMap.put("msg", "标准项名称【" + entity.getCode() + "】已存在");
                }
            }
        } else {
            StandardItem tmp = standardItemService.findById(entity.getId());
            if (!resultMap.containsKey("msg") && StringUtils.isNotBlank(entity.getCode())) {
                maps = jdbcTemplate.queryForList(sqlBuf.toString() + " AND code='" + entity.getCode() + "'");
                if (maps != null && maps.size() >= (entity.getCode().equals(tmp.getCode()) ? 2 : 1)) {
                    resultMap.put("result", 0);
                    resultMap.put("msg", "标准项编号【" + entity.getCode() + "】已存在");
                }
            }
            if (!resultMap.containsKey("msg") && StringUtils.isNotBlank(entity.getName())) {
                maps = jdbcTemplate.queryForList(sqlBuf.toString() + " AND name='" + entity.getName() + "'");
                if (maps != null && maps.size() >= (entity.getName().equals(tmp.getName()) ? 2 : 1)) {
                    resultMap.put("result", 0);
                    resultMap.put("msg", "标准项名称【" + entity.getCode() + "】已存在");
                }
            }
        }
        return resultMap;
    }
}
