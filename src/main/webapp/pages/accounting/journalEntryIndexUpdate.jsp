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
	<style type="text/css" media="screen"><!-- @import url("assets/sirius.css"); --></style>
	<script type="text/javascript">
		function save()
		{
			document.editForm.action = "<c:url value='/page/journalentryindexedit.htm'/>";
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
	<!-- main menu -->
		<%@ include file="/common/sirius-menu.jsp"%>
	
	<div id="se-navigator">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td width="60%">${breadcrumb }</td>
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
						<a class="item-button-list" href="<c:url value='/page/journalentryindexview.htm'/>"><span><spring:message code='sirius.list'/> </span></a>
						<a class="item-button-save" href="javascript:save();"><span><spring:message code='sirius.save'/> </span></a>
					</div>					  
					  
					<div class="main-box">
						<c:if test="${not empty message}">
							<%@ include file="/common/error.jsp"%>
						</c:if>
						<sesform:form cssClass="edit-form" id="editForm" name="editForm" method="post" modelAttribute="journalEntryIndex_edit">
						<table width="714" border="0" cellpadding="0" cellspacing="0">
 						<tr><td colspan="3">&nbsp;</td></tr>
 						<tr> 	
 							<td width="153"><spring:message code='sirius.name'/>  :</td>
							<td width="230"><form:input path="name" cssClass="inputbox" maxlength="50"/></td>
							<td width="331"><form:errors path="name" cssClass="error"/></td>
 						</tr>
 						<tr>
 							<td><spring:message code='sirius.note'/>  :</td>
							<td><form:textarea path="note" cols="50" rows="6"/></td>
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
	
  	<div style="clear:both;height:0px">&nbsp;</div>
	<div id="footer">
		<%@ include file="/common/sirius-footer.jsp"%>
	</div>
	<div style="clear:both;height:20px">&nbsp;</div>

</div>
</body>

</html>
