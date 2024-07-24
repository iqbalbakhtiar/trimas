<div dojoType="FloatingPane" id="filter" title="<spring:message code='sirius.paging.filter'/>" constrainToContainer="true" style="width: 32%; height: 300px; left: 350px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" style='border:none;'>
		<tr>
			<td align="right"><spring:message code="sirius.code"/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td><input type="text" id="code" name="code" value="${filterCriteria.code}" size="30" class="inputbox"/></td>
		</tr>
        <tr>
			<td align="right"><spring:message code='customer'/> / <spring:message code='supplier'/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td><input type="text" id="customer" name="customer" value="${filterCriteria.customer}" size="30" class="inputbox"/></td>
		</tr>
        <tr>
			<td align="right"><spring:message code='sirius.reference'/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td><input type="text" id="reference" name="reference" value="${filterCriteria.reference}" size="30" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="sirius.paymentmethodtype"/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td>
				<select id="paymentMethodType" name="paymentMethodType" class="combobox">
					<option value="" ${empty filterCriteria.paymentMethodType ? 'selected' : ''}>ALL</option>
					<option value="CASH" ${filterCriteria.paymentMethodType == 'CASH' ? 'selected' : ''}>CASH</option>
					<option value="TRANSFER" ${filterCriteria.paymentMethodType == 'TRANSFER' ? 'selected' : ''}>TRANSFER</option>
				</select>
			</td>
		</tr>
		<tr>
            <td align="right"><spring:message code='sirius.datefrom'/>&nbsp;&nbsp;</td>
            <td>:&nbsp;</td>
            <td><input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>" class="datepicker"/></td>
		</tr>
		<tr>
            <td align="right"><spring:message code='sirius.dateto'/>&nbsp;&nbsp;</td>
            <td>:&nbsp;</td>
            <td><input id="dateTo" name="dateTo" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>" class="datepicker"/></td>
		</tr>
		<tr><td colspan="3">&nbsp;</td></tr>
		<tr>
			<td colspan="3" align="center">
				<%@ include file="/common/filter.jsp"%>
			</td>
		</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	function searchs()
	{
		var url = "<c:url value='/page/receiptmanualview.htm'/>";
		document.filterForm.action = url;
		document.filterForm.submit();
	}
			
	function resets()
	{
		window.location = "<c:url value='/page/receiptmanualview.htm'/>";
	}
			
	function step(action)
	{
		var page="1";
				
		if(action == "next")
			page="${filterCriteria.nextPage}";
		else if(action == "prev")
			page="${filterCriteria.prevPage}";
		else if(action == "last")
			page="${filterCriteria.totalPage}";					
			
		document.filterForm.action = "<c:url value='/page/receiptmanualview.htm?page='/>"+page;
		document.filterForm.submit();
	}
</script>	