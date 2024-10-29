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
<%--    <%@ include file="/filter/accounting/billingFilter.jsp"%>--%>
</head>
<body>

<div id="se-r00">
	<div id="se-r01">&nbsp;</div>
	<div id="se-r02">&nbsp;</div>
</div>

<div id="se-containers">

	<%@ include file="/common/sirius-menu.jsp"%>
	
	<div id="se-navigator">	
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td width="60%">${breadcrumb}</td>
            <td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
        </tr>
        </table>
	</div>

	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">

						<h1 class="page-title">${pageTitle}</h1>
						
						<div class="clears">&nbsp;</div>
						<div class="item-navigator">
                        	<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
                            <tr>
                            <td width="30%" height="30" align="left" valign="middle">															
                                <div class="toolbar-clean">
                                    <div dojoType="Toggler" targetId="filter">
                                        <a class="item-button-search" href="javascript:return;"><span><spring:message code="sirius.paging.filter"/></span></a>
                                    </div>
                                </div>
                            </td>
                            <td width="70%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
                            </tr>
                        	</table>
					  	</div>
                        <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <th width="1%"><div style="width:45px;"></div></th>
                          	<th width="8%"><spring:message code="sirius.code"/></th>
                          	<th width="8%"><spring:message code="sirius.date"/></th>
                          	<th width="8%"><spring:message code="customer"/></th>
                          	<th width="8%"><spring:message code="sirius.status"/></th>
                          	<th width="8%"><spring:message code="sirius.note"/></th>
                        </tr>
                        <c:forEach items="${billings}" var="billing">
                        <tr>
                            <td class="tools">
                                <a class="item-button-edit"  href="<c:url value='/page/billingpreedit.htm?id=${billing.id}'/>"  title="Edit"><span>Edit</span></a>
                            </td>
                            <td nowrap="nowrap"><c:out value="${billing.code}" /></td>
                            <td nowrap="nowrap"><fmt:formatDate value='${billing.date}' pattern='dd-MM-yyyy'/></td>
							<td nowrap="nowrap"><c:out value="${billing.customer.fullName}" /></td>
							<td nowrap="nowrap"><c:out value="${billing.financialStatus}" /></td>
							<td nowrap="nowrap"><c:out value="${billing.note}" /></td>
                        </tr>
                        </c:forEach>
                        <tr class="end-table"><td colspan="8">&nbsp;</td></tr>
                        </table>
                        <table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
						<tr><td width="70%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td></tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
  	<%@ include file="/common/sirius-footer.jsp"%>

</div>
</body>
</html>
