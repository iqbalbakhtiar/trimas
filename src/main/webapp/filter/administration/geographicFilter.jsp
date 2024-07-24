<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 25%; height: 180px; left: 250px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="2" align="right">
		<tr>
			<td align="right" nowrap="nowrap" style="width: 25%"><spring:message code="geographic.code"/></td>
			<td nowrap="nowrap" style="width: 1%">:</td>
			<td nowrap="nowrap" style="width: 75%"><input id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap"><spring:message code="geographic.name"/></td>
			<td nowrap="nowrap">:</td>
            <td nowrap="nowrap">
           		<input type="text" id="name" name="name" value="${filterCriteria.name}" size="35" class="inputbox"/>
            </td>
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
