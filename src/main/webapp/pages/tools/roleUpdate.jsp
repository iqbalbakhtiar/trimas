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
	<style type="text/css" media="screen">
		@import url("<c:url value='/assets/sirius.css'/>");
	</style>

	<script type="text/javascript">
		function edit()
		{
			document.editForm.action = "<c:url value='/page/roleedit.htm'/>";
	  		document.editForm.submit();
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
							 <c:if test='${access.add and access.edit}'>
								<a class="item-button-copy" href="<c:url value='/page/roleprecopy.htm'/>"><span><spring:message code="sirius.copy"/></span></a>
							</c:if>
							<a class="item-button-save" href="javascript:edit();"><span><spring:message code="sirius.save"/></span></a>
					  	</div>
						<div class="main-box">
							<sesform:form name="editForm" method="post" modelAttribute="adapter">
								<table width="100%" style="border:none">
								<tr>
									<td width="20%" align="right"><spring:message code="role.id"/></td>
								  	<td width="3%">:</td>
							  	  	<td width="77%" align="left"><form:input path="role.roleId" cssClass="inputbox"/></td>
								</tr>
								<tr>
									<td align="right"><spring:message code="role.name"/></td>
									<td>:</td>
									<td align="left"><form:input path="role.name" cssClass="inputbox"/></td>
								</tr>
								<tr>
									<td align="right"><spring:message code="sirius.note"/></td>
									<td>:</td>
									<td align="left"><form:textarea path="role.note" cols="50" rows="6"/></td>
								</tr>
								</table>
								<br/>
								<div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 350px;">
									<div id="module" dojoType="ContentPane" label="<spring:message code="role.module"/>" class="tab-pages" refreshOnShow="true">
										<table class="table-list" width="75%" cellspacing="0" cellpadding="0" align="center">
										<thead>
										<tr>
											<th align="left"><spring:message code="role.visibility"/></th>
											<th align="left" colspan="2"><spring:message code="role.level"/></th>
											<th align="left"><spring:message code="role.module"/></th>
										</tr>
										</thead>
										<tbody>
										<c:forEach items="${adapter.accessModules}" var="module" varStatus="status">
										<tr>
								 	  	  	<td width="7%" align="center"><form:checkbox path="accessModules[${status.index}].enabled" onclick="changeCheckbox(${status.index})" id="chkModule_${status.index}"/></td>
											<td width="15%">
			 				  					<form:select path="accessModules[${status.index}].type" id="module_${status.index}">
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
										<tbody>
										</table>
									</div>
									<div id="company" dojoType="ContentPane" label="<spring:message code="organization"/>" class="tab-pages">
										<table class="table-list" width="75%" cellspacing="0" cellpadding="0" align="center">
											<thead>
										<tr>
											<th colspan="2" align="center" align="left"><spring:message code="role.accessibility"/> </th>
											<th width="15%" align="right"><spring:message code="sirius.name"/></th>
											<th width="65%" align="left">&nbsp;</th>
										</tr>
										</thead>
										<tbody>
											<c:forEach items="${adapter.organizationRoles}" var="organization" varStatus="status">
											<tr>
									 	  	  	<td width="5%"><form:checkbox value="true" path="organizationRoles[${status.index}].enabled"/></td>
											  	<td width="5%">${organization.code}</td>
											  	<td >${organization.name}</td>
											  	<td>&nbsp;</td>
											</tr>
											</c:forEach>
											<tbody>
										</table>
									</div>
									<div id="facility" dojoType="ContentPane" label="<spring:message code="facility"/>" class="tab-pages">
										<table class="table-list" width="75%" cellspacing="0" cellpadding="0" align="center">
											<thead>
										<tr>
											<th colspan="2" align="center" align="left"><spring:message code="role.accessibility"/></th>
											<th width="15%" align="right"><spring:message code="sirius.name"/></th>
											<th width="65%" align="left">&nbsp;</th>
										</tr>
										</thead>
										<tbody>
											<c:forEach items="${adapter.facilityRoles}" var="facility" varStatus="status">
											<tr>
									 	  	  	<td width="5%"><form:checkbox value="true" path="facilityRoles[${status.index}].enabled"/></td>
											  	<td width="10%">${facility.code}</td>
											  	<td >${facility.name}</td>
											  	<td>&nbsp;</td>
											</tr>
											</c:forEach>
											<tbody>
										</table>
									</div>
									<div id="grid" dojoType="ContentPane" label="<spring:message code="grid"/>" class="tab-pages">
										<table class="table-list" width="75%" cellspacing="0" cellpadding="0" align="center">
											<thead>
										<tr>
											<th colspan="2" align="center" align="left"><spring:message code="role.accessibility"/></th>
											<th width="15%" align="right"><spring:message code="sirius.name"/></th>
											<th width="65%" align="left">&nbsp;</th>
										</tr>
										</thead>
										<tbody>
											<c:forEach items="${adapter.gridRoles}" var="grid" varStatus="status">
											<tr>
									 	  	  	<td width="5%"><form:checkbox value="true" path="gridRoles[${status.index}].enabled"/></td>
											  	<td width="10%">${grid.code}</td>
											  	<td >${grid.name}</td>
											  	<td>&nbsp;</td>
											</tr>
											</c:forEach>
											<tbody>
										</table>
									</div>
									<div id="category" dojoType="ContentPane" label="<spring:message code="category"/>" class="tab-pages">
										<table class="table-list" width="75%" cellspacing="0" cellpadding="0" align="center">
											<thead>
										<tr>
											<th colspan="2" align="center" align="left"><spring:message code="role.accessibility"/></th>
											<th width="15%" align="right"><spring:message code="sirius.name"/></th>
											<th width="65%" align="left">&nbsp;</th>
										</tr>
										</thead>
										<tbody>
											<c:forEach items="${adapter.categoryRoles}" var="category" varStatus="status">
											<tr>
									 	  	  	<td width="5%"><form:checkbox value="true" path="categoryRoles[${status.index}].enabled"/></td>
											  	<td width="10%">${category.code}</td>
											  	<td >${category.name}</td>
											  	<td>&nbsp;</td>
											</tr>
											</c:forEach>
											<tbody>
										</table>
									</div>
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
<script type="text/javascript">
	function changeCheckbox(i){
		var chkStatus=$("#chkModule_"+i).prop("checked");
		if(chkStatus)
			$("#module_"+i).val(6);
		else
			$("#module_"+i).val(1);
	}
</script>
</html>
