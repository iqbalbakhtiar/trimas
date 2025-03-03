<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true"
	 style="width: 35%; height: 350px; left: 300px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
	<table width="100%" cellspacing="0" cellpadding="0" align="right">
	<tr>
		<td align="right" style="WIDTH:130px;"><spring:message code="sirius.code"/>&nbsp;&nbsp;</td>
		<td width="4">:&nbsp;</td>
		<td width="310" height="28"><input type="text" name="code" id="code" class="inputbox" value="<c:out value='${filterCriteria.code}'/>"/></td>
	</tr>
	<tr>
		<td align="right"><spring:message code="sirius.datefrom"/> &nbsp;</td>
		<td align="center">:</td>
		<td>
			<input id="dateFrom" name="dateFrom" class="datepicker" value="<fmt:formatDate value='${filterCriteria.dateFrom}' pattern='dd-MM-yyyy'/>"/>
			&nbsp;<spring:message code="sirius.dateto"/> &nbsp;:&nbsp;
			<input id="dateTo" name="dateTo" class="datepicker" value="<fmt:formatDate value='${filterCriteria.dateTo}' pattern='dd-MM-yyyy'/>"/>
		</td>
	</tr>
	<tr>
		<td align="right" style="WIDTH:130px;"><spring:message code="supplier"/>&nbsp;&nbsp;</td>
		<td width="4">:&nbsp;</td>
		<td width="310" height="28"><input type="text" id="supplierName" name="supplierName" class="inputbox" value="<c:out value='${filterCriteria.supplierName}'/>"/></td>
	</tr>
	<tr>
		<td align="right" style="WIDTH:130px;"><spring:message code="sirius.tax"/>&nbsp;&nbsp;</td>
		<td width="4">:&nbsp;</td>
		<td width="310" height="28"><input type="text" id="tax" name="tax" class="inputbox" value="<c:out value='${filterCriteria.tax}'/>"/></td>
	</tr>
	<tr>
		<td align="right"><spring:message code="salesorder.approvalstatus"/>&nbsp;</td>
		<td >:</td>
		<td >
			<select id="approvalDecisionStatus" name="approvalDecisionStatus">
				<option value=""><spring:message code="sirius.all"/></option>
				<c:forEach items="${approvableDecisionStat}" var="status">
					<option value="${status}"
						${not empty filterCriteria.approvalDecisionStatus and filterCriteria.approvalDecisionStatus == status ? "selected" : ""}>
							${status.normalizedName}
					</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right" style="WIDTH:130px;"><spring:message code="sirius.approver"/>&nbsp;&nbsp;</td>
		<td width="4">:&nbsp;</td>
		<td width="310" height="28"><input type="text" id="approver" name="approver" class="inputbox" value="<c:out value='${filterCriteria.approver}'/>"/></td>
	</tr>
	<tr>
		<td align="right" style="WIDTH:130px;"><spring:message code="dpo.billto"/>&nbsp;&nbsp;</td>
		<td width="4">:&nbsp;</td>
		<td width="310" height="28"><input type="text" id="billToAddress" name="billToAddress" class="inputbox" value="<c:out value='${filterCriteria.billToAddress}'/>"/></td>
	</tr>
	<tr>
		<td align="right" style="WIDTH:130px;"><spring:message code="dpo.shipto"/>&nbsp;&nbsp;</td>
		<td width="4">:&nbsp;</td>
		<td width="310" height="28"><input type="text" id="shipToFacility" name="shipToFacility" class="inputbox" value="<c:out value='${filterCriteria.shipToFacility}'/>"/></td>
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
