<div dojoType="FloatingPane" id="filter" title="<spring:message code='sirius.paging.filter'/>" constrainToContainer="true" style="width: 35%; height: 275px; left: 350px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" style='border:none;'>
		<tr>
			<td align="right"><spring:message code="sirius.code"/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td><input type="text" id="code" name="code" value="${filterCriteria.code}" size="30" class="inputbox"/></td>
		</tr>
<%-- 		<tr>
			<td align="right" style="WIDTH:130px;"><spring:message code='organization'/>&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="310" height="28">
				<select id="orgto" name="organization" class="combobox">
					<c:if test='${not empty filterCriteria.organization}'>
	                  	<option value="${filterCriteria.organization}">${org.fullName}</option>
	                </c:if>
				</select>
            	&nbsp;
				<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=orgto'/>');"  title="Company Structure" />
			</td>
		</tr> --%>
        <tr>
			<td align="right"><spring:message code='supplier'/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td><input type="text" id="supplier" name="supplier" value="${filterCriteria.supplier}" size="30" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="payment.payment.type"/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td>
				<select id="type" name="type" class="combobox">
					<option value="" ${empty filterCriteria.type ? 'selected' : ''}>ALL</option>
					<option value="CASH" ${filterCriteria.type == 'CASH' ? 'selected' : ''}>CASH</option>
					<option value="TRANSFER" ${filterCriteria.type == 'TRANSFER' ? 'selected' : ''}>TRANSFER</option>
				</select>
			</td>
		</tr>
		<tr>
            <td align="right"><spring:message code='sirius.datefrom'/>&nbsp;&nbsp;</td>
            <td>:&nbsp;</td>
            <td>
                <input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
                &nbsp;<spring:message code='sirius.dateto'/>&nbsp;:&nbsp;
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
<script type="text/javascript">
	function searchs()
	{
		var url = "<c:url value='/page/paymentview.htm'/>";
		document.filterForm.action = url;
		document.filterForm.submit();
	}
			
	function resets()
	{
		window.location = "<c:url value='/page/paymentview.htm'/>";
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
			
		document.filterForm.action = "<c:url value='/page/paymentview.htm?page='/>"+page;
		document.filterForm.submit();
	}
</script>	