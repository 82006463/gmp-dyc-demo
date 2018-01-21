package com.zhanlu.custom.dm.web;

import com.zhanlu.custom.common.service.CustomService;
import com.zhanlu.custom.dm.entity.BackupDb;
import com.zhanlu.custom.dm.service.BackupDbService;
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
 * 备份数据库
 */
@Controller
@RequestMapping(value = "/custom/dm/backupDb")
public class BackupDbController {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BackupDbService backupDbService;
    @Autowired
    private CustomService customService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<BackupDb> page, HttpServletRequest request) {
        User user = customService.getUser(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.DESC);
        }
        page = backupDbService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("dm/backupDbList");
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView mv = new ModelAndView("dm/backupDbEdit");
        BackupDb entity = new BackupDb();
        entity.setStatus(1);
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) {
        BackupDb entity = backupDbService.findById(id);
        ModelAndView mv = new ModelAndView("dm/backupDbEdit");
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(BackupDb entity, HttpServletRequest req) {
        User user = customService.getUser(req);
        if (entity.getId() == null) {
            entity.setCreaterId(user.getId());
            entity.setCreateTime(new Date());
            entity.setTenantId(user.getOrg().getId());
        } else {
            entity.setUpdaterId(user.getId());
            entity.setUpdateTime(new Date());
        }
        backupDbService.saveOrUpdate(entity);
        ModelAndView mv = new ModelAndView("redirect:/custom/dm/backupDb");
        return mv;
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id, HttpServletRequest req) {
        BackupDb entity = backupDbService.findById(id);
        entity.setStatus(0);
        return this.update(entity, req);
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        BackupDb entity = backupDbService.findById(id);
        ModelAndView mv = new ModelAndView("dm/backupDbView");
        mv.addObject("entity", entity);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public Object check(BackupDb entity, HttpServletRequest req) {
        User user = customService.getUser(req);
        StringBuilder sqlBuf = new StringBuilder("SELECT id FROM cms_equipment WHERE 1=1 AND tenant_id=" + user.getOrg().getId());

        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("result", 1);
        List<Map<String, Object>> maps = null;
        if (entity.getId() == null || entity.getId().longValue() < 1) {
            if (!resultMap.containsKey("msg") && StringUtils.isNotBlank(entity.getCode())) {
                maps = jdbcTemplate.queryForList(sqlBuf.toString() + " AND code='" + entity.getCode() + "'");
                if (maps != null && maps.size() >= 1) {
                    resultMap.put("result", 0);
                    resultMap.put("msg", "器具编号【" + entity.getCode() + "】已存在");
                }
            }
            if (!resultMap.containsKey("msg") && StringUtils.isNotBlank(entity.getName())) {
                maps = jdbcTemplate.queryForList(sqlBuf.toString() + " AND name='" + entity.getName() + "'");
                if (maps != null && maps.size() >= 1) {
                    resultMap.put("result", 0);
                    resultMap.put("msg", "器具名称【" + entity.getCode() + "】已存在");
                }
            }
        } else {
            BackupDb tmp = backupDbService.findById(entity.getId());
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
        }
        return resultMap;
    }

}
