package com.zhanlu.office;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel Tool
 */
public class ExcelUtils {

    //公司信息
    private String compKey;
    private String compVal;
    //人员信息
    private String userKey;
    private String userVal;
    //日期
    private String dateKey;
    private String dateVal;
    //页面
    private String fileKey;
    private String fileVal;
    //搜索条件
    private List<String> search;
    //表头
    private List<String> header = new ArrayList<>();
    //表体
    private List<List<String>> body = new ArrayList<>();

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

    public ExcelUtils setFile(String fileKey, String fileVal) {
        this.fileKey = fileKey;
        this.fileVal = fileVal;
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
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

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
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 1, 3));
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

        //页面
        if (StringUtils.isNotBlank(fileKey) && StringUtils.isNotBlank(fileVal)) {
            rowIndex++;
            row = sheet.createRow(rowIndex);
            cell = row.createCell(0);
            cell.setCellValue(fileKey);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 1, 2));
            cell = row.createCell(1);
            cell.setCellValue(fileVal);
        }

        //搜索条件
        if (search != null && search.size() > 0) {
            rowIndex++;
            rowIndex++;
            columnIndex = 0;
            row = sheet.createRow(rowIndex);
            for (int i = 0; i < search.size(); i++) {
                if (columnIndex == 0) {
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 1));
                } else if (columnIndex == 1) {
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 2, 3));
                }
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
        for (int i = 0; i < header.size(); i++) {
            sheet.setColumnWidth(i, 5120);
            String item = header.get(i);
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
