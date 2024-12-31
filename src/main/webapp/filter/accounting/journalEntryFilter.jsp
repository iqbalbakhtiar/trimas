<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 35%; height: 280px; left: 400px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="0" align="right">
		<tr>
			<td align="right" style="WIDTH:130px;"><spring:message code="journalentry.organization"/>&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="310" height="28">
				<select id="orgto" name="organization" class="combobox-ext">
				<c:if test='${not empty filterCriteria.organization}'>
                    	<option value="${filterCriteria.organization}"></option>
                </c:if>
				</select>
				<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=orgto'/>');"  title="Company Structure" />
			</td>
		</tr>
		<tr>
			<td align="right" style="WIDTH:130px;"><spring:message code="journalentry.code"/>&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="411" height="28"><input type="text" id="entryNo" name="code" value="${filterCriteria.code}" size="30" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right" style="WIDTH:130px;"><spring:message code="journalentry.name"/>&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="411" height="28"><input type="text" id="name" name="name" maxlength="50" value="<c:out value='${filterCriteria.name}'/>" size="25" class="inputbox"/></td>
		</tr>
        <tr>
			<td align="right" style="WIDTH:130px;"><spring:message code="journalentry.type"/>&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="411" height="28">
            	<select name="entryType">
                	<option value="" selected>&nbsp;</option>
                    <option value="ENTRY" <c:if test="${filterCriteria.entryType == 'ENTRY'}">selected</c:if>>ENTRY</option>
                    <option value="ADJUSTMENT" <c:if test="${filterCriteria.entryType == 'ADJUSTMENT'}">selected</c:if>>ADJUSTMENT</option>
                    <option value="CORRECTION" <c:if test="${filterCriteria.entryType == 'CORRECTION'}">selected</c:if>>CORRECTION</option>
                </select>
            </td>
		</tr>
        <tr>
			<td align="right" style="WIDTH:130px;"><spring:message code="journalentry.source"/>&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="411" height="28">
            	<select name="entrySourceType">
                	<option value="" selected>&nbsp;</option>
					<option value="AUTOMATIC" ${not empty filterCriteria.entrySourceType and filterCriteria.entrySourceType eq 'AUTOMATIC' ? "selected" : ""}>AUTOMATIC</option>
					<option value="STANDARD" ${not empty filterCriteria.entrySourceType and filterCriteria.entrySourceType eq 'STANDARD' ? "selected" : ""}>STANDARD</option>
                </select>
            </td>
		</tr>
		<tr>
			<td align="right"><spring:message code="sirius.datefrom"/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td>
				<input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
				&nbsp;<spring:message code="sirius.dateto"/> &nbsp;:&nbsp;
				<input id="dateTo" name="dateTo" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
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
<script type="text/javascript">		
	function searchs()
	{
		var url = "<c:url value='/page/journalentryview.htm'/>";
			
		document.filterForm.action = url;
		document.filterForm.submit();
	}
		
	function resets()
	{
		window.location = "<c:url value='/page/journalentryview.htm'/>";
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

		document.filterForm.action = "<c:url value='/page/journalentryview.htm?page='/>"+page;
		document.filterForm.submit();
	}
</script>