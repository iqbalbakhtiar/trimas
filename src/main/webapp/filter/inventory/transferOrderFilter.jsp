<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 30%; height: 150px; left: 225px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="0" align="right">
			<tr>
				<td width="35%" align="right"><spring:message code="transferorder.code"/>&nbsp;&nbsp;</td>
				<td width="5%">:&nbsp;</td>
				<td width="60%"><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
			</tr>
			<%-- <tr>
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
            </tr> --%>
			<tr>
				<td align="right"><spring:message code="sirius.datefrom"/>&nbsp;&nbsp;</td>
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