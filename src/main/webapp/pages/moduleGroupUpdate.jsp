<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<div class="toolbar">
	<a class="item-button-save"  onclick="save();"><span>Save</span></a>
</div>
			
<div class="main-box">		  
	<sesform:form id="editForm" name="editForm" action="page/modulegroupedit.htm" method="post" modelAttribute="moduleGroup">
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
	<br/>
	<div id="modulecontainer" dojoType="TabContainer" style="width:100% ; height: 250px;">
		<div id="member" dojoType="ContentPane" label="Child Group" class="tab-pages">
			<div class="toolbar-clean">
				<a class="item-button-new" href="javascript:displayContent('page/modulegrouppreadd.htm?parent=${moduleGroup.id}')"><span>New Group</span></a>
			</div>				  
			<table width="100%" cellpadding="0" cellspacing="0" class="table-list" align="center">
			<tr>
				<th>&nbsp;</th>
				<th>Name</th>
				<th>Note</th>
			</tr>
			<c:forEach items="${moduleGroup.members}" var="member">
			<tr>
				<td class="tools">
					<a class="item-button-edit" href="javascript:displayContent('page/modulegrouppreedit.htm?id=${member.id}')"><span>Edit</span></a>
					<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/modulegroupdelete.htm?id=${member.id}'/>');" title="Delete"><span>Delete</span></a>
				</td>
				<td>${member.name}</td>
				<td>${member.note}</td>
			</tr>
			</c:forEach>
			<tr class="end-table"><td colspan="4">&nbsp;</td></tr>
			</table>							
		</div>
		<div id="module" dojoType="ContentPane" label="Module" class="tab-pages">
			<div class="toolbar-clean">
				<a class="item-button-new" href="javascript:displayContent('page/modulepreadd.htm?id=${moduleGroup.id}')"><span>New Module</span></a>
			</div>				  
			<table width="100%" cellpadding="0" cellspacing="0" class="table-list" align="center">
			<tr>
				<th width="5%">&nbsp;</th>
				<th width="6%">Code</th>
				<th width="44%">Name</th>
				<th width="20%">Default URI</th>
			</tr>
			<c:forEach items="${moduleGroup.modules}" var="module">
			<tr>
				<td class="tools">
					<a class="item-button-edit" href="javascript:displayContent('page/modulepreedit.htm?id=${module.id}')"><span>Edit</span></a>
					<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/moduledelete.htm?id=${module.id}'/>');" title="Delete"><span>Delete</span></a>
				</td>
				<td>${module.code}</td>
				<td>${module.name}</td>
				<td>${module.defaultUri}</td>
			</tr>
			</c:forEach>
			<tr class="end-table"><td colspan="4">&nbsp;</td></tr>
			</table>							
		</div>
	</div>
</div>