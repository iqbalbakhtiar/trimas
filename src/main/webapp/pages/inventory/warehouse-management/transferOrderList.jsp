<%@ include file="/common/sirius-general-top.jsp"%>
<%@ include file="/filter/inventory/transferOrderFilter.jsp"%>					
<div class="item-navigator">
	<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
	<tr>
		<td width="60%" height="30" align="left" valign="middle">
			<div class="toolbar-clean">
				<c:if test='${access.add}'>
					<a class="item-button-new" href="<c:url value='/page/transferorderpreadd.htm'/>"><span><spring:message code="transferorder.new"/></span></a>
				</c:if>
				<div dojoType="Toggler" targetId="filter">
					<a class="item-button-search"><span><spring:message code="sirius.filter"/></span></a>
				</div>
			</div>
		</td>
		<td width="70%" align="right" height="20"><%@ include file="/pages/includes/navigation.jsp"%></td>
	</tr>
	</table>
</div>

<table width="100%" cellpadding="0" cellspacing="0" class="table-list">
	<tr>
		<th width="1%"><div style="width:40px"></div></th>
		<th width="20%"><spring:message code="sirius.code"/></th>
		<th width="8%"><spring:message code="sirius.date"/></th>
		<th width="15%"><spring:message code="organization"/></th>
		<th width="15%"><spring:message code="facility"/></th>
		<th width="50%"><spring:message code="sirius.note"/></th>
	</tr>
		<c:forEach items="${orders}" var="order">
			<tr>
				<td class="tools">
					<c:if test='${access.edit}'>
						<a class="item-button-edit" href="<c:url value='/page/transferorderpreedit.htm?id=${order.id}'/>" title="Edit"><span><spring:message code="sirius.edit"/></span></a>
					</c:if>
				</td>
				<td nowrap="nowrap"><c:out value="${order.code}"/></td>
				<td nowrap="nowrap"><fmt:formatDate value='${order.date}' pattern='dd-MM-yyyy'/></td> 
				<td nowrap="nowrap"><c:out value="${order.organization.fullName}"/></td>
				<td nowrap="nowrap"><c:out value="${order.source.name}"/></td>
				<td nowrap="nowrap"><c:out value="${order.note}"/></td>
			</tr>
		</c:forEach>
	<tr class="end-table"><td colspan="6">&nbsp;</td></tr>
</table>

<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
	<tr>
		<td align="right"><%@ include file="/pages/includes/navigation.jsp"%></td>
	</tr>
</table>
<%@ include file="/common/sirius-general-bottom.jsp"%>