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
							<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
								<tr>
									<td width="35%" height="30" align="left" valign="middle">
									<div class="toolbar-clean">
										<div dojoType="Toggler" targetId="filter">
											<a class="item-button-list" href="<c:url value='/page/deliveryorderrealizationview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
											<a class="item-button-search" href="javascript:return false;"><span><spring:message code="sirius.paging.filter"/></span></a>
										</div>
									</div>
									</td>
									<td width="65%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
								</tr>
							</table>
					  	</div>
					  	<table class="table-list" cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<th width="3%">&nbsp;</th>
								<th width="5%"><spring:message code="deliveryorder"/></th>
								<th width="5%"><spring:message code="deliveryorder.do.date"/></th>
								<th width="20%"><spring:message code="deliveryorder.shippingaddress.lineitem"/></th>
								<th width="5%"><spring:message code="deliveryorder.amount"/></th>
							</tr>
							<c:forEach items="${deliveryOrders}" var="delivery">
								<tr>
									<td class="tools" valign="top">
										<c:if test='${access.add}'>
											<a class="item-button-edit" href="<c:url value='/page/deliveryorderrealizationpreadd2.htm?deliveryId=${delivery.id}'/>" title="<spring:message code='sirius.edit'/>"><span><spring:message code="sirius.edit"/></span></a>
										</c:if>
									</td>
									<td nowrap="nowrap" valign="top"><a href="<c:url value='/page/deliveryorderpreedit.htm?id=${delivery.id}'/>"><c:out value="${delivery.code}"></c:out></a></td>
									<td nowrap="nowrap" valign="top"><fmt:formatDate value='${delivery.date}' pattern='dd-MM-yyyy'/></td>
									<td nowrap="nowrap" valign="top">
										${delivery.shippingAddress.addressName}
										<ul style="margin: 0; padding-left: 15px; list-style-type: none;">
											<c:forEach items="${delivery.items}" var="item">
												<li>${item.deliveryReferenceItem.product.name}</li>
											</c:forEach>
										</ul>
									</td>
									<td nowrap="nowrap" valign="top">
										&nbsp;
										<c:forEach items="${delivery.items}" var="item">
										<c:if test="${item.deliveryItemType eq 'BASE'}">
											<br/>
											<fmt:formatNumber value='${item.deliveryReferenceItem.quantity}' pattern=',##0.00'/> ${item.deliveryReferenceItem.product.unitOfMeasure.measureId}
										</c:if>
										</c:forEach>
									</td>
								</tr>
							</c:forEach>
							<tr class="end-table"><td colspan="10">&nbsp;</td></tr>
						</table>
						<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
							<tr>
								<td width="65%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
  	<%@ include file="/common/sirius-footer.jsp"%>

<div style="display: none">
	<%@ include file="/common/dialog.jsp"%>
</div>
</div>
</body>
<!-- END OF BODY -->
</html>
