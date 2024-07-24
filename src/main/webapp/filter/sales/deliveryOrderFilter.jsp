<div dojoType="FloatingPane" id="filter" title='<spring:message code="sirius.paging.filter"/>'' constrainToContainer="true" style="width: 40%; height: 330px; left: 350px; display:none;" toggle="explode">
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" border="0" align="right">
		<tr>
			<td align="right"><spring:message code="deliveryorder.code"/></td>
			<td>:</td>
			<td><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="deliveryorder.customer"/></td>
			<td>:</td>
			<td><input type="text" id="customer" name="customer" value="${filterCriteria.customer}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="deliveryorder.driver"/></td>
			<td>:</td>
			<td><input type="text" id="driver" name="driver" value="${filterCriteria.driver}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="deliveryorder.vehicle"/></td>
			<td>:</td>
			<td><input type="text" id="vehicle" name="vehicle" value="${filterCriteria.vehicle}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
            <td align="right"><spring:message code="sirius.datefrom"/></td>
            <td>:</td>
            <td><input id="dateFrom" name="dateFrom" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>" class="datepicker"/></td>
		</tr>
		<tr>
            <td align="right"><spring:message code="sirius.dateto"/></td>
            <td>:</td>
            <td><input id="dateTo" name="dateTo" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>" class="datepicker"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="deliveryplanning.dorcreated"/></td>
			<td>:</td>
			<td>
				<select id="dorCreated" name="dorCreated">
					<option value=''></option>
					<option value='true' <c:out value="${not empty filterCriteria.dorCreated && filterCriteria.dorCreated ? 'selected' : ''}"/>><spring:message code="sirius.yes"/></option>
					<option value='false' <c:out value="${not empty filterCriteria.dorCreated && !filterCriteria.dorCreated ? 'selected' : ''}"/>><spring:message code="sirius.no"/></option>
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