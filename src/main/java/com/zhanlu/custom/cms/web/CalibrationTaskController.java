package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.*;
import com.zhanlu.custom.cms.service.*;
import com.zhanlu.custom.common.service.CustomService;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.security.entity.User;
import com.zhanlu.framework.security.shiro.ShiroUtils;
import com.zhanlu.office.ExcelExporter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 月度外校任务
 */
@Controller
@RequestMapping(value = "/custom/cms/calibrationTask")
public class CalibrationTaskController {

    @Autowired
    private MeasureCompService measureCompService;
    @Autowired
    private CalibrationInService calibrationInService;
    @Autowired
    private CalibrationExtService calibrationExtService;
    @Autowired
    private CalibrationTmpService calibrationTmpService;
    @Autowired
    private CalibrationTaskService calibrationTaskService;
    @Autowired
    private CustomService customService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<CalibrationTask> page, HttpServletRequest req) {
        User user = customService.getUser(req);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        filters.add(new PropertyFilter("LEI_status", "3"));
        if (!ShiroUtils.getPrincipal().getAuthorities().contains("cms_calibrationTask_review")) {
            filters.add(new PropertyFilter("EQS_approver", (String) req.getSession().getAttribute("username")));
        }
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.DESC);
        }
        page = calibrationTaskService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/calibrationTaskList");
        mv.addObject("page", page);
        mv.addObject("measureComps", measureCompService.findList(null));
        return mv;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, HttpServletRequest req) {
        ModelAndView mv = new ModelAndView("cms/calibrationTaskEdit");
        CalibrationTask entity = calibrationTaskService.findById(id);
        mv.addObject("entity", entity);

        User user = customService.getUser(req);
        List<PropertyFilter> filters = new ArrayList<>();
        filters.add(new PropertyFilter("EQL_taskId", id.toString()));
        if (entity.getCalibrationMode().intValue() == 1) {
            Page<CalibrationIn> page = new Page<>(Integer.MAX_VALUE);
            calibrationInService.findPage(page, filters);
            mv.addObject("children", page.getResult());
        } else if (entity.getCalibrationMode().intValue() == 2) {
            Page<CalibrationExt> page = new Page<>(Integer.MAX_VALUE);
            calibrationExtService.findPage(page, filters);
            mv.addObject("children", page.getResult());
        } else if (entity.getCalibrationMode().intValue() == 3) {
            Page<CalibrationTmp> page = new Page<>(Integer.MAX_VALUE);
            calibrationTmpService.findPage(page, filters);
            mv.addObject("children", page.getResult());
        }
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(Long id, Integer status, @RequestParam(value = "files", required = false) CommonsMultipartFile[] files, HttpServletRequest req) {
        CalibrationTask entity = calibrationTaskService.findById(id);
        entity.setStatus(status);
        try {
            calibrationTaskService.updateTask(entity, files, req.getParameterMap());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ModelAndView mv = new ModelAndView("redirect:/custom/cms/calibrationTask");
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("cms/calibrationTaskView");
        CalibrationTask entity = calibrationTaskService.findById(id);
        mv.addObject("entity", entity);
        List<PropertyFilter> filters = new ArrayList<>();
        filters.add(new PropertyFilter("EQL_taskId", id.toString()));
        if (entity.getCalibrationMode().intValue() == 1) {
            Page<CalibrationIn> page = new Page<>(Integer.MAX_VALUE);
            calibrationInService.findPage(page, filters);
            mv.addObject("children", page.getResult());
        } else if (entity.getCalibrationMode().intValue() == 2) {
            Page<CalibrationExt> page = new Page<>(Integer.MAX_VALUE);
            calibrationExtService.findPage(page, filters);
            mv.addObject("children", page.getResult());
        } else if (entity.getCalibrationMode().intValue() == 3) {
            Page<CalibrationTmp> page = new Page<>(Integer.MAX_VALUE);
            calibrationTmpService.findPage(page, filters);
            mv.addObject("children", page.getResult());
        }
        return mv;
    }

    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
    public ModelAndView download(@PathVariable("id") Long id, Long planId, HttpServletResponse resp) {
        CalibrationTask entity = calibrationTaskService.findById(id);
        String filePath = null;
        if (entity.getCalibrationMode().intValue() == 1) {
            filePath = calibrationInService.findById(planId).getFilePath();
        } else if (entity.getCalibrationMode().intValue() == 2) {
            filePath = calibrationExtService.findById(planId).getFilePath();
        } else if (entity.getCalibrationMode().intValue() == 3) {
            filePath = calibrationTmpService.findById(planId).getFilePath();
        }
        if (StringUtils.isNotBlank(filePath)) {
            File file = new File(filePath);
            if (file.exists()) {
                resp.setContentType("application/force-download");// 设置强制下载不打开
                try (OutputStream out = resp.getOutputStream(); FileInputStream fis = new FileInputStream(file)) {
                    resp.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(filePath.substring(filePath.lastIndexOf("/") + 1), "UTF-8"));
                    IOUtils.copy(fis, out);
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    @RequestMapping(value = "/exportFile", method = RequestMethod.GET)
    public void exportFile(Long taskId, HttpServletRequest req, HttpServletResponse resp) {
        User user = customService.getUser(req);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("multipart/form-data");
        try (OutputStream out = resp.getOutputStream()) {
            String fileName = URLEncoder.encode("待办任务-" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".xls", "UTF-8");
            resp.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            ExcelExporter table = ExcelExporter.getInstance();
            table.setComp("公司名：", user.getOrg().getName());
            table.setUser("导出人：", user.getFullname());
            table.setDate("导出日期：", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm"));
            table.setFile("文件名称：", URLDecoder.decode(fileName, "UTF-8"));

            List<String> header = new ArrayList<>();
            header.add("器具编号");
            header.add("器具名称");
            header.add("所在房间");
            header.add("出厂编号");
            header.add("记录/证书编号");
            header.add("校准结果");
            header.add("实际校准时间");
            header.add("备注");
            table.setHeader(header);

            CalibrationTask entity = calibrationTaskService.findById(taskId);
            List<PropertyFilter> filters = new ArrayList<>();
            filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
            filters.add(new PropertyFilter("EQL_taskId", taskId.toString()));
            List<List<String>> body = new ArrayList<>();
            if (entity.getCalibrationMode().intValue() == 1) {
                Page<CalibrationIn> page = new Page<>(Integer.MAX_VALUE);
                calibrationInService.findPage(page, filters);
                if (page.getResult() != null && !page.getResult().isEmpty()) {
                    for (CalibrationIn tmp : page.getResult()) {
                        Equipment equipment = tmp.getEquipment();
                        List<String> tmpList = new ArrayList<>();
                        tmpList.add(equipment.getCode());
                        tmpList.add(equipment.getName());
                        tmpList.add(StringUtils.isBlank(equipment.getRoom()) ? "N.A." : equipment.getRoom());
                        tmpList.add(StringUtils.isBlank(equipment.getFactoryCode()) ? "N.A." : equipment.getFactoryCode());
                        tmpList.add("");
                        tmpList.add("");
                        tmpList.add("");
                        tmpList.add("");
                        body.add(tmpList);
                    }
                }
            } else if (entity.getCalibrationMode().intValue() == 2) {
                Page<CalibrationExt> page = new Page<>(Integer.MAX_VALUE);
                calibrationExtService.findPage(page, filters);
                if (page.getResult() != null && !page.getResult().isEmpty()) {
                    for (CalibrationExt tmp : page.getResult()) {
                        Equipment equipment = tmp.getEquipment();
                        List<String> tmpList = new ArrayList<>();
                        tmpList.add(equipment.getCode());
                        tmpList.add(equipment.getName());
                        tmpList.add(StringUtils.isBlank(equipment.getRoom()) ? "N.A." : equipment.getRoom());
                        tmpList.add(StringUtils.isBlank(equipment.getFactoryCode()) ? "N.A." : equipment.getFactoryCode());
                        tmpList.add("");
                        tmpList.add("");
                        tmpList.add("");
                        tmpList.add("");
                        body.add(tmpList);
                    }
                }
            } else if (entity.getCalibrationMode().intValue() == 3) {
                Page<CalibrationTmp> page = new Page<>(Integer.MAX_VALUE);
                calibrationTmpService.findPage(page, filters);
                if (page.getResult() != null && !page.getResult().isEmpty()) {
                    for (CalibrationTmp tmp : page.getResult()) {
                        Equipment equipment = tmp.getEquipment();
                        List<String> tmpList = new ArrayList<>();
                        tmpList.add(equipment.getCode());
                        tmpList.add(equipment.getName());
                        tmpList.add(StringUtils.isBlank(equipment.getRoom()) ? "N.A." : equipment.getRoom());
                        tmpList.add(StringUtils.isBlank(equipment.getFactoryCode()) ? "N.A." : equipment.getFactoryCode());
                        tmpList.add("");
                        tmpList.add("");
                        tmpList.add("");
                        tmpList.add("");
                        body.add(tmpList);
                    }
                }
            }
            table.setBody(body);
            HSSFWorkbook workbook = table.build();
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
