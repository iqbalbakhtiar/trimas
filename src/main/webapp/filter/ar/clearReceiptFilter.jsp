<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 47%; height: 230px; left: 400px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="1" align="right">
		<tr>
			<td align="right" width="15%"><spring:message code="sirius.code"/></td>
			<td align="4%">:&nbsp;</td>
			<td align="45%"><input id="code" name="code" value="${filterCriteria.code}" size="30" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="receipt"/></td>
			<td>:&nbsp;</td>
			<td><input id="receipt" name="receipt" value="${filterCriteria.receipt}" size="30" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="customer"/></td>
			<td>:&nbsp;</td>
			<td><input id="customer" name="customer" value="${filterCriteria.customer}" size="30" class="inputbox"/></td>
		</tr>
		<tr>
            <td align="right"><spring:message code="sirius.datefrom"/></td>
            <td>:&nbsp;</td>
            <td>
                <input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='MM/dd/yyyy'/>" class="datepicker"/>
                &nbsp;<spring:message code="sirius.dateto"/> &nbsp;:&nbsp;
                <input id="dateTo" name="dateTo" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='MM/dd/yyyy'/>" class="datepicker"/>
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
