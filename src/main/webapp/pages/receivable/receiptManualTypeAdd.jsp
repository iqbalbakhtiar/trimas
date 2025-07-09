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
                            <c:if test='${access.add}'>
                            	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
                            </c:if>
                        </div>
                        <div class="main-box">			
							<sesform:form id="addForm" name="addForm" method="post" modelAttribute='receiptType_form'>
                            	<table width="100%" cellpadding="0" cellspacing="0" border="0">
                                <tr>
                                	<td width="53%" align="left" valign="top">
                                   	  <table width="100%" style="border:none">
                                        <tr>
                                            <td width="24%" align="right"><spring:message code="sirius.code"/> :</td>
                                            <td width="76%"><input size="25" value='<spring:message code="sirius.auto.generated"/>' class="input-disabled" disabled/></td>					
                                        </tr>
		 								<tr>
		 									<td align="right"><spring:message code="sirius.name"/> :</td>
											<td><form:input id='name' path="name" cssClass="inputbox"/></td>
			 							</tr>
			 							<tr>
		 									<td align="right"><spring:message code="receiptreferencetype"/> :</td>
											<td>
												<form:select id="referenceType" path='referenceType'>
													<c:forEach items="${referenceTypes}" var="refType">
														<form:option value='${refType}'><spring:message code="receiptreferencetype.${refType.messageName}"/></form:option>
													</c:forEach>
												</form:select>
											</td>
			 							</tr>
                                        <tr>
                                            <td nowrap="nowrap" align="right"><spring:message code="glaccount.account.name"/> :</td>
                                            <td>
                                                <form:select id="account" path="account" cssClass="combobox-ext">
                                                </form:select>
                                                <a class="item-popup" onclick="openAccount();"  title='<spring:message code="glaccount.account"/>'/>
                                            </td>
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
					</div>
				</div>
			</div>
		</div>
	</div>
  	<%@ include file="/common/sirius-footer.jsp"%>
</div>
</body>
</html>
<script type="text/javascript" language="javascript">
	$(function(){
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: '<spring:message code="receipttype"/>',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
	
		$('.item-button-save').click(function(e){
			if(!$('#name').val())
			{
				alert('<spring:message code="sirius.name"/> <spring:message code="notif.empty"/> !!!');
				return;
			}
			
			if(!$('#account').val())
			{
				alert('<spring:message code="notif.select1"/> <spring:message code="glaccount"/> <spring:message code="notif.select2"/> !!!');
				return;
			}

			$.ajax({
				url:"<c:url value='/page/receiptmanualtypeadd.htm'/>",
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