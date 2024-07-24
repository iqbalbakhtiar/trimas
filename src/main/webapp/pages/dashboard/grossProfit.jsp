<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>">
	<html>
<head>
<title>Sales Daily</title>
<style type="text/css" media="screen">
<!--
@import url("assets/sirius.css");

-->
<!--
@import url("css/report_format.css");
-->
</style>
<%@ include file="/filter/dashboard/orderFilter.jsp" %>
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
						<td width="60%">Dashboard > Gross Profit</td>
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
									Gross Profit<br>
								</h1>

								<div class="toolbar">
									<a class="item-button-back" href="<c:url value='/page/dashboard.htm'/>"><span>Back</span></a> <a class="item-button-print" href="javascript:window.print();"><span>Print</span></a>
									<div dojoType="Toggler" targetId="filter"><a class="item-button-search" href="javascript:return;"><span>Filter</span></a>
								</div>
							</div>

							<div class="main-box">
								<div class="main_container">
									<div class="report">
										<strong>Gross Profit</strong>
									</div>
									<div class="clears">&nbsp;</div>
									<center><img src="<c:url value='/charts/${filename}'/>.png"/></center>
									<table width="100%" cellpadding="5" cellspacing="0" class="report-table">
										<thead>
											<tr>
												<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Sales</th>
												<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Company</th>
												<th width="5%" align="center" style="border-bottom: 1px solid black; border-top: 1px solid black;">JAN</th>
												<th width="5%" align="center" style="border-bottom: 1px solid black; border-top: 1px solid black;">FEB</th>
												<th width="5%" align="center" style="border-bottom: 1px solid black; border-top: 1px solid black;">MAR</th>
												<th width="5%" align="center" style="border-bottom: 1px solid black; border-top: 1px solid black;">APR</th>
												<th width="5%" align="center" style="border-bottom: 1px solid black; border-top: 1px solid black;">MAY</th>
												<th width="5%" align="center" style="border-bottom: 1px solid black; border-top: 1px solid black;">JUN</th>
												<th width="5%" align="center" style="border-bottom: 1px solid black; border-top: 1px solid black;">JUL</th>
												<th width="5%" align="center" style="border-bottom: 1px solid black; border-top: 1px solid black;">AUG</th>
												<th width="5%" align="center" style="border-bottom: 1px solid black; border-top: 1px solid black;">SEP</th>
												<th width="5%" align="center" style="border-bottom: 1px solid black; border-top: 1px solid black;">OCT</th>
												<th width="5%" align="center" style="border-bottom: 1px solid black; border-top: 1px solid black;">NOV</th>
												<th width="5%" align="center" style="border-bottom: 1px solid black; border-top: 1px solid black;">DEC</th>
												<th width="5%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Total</th>
											</tr>
										</thead>
										<tbody>
											<c:set var="gtotal" value="0" />
											<c:forEach items='${reports}' var='report'>
												<tr>
													<td align="left"><c:out value='${report.salesPerson.firstName}' /></td>
													<td align="left"><c:out value='${report.organization.code}' /></td>
													<td align="right"><fmt:formatNumber value='${report.january-report.januaryCogs}' /></td>
													<td align="right"><fmt:formatNumber value='${report.february-report.februaryCogs}' /></td>
													<td align="right"><fmt:formatNumber value='${report.march-report.marchCogs}' /></td>
													<td align="right"><fmt:formatNumber value='${report.april-report.aprilCogs}' /></td>
													<td align="right"><fmt:formatNumber value='${report.may-report.mayCogs}' /></td>
													<td align="right"><fmt:formatNumber value='${report.june-report.juneCogs}' /></td>
													<td align="right"><fmt:formatNumber value='${report.july-report.julyCogs}' /></td>
													<td align="right"><fmt:formatNumber value='${report.august-report.augustCogs}' /></td>
													<td align="right"><fmt:formatNumber value='${report.september-report.septemberCogs}' /></td>
													<td align="right"><fmt:formatNumber value='${report.october-report.octoberCogs}' /></td>
													<td align="right"><fmt:formatNumber value='${report.november-report.novemberCogs}' /></td>
													<td align="right"><fmt:formatNumber value='${report.december-report.decemberCogs}' /></td>
													<td align="right"><fmt:formatNumber value='${report.total-report.totalCogs}' /></td>
													<c:set var="gtotal" value="${gtotal+(report.total-report.totalCogs)}" />
												</tr>
											</c:forEach>
											</tbody>
											<tfooter>
											<tr>
												<td align="left" colspan="14"></td>
												<td align="right" style="border-top: solid 1px #000000;"><fmt:formatNumber value='${gtotal}' /></td>
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
		<!-- /rounded -->

		<!-- footer -->
		<div style="clear: both; height: 0px">&nbsp;</div>
		<div id="footer">
			<div>
				<div class="area" dojoType="Container" id="quick_link_container">
					<span>&copy; 2012 siriusERP v1.5</span>
				</div>
			</div>
		</div>
		<div style="clear: both; height: 20px">&nbsp;</div>

		<!-- /footer -->
	</div>
	<!-- /main containers -->



</body>
<!-- END OF BODY -->
	</html>
