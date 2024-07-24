<div dojoType="FloatingPane" id="filter" title="<spring:message code='sirius.paging.filter'/>" constrainToContainer="true" style="width: 35%; height: 350px; left: 350px; display:none;" toggle="explode">
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="1" align="right">
		<tr>
			<td width="35%" align="right"><spring:message code="purchaseorder.code"/></td>
			<td width="5%">:&nbsp;</td>
			<td width="60%"><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
       <%--  <tr>
			<td align="right"><spring:message code='organization'/></td>
			<td>:&nbsp;</td>
			<td>
				<input type="text" id="organizationName" name="organizationName" value="${filterCriteria.organizationName}" size="35" class="inputbox"/>
			</td>
		</tr> --%>
		<tr>
			<td align="right"><spring:message code='sirius.facility'/></td>
			<td>:&nbsp;</td>
			<td>
				<input type="text" id="facilityName" name="facilityName" value="${filterCriteria.facilityName}" size="35" class="inputbox"/>
            </td>
		</tr>
		<tr>
			<td align="right"><spring:message code='supplier'/></td>
			<td>:&nbsp;</td>
			<td>
				<input type="text" id="supplierName" name="supplierName" value="${filterCriteria.supplierName}" size="35" class="inputbox"/>
            </td>
		</tr>
		<tr>
			<td align="right"><spring:message code='barcode'/></td>
			<td>:&nbsp;</td>
			<td>
				<input type="text" id="barcode" name="barcode" value="${filterCriteria.barcode}" size="35" class="inputbox"/>
            </td>
		</tr>
 		<tr>
			<td align="right"><spring:message code='goodsreceipt'/></td>
			<td>:&nbsp;</td>
			<td>
				<input type="text" id="reference" name="reference" value="${filterCriteria.reference}" size="35" class="inputbox"/>
            </td>
		</tr>
 		<tr>
			<td align="right"><spring:message code='invoiceverification'/></td>
			<td>:&nbsp;</td>
			<td>
				<input type="text" id="invoiceVerificationCode" name="invoiceVerificationCode" value="${filterCriteria.invoiceVerificationCode}" size="35" class="inputbox"/>
            </td>
		</tr>
		<tr>
            <td align="right"><spring:message code="sirius.datefrom"/>&nbsp;&nbsp;</td>
            <td>:&nbsp;</td>
            <td>
                <input id="dateFrom" name="dateFrom" class="datepicker" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>" displayFormat="dd-MM-yyyy" saveFormat="dd-MM-yyyy"/>
            </td>
		</tr>
		<tr>
            <td align="right"><spring:message code="sirius.dateto"/>&nbsp;&nbsp;</td>
            <td>:&nbsp;</td>
            <td>
                <input id="dateTo" name="dateTo" class="datepicker" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>" displayFormat="dd-MM-yyyy" saveFormat="dd-MM-yyyy"/>
            </td>
		</tr>
		<tr><td colspan="3">&nbsp;</td></tr>
		<tr>
			<td colspan="2">&nbsp;</td>
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