<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/customerview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
<sesform:form id="addForm" name="addForm" method="post" modelAttribute="customer_group_edit" enctype="multipart/form-data">
	<table style="border:none" width="100%">
		<tr>
			<td width="34%" align="right"><span>Customer ID</td>
			<td width="1%" align="center">:</td>
			<td width="64%"><form:input id='code' path="code" disabled='true' class='input-disabled'/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="organization"/></td>
			<td width="1%" align="center">:</td>
			<td>
				<form:select id="org" path="organization" cssClass="combobox-ext input-disabled">
		    		<c:if test='${not empty relationship.partyTo.id}'>
						<form:option value='${relationship.partyTo.id}' label='${relationship.partyTo.fullName}'/>
		    		</c:if>
				</form:select>
			</td>
		</tr>
		<tr>
			<td align="right"><span>Customer Group Name</td>
			<td width="1%" align="center">:</td>
			<td><form:input id='fullName' path="fullName" disabled='true' class='input-disabled'/></td>
		</tr>
		<tr>
			<td align="right"><spring:message code="sirius.status"/> :</td>
			<td width="1%" align="center">:</td>
			<td>
				<form:radiobutton path="active" value="true" label="Active"/>
				<form:radiobutton path="active" value="false" label="Inactive"/>	
			</td>
		</tr>
		<tr>
			<td align="right"><span><spring:message code="party.note"/></td>
			<td width="1%" align="center">:</td>
			<td><form:textarea path="note" rows="6" cols="45"/></td>
			<td>&nbsp;</td>
		</tr>
	</table>
</sesform:form>
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
$(function(){
	$('.item-button-save').click(function(){
		save();
	});
	
});

function save() {
	var xhr = new XMLHttpRequest();
	xhr.open('POST', "<c:url value='/page/customergroupedit.htm?relationshipId='/>" + ${relationship.id});
	xhr.responseType = 'json';
	
	if(xhr.readyState == 1) {
		$dialog.empty();
		$dialog.html('<spring:message code="notif.saving"/>');
		$dialog.dialog('open');
	}
	
	xhr.onreadystatechange = function () {
		if(xhr.readyState == 4) {
			var json = xhr.response;
			if(json) {
				if(json.status == 'OK') {
					$dialog.dialog('close');
					
					let url = "<c:url value='/page/customergrouppreedit.htm?id='/>"+json.data.relationshipId;;
					
					window.location=url;
				} else {
					afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
				}
			}
		}
	};
	
	xhr.send(new FormData($('#addForm')[0]));
}
</script>