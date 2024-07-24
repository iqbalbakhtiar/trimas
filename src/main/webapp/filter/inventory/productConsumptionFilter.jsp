<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 55%; height: 250px; left: 350px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="1">
		<tr>
			<td align="right"><spring:message code="sirius.id"/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
            <td align="right"><spring:message code="sirius.date"/>&nbsp;&nbsp;</td>
            <td>:&nbsp;</td>
            <td>
                <input id="date" name="date" value="<fmt:formatDate value='${filterCriteria.date}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
            </td>
		</tr>
		<tr>
            <td align="right"><spring:message code="productconsumption.warehouse"/>&nbsp;&nbsp;</td>
            <td>:&nbsp;</td>
            <td><input type="text" id="warehouse" name="warehouse" value="${filterCriteria.warehouse}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
            <td align="right"><spring:message code="productconsumption.master"/>&nbsp;&nbsp;</td>
            <td>:&nbsp;</td>
            <td><input type="text" id="consumptionMaster" name="consumptionMaster" value="${filterCriteria.consumptionMaster}" size="35" class="inputbox"/></td>
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
