package com.zhanlu.meta.web;

import com.zhanlu.framework.logic.MongoLogic;
import com.zhanlu.framework.nosql.item.QueryItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;

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

    @ResponseBody
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public Map<String, Object> getFile(String fileCode) {
        List<QueryItem> queryItems = new ArrayList<>(2);
        queryItems.add(new QueryItem("Eq_String_fileCode", fileCode));
        Map<String, Object> dmsFile = mongoLogic.findOne("meta_dms_file", queryItems);
        List<Map<String, Object>> dataList = new ArrayList<>();
        if (dmsFile != null && dmsFile.size() > 0) {
            for (Map.Entry<String, Object> entry : dmsFile.entrySet()) {
                if (entry.getKey().startsWith("v_") || entry.getKey().startsWith("wf_") || entry.getKey().equals("metaType") || entry.getKey().equals("cmcode"))
                    continue;
                Map<String, Object> item = new HashMap<>();
                item.put("key", entry.getKey());
                Object tmpVal = entry.getValue();
                if (entry.getValue() instanceof Date) {
                    tmpVal = DateFormatUtils.format((Date) entry.getValue(), "yyyy-MM-dd");
                } else if (entry.getValue() instanceof Timestamp) {
                    tmpVal = DateFormatUtils.format((Timestamp) entry.getValue(), "yyyy-MM-dd HH:mm:ss");
                } else if (entry.getValue() instanceof Object[]) {
                    tmpVal = "";
                }
                item.put("value", tmpVal);
                dataList.add(item);
            }
        }
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("size", dataList.size());
        resultMap.put("data", dataList);
        return resultMap;
    }

}
