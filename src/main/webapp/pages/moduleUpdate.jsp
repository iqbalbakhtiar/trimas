<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="javascript:displayContent('page/modulegrouppreedit.htm?id=${module.moduleGroup.id}');"><span>List</span></a>
	<a class="item-button-save"  onclick="save();"><span>Save</span></a>
</div>
			
<div class="main-box">		  
	<sesform:form id="editForm" name="editForm" action="page/moduleedit.htm" method="post" modelAttribute="module">
	<sessionConversation:insertSessionConversationId attributeName='module'/>
		<table style="border:none">
		<tr>
			<td align="right">ID :</td>
			<td><form:input path="code" size="35"/></td>
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
			<td>
				<form:select id="group" path="moduleGroup" cssClass="combobox-ext">
					<form:option value='${module.moduleGroup.id}' label='${module.moduleGroup.name}' />
				</form:select>
				&nbsp;
				<img src="../assets/icons/list_extensions.gif" onclick="javascript:openpopup('<c:url value='/page/modulegrouppopup.htm?target=group'/>');"  title="Module Group" />
			</td>
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
	<br/>
	<div id="modulecontainer" dojoType="TabContainer" style="width:100% ; height: 250px;">
		<div id="member" dojoType="ContentPane" label="Detail" class="tab-pages">
			<div class="toolbar-clean">
				<a class="item-button-new" href="javascript:displayContent('page/moduledetailpreadd.htm?parent=${module.id}')"><span>New Detail URI</span></a>
			</div>				  
			<table width="100%" cellpadding="0" cellspacing="0" class="table-list" align="center">
			<tr>
				<th><div style="WIDTH:40px;"></div></th>
				<th>URI</th>
				<th>Type</th>
			</tr>
			<c:forEach items="${module.details}" var="member">
			<tr>
				<td class="tools">
					<a class="item-button-edit" href="javascript:displayContent('page/moduledetailpreedit.htm?id=${member.id}')"><span>Edit</span></a>
					<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/moduledetaildelete.htm?id=${member.id}'/>');" title="Delete"><span>Delete</span></a>
				</td>
				<td>${member.uri}</td>
				<td>${member.detailType}</td>
			</tr>
			</c:forEach>
			<tr class="end-table"><td colspan="3">&nbsp;</td></tr>
			</table>					
		</div>
	</div>
	<br/><br/>
</div>