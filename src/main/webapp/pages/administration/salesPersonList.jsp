<%@ include file="/common/sirius-general-top.jsp"%>
<%@ include file="/filter/administration/employeeFilter.jsp"%>
<div class="item-navigator">
	<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
		<tr>
			<td width="35%" height="30" align="left" valign="middle">
				<div class="toolbar-clean">
					<c:if test='${access.add}'>
						<a class="item-button-new" href="<c:url value='/page/salespersonpreadd.htm'/>"><span><spring:message code="salesperson.new"/></span></a>
					</c:if>
					<div dojoType="Toggler" targetId="filter">
						<a class="item-button-search" href="javascript:return false;"><span><spring:message code="sirius.paging.filter"/></span></a>
					</div>
				</div>
			</td>
			<td width="65%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
		</tr>
	</table>
</div>
<div style="overflow-x: auto;">
	<table class="table-list" cellspacing="0" cellpadding="0" width="100%">
		<tr>
			<th width="3%">&nbsp;</th>
			<th width="10%"><spring:message code="sirius.code"/></th>
			<th width="15%"><spring:message code="sirius.name"/></th>
			<th width="12%"><spring:message code="organization"/></th>
			<th width="5%"><spring:message code="sirius.status"/></th>
			<th width="20%"><spring:message code="sirius.note"/></th>
		</tr>
		<c:forEach items="${persons}" var="emp">
			<tr>
				<td class="tools">
					<c:if test='${access.edit}'>
						<a class="item-button-edit" href="<c:url value='/page/salespersonpreedit.htm?id=${emp.id}'/>"  title="Edit"><span><spring:message code="sirius.edit"/></span></a>
					</c:if>
					<c:if test='${access.delete and emp.partyFrom.active}'>
						<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/salespersondelete.htm?id=${emp.id}'/>');" title="Delete"><span><spring:message code="sirius.delete"/></span></a>
					</c:if>
				</td>
				<td nowrap="nowrap">${emp.partyFrom.code}</td>
				<td nowrap="nowrap">${emp.partyFrom.fullName}</td>
				<td nowrap="nowrap">${emp.partyTo.fullName}</td>
				<td nowrap="nowrap">
					<c:if test='${emp.active}'><spring:message code="sirius.active"/></c:if>
					<c:if test='${!emp.active}'><div style="color: red;"><spring:message code="sirius.inactive"/></c:if>
				</td>
				<td>${emp.note}</td>
			</tr>
		</c:forEach>
		<tr class="end-table"><td colspan="6">&nbsp;</td></tr>
	</table>
</div>
<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
	<tr>
		<td align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
	</tr>
</table>

<%@ include file="/common/sirius-general-bottom.jsp"%>