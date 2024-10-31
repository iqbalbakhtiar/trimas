<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 35%; height: 240px; left: 425px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" style="border:none">
		<tr>
			<td align="right"><spring:message code="sirius.code"/></td>
			<td>:&nbsp;</td>
			<td><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="organization"/></td>
			<td>:&nbsp;</td>
			<td>
				<select id="org" name="organization" class="combobox-ext">
					<c:if test='${not empty org}'>
                		<option value="${org.id}">${org.fullName}</option>
                	</c:if>
				</select>
				<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title="Company Structure" />
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="facility"/></td>
			<td>:</td>
			<td>
				<select id="facility" name="facility" class="combobox-ext">
					<c:if test="${not empty facility}">
						<option value="${facility.id}">${facility.name}</option>
					</c:if>
				</select>
				<a class="item-popup" onclick="openfacility();" title="Facility" />
			</td>
		</tr>
		<tr>
            <td align="right"><spring:message code="sirius.datefrom"/></td>
            <td>:&nbsp;</td>
            <td>
                <input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
                &nbsp;<spring:message code="sirius.dateto"/>&nbsp;:&nbsp;
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