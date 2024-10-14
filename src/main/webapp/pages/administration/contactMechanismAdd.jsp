<%@ include file="/common/sirius-general-top.jsp"%>	
					<div class="toolbar">
						<a class="item-button-back" /><span><spring:message code="sirius.back"/></span></a>
						<c:if test='${access.add}'>
							<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
						</c:if>
					</div>					  
					<div class="main-box">
						<sesform:form id='addForm' name="addForm" method="post" modelAttribute="contactMechanism_add">
						<table>
 						<tr> 	
 							<td align="right" width="24%"><spring:message code="sirius.name"/> :</td>
							<td width="40%"> <input class="input-disabled" value="${contactMechanism_add.party.fullName}" disabled="disabled" size="36"/></td>
 						</tr>
						<tr>
							<td align="right"><spring:message code="contactmechanism.name"/> :</td>
							<td> <form:input path="contactName" cssClass="inputbox" size="15"/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="contactmechanism.type"/> :</td>
							<td>
								<form:select path="contactMechanismType">
									<option value="PHONE">PHONE</option>
									<option value="MOBILE">MOBILE</option>
									<option value="FAX">FAX</option>
									<option value="EMAIL">EMAIL</option>
									<option value="WEBSITE">WEBSITE</option>
								</form:select>
							</td>
						</tr>
						<tr>
							<td align="right"><spring:message code="contactmechanism.detail"/> :</td>
							<td> <form:input path="contact" cssClass="inputbox" size="15"/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="contactmechanism.department"/> :</td>
							<td> <form:input path="department" cssClass="inputbox" size="15"/></td>
						</tr>
 						<tr>
							<td align="right"><spring:message code="sirius.status"/> :</td>
							<td>
								<form:radiobutton path="active" value="true" label="Active"/>
								<form:radiobutton path="active" value="false" label="Inactive"/>
							</td>
						</tr>
                        <tr>
							<td align="right"><spring:message code="sirius.note"/> :</td>
							<td><form:textarea path='note' cols='55' rows='6'/></td>
						</tr>
 						</table>
						</sesform:form>
					</div>
				<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
$(function() {
	var $dialog = $('<div></div>').dialog({autoOpen: false, title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
	$('.item-button-save').click(function()
	{
		if(validateForm()) {
			save();
		}
	});
	
	$('.item-button-back').click(function()
	{
		<c:if test='${empty redirectURL}'>
			window.location="<c:url value='/page/partypreedit.htm?id=${contactMechanism_add.party.id}'/>";
		</c:if>
		<c:if test='${not empty redirectURL}'>
			window.location="<c:url value='/page/${redirectURL}?id=${relationshipId}&lastPanel=contact'/>";
		</c:if>
	});
});

function validateForm() {
	var contactName = $('input[name="contactName"]').val();
	var contact = $('input[name="contact"]').val();
	var department = $('input[name="department"]').val();
	var contactMechanismType = $('select[name="contactMechanismType"]').val();
	var active = $('input[name="active"]:checked').val();

	if (contactName == null || contactName.trim() === "") {
		alert('<spring:message code="contactmechanism.name"/> <spring:message code="notif.empty"/> !');
		return false;
	}

	if (contact == null || contact.trim() === "") {
		alert('<spring:message code="contactmechanism.detail"/> <spring:message code="notif.empty"/> !');
		return false;
	}

	if (department == null || department.trim() === "") {
		alert('<spring:message code="contactmechanism.department"/> <spring:message code="notif.empty"/> !');
		return false;
	}

	if (contactMechanismType == null || contactMechanismType === "") {
		alert('<spring:message code="contactmechanism.type"/> <spring:message code="notif.empty"/> !');
		return false;
	}

	if (active == null || active === undefined) {
		alert('<spring:message code="sirius.status"/> <spring:message code="notif.empty"/> !');
		return false;
	}
	return true;
}

function save() {
	$.ajax({
		url:"<c:url value='/page/contactmechanismadd.htm'/>",
		data:$('#addForm').serialize(),
		type : 'POST',
		dataType : 'json',
		beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
		success : function(json) {
			if(json)
			{
				if(json.status == 'OK')
				{
					$dialog.dialog('close');
					<c:if test='${empty redirectURL}'>
						window.location="<c:url value='/page/partypreedit.htm?id=${contactMechanism_add.party.id}'/>";
					</c:if>
					<c:if test='${not empty redirectURL}'>
						window.location="<c:url value='/page/${redirectURL}?id=${relationshipId}&lastPanel=contact'/>";
					</c:if>
				}
				else
						afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
			}
		}
	});
}
</script>
