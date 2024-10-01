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
	<%@ include file="/common/sirius-header.jsp"%>
	<script type="text/javascript">
		function save()
		{			
			document.addForm.action = "<c:url value='/page/partyadd.htm'/>";
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
							<a class="item-button-list" href="<c:url value='/page/partyview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<a class="item-button-save" href="javascript:save();"><span><spring:message code="sirius.save"/></span></a>
					  	</div>
					  
						<div class="main-box">
							<sesform:form id="addForm" name="addForm" method="post" modelAttribute="party_add" enctype="multipart/form-data">
 								<table style="border:none" width="100%">
 								<tr>
 									<td width="34%" align="right"><span><spring:message code="party.code"/></td>
                                    <td width="1%" align="center">:</td>
									<td width="64%"><input class="inputbox input-disabled" value="Auto Number" disabled/></td>
									<td width="1%"><form:errors path="code"/></td>
 								</tr>
                                <tr>
 									<td align="right"><span><spring:message code="party.salutation"/></td>
                                    <td width="1%" align="center">:</td>
									<td><form:input path="salutation" cssClass="inputbox" /></td>
									<td><form:errors path="salutation"/></td>
	 							</tr>
 								<tr>
 									<td align="right"><span>Party Name</td>
                                    <td width="1%" align="center">:</td>
									<td><form:input path="fullName" cssClass="inputbox" /></td>
									<td><form:errors path="fullName"/></td>
	 							</tr>
                                <tr>
 									<td align="right"><span><spring:message code="party.code.tax"/></td>
                                    <td width="1%" align="center">:</td>
									<td><form:input path="taxCode" cssClass="inputbox" /></td>
									<td><form:errors path="taxCode"/></td>
	 							</tr>
                                <tr>
	 							<tr>
 									<td align="right"><span><spring:message code="party.birthdate"/></td>
                                    <td width="1%" align="center">:</td>
									<td><input id="birthDate" name="birthDate" class="datepicker"/></td>
									<td>&nbsp;</td>
	 							</tr>
                                <tr>
 									<td align="right"><span><spring:message code="party.picture"/></td>
                                    <td width="1%" align="center">:</td>
									<td><input type="file" name="file"/></td>
									<td>&nbsp;</td>
	 							</tr>
 								<tr>
 									<td align="right"><span><spring:message code="party.note"/></td>
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
