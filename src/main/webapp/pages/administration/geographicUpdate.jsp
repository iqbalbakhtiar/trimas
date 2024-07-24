<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName()+":" +request.getServerPort()+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
	<%@ include file="/common/sirius-header.jsp"%>
	<title>${title}</title>
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
							<a class="item-button-list" href="<c:url value='/page/geographicview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<a class="item-button-save" href="javascript:save();"><span><spring:message code="sirius.save"/></span></a>
					  	</div>
						
						<div class="main-box">
				 			<sesform:form cssClass="edit-form" id="editForm" name="editForm" method="post" modelAttribute="geographic_edit">
					 		<table border="0" width="100%">
					 		<tr>
								<td width="30%" align="right"><spring:message code="geographic.code"/> :</td>
								<td><form:input id="code" path="code" cssClass="inputbox" maxlength="30"/></td>
								<td><form:errors cssClass="error" path="code" /></td>
						 	</tr>
					 		<tr>
								<td align="right"><spring:message code="geographic.name"/> :</td>
								<td><form:input id="name" path="name" cssClass="inputbox" maxlength="50" cssStyle="width:300px"/></td>
								<td><form:errors cssClass="error" path="name"/></td>
					 		</tr>
					 		<tr>
								<td align="right"><spring:message code="geographic.type"/> :</td>
								<td>
				  					<form:select path="geographicType" disabled="true" cssClass="inputbox">
					  					<c:forEach items="${types}" var="type">
					  						<option value="${type.id}" ${geographic_edit.geographicType.id == type.id ? 'selected':''}>
												<c:if test="${type.name == 'Country'}"><spring:message code="geographic.country"/></c:if>
												<c:if test="${type.name == 'Province'}"><spring:message code="geographic.province"/></c:if>
												<c:if test="${type.name == 'City'}"><spring:message code="geographic.city"/></c:if>
												<c:if test="${type.name == 'District'}"><spring:message code="geographic.district"/></c:if>
											</option>
										</c:forEach>
				  						<%-- <form:options items="${types}" itemLabel="name" itemValue="id"/> --%>
				  					</form:select>
								</td>
								<td><form:errors cssClass="error" path="geographicType"/></td>
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
		if(!$('#code').val())
		{
			alert('<spring:message code="geographic.code"/> <spring:message code="notif.empty"/> !!!');
			return;
		}
		
		if(!$('#name').val())
		{
			alert('<spring:message code="geographic.name"/> <spring:message code="notif.empty"/> !!!');
			return;
		}
		
		document.editForm.action = "<c:url value='/page/geographicedit.htm'/>";
		document.editForm.submit();
	}
</script>