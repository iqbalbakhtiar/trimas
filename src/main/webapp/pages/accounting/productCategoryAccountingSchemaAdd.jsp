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
	<%@ include file="/common/sirius-header.jsp" %>
</head>

<body onload="error();">

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
							<a class="item-button-list" href="<c:url value='/page/accountingschemapreedit.htm?id=${categorySchema.accountingSchema.id}'/>"><span><spring:message code="sirius.list"/></span></a>
                            <c:if test='${access.add}'>
								<a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
                            </c:if>
					  	</div>
					  
						<div class="main-box">
							<sesform:form id="addForm" name="addForm" method="post" modelAttribute="categorySchema">
								<table width="100%" style="border:none">
                                <tr>
									<td width="33%" align="right" nowrap="nowrap"><spring:message code="accountingschema"/> :</td>
					  				<td width="59%">
										<form:select id="accountingSchema" path="accountingSchema" cssClass="combobox-ext input-disabled" disabled='true'>
                                           	<form:option value='${categorySchema.accountingSchema.id}' label='${categorySchema.accountingSchema.name}' />
										</form:select>
								  	</td>
                                    <td width="8%">&nbsp;</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><spring:message code="accountingschema.prodcategory"/> :</td>
									<td>
										<form:select id="category" path="category" cssClass="combobox-ext">
                                           	<c:if test='${not empty categorySchema.category}'>
                                               	<form:option value='${categorySchema.category.id}' label='${categorySchema.category.name}' />
                                            </c:if>
										</form:select>
										<a class="item-popup" onclick="javascript:showcategory()"  title="Product Category" />
									</td>
                                    <td>&nbsp;</td>
								</tr>
                                <tr>
									<td nowrap="nowrap" align="right"><spring:message code="accountingschema.note"/> :</td>
									<td><form:textarea path="note" cols="55" rows="7"/></td>
                                    <td>&nbsp;</td>
								</tr>
                                <tr><td colspan="3">&nbsp;</td></tr>
                                <tr>
                                	<td align="right"><strong><spring:message code="accountingschema.salesaccount"/></strong></td>
                                	<td colspan="2">&nbsp;</td>
                                </tr>
                                <c:forEach items="${categorySchema.accounts}" var="closing" varStatus="status">
                                <c:if test="${closing.closingAccount.closingAccountType.groupType == 'PRD_SALES'}">
                                <tr>
                                    <td align="right"><c:out value='${closing.closingAccount.closingAccountType.name}'/> : </td>
                              		<td>
                                        <form:select id="glaccount[${status.index}]" path="accounts[${status.index}].closingAccount.account" cssClass="combobox-ext">
                                            <option value="${closing.closingAccount.account.id}"><c:out value='${closing.closingAccount.account.code} ${closing.closingAccount.account.name}'/></option>
                                        </form:select>
                                        <a class="item-popup" onclick="javascript:popup('glaccount[${status.index}]');"  title="Search Account" />
                                    </td>							
                                    <td width="8%">&nbsp;</td>
                                </tr>
                                </c:if>
                                </c:forEach>
                                <tr><td colspan="3">&nbsp;</td></tr>
                                <tr>
                                	<td align="right"><strong><spring:message code="accountingschema.procurementaccount"/></strong></td>
                                	<td colspan="2">&nbsp;</td>
                                </tr>
                                <c:forEach items="${categorySchema.accounts}" var="closing" varStatus="status">
                                <c:if test="${closing.closingAccount.closingAccountType.groupType == 'PRD_PROCUREMENT'}">
                                <tr>
                                    <td align="right"><c:out value='${closing.closingAccount.closingAccountType.name}'/> : </td>
                              		<td>
                                        <form:select id="glaccount[${status.index}]" path="accounts[${status.index}].closingAccount.account" cssClass="combobox-ext">
                                            <option value="${closing.closingAccount.account.id}"><c:out value='${closing.closingAccount.account.code} ${closing.closingAccount.account.name}'/></option>
                                        </form:select>
                                        <a class="item-popup" onclick="javascript:popup('glaccount[${status.index}]');"  title="Search Account" />
                                    </td>							
                                    <td width="8%">&nbsp;</td>
                                </tr>
                                </c:if>
                                </c:forEach>
                                <tr><td colspan="3">&nbsp;</td></tr>
                                <tr>
                                	<td align="right"><strong><spring:message code="accountingschema.inventoryaccount"/></strong></td>
                                	<td colspan="2">&nbsp;</td>
                                </tr>
                                <c:forEach items="${categorySchema.accounts}" var="closing" varStatus="status">
                                <c:if test="${closing.closingAccount.closingAccountType.groupType == 'PRODUCT'}">
                                <tr>
                                    <td align="right"><c:out value='${closing.closingAccount.closingAccountType.name}'/> : </td>
                              		<td>
                                        <form:select id="glaccount[${status.index}]" path="accounts[${status.index}].closingAccount.account" cssClass="combobox-ext">
                                            <option value="${closing.closingAccount.account.id}"><c:out value='${closing.closingAccount.account.code} ${closing.closingAccount.account.name}'/></option>
                                        </form:select>
                                        <a class="item-popup" onclick="javascript:popup('glaccount[${status.index}]');"  title="Search Account" />
                                    </td>							
                                    <td width="8%">&nbsp;</td>
                                </tr>
                                </c:if>
                                </c:forEach>
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
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: '<spring:message code="accountingschema.prodcategory"/>',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
	
		$('.item-button-save').click(function(){

			if(!$('#category').val())
			{
				alert('<spring:message code="notif.select1"/> <spring:message code="accountingschema.prodcategory"/> <spring:message code="notif.select2"/> !!!');
				return;
			}
			
			$.ajax({
				url:"<c:url value='/page/productcategoryaccountingschemaadd.htm'/>",
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
							window.location="<c:url value='/page/accountingschemapreedit.htm?id='/>"+json.id;
						}
						else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
					}
				},
				error : function(xhr) {
					console.log(xhr.responseText);
				},
			});
		});
	});

	function save()
	{
		if(!$('#category').val())
		{
			alert('<spring:message code="notif.select1"/> <spring:message code="accountingschema.prodcategory"/> <spring:message code="notif.select2"/> !!!');
			return;
		}
		
		document.addForm.action = "<c:url value='/page/productcategoryaccountingschemaadd.htm'/>";
		document.addForm.submit();
	}
	
	function showcategory()
	{
		openpopup("<c:url value='/page/popupproductcategoryview.htm?target=category'/>");
	}
			
	function popup(target)
	{
			
		openpopup("<c:url value='/page/popupglaccountview.htm?level=ACCOUNT&coa=${categorySchema.accountingSchema.chartOfAccount.id}&target='/>"+target);
	}
			
	function error()
	{
		<c:if test='${not empty message}'>
			alert('${message}');
		</c:if>
	}
</script>