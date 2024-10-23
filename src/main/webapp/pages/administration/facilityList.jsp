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
	<%@ include file="/filter/facilityFilter.jsp"%>
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
						<div class="clears">&nbsp;</div>
						<div class="item-navigator">
							<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
							<tr>
								<td width="30%" height="30" align="left" valign="middle">														
									<div class="toolbar-clean">
										<c:if test='${access.add}'>
											<a class="item-button-new" href="<c:url value='/page/facilitypreadd.htm'/>"><span><spring:message code="sirius.new"/></span></a>
										</c:if>
							  			<div dojoType="Toggler" targetId="filter">
											<a class="item-button-search" href="javascript:return;"><span><spring:message code="sirius.paging.filter"/></span></a>
										</div>
									</div>
								</td>
								<td width="65%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
							</tr>
							</table>
					  	</div>					  
						<table width="100%" cellpadding="0" cellspacing="0" class="table-list">
						<tr>
							<th width="1%"><div style="width:45px"></div></th>
							<th width="8%"><spring:message code="facility.code"/></th>
	  	  	  	  	  	  	<th width="12%"><spring:message code="facility.name"/></th>
							<th width="8%"><spring:message code="facility.type"/></th>
							<th width="12%"><spring:message code="facility.owner"/></th>
       	  	  	  	  	  	<th width="60%"><spring:message code="facility.note"/></th>
						</tr>
						<c:forEach items="${facilitys}" var="fac">
						<tr>
							<td class="tools">
								<c:if test='${access.edit}'>
									<a class="item-button-edit" href="<c:url value='/page/facilitypreedit.htm?id=${fac.id}'/>"><span><spring:message code="sirius.edit"/></span></a>
								</c:if>
								<c:if test='${access.delete && fac.deletable}'>
									<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/facilitydelete.htm?id=${fac.id}'/>', '<spring:message code="notif.delete"/>');"><span><spring:message code="sirius.delete"/></span></a>
								</c:if>
							</td>
							<td nowrap="nowrap"><c:out value='${fac.code}'/></td>
							<td nowrap="nowrap"><c:out value='${fac.name}'/></td>
							<td nowrap="nowrap"><c:out value='${fac.facilityType.name}'/></td>
							<td nowrap="nowrap"><c:out value='${fac.owner.fullName}'/></td>
							<td><c:out value='${fac.note}'/></td>
						</tr>
						</c:forEach>
						<tr class="end-table"><td colspan="6">&nbsp;</td></tr>
						</table>
						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
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
