package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.Equipment;
import com.zhanlu.custom.cms.service.CmsService;
import com.zhanlu.custom.cms.service.EquipmentService;
import com.zhanlu.excel.ExcelUtils;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.security.entity.User;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * 通知
 */
@Controller
@RequestMapping(value = "/custom/cms/equipment")
public class EquipmentController {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private CmsService cmsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<Equipment> page, HttpServletRequest request) {
        User user = cmsService.getUser(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.DESC);
        }
        page = equipmentService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/equipmentList");
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView mv = new ModelAndView("cms/equipmentEdit");
        Equipment entity = new Equipment();
        entity.setTmpStatus(1);
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) {
        Equipment entity = equipmentService.findById(id);
        ModelAndView mv = new ModelAndView("cms/equipmentEdit");
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(Equipment entity, HttpServletRequest req) {
        User user = cmsService.getUser(req);
        if (entity.getId() == null) {
            entity.setCreaterId(user.getId());
            entity.setCreateTime(new Date());
            entity.setTenantId(user.getOrg().getId());
        } else {
            entity.setUpdaterId(user.getId());
            entity.setUpdateTime(new Date());
        }
        equipmentService.saveOrUpdate(entity);
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/equipment");
        return mv;
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id, HttpServletRequest req) {
        Equipment entity = equipmentService.findById(id);
        entity.setStatus(0);
        return this.update(entity, req);
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        Equipment entity = equipmentService.findById(id);
        ModelAndView mv = new ModelAndView("cms/equipmentView");
        mv.addObject("entity", entity);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public Object check(Equipment entity, HttpServletRequest req) {
        User user = cmsService.getUser(req);
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
            Equipment tmp = equipmentService.findById(entity.getId());
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

    @RequestMapping(value = "/exportFile", method = RequestMethod.GET)
    public void exportFile(HttpServletRequest req, HttpServletResponse resp) {
        User user = cmsService.getUser(req);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("multipart/form-data");
        try (OutputStream out = resp.getOutputStream()) {
            String fileName = URLEncoder.encode("器具-" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".xls", "UTF-8");
            resp.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            ExcelUtils table = new ExcelUtils();
            table.setComp("公司名：", user.getOrg().getName());
            table.setUser("导出人：", user.getFullname());
            table.setDate("导出日期：", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm"));
            table.setFile("文件名称：", URLDecoder.decode(fileName, "UTF-8"));

            List<String> search = new ArrayList<>();
            List<String> header = new ArrayList<>();

            Page<Equipment> page = new Page<>(Integer.MAX_VALUE);
            List<PropertyFilter> filters = new ArrayList<>();
            filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
            if (StringUtils.isNotBlank(req.getParameter("filter_EQS_code"))) {
                filters.add(new PropertyFilter("EQS_code", req.getParameter("filter_EQS_code")));
                search.add("器具编号：" + req.getParameter("filter_EQS_code"));
            }
            if (StringUtils.isNotBlank(req.getParameter("filter_LIKES_name"))) {
                filters.add(new PropertyFilter("LIKES_name", req.getParameter("filter_LIKES_name")));
                search.add("器具名称：" + req.getParameter("filter_LIKES_name"));
            }
            if (StringUtils.isNotBlank(req.getParameter("filter_EQI_calibrationMode"))) {
                String calibrationMode = req.getParameter("filter_EQI_calibrationMode");
                filters.add(new PropertyFilter("EQI_calibrationMode", calibrationMode));
                search.add("校准方式：" + (calibrationMode.equals("1") ? "内校" : calibrationMode.equals("2") ? "外校" : "N.A."));
            }
            if (StringUtils.isNotBlank(req.getParameter("filter_EQI_status"))) {
                String status = req.getParameter("filter_EQI_status");
                filters.add(new PropertyFilter("EQI_status", status));
                search.add("器具状态：" + (status.equals("-1") ? "失效" : status.equals("1") ? "暂存中" : status.equals("2") ? "拒绝中" : status.equals("3") ? "复核中" : status.equals("4") ? "正常" : status.equals("5") ? "延期" : "N.A."));
            }

            equipmentService.findPage(page, filters);
            if (search.size() > 0) {
                table.setSearch(search);
            }
            header.add("器具编号");
            header.add("器具名称");
            header.add("型号");
            header.add("出厂编号");
            header.add("所在房间");
            header.add("所属设备");
            header.add("功能");
            header.add("精度");
            header.add("校准规范编号");
            header.add("校准规范名称");
            header.add("使用范围下限");
            header.add("使用范围上限");
            header.add("校准方式");
            header.add("校准周期(月)");
            header.add("上次校准时间");
            header.add("待校准时间");
            header.add("状态");
            table.setHeader(header);
            if (page.getResult() != null && !page.getResult().isEmpty()) {
                List<List<String>> body = new ArrayList<>();
                for (Equipment tmp : page.getResult()) {
                    List<String> tmpList = new ArrayList<>();
                    tmpList.add(tmp.getCode());
                    tmpList.add(tmp.getName());
                    tmpList.add(StringUtils.isBlank(tmp.getModel()) ? "N.A." : tmp.getModel());
                    tmpList.add(StringUtils.isBlank(tmp.getFactoryCode()) ? "N.A." : tmp.getFactoryCode());
                    tmpList.add(StringUtils.isBlank(tmp.getRoom()) ? "N.A." : tmp.getRoom());
                    tmpList.add(StringUtils.isBlank(tmp.getEquipment()) ? "N.A." : tmp.getEquipment());
                    tmpList.add(StringUtils.isBlank(tmp.getFunc()) ? "N.A." : tmp.getFunc());
                    tmpList.add(StringUtils.isBlank(tmp.getPrecision()) ? "N.A." : tmp.getPrecision());
                    tmpList.add(StringUtils.isBlank(tmp.getCalibration()) ? "N.A." : tmp.getCalibration());
                    tmpList.add(StringUtils.isBlank(tmp.getCalibrationName()) ? "N.A." : tmp.getCalibrationName());
                    tmpList.add(tmp.getMeasureRangeMin() == null ? "N.A." : tmp.getMeasureRangeMin() + "");
                    tmpList.add(tmp.getMeasureRangeMax() == null ? "N.A." : tmp.getMeasureRangeMax() + "");
                    if (tmp.getCalibrationMode().intValue() == 1) {
                        tmpList.add("内校");
                    } else if (tmp.getCalibrationMode().intValue() == 2) {
                        tmpList.add("外校");
                    } else {
                        tmpList.add("N.A.");
                    }
                    tmpList.add(tmp.getCalibrationCycle() + "");
                    tmpList.add(tmp.getLastActualDate() == null ? "N.A." : DateFormatUtils.format(tmp.getLastActualDate(), "yyyy-MM-dd"));
                    tmpList.add(tmp.getExpectDate() == null ? "N.A." : DateFormatUtils.format(tmp.getExpectDate(), "yyyy-MM-dd"));
                    tmpList.add(tmp.getStatus() == null ? "N.A." : tmp.getStatus().intValue() == -1 ? "失效" : tmp.getStatus().intValue() == 1 ? "暂存中" : tmp.getStatus().intValue() == 2 ? "拒绝中" : tmp.getStatus().intValue() == 3 ? "复核中" : tmp.getStatus().intValue() == 4 ? "正常" : "延期");
                    body.add(tmpList);
                }
                table.setBody(body);
            }
            HSSFWorkbook workbook = table.build();
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
