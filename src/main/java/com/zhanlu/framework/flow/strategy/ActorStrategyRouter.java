package com.zhanlu.framework.flow.strategy;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 审批人员规则路由器
 */
@Component
public class ActorStrategyRouter {

    //private static final Logger LOGGER = Logger.getLogger(ActorStrategyRouter.class);

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 审批人员规则路由方法
     *
     * @param operator    当前审批人员
     * @param assigneeStr 规则字符串
     * @param flowArgs    流程变量
     * @return
     */
    public Set<String> findActors(String operator, String assigneeStr, Map<String, Object> flowArgs) {
        Set<String> resultSet = new HashSet<>();
        if (StringUtils.isEmpty(assigneeStr)) {
            return resultSet;
        }

        Map<String, List<String>> ruleMap = new HashMap<>();
        String[] rule1Arr = assigneeStr.split(",");
        for (String rule1Str : rule1Arr) {
            String[] rule2Arr = rule1Str.split(ActorStrategy.RULE_MID);
            String preName = rule2Arr[0];
            if (preName.equals("creator")) { // 创建者
                resultSet.add(flowArgs.get(preName).toString());
                continue;
            }

            if (rule2Arr.length > 2) {
                for (int i = 2; i < rule2Arr.length; i++) { //解决规则中有多个"_"字符的问题
                    rule2Arr[1] += "_" + rule2Arr[i];
                }
            }

            List<String> ruleList = ruleMap.get(preName) == null ? new ArrayList<String>() : ruleMap.get(preName);
            if (preName.equals(ActorStrategy.RULE_U)) {
                if (flowArgs.get(rule2Arr[1]) instanceof String[]) { //页面选择的用户
                    ruleList.addAll(Arrays.asList((String[]) flowArgs.get(rule2Arr[1])));
                } else {
                    ruleList.add(flowArgs.get(rule2Arr[1]).toString());
                }
            } else if (preName.equals(ActorStrategy.RULE_RD)) {
                String[] rdCodes = rule2Arr[1].split("\\&");
                rule2Arr[1] = rdCodes[0] + "&";
                if (rdCodes.length > 1 && flowArgs.containsKey(rdCodes[1])) //页面选择的部门
                    rule2Arr[1] += flowArgs.get(rdCodes[1]);
                else //当前部门
                    rule2Arr[1] += flowArgs.get("v_deptCode");
                ruleList.add(rule2Arr[1]);
            } else {
                ruleList.add(rule2Arr[1]);
            }
            ruleMap.put(preName, ruleList);
        }

        for (Map.Entry<String, List<String>> entry : ruleMap.entrySet()) {
            if (entry.getValue().isEmpty())
                continue;
            ActorStrategy actorService = applicationContext.getBean(entry.getKey() + "_ActorStrategy", ActorStrategy.class);
            List<Object> tmpList = actorService.findActors(operator, entry.getValue(), flowArgs);
            if (tmpList == null || tmpList.isEmpty())
                continue;
            for (Object tmp : tmpList)
                resultSet.add(tmp.toString());
        }
        return resultSet;
    }

}
