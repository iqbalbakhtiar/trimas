<script type="text/javascript">
	function searchs()
	{
		var url = "<c:url value='/page/invoicefromsupplierview.htm'/>";
		document.filterForm.action = url;
		document.filterForm.submit();
	}

	function resets()
	{
		//document.getElementById("code").value = "";
		//document.getElementById("name").value = "";

		var url = "<c:url value='/page/invoicefromsupplierview.htm'/>";
		window.location = url;

		//document.filterForm.action = url;
		//document.filterForm.submit();
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

		document.filterForm.action = "<c:url value='/page/invoicefromsupplierview.htm?page='/>"+page;
		document.filterForm.submit();
	}
</script>
<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 50%; height: 280px; left: 425px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="0" align="right">
		<tr>
			<td align="right">Legend&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
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
			<td align="right">Item&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td>
            	<input type="textbox" name="itemAlias" value="${filterCriteria.itemAlias}"/>
            </td>
		</tr>
		<tr>
			<td align="right">Notes&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td>
            	<input type="textbox" name="notes" value="${filterCriteria.notes}"/>
            </td>
		</tr>
		<tr>
			<td align="right">No.Faktur Pajak&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td>
            	<input type="textbox" name="taxNumber" value="${filterCriteria.taxNumber}"/>
            </td>
		</tr>
		<tr>
			<td align="right">Status&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td>
            	<select name="status">
            		<option value="" ${filterCriteria.status==""?"selected":""}>ALL</option>
            		<option ${filterCriteria.status=="UNPAID"?"selected":""}>UNPAID</option>
            		<option ${filterCriteria.status=="PAID"?"selected":""}>PAID</option>
            	</select>
            </td>
		</tr>
		<tr>
            <td align="right">Date From&nbsp;&nbsp;</td>
            <td>:&nbsp;</td>
            <td>
                <input id="dateFrom" name="dateFrom" formatLength="long" dojoType="dropdowndatepicker" lang="en-us" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='MM/dd/yyyy'/>" displayFormat="dd-MM-yyyy" saveFormat="dd-MM-yyyy"/>
                &nbsp;Date To&nbsp;:&nbsp;
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
