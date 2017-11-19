package com.zhanlu.custom.cms.web;

import com.zhanlu.custom.cms.entity.CalibrationYear;
import com.zhanlu.custom.cms.entity.Equipment;
import com.zhanlu.custom.cms.service.CalibrationYearService;
import com.zhanlu.custom.cms.service.CmsService;
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
 * 年校
 */
@Controller
@RequestMapping(value = "/custom/cms/calibrationYear")
public class CalibrationYearController {

    @Autowired
    private CalibrationYearService calibrationYearService;
    @Autowired
    private CmsService cmsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<CalibrationYear> page, HttpServletRequest request) {
        User user = cmsService.getUser(request);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("expectDate");
            page.setOrder(Page.ASC);
        }
        page = calibrationYearService.findPage(page, filters);
        ModelAndView mv = new ModelAndView("cms/calibrationYearList");
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        CalibrationYear entity = calibrationYearService.findById(id);
        ModelAndView mv = new ModelAndView("cms/calibrationYearView");
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/exportFile", method = RequestMethod.GET)
    public void exportFile(HttpServletRequest req, HttpServletResponse resp) {
        User user = cmsService.getUser(req);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("multipart/form-data");
        try (OutputStream out = resp.getOutputStream()) {
            String fileName = URLEncoder.encode("年度校准计划-" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".xls", "UTF-8");
            resp.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            ExcelUtils table = new ExcelUtils();
            table.setComp("公司名：", user.getOrg().getName());
            table.setUser("导出人：", user.getFullname());
            table.setDate("导出日期：", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm"));
            table.setFile("文件名称：", URLDecoder.decode(fileName, "UTF-8"));

            List<String> search = new ArrayList<>();
            List<String> header = new ArrayList<>();

            Page<CalibrationYear> page = new Page<>(Integer.MAX_VALUE);
            List<PropertyFilter> filters = new ArrayList<>();
            filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
            if (StringUtils.isNotBlank(req.getParameter("filter_GED_expectDate"))) {
                filters.add(new PropertyFilter("GED_expectDate", req.getParameter("filter_GED_expectDate")));
                search.add("待校准日期：" + req.getParameter("filter_GED_expectDate"));
            }
            if (StringUtils.isNotBlank(req.getParameter("filter_LED_expectDate"))) {
                filters.add(new PropertyFilter("LED_expectDate", req.getParameter("filter_LED_expectDate")));
                search.add("待校准日期：" + req.getParameter("filter_LED_expectDate"));
            }
            calibrationYearService.findPage(page, filters);
            if (search.size() > 0) {
                table.setSearch(search);
            }
            header.add("器具编号");
            header.add("器具名称");
            header.add("所在房间");
            header.add("待校准日期");
            header.add("校准方式");
            table.setHeader(header);
            if (page.getResult() != null && !page.getResult().isEmpty()) {
                List<List<String>> body = new ArrayList<>();
                for (CalibrationYear tmp : page.getResult()) {
                    Equipment equipment = tmp.getEquipment();
                    List<String> tmpList = new ArrayList<>();
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
