<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!doctype html>
<html lang="en">
<head>
    <title>Top</title>
    <%@ include file="/common/meta.jsp" %>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <link href="${ctx}/styles/css/index.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <div id="header">
        <div class="logo" style="margin-bottom: 25px;">
            <%--<img src="${ctx }/styles/images/snaker.png" alt="Snaker"/>--%>
                <img src="${ctx}/styles/images/logo_dyc.gif" alt="DYC" style="height: 80px;"/>
        </div>
        <ul class="qj">
            <li class="top_aqtc">
                <%--<span><shiro:principal/></span>--%>
                <a href="javascript:void(0)" onclick="window.parent.location.href='${ctx}/security/user/uppwd/${username}'" title="修改密码"><shiro:principal/></a>
            </li>
            <li class="top_aqtc">
                <a href="javascript:void(0)" onclick="window.parent.location.href='${ctx}/logout'">安全退出</a>
            </li>
        </ul>
    </div>
    <div id="admin-nav-wrap">
        <div class="admin-nav">
            <ul>
                <li>
                    <%--<a href="#">德亦诚全面质量管理系统</a>--%>
                </li>
            </ul>
        </div>
    </div>
</body>
</html>