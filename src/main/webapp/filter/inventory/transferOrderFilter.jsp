<script type="text/javascript">
	function searchs()
	{
		var url = "<c:url value='/page/transferorderview.htm?mode=search'/>";
		document.filterForm.action = url;
		document.filterForm.submit();
	}
			
	function resets()
	{
		window.location = "<c:url value='/page/transferorderview.htm'/>";
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
			
		document.filterForm.action = "<c:url value='/page/transferorderview.htm?page='/>"+page;
		document.filterForm.submit();
	}
</script>	
<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 40%; height: 255px; left: 225px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="0" align="right">
		<tr>
			<td width="35%" align="right"><spring:message code="transferorder.code"/>&nbsp;&nbsp;</td>
			<td width="5%">:&nbsp;</td>
			<td width="60%"><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="transferorder.source"/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td>
				<select id="source" name="source" class="combobox">
					<c:if test='${not empty filterCriteria.source}'>
						<option value='${filterCriteria.source}' label='${source.name}' />
					</c:if>
				</select>
				<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupfacilityview.htm?target=source&active=true'/>');"  title="Facility From" />
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="transferorder.destination"/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td>
				<select id="destination" name="destination" class="combobox">
					<c:if test='${not empty filterCriteria.destination}'>
						<option value='${filterCriteria.destination}' label='${destination.name}' />
					</c:if>
				</select>
				<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupfacilityview.htm?target=destination&active=true'/>');"  title="Facility To" />
			</td>
		</tr>
		<%-- <tr>
			<td align="right"><spring:message code="goodsissue"/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td><input type="text" id="destination" name="goodsIssue" value="${filterCriteria.goodsIssue}" size="35" class="inputbox"/></td>
		</tr> --%>
		<tr>
			<td align="right"><spring:message code="sirius.createdby"/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td><input type="text" id="destination" name="createdBy" value="${filterCriteria.createdBy}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
            <td align="right"><spring:message code="sirius.datefrom"/>&nbsp;&nbsp;</td>
            <td>:&nbsp;</td>
            <td>
                <input id="dateFrom" name="dateFrom" dojoType="dropdowndatepicker" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='MM/dd/yyyy'/>" displayFormat="dd-MM-yyyy" saveFormat="dd-MM-yyyy"/>
            </td>
		</tr>
		<tr>
            <td align="right"><spring:message code="sirius.dateto"/>&nbsp;&nbsp;</td>
            <td>:&nbsp;</td>
            <td>
                <input id="dateTo" name="dateTo" dojoType="dropdowndatepicker" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='MM/dd/yyyy'/>" displayFormat="dd-MM-yyyy" saveFormat="dd-MM-yyyy"/>
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
