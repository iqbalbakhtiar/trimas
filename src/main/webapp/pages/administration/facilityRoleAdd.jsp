<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + 		request.getServerPort()+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
	<title>Facility Role >> Add</title>
	<%@ include file="/common/sirius-header.jsp" %>
    	<style type="text/css" media="screen">
    	@import url("<c:url value='/assets/sirius.css'/>");
		@import url("<c:url value='/css/jquery-ui-1.8.2.custom.css'/>");
    </style>
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
			<td width="60%">Company Administration > Party > Facility Role > Add</td>
			<td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
		</tr>
		</table>	
	</div>

	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
						
						<h1 class="page-title">A08 - Facility Role</h1>
						
					  	<div class="toolbar">
							<a class="item-button-list" href="<c:url value='/page/partypreedit.htm?id=${facility_role_add.party.id}'/>"><span>List</span></a>
							<c:if test='${access.add}'>
                            	<a class="item-button-save"><span>Save</span></a>
                            </c:if>
					  	</div>
					  
						<div class="main-box">
							<sesform:form id="addForm" name="addForm" method="post" modelAttribute="facility_role_add">
								<table width="100%" style="border:none">
                                <tr>
									<td nowrap="nowrap" align="right">Facility :</td>
									<td>
										<form:select id="facility" path="facility" cssClass="combobox">
                                        	<c:forEach items='${facilitys}' var='type'>
                                               	<form:option value='${type.id}' label='${type.name}' />
                                            </c:forEach>
										</form:select>
									</td>
								</tr>
                                <tr>
									<td nowrap="nowrap" align="right">Type :</td>
									<td>
										<form:select id="facilityRoleType" path="facilityRoleType" cssClass="combobox">
                                        	<c:forEach items='${types}' var='type'>
                                               	<form:option value='${type.id}' label='${type.name}' />
                                            </c:forEach>
										</form:select>
									</td>
								</tr>
                                <tr>
									<td nowrap="nowrap" align="right">Default :</td>
									<td>
										<form:radiobutton path='selected' value='true' label='Yes'/>
                                        <form:radiobutton path='selected' value='false' label='No'/> 
									</td>
								</tr>
                                <tr>
									<td nowrap="nowrap" align="right">Note :</td>
									<td><form:textarea path="note" cols="55" rows="7"/></td>
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
		var $dialog = $('<div></div>').dialog({autoOpen: false, title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
		$('.item-button-save').click(function(){
			$.ajax({
				url:"<c:url value='/page/facilityroleadd.htm'/>",
				data:$('form').serialize(),
				method : 'POST',
				dataType : 'json',
				beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
				success : function(json) {
					if(json)
					{
						if(json.status == 'OK')
						{
							$dialog.dialog('close');
							
							<c:if test='${empty redirectURL}'>
								window.location="<c:url value='/page/partypreedit.htm?id=${facility_role_add.party.id}'/>";
							</c:if>
							<c:if test='${not empty redirectURL}'>
								window.location="<c:url value='/page/${redirectURL}?id=${facility_role_add.party.id}&relationshipId=${relationshipId}'/>";
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
				window.location="<c:url value='/page/partypreedit.htm?id=${facility_role_add.party.id}'/>";
			</c:if>
			<c:if test='${not empty redirectURL}'>
				window.location="<c:url value='/page/${redirectURL}?id=${facility_role_add.party.id}&relationshipId=${relationshipId}'/>";
			</c:if>
		});
	});
</script>