<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
                            <a class="item-button-list" href="<c:url value='/page/receiptmanualtypeview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
                            <c:if test='${access.edit}'>
                            	<a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
                            </c:if>
			    			<c:if test="${access.delete and receiptType_edit.deleteable}">
                            	<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/receiptmanualtypedelete.htm?id=${receiptType_edit.id}'/>');" title="<spring:message code='sirius.delete'/>"><span><spring:message code="sirius.delete"/></span></a>
                            </c:if>
                        </div>
                        <div class="main-box">			
							<sesform:form id="editForm" name="editForm" method="post" modelAttribute='receiptType_form'>
                            	<table width="100%" cellpadding="0" cellspacing="0" border="0">
                                <tr>
                                	<td width="56%" align="left" valign="top">
                                   		<table width="100%" style="border:none">
                                        <tr>
                                            <td width="24%" align="right"><spring:message code="sirius.code"/> : </td>
                                            <td width="76%"><input size="15" value="${receiptType_edit.code}" class="input-disabled" disabled/></td>					
                                        </tr>
		 								<tr>
		 									<td align="right"><spring:message code="sirius.name"/> :</td>
											<td><form:input id='name' path="name" size="35"/></td>
			 							</tr>
			 							<tr>
		 									<td align="right"><spring:message code="receiptreferencetype"/> :</td>
											<td>
												<form:select id="referenceType" path='referenceType'>
													<form:option value='${receiptType_edit.referenceType}'><spring:message code="receiptreferencetype.${receiptType_edit.referenceType.messageName}"/></form:option>
												</form:select>
											</td>
			 							</tr>
                                        <tr>
                                            <td nowrap="nowrap" align="right"><spring:message code="glaccount.account.name"/> :</td>
                                            <td>
                                                <form:select id="account" path="account" cssClass="combobox-ext">
                                                    <c:if test='${not empty receiptType_edit.account}'>
                                                        <form:option value='${receiptType_edit.account.id}' label='${receiptType_edit.account.code} - ${receiptType_edit.account.name}'/>
                                                    </c:if>
                                                </form:select>
                                                <a class="item-popup" onclick="openAccount();"  title='<spring:message code="glaccount.account"/>'/>
                                            </td>
                                        </tr>
										<tr>
		 									<td align="right"><spring:message code="sirius.active"/> :</td>
											<td>
												<form:radiobutton path="enabled" value="true"/><spring:message code="sirius.yes"/>
												<form:radiobutton path="enabled" value="false"/><spring:message code="sirius.no"/>										
											</td>
											<td><form:errors path="enabled"/></td>
			 							</tr>
                                        <tr>
                                            <td width="24%" align="right"><spring:message code="sirius.note"/> : </td>
                                            <td><form:textarea path='note' cols='45' rows='6'/></td>					
                                        </tr>
                                        </table>
                                    </td>
                                </tr>
                                </table>
							</sesform:form>
						</div>
						<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${receiptType_edit.createdBy.fullName}'/> (<fmt:formatDate value='${receiptType_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${receiptType_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${receiptType_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
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
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: '<spring:message code="receipttype"/>',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
	
		$('.item-button-save').click(function(){
			$.ajax({
				url:"<c:url value='/page/receiptmanualtypeedit.htm'/>",
				data:$('#editForm').serialize(),
				method : 'POST',
				dataType : 'json',
				beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
				success : function(json) {
					if(json)
					{
						if(json.status == 'OK')
						{
							$dialog.dialog('close');
							window.location="<c:url value='/page/receiptmanualtypepreedit.htm?id='/>"+json.id;
						}
						else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
					}
				}
			});		
		});
	});
		
	function openAccount()
	{
		openpopup("<c:url value='/page/popupglaccountview.htm?target=account'/>");
	}
</script>
