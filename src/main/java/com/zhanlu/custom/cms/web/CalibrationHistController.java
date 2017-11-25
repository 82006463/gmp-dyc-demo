package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.CalibrationHist;
import com.zhanlu.custom.cms.entity.Equipment;
import com.zhanlu.custom.cms.service.CalibrationHistService;
import com.zhanlu.custom.cms.service.CmsService;
import com.zhanlu.office.ExcelUtils;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 校准历史
 */
@Controller
@RequestMapping(value = "/custom/cms/calibrationHist")
public class CalibrationHistController {

    @Autowired
    private CalibrationHistService calibrationHistService;
    @Autowired
    private CmsService cmsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<CalibrationHist> page, HttpServletRequest request) {
        User user = cmsService.getUser(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("id");
            page.setOrder(Page.DESC);
        }
        page = calibrationHistService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/calibrationHistList");
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        CalibrationHist entity = calibrationHistService.findById(id);
        ModelAndView mv = new ModelAndView("cms/calibrationHistView");
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/exportFile", method = RequestMethod.GET)
    public void exportFile(HttpServletRequest req, HttpServletResponse resp) {
        User user = cmsService.getUser(req);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("multipart/form-data");
        try (OutputStream out = resp.getOutputStream()) {
            String fileName = URLEncoder.encode("校准历史-" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".xls", "UTF-8");
            resp.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            ExcelUtils table = new ExcelUtils();
            table.setComp("公司名：", user.getOrg().getName());
            table.setUser("导出人：", user.getFullname());
            table.setDate("导出日期：", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm"));
            table.setFile("文件名称：", URLDecoder.decode(fileName, "UTF-8"));

            List<String> search = new ArrayList<>();
            List<String> header = new ArrayList<>();

            Page<CalibrationHist> page = new Page<>(Integer.MAX_VALUE);
            List<PropertyFilter> filters = new ArrayList<>();
            filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
            if (StringUtils.isNotBlank(req.getParameter("filter_GED_actualDate"))) {
                filters.add(new PropertyFilter("GED_actualDate", req.getParameter("filter_GED_actualDate")));
                search.add("实际校准时间：" + req.getParameter("filter_GED_actualDate"));
            }
            if (StringUtils.isNotBlank(req.getParameter("filter_LED_actualDate"))) {
                filters.add(new PropertyFilter("LED_actualDate", req.getParameter("filter_LED_actualDate")));
                search.add("实际校准时间：" + req.getParameter("filter_LED_actualDate"));
            }
            if (StringUtils.isNotBlank(req.getParameter("filter_EQS_taskCode"))) {
                filters.add(new PropertyFilter("EQS_taskCode", req.getParameter("filter_EQS_taskCode")));
                search.add("任务编号：" + req.getParameter("filter_EQS_taskCode"));
            }
            if (StringUtils.isNotBlank(req.getParameter("filter_EQS_equipmentCode"))) {
                filters.add(new PropertyFilter("EQS_equipmentCode", req.getParameter("filter_EQS_equipmentCode")));
                search.add("器具编号：" + req.getParameter("filter_EQS_equipmentCode"));
            }
            if (StringUtils.isNotBlank(req.getParameter("filter_EQS_calibrationResult"))) {
                String calibrationResult = req.getParameter("filter_EQS_calibrationResult");
                filters.add(new PropertyFilter("EQS_calibrationResult", calibrationResult));
                search.add("校准结果：" + (calibrationResult.equals("1") ? "合格" : "不合格"));
            }
            if (StringUtils.isNotBlank(req.getParameter("filter_EQI_calibrationMode"))) {
                String calibrationMode = req.getParameter("filter_EQI_calibrationMode");
                filters.add(new PropertyFilter("EQI_calibrationMode", calibrationMode));
                search.add("校准方式：" + (calibrationMode.equals("1") ? "内校" : calibrationMode.equals("2") ? "外校" : "临校"));
            }
            if (StringUtils.isNotBlank(req.getParameter("filter_EQI_calibrationStatus"))) {
                String calibrationStatus = req.getParameter("filter_EQI_calibrationStatus");
                filters.add(new PropertyFilter("EQI_calibrationStatus", calibrationStatus));
                search.add("校准状态：" + (calibrationStatus.equals("1") ? "正常" : "延期"));
            }

            calibrationHistService.findPage(page, filters);
            if (search.size() > 0) {
                table.setSearch(search);
            }
            header.add("任务编号");
            header.add("器具编号");
            header.add("器具名称");
            header.add("所在房间");
            header.add("待校准时间");
            header.add("校准方式");
            header.add("记录/证书编号");
            header.add("校准结果");
            header.add("实际校准时间");
            header.add("校准状态");
            header.add("备注");
            table.setHeader(header);
            if (page.getResult() != null && !page.getResult().isEmpty()) {
                List<List<String>> body = new ArrayList<>();
                for (CalibrationHist tmp : page.getResult()) {
                    Equipment equipment = tmp.getEquipment();
                    List<String> tmpList = new ArrayList<>();
                    tmpList.add(tmp.getTaskCode());
                    tmpList.add(equipment.getCode());
                    tmpList.add(equipment.getName());
                    tmpList.add(StringUtils.isBlank(equipment.getRoom()) ? "N.A." : equipment.getRoom());
                    tmpList.add(tmp.getExpectDate() == null ? "N.A." : DateFormatUtils.format(tmp.getExpectDate(), "yyyy-MM-dd"));
                    if (tmp.getCalibrationMode().intValue() == 1) {
                        tmpList.add("内校");
                    } else if (tmp.getCalibrationMode().intValue() == 1) {
                        tmpList.add("外校");
                    } else if (tmp.getCalibrationMode().intValue() == 1) {
                        tmpList.add("临校");
                    } else {
                        tmpList.add("N.A.");
                    }
                    tmpList.add(StringUtils.isBlank(tmp.getCertCode()) ? "N.A." : tmp.getCertCode());
                    tmpList.add(tmp.getCalibrationResult().equals("1") ? "合格" : "不合格");
                    tmpList.add(tmp.getActualDate() == null ? "N.A." : DateFormatUtils.format(tmp.getActualDate(), "yyyy-MM-dd"));
                    tmpList.add(tmp.getCalibrationStatus() == null ? "N.A." : tmp.getCalibrationStatus().intValue() == 1 ? "正常" : "延期");
                    tmpList.add(StringUtils.isBlank(tmp.getRemark()) ? "N.A." : tmp.getRemark());
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
