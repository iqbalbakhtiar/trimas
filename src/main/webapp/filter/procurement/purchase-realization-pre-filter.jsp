<script type="text/javascript">
	function searchs()
	{
		var url = "<c:url value='/page/purchaseorderrealizationview.htm'/>";
		document.filterForm.action = url;
		document.filterForm.submit();
	}

	function resets()
	{
		document.getElementById("code").value = "";
		document.getElementById("supplier").value = "";
		document.getElementById("requisitioner").value = "";
		document.getElementById("dateFrom").value = "";
		document.getElementById("dateTo").value = "";

		var url = "<c:url value='/page/purchaseorderrealizationview.htm'/>";

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

		document.filterForm.action = "<c:url value='/page/purchaseorderrealizationview.htm?page='/>"+page;
		document.filterForm.submit();
	}

	function opensupplier()
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('Please select Organization first!');
			return;
		}

		openpopup("<c:url value='/page/popupsupplierview.htm?target=supplier&organization='/>"+org.value);
	}

	function openrequisitioner()
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('Please select Organization first!');
			return;
		}

		openpopup("<c:url value='/page/popuppurchaserequisitionview.htm?target=requisitioner&organization='/>"+org.value);
	}
</script>
<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 48%; height: 250px; left: 350px; display:none;" toggle="explode">
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="0" align="right">
		<tr>
			<td align="right">ID&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
        <tr>
			<td align="right" style="WIDTH:130px;">Company&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="310" height="28">
				<select id="org" name="organization" class="combobox-ext">
				</select>
            	&nbsp;
				<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');"  title="Company Structure" />
			</td>
		</tr>
        <tr>
			<td align="right">Supplier Name&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td>
            	<select id="supplier" name="supplier" class="combobox-ext">
				</select>
            	&nbsp;
				<a class="item-popup" onclick="opensupplier();"  title="Supplier" />
            </td>
		</tr>
        <tr>
			<td align="right">Requisitioner Name&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td>
            	<select id="requisitioner" name="requisitioner" class="combobox-ext">
				</select>
            	&nbsp;
				<a class="item-popup" onclick="openrequisitioner();"  title="Requisitioner" />
            </td>
		</tr>
		<tr>
            <td align="right">Date From &nbsp;&nbsp;</td>
            <td>:&nbsp;</td>
            <td>
                <input id="dateFrom" name="dateFrom" formatLength="long" dojoType="dropdowndatepicker" lang="en-us" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='MM/dd/yyyy'/>" displayFormat="dd-MM-yyyy" saveFormat="dd-MM-yyyy"/>
                &nbsp;Date To &nbsp;:&nbsp;
                <input id="dateTo" name="dateTo" formatLength="long" dojoType="dropdowndatepicker" lang="en-us" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='MM/dd/yyyy'/>" displayFormat="dd-MM-yyyy" saveFormat="dd-MM-yyyy"/>
            </td>
		</tr>
		<tr><td colspan="3">&nbsp;</td></tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td align="left">
				<input type="button" value="Search" style="WIDTH:60px; HEIGHT:25px" alt="Search" onClick="searchs();" class="btn"/>
				<input type="button" value="Reset"  style="WIDTH:60px; HEIGHT:25px" alt="Reset" onClick="resets();" class="btn"/>
			</td>
		</tr>
		</table>
	</form>
</div>
