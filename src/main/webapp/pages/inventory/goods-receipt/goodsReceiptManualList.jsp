<%@ include file="/pages/includes/sirius-head.jsp"%>

<%@ include file="/filter/inventory/goodsReceiptManualFilter.jsp"%>
<div class="item-navigator">
	<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
	<tr>
		<td width="30%" height="30" align="left" valign="middle">
			<div class="toolbar-clean">
				<c:if test='${access.add}'>
					<a class="item-button-new" href="<c:url value='/page/goodsreceiptmanualpreadd.htm'/>"><span>New Goods Receipt Manual</span></a>
				</c:if>
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
	<th width="1%" nowrap="nowrap"><div style="width: 45px">&nbsp;</div></th>
	<th width="6%" nowrap="nowrap">Date</th>
	<th width="8%" nowrap="nowrap">ID</th>
	<th width="8%" nowrap="nowrap">Organization</th>
	<th width="8%" nowrap="nowrap">Facility</th>
	<th width="40%" nowrap="nowrap">Note</th>
</tr>
<c:forEach items="${receipts}" var="rec">
<tr valign="top">
	<td class="tools">
		<c:if test="${access.edit}">
			<a class="item-button-edit" href="<c:url value='/page/goodsreceiptmanualpreedit.htm?id=${rec.id}'/>"  title="Edit"><span>Edit</span></a>
		</c:if>
		<c:if test="${access.delete}">
			<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/goodsreceiptmanualdelete.htm?id=${rec.id}'/>');" title="Del"><span>Del</span></a>
		</c:if>
	</td>
	<td nowrap="nowrap"><fmt:formatDate value='${rec.date}' pattern='dd-MM-yyyy'/></td>
	<td nowrap="nowrap"><c:out value='${rec.code}'/></td>
	<td nowrap="nowrap"><c:out value='${rec.organization.fullName}'/></td>
	<td nowrap="nowrap"><c:out value='${rec.facility.name}'/></td>
	<td><c:out value='${rec.note}'/></td>
</tr>
</c:forEach>
<tr class="end-table"><td colspan="10">&nbsp;</td></tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
<tr>
	<td align="right"><%@ include file="/pages/includes/navigation.jsp"%></td>
</tr>
</table>

<%@ include file="/pages/includes/sirius-foot.jsp"%>
