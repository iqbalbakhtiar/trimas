<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
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
</head>
<body>
	<div id="se-containers">
		<%@ include file="/common/sirius-menu.jsp"%>
		<div id="se-navigator">
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td width="60%" style="font-family: Verdana, Arial, Helvetica, sans-serif;font-style: italic;color: #999999;font-size: 11px;"><spring:message code="sirius.clickmenu"/></td>
					<td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
				</tr>
			</table>
		</div>
		<div id="r11">
			<div id="r12">
				<div id="r13">
					<div id="r14">
						<div id="se-contents">
							<table border="0" cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td width="75%" align="left" valign="top">
										<img src="<c:url value='/images/dashboard.gif'/>"><br/>
									</td>
									<td width="25%" align="left" valign="top">
										<img src="<c:url value='/images/company_news.gif'/>"><br/>
										<br/>
										<table border="0" cellpadding="0" cellspacing="0" width="100%">
											<tbody>
												<c:if test="${requested > 0}">
													<div id="callbox" class="callout approvalCount">
														<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
													  	<div class="callout-header">Approval</div>
														<div class="callout-container">
															<a class="dashboard_button_urgent" href="<c:url value='/page/approvalview.htm'/>"><c:out value="${requested}" />&nbsp;Approval to be approved.</a>
														</div>
													</div>
												</c:if>
												<c:if test="${not empty news}">
													<div id="callbox" class="callout latestNews">
														<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
													  	<div class="callout-header"><c:out value='${news.title}-${news.createdBy.fullName}'/></div>
														<div class="callout-container">
															<a class="dashboard_button_description" href="<c:url value='/page/newsView.htm'/>">${news.content}</a>
														</div>
													</div>
												</c:if>
											</tbody>
											<tbody id="callout"></tbody>
										</table>
									</td>
								</tr>
							</table>
							<div id="callbox" class="callout" style="display:none;">
								<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
							  	<div class="callout-header"></div>
								<div class="callout-container"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="/common/address.jsp"%>
		<%@ include file="/common/sirius-footer.jsp"%>
	</div>
</body>
</html>