<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 30%; height: 250px; left: 50%; top: 50%; transform: translate(-50%, -50%); display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="1" align="right">
			<tr>
				<td width="35%" align="right"><spring:message code="sirius.code"/>&nbsp;</td>
				<td width="5%" align="center">:</td>
				<td width="60%"><input name="code" id="code" class="inputbox" value="${filterCriteria.code}"/></td>
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
				<td align="right"><spring:message code="organization"/>&nbsp;</td>
				<td align="center">:</td>
				<td ><input type="text" id="org" name="org" class="inputbox" value="${filterCriteria.org}"/></td>
			</tr>
			<tr>
				<td align="right"><spring:message code="sirius.createdby"/>&nbsp;</td>
				<td align="center">:</td>
				<td ><input type="text" id="createdBy" name="createdBY" class="inputbox" value="${filterCriteria.createdBy}"/></td>
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
