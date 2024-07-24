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
	<%@ include file="/filter/approvableOverFilter.jsp"%>
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

						<h1 class="page-title"></h1>
						<h1 class="page-title">${pageTitle }</h1>
						
						<div class="item-navigator">
							<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
							<tr>
								<td width="30%" height="30" align="left" valign="middle">&nbsp;
                   <div class="toolbar-clean">
							  			<div dojoType="Toggler" targetId="filter">
											<a class="item-button-search" href="javascript:return;"><span><spring:message code="sirius.filter"/></span></a>
										</div>
									</div>
				                </td>
				                <td width="70%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
							</tr>
							</table>
					  	</div>
					  
						<table width="100%" cellpadding="0" cellspacing="0" class="table-list">
						<tr valign="top">
							<th width="4%">&nbsp;</th>
			  	  	  	  	<th width="17%"><spring:message code="sirius.id"/></th>
			  	  	  	  	<th width="9%"><spring:message code="sirius.date"/></th>
               	  	  	  	<th width="22%"><spring:message code="organization"/></th>
               	  	  	  	<th width="13%"><spring:message code="sirius.type"/></th>
               	  	  	  	<th width="12%"><spring:message code="sirius.person"/></th>
               	  	  	  	<th width="12%"><spring:message code="sirius.amount"/></th>
                            <th width="12%"><spring:message code="sirius.approver"/></th>
						</tr>
						<c:forEach items="${approvables}" var="app">
						<tr>
							<td class="tools">
								<c:if test='${access.edit}'>
                    <a class="item-button-edit" href="<c:url value='/page/approvaloverpreedit.htm?id=${app.id}'/>"><span><spring:message code="sirius.edit"/></span></a>
                </c:if>
							</td>
							<td><c:out value='${app.code}'/></td>
							<td><fmt:formatDate value='${app.date}' pattern='dd-MM-yyyy'/></td> 
							<td><c:out value="${app.organization.fullName}"/></td>
							<td><c:out value='${app.approvableType.normalizedName}'/></td>
							<td><c:out value='${app.person}'/></td>
							<td align="right"><fmt:formatNumber value="${app.total}" pattern=',##0.00'/></td>
              <td><c:out value='${app.approvalDecision.forwardTo.fullName}'/></td>
						</tr>
						</c:forEach>
						<tr class="end-table"><td colspan="8">&nbsp;</td></tr>
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
	
 	<div style="clear:both;height:0px">&nbsp;</div>
	<div id="footer">
		<div>
			<%@ include file="/common/sirius-footer.jsp"%>
		</div>
	</div>
	<div style="clear:both;height:20px">&nbsp;</div>

</div>

</body>
<div style="display: none">
	<%@ include file="/common/dialog.jsp"%>
</div>
</html>
<script type="text/javascript" src="assets/dialog.js"></script>