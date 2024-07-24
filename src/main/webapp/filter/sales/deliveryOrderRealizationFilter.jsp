<div dojoType="FloatingPane" id="filter" title="Filter" style="width: 35%; height: 300px; left: 300px; display:none;" toggle="explode">
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="1" align="right">
		<tr>
			<td align="right"><spring:message code="deliveryrealization.code"/></td>
			<td>:</td>
			<td><input type="text" id="code" name="code" value="${filterCriteria.code}" size="35" class="inputbox"/></td>
		</tr>
		<%-- <tr>
			<td align="right" style="WIDTH:130px;">Company&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="310" height="28">
				<select id="org" name="organization" class="combobox-ext">
				<c:if test='${not empty filterCriteria.organization}'>
                  <option value="${filterCriteria.organization}"></option>
                </c:if>
				</select>
				<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');"  title="Company Structure" />
			</td>
		</tr> --%>
		<tr>
			<td align="right"><spring:message code="deliveryrealization.customer"/></td>
			<td>:</td>
			<td><input type="text" id="customer" name="customer" value="${filterCriteria.customer}" size="35" class="inputbox"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="deliveryorder"/></td>
			<td>:</td>
			<td width="304" height="28"><input type="text" id="order" name="order" value="${filterCriteria.order}" size="35" class="inputbox"/></td>
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
		<%-- <tr>
			<td align="right" style="WIDTH:130px;">Billing Created&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="310" height="28">
				<select id="billingCreated" name="billingCreated">
					<option value='${filterCriteria.billingCreated}'></option>
					<option value='true'>YES</option>
					<option value='false'>NO</option>
				</select>
			</td>
		</tr> --%>
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