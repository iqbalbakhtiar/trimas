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
						<h1 class="page-title">${pageTitle}</h1>
						<div class="toolbar-clean">
							<a class="item-button-new" href="<c:url value='/page/currencypreadd.htm'/>"><span><spring:message code="sirius.new"/></span></a>
						</div>
				  		<table width="100%" cellpadding="0" cellspacing="0" class="table-list">
				  		<tr>
				  			<th width="8%"><div style="width:44px"></div></th>
				  			<th width="16%"><spring:message code="sirius.code"/></th>
				  			<th width="25%"><spring:message code="sirius.name"/></th>
				  			<th width="51%">&nbsp;</th>
				  		</tr>
						<c:forEach items="${data}" var="currencyBean">
						<tr>
 							<td class="tools">
 	 							<a class="item-button-edit" href="<c:url value='/page/currencypreedit.htm?id=${currencyBean.id}'/>"  title="<spring:message code='sirius.edit'/>"><span><spring:message code="sirius.edit"/></span></a>
  								<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/currencydelete.htm?id=${currencyBean.id}'/>');" title="<spring:message code='sirius.delete'/>"><span><spring:message code="sirius.delete"/></span></a>
	 						</td>
 							<td>${currencyBean.symbol}</td>
 							<td>${currencyBean.name}</td>
 							<td align="left"><c:if test='${currencyBean.base}'><strong><spring:message code='sirius.default'/></strong></c:if></td>
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
