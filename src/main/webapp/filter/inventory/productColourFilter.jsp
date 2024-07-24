<div dojoType="FloatingPane" id="filter" title='<spring:message code="sirius.paging.filter"/>' constrainToContainer="true" style="width: 25%; height: 200px; left: 350px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
	<table width="100%" cellspacing="0" cellpadding="1" align="right">
	<tr>
		<td align="right" style="width: 60px"><spring:message code="productcolour.code"/></td>
		<td align="center">:&nbsp;</td>
		<td><input name="code" id="code" class="inputbox" value="${filterCriteria.code}"/></td>
	</tr>
	<tr>
		<td align="right"><spring:message code="productcolour.name"/></td>
		<td align="center">:&nbsp;</td>
		<td ><input type="text" id="name" name="name" class="inputbox" value="${filterCriteria.name}"/></td>
	</tr>
	<tr><td colspan="3">&nbsp;</td></tr>
	<tr>
		<td colspan="2">&nbsp;</td>
		<td align="left">
			<%@ include file="/common/filter.jsp"%>
		</td>
	</tr>
	</table>
	</form>
</div>
