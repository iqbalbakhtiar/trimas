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
	<script type="text/javascript" src="assets/dialog.js"></script>
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
						<div class="toolbar-clean">
							<c:if test="${access.add}">
								<a class="item-button-new" href="<c:url value='/page/uompreadd.htm' />"><span><spring:message code="uom.new"/></span></a>
							</c:if>
					  	</div>
						<table class="table-list" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<th width="8%">&nbsp;</th>
							<th width="15%"><spring:message code="uom.code"/></th>
							<th width="19%"><spring:message code="uom.name"/></th>
							<th width="18%"><spring:message code="uom.type"/></th>
						</tr>
						<c:forEach items="${data}" var="measureBean">
						<tr>
 							<td class="tools">
								<c:if test="${access.edit}">
									<a class="item-button-edit" href="<c:url value='/page/uompreedit.htm?id=${measureBean.id}'/>"><span><spring:message code="sirius.edit"/></span></a>
								</c:if>
								<c:if test="${access.delete}">
									<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/uomdelete.htm?id=${measureBean.id}'/>');"><span><spring:message code="sirius.delete"/></span></a>
								</c:if>
 							</td>
 							<td><c:out value='${measureBean.measureId}'/></td>
 							<td><c:out value='${measureBean.name}'/></td>
 							<td><c:out value='${measureBean.type}'/></td>
						</tr>
						</c:forEach>
						<tr class="end-table"><td colspan="4">&nbsp;</td></tr>
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
