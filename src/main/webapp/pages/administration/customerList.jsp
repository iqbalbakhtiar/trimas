<%@ include file="/common/sirius-general-top.jsp"%>
<%@ include file="/filter/customer/customerFilter.jsp"%>
<div class="item-navigator">
	<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
		<tr>
			<td width="35%" height="30" align="left" valign="middle">

				<div class="toolbar-clean">
					<c:if test='${access.add}'>
						<a class="item-button-new" href="<c:url value='/page/customerpreadd.htm' />"><span>Customer</span></a>
						<%-- <a class="item-button-new" href="<c:url value='/page/customergrouppreadd.htm' />"><span>Customer Group</span></a> --%>
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
			<th width="3%"><spring:message code="sirius.code"/></th>
			<th width="10%"><spring:message code="sirius.name"/></th>
			<th width="5%"><spring:message code="sirius.organization"/></th>
			<th width="5%"><spring:message code="sirius.status"/></th>
			<th width="5%"><spring:message code="sirius.type"/></th>
			<%-- <th width="5%"><spring:message code="customer.group"/></th> --%>
			<th width="10%"><spring:message code="sirius.note"/></th>
		</tr>
		<c:forEach items="${customers}" var="customer">
			<tr>
				<td class="tools">
					<c:if test='${access.edit}'>
						<c:url var="editUrl" value='/page/${customer.partyFrom.base ? "customergrouppreedit" : "customerpreedit"}.htm'>
							<c:param name="id" value="${customer.id}"/>
						</c:url>
						<a class="item-button-edit" href="${editUrl}" title="<spring:message code='sirius.edit'/>">
							<span><spring:message code="sirius.edit"/></span>
						</a>
					</c:if>
					<c:if test='${access.delete}'>
						<c:url var="deleteUrl" value='/page/${customer.partyFrom.base ? "customergroupdelete" : "customerdelete"}.htm'>
							<c:param name="id" value="${customer.id}"/>
						</c:url>
						<a class="item-button-delete" href="javascript:showDialog('${deleteUrl}');" title="<spring:message code='sirius.delete'/>">
							<span><spring:message code="sirius.delete"/></span>
						</a>
					</c:if>
				</td>
				<td nowrap="nowrap">${customer.partyFrom.code}</td>
				<td nowrap="nowrap">${customer.partyFrom.fullName}</td>
				<td nowrap="nowrap">${customer.partyTo.fullName}</td>
				<td nowrap="nowrap"><spring:message code="sirius.${customer.active ? 'active' : 'inactive'}"/></td>
				<td nowrap="nowrap"><spring:message code="${customer.partyFrom.base ? 'sirius.group' : 'customer'}"/></td>
					<%-- <td nowrap="nowrap">${customer.partyFrom.partyGroup.fullName}</td> --%>
				<td nowrap="nowrap">${customer.partyFrom.note}</td>
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