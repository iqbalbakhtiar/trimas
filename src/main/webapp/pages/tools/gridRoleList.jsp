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
	
	<%@ include file="/filter/gridRoleFilter.jsp"%>
	<style type="text/css" media="screen">
		@import url("<c:url value='/assets/sirius.css'/>");
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

						<h1 class="page-title"></h1>
						<h1 class="page-title">${pageTitle}</h1>
						<div class="clears">&nbsp;</div>
						<div class="item-navigator">
							<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
							<tr>
								<td width="30%" height="30" align="left" valign="middle">															
									<div class="toolbar-clean">
										<c:if test='${access.add}'>
											<a class="item-button-new" href="<c:url value='/page/gridrolepreadd.htm'/>"><span><spring:message code="sirius.new"/></span></a>
										</c:if>
							  			<div dojoType="Toggler" targetId="filter">
											<a class="item-button-search" href="javascript:return;"><span><spring:message code="sirius.paging.filter"/></span></a>
										</div>
									</div>
								</td>
								<td width="70%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
								</tr>
							</table>
					  	</div>					  
						<table width="100%" cellpadding="0" cellspacing="0" class="table-list">
						<tr>
							<th width="4%">&nbsp;</th>
							<th width="20%"><spring:message code="sirius.name"/></th>
							<th width="16%"><spring:message code="billing.organization"/></th>
						</tr>
						<c:forEach items="${roles}" var="role">
						<tr>
							<td class="tools">
								<c:if test='${access.edit}'>
									<a class="item-button-edit" href="<c:url value='/page/gridrolepreedit.htm?id=${role.id}'/>" title="<spring:message code='sirius.edit'/>"><span><spring:message code="sirius.edit"/></span></a>
								</c:if>
								<c:if test='${access.delete}'>
									<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/gridroledelete.htm?id=${role.id}'/>');" title="<spring:message code='sirius.delete'/>"><span><spring:message code="sirius.delete"/></span></a>
								</c:if>
							</td>
							<td><c:out value='${role.name}'/></td>
							<td><c:out value='${role.organization.fullName}'/></td>
						</tr>
						</c:forEach>
						<tr class="end-table"><td colspan="4">&nbsp;</td></tr>
						</table>
						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
						<tr>
							<td width="70%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
						</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
 	<%@ include file="/common/sirius-footer.jsp"%>

</div>
</body>
<div style="display: none">
	<%@ include file="/common/dialog.jsp"%>
</div>
</html>
