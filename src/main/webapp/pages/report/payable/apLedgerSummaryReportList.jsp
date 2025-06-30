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
	<title>${title}</title>
	<%@ include file="/common/sirius-header.jsp"%>
	<style type="text/css" media="screen">
		@import url("<c:url value='/assets/sirius.css'/>");
		@import url("<c:url value='/assets/sirius-print.css'/>");
	</style>
	<style type="text/css" media="print">
		@import url("<c:url value='/assets/sirius-print.css'/>");
	</style>
	<script type="text/javascript">
		function printPage()
		{
   			print();
		}
	</script>
</head>
<body>
<div id="se-r00">
	<div id="se-r01">&nbsp;</div>
	<div id="se-r02">&nbsp;</div>
</div>
<div id="se-containers">
	<div class="area" dojoType="Container" id="quick_link_container">
		<%@ include file="/common/sirius-menu.jsp"%>
	</div>
	<div id="se-navigator">
		<div class="area" dojoType="Container" id="quick_link_container">
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td width="60%">${breadcrumb}</td>
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
							<h1 class="page-title">${pageTitle}</h1>
					  		<div class="toolbar">
								<a class="item-button-back" href="<c:url value='/page/apledgersummarypre.htm'/>"><span><spring:message code="sirius.back"/></span></a>
								<a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
								<a class="item-button-export-xls" download="apledgersummaryexcell.xls" href="#" onclick="return ExcellentExport.excel(this, 'apledgersummary', 'AP Ledger Summary');"><span><spring:message code="sirius.export"/></span></a>
					  		</div>
						</div>
						
						<div class="main-box">
                            <div class="main_container">
                            	<div class="report"><strong>REPORT AP LEDGER SUMMARY</strong></div>
                                <div class="clears">&nbsp;</div>
                                <div class="report">
                                    <table width="498">	
                                    <tr>	
                                        <td width="100" ><spring:message code="organization"/></td>
                                        <td width="11" >:&nbsp;&nbsp;</td>
                                        <td width="344" ><c:out value='${organization.fullName}'/></td>			  
                                    </tr>
                                    <tr>	
                                        <td width="100" ><spring:message code="sirius.period"/></td>
                                        <td width="11" >:&nbsp;&nbsp;</td>
                                        <td width="344" ><fmt:formatDate value='${reportCriteria.dateFrom}' pattern='dd MMM yyyy' /> &nbsp;-&nbsp; <fmt:formatDate value='${reportCriteria.dateTo}' pattern='dd MMM yyyy' /></td>			  
                                    </tr>
                                    </table>
                                </div>
                                <div class="clears">&nbsp;</div>

                                <c:set var='totalOpening' value='${0}'/>
                                <c:set var='totalDebet' value='${0}'/>
                                <c:set var='totalCredit' value='${0}'/>
                                <c:set var='totalClosing' value='${0}'/>
                                <table width="100%"  cellpadding="5" cellspacing="0" class="report-table" id="apledgersummary" name="apledgersummary">
                                <thead>
                                <tr>
                                    <th width="31%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="supplier"/></th>
                               	  	<th width="14%" align="right" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="accreport.opening"/></th>
                                  	<th width="14%" align="right" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="sirius.credit2"/></th>
                                  	<th width="14%" align="right" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="sirius.debit2"/></th>
                               	  	<th align="right" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="accreport.closing"/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items='${reports}' var='report'>
                                <tr>
                                    <td align="left"><a href="<c:url value='/page/apledgerdetailview.htm?organization=${reportCriteria.organization}&supplierId=${report.supplier.id}&dateFrom='/><fmt:formatDate value='${reportCriteria.dateFrom}' pattern='dd-MM-yyyy'/>&dateTo=<fmt:formatDate value='${reportCriteria.dateTo}' pattern='dd-MM-yyyy'/>"><c:out value='${report.supplier.fullName}'/></a></td>
                                    <td align="right"><fmt:formatNumber value='${report.opening}' pattern=',##0.00'/></td>
                                    <td align="right"><fmt:formatNumber value='${report.debet}' pattern=',##0.00'/></td>
                                    <td align="right"><fmt:formatNumber value='${report.credit}' pattern=',##0.00'/></td>
                                    <td align="right"><fmt:formatNumber value='${report.closing}' pattern=',##0.00'/></td>
                                </tr>
                                <c:set var='totalOpening' value='${totalOpening+report.opening}'/>
                                <c:set var='totalDebet' value='${totalDebet+report.debet}'/>
                                <c:set var='totalCredit' value='${totalCredit+report.credit}'/>
                                <c:set var='totalClosing' value='${totalClosing+report.closing}'/>
                                </c:forEach>
                                </tbody>
                                <tfoot>
                                <tr>
                                  	<td align="left" style="border-top:1px solid black;border-bottom:1px solid black;"><strong><spring:message code="sirius.total"/></strong></td>
                                    <td align="right" width="14%" style="border-top:1px solid black;border-bottom:1px solid black;">
                                        <strong>
                                        <fmt:formatNumber value='${totalOpening}' pattern=',##0.00'/>
                                        <c:remove var='totalOpening'/>
                                      </strong>
                                    </td>
                                    <td align="right" width="14%" style="border-top:1px solid black;border-bottom:1px solid black;">
                                        <strong>
                                        <fmt:formatNumber value='${totalDebet}' pattern=',##0.00'/>
                                        <c:remove var='totalDebet'/>
                                      </strong>
                                    </td>
                                    <td align="right" width="14%" style="border-top:1px solid black;border-bottom:1px solid black;">
                                        <strong>
                                        <fmt:formatNumber value='${totalCredit}' pattern=',##0.00'/>
                                        <c:remove var='totalCredit'/>
                                      </strong>
                                    </td>
                                    <td align="right" width="14%" style="border-top:1px solid black;border-bottom:1px solid black;">
                                        <strong>
                                        <fmt:formatNumber value='${totalClosing}' pattern=',##0.00'/>
                                        <c:remove var='totalClosing'/>
                                   	  </strong>
                               	  	</td>
                                </tr>
                                </tfoot>
                                </table>
                            </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div><!-- /rounded -->

	<!-- footer -->
  	<%@ include file="/common/sirius-footer.jsp"%>
	<!-- /footer -->
</div><!-- /main containers -->



</body>
<!-- END OF BODY -->
</html>
