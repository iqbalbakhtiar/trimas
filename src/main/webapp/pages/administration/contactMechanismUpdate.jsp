<%@ include file="/common/sirius-general-top.jsp"%>	
						<div class="toolbar">
							<a class="item-button-back" ><span><spring:message code="sirius.back"/></span></a>
							<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
						</div>					  
						<div class="main-box">
							<sesform:form id='editForm' name="editForm" method="post" modelAttribute="contactMechanism_edit" cssClass="edit-form">
							<table>
	 						<tr> 	
	 							<td align="right" width="24%"><spring:message code="sirius.name"/>: </td>
								<td width="40%"><input class="input-disabled" value="${contactMechanism_edit.party.fullName}" disabled="disabled" size="36"/></td>
	 						</tr>
							<tr>
								<td align="right"><spring:message code="contactmechanism.name"/> :</td>
								<td> <form:input path="contactName" cssClass="inputbox" size="15"/></td>
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
	 							<td align="right"><spring:message code="contactmechanism.type"/> :</td>
								<td>
									<form:select path="contactMechanismType">
										<form:option value="PHONE" label="PHONE"/>
										<form:option value="MOBILE" label="MOBILE"/>
										<form:option value="FAX" label="FAX"/>
										<form:option value="EMAIL" label="EMAIL"/>
										<form:option value="WEBSITE" label="WEBSITE"/>
									</form:select>
								</td>
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
				<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${contactMechanism_edit.createdBy.fullName}'/> (<fmt:formatDate value='${contactMechanism_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${contactMechanism_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${contactMechanism_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
			<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
	$(function(){
		var $dialog = $('<div></div>').dialog({autoOpen: false, title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
		$('.item-button-save').click(function(){
			if(!$('#contact').val())
			{
				alert('Contact cannot be empty!');
				return;
			}
				
			$.ajax({
				url:"<c:url value='/page/contactmechanismedit.htm'/>",
				data:$('#editForm').serialize(),
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
								window.location="<c:url value='/page/partypreedit.htm?id=${contactMechanism_edit.party.id}'/>";
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
		});
		
		$('.item-button-back').click(function(){
			<c:if test='${empty redirectURL}'>
				window.location="<c:url value='/page/partypreedit.htm?id=${contactMechanism_edit.party.id}'/>";
			</c:if>
			<c:if test='${not empty redirectURL}'>
				window.location="<c:url value='/page/${redirectURL}?id=${relationshipId}&lastPanel=contact'/>";
			</c:if>
		});
	});
</script>
