package com.zhanlu.excel;

import org.apache.poi.hssf.usermodel.*;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/4.
 */
public class Table {

    private static final Table instance = new Table();

    public static Table getInstance() {
        return instance;
    }

    private Map<Integer, String> header = new LinkedHashMap<>();
    private List<Map<Integer, String>> body = new ArrayList<>();

    public Map<Integer, String> setHeader(Map<Integer, String> header) {
        this.header = header;
        return this.header;
    }

    public Map<Integer, String> addRow(Map<Integer, String> row) {
        body.add(row);
        return row;
    }

    public boolean build(OutputStream out) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        int rowIndex = 0;
        HSSFRow row = sheet.createRow(rowIndex);
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        int columnIndex = 0;
        for (Map.Entry<Integer, String> entry : header.entrySet()) {
            HSSFCell cell = row.createCell(columnIndex);
            cell.setCellValue(entry.getValue());
            columnIndex++;
        }
        for (Map<Integer, String> map : body) {
            row = sheet.createRow(++rowIndex);
            columnIndex = 0;
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                HSSFCell cell = row.createCell(columnIndex);
                cell.setCellValue(entry.getValue());
                columnIndex++;
            }
        }

        try {
            wb.write(out);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
