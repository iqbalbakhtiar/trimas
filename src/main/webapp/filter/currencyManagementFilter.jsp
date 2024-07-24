<script type="text/javascript">
		function searchs()
		{
			document.filterForm.action = "<c:url value='/page/exchangemanagementview.htm'/>";
			document.filterForm.submit();
		}
		
		function resets()
		{
			var url = "<c:url value='/page/exchangemanagementview.htm'/>";
			window.location = url;
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
				
			var url = "<c:url value='/page/exchangemanagementview.htm?page='/>"+page;

			window.location = url;
		}			
</script>
<div dojoType="FloatingPane" id="filter" title='<spring:message code="sirius.paging.filter"/>' constrainToContainer="true" style="width: 35%; height: 250px; left: 350px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
	<table width="100%" cellspacing="0" cellpadding="1" align="right">
	<tr>
		<td align="right" style="WIDTH:130px;"><spring:message code="currencymanagement.from"/></td>
		<td align="center">:&nbsp;</td>
		<td>
			<select id="from" name="from">
				<option value="0"></option>
				<c:forEach items="${currencys}" var="currency">
					<option value="${currency.id}" ${currency.id == filterCriteria.from ? 'selected' : ''}><c:out value="${currency.symbol}"/></option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="currencymanagement.to"/></td>
		<td align="center">:&nbsp;</td>
		<td>
			<select id="to" name="to">
				<option value="0"></option>
				<c:forEach items="${currencys}" var="currency">
					<option value="${currency.id}" ${currency.id == filterCriteria.to ? 'selected' : ''}><c:out value="${currency.symbol}"/></option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="sirius.datefrom"/></td>
		<td align="center">:&nbsp;</td>
		<td>
			<input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
			<spring:message code="sirius.dateto"/>
			<input id="dateTo" name="dateTo" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
		</td>
	</tr>
	<tr>
		<td align="right"><spring:message code="currencymanagement.type"/></td>
		<td align="center">:&nbsp;</td>
		<td>
			<select id="type" name="type">
				<option value=""></option>
				<option value="SPOT" ${'SPOT' == filterCriteria.type ? 'selected' : ''}>SPOT</option>
				<option value="MIDDLE" ${'MIDDLE' == filterCriteria.type ? 'selected' : ''}>MIDDLE</option>
				<option value="TAX" ${'TAX' == filterCriteria.type ? 'selected' : ''}>TAX</option>
			</select>
        </td>
	</tr>
	<tr><td colspan="3">&nbsp;</td></tr>
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
