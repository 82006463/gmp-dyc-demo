package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.CalibrationExt;
import com.zhanlu.custom.cms.entity.CalibrationIn;
import com.zhanlu.custom.cms.entity.Equipment;
import com.zhanlu.custom.cms.service.CalibrationInService;
import com.zhanlu.custom.cms.service.CmsService;
import com.zhanlu.custom.cms.service.MeasureCompService;
import com.zhanlu.excel.ExcelUtils;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.security.entity.User;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * 月度内校
 */
@Controller
@RequestMapping(value = "/custom/cms/calibrationIn")
public class CalibrationInController {

    @Autowired
    private MeasureCompService measureCompService;
    @Autowired
    private CalibrationInService calibrationInService;
    @Autowired
    private CmsService cmsService;

    @RequestMapping(value = "/{status}", method = RequestMethod.GET)
    public ModelAndView list(Page<CalibrationIn> page, @PathVariable Integer status, Integer taskStatus, HttpServletRequest request) {
        User user = cmsService.getUser(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        ModelAndView mv = new ModelAndView("cms/calibrationPlanList");
        mv.addObject("type", "calibrationIn");
        if (status != null && status.intValue() == 3) {
            filters.add(new PropertyFilter("EQI_status", status.toString()));
            mv.addObject("status", status);
            mv.addObject("measureComps", measureCompService.findList(null));
        } else {
            if (taskStatus != null && taskStatus.intValue() == -1) {
                filters.add(new PropertyFilter("LEI_status", "2"));
            } else if (taskStatus != null && taskStatus.intValue() == 1) {
                filters.add(new PropertyFilter("EQI_status", "3"));
            } else {
                filters.add(new PropertyFilter("LEI_status", "3"));
            }
            mv.addObject("status", 1);
        }
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.DESC);
        }
        page = calibrationInService.findPage(page, filters);
        mv.addObject("page", page);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/generateTask", method = RequestMethod.GET)
    public Object generateTask(HttpServletRequest request) {
        User user = cmsService.getUser(request);
        return calibrationInService.generateTask(user);
    }

    @ResponseBody
    @RequestMapping(value = "/sendTask", method = RequestMethod.GET)
    public Object sendTask(HttpServletRequest req) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("result", 0);
        String measureCompId = req.getParameter("measureCompId");
        String approver = req.getParameter("approver");
        User user = cmsService.getUser(approver);
        /*if (StringUtils.isBlank(measureCompId)) {
            resultMap.put("msg", "请选择计量公司");
        } else */
        if (StringUtils.isBlank(approver)) {
            resultMap.put("msg", "请输入计量实施人");
        } else if (user == null) {
            resultMap.put("msg", "计量实施人账号不存在");
        }
        if (resultMap.size() > 1) {
            return resultMap;
        }
        resultMap = calibrationInService.sendTask(user, null, approver);
        return resultMap;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable("id") Long id, Integer status) {
        CalibrationIn entity = calibrationInService.findById(id);
        entity.setStatus(1);
        calibrationInService.saveOrUpdate(entity);
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/calibrationIn/" + status);
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        CalibrationIn entity = calibrationInService.findById(id);
        ModelAndView mv = new ModelAndView("cms/calibrationInView");
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/{status}/exportFile", method = RequestMethod.GET)
    public void exportFile(@PathVariable Integer status, HttpServletRequest req, HttpServletResponse resp) {
        User user = cmsService.getUser(req);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("multipart/form-data");
        try (OutputStream out = resp.getOutputStream()) {
            String fileName = URLEncoder.encode("月度内校" + (status.intValue() == 3 ? "任务-" : "计划-") + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".xls", "UTF-8");
            resp.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            ExcelUtils table = new ExcelUtils();
            table.setComp("公司名：", user.getOrg().getName());
            table.setUser("导出人：", user.getFullname());
            table.setDate("导出日期：", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm"));
            table.setFile("文件名称：", URLDecoder.decode(fileName, "UTF-8"));

            List<String> search = new ArrayList<>();
            List<String> header = new ArrayList<>();

            Page<CalibrationIn> pageExt = new Page<>(Integer.MAX_VALUE);
            List<PropertyFilter> filters = new ArrayList<>();
            filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
            filters.add(new PropertyFilter(status.intValue() == 3 ? "EQI_status" : "LEI_status", "3"));
            if (StringUtils.isNotBlank(req.getParameter("filter_GED_expectDate"))) {
                filters.add(new PropertyFilter("GED_expectDate", req.getParameter("filter_GED_expectDate")));
                search.add("待校准日期：" + req.getParameter("filter_GED_expectDate"));
            }
            if (StringUtils.isNotBlank(req.getParameter("filter_LED_expectDate"))) {
                filters.add(new PropertyFilter("LED_expectDate", req.getParameter("filter_LED_expectDate")));
                search.add("待校准日期：" + req.getParameter("filter_LED_expectDate"));
            }
            calibrationInService.findPage(pageExt, filters);
            if (search.size() > 0) {
                table.setSearch(search);
            }
            header.add("器具编号");
            header.add("器具名称");
            header.add("所在房间");
            header.add("上次校准日期");
            header.add("校准周期(月)");
            header.add("待校准日期");
            table.setHeader(header);
            if (pageExt.getResult() != null && !pageExt.getResult().isEmpty()) {
                List<List<String>> body = new ArrayList<>();
                for (CalibrationIn tmp : pageExt.getResult()) {
                    Equipment equipment = tmp.getEquipment();
                    List<String> tmpList = new ArrayList<>();
                    tmpList.add(equipment.getCode());
                    tmpList.add(equipment.getName());
                    tmpList.add(StringUtils.isBlank(equipment.getRoom()) ? "N.A." : equipment.getRoom());
                    tmpList.add(tmp.getLastActualDate() == null ? "N.A." : DateFormatUtils.format(tmp.getLastActualDate(), "yyyy-MM-dd"));
                    tmpList.add(equipment.getCalibrationCycle() == null ? "N.A." : equipment.getCalibrationCycle() + "");
                    tmpList.add(tmp.getExpectDate() == null ? "N.A." : DateFormatUtils.format(tmp.getExpectDate(), "yyyy-MM-dd"));
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
