package com.zhanlu.custom.dms.web;

import com.zhanlu.custom.common.service.CustomService;
import com.zhanlu.custom.dms.entity.DmsFile;
import com.zhanlu.custom.dms.service.DmsFileService;
import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.security.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 月度外校
 */
@Slf4j
@Controller
@RequestMapping(value = "/custom/dms/file/up")
public class FileUpController {

    @Autowired
    private DmsFileService dmsFileService;
    @Autowired
    private CustomService customService;
    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Value("${upload.file.path}")
    private String uploadPath;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Page<DmsFile> page, HttpServletRequest req) {
        User user = customService.getUser(req);
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
        filters.add(new PropertyFilter("EQL_tenantId", user.getOrg().getId().toString()));
        ModelAndView mv = new ModelAndView("dms/fileList");
        //设置默认排序方式
        if (!page.isOrderBySetted()) {
            page.setOrderBy("updateTime");
            page.setOrder(Page.DESC);
        }
        page = dmsFileService.findPage(page, filters);
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView mv = new ModelAndView("dms/fileEdit");
        DmsFile entity = new DmsFile();
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) {
        DmsFile entity = dmsFileService.findById(id);
        ModelAndView mv = new ModelAndView("dms/fileEdit");
        mv.addObject("entity", entity);
        return mv;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(DmsFile entity, @RequestParam(value = "files", required = false) CommonsMultipartFile[] files, HttpServletRequest req) {
        User user = customService.getUser(req);
        if (entity.getId() == null) {
            entity.setCreaterId(user.getId());
            entity.setCreateTime(new Date());
            entity.setTenantId(user.getOrg().getId());
        } else {
            entity.setUpdaterId(user.getId());
            entity.setUpdateTime(new Date());
        }
        if (files != null && files.length > 0) {
            CommonsMultipartFile file = files[0];
            entity.setFileName(file.getOriginalFilename());
            File dir = new File("/dyc/upload/file/dms");
            if (!dir.exists())
                dir.mkdirs();
            entity.setFilePath(dir.getAbsolutePath() + "/" + entity.getFileName());
            try {
                FileUtils.writeByteArrayToFile(new File(entity.getFilePath()), file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dmsFileService.saveOrUpdate(entity);
        ModelAndView mv = new ModelAndView("redirect:/custom/dms/file/up");
        return mv;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable("id") Long id) {
        DmsFile entity = dmsFileService.findById(id);
        entity.setStatus(-1);
        dmsFileService.saveOrUpdate(entity);
        ModelAndView mv = new ModelAndView("redirect:/custom/dms/file/up");
        return mv;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        DmsFile entity = dmsFileService.findById(id);
        ModelAndView mv = new ModelAndView("dms/fileView");
        mv.addObject("entity", entity);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public Object check(DmsFile entity, HttpServletRequest req) {
        User user = customService.getUser(req);
        StringBuilder sqlBuf = new StringBuilder("SELECT id FROM dms_file WHERE tenant_id=" + user.getOrg().getId());

        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("result", 1);
        List<Map<String, Object>> maps = null;
        if (entity.getId() == null || entity.getId().longValue() < 1) {
            if (!resultMap.containsKey("msg") && StringUtils.isNotBlank(entity.getCode())) {
                maps = jdbcTemplate.queryForList(sqlBuf.toString() + " AND code='" + entity.getCode() + "'");
                if (maps != null && maps.size() >= 1) {
                    resultMap.put("result", 0);
                    resultMap.put("msg", "文件编号【" + entity.getCode() + "】已存在");
                }
            }
        } else {
            DmsFile tmp = dmsFileService.findById(entity.getId());
            if (!resultMap.containsKey("msg") && StringUtils.isNotBlank(entity.getCode())) {
                maps = jdbcTemplate.queryForList(sqlBuf.toString() + " AND code='" + entity.getCode() + "'");
                if (maps != null && maps.size() >= (entity.getCode().equals(tmp.getCode()) ? 2 : 1)) {
                    resultMap.put("result", 0);
                    resultMap.put("msg", "文件编号【" + entity.getCode() + "】已存在");
                }
            }
        }
        return resultMap;
    }

}
