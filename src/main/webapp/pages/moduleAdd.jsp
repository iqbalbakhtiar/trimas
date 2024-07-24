<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>

<div class="toolbar">
	<a class="item-button-save"  onclick="save();"><span>Save</span></a>
</div>
			
<div class="main-box" style="height:370px">		  
	<sesform:form id="editForm" name="editForm" action="page/moduleadd.htm" method="post" modelAttribute="module">
	<sessionConversation:insertSessionConversationId attributeName='module'/>
	<table style="border:none">
	<tr>
		<td align="right">ID :</td>
		<td><input id="code" name='code' class='input-disabled' value="Auto Generated" disabled/></td>
	</tr>
	<tr>
		<td align="right">Name (English) :</td>
		<td><form:input path="name" size="75"/></td>
	</tr>
	<tr>
		<td align="right">Name (Indonesian) :</td>
		<td><form:input path="alias" size="75"/></td>
	</tr>
	<tr>
		<td align="right">Default URI :</td>
		<td><form:input path="defaultUri" size="75"/></td>
	</tr>
	<tr>
		<td align="right">Group :</td>
		<td><input value="${module.moduleGroup.name}" class="inputbox"/></td>
	</tr>
	<tr>
		<td align="right">Enabled :</td>
		<td>
			<form:radiobutton path="enabled" value="true" label="Yes"/>
			<form:radiobutton path="enabled" value="false" label="No"/>
		</td>
	</tr>
	<tr>
		<td align="right">Menu Index :</td>
		<td><form:input path="menuIndex" size="5"/></td>
	</tr>
	</table>
	</sesform:form>
</div>