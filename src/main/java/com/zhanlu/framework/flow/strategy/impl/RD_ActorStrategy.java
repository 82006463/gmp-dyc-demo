package com.zhanlu.framework.flow.strategy.impl;

import com.zhanlu.framework.flow.strategy.ActorStrategy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("RD_ActorStrategy")
public class RD_ActorStrategy implements ActorStrategy {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Object> findActors(String operator, List<String> codes, Map<String, Object> flowArgs) {
        StringBuilder sqlBuf = new StringBuilder(SQL_RD);
        List<Object> paramList = new ArrayList<>(codes.size() * 2);

        sqlBuf.append("(");
        for (int i = 0; i < codes.size(); i++) {
            String itemVal = codes.get(i);
            String[] rdCodes = itemVal.split("\\&");
            if (i > 0) sqlBuf.append(" OR ");

            sqlBuf.append("(r.code=? AND d.code=?)");
            paramList.add(rdCodes[0]);
            paramList.add(rdCodes[1]);
        }
        sqlBuf.append(")");
        return jdbcTemplate.queryForList(sqlBuf.toString(), paramList.toArray(), Object.class);
    }

}
