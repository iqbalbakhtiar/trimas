<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="javascript:displayContent('page/modulepreedit.htm?id=${moduleDetail.module.id}')"><span>List</span></a>
	<a class="item-button-save"  onclick="save();"><span>Save</span></a>
</div>
			
<div class="main-box" style="height:370px">		  
	<sesform:form id="editForm" name="editForm" action="page/moduledetailedit.htm" method="post" modelAttribute="moduleDetail">
	<sessionConversation:insertSessionConversationId attributeName='moduleDetail'/>
	<table style="border:none">
	<tr>
		<td align="right">URI :</td>
		<td><form:input path="uri" size="75"/></td>
	</tr>
	<tr>
		<td align="right">Group :</td>
		<td><input value="${moduleDetail.module.name}" size="75"/></td>
	</tr>
	<tr>
		<td align="right">Type :</td>
		<td>
			<form:select path="detailType" cssClass="combobox">
				<form:option value="READ">READ</form:option>
				<form:option value="ADD">ADD</form:option>
				<form:option value="EDIT">EDIT</form:option>
				<form:option value="DELETE">DELETE</form:option>
				<form:option value="PRINT">PRINT</form:option>
			</form:select>
		</td>
	</tr>
	</table>
	</sesform:form>
</div>