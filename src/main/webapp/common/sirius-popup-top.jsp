<%--
  Created by IntelliJ IDEA.
  User: rama
  Date: 23/06/25
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
    <title>${title}</title>
    <%@ include file="/common/filterandpaging.jsp"%>
</head>

<body>
<div id="se-containers_pick">
    <div id="se-r00">
        <div id="se-r01a">&nbsp;</div>
        <div id="se-r03">&nbsp;</div>
    </div>
    <div id="r11">
        <div id="r12">
            <div id="r13">
                <div id="r14">
                    <div id="se-contents">