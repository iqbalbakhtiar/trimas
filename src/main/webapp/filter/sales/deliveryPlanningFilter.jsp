<div dojoType="FloatingPane" id="filter" title='<spring:message code="sirius.paging.filter"/>' constrainToContainer="true" style="width: 30%; height: 260px; left: 350px; display:none;" toggle="explode">
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="2" cellpadding="1" align="center">
		<tr>
			<td align="right"><spring:message code="deliveryplanning.code"/></td>
			<td width="4">:</td>
			<td><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="deliveryplanning.salesorder"/></td>
			<td>:</td>
			<td><input type="text" id="salesOrderCode" name="salesOrderCode" value="${filterCriteria.salesOrderCode}" size="35" class="inputbox"/></td>
		</tr>
        <tr>
			<td align="right"><spring:message code="deliveryplanning.customer"/></td>
			<td>:</td>
			<td><input type="text" id="customerName" name="customerName" value="${filterCriteria.customerName}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
            <td align="right"><spring:message code="sirius.datefrom"/></td>
            <td>:</td>
            <td><input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>" class="datepicker"/></td>
		</tr>
		<tr>
            <td align="right"><spring:message code="sirius.dateto"/></td>
            <td>:</td>
            <td><input id="dateTo" name="dateTo" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>" class="datepicker"/></td>
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