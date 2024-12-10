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
	<%@ include file="/filter/approver/approverFilter.jsp"%>
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
										<a class="item-button-new" href="<c:url value='/page/approverpreadd.htm' />"><span><spring:message code="approver.new"/></span></a>
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
					  	  	<th width="3%"><spring:message code="sirius.code"/></th>
					  	  	<th width="10%"><spring:message code="sirius.name"/></th>
					  	  	<th width="5%"><spring:message code="sirius.organization"/></th>
					  	  	<th width="5%"><spring:message code="approver.type"/></th>
					  	  	<th width="5%"><spring:message code="sirius.status"/></th>
					  	  	<th width="10%"><span><spring:message code="sirius.note"/></th>
						</tr>
						<c:forEach items="${approvers}" var="approver">
						<tr>
							<td class="tools">
								<a class="item-button-edit" href="<c:url value='/page/approverpreedit.htm?id=${approver.id}'/>" title="<spring:message code='sirius.edit'/>"><span><spring:message code="sirius.edit"/></span></a>
								<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/approverdelete.htm?id=${approver.id}'/>');" title="<spring:message code='sirius.delete'/>"><span><spring:message code="sirius.delete"/></span></a>
							</td>
							<td nowrap="nowrap">${approver.partyFrom.code}</td> 
							<td nowrap="nowrap">${approver.partyFrom.fullName}</td>
							<td nowrap="nowrap">${approver.partyTo.fullName}</td>
							<td nowrap="nowrap">${approver.partyRoleTypeFrom.name}</td>
							<td nowrap="nowrap"><spring:message code="sirius.${approver.active ? 'active' : 'inactive'}"/></td>
							<td nowrap="nowrap">${approver.note}</td>
						</tr>
						</c:forEach>
						<tr class="end-table"><td colspan="7">&nbsp;</td></tr>
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

<div style="display: none">
	<%@ include file="/common/dialog.jsp"%>
</div>
</div>
</body>
<!-- END OF BODY -->
</html>
