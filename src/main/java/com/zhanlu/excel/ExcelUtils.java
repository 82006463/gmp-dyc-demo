package com.zhanlu.excel;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel Tool
 */
public class ExcelUtils {

    private static final ExcelUtils instance = new ExcelUtils();

    //公司信息
    private String compKey;
    private String compVal;
    //人员信息
    private String userKey;
    private String userVal;
    //日期
    private String dateKey;
    private String dateVal;
    //搜索条件
    private List<String> search;
    //表头
    private List<String> header = new ArrayList<>();
    //表体
    private List<List<String>> body = new ArrayList<>();

    public static ExcelUtils getInstance() {
        return instance;
    }

    public ExcelUtils setComp(String compKey, String compVal) {
        this.compKey = compKey;
        this.compVal = compVal;
        return this;
    }

    public ExcelUtils setUser(String userKey, String userVal) {
        this.userKey = userKey;
        this.userVal = userVal;
        return this;
    }

    public ExcelUtils setDate(String dateKey, String dateVal) {
        this.dateKey = dateKey;
        this.dateVal = dateVal;
        return this;
    }

    public ExcelUtils setSearch(List<String> search) {
        this.search = search;
        return this;
    }

    public ExcelUtils setHeader(List<String> header) {
        this.header = header;
        return this;
    }

    public ExcelUtils setBody(List<List<String>> body) {
        this.body = body;
        return this;
    }

    public ExcelUtils addRow(List<String> row) {
        body.add(row);
        return this;
    }

    public HSSFWorkbook build() {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        int rowIndex = 0;
        int columnIndex = 0;
        String cellItem = null;
        HSSFRow row = null;
        HSSFCell cell = null;

        //公司
        if (StringUtils.isNotBlank(compKey) && StringUtils.isNotBlank(compVal)) {
            row = sheet.createRow(rowIndex);
            cell = row.createCell(0);
            cell.setCellValue(compKey);
            cell = row.createCell(1);
            cell.setCellValue(compVal);
        }

        //人员
        if (StringUtils.isNotBlank(userKey) && StringUtils.isNotBlank(userVal)) {
            rowIndex++;
            row = sheet.createRow(rowIndex);
            cell = row.createCell(0);
            cell.setCellValue(userKey);
            cell = row.createCell(1);
            cell.setCellValue(userVal);
        }

        //日期
        if (StringUtils.isNotBlank(dateKey) && StringUtils.isNotBlank(dateVal)) {
            rowIndex++;
            row = sheet.createRow(rowIndex);
            cell = row.createCell(0);
            cell.setCellValue(dateKey);
            cell = row.createCell(1);
            cell.setCellValue(dateVal);
        }

        //搜索条件
        if (search != null && search.size() > 0) {
            rowIndex++;
            columnIndex = 0;
            row = sheet.createRow(rowIndex);
            for (int i = 0; i < search.size(); i++) {
                cellItem = search.get(i);
                cell = row.createCell(columnIndex);
                cell.setCellValue(cellItem);
                columnIndex++;
                if (columnIndex == 2) {
                    rowIndex++;
                    row = sheet.createRow(rowIndex);
                    columnIndex = 0;
                } else if (columnIndex == 1 && i == search.size() - 1) {
                    rowIndex++;
                }
            }
        } else {
            rowIndex++;
        }

        //表头
        rowIndex++;
        columnIndex = 0;
        row = sheet.createRow(rowIndex);
        for (String item : header) {
            cell = row.createCell(columnIndex);
            cell.setCellValue(item);
            columnIndex++;
        }

        //表体
        if (body != null && body.size() > 0) {
            for (List<String> rowItem : body) {
                rowIndex++;
                columnIndex = 0;
                row = sheet.createRow(rowIndex);
                for (String item : rowItem) {
                    cell = row.createCell(columnIndex);
                    cell.setCellValue(item);
                    columnIndex++;
                }
            }
        }

        return wb;
    }
}
