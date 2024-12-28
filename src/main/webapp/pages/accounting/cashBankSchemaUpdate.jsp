<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName()+":"+request.getServerPort()+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
	<title>${title}</title>
	<%@ include file="/common/sirius-header.jsp" %>
</head>
<body>
<div id="se-r00">
	<div id="se-r01">&nbsp;</div>
	<div id="se-r02">&nbsp;</div>
</div>
<div id="se-containers">

	<%@ include file="/common/sirius-menu.jsp"%>

	<div id="se-navigator">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td width="60%">${breadcrumb}</td>
			<td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
		</tr>
		</table>	
	</div>

	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
						
						<h1 class="page-title">${pageTitle}</h1>
						
					  	<div class="toolbar">
							<a class="item-button-list" href="<c:url value='/page/accountingschemapreedit.htm?id=${cashbank_schema_edit.accountingSchema.id}'/>"><span><spring:message code="sirius.list"/></span></a>
							<c:if test='${access.edit}'>
								<a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
							</c:if>
					  	</div>
					  
						<div class="main-box">
							<sesform:form id="addForm" name="addForm" method="post" modelAttribute="cashbank_schema_edit">
								<table width="100%" style="border:none">
								<tr>
									<td width="33%" align="right" nowrap="nowrap"><spring:message code="accountingschema"/> :</td>
					  				<td width="59%">
										<select class="combobox-ext input-disabled" disabled='true'>
											<option>${cashbank_schema_edit.accountingSchema.name}</option>
										</select>
								  	</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><spring:message code="accountingschema.closingtype"/> :</td>
									<td>
										<form:select id="closingAccountType" path="closingAccountType" cssClass="combobox">
											<form:option value='${cashbank_schema_edit.closingAccountType.id}' label='${cashbank_schema_edit.closingAccountType.name}'/>
										</form:select>
									</td>
								</tr>
								<tr>
									<td align="right"><spring:message code="accountingschema.cashbanktype"/> : </td>
									<td>
										<form:select id='methodType' path='methodType'>
										    <form:option value='CASH' label='CASH'/>
										    <form:option value='TRANSFER' label='TRANSFER'/>
										</form:select>
									  </td>
								</tr>
								<tr>
									<td align="right"><spring:message code="accountingschema.cashbank"/> : </td>
									<td>
										<form:select id="bankAccount" path="bankAccount" cssClass="combobox-ext">
											<form:option value='${cashbank_schema_edit.bankAccount.id}' label='${cashbank_schema_edit.bankAccount.bankName} - ${cashbank_schema_edit.bankAccount.accountNo}'/>
										</form:select>
										<a class="item-popup" onclick="openaccount('bankAccount','methodType');"  title="Bank Account" />
									</td>
								</tr>
								<tr><td colspan="3">&nbsp;</td></tr>
							</table>
							</sesform:form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="/common/sirius-footer.jsp"%>

</div>
</body>
</html>
<script type="text/javascript">
	function openaccount(target, method)
	{
		var methodType = document.getElementById(method);
		var method = 'CASH';
		
		if(methodType.value != 'CASH')
			method = 'BANK';

		openpopup("<c:url value='/page/popupbankaccountview.htm?target='/>"+target+'&organization=${cashbank_schema_edit.accountingSchema.organization.id}&type='+method);
	}
	
	$(function(){
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: '<spring:message code="accountingschema.cashbank"/>',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
	
		$('.item-button-save').click(function()
		{
			if(!$('#closingAccountType').val())
			{
				alert('<spring:message code="notif.select1"/> <spring:message code="accountingschema.closingtype"/> <spring:message code="notif.select2"/> !!!');
				return;
			}
			
			if(!$('#bankAccount').val())
			{
				alert('<spring:message code="notif.select1"/> <spring:message code="accountingschema.cashbank"/> <spring:message code="notif.select2"/> !!!');
				return;
			}
			
			$.ajax({
				url:"<c:url value='/page/cashbankschemaedit.htm'/>",
				data:$('#addForm').serialize(),
				method : 'POST',
				dataType : 'json',
				beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
				success : function(json) {
					if(json)
					{
						if(json.status == 'OK')
						{
							$dialog.dialog('close');
							window.location="<c:url value='/page/accountingschemapreedit.htm?id=${cashbank_schema_edit.accountingSchema.id}'/>";
						}
						else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
					}
				}
			});		
		});
	});
</script>