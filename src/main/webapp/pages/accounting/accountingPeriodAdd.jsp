<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<div class="toolbar">
	<a class="item-button-save" href="javascript:validate();"><span><spring:message code="sirius.save"/></span></a>
</div>
<div class="main-box" style="height:370px">
	<sesform:form id="addForm" name="addForm" action="page/accountingperiodadd.htm" method="post" modelAttribute="accountingPeriod">
		<table style="border:none">
		<tr>
			<td width="150"><div align="right"><spring:message code="accountingperiod.code"/> :</div></td>
			<td width="378"><form:input path="code" size="10" id="code"/></td>
			<tr>
				<td><div align="right"><spring:message code="accountingperiod.name"/> :</div></td>
				<td><form:input path="name" size="50" id="name"/></td>
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
					<select class="combobox-ext">
						<option selected>${accountingPeriod.parent.name}</option>
					</select>
				</td>
			</tr>
			</c:if>
			<tr>
				<td><div align="right"><spring:message code="accountingperiod.startdate"/> :</div></td>
				<td><input name="startDate" dojotype="dropdowndatepicker" displayformat="dd-MM-yyyy" saveFormat="dd-MM-yyyy" lang="en-us" containerToggle="explode" containerToggleDuration="300"/></td>
			</tr>
			<tr>
				<td><div align="right"><spring:message code="accountingperiod.enddate"/> :</div></td>
				<td><input name="endDate" dojotype="dropdowndatepicker" displayformat="dd-MM-yyyy" saveFormat="dd-MM-yyyy" lang="en-us" containerToggle="explode" containerToggleDuration="300" /></td>
			</tr>
			<tr>
				<td><div align="right"><spring:message code="accountingperiod.status"/> :</div></td>
				<td>
					<form:select path="status">
						<form:option value="FUTURE"><spring:message code="accountingperiod.future"/></form:option>
                        <form:option value="OPEN"><spring:message code="accountingperiod.open"/></form:option>
					</form:select>
				</td>
			</tr>
			<tr>
				<td><div align="right"><spring:message code="accountingperiod.organization"/> :</div></td>
				<td>
					<form:select id="org" path="organization" cssClass="combobox-ext">
					</form:select>
					&nbsp;
					<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');"  title="Company Structure" />
				</td>
			</tr>
			<tr>
				<td><div align="right"><spring:message code="accountingperiod.note"/> :</div></td>
				<td><form:textarea path="note" cols="50" rows="6"/> </td>
			</tr>
			</table>
	</sesform:form>
</div>