<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>">
	<html>
<head>
<title>Ageing Detail</title>
<style type="text/css" media="screen">
<!--
@import url("assets/sirius.css");

-->
<!--
@import url("css/report_format.css");
-->
</style>
<script type="text/javascript" src="assets/dialog.js"></script>
<style type="text/css" media="print">
<!--
@import url("css/print_format.css");
-->
</style>
<script src="<c:url value='/js/jquery-1.4.2.min.js'/>"></script>
<script src="<c:url value='/js/jquery-ui-1.8.2.custom.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/number-functions.js'/>"></script>
<script type="text/javascript">
	function printPage() {
		print();
	}
</script>
<%@ include file="/filter/dashboard/ageingFilter.jsp"%>
</head>
<!-- BEGIN OF BODY -->
<body>
	<!-- top  menu -->
	<div class="area" dojoType="Container" id="quick_link_container">
		<%@ include file="/common/sirius-header.jsp"%>
	</div>
	<!-- /top menu -->
	<!-- rounded -->
	<div id="se-r00">
		<div id="se-r01">&nbsp;</div>
		<div id="se-r02">&nbsp;</div>
	</div>
	<!-- /rounded -->
	<!-- main containers -->
	<div id="se-containers">
		<!-- main menu -->
		<div class="area" dojoType="Container" id="quick_link_container">
			<%@ include file="/common/sirius-menu.jsp"%>
		</div>
		<div id="se-navigator">
			<div class="area" dojoType="Container" id="quick_link_container">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td width="60%">Dashboard > Ageing Detail (Customer)</td>
						<td width="40%" align="right"><%@ include file="/common/welcome.jsp"%></td>
					</tr>
				</table>
			</div>
		</div>
		<div id="r11">
			<div id="r12">
				<div id="r13">
					<div id="r14">
						<div id="se-contents">
							<div class="area" dojoType="Container" id="quick_link_container">
								<h1 class="page-title">
									AGEING SUMMARY (CUSTOMER)
								</h1>
								<div class="toolbar">
									<c:if test="${filter.personId!=0}">
										<a class="item-button-back" href="javascript:history.go(-1);"><span>Back</span></a>
									</c:if>
									<a class="item-button-print" href="javascript:window.print();"><span>Print</span></a>
									<div dojoType="Toggler" targetId="filter">
										<a class="item-button-search" href="javascript:return;"><span>Filter</span></a>
									</div>
								</div>
								<div class="main-box">
									<div class="main_container">
										<div class="report">
											<strong>Ageing Summary</strong>
										</div>
										<div class="clears">&nbsp;</div>
										<table width="100%" cellpadding="5" cellspacing="0" class="report-table tablesorter">
											<thead>
												<tr>
													<th width="30%" align="left" style="border-bottom: 1px solid black;border-top: 1px solid black;">Customer</th>
													<th width="10%" align="center" style="border-bottom: 1px solid black;border-top: 1px solid black;">Not Yet Due</th>
													<th width="10%" align="center" style="border-bottom: 1px solid black;border-top: 1px solid black;">0-30</th>
													<th width="10%" align="center" style="border-bottom: 1px solid black;border-top: 1px solid black;">30-60</th>
													<th width="10%" align="center" style="border-bottom: 1px solid black;border-top: 1px solid black;">60-90</th>
													<th width="10%" align="center" style="border-bottom: 1px solid black;border-top: 1px solid black;">&gt;90</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items='${reports}' var='report'>
													<c:set var="month1" value="${month1+report.month1}"/>
													<c:set var="month2" value="${month2+report.month2}"/>
													<c:set var="month3" value="${month3+report.month3}"/>
													<c:set var="month4" value="${month4+report.month4}"/>
													<c:set var="notyet" value="${notyet+report.notyetDue}"/>
													<tr>
														<td align="left"><c:out value='${report.party.firstName}' /></td>
														<td align="right"><fmt:formatNumber value='${report.notyetDue}' pattern="#,###.00" /></td>
														<td align="right"><fmt:formatNumber value='${report.month1}' pattern="#,###.00" /></td>
														<td align="right"><fmt:formatNumber value='${report.month2}' pattern="#,###.00" /></td>
														<td align="right"><fmt:formatNumber value='${report.month3}' pattern="#,###.00" /></td>
														<td align="right"><fmt:formatNumber value='${report.month4}' pattern="#,###.00" /></td>
													</tr>
												</c:forEach>
											</tbody>
											<tfooter>
												<tr>
													<td style="border-bottom: 1px solid black;border-top: 1px solid black;"><b>Total</b></td>
													<td align="right" style="border-bottom: 1px solid black;border-top: 1px solid black;"><fmt:formatNumber value='${notyet}'  pattern="#,###.00" /></td>
													<td align="right" style="border-bottom: 1px solid black;border-top: 1px solid black;"><fmt:formatNumber value='${month1}'  pattern="#,###.00" /></td>
													<td align="right" style="border-bottom: 1px solid black;border-top: 1px solid black;"><fmt:formatNumber value='${month2}'  pattern="#,###.00" /></td>
													<td align="right" style="border-bottom: 1px solid black;border-top: 1px solid black;"><fmt:formatNumber value='${month3}'  pattern="#,###.00" /></td>
													<td align="right" style="border-bottom: 1px solid black;border-top: 1px solid black;"><fmt:formatNumber value='${month4}'  pattern="#,###.00" /></td>
												</tr>
											</tfooter>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			</div>
			<!-- /rounded -->
			<!-- footer -->
			<div style="clear: both;height: 0px">&nbsp;</div>
			<div id="footer">
				<div>
					<div class="area" dojoType="Container" id="quick_link_container">
						<span>&copy; 2012 siriusERP v1.5</span>
					</div>
				</div>
			</div>
			<div style="clear: both;height: 20px">&nbsp;</div>
		</div>
</body>
<script type="text/javascript">
	function filterByPerson(id) {
		$("#personId").val(id);
		document.filterForm.submit();
	}
</script>
	</html>