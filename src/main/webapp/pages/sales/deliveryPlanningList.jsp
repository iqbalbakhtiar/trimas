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
	<%@ include file="/filter/sales/deliveryPlanningFilter.jsp"%>

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
											<a class="item-button-search" href="javascript:return;"><span>Filter</span></a>
										</div>
									</div>
								</td>
								<td width="70%" align="right" height="20"><%@ include file="/pages/includes/navigation.jsp"%></td>
							</tr>
							</table>
					  	</div>

						<table width="100%" cellpadding="0" cellspacing="0" class="table-list">
						<tr>
							<th width="1%"><div style="width: 35px">&nbsp;</div></th>
							<th width="5%"><spring:message code="deliveryplanning.date"/></th>
			  	  	  	  	<th width="10%"><spring:message code="deliveryplanning.code"/></th>
							<th width="12%"><spring:message code="customer"/></th>
							<th width="10%"><spring:message code="deliveryplanning.salesorder"/></th>
							<th width="55%"><spring:message code="sirius.note"/></th>
						</tr>
						<c:forEach items="${plannings}" var="plan">
						<tr>
							<td class="tools">
								<c:if test='${access.edit}'>
									<a class="item-button-edit" href="<c:url value='/page/deliveryplanningpreedit.htm?id=${plan.id}'/>"  title="Edit"><span>Edit</span></a>
								</c:if>
							</td>
							<td nowrap="nowrap"><fmt:formatDate value='${plan.date}' pattern='dd-MM-yyyy'/></td>
							<td nowrap="nowrap">${plan.code}</td>
							<td nowrap="nowrap"><c:out value='${plan.salesOrder.customer.fullName}'/></td>
							<td nowrap="nowrap"><a href="<c:url value='/page/${plan.salesOrder.uri}?id=${plan.salesOrder.id}'/>"><c:out value='${plan.salesOrder.code}'/></a></td>
							<td nowrap="nowrap"><c:out value="${plan.salesOrder.note}"/></td>
						</tr>
						</c:forEach>
						<tr class="end-table"><td colspan="6">&nbsp;</td></tr>
						</table>
						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
						<tr>
							<td align="right"><%@ include file="/pages/includes/navigation.jsp"%></td>
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
