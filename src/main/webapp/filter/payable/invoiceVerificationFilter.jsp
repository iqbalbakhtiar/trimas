<div dojoType="FloatingPane" id="filter" title="<spring:message code='sirius.paging.filter'/>" constrainToContainer="true" style="width: 30%; height: 270px; left: 350px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="0" align="right">
		<tr>
			<td width="180" align="right"><spring:message code='invoiceverification.code'/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code='supplier'/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td><input type="text" id="supplierName" name="supplierName" value="${filterCriteria.supplierName}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code='goodsreceipt'/>&nbsp;&nbsp;</td>
			<td>:</td>
			<td width="304" height="28"><input type="text" id="reference" name="reference" value="${filterCriteria.reference}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code='purchaseorder'/>&nbsp;&nbsp;</td>
			<td>:</td>
			<td width="304" height="28"><input type="text" id="purchaseOrderCode" name="purchaseOrderCode" value="${filterCriteria.purchaseOrderCode}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
            <td align="right"><spring:message code='sirius.datefrom'/>&nbsp;&nbsp;</td>
            <td>:&nbsp;</td>
            <td>
                <input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
            </td>
		</tr>
		<tr>
            <td align="right"><spring:message code='sirius.dateto'/>&nbsp;&nbsp;</td>
            <td>:&nbsp;</td>
            <td>
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
		var url = "<c:url value='/page/invoiceverificationview.htm'/>";
		document.filterForm.action = url;
		document.filterForm.submit();
	}
			
	function resets()
	{
		var url = "<c:url value='/page/invoiceverificationview.htm'/>";
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
			
		document.filterForm.action = "<c:url value='/page/invoiceverificationview.htm?page='/>"+page;
		document.filterForm.submit();
	}
</script>