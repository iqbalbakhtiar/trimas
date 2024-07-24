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
	<%@ include file="/filter/newsFilter.jsp"%>
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
						<h1 class="page-title"></h1>
						<h1 class="page-title">${pageTitle}</h1>
						
						<div class="item-navigator">
						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
						<tr>
							<td width="30%" height="30" align="left" valign="middle">															
								<div class="toolbar-clean">
									<a class="item-button-new" href="<c:url value='/page/newsPrepareAdd.htm'/>"><span><spring:message code="news.new"/></span></a>
							  		<div dojoType="Toggler" targetId="filter">
										<a class="item-button-search" href="javascript:return;"><span><spring:message code="sirius.paging.filter"/></span></a>
									</div>
								</div>
							</td>
							<td width="65%" align="right" height="20"><a href="javascript:step('first');"><spring:message code="sirius.paging.first"/></a> | <a href="javascript:step('prev');"><spring:message code="sirius.paging.prev"/></a> | <spring:message code="sirius.paging.page"/> <c:out value="${data.filterCriteria.page}"/> of <c:out value="${data.filterCriteria.totalPage}"/> | <a href="javascript:step('next');"><spring:message code="sirius.paging.next"/></a> | <a href="javascript:step('last');"><spring:message code="sirius.paging.last"/></a></td>
						</tr>
						</table>
					  </div>
						<table class="table-list" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<th width="4%">&nbsp;</th>
							<th width="8%"><spring:message code="news.date"/></th>
							<th width="20%"><spring:message code="news.title"/></th>
							<th width="75%"><spring:message code="sirius.createdby"/></th>
						  </tr>
						<c:forEach items="${news}" var="news">
						<tr>
							<td class="tools">
							 	<a class="item-button-edit"   href="<c:url value='/page/newsPrepareUpdate.htm?id=${news.id}'/>" title="Edit"><span>Edit</span></a>
								<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/newsDelete.htm?id=${news.id}'/>', '<spring:message code="notif.delete"/>')" title="Delete"><span>Delete</span></a>
							</td>
							<td nowrap="nowrap"><fmt:formatDate value='${news.date}' pattern='dd-MMM-yyyy'/></td> 
							<td nowrap="nowrap"><c:out value='${news.title}'/></td>          
							<td nowrap="nowrap"><c:out value='${news.createdBy.fullName}'/></td>
						</tr>
						</c:forEach> 
						<tr class="end-table"><td colspan="5">&nbsp;</td></tr>
						</table>
						<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
						<tr>
							<td width="65%" align="right" height="20"><a href="javascript:step('first');"><spring:message code="sirius.paging.first"/></a> | <a href="javascript:step('prev');"><spring:message code="sirius.paging.prev"/></a> | <spring:message code="sirius.paging.page"/> <c:out value="${data.filterCriteria.page}"/> of <c:out value="${data.filterCriteria.totalPage}"/> | <a href="javascript:step('next');"><spring:message code="sirius.paging.next"/></a> | <a href="javascript:step('last');"><spring:message code="sirius.paging.last"/></a></td>
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
