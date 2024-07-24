<script type="text/javascript">
	$("form").submit(function (e){
		e.preventDefault();
	})
	
	function searchFilter()
	{
		let url = "<c:url value='${url}'/>";
	
		document.filterForm.action = url;
		document.filterForm.submit();
	}
	
	function reloadFilter()
	{
		let uri = "<c:url value='${uri}'/>";
		window.location = uri;
	}
	
	function step(action)
	{
		if(action == "next")
			document.filterForm.elements["page"].value="${filterCriteria.nextPage}";
		else if(action == "prev")
			document.filterForm.elements["page"].value="${filterCriteria.prevPage}";
		else if(action == "last")
			document.filterForm.elements["page"].value="${filterCriteria.totalPage}";
	
		document.filterForm.action = "<c:url value='${uri}'/>";
		document.filterForm.submit();
	}
</script>
<input type="hidden" name="page" value="1"/>
<input type="submit" class="btn" style="WIDTH:60px; HEIGHT:25px" value='<spring:message code="sirius.search"/>' onclick="searchFilter();"/>
<input type="button" class="btn" style="WIDTH:60px; HEIGHT:25px" value='<spring:message code="sirius.reset"/>' onclick="reloadFilter();"/>