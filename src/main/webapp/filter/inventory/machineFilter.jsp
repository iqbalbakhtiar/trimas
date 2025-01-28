<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 25%; height: 250px; left: 300px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
	<table width="100%" cellspacing="0" cellpadding="0" align="right">
	<tr>
		<td align="right" style="WIDTH:130px;"><spring:message code="sirius.code"/>&nbsp;&nbsp;</td>
		<td width="4">:&nbsp;</td>
		<td width="310" height="28"><input type="text" name="code" id="code" class="inputbox" value="<c:out value='${filterCriteria.code}'/>"/></td>
	</tr>
	<tr>
		<td align="right" style="WIDTH:130px;"><spring:message code="sirius.name"/>&nbsp;&nbsp;</td>
		<td width="4">:&nbsp;</td>
		<td width="310" height="28"><input type="text" id="name" name="name" class="inputbox" value="<c:out value='${filterCriteria.name}'/>"/></td>
	</tr>
	<tr>
		<td align="right"><spring:message code="sirius.status"/>&nbsp;</td>
		<td align="center">:</td>
		<td>
			<select id="active" name="active">
			    <option value="" ${filterCriteria.status == null ? "selected" : ""}>
			        <spring:message code="sirius.all"/>
			    </option>
			    <option value="true" ${filterCriteria.status != null && filterCriteria.status ? "selected" : ""}>
			        <spring:message code="sirius.active"/>
			    </option>
			    <option value="false" ${filterCriteria.status != null && !filterCriteria.status ? "selected" : ""}>
			        <spring:message code="sirius.inactive"/>
			    </option>
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
