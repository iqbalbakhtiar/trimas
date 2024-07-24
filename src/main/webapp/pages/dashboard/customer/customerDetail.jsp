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
<title>Sales Daily</title>
<style type="text/css" media="screen">
<!--
@import url("assets/sirius.css");

-->
<!--
@import url("css/report_format.css");
-->
</style>
<%@ include file="/filter/dashboard/orderFilter.jsp"%>
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
						<td width="60%">Dashboard > Monthly Sales (Salesperson)</td>
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
									MONTHLY SALES
									<c:if test="${filter.personId==0}">
										(CUSTOMER)
									</c:if>
									<c:if test="${filter.personId!=0}">
										(${filter.personIndex}) - DETAIL
									</c:if>
									<br>
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
											<strong> MONTHLY SALES <c:if test="${filter.personId==0}">
												(CUSTOMER)
											</c:if> <c:if test="${filter.personId!=0}">
												(${filter.personIndex}) - DETAIL
											</c:if>
											</strong>
										</div>
										<div class="clears">&nbsp;</div>
										<table width="100%" cellpadding="5" cellspacing="0" class="report-table">
											<thead>
												<tr>
													<th width="30%" align="left" style="border-bottom: 1px solid black;border-top: 1px solid black;">Person</th>
													<c:if test="${filter.personId!=0}">
														<th width="30%" align="left" style="border-bottom: 1px solid black;border-top: 1px solid black;">Company</th>
														<th width="30%" align="left" style="border-bottom: 1px solid black;border-top: 1px solid black;">Location</th>
														<th width="30%" align="left" style="border-bottom: 1px solid black;border-top: 1px solid black;">Product</th>
													</c:if>
													<th width="10%" align="right" style="border-bottom: 1px solid black;border-top: 1px solid black;">Revenue</th>
													<th width="10%" align="right" style="border-bottom: 1px solid black;border-top: 1px solid black;">Cogs</th>
													<th width="10%" align="right" style="border-bottom: 1px solid black;border-top: 1px solid black;">Gross Profit</th>
													<th width="10%" align="right" style="border-bottom: 1px solid black;border-top: 1px solid black;">Gross Margin</th>
												</tr>
											</thead>
											<tbody>
												<c:set var="revenue" value='0'/>
												<c:forEach items='${reports}' var='report'>
													<tr>
														<c:set var="gross" value="${report.totalAmount-report.totalCogs}" />
														<td align="left"><c:if test="${filter.personId==0}">
																<a href="javascript:filterByPerson(${report.customer.id})"><c:out value='${report.customer.firstName}' /></a>
															</c:if> <c:if test="${filter.personId!=0}">
																<c:out value='${report.customer.firstName}' />
															</c:if></td>
														<c:if test="${filter.personId!=0}">
															<td align="left"><c:out value='${report.organization.code}' /></td>
															<td align="left"><c:out value='${report.organization.partyIndex.cityIndex}' /></td>
															<td align="left"><c:out value='${report.organization.partyIndex.productIndex}' /></td>
														</c:if>
														<td align="right">
															<fmt:formatNumber value='${report.totalAmount}' pattern="#,###.00" />
															<c:set var="revenue" value='${report.totalAmount+revenue}'/>
														</td>
														<td align="right"><fmt:formatNumber value='${report.totalCogs}' pattern="#,###.00" /></td>
														<td align="right"><fmt:formatNumber value='${gross}' pattern="#,###.00" /></td>
														<c:if test="${report.totalAmount>0}">
															<td align="right"><fmt:formatNumber value='${(gross/report.totalAmount)*100}' pattern="#,###.00" />%</td>
														</c:if>
														<c:if test="${report.totalAmount<=0}">
															<td align="right">0%</td>
														</c:if>
													</tr>
												</c:forEach>
												</tbody>
												<tfooter>
												<tr>
													<td colspan="${filter.personId==0?'1':'4'}" style="border-top: 1px solid black;">&nbsp;</td>
													<td style="border-top: 1px solid black;" align="right"><fmt:formatNumber value='${revenue}' pattern="#,###.00"/></td>
													<td style="border-top: 1px solid black;">&nbsp;</td>
													<td style="border-top: 1px solid black;">&nbsp;</td>
													<td style="border-top: 1px solid black;">&nbsp;</td>
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