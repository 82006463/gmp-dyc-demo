package com.zhanlu.meta.web;

import com.zhanlu.framework.logic.MongoLogic;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * 报表Controller
 *
 * @author zhanlu
 * @date 2017-03-11
 * @since 0.1
 */
@Controller
@RequestMapping(value = "/file")
public class FileController {

    @Autowired
    private MongoLogic mongoLogic;

    /**
     * 分页列表
     */
    @RequestMapping(value = "download", method = RequestMethod.GET)
    public void download(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String fileName = req.getParameter("fileName");
        String filePath = req.getSession().getServletContext().getRealPath("/") + "/upload/";

        File file = new File(filePath + "/" + fileName);
        if (file.exists()) {
            resp.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            FileUtils.copyFile(file, resp.getOutputStream());
        }
    }

}
