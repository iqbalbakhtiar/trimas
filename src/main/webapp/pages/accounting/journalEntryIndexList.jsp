<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
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
						
						<h1 class="page-title">${pageTitle }</h1>
										  		
						<div class="clears">&nbsp;</div>

					 	<div class="item-navigator">
							<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
							<tr>
								<td width="32%" height="30" align="left" valign="middle">															
									<div class="toolbar-clean">
										<a class="item-button-new" href="<c:url value='/page/journalentryindexpreadd.htm' />"><span><spring:message code='sirius.new'/></span></a>
									</div>
								</td>
							</tr>
							</table>
						</div>  
				  		<table class="table-list" cellspacing="0" cellpadding="0" width="100%">
			  			<tr>
				  			<th width="8%"><div style="width:40px">&nbsp;</div></th>
			  			  <th width="25%"><spring:message code='sirius.name'/></th>
			  			  <th width="67%"><spring:message code='sirius.note'/></th>
			  			</tr>
						<c:forEach items="${indexs}" var="index">
						<tr>
 							<td class="tools">
  								<a class="item-button-edit" href="<c:url value='/page/journalentryindexpreedit.htm?id=${index.id}'/>" title="<spring:message code='sirius.edit'/>"><span><spring:message code='sirius.edit'/></span></a>
   								<a class="item-button-delete" href="javascript:showDialog('<c:url value='/page/journalentryindexdelete.htm?id=${index.id}'/>');" title="<spring:message code='sirius.delete'/>"><span><spring:message code='sirius.delete'/></span></a>
 							</td>
 							<td>${index.name}</td>
			 				<td>${index.note}</td>
						</tr>
						</c:forEach>
						<tr class="end-table"><td colspan="3">&nbsp;</td></tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div style="clear:both;height:0px">&nbsp;</div>
	<div id="footer"><%@ include file="/common/sirius-footer.jsp"%></div>
	<div style="clear:both;height:20px">&nbsp;</div>
</div>
<%@ include file="/common/dialog.jsp"%>
</body>
</html>
