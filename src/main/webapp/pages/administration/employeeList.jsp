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
	<title>${title}</title>
	<%@ include file="/common/sirius-header.jsp"%>
	<%@ include file="/filter/administration/employeeFilter.jsp"%>
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
					  	<div class="item-navigator">
							<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
							<tr>
								<td width="35%" height="30" align="left" valign="middle">
									<div class="toolbar-clean">
										<c:if test='${access.add}'>
											<a class="item-button-new" href="<c:url value='/page/employeepreadd.htm'/>"><span><spring:message code="employee.new"/></span></a>
								   		</c:if>
								   		<div dojoType="Toggler" targetId="filter">
											<a class="item-button-search" href="javascript:return false;"><span><spring:message code="sirius.paging.filter"/></span></a>
										</div>
								   </div>
								</td>
								<td width="65%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
							</tr>
							</table>
					  	</div>
					  	<table class="table-list" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<th width="3%">&nbsp;</th>
					  	  	<th width="10%"><spring:message code="sirius.code"/></th>
					  	  	<th width="15%"><spring:message code="sirius.name"/></th>
					  	  	<th width="12%"><spring:message code="organization"/></th>
					  	  	<th width="5%"><spring:message code="sirius.status"/></th>
					  	  	<th width="20%"><spring:message code="sirius.note"/></th>
						</tr>
						<c:forEach items="${employees}" var="emp">
						<tr>
							 <td class="tools">
							 	<c:if test='${access.edit}'>
									<a class="item-button-edit" href="<c:url value='/page/employeepreedit.htm?id=${emp.id}'/>"  title="Edit"><span><spring:message code="sirius.edit"/></span></a>
								</c:if>
								<c:if test='${access.delete and emp.partyFrom.active}'>
									<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/employeedelete.htm?id=${emp.id}'/>');" title="Delete"><span><spring:message code="sirius.delete"/></span></a>
								</c:if>
							</td>
							<td nowrap="nowrap">${emp.partyFrom.code}</td>
							<td nowrap="nowrap">${emp.partyFrom.fullName}</td>
							<td nowrap="nowrap">${emp.partyTo.fullName}</td>
					  	  	<td nowrap="nowrap">
								<c:if test='${emp.active}'><spring:message code="sirius.active"/></c:if>
								<c:if test='${!emp.active}'><div style="color: red;"><spring:message code="sirius.inactive"/></c:if>
							</td>
							<td>${emp.note}</td>
						</tr>
						</c:forEach>
						<tr class="end-table"><td colspan="6">&nbsp;</td></tr>
						</table>
						<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
						<tr>
							<td width="65%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
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
</html>