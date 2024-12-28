<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>

<div class="toolbar">
	<c:if test="${access.delete && chartOfAccount.deleteable}">
		<a class="item-button-delete" href="javascript:window.location='page/chartofaccountdelete.htm?id=${chartOfAccount.id}';"><span><spring:message code="sirius.delete"/></span></a>
	</c:if>
	<a class="item-button-save" href="javascript:document.editForm.submit();"><span>Save</span></a>
    <a class="item-button-print" href="javascript:window.location='page/chartofaccountprint.htm?id=${chartOfAccount.id}';"><span><spring:message code="sirius.print"/></span></a>
	<a class="item-button-add-gl-child" href="javascript:displayContent('page/glaccountpreadd.htm?coa=${chartOfAccount.id}');"><span><spring:message code="glaccount.new"/></span></a>
</div>
			
<div class="main-box" style="height:370px">		  
<sesform:form id="editForm" name="editForm" action="page/chartofaccountedit.htm" method="post" modelAttribute="chartOfAccount">
	<table style="border:none" width="100%">
	<tr>
		<td align="right"><spring:message code="coa.code"/> :</td>
		<td><form:input path="code" cssClass="inputbox"/></td>
	</tr>
	<tr>
		<td align="right"><spring:message code="coa.name"/> :</td>
		<td><form:input path="name" cssClass="inputbox"/></td>
	</tr>
	<tr>
		<td align="right"><spring:message code="coa.note"/> :</td>
		<td><form:textarea path="note" cols="50" rows="6"/></td>
	</tr>
	</table>
</sesform:form>
</div>