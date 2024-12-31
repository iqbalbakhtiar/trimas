<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 550px; height: 320px; left: 400px; display:none;" toggle="explode" bg>
	<sesform:form id="filterForm" name="filterForm" method="post" modelAttribute="criteria">
		<table width="100%" cellspacing="0" cellpadding="0" align="right">
        <tr>
			<td align="right" style="WIDTH:130px;"><spring:message code="journalentry.organization"/>&nbsp;&nbsp;</td>
			<td width="4">:</td>
			<td width="310" height="28">
				<select id="orgto" name="organization" class="combobox-ext">
                </select>
				<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=orgto'/>');"  title="Company Structure" />
			</td>
		</tr>
		<tr>
			<td align="right" style="WIDTH:130px;"><spring:message code="journalentry.code"/>&nbsp;&nbsp;</td>
			<td width="4">:</td>
			<td width="411" height="28"><input name="code" value="${filterCriteria.code}" size="30" /></td>
		</tr>
		<tr>
			<td align="right" style="WIDTH:130px;"><spring:message code="journalentry.name"/>&nbsp;&nbsp;</td>
			<td width="4">:</td>
			<td width="411" height="28"><input name="name" value="${filterCriteria.name}" size="30"/></td>
		</tr>
		<tr>
			<td align="right" style="WIDTH:130px;"><spring:message code="journalentry.type"/>&nbsp;&nbsp;</td>
			<td width="4">:</td>
			<td width="411" height="28">
				<select id="entryType" name="entryType" class="combobox-min">
					<option value='${filterCriteria.entryType}'></option>
					<option value="AUTOMATIC">AUTOMATIC</option>
					<option value="STANDARD">STANDARD</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right" style="WIDTH:130px;"><spring:message code="accountingperiod"/>&nbsp;&nbsp;</td>
			<td width="4">:</td>
			<td width="411" height="28"><input name="period" value="${filterCriteria.period}" size="30" /></td>
		</tr>
		<tr>
			<td align="right" style="WIDTH:130px;"><spring:message code="sirius.createdby"/>&nbsp;&nbsp;</td>
			<td width="4">:</td>
			<td width="411" height="28"><input name="createdBy" value="${filterCriteria.createdBy}" size="30" /></td>
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
	</sesform:form>
</div>
<script type="text/javascript">		
	function searchs()
	{
		document.filterForm.action = "<c:url value='/page/journalBatchingList.htm'/>";
		document.filterForm.submit();
	}
	
	function resets()
	{
		window.location = "<c:url value='/page/journalBatchingListAll.htm'/>";
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
		
		document.filterForm.action = "<c:url value='/page/journalBatchingList.htm?page='/>"+page;
		document.filterForm.submit();
	}
</script>