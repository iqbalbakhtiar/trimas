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
<title>Annual Sales (By Quarter)</title>
<style type="text/css" media="screen">
<!--
@import url("assets/sirius.css");

-->
<!--
@import url("css/report_format.css");
-->
</style>

<%@ include file="/filter/dashboard/annualFilterDetail.jsp"%>
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
						<td width="60%">Dashboard > Annual Sales (By Quarter)</td>
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
									Annual Sales (By Quarter)<br/>
								</h1>

								<div class="toolbar">
									<a class="item-button-back" href="javascript:history.go(-1);"><span>Back</span></a>
									<a class="item-button-print" href="javascript:window.print();"><span>Print</span></a>
									<div dojoType="Toggler" targetId="filter"><a class="item-button-search" href="javascript:return;"><span>Filter</span></a></div>
									<div style="float:right;">
										<a class="item-button-chart" href="<c:url value='/page/dashboardchartcustomermonthly.htm'/>"><span>Net Sales View</span></a>
										<a class="item-button-chart" href="<c:url value='/page/dashboardchartcustomermonthlygross.htm'/>"><span>Gross Profit View</span></a>
										<a class="item-button-chart" href="<c:url value='/page/dashboardchartcustomermonthlyqty.htm'/>"><span>Quantity Sold View</span></a>
									</div>
								</div>

								<div class="main-box">
									<div class="main_container">
										<center><img src="<c:url value='/charts/${filename}'/>.png"/></center>
										<table width="100%" cellpadding="5" cellspacing="0" class="report-table">
											<thead>
												<tr>
													<c:if test="${filter.indexName=='PERSON_INDEX'}">
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Person</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Company</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Location</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Product</th>
													</c:if>
													<c:if test="${filter.indexName=='COMPANY_INDEX'}">
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Company</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Person</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Location</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Product</th>
													</c:if>
													<c:if test="${filter.indexName=='CITY_INDEX'}">
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Location</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Company</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Person</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Product</th>
													</c:if>
													<c:if test="${filter.indexName=='PRODUCT_INDEX'}">
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Product</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Company</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Person</th>
														<th width="10%" align="left" style="border-bottom: 1px solid black; border-top: 1px solid black;">Location</th>
													</c:if>
													<th width="15%" align="right" style="border-bottom: 1px solid black; border-top: 1px solid black;">Q1</th>
													<th width="15%" align="right" style="border-bottom: 1px solid black; border-top: 1px solid black;">Q2</th>
													<th width="15%" align="right" style="border-bottom: 1px solid black; border-top: 1px solid black;">Q3</th>
													<th width="15%" align="right" style="border-bottom: 1px solid black; border-top: 1px solid black;">Q4</th>
													<th width="15%" align="right" style="border-bottom: 1px solid black; border-top: 1px solid black;">Total</th>
												</tr>
											</thead>
											<tbody>
												<c:set var="gtotal" value="0" />
												<c:set var="t1" value="0" />
												<c:set var="t2" value="0" />
												<c:set var="t3" value="0" />
												<c:set var="t4" value="0" />
												<c:forEach items='${reports}' var='report'>
													<c:set var="total" value="0" />
													<tr>
														<c:if test="${filter.indexName=='PERSON_INDEX'}">
															<td align="left"><c:out value='${report.salesPerson.firstName}' /></td>
															<c:if test="${empty filter.companyIndex}">
																<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('COMPANY_INDEX','${report.salesPerson.id}')" /></td>
															</c:if>
															<c:if test="${not empty filter.companyIndex}">
																<td align="left">${report.organizationPartyIndex.companyIndex}</td>
															</c:if>
															<c:if test="${empty filter.cityIndex}">
																<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('CITY_INDEX','${report.salesPerson.id}')" /></td>
															</c:if>
															<c:if test="${not empty filter.cityIndex}">
																<td align="left">${report.organizationPartyIndex.cityIndex}</td>
															</c:if>
															<c:if test="${empty filter.productIndex}">
																<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('PRODUCT_INDEX','${report.salesPerson.id}')" /></td>
															</c:if>
															<c:if test="${not empty filter.productIndex}">
																<td align="left">${report.organizationPartyIndex.productIndex}</td>
															</c:if>
														</c:if>
														<c:if test="${filter.indexName=='COMPANY_INDEX'}">
															<td align="left"><c:out value='${report.organizationPartyIndex.companyIndex}' /></td>
															<c:if test="${empty filter.personIndex}">
																<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('PERSON_INDEX','${report.organizationPartyIndex.companyIndex}')" /></td>
															</c:if>
															<c:if test="${not empty filter.personIndex}">
																<td align="left">${report.salesPerson.firstName}</td>
															</c:if>
															<c:if test="${empty filter.cityIndex}">
																<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('CITY_INDEX','${report.organizationPartyIndex.companyIndex}')" /></td>
															</c:if>
															<c:if test="${not empty filter.cityIndex}">
																<td align="left">${report.organizationPartyIndex.cityIndex}</td>
															</c:if>
															<c:if test="${empty filter.productIndex}">
																<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('PRODUCT_INDEX','${report.organizationPartyIndex.companyIndex}')" /></td>
															</c:if>
															<c:if test="${not empty filter.productIndex}">
																<td align="left">${report.organizationPartyIndex.productIndex}</td>
															</c:if>
														</c:if>
														<c:if test="${filter.indexName=='CITY_INDEX'}">
															<td align="left"><c:out value='${report.organizationPartyIndex.cityIndex}' /></td>
															<c:if test="${empty filter.companyIndex}">
																<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('COMPANY_INDEX','${report.organizationPartyIndex.cityIndex}')" /></td>
															</c:if>
															<c:if test="${not empty filter.companyIndex}">
																<td align="left">${report.organizationPartyIndex.companyIndex}</td>
															</c:if>
															<c:if test="${empty filter.personIndex}">
																<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('PERSON_INDEX','${report.organizationPartyIndex.cityIndex}')" /></td>
															</c:if>
															<c:if test="${not empty filter.personIndex}">
																<td align="left">${report.salesPerson.firstName}</td>
															</c:if>
															<c:if test="${empty filter.productIndex}">
																<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('PRODUCT_INDEX','${report.organizationPartyIndex.cityIndex}')" /></td>
															</c:if>
															<c:if test="${not empty filter.productIndex}">
																<td align="left">${report.organizationPartyIndex.productIndex}</td>
															</c:if>
														</c:if>
														<c:if test="${filter.indexName=='PRODUCT_INDEX'}">
															<td align="left"><c:out value='${report.organizationPartyIndex.productIndex}' /></td>
															<c:if test="${empty filter.companyIndex}">
																<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('COMPANY_INDEX','${report.organizationPartyIndex.productIndex}')" /></td>
															</c:if>
															<c:if test="${not empty filter.companyIndex}">
																<td align="left">${report.organizationPartyIndex.companyIndex}</td>
															</c:if>
															<c:if test="${empty filter.personIndex}">
																<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('PERSON_INDEX','${report.organizationPartyIndex.productIndex}')" /></td>
															</c:if>
															<c:if test="${not empty filter.personIndex}">
																<td align="left">${report.salesPerson.firstName}</td>
															</c:if>
															<c:if test="${empty filter.cityIndex}">
																<td align="center"><img src="<c:url value='/assets/icons/search.gif'/>" onclick="javascript:showDetails('CITY_INDEX','${report.organizationPartyIndex.productIndex}')" /></td>
															</c:if>
															<c:if test="${not empty filter.cityIndex}">
																<td align="left">${report.organizationPartyIndex.cityIndex}</td>
															</c:if>
														</c:if>
														<td align="right">
															<fmt:formatNumber value="${report.quarter1!=null?report.quarter1:0}" />
															<c:set var="t1" value="${t1+report.quarter1}" />
														</td>
														<td align="right">
															<fmt:formatNumber value="${report.quarter2!=null?report.quarter2:0}" />
															<c:set var="t2" value="${t2+report.quarter2}" />
														</td>
														<td align="right">
															<fmt:formatNumber value="${report.quarter3!=null?report.quarter3:0}" />
															<c:set var="t3" value="${t3+report.quarter3}" />
														</td>
														<td align="right">
															<fmt:formatNumber value="${report.quarter4!=null?report.quarter4:0}" />
															<c:set var="t4" value="${t4+report.quarter4}" />
														</td>
														<c:set var="total" value="${report.quarter1+report.quarter2+report.quarter3+report.quarter4}" />
														<c:set var="gtotal" value="${total+gtotal}" />
														<td align="right"><fmt:formatNumber value="${total}" /></td>
													</tr>
												</c:forEach>
												</tbody>
												<tfooter>
												<tr>
													<td align="left" style="border-top: solid 1px #000000;"></td>
													<td align="left" style="border-top: solid 1px #000000;"></td>
													<td align="left" style="border-top: solid 1px #000000;"></td>
													<td align="left" style="border-top: solid 1px #000000;"></td>
													<td align="right" style="border-top: solid 1px #000000;"><fmt:formatNumber value='${t1}' /></td>
													<td align="right" style="border-top: solid 1px #000000;"><fmt:formatNumber value='${t2}' /></td>
													<td align="right" style="border-top: solid 1px #000000;"><fmt:formatNumber value='${t3}' /></td>
													<td align="right" style="border-top: solid 1px #000000;"><fmt:formatNumber value='${t4}' /></td>
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
		document.filterForm.action="<c:url value='/page/dashboardchartcustomerquarter.htm?page=2'/>";
		document.filterForm.submit();
	}
</script>
</body>
</html>