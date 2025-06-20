<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 25%; height: 250px; left: 300px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
	<table width="100%" cellspacing="0" cellpadding="0" align="right">
		<tr>
			<td align="right" style="WIDTH:130px;"><spring:message code="sirius.code"/>&nbsp;&nbsp;</td>
			<td width="4">:&nbsp;</td>
			<td width="310" height="28"><input type="text" name="code" id="code" class="inputbox" value="<c:out value='${filterCriteria.code}'/>"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="productionorder.lotnumber"/>&nbsp;&nbsp;</td>
			<td>:&nbsp;</td>
			<td><input type="text" name="lotNumber" id="lotNumber" class="inputbox" value="<c:out value='${filterCriteria.lotNumber}'/>"/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="sirius.status"/>&nbsp;</td>
			<td align="center">:</td>
			<td>
				<select id="status" name="status">
				    <option value="" ${empty filterCriteria.status ? "selected" : ""}>
				        <spring:message code="sirius.all"/>
				    </option>
				    <c:forEach items="${statuses}" var="status">
				    	<option value="${status}" label="${status}" ${filterCriteria.status eq status ? 'selected' : ''}/>
				    </c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right"><spring:message code="sirius.date"/> &nbsp;</td>
			<td align="center">:</td>
			<td>
				<input id="dateFrom" name="dateFrom" class="datepicker" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>"/>
				&nbsp;
				<input id="dateTo" name="dateTo" class="datepicker" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>"/>
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
