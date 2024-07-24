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
<title>Annual Gross Sales (By Customer Monthly)</title>
<style type="text/css" media="screen">
<!--
@import url("assets/sirius.css");

-->
<!--
@import url("css/report_format.css");
-->
</style>
<%@ include file="/filter/dashboard/annualFilter.jsp"%>
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
						<td width="60%">Dashboard > Annual Gross Sales (By Customer Monthly)</td>
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
									Annual Gross Sales (By Customer Monthly)<br/>
								</h1>

								<div class="toolbar">
									<a class="item-button-back" href="<c:url value='/page/dashboard.htm'/>"><span>Back</span></a> <a class="item-button-print" href="javascript:window.print();"><span>Print</span></a>
									<div dojoType="Toggler" targetId="filter"><a class="item-button-search" href="javascript:return;"><span>Filter</span></a></div>
									<div style="float:right;">
										<a class="item-button-chart" href="<c:url value='/page/dashboardchartcustomermonthly.htm'/>"><span>Net Sales View</span></a>
										<a class="item-button-chart" href="<c:url value='/page/dashboardchartcustomermonthlygross.htm'/>"><span>Gross Profit View</span></a>
										<a class="item-button-chart" href="<c:url value='/page/dashboardchartcustomermonthlyqty.htm'/>"><span>Quantity Sold View</span></a>
									</div>
								</div>

								<div class="main-box">
									<div class="main_container">
										<div class="report">
											<strong>Annual Gross Sales (By Customer Monthly)</strong>
										</div>
										<div class="clears">&nbsp;</div>
										<center><img src="<c:url value='/charts/${filename}'/>.png"/></center>
										<table width="100%" cellpadding="5" cellspacing="0" class="report-table">
											<thead>
												<tr>
													<c:if test="${filter.indexName=='PERSON_INDEX'}">
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Customer</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Company</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Location</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Product</th>
													</c:if>
													<c:if test="${filter.indexName=='COMPANY_INDEX'}">
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Company</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Customer</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Location</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Product</th>
													</c:if>
													<c:if test="${filter.indexName=='CITY_INDEX'}">
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Location</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Company</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Customer</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Product</th>
													</c:if>
													<c:if test="${filter.indexName=='PRODUCT_INDEX'}">
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Product</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Company</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Customer</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Location</th>
													</c:if>
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
												<c:set var="gjan" value="0" />
												<c:set var="gmar" value="0" />
												<c:set var="gfeb" value="0" />
												<c:set var="gapr" value="0" />
												<c:set var="gmay" value="0" />
												<c:set var="gjun" value="0" />
												<c:set var="gjul" value="0" />
												<c:set var="gaug" value="0" />
												<c:set var="gsep" value="0" />
												<c:set var="goct" value="0" />
												<c:set var="gnov" value="0" />
												<c:set var="gdec" value="0" />
												<c:set var="gtotal" value="0" />
												<c:forEach items='${reports}' var='report'>
													<tr>
														<c:if test="${filter.indexName=='PERSON_INDEX'}">
															<td align="left"><c:out value='${report.party.firstName}' /></td>
															<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('COMPANY_INDEX','${report.party.id}')" /></td>
															<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('CITY_INDEX','${report.party.id}')" /></td>
															<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('PRODUCT_INDEX','${report.party.id}')" /></td>
														</c:if>
														<c:if test="${filter.indexName=='COMPANY_INDEX'}">
															<td align="left"><c:out value='${report.organization.partyIndex.companyIndex}' /></td>
															<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('PERSON_INDEX','${report.organization.partyIndex.companyIndex}')" /></td>
															<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('CITY_INDEX','${report.organization.partyIndex.companyIndex}')" /></td>
															<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('PRODUCT_INDEX','${report.organization.partyIndex.companyIndex}')" /></td>
														</c:if>
														<c:if test="${filter.indexName=='CITY_INDEX'}">
															<td align="left"><c:out value='${report.organization.partyIndex.cityIndex}' /></td>
															<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('COMPANY_INDEX','${report.organization.partyIndex.cityIndex}')" /></td>
															<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('PERSON_INDEX','${report.organization.partyIndex.cityIndex}')" /></td>
															<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('PRODUCT_INDEX','${report.organization.partyIndex.cityIndex}')" /></td>
														</c:if>
														<c:if test="${filter.indexName=='PRODUCT_INDEX'}">
															<td align="left"><c:out value='${report.organization.partyIndex.productIndex}' /></td>
															<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('COMPANY_INDEX','${report.organization.partyIndex.productIndex}')" /></td>
															<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('PERSON_INDEX','${report.organization.partyIndex.productIndex}')" /></td>
															<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('CITY_INDEX','${report.organization.partyIndex.productIndex}')" /></td>
														</c:if>
														<td align="right"><fmt:formatNumber value='${report.january-report.januaryCogs}' pattern="#,###.00"/></td>
														<td align="right"><fmt:formatNumber value='${report.february-report.februaryCogs}'  pattern="#,###.00"/></td>
														<td align="right"><fmt:formatNumber value='${report.march-report.marchCogs}'  pattern="#,###.00"/></td>
														<td align="right"><fmt:formatNumber value='${report.april-report.aprilCogs}'  pattern="#,###.00"/></td>
														<td align="right"><fmt:formatNumber value='${report.may-report.mayCogs}'  pattern="#,###.00"/></td>
														<td align="right"><fmt:formatNumber value='${report.june-report.juneCogs}'  pattern="#,###.00"/></td>
														<td align="right"><fmt:formatNumber value='${report.july-report.julyCogs}'  pattern="#,###.00"/></td>
														<td align="right"><fmt:formatNumber value='${report.august-report.augustCogs}'  pattern="#,###.00"/></td>
														<td align="right"><fmt:formatNumber value='${report.september-report.septemberCogs}'  pattern="#,###.00"/></td>
														<td align="right"><fmt:formatNumber value='${report.october-report.octoberCogs}'  pattern="#,###.00"/></td>
														<td align="right"><fmt:formatNumber value='${report.november-report.novemberCogs}'  pattern="#,###.00"/></td>
														<td align="right"><fmt:formatNumber value='${report.december-report.decemberCogs}'  pattern="#,###.00"/></td>
														<td align="right"><fmt:formatNumber value='${report.total-report.totalCogs}'  pattern="#,###.00"/></td>
														<c:set var="gtotal" value="${gtotal+(report.total-report.totalCogs)}" />
														<c:set var="gjan" value="${gjan+(report.january-report.januaryCogs)}" />
														<c:set var="gmar" value="${gmar+(report.march-report.marchCogs)}" />
														<c:set var="gfeb" value="${gfeb+(report.february-report.februaryCogs)}" />
														<c:set var="gapr" value="${gapr+(report.april-report.aprilCogs)}" />
														<c:set var="gmay" value="${gmay+(report.may-report.mayCogs)}" />
														<c:set var="gjun" value="${gjun+(report.june-report.juneCogs)}" />
														<c:set var="gjul" value="${gjul+(report.july-report.julyCogs)}" />
														<c:set var="gaug" value="${gaug+(report.august-report.augustCogs)}" />
														<c:set var="gsep" value="${gsep+(report.september-report.septemberCogs)}" />
														<c:set var="goct" value="${goct+(report.october-report.octoberCogs)}" />
														<c:set var="gnov" value="${gnov+(report.november-report.novemberCogs)}" />
														<c:set var="gdec" value="${gdec+(report.december-report.decemberCogs)}" />

													</tr>
												</c:forEach>
												</tbody>
												<tfooter>
												<tr>
													<td colspan="4" align="right" style="border-top: solid 1px #000000;">&nbsp;</td>
													<td align="right" style="border-top: solid 1px #000000;"><fmt:formatNumber value='${gjan}'  pattern="#,###.00"/></td>
													<td align="right" style="border-top: solid 1px #000000;"><fmt:formatNumber value='${gfeb}'  pattern="#,###.00"/></td>
													<td align="right" style="border-top: solid 1px #000000;"><fmt:formatNumber value='${gmar}'  pattern="#,###.00"/></td>
													<td align="right" style="border-top: solid 1px #000000;"><fmt:formatNumber value='${gapr}'  pattern="#,###.00"/></td>
													<td align="right" style="border-top: solid 1px #000000;"><fmt:formatNumber value='${gmay}'  pattern="#,###.00"/></td>
													<td align="right" style="border-top: solid 1px #000000;"><fmt:formatNumber value='${gjun}'  pattern="#,###.00"/></td>
													<td align="right" style="border-top: solid 1px #000000;"><fmt:formatNumber value='${gjul}'  pattern="#,###.00"/></td>
													<td align="right" style="border-top: solid 1px #000000;"><fmt:formatNumber value='${gaug}'  pattern="#,###.00"/></td>
													<td align="right" style="border-top: solid 1px #000000;"><fmt:formatNumber value='${gsep}'  pattern="#,###.00"/></td>
													<td align="right" style="border-top: solid 1px #000000;"><fmt:formatNumber value='${goct}'  pattern="#,###.00"/></td>
													<td align="right" style="border-top: solid 1px #000000;"><fmt:formatNumber value='${gnov}'  pattern="#,###.00"/></td>
													<td align="right" style="border-top: solid 1px #000000;"><fmt:formatNumber value='${gdec}'  pattern="#,###.00"/></td>
													<td align="right" style="border-top: solid 1px #000000;"><fmt:formatNumber value='${gtotal}'  pattern="#,###.00"/></td>
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
	function showDetails(index,param){
		var cidx=$("#currentIndex").val();
		switch(cidx){
		case "PERSON_INDEX":
			$("#personHidden").val(param);
			break;
		case "CITY_INDEX":
			$("#cityHidden").val(param);
			break;
		case "COMPANY_INDEX":
			$("#companyHidden").val(param);
			break;
		case "PRODUCT_INDEX":
			$("#productHidden").val(param);
			break;
		}
		$("#indexName").val(index);
		document.filterForm.action="<c:url value='/page/dashboardchartcustomermonthlygross.htm?page=2'/>";
		document.filterForm.submit();
	}
</script>
</html>
