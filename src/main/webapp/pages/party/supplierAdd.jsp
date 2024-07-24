<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
							<a class="item-button-list" href="<c:url value='/page/supplierview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
					  	</div>					  
						<div class="main-box">
							<sesform:form id="addForm" name="addForm" method="post" modelAttribute="supplier_add" enctype="multipart/form-data">
 								<table style="border:none" width="100%">
 								<tr>
 									<td width="34%" align="right"><spring:message code="supplier.type"/> :</td>
							  		<td width="64%">
			  					  		<form:select path="type" class="combobox">
											<form:option value="personal"><spring:message code="party.personal"/></form:option>
											<form:option value="group"><spring:message code="party.group"/></form:option>
										</form:select>
									</td>
									<td>&nbsp;</td>
 								</tr>
                             	<tr>
                                    <td nowrap="nowrap" align="right"><spring:message code="supplier.company"/> :</td>
                                    <td>
                                        <form:select id="org" path="organization" cssClass="combobox-ext">
										<c:if test='${not empty supplier_add.organization}'>
											<form:option value='${supplier_add.organization.id}' label='${supplier_add.organization.firstName}'/>
										</c:if>
                                        </form:select>
                                        <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');"  title="Company Structure" />
                                    </td>
                                    <td>&nbsp;</td>
                                </tr>
 								<tr>
 									<td align="right"><spring:message code="supplier.code"/> :</td>
									<td><input value="Auto Number" disabled class="input-disabled"/></td>
									<td><form:errors path="code"/></td>
 								</tr>
                                <tr>
 									<td align="right"><spring:message code="supplier.salutation"/> :</td>
									<td><form:input path="salutation" size='5'/></td>
									<td><form:errors path="salutation"/></td>
	 							</tr>
 								<tr>
 									<td align="right"><spring:message code="supplier.name"/> :</td>
									<td><form:input cssClass='check_field' id='firstName' path="firstName" size='36'/></td>
									<td><form:errors path="firstName"/></td>
	 							</tr>
                                <tr>
 									<td align="right"><spring:message code="supplier.taxcode"/> :</td>
									<td><form:input path="taxCode"/></td>
									<td><form:errors path="taxCode"/></td>
	 							</tr>
                                <tr>
 									<td align="right"><spring:message code="supplier.accountnumber"/> :</td>
									<td><form:input path="accountNumber"/></td>
									<td><form:errors path="accountNumber"/></td>
	 							</tr>
	                            <tr>
	                              	<td align="right"><spring:message code="supplier.status"/> :</td>
	                            	<td>
	                            		<form:radiobutton path='pkp' value='true'/><spring:message code="supplier.pkp"/>
	                            		<form:radiobutton path='pkp' value='false'/><spring:message code="supplier.nonpkp"/>
	                            	</td>
	                            	<td>&nbsp;</td>
	                            </tr>
 								<tr>
 									<td align="right"><spring:message code="supplier.note"/> :</td>
									<td><form:textarea path="note" rows="6" cols="45"/></td>
									<td>&nbsp;</td>
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
$(function(){
	$first = $('#firstName');

	var $dialog = $('<div></div>').dialog({autoOpen: false,title: '<spring:message code="supplier"/>',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
	$('.item-button-save').click(function()
	{
		if(!$first.val())
		{
			alert('<spring:message code="supplier.name"/> <spring:message code="notif.empty"/> !!!');
			return;
		}
		
		$.ajax({
			url:"<c:url value='/page/supplieradd.htm'/>",
			data:$('#addForm').serialize(),
			type : 'POST',
			dataType : 'json',
			beforeSend:function()
			{
				$dialog.empty();
				$dialog.html('<spring:message code="notif.saving"/>');
				$dialog.dialog('open');
			},
			success : function(json) {
				if(json)
				{
					if(json.status == 'OK')
					{
						$dialog.dialog('close');
						window.location="<c:url value='/page/supplierpreedit.htm?id='/>"+json.data.id;
					}
					else
					{
						$dialog.empty();
						$dialog.html('<spring:message code="notif.profailed"/> :<br/>'+json.message);
					}
				}
			}
		});
	});
});
</script>