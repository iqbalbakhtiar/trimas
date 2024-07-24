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
							<a class="item-button-list" href="<c:url value='/page/salespersonview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>
					  	</div>					  
						<div class="main-box">
							<sesform:form id="addForm" name="addForm" method="post" modelAttribute="salesPerson_add" enctype="multipart/form-data">
								<c:set var="newPerson" value=""/>
								<c:if test="${!salesPerson_add.newPerson}">
									<c:set var="newPerson" value="disabled='true' class='input-disabled'"/>
								</c:if>
 								<table style="border:none" width="100%">
                                <tr>
                                    <td nowrap="nowrap" align="right"><spring:message code="salesperson.organization"/></td>
                                    <td>:</td>
                                    <td>
                                        <form:select id="org" path="organization" cssClass="combobox-ext">
										<c:if test='${not empty salesPerson_add.organization}'>
											<form:option value='${salesPerson_add.organization.id}' label='${salesPerson_add.organization.firstName}'/>
										</c:if>
                                        </form:select>
                                        <input name="organizationId" value="${criteria.organization}" type="hidden"/>
                                    </td>
                                </tr>
 								<tr>
 									<td align="right"><spring:message code="salesperson.code"/></td>
                                    <td width="1%" align="center">:</td>
									<td>
										<c:if test="${salesPerson_add.newPerson}">
											<input value="Auto Number" disabled class="input-disabled"/>
										</c:if>
										<c:if test="${!salesPerson_add.newPerson}">
											<input value="${salesPerson_add.code}" disabled class="input-disabled"/>
										</c:if>
									</td>
									<td><form:errors path="code"/></td>
 								</tr>
                                <tr>
 									<td align="right"><spring:message code="salesperson.salutation"/></td>
                                    <td width="1%" align="center">:</td>
									<td>
										<c:if test="${salesPerson_add.newPerson}">
											<form:input path="salutation"/>
										</c:if>
										<c:if test="${!salesPerson_add.newPerson}">
											<input value="${salesPerson_add.salutation}" size='5' disabled class="input-disabled"/>
										</c:if>
									</td>
									<td><form:errors path="salutation"/></td>
	 							</tr>
 								<tr>
 									<td align="right"><spring:message code="salesperson.name"/></td>
                                    <td width="1%" align="center">:</td>
									<td>
										<c:if test="${salesPerson_add.newPerson}">
											<form:input id='firstName' path="firstName" size='36'/>
										</c:if>
										<c:if test="${!salesPerson_add.newPerson}">
											<input id='firstName' value="${salesPerson_add.fullName}" size='36' disabled class="input-disabled"/>
										</c:if>
									</td>
									<td><form:errors path="firstName"/></td>
	 							</tr>
 								<tr>
 									<td align="right"><spring:message code="salesperson.note"/></td>
                                    <td width="1%" align="center">:</td>
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
	
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: '<spring:message code="salesperson"/>',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
		$('.item-button-save').click(function(){
			if(!$first.val())
			{
				alert('<spring:message code="salesperson.name"/> <spring:message code="notif.empty"/> !!!');
				return;
			}
			
			$.ajax({
				url:"<c:url value='/page/salespersonadd.htm'/>",
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
							window.location="<c:url value='/page/salespersonpreedit.htm?id='/>"+json.id;
						}
						else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
					}
				}
			});
		});
	});
</script>