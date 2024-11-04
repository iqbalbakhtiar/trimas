<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
	<title>${title}</title>
    <%@ include file="/common/sirius-header.jsp"%>
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
							<a class="item-button-list" href="<c:url value='/page/bankaccountview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<c:if test='${access.add}'>
	                            <a class="item-button-save" style="cursor: pointer;"><span><spring:message code="sirius.save"/></span></a>
                            </c:if>
					  	</div>
					  
						<div class="main-box">
							<sesform:form cssClass="edit-form" id="addForm" name="addForm" method="post" modelAttribute="bankAccount_form">
			 				<table width="100%" style="border:none" class="space">
							<tr>
								<td width="29%" align="right"><spring:message code="bankaccount.code"/> :</td>
					 		  	<td width="70%"><form:input id="code" path="code" maxlength="30" cssClass="inputbox"/></td>
							</tr>
							<tr>
								<td align="right"><spring:message code="bankaccount.bankname"/> :</td>
								<td><form:input id="bankName" path="bankName" maxlength="50" size='50'/></td>
							</tr>
							<tr>
								<td align="right"><spring:message code="bankaccount.accountname"/> :</td>
								<td><form:input id="accountName" path="accountName" maxlength="50" size='50'/></td>
							</tr>
                            <tr>
								<td width="29%" align="right"><spring:message code="bankaccount.accountno"/> :</td>
				  				<td width="70%"><form:input id="accountNo" path="accountNo" maxlength="50" cssClass="inputbox"/></td>
							</tr>
                            <tr>
								<td width="29%" align="right"><spring:message code="bankaccount.type"/> :</td>
				  				<td width="70%">
                                	<form:select path='accountType'>
                                    	<form:option value='CASH' label='CASH'/>
                                        <form:option value='BANK' label='BANK'/>
                                    </form:select>
                                </td>
							</tr>
                            <tr>
								<td align="right"><spring:message code="bankaccount.holder"/> :</td>
                                <td>
                                	<form:select id="org" path="holder" cssClass="combobox-ext">
									<c:if test='${not empty bankAccount_form.organization}'>
										<form:option value='${bankAccount_form.organization.id}' label='${bankAccount_form.organization.fullName}' />
									</c:if>
									</form:select>
									<a class="item-popup" onclick="openHolder()"  title='<spring:message code="organization.structure"/>' />
                                </td>
							</tr>
                            <tr>
                            	<td align="right"><spring:message code="bankaccount.note"/> :</td>
						  		<td><form:textarea cols="50" rows="6" path="note" /></td>
                            </tr>
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
$(function()
{
	var $dialog = $('<div></div>').dialog({autoOpen: false,title: '<spring:message code="bankaccount"/>',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
	$('.item-button-save').click(function(e)
	{
		if(validateForm()) {
			save();	
		}
		
	});

});

function validateForm() {
	if(!$('#code').val())
	{
		alert('<spring:message code="bankaccount.code"/> <spring:message code="notif.empty"/> !!!');
		return false;
	}

	if(!$('#bankName').val())
	{
		alert('<spring:message code="bankaccount.bankname"/> <spring:message code="notif.empty"/> !!!');
		return false;
	}

	if(!$('#accountName').val())
	{
		alert('<spring:message code="bankaccount.accountname"/> <spring:message code="notif.empty"/> !!!');
		return false;
	}

	if(!$('#accountNo').val())
	{
		alert('<spring:message code="bankaccount.accountno"/> <spring:message code="notif.empty"/> !!!');
		return false;
	}

	if(!$('#org').val())
	{
		alert('<spring:message code="notif.select1"/> <spring:message code="bankaccount.holder"/> <spring:message code="notif.select2"/> !!!');
		return false;
	}
	
	return true;
}

function save() {
	$.ajax({
		url:"<c:url value='/page/bankaccountadd.htm'/>",
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
					window.location="<c:url value='/page/bankaccountview.htm'/>";
				}
				else
						afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
			}
		}
	});
}

function openHolder() {
	const baseUrl = '<c:url value="/page/popuppartyrelationview.htm"/>';
	const params = {
		target: 'partyGroup',
		base: false
	};

	openpopup(buildUrl(baseUrl, params));
}
</script>