package com.zhanlu.framework.flow.strategy.impl;

import com.zhanlu.framework.flow.strategy.ActorStrategy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("R_ActorStrategy")
public class R_ActorStrategy implements ActorStrategy {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> findActors(String operator, List<String> codes, Map<String, Object> flowArgs) {
        StringBuilder sqlBuf = new StringBuilder(SQL_R);
        List<Object> paramList = new ArrayList<>(codes.size());

        sqlBuf.append("(");
        for (int i = 0; i < codes.size(); i++) {
            if (i > 0) sqlBuf.append(" OR ");
            sqlBuf.append("r.code=?");
            paramList.add(codes.get(i));
        }
        sqlBuf.append(")");
        return jdbcTemplate.queryForList(sqlBuf.toString(), paramList.toArray());
    }
}
