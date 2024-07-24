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
	<title>${title}</title>
	<%@ include file="/common/sirius-header.jsp"%>
	
	<script type="text/javascript">
		function add()
		{
			if(!$('#roleid').val()) {
				alert("Role ID can't be empty !");
				return;
			}
				
			if(!$('#rolename').val()) {
				alert("Role Name can't be empty !");
				return;
			}
		
			document.addForm.action = "<c:url value='/page/roleadd.htm'/>";
	  		document.addForm.submit();
		}
	</script>


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
							<a class="item-button-list" href="<c:url value='/page/roleview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<a class="item-button-save" href="javascript:add();"><span><spring:message code="sirius.save"/></span></a>
					  	</div>
						<div class="main-box">
							<sesform:form name="addForm" method="post" modelAttribute="adapter">
								<table width="100%" style="border:none" cellpadding="3">
								<tr>
									<td width="20%" align="right"><spring:message code="role.id"/></td>
								  	<td width="3%">:</td>
							  	  	<td width="77%" align="left"><form:input id="roleid" path="role.roleId" cssClass="inputbox"/></td>
								</tr>
								<tr>
									<td align="right"><spring:message code="role.name"/></td>
									<td>:</td>
									<td align="left"><form:input id="rolename" path="role.name" cssClass="inputbox"/></td>
								</tr>
								<tr>
									<td align="right"><spring:message code="sirius.note"/></td>
									<td>:</td>
									<td align="left"><form:textarea path="role.note" cols="50" rows="6"/></td>
								</tr>
								</table>
								<br/>
								<table width="75%" border="0" cellpadding="1" cellspacing="1" align="center">
								<tr>
									<td align="left"><strong><spring:message code="role.visibility"/></strong></td>
									<td align="left" colspan="2"><strong><spring:message code="role.level"/></strong></td>
									<td align="left"><strong><spring:message code="role.module"/></strong></td>
								</tr>
								<c:forEach items="${adapter.accessModules}" var="module" varStatus="status">
								<tr>
						 	  	  	<td width="7%" align="center"><form:checkbox path="accessModules[${status.index}].enabled"/></td>
									<td width="15%">
	 				  					<form:select path="accessModules[${status.index}].type">
											<form:options items="${adapter.types}" itemValue="id" itemLabel="name" />
										</form:select>
								  	</td>
								  	<td width="10%" align="right" style="padding-right:2.5%;">${module.code}</td>
								  	<td width="53%">${module.name}</td>
								</tr>
								</c:forEach>
								<tr>
									<td colspan="4">&nbsp;</td>
								</tr>
								<tr>
									<td colspan="4" align="left"><strong><spring:message code="role.company"/></strong></td>
								</tr>
								<c:forEach items="${adapter.organizationRoles}" var="organization" varStatus="status">
								<tr>
						 	  	  	<td align="center"><form:checkbox value="true" path="organizationRoles[${status.index}].enabled"/></td>
								  	<td colspan="3">${organization.name}</td>
								</tr>
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
