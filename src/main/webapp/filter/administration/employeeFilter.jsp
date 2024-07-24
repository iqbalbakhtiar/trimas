<div dojoType="FloatingPane" id="filter" title="Filter" constrainToContainer="true" style="width: 30%; height: 220px; left: 350px; display:none;" toggle="explode" bg>
	<form id="filterForm" name="filterForm" method="post">
	<table width="100%" cellspacing="0" cellpadding="1" align="right">
	<tr>
		<td width="30%" align="right"><spring:message code="organization"/>&nbsp;</td>
		<td width="1%" align="center">:</td>
		<td width="70%" ><input id="organizationName" name="organizationName" class="inputbox" value="${filterCriteria.organizationName}"/></td>
	</tr>
	<tr>
		<td align="right"><spring:message code="sirius.code"/>&nbsp;</td>
		<td align="center">:</td>
		<td><input type="text" name="code" id="code" class="inputbox" value="<c:out value='${filterCriteria.code}'/>"/></td>
	</tr>
	<tr>
		<td align="right" style="WIDTH:150px;"><spring:message code="sirius.name"/>&nbsp;</td>
		<td align="center">:</td>
		<td><input type="text" id="name" name="name" class="inputbox" value="<c:out value='${filterCriteria.name}'/>"/></td>
	</tr>
	<tr>
		<td align="right"><spring:message code="sirius.status"/>&nbsp;</td>
		<td align="center">:</td>
		<td>
          	<select id="status" name="status">
				<option value=""><spring:message code="sirius.all"/></option>
				<option value="true" ${not empty filterCriteria.status and filterCriteria.status ? "selected" : ""}><spring:message code="sirius.active"/></option>
				<option value="false" ${not empty filterCriteria.status and !filterCriteria.status ? "selected" : ""}><spring:message code="sirius.inactive"/></option>
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