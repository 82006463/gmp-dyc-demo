package com.zhanlu.excel;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.*;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/4.
 */
public class Table {

    private static final Table instance = new Table();

    //公司信息
    private String compInfo;
    //搜索条件
    private List<String> search;
    //表头
    private List<String> header = new ArrayList<>();
    //表体
    private List<List<String>> body = new ArrayList<>();

    public static Table getInstance() {
        return instance;
    }

    public Table setCompInfo(String compInfo) {
        this.compInfo = compInfo;
        return this;
    }

    public Table setSearch(List<String> search) {
        this.search = search;
        return this;
    }

    public Table setHeader(List<String> header) {
        this.header = header;
        return this;
    }

    public Table setBody(List<List<String>> body) {
        this.body = body;
        return this;
    }

    public Table addRow(List<String> row) {
        body.add(row);
        return this;
    }

    public boolean build(OutputStream out) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        int rowIndex = 0;
        int columnIndex = 0;
        String cellItem = null;

        //公司
        HSSFRow row = sheet.createRow(rowIndex);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("公司名");
        cell = row.createCell(1);
        cell.setCellValue(compInfo);

        //搜索条件
        rowIndex++;
        columnIndex = 0;
        row = sheet.createRow(rowIndex);
        for (int i = 0; i < search.size(); i++) {
            cellItem = search.get(i);
            cell = row.createCell(columnIndex);
            cell.setCellValue(cellItem);
            columnIndex++;
            if (columnIndex == 2 && i < search.size() - 1) {
                rowIndex++;
                row.createCell(columnIndex);
                columnIndex = 0;
            }
        }

        //表头
        rowIndex++;
        rowIndex++;
        columnIndex = 0;
        row = sheet.createRow(rowIndex);
        for (String item : header) {
            cell = row.createCell(columnIndex);
            cell.setCellValue(item);
            columnIndex++;
        }

        //表体
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

        try {
            wb.write(out);
            IOUtils.closeQuietly(out);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
