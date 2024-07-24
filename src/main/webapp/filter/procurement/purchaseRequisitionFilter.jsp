<div dojoType="FloatingPane" id="filter" title='<spring:message code="sirius.paging.filter"/>' constrainToContainer="true" style="width: 48%; height: 320px; left: 350px; display:none;" toggle="explode">
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="2" cellpadding="0" align="right">
		<tr>
			<td align="right">ID&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
        <tr>
			<td align="right" style="WIDTH:130px;"><spring:message code="organization"/>&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="310" height="28">
				<input type="text" id="organizationName" name="organizationName" value="${filterCriteria.organizationName}" size="35" class="inputbox"/>
			</td>
		</tr>
        <tr>
			<td align="right"><spring:message code="supplier"/>&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td>
            	<input type="text" id="supplierName" name="supplierName" value="${filterCriteria.supplierName}" size="35" class="inputbox"/>
            </td>
		</tr>
        <tr>
			<td align="right"><spring:message code="purchaserequisition.requisitioner"/>&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td>
            	<input type="text" id="requisitionerName" name="requisitionerName" value="${filterCriteria.requisitionerName}" size="35" class="inputbox"/>
            </td>
		</tr>
		<tr>
            <td align="right"><spring:message code="sirius.datefrom"/> &nbsp;&nbsp;</td>
            <td>:&nbsp;</td>
            <td>
                <input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
                &nbsp;<spring:message code="sirius.dateto"/> &nbsp;:&nbsp;
                <input id="dateTo" name="dateTo" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
            </td>
		</tr>
		<tr>
			<td align="right"><spring:message code="purchaserequisition.approvalstatus"/>&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="310" height="28">
				<select id="approval" name="approval">
					<option value=""></option>
					<c:forEach items="${approvalStatus}" var="status">
						<option value="${status}" ${status == filterCriteria.approval ? 'selected' : ''}><spring:message code="approval.${status.message}"/></option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
             <td align="right"><spring:message code="purchaserequisition.pocreated"/>&nbsp;&nbsp;</td>
             <td>:&nbsp;</td>
             <td>
                <select id="purchase" name="purchase">
                  	<option value=""></option>
                   	<option value="true" ${not empty filterCriteria.purchase and filterCriteria.purchase ? 'selected' : ''}><spring:message code="sirius.yes"/></option>
				   	<option value="false" ${not empty filterCriteria.purchase and !filterCriteria.purchase ? 'selected' : ''}><spring:message code="sirius.no"/></option>
	            </select>
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
			alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
			return;
		}
		
		openpopup("<c:url value='/page/popupsupplierview.htm?target=supplier&organization='/>"+org.value);
	}
	
	function openrequisitioner()
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
			return;
		}
		
		openpopup("<c:url value='/page/popuppurchaserequisitionview.htm?target=requisitioner&organization='/>"+org.value);
	}
</script>