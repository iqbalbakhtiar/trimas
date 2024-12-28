<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<div class="toolbar">
	<a class="item-button-save" href="javascript:validate();"><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box" style="height:370px">
<sesform:form id="addForm" name="addForm" action="page/chartofaccountadd.htm" method="post" modelAttribute="chartOfAccount">
	<table style="border:none">
	<tr>
		<td align="right"><spring:message code="coa.code"/> :</td>
		<td><form:input path="code" cssClass="inputbox" id="code"/></td>
	</tr>
	<tr>
		<td align="right"><spring:message code="coa.name"/> :</td>
		<td><form:input path="name" cssClass="inputbox" id="name"/></td>
	</tr>
	<tr>
		<td align="right"><spring:message code="coa.note"/> :</td>
		<td><form:textarea path="note" cols="50" rows="6" id="note"/></td>
	</tr>
	</table>
</sesform:form>
</div>