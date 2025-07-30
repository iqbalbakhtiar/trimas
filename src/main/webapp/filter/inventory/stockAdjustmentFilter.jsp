<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 26%; height: 240px; left: 425px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" style="border:none">
		<tr>
			<td align="right"><spring:message code="organization"/></td>
			<td>:&nbsp;</td>
			<td><input type="text" id="organizationName" name="organizationName" value="${filterCriteria.organizationName}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="facility"/></td>
			<td>:&nbsp;</td>
			<td><input type="text" id="facilityName" name="facilityName" value="${filterCriteria.facilityName}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="sirius.code"/></td>
			<td>:</td>
			<td><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
            <td align="right"><spring:message code="sirius.datefrom2"/></td>
            <td>:&nbsp;</td>
            <td>
                <input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
                -
                <input id="dateTo" name="dateTo" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
            </td>
		</tr>
		<tr><td colspan="3">&nbsp;</td></tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td align="left">
				<%@ include file="/pages/includes/filter.jsp"%>
			</td>
		</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
function openfacility()
{
	var org = document.getElementById('org');
	if(org.value == '')
	{
		alert('<spring:message code="organization"/> <spring:message code="notif.empty"/> !');
		return;
	}
	
	openpopup("<c:url value='/page/popupfacilityview.htm?target=facility&organization='/>"+org.value);
}
</script>