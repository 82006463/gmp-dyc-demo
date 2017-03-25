package com.zhanlu.framework.security.service;

import com.zhanlu.framework.common.service.CommonTreeService;
import com.zhanlu.framework.security.dao.OrgDao;
import com.zhanlu.framework.security.entity.Org;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 部门管理类
 *
 * @author yuqs
 * @since 0.1
 */
@Service
public class OrgService extends CommonTreeService<Org, Long> {

    @Autowired
    private OrgDao orgDao;

    @PostConstruct
    @Override
    public void initDao() {
        super.commonDao = orgDao;
    }

}
