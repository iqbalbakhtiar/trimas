<%@ include file="/common/sirius-general-top.jsp"%>	
					<div class="toolbar">
						<a class="item-button-back" /><span><spring:message code="sirius.back"/></span></a>
						<c:if test='${access.add}'>
							<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
						</c:if>
					</div>					  
					<div class="main-box">
						<sesform:form id='addForm' name="addForm" method="post" modelAttribute="partyBankAccount_edit">
						<table style="border:none" width="100%">
                        <tr> 	
 							<td align="right" width="24%"><spring:message code="sirius.name"/> :</td>
							<td width="40%"> <input class="input-disabled" value="${partyBankAccount_edit.party.fullName}" disabled="disabled" size="36"/></td>
 						</tr>
 						<tr>
							<td align="right"><spring:message code="bankaccount.account"/> :</td>
							<td>
								<form:select id="bank" path="bankAccount" cssClass="combobox-ext">
									<form:option value='${partyBankAccount_edit.bankAccount.id}' label='${partyBankAccount_edit.bankAccount.bankName} - ${partyBankAccount_edit.bankAccount.accountName}'/>
								</form:select>
								<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupbankaccountview.htm?target=bank'/>');"  title='<spring:message code="bankaccount.account"/>' />
							</td>
						</tr>
						<tr>
							<td align="right"><spring:message code="sirius.status"/> :</td>
							<td>
								<form:radiobutton path="enabled" value="true" label="Active"/>
								<form:radiobutton path="enabled" value="false" label="Inactive"/>
							</td>
						</tr>
                        <tr>
                            <td align="right"><spring:message code="sirius.default"/> :</td>
                            <td>
                                <form:radiobutton path="selected" value='true' label='Yes'/>
                                <form:radiobutton path="selected" value='false' label='No'/>
                            </td>
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
			$.ajax({
				url:"<c:url value='/page/partybankaccountedit.htm'/>",
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
								window.location="<c:url value='/page/partypreedit.htm?id=${partyBankAccount_edit.party.id}'/>";
							</c:if>
							<c:if test='${not empty redirectURL}'>
								window.location="<c:url value='/page/${redirectURL}?id=${relationshipId}&lastPanel=bank'/>";
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
				window.location="<c:url value='/page/partypreedit.htm?id=${partyBankAccount_edit.party.id}'/>";
			</c:if>
			<c:if test='${not empty redirectURL}'>
				window.location="<c:url value='/page/${redirectURL}?id=${relationshipId}&lastPanel=bank'/>";
			</c:if>
		});
	});
</script>
