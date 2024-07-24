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
	<title>${title }</title>
	<%@ include file="/common/sirius-header.jsp"%>
	<script type="text/javascript">
		function save()
		{			
			document.addForm.action = "<c:url value='/page/taxadd.htm'/>";
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
						
						<h1 class="page-title">${pageTitle }</h1>
						
					  	<div class="toolbar">
							<a class="item-button-list" href="<c:url value='/page/taxview.htm'/>"><span><spring:message code='sirius.list'/></span></a>
							<a class="item-button-save" href="javascript:save();"><span><spring:message code='sirius.save'/></span></a>
					 	</div>
					  
						<div class="main-box">
							<sesform:form cssClass="edit-form" id="addForm" name="addForm" method="post" modelAttribute="tax_add">
                                <table  width="100%" style="border:none">
                                <tr>
                                  	<td width="23%" align="right"><spring:message code='tax.id'/> : </td>
                                    <td width="40%"><form:input path="taxId" id="taxId" cssClass="inputbox" maxlength="20"/></td>
                                    <td width="37%"><form:errors cssClass="error" path="taxId"/></td>
                                </tr>
                                <tr>
                                    <td align="right"><spring:message code='tax.name'/> : </td>
                                    <td><form:input path="taxName" id="taxName" cssClass="inputbox"/></td>
                                    <td><form:errors cssClass="error" path="taxName"/></td>
                                </tr>
                                <tr>
                                    <td align="right"><spring:message code='tax.rate'/> : </td>
                                    <td><form:input path="taxRate" id="taxRate" cssClass="inputbox"/></td>
                                    <td><form:errors cssClass="error" path="taxRate"/></td>
                                </tr>
                                <tr>
                                    <td align="right"><spring:message code='sirius.note'/> : </td>
                                    <td><form:textarea path="description" cols="40" rows="5" cssClass="inputbox"/></td>
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