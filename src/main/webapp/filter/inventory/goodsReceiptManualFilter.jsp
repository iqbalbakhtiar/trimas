<script type="text/javascript">
	function searchs()
	{
		var url = "<c:url value='/page/goodsreceiptmanualview.htm'/>";
		
		document.filterForm.action = url;
		document.filterForm.submit();
	}
			
	function resets()
	{
		document.getElementById("code").value = "";
		document.getElementById("reference").value = "";
				
		var url = "<c:url value='/page/goodsreceiptmanualview.htm'/>";
				
		document.filterForm.action = url;
		document.filterForm.submit();
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
			
		document.filterForm.action = "<c:url value='/page/goodsreceiptmanualview.htm?page='/>"+page;
		document.filterForm.submit();
	}
</script>	
<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 40%; height: 230px; left: 225px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="0" align="right">
		<tr>
			<td width="35%" align="right"><spring:message code="goodsreceipt.code"/>&nbsp;&nbsp;</td>
			<td width="5%">:&nbsp;</td>
			<td width="60%"><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="goodsreceipt.source"/>&nbsp;&nbsp;</td>
			<td>:</td>
			<td><input type="text" id="source" name="source" value="${filterCriteria.source}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="goodsreceipt.reference"/>&nbsp;&nbsp;</td>
			<td>:</td>
			<td><input type="text" id="reference" name="reference" value="${filterCriteria.reference}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
            <td align="right"><spring:message code="sirius.datefrom"/>&nbsp;&nbsp;</td>
            <td>:&nbsp;</td>
            <td>
                <input id="dateFrom" name="dateFrom" formatLength="long" dojoType="dropdowndatepicker" lang="en-us" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='MM/dd/yyyy'/>" displayFormat="dd-MM-yyyy" saveFormat="dd-MM-yyyy"/>
                &nbsp;<spring:message code="sirius.dateto"/>&nbsp;:&nbsp;
                <input id="dateTo" name="dateTo" formatLength="long" dojoType="dropdowndatepicker" lang="en-us" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='MM/dd/yyyy'/>" displayFormat="dd-MM-yyyy" saveFormat="dd-MM-yyyy"/>
            </td>
		</tr>
		<tr><td colspan="3">&nbsp;</td></tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td align="left">
				<input type="button" value='<spring:message code="sirius.search"/>' style="WIDTH:60px; HEIGHT:25px" alt="Search" onClick="searchs();" class="btn"/>
				<input type="button" value='<spring:message code="sirius.reset"/>'  style="WIDTH:60px; HEIGHT:25px" alt="Reset" onClick="resets();" class="btn"/>
			</td>
		</tr>
		</table>
	</form>
</div>
