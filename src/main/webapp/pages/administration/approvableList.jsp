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
	<%@ include file="/filter/approvableFilter.jsp"%>
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
						<table width="100%" cellpadding="0" cellspacing="0" class="table-list">
						<tr>
							<th width="4%">&nbsp;</th>
			  	  	  	  	<th width="12%"><spring:message code="approval.code"/></th>
			  	  	  	  	<th width="10%"><spring:message code="approval.date"/></th>
               	  	  	  	<th width="10%"><spring:message code="approval.organization"/></th>
               	  	  	  	<th width="15%"><spring:message code="approval.type"/></th>
               	  	  	  	<th width="10%"><spring:message code="approval.approver"/></th>
               	  	  	  	<th width="5%"><spring:message code="approval.amount"/></th>
               	  	  	  	<th width="25%"><spring:message code="currencymanagement"/></th>
						</tr>
						<c:forEach items="${approvables}" var="app">
						<tr valign="top">
							<td class="tools">
								<c:if test='${access.edit}'>
								    <a class="item-button-edit" href="<c:url value='/page/${app.uri}?id=${app.reviewID}'/>"><span><spring:message code='sirius.edit'/></span></a>
								</c:if>
							</td>
							<td nowrap="nowrap"><c:out value='${app.code}'/></td>
							<td nowrap="nowrap"><fmt:formatDate value='${app.date}' pattern='dd-MM-yyyy'/></td> 
							<td nowrap="nowrap"><c:out value="${app.organization.fullName}"/></td>
							<td nowrap="nowrap"><spring:message code="approval.${app.approvableType.messageName}"/></td>
							<td nowrap="nowrap"><c:out value='${app.approver.fullName}'/></td>
							<td nowrap="nowrap"><fmt:formatNumber value="${app.amount}" pattern=',##0.00'/></td>
							
							<td nowrap="nowrap" ><c:out value='${app.currency.symbol}'/></td>
						</tr>
						</c:forEach>
						<tr class="end-table"><td colspan="8">&nbsp;</td></tr>
						</table>
						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
						<tr>
							<td width="70%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
						</tr>
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
