<script type="text/javascript">
	function searchs()
	{
		var url = "<c:url value='/page/userview.htm'/>";
		document.filterForm.action = url;
		document.filterForm.submit();
	}
			
	function resets()
	{		
		var url = "<c:url value='/page/userview.htm'/>";
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
			
		document.filterForm.action = "<c:url value='/page/userview.htm?page='/>"+page;
		document.filterForm.submit();
	}
</script>	
<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 50%; height: 280px; left: 300px;display:none;   " toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="0" align="right">
		<tr>
			<td align="right" style="WIDTH:130px;"><spring:message code="user.id"/> &nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="411" height="28"><input type="text" id="name" name="name" value="${filterCriteria.name}" size="30" class="inputbox"/></td>
		</tr>
		<tr>
		  <td align="right" style="WIDTH:130px;"><spring:message code="user.name"/>&nbsp;</td>
		  <td>:</td>
		  <td height="28"><input type="text" id="code" name="code" value="${filterCriteria.code}" size="30" class="inputbox"/></td>
		  </tr>
		<tr>
			<td align="right" style="WIDTH:130px;"><spring:message code="user.role"/>&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="411" height="28">
				<input type="text" id="roleName" name="roleName" value="${filterCriteria.roleName}" size="30" class="inputbox"/>		</td>
		</tr>
		
		
		<tr><td colspan="3">&nbsp;</td></tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td align="left">
				<input type="button" value="<spring:message code='sirius.search'/>" style="WIDTH:60px; HEIGHT:25px" alt="Search" onClick="searchs();" class="btn"/>
				<input type="button" value="<spring:message code='sirius.reset'/>"  style="WIDTH:60px; HEIGHT:25px" alt="Reset" onClick="resets();" class="btn"/>			</td>
		</tr>
		</table>
	</form>
</div>
