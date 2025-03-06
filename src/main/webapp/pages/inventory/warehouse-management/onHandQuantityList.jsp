<%@ include file="/pages/includes/sirius-head.jsp"%>	

<%@ include file="/filter/inventory/onHandQuantityFilter.jsp"%>
<div class="item-navigator">
	<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
		<tr>
			<td width="30%" height="30" align="left" valign="middle">
				<div class="toolbar-clean">
					<div dojoType="Toggler" targetId="filter">
						<a class="item-button-search" href="javascript:return;"><span><spring:message code="sirius.paging.filter"/></span>
						</a>
					</div>
				</div></td>
			<td width="70%" align="right" height="20"><%@ include file="/pages/includes/navigation.jsp"%></td>
		</tr>
	</table>
</div>
<div style="overflow-x: auto;">
	<table style="width:110%;" cellpadding="0" cellspacing="0" class="table-list">
	<tr>
		<th width="5%">&nbsp;</th>
		<th width="10%"><spring:message code="product.code"/></th>
		<th width="25%" nowrap="nowrap"><spring:message code="product.name"/></th>
		<th width="22%" nowrap="nowrap"><spring:message code="product.category"/></th>
		<th width="10%" nowrap="nowrap"><spring:message code="product.onhand"/></th>
		<th width="10%" nowrap="nowrap"><spring:message code="product.available"/></th>
		<th width="10%" nowrap="nowrap"><spring:message code="product.reserved"/></th>
		<th width="6%" nowrap="nowrap"><spring:message code="product.uom"/></th>
	</tr>
	<c:forEach items="${onhands}" var="on">
		<tr>
			<td class="tools">
				<c:if test="${access.add}">
					<a class="item-button-edit" href="<c:url value='/page/onhandquantityeditview.htm?product=${on.product.id}'/>" title="Edit"><span><spring:message code="sirius.edit"/></span></a>
				</c:if>
			</td>
			<td nowrap="nowrap"><c:out value='${on.product.code}' /></td>
			<td nowrap="nowrap"><c:out value='${on.product.name}' /></td>
			<td nowrap="nowrap"><c:out value='${on.product.productCategory.name}' /></td>
			<td><fmt:formatNumber value='${on.onHand}' pattern=',##0.00' /></td>
			<td><fmt:formatNumber value='${on.availableSale}' pattern=',##0.00' /></td>
			<td><fmt:formatNumber value='${on.reserved}' pattern=',##0.00' /></td>
			<td><c:out value='${on.product.unitOfMeasure.measureId}' /></td>
		</tr>
	</c:forEach>
	<tr class="end-table">
		<td colspan="9">&nbsp;</td>
	</tr>
	</table>
</div>
<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
	<tr>
		<td align="right"><%@ include file="/pages/includes/navigation.jsp"%></td>
	</tr>
</table>

<%@ include file="/pages/includes/sirius-foot.jsp"%>