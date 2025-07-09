<%@ include file="/common/sirius-general-top.jsp"%>
<%@ include file="/filter/accounting/billingBatchFilter.jsp"%>
<div class="item-navigator">
	<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
		<tr>
			<td width="30%" height="30" align="left" valign="middle">
				<div class="toolbar-clean">
					<c:if test='${access.add}'>
						<a class="item-button-new" href="<c:url value='/page/billingbatchpreadd.htm' />"><span><spring:message code="billing.batch.new"/></span></a>
					</c:if>
					<div dojoType="Toggler" targetId="filter">
						<a class="item-button-search" href="javascript:return;"><span><spring:message code="sirius.filter"/></span></a>
					</div>
				</div>
			</td>
			<td width="70%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
		</tr>
	</table>
</div>
<div style="overflow-x: auto;">
	<table class="table-list" cellspacing="0" cellpadding="0" width="100%">
		<tr>
			<th width="1%"><div style="width:45px;"></div></th>
			<th width="7%"><spring:message code="sirius.code"/></th>
			<th width="7%"><spring:message code="sirius.date"/></th>
			<th width="15%"><spring:message code="customer"/></th>
			<th width="10%"><spring:message code="sirius.total.amount"/></th>
			<th width="40%"><spring:message code="sirius.note"/></th>
		</tr>
		<c:forEach items="${batches}" var="com">
			<tr>
				<td class="tools">
					<a class="item-button-edit" href="<c:url value='/page/billingbatchpreedit.htm?id=${com.id}'/>"  title="Edit"><span>Edit</span></a>
				</td>
				<td nowrap="nowrap"><c:out value="${com.code}" /></td>
				<td nowrap="nowrap"><fmt:formatDate value='${com.date}' pattern='dd-MM-yyyy'/></td>
				<td nowrap="nowrap"><c:out value="${com.customer.fullName}" /></td>
				<td><fmt:formatNumber value='${com.amount}' pattern=',##0.00'/></td>
				<td class="break-over">${com.note}</td>
			</tr>
		</c:forEach>
		<tr class="end-table"><td colspan="8">&nbsp;</td></tr>
	</table>
</div>
<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
	<tr>
		<td align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
	</tr>
</table>

<%@ include file="/common/sirius-general-bottom.jsp"%>