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
    <%@ include file="/filter/accounting/bankAccountFilter.jsp"%>
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
                                    <a class="item-button-new" href="<c:url value='/page/bankaccountpreadd.htm' />"><span><spring:message code="bankaccount.new"/></span></a>
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
                          	<th width="10%"><spring:message code="bankaccount.code"/></th>
                          	<th width="12%"><spring:message code="bankaccount.bankname"/></th>
                          	<th width="10%"><spring:message code="bankaccount.branch"/></th>
                          	<th width="12%"><spring:message code="bankaccount.accountname"/></th>
                            <th width="12%"><spring:message code="bankaccount.accountno"/></th>
                          	<th width="45%"><spring:message code="bankaccount.holder"/></th>
                        </tr>
                        <c:forEach items="${accounts}" var="account">
                        <tr>
                            <td class="tools">
                                <a class="item-button-edit"  href="<c:url value='/page/bankaccountpreedit.htm?id=${account.id}'/>"  title="Edit"><span>Edit</span></a>
                                <c:if test='${empty account.partys}'>
                                	<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/bankaccountdelete.htm?id=${account.id}'/>', '<spring:message code="notif.delete"/>');" title="Delete"><span>Delete</span></a>
                                </c:if>
                            </td>
                            <td nowrap="nowrap"><c:out value="${account.code}" /></td> 
                            <td nowrap="nowrap"><c:out value="${account.bankName}" /></td>  
                            <td nowrap="nowrap"><c:out value="${account.bankBranch}" /></td>       
                            <td nowrap="nowrap"><c:out value="${account.accountName}" /></td>
                            <td nowrap="nowrap"><c:out value="${account.accountNo}" /></td>
                            <td nowrap="nowrap"><c:out value="${account.holder.fullName}" /></td>
                        </tr>
                        </c:forEach>
                        <tr class="end-table"><td colspan="7">&nbsp;</td></tr>
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
