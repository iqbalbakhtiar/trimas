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
						<%@ include file="/filter/roleFilter.jsp"%>
						<h1 class="page-title">${pageTitle}</h1>
						<div class="clears">&nbsp;</div>
						<div class="toolbar-clean">
                        	<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
							<tr>
								<td width="35%" height="30" align="left" valign="middle">
																								
								<div class="toolbar-clean">
									<c:if test='${access.add}'>
										<a class="item-button-new" href="<c:url value='/page/rolepreadd.htm' />"><span><spring:message code="sirius.new"/></span></a>
							   		</c:if>
							   		<div dojoType="Toggler" targetId="filter">
										<a class="item-button-search" href="javascript:return false;"><span><spring:message code="sirius.paging.filter"/></span></a>
									</div>
							   </div>

								</td>
								<td width="70%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
								</tr>
							</table>
					  	</div>
		  				<table class="table-list" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<th width="7%">&nbsp;</th>
						  	<th width="16%"><spring:message code="role.id"/></th>
						  	<th width="29%"><spring:message code="role.name"/></th>
						  	<th width="48%"><spring:message code="sirius.note"/></th>
						</tr>
						<c:forEach items="${roles}" var="role">
						<tr>
						   	<td class="tools">
								<a class="item-button-edit" href="<c:url value='/page/rolepreedit.htm?id=${role.id}'/>"><span><spring:message code="sirius.edit"/></span></a>
								<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/roledelete.htm?id=${role.id}'/>');"><span><spring:message code="sirius.delete"/></span></a>
							</td>
							<td>${role.roleId}</td>
							<td>${role.name}</td>
							<td>${role.note}</td>					
						</tr>
					</c:forEach>
					<tr class="end-table"><td colspan="4">&nbsp;</td></tr>
					</table>
                    <table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
						<tr>
						<td width="70%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
						</tr>
						</table>
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
<div style="display: none">
	<%@ include file="/common/dialog.jsp"%>
</div>
