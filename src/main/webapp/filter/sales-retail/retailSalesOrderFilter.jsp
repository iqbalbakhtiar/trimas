<script type="text/javascript">
	function searchs()
	{
		document.filterForm.action = "<c:url value='/page/retailsalesorderview.htm'/>";
		document.filterForm.submit();
	}
			
	function resets()
	{
		window.location = "<c:url value='/page/retailsalesorderview.htm'/>";
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
			
		document.filterForm.action = "<c:url value='/page/retailsalesorderview.htm?page='/>"+page;
		document.filterForm.submit();
	}
</script>	
<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 30%; height: 220px; left: 500px; display:none;" toggle="explode">
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" border="0" align="right">
		<tr>
			<td align="right" nowrap="nowrap"><spring:message code='retailsales.code'/></td>
			<td nowrap="nowrap">:&nbsp;</td>
			<td nowrap="nowrap"><input type="text" id="code" name="code" value="${filterCriteria.code}" class="inputbox-ext"/></td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap"><spring:message code='retailsales.customer'/></td>
			<td nowrap="nowrap">:&nbsp;</td>
			<td nowrap="nowrap"><input type="text" id="customerName" name="customerName" value="${filterCriteria.customerName}" class="inputbox-ext"/></td>
		</tr>
		<tr>
            <td align="right" nowrap="nowrap"><spring:message code='sirius.datefrom'/></td>
            <td nowrap="nowrap">:&nbsp;</td>
            <td nowrap="nowrap">
                <input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
            </td>
		</tr>
		<tr>
            <td align="right" nowrap="nowrap"><spring:message code='sirius.dateto'/></td>
            <td nowrap="nowrap">:&nbsp;</td>
            <td nowrap="nowrap">
                <input id="dateTo" name="dateTo" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
            </td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td align="left">
				<input type="button" value='<spring:message code="sirius.search"/>' style="WIDTH:60px; HEIGHT:25px" alt='<spring:message code="sirius.search"/>' onClick="searchs();" class="btn"/>
				<input type="button" value='<spring:message code="sirius.reset"/>'  style="WIDTH:60px; HEIGHT:25px" alt='<spring:message code="sirius.reset"/>' onClick="resets();" class="btn"/>
			</td>
		</tr>
		</table>
	</form>
</div>
