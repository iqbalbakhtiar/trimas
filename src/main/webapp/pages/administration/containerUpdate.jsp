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
                        <div class="clears">&nbsp;</div>
						
					  	<div class="toolbar">
							<a class="item-button-list" href="<c:url value='/page/gridpreedit.htm?id=${container.grid.id}'/>"><span><spring:message code="sirius.list"/></span></a>
							<c:if test='${access.add}'>
								<a class="item-button-save" href="javascript:save();"><span><spring:message code="sirius.save"/></span></a>
							</c:if>
					  	</div>
					  
						<div class="main-box">
							<sesform:form id="editForm" name="editForm" method="post" modelAttribute="container">
								<table width="100%" style="border:none">
								<tr>
									<td width="20%" nowrap="nowrap" align="right"><spring:message code="container.code"/> :</td>
								  	<td width="80%"><form:input path='code' size="48" disabled="true" cssClass="input-disabled"/></td>
								</tr>
								<tr>
									<td width="20%" nowrap="nowrap" align="right"><spring:message code="container.name"/> :</td>
								  	<td width="80%"><form:input path='name' size="48" disabled="true" cssClass="input-disabled"/></td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><spring:message code="container.type"/> :</td>
									<td>
										<form:select id="containerType" path="containerType" cssClass="combobox input-disabled" disabled='true'>
										<form:option value='${container.containerType.id}' label='${container.containerType.name}' />
										</form:select>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right" valign="top"><spring:message code="container.note"/> :</td>
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
	function save()
	{		
		document.editForm.action = "<c:url value='/page/containeredit.htm'/>";
		document.editForm.submit();
	}
					
	function error()
	{
		<c:if test='${not empty message}'>
			alert('${message}');
		</c:if>
	}
</script>