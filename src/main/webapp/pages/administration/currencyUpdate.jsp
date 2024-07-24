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
	<title>${title }</title>
	<%@ include file="/common/sirius-header.jsp"%>
	
	<script type="text/javascript">
		function save()
		{			
			document.updateForm.action = "<c:url value='/page/currencyedit.htm'/>";
			document.updateForm.submit();
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
		<td width="40%" align="right">
			<%@ include file="/common/welcome.jsp"%>
		</td>
	</tr>
	</table>
	</div>
	
	<div id="r11">
		<div id="r12">
			<div id="r13">
				<div id="r14">
					<div id="se-contents">
					
					<h1 class="page-title">C33 - Currency</h1>
						
					<div class="toolbar">
						<a class="item-button-list" href="<c:url value='/page/currencyview.htm'/>"><span><spring:message code='sirius.list'/></span></a>
						<a class="item-button-save" href="javascript:save();"><span><spring:message code='sirius.save'/></span></a>
					</div>					  
					  
					<div class="main-box">
						<c:if test="${not empty message}">
							<%@ include file="/common/error.jsp"%>
						</c:if>
						<sesform:form cssClass="edit-form" id="updateForm" name="updateForm" method="post" modelAttribute="currency_edit">
						<table border="0" cellpadding="0" cellspacing="0">
 						<tr>
 							<td width="104"><div class="form-label"><spring:message code='sirius.id'/>:</div></td>
							<td width="240"><form:input id="currencyId" path="symbol" cssClass="inputbox" /></td>
							<td width="360"><form:errors path="symbol" cssClass="error"/></td>
 						</tr>
 						<tr>
 							<td><div class="form-label"><spring:message code='sirius.name'/> :</div></td>
							<td><form:input id="currencyName" path="name" cssClass="inputbox" /></td>
							<td><form:errors path="name" cssClass="error"/></td>
 						</tr>
 						<tr>
 							<td width="128"><div class="form-label">&nbsp;</div></td>
						  	<td width="192"><input type="checkbox" name="base" <c:if test='${currency_edit.base}'>checked</c:if> ><spring:message code='sirius.default'/></input></td>
						    <td><form:errors path="base" cssClass="error"/></td>
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