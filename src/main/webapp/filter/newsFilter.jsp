<script type="text/javascript" language="javascript">
	function searchs()
	{
		var url = "<c:url value='/page/newsView.htm'/>";
		document.filterForm.action = url;
		document.filterForm.submit();
	}
		
	function resets()
	{
		var url = "<c:url value='/page/newsView.htm'/>";
		window.location = url;
	}
			
	function step(action)
	{
		var page="1";
				
		if(action == "next")
			page="<c:out value='${data.filterCriteria.nextPage}'/>";
		else if(action == "prev")
			page="<c:out value='${data.filterCriteria.prevPage}'/>";
		else if(action == "last")
			page="<c:out value='${data.filterCriteria.totalPage}'/>";					
				
		var url = "<c:url value='/page/newsView.htm?mode=${data.mode}&page='/>"+page;
		document.filterForm.action = url;
		document.filterForm.submit();
	}
</script>
<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 40%; height: 200px; left: 400px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
	<table width="100%" cellspacing="0" cellpadding="0" align="right">
	<tr>
		<td width="364" align="right"><spring:message code="sirius.datefrom"/> :</td>
		<td width="364" height="28">
			<input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${data.filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
			<spring:message code="sirius.dateto"/> : 
			<input id="dateTo" name="dateTo" value="<fmt:formatDate value='${data.filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
		</td>
	</tr>
	<tr>
		<td width="200" align="right"><spring:message code="news.title"/> :</td>
		<td width="364" height="28"><input type="text" id="title" name="title" value="<c:out value='${data.filterCriteria.title}'/>" size="50"/></td>
	</tr>
	<tr>
		<td align="right"><spring:message code="sirius.createdby"/> :</td>
		<td width="364" height="28"><input type="text" id="postBy" name="postBy" value="<c:out value='${data.filterCriteria.postBy}'/>" size="30"/></td>
	</tr>
	<tr><td colspan="">&nbsp;</td></tr>
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
