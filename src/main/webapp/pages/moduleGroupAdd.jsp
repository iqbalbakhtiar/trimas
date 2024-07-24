<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>

<div class="toolbar">
	<a class="item-button-save"  onclick="save();"><span>Save</span></a>
</div>
			
<div class="main-box" style="height:370px">		  
	<sesform:form id="editForm" name="editForm" action="page/modulegroupadd.htm" method="post" modelAttribute="moduleGroup">
	<sessionConversation:insertSessionConversationId attributeName='moduleGroup'/>
		<table style="border:none">
		<tr>
			<td align="right">Code :</td>
			<td><form:input path="code" cssClass="inputbox"/></td>
		</tr>
		<tr>
			<td align="right">Name :</td>
			<td><form:input path="name" cssClass="inputbox"/></td>
		</tr>
		<c:if test="${not empty moduleGroup.parent}">
		<tr>
			<td align="right">Parent :</td>
			<td><input value="${moduleGroup.parent.name}" class="inputbox"/></td>
		</tr>
		</c:if>
		<tr>
			<td valign="top" align="right">Note :</td>
			<td><form:textarea path="note" cols="50" rows="6"/></td>
		</tr>
		</table>
	</sesform:form>
</div>