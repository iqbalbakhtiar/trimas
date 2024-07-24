<div dojoType="FloatingPane" id="filter" title="<spring:message code='sirius.paging.filter'/>" constrainToContainer="true" style="width: 35%; height: 250px; left: 350px; display:none;" toggle="explode">
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="1" align="right">
		<tr>
			<td width="25%" align="right"><spring:message code="purchaseorder.code"/> :</td>
			<td width="75%"><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
        <tr>
			<td align="right"><spring:message code='organization'/> :</td>
			<td>
				<%--<select id="org" name="organization" class="combobox-ext">
				</select>
				<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');"  title="Company Structure" />--%>
				<input type="text" id="organizationName" name="organizationName" value="${filterCriteria.organizationName}" size="35" class="inputbox"/>
			</td>
		</tr>
        <tr>
			<td align="right"><spring:message code='supplier'/> :</td>
			<td>
            	<%--<select id="supplier" name="supplier" class="combobox-ext">
				</select>
				<a class="item-popup" onclick="opensupplier();"  title="Supplier" />--%>
				<input type="text" id="supplierName" name="supplierName" value="${filterCriteria.supplierName}" size="35" class="inputbox"/>
            </td>
		</tr>
		<tr>
            <td align="right"><spring:message code='sirius.datefrom'/> :</td>
            <td>
                <input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
            </td>
		</tr>
		<tr>
            <td align="right"><spring:message code='sirius.dateto'/> :</td>
            <td>
                <input id="dateTo" name="dateTo" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
            </td>
		</tr>
		<tr><td colspan="2">&nbsp;</td></tr>
		<tr>
			<td>&nbsp;</td>
			<td align="left">
				<%@ include file="/common/filter.jsp"%>
			</td>
		</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
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
</script>	