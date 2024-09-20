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
</head>
<body onload="display()">
	<%@ include file="/common/sirius-header.jsp"%>

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
							<a class="item-button-list" href="<c:url value='/page/productcategoryview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<c:if test="${access.edit}">
								<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
							</c:if>
						</div>
			
						<div class="main-box">
							<sesform:form id="editForm" name="editForm" method="post" modelAttribute="category_edit">
								<table style="border:none" width="100%" id="category_table">
									<tr>
										<td width="23%" align="right"><spring:message code="productcategory.id"/> :</td>
									  	<td width="30%"><input class="inputbox input-disabled" value="Auto Generated" disabled/></td>
								  	</tr>	
									<tr>
										<td align="right"><spring:message code="productcategory"/> :</td>
										<td><form:input id='name' path="name" maxlength="50" disabled='true' class='input-disabled'/></td>
										<td><form:errors path="name"/></td>							
									</tr>
									<tr>
										<td align="right"><spring:message code="sirius.type"/> :</td>
										<td>
											<form:select id="type" path="type" cssClass="combobox input-disabled" disabled='true'>
												<form:option value="STOCK">STOCK</form:option>
												<form:option value="NONSTOCK">NON STOCK</form:option>
											</form:select>
										</td>							
									</tr>
                                    <tr>
										<td align="right"><spring:message code="sirius.note"/> :</td>
										<td><form:textarea path="note" cols='55' rows='6'/></td>
										<td>&nbsp;</td>
									</tr>
							  	</table>
							</sesform:form>
						</div>
						<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${category_edit.createdBy.fullName}'/> (<fmt:formatDate value='${category_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${category_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${category_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
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
		var $dialog = $('<div></div>').dialog({autoOpen: false, title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});	
		$('.item-button-save').click(function(){
			$.ajax({
				url:"<c:url value='/page/productcategoryedit.htm'/>",
				data:$('#editForm').serialize(),
				type : 'POST',
				dataType : 'json',
				beforeSend : beforeSend($dialog, '<spring:message code="notif.updating"/>'),
				success : function(json) {
					if(json)
					{
						if(json.status == 'OK')
						{
							$dialog.dialog('close');
							window.location="<c:url value='/page/productcategoryview.htm'/>";
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
</script>