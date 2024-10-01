<%@ include file="/common/sirius-general-top.jsp"%>	
					<div class="toolbar">
						<a class="item-button-back" /><span><spring:message code="sirius.back"/></span></a>
						<c:if test='${access.add}'>
							<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
						</c:if>
					</div>					  
					<div class="main-box">
						<sesform:form id='addForm' name="addForm" method="post" modelAttribute="plafon_add">
						<table style="border:none" width="100%">
						<tr> 	
 							<td align="right" width="24%"><spring:message code="organization"/> :</td>
							<td width="40%"> <input class="input-disabled" value="${plafon_add.partyRelationship.partyTo.fullName}" disabled="disabled" size="36"/></td>
 						</tr>
                        <tr> 	
 							<td align="right" width="24%"><spring:message code="sirius.name"/> :</td>
							<td width="40%"> <input class="input-disabled" value="${plafon_add.partyRelationship.partyFrom.fullName}" disabled="disabled" size="36"/></td>
 						</tr>
 						<tr>
							<td width="18%" align="right"><spring:message code="plafon"/> :</td>
							<td width="40%"><form:input path="plafon" size="10"/></td>							
						</tr>
						<tr>
							<td align="right"><spring:message code="sirius.status"/> :</td>
							<td>
								<form:radiobutton path="active" value="true" label="Active"/>
								<form:radiobutton path="active" value="false" label="Inactive"/>
							</td>
						</tr>
						<tr>
							<td align="right"><spring:message code="plafon.validfrom"/> :</td>
							<td><input id="validFrom" name="validFrom" class="datepicker"/></td>
						</tr>
						<tr>
							<td align="right"><spring:message code="sirius.note"/></td>
							<td><form:textarea path="note" rows="6" cols="45"/></td>
							<td>&nbsp;</td>
						</tr>
 						</table>
						</sesform:form>
					</div>
				<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
	$(function()
	{
		var $dialog = $('<div></div>').dialog({autoOpen: false, title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
		$('.item-button-save').click(function()
		{
			if(!$('#plafon').val())
			{
				alert('Plafon cannot be empty!');
				return;
			}
				
			$.ajax({
				url:"<c:url value='/page/plafonadd.htm'/>",
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
								window.location="<c:url value='/page/partypreedit.htm?id=${plafon_add.party.id}'/>";
							</c:if>
							<c:if test='${not empty redirectURL}'>
								window.location="<c:url value='/page/${redirectURL}?id=${relationshipId}&lastPanel=plafon'/>";
							</c:if>
						}
						else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
					}
				}
			});
		});
		
		$('.item-button-back').click(function()
		{
			<c:if test='${empty redirectURL}'>
				window.location="<c:url value='/page/partypreedit.htm?id=${plafon_add.party.id}'/>";
			</c:if>
			<c:if test='${not empty redirectURL}'>
				window.location="<c:url value='/page/${redirectURL}?id=${relationshipId}&lastPanel=plafon'/>";
			</c:if>
		});
	});
</script>
