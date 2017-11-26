package com.zhanlu.custom.common.service;

import com.zhanlu.custom.common.dao.PrinterDao;
import com.zhanlu.custom.common.entity.Printer;
import com.zhanlu.framework.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 打印机
 */
@Service
public class PrinterService extends CommonService<Printer, Long> {

    @Autowired
    private PrinterDao printerDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = printerDao;
    }

}
