<script type="text/javascript">
	function searchs()
	{
		var url = "<c:url value='/page/purchaserequestview.htm'/>";
		document.filterForm.action = url;
		document.filterForm.submit();
	}
			
	function resets()
	{
		var url = "<c:url value='/page/purchaserequestview.htm'/>";
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
			
		document.filterForm.action = "<c:url value='/page/purchaserequestview.htm?page='/>"+page;
		document.filterForm.submit();
	}
</script>	
<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 35%; height: 200px; left: 350px; display:none;" toggle="explode">
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="2" cellpadding="0" align="right">
		<tr>
			<td align="right"><spring:message code="purchaserequest.code"/> :</td>
			<td><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
        <%-- <tr>
			<td align="right">Requisitioner Name&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td>
				<input type="text" id="requisitioner" name="requisitioner" value="${filterCriteria.requisitioner}" size="35" class="inputbox"/>
            </td>
		</tr> --%>
		<tr>
            <td align="right"><spring:message code='sirius.datefrom'/> :</td>
            <td>
            	<input id="dateFrom" name="dateFrom" class="datepicker" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>"/>
            </td>
		</tr>
		<tr>
            <td align="right"><spring:message code='sirius.dateto'/> :</td>
            <td>
                <input id="dateTo" name="dateTo" class="datepicker" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>"/>
            </td>
		</tr>
		<!--<tr>
			<td align="right"><spring:message code="product.group"/></td>
			<td align="center">:&nbsp;</td>
			<td ><input type="text" id="groupFeature" name="groupFeature" class="inputbox" value="${filterCriteria.groupFeature}"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="product.variety"/></td>
			<td align="center">:&nbsp;</td>
			<td ><input type="text" id="varietyFeature" name="varietyFeature" class="inputbox" value="${filterCriteria.varietyFeature}"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="product.type"/></td>
			<td align="center">:&nbsp;</td>
			<td ><input type="text" id="typeFeature" name="typeFeature" class="inputbox" value="${filterCriteria.typeFeature}"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="product.brand"/></td>
			<td align="center">:&nbsp;</td>
			<td ><input type="text" id="brandFeature" name="brandFeature" class="inputbox" value="${filterCriteria.brandFeature}"/></td>
		</tr>-->
		<tr><td colspan="2">&nbsp;</td></tr>
		<tr>
			<td>&nbsp;</td>
			<td align="left">
				<input type="button" value='<spring:message code="sirius.search"/>' style="WIDTH:60px; HEIGHT:25px" alt="Search" onClick="searchs();" class="btn"/>
				<input type="button" value='<spring:message code="sirius.reset"/>'  style="WIDTH:60px; HEIGHT:25px" alt="Reset" onClick="resets();" class="btn"/>
			</td>
		</tr>
		</table>
	</form>
</div>
