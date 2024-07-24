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
	<style type="text/css" media="screen"><!-- @import url("assets/sirius.css"); --></style>
	<script type="text/javascript">
		function add()
		{
			document.addForm.action = "<c:url value='/page/salessupervisoradd.htm'/>";
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
							<a class="item-button-list" href="<c:url value='/page/salessupervisorview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
							<a class="item-button-save" href="javascript:add();"><span><spring:message code="sirius.save"/></span></a>
					  	</div>
					  	
						<div class="main-box">			
							<sesform:form name="addForm" id="addForm" method="post">
							<table width="100%" style="border:none">
                            <tr>
                                <td width="24%" valign="top" align="right"><spring:message code="supervisor.name"/> :</td>
                                <td width="40%" valign="top">
                                    <select id="superVisor" name="superVisor" class="combobox-ext">
                                        <c:forEach items="${superVisors}" var="sup">
                                            <option value="${sup.id}">${sup.person.fullName}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td width="36%">&nbsp;</td>
                            </tr>
							<tr>
								<td align="right"><spring:message code="supervisor.status"/> :</td>
								<td>
									<input type="radio" name="superVisorStatus" value="true" checked="checked"/><spring:message code="sirius.yes"/>
									<input type="radio" name="superVisorStatus" value="false"/><spring:message code="sirius.no"/>
								</td>
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

</body>
</html>
