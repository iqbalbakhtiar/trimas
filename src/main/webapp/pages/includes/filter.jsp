<input type="submit" value="Search" style="WIDTH:60px; HEIGHT:25px" alt="Search" onClick="searchs();" class="btn"/>
<input type="button" value="Reset"  style="WIDTH:60px; HEIGHT:25px" alt="Reset" onClick="resets();" class="btn"/>
<script type="text/javascript">
	$("form").submit(function (e){
		e.preventDefault();
	})

	var url=window.location.pathname;
	
	function searchs()
	{
		document.filterForm.action = url;
		document.filterForm.submit();
	}
			
	function resets()
	{			
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
			
		document.filterForm.action = url+"?page="+page;
		document.filterForm.submit();
	}
</script>