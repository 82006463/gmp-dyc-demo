package com.zhanlu.framework.flow.strategy;

import java.util.List;
import java.util.Map;

/**
 * 流程审批人员解析策略
 * <p>
 * R_角色编号：审批角色
 * RD_角色编程&&部门编号(不带部门编号默认为本部门)
 * D_部门编号：指定部门下的人员
 * DM_部门层级：本部门对应的指定层级下的部门负责人/DM_owner：当前部门负责人/DM_up：上级部门负责人
 * U_节点名称：审批页面选择的用户
 */
public interface ActorStrategy {

    String RULE_MID = "_"; //中间分隔符

    //参与流程审批角色字符串前缀、SQL语句
    String RULE_U = "U"; //用户
    String RULE_R = "R"; //角色
    String RULE_D = "D"; //部门
    String RULE_RD = "RD"; //角色+部门
    String SQL_U = "SELECT u.username FROM sec_user u WHERE 1=1 AND ";
    String SQL_R = "SELECT u.username FROM sec_user u,sec_role r,sec_role_user ru WHERE u.id=ru.user_id AND r.id=ru.role_id AND ";
    String SQL_D = "SELECT u.username FROM sec_user u,sec_org d WHERE u.org=d.id AND ";
    String SQL_RD = "SELECT u.username FROM sec_user u,sec_role r,sec_role_user ru,sec_org d WHERE u.id=ru.user_id AND r.id=ru.role_id AND u.org=d.id AND ";

    /**
     * 查找节点审批人员
     *
     * @param operator 当前用户
     * @param codes    多个Code
     * @param flowArgs 流程变量
     * @return
     */
    List<Object> findActors(String operator, List<String> codes, Map<String, Object> flowArgs);

}
