<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 25%; height: 250px; left: 300px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="0" align="right">
		<tr>
			<td align="right" style="WIDTH:130px;"><spring:message code="sirius.code"/>&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="310" height="28"><input type="text" name="code" id="code" class="inputbox" value="<c:out value='${filterCriteria.code}'/>"/></td>
		</tr>
		<tr>
			<td align="right" style="WIDTH:130px;"><spring:message code="sirius.approver"/>&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="310" height="28"><input type="text" id="approverName" name="approverName" class="inputbox" value="<c:out value='${filterCriteria.approverName}'/>"/></td>
		</tr>
		<tr>
			<td align="right" style="WIDTH:130px;"><spring:message code="sirius.operator"/>&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="310" height="28"><input type="text" id="operatorName" name="operatorName" class="inputbox" value="<c:out value='${filterCriteria.operatorName}'/>"/></td>
		</tr>
		<tr>
	           <td align="right"><spring:message code="sirius.date"/> &nbsp;&nbsp;</td>
	           <td>:&nbsp;</td>
	           <td>
	               <input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
	               -
	               <input id="dateTo" name="dateTo" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
	           </td>
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
