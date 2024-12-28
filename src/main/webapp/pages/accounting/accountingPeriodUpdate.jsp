<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>

<div class="toolbar">
	<a class="item-button-delete" href="javascript:perioddelete(${accountingPeriod.id})"><span><spring:message code="sirius.delete"/></span></a>
	<a class="item-button-save" href="javascript:document.editForm.submit();"><span><spring:message code="sirius.save"/></span></a>
	<a class="item-button-add-gl-child" href="javascript:displayContent('page/accountingperiodpreadd.htm?parent=${accountingPeriod.id}');"><span><spring:message code="sirius.newchild"/></span></a>
	<c:if test="${accountingPeriod.level == 'CHILD' and accountingPeriod.status == 'OPEN'}">
		<a class="item-button-closing-accounting-period" href="javascript:periodclose('<c:url value='/page/accountingperiodpreclose.htm?id=${accountingPeriod.id}'/>','${accountingPeriod.id}');"><span><spring:message code="sirius.preclose"/></span></a>
	</c:if>
	<c:if test="${accountingPeriod.level == 'CHILD' and accountingPeriod.status == 'PRECLOSE'}">
		<a class="item-button-closing-accounting-period" href="javascript:periodclose('<c:url value='/page/accountingperiodclose.htm?id=${accountingPeriod.id}'/>','${accountingPeriod.id}');"><span><spring:message code="sirius.close"/></span></a>
	</c:if>
	<c:if test="${accountingPeriod.level == 'CHILD' and (accountingPeriod.status == 'CLOSED' or accountingPeriod.status == 'PRECLOSE')}">
		<a class="item-button-open-accounting-period" href="<c:url value='/page/accountingperiodopen.htm?id=${accountingPeriod.id}'/>"><span><spring:message code="sirius.open"/></span></a>
	</c:if>
</div>

<div class="main-box" style="height:370px">
	<sesform:form id="editForm" name="editForm" action="page/accountingperiodedit.htm" method="post" modelAttribute="accountingPeriod">
		<table style="border:none">
		<tr>
			<td width="150"><div align="right"><spring:message code="accountingperiod.code"/> :</div></td>
			<td width="378"><form:input path="code" size="20"/></td>
			<tr>
				<td><div align="right"><spring:message code="accountingperiod.name"/> :</div></td>
				<td><form:input path="name" size="50"/></td>
			</tr>
            <tr>
				<td><div align="right"><spring:message code="accountingperiod.level"/> :</div></td>
				<td>
					<form:select path="level">
						<form:option value="GROUP"><spring:message code="accountingperiod.group"/></form:option>
						<form:option value="CHILD"><spring:message code="accountingperiod.child"/></form:option>
					</form:select>
				</td>
			</tr>
			<c:if test="${not empty accountingPeriod.parent}">
			<tr>
				<td><div align="right"><spring:message code="accountingperiod.parent"/> :</div></td>
				<td>
					<form:select path="parent" cssClass="combobox-ext">
						<option>${accountingPeriod.parent.name}</option>
					</form:select>
				</td>
			</tr>
			</c:if>
			<tr>
				<td><div align="right"><spring:message code="accountingperiod.startdate"/> :</div></td>
				<td><input name="startDate" value="<fmt:formatDate value='${accountingPeriod.startDate}' pattern='MM/dd/yyyy'/>" dojotype="dropdowndatepicker" displayformat="dd-MM-yyyy" saveFormat="dd-MM-yyyy" lang="en-us" containerToggle="explode" containerToggleDuration="300"/></td>
			</tr>
			<tr>
				<td><div align="right"><spring:message code="accountingperiod.enddate"/> :</div></td>
				<td><input name="endDate" dojotype="dropdowndatepicker" displayformat="dd-MM-yyyy" saveFormat="dd-MM-yyyy" lang="en-us" value="<fmt:formatDate value='${accountingPeriod.endDate}' pattern='MM/dd/yyyy'/>" containerToggle="explode" containerToggleDuration="300" /></td>
			</tr>
			<tr>
				<td><div align="right"><spring:message code="accountingperiod.status"/> :</div></td>
				<td>
					<input type="text" name="status" value="${accountingPeriod.status}" disabled class="input-disabled"/>
					<%-- <form:select path="status">
						<form:option value="OPEN"><spring:message code="accountingperiod.open"/></form:option>
            			<form:option value="PRECLOSE"><spring:message code="accountingperiod.preclose"/></form:option>
						<form:option value="CLOSED"><spring:message code="accountingperiod.close"/></form:option>
						<form:option value="FUTURE"><spring:message code="accountingperiod.future"/></form:option>
					</form:select> --%>
				</td>
			</tr>
			<tr>
				<td><div align="right"><spring:message code="accountingperiod.organization"/> :</div></td>
				<td>
					<select path="organization" class="combobox-ext">
						<option>${accountingPeriod.organization.firstName} ${accountingPeriod.organization.middleName} ${accountingPeriod.organization.lastName}</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><div align="right"><spring:message code="accountingperiod.note"/> :</div></td>
				<td><form:textarea path="note" cols="50" rows="6"/> </td>
			</tr>
			</table>
	</sesform:form>
</div>