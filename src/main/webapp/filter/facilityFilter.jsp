<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 30%; height: 150px; left: 500px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="2" align="right">
		<tr>
			<td align="right" nowrap="nowrap"><spring:message code="facility.name"/></td>
			<td>:</td>
			<td nowrap="nowrap"><input type="text" id="name" name="name" value="${filterCriteria.name}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td colspan="3">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td align="left">
				<%@ include file="/common/filter.jsp"%>
			</td>
		</tr>
		</table>
	</form>
</div>
