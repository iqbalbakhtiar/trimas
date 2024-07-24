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
	<%@ include file="/common/sirius-header.jsp"%><!-- /top menu -->
	<%@ include file="/filter/auditTrailsFilter.jsp"%>
	<style type="text/css" media="screen"><!-- @import url("assets/sirius.css"); --></style>
</head>

<body>

<div id="se-r00">
	<div id="se-r01">&nbsp;</div>
	<div id="se-r02">&nbsp;</div>
</div>

<div id="se-containers">
	<!-- main menu -->
	<%@ include file="/common/sirius-menu.jsp"%>
	<!-- /main menu -->
	
	<!-- tips & page navigator -->
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
					<!-- main contents goes here -->
						<h1 class="page-title"></h1>
						<h1 class="page-title">${pageTitle }</h1>
						
						<div class="item-navigator">
							<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
							<tr>
								<td width="23%" height="30" align="left" valign="middle">															
									<div class="toolbar-clean">
										<div dojoType="Toggler" targetId="filter">
											<a class="item-button-search" href="javascript:return;"><span><spring:message code="sirius.filter"/>  </span></a>
										</div>
							  		</div>
								</td>
								<td width="70%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
							</tr>
							</table>
					  	</div>
                        <table class="table-list" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <th width="18%" scope="col" align="left"><spring:message code="sirius.date"/> &amp; <spring:message code="history.time"/></th>
                            <th width="17%" scope="col" align="left"><spring:message code="sirius.menu"/></th>
                            <th width="19%" scope="col" align="left"><spring:message code="sirius.id"/></th>
                            <th width="12%" scope="col" align="left"> <spring:message code="history.action"/></th>
                            <th width="34%" scope="col" align="left"> <spring:message code="history.person"/></th>
                        </tr>
                        <c:forEach items="${trails}" var="activityHistory">
                        <tr>
                            <td   class="rowOdd" align="left"><fmt:formatDate value="${activityHistory.actionDate}" pattern='dd   MMMM   yyyy hh:mm:ss'/></td>
                            <td  align="left" class="rowEven"><c:out value="${activityHistory.accessedModule}" /></td>
                            <td   class="rowOdd" align="left"><c:out value="${activityHistory.accessedModuleId}" /></td>
                            <td  align="left" class="rowEven"><c:out value="${activityHistory.actionType}" /></td>
                            <td  class="rowOdd" align="left"><c:out value="${activityHistory.activePerson.code}" /> - <c:out value="${activityHistory.activePerson.firstName}" /> <c:out value="${activityHistory.activePerson.middleName}" /> <c:out value="${activityHistory.activePerson.lastName}" />&nbsp;</td>
                        </tr>
                        </c:forEach>
                        <tr class="end-table"><td colspan="5">&nbsp;</td></tr>
                        </table>
                        <table border="0" cellpadding="0" cellspacing="0" width="99%" align="center" height="20">
                             <td width="70%" align="right" height="20"><%@ include file="/common/navigate.jsp"%></td>
                        </table>
					</div>
				</div>
			</div>
		</div>
	</div><!-- /rounded -->

	<!-- footer -->
  <div style="clear:both;height:0px">&nbsp;</div>
	<div id="footer">
		<%@ include file="/common/sirius-footer.jsp"%>
	</div>
	<div style="clear:both;height:20px">&nbsp;</div>

	<!-- /footer -->
</div>
<!-- /main containers -->
</body>
<!-- END OF BODY -->
</html>
