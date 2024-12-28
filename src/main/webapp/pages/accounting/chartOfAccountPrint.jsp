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
	<%@ include file="/common/sirius-header.jsp"%>
	<style type="text/css" media="screen">
		@import url("<c:url value='/assets/sirius-print.css'/>");
	</style>
	<style type="text/css" media="print">
		@import url("<c:url value='/assets/sirius-print.css'/>");
	</style>
</head>
<body>

<div id="se-r00">
	<div id="se-r01">&nbsp;</div>
	<div id="se-r02">&nbsp;</div>
</div>

<div id="se-containers">

	<div class="area" dojoType="Container" id="quick_link_container">
		<%@ include file="/common/sirius-menu.jsp"%>
	</div>

	<div id="se-navigator">
        <div class="area" dojoType="Container" id="quick_link_container">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td width="60%">${breadcrumb}</td>
                <td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
            </tr>
            </table>
        </div>
	</div>

	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
						<div class="area" dojoType="Container" id="quick_link_container">						
                            <h1 class="page-title">${pageTitle}</h1>                            
                            <div class="toolbar">
                                <a class="item-button-back" href="<c:url value='/page/chartofaccountview.htm'/>"><span><spring:message code="sirius.back"/></span></a>
                                <a class="item-button-print" href="javascript:print();"><span><spring:message code="sirius.print"/></span></a>
                            </div>
						</div>
						<div class="main-box">
                            <div class="main_container">
                                <table width="100%"  border="0" class="CSS1" bgcolor="white">
                                <thead>
                                <tr>
                                	<th width="29%" align="left"><spring:message code="coa.code"/></th>
                                    <th width="48%" align="left"><spring:message code="coa.name"/></th>
                                    <th width="12%" align="center"><spring:message code="glaccount.postingtype"/></th>
                                    <th width="11%" align="center"><spring:message code="glaccount.closingtype"/></th>
                                </tr>
                                <tr>
                                	<th colspan="4"><hr/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items='${COA.acounts}' var='account'>
                                <tr>
                                	<td>
                                    	<c:if test='${not empty account.childrens}'><strong></c:if>
                                    		<c:out value='${account.code}'/>
                                        <c:if test='${not empty account.childrens}'></strong></c:if>
                                    </td>
                                    <td>
                                    	<c:if test='${not empty account.childrens}'><strong></c:if>
                                        	<c:out value='${account.name}'/>
                                        <c:if test='${not empty account.childrens}'></strong></c:if>
                                    </td>
                                    <td align="center"><c:if test='${empty account.childrens}'><c:out value='${account.postingType}'/></c:if></td>
                                    <td align="center"><c:if test='${empty account.childrens}'><c:out value='${account.closingType}'/></c:if></td>
                                </tr>
                                <c:forEach items='${account.childrens}' var='account2'>
                                <tr>
                                	<td>
                                    	<c:if test='${not empty account2.childrens}'><strong></c:if>
                                    		&nbsp;<c:out value='${account2.code}'/>
                                        <c:if test='${not empty account2.childrens}'></strong></c:if>
                                    </td>
                                    <td>
                                    	<c:if test='${not empty account2.childrens}'><strong></c:if>
                                        	&nbsp;<c:out value='${account2.name}'/>
                                        <c:if test='${not empty account2.childrens}'></strong></c:if>
                                    </td>
                                    <td align="center"><c:if test='${empty account2.childrens}'><c:out value='${account2.postingType}'/></c:if></td>
                                    <td align="center"><c:if test='${empty account2.childrens}'><c:out value='${account2.closingType}'/></c:if></td>
                                </tr>
                                <c:forEach items='${account2.childrens}' var='account3'>
                                <tr>
                                	<td>
                                    	<c:if test='${not empty account3.childrens}'><strong></c:if>
                                    		&nbsp;&nbsp;<c:out value='${account3.code}'/>
                                        <c:if test='${not empty account3.childrens}'></strong></c:if>
                                    </td>
                                    <td>
                                    	<c:if test='${not empty account3.childrens}'><strong></c:if>
                                        	&nbsp;&nbsp;<c:out value='${account3.name}'/>
                                        <c:if test='${not empty account3.childrens}'></strong></c:if>
                                    </td>
                                    <td align="center"><c:if test='${empty account3.childrens}'><c:out value='${account3.postingType}'/></c:if></td>
                                    <td align="center"><c:if test='${empty account3.childrens}'><c:out value='${account3.closingType}'/></c:if></td>
                                </tr>
                                <c:forEach items='${account3.childrens}' var='account4'>
                                <tr>
                                	<td>
                                    	<c:if test='${not empty account4.childrens}'><strong></c:if>
                                    		&nbsp;&nbsp;&nbsp;<c:out value='${account4.code}'/>
                                        <c:if test='${not empty account4.childrens}'></strong></c:if>
                                    </td>
                                    <td>
                                    	<c:if test='${not empty account4.childrens}'><strong></c:if>
                                        	&nbsp;&nbsp;&nbsp;<c:out value='${account4.name}'/>
                                        <c:if test='${not empty account4.childrens}'></strong></c:if>
                                    </td>
                                    <td align="center"><c:if test='${empty account4.childrens}'><c:out value='${account4.postingType}'/></c:if></td>
                                    <td align="center"><c:if test='${empty account4.childrens}'><c:out value='${account4.closingType}'/></c:if></td>
                                </tr>
                                <c:forEach items='${account4.childrens}' var='account5'>
                                <tr>
                                	<td>
                                    	<c:if test='${not empty account5.childrens}'><strong></c:if>
                                    		&nbsp;&nbsp;&nbsp;&nbsp;<c:out value='${account5.code}'/>
                                        <c:if test='${not empty account5.childrens}'></strong></c:if>
                                    </td>
                                    <td>
                                    	<c:if test='${not empty account5.childrens}'><strong></c:if>
                                        	&nbsp;&nbsp;&nbsp;&nbsp;<c:out value='${account5.name}'/>
                                        <c:if test='${not empty account5.childrens}'></strong></c:if>
                                    </td>
                                    <td align="center"><c:if test='${empty account5.childrens}'><c:out value='${account5.postingType}'/></c:if></td>
                                    <td align="center"><c:if test='${empty account5.childrens}'><c:out value='${account5.closingType}'/></c:if></td>
                                </tr>
                                <c:forEach items='${account5.childrens}' var='account6'>
                                <tr>
                                	<td>
                                    	<c:if test='${not empty account6.childrens}'><strong></c:if>
                                    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value='${account6.code}'/>
                                        <c:if test='${not empty account6.childrens}'></strong></c:if>
                                    </td>
                                    <td>
                                    	<c:if test='${not empty account6.childrens}'><strong></c:if>
                                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value='${account6.name}'/>
                                        <c:if test='${not empty account6.childrens}'></strong></c:if>
                                    </td>
                                    <td align="center"><c:if test='${empty account6.childrens}'><c:out value='${account6.postingType}'/></c:if></td>
                                    <td align="center"><c:if test='${empty account6.childrens}'><c:out value='${account6.closingType}'/></c:if></td>
                                </tr>
                                <c:forEach items='${account6.childrens}' var='account7'>
                                <tr>
                                	<td>
                                    	<c:if test='${not empty account7.childrens}'><strong></c:if>
                                    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value='${account7.code}'/>
                                        <c:if test='${not empty account7.childrens}'></strong></c:if>
                                    </td>
                                    <td>
                                    	<c:if test='${not empty account7.childrens}'><strong></c:if>
                                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value='${account7.name}'/>
                                        <c:if test='${not empty account7.childrens}'></strong></c:if>
                                    </td>
                                    <td align="center"><c:if test='${empty account7.childrens}'><c:out value='${account7.postingType}'/></c:if></td>
                                    <td align="center"><c:if test='${empty account7.childrens}'><c:out value='${account7.closingType}'/></c:if></td>
                                </tr>
                                <c:forEach items='${account7.childrens}' var='account8'>
                                <tr>
                                	<td>
                                    	<c:if test='${not empty account8.childrens}'><strong></c:if>
                                    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value='${account8.code}'/>
                                        <c:if test='${not empty account8.childrens}'></strong></c:if>
                                    </td>
                                    <td>
                                    	<c:if test='${not empty account8.childrens}'><strong></c:if>
                                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value='${account8.name}'/>
                                        <c:if test='${not empty account8.childrens}'></strong></c:if>
                                    </td>
                                    <td align="center"><c:if test='${empty account8.childrens}'><c:out value='${account8.postingType}'/></c:if></td>
                                    <td align="center"><c:if test='${empty account8.childrens}'><c:out value='${account8.closingType}'/></c:if></td>
                                </tr>
                                <c:forEach items='${account8.childrens}' var='account9'>
                                <tr>
                                	<td>
                                    	<c:if test='${not empty account9.childrens}'><strong></c:if>
                                    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value='${account9.code}'/>
                                        <c:if test='${not empty account9.childrens}'></strong></c:if>
                                    </td>
                                    <td>
                                    	<c:if test='${not empty account9.childrens}'><strong></c:if>
                                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value='${account9.name}'/>
                                        <c:if test='${not empty account9.childrens}'></strong></c:if>
                                    </td>
                                    <td align="center"><c:if test='${empty account9.childrens}'><c:out value='${account9.postingType}'/></c:if></td>
                                    <td align="center"><c:if test='${empty account9.childrens}'><c:out value='${account9.closingType}'/></c:if></td>
                                </tr>
                                <c:forEach items='${account9.childrens}' var='account10'>
                                <tr>
                                	<td>
                                    	<c:if test='${not empty account10.childrens}'><strong></c:if>
                                    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value='${account10.code}'/>
                                        <c:if test='${not empty account10.childrens}'></strong></c:if>
                                    </td>
                                    <td>
                                    	<c:if test='${not empty account10.childrens}'><strong></c:if>
                                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value='${account10.name}'/>
                                        <c:if test='${not empty account10.childrens}'></strong></c:if>
                                    </td>
                                    <td align="center"><c:if test='${empty account10.childrens}'><c:out value='${account10.postingType}'/></c:if></td>
                                    <td align="center"><c:if test='${empty account10.childrens}'><c:out value='${account10.closingType}'/></c:if></td>
                                </tr>
                                </c:forEach>
                                </c:forEach>
                                </c:forEach>
                                </c:forEach>
                                </c:forEach>
                                </c:forEach>
                                </c:forEach>
                                </c:forEach>
                                </c:forEach>
                                </c:forEach>
                                </tbody>
                                </table>
                            </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
  	<%@ include file="/common/sirius-footer.jsp"%>

</div>
</body>
</html>
