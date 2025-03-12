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
	<!-- <style type="text/css" media="screen">
		@import url("<c:url value='/assets/sirius.css'/>");
		@import url("<c:url value='/assets/sirius-print.css'/>");
	</style> -->
	<!-- <style type="text/css" media="print">
		@import url("<c:url value='/assets/sirius-print.css'/>");
	</style> -->
	
	<script type="text/javascript">
			function printPage()
			{
	   			print();
			}
	</script>
	<style type="text/css">
        .hidden-row{
            display: none;
        }
    </style>
</head>
<body>
<div class="area" dojoType="Container" id="quick_link_container">
	<%@ include file="/common/sirius-header.jsp"%>
</div>
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
							<h1 class="page-title">${pageTitle}<br>
							</h1>

					  		<div class="toolbar">
								<a class="item-button-back" href="<c:url value='/page/apledgerdetailpre.htm'/>"><span>Back</span></a>
								<a class="item-button-print" href="javascript:window.print();"><span>Print</span></a>
                            	<%-- <a class="item-button-export-xls" href="<c:url value='/page/apledgerdetailexcell.xls'/>"><span>Export</span></a> --%>
								<a class="item-button-export-xls" download="apledgerdetailexcell.xls" href="#" onclick="return ExcellentExport.excel(this, 'apledgerdetail', 'AP Ledger Detail');"><span>Export</span></a>
								<%-- <a class="item-button-rprev" href="<c:url value='/page/apledgerdetailview.htm?organization=${organization.id}&supplier=${criteria.supplier}&date='/><fmt:formatDate value='${criteria.prev}' pattern='dd-MM-yyyy'/>"><span>Prev</span></a>
								<a class="item-button-rnext" href="<c:url value='/page/apledgerdetailview.htm?organization=${organization.id}&supplier=${criteria.supplier}&date='/><fmt:formatDate value='${criteria.next}' pattern='dd-MM-yyyy'/>"><span>Next</span></a> --%>
					  		</div>
						</div>

						<div class="main-box">
                            <div class="main_container">
                            	<div class="report"><strong>REPORT AP LEDGER DETAIL</strong></div>
                                <div class="clears">&nbsp;</div>
                                <div class="report">
                                    <table width="498">
                                    <tr>
                                        <td width="100" >Company</td>
                                        <td width="11" >:&nbsp;&nbsp;</td>
                                        <td width="344" ><c:out value='${organization.fullName}'/></td>
                                    </tr>
                                    <tr>
                                        <td width="100" >Period</td>
                                        <td width="11" >:&nbsp;&nbsp;</td>
                                        <td width="344" ><fmt:formatDate value='${criteria.dateFrom}' pattern='dd MMM yyyy' /> &nbsp;-&nbsp; <fmt:formatDate value='${criteria.dateTo}' pattern='dd MMM yyyy' /></td>
                                    </tr>
                                    </table>
                                </div>
                                <div class="clears">&nbsp;</div>
                                <c:set var='g_all' value='${0}'/>
                                <table width="100%" cellpadding="5" cellspacing="0" class="report-table" name="apledgerdetail" id="apledgerdetail">
                                <%-- <tr class="hidden-row"><td colspan="5"><strong>REPORT AP LEDGER DETAIL</strong></td></tr>
                                <tr class="hidden-row">
                                    <td>Company</td>
                                    <td>:&nbsp;&nbsp;</td>
                                    <td><c:out value='${organization.firstName} ${organization.middleName} ${organization.lastName}'/></td>
                                </tr>
                                <tr class="hidden-row">
                                    <td>Period</td>
                                    <td>:&nbsp;&nbsp;</td>
                                    <td><fmt:formatDate value='${criteria.dateFrom}' pattern='dd MMM yyyy' /> &nbsp;-&nbsp; <fmt:formatDate value='${criteria.dateTo}' pattern='dd MMM yyyy' /></td>
                                </tr>
                                <tr class="hidden-row"><td colspan="5">&nbsp;</td></tr> --%>
                                <tr>
                                    <th width="10%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;">Date</th>
                                  <th width="15%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;">Supplier</th>
                                  <th width="10%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;"></th>
                               	  <th width="17%" align="right" style="border-bottom:1px solid black;border-top:1px solid black;">Debet</th>
                               	  <th width="17%" align="right" style="border-bottom:1px solid black;border-top:1px solid black;">Credit</th>
                           	  	  <th width="17%" align="right" style="border-bottom:1px solid black;border-top:1px solid black;">Amount</th>
                                </tr>
                                <c:forEach items='${reports}' var='report'>
                                <c:set var='grand' value='${report.opening}'/>
                                <c:set var='t_debet' value='${0}'/>
                                <c:set var='t_credit' value='${0}'/>
                                <tr>
                                	<%-- <td align="left"><c:out value='${report.supplier.code}'/></td>  --%>
                                    <td align="left" colspan="5"><c:out value='${report.supplier.fullName}'/></td>
                                </tr>
                                <tr>
                                    <td align="left"><fmt:formatDate value='${report.start}' pattern='dd-MMM-yyyy'/></td>
                                    <td></td>
                                    <td align="left" colspan="3">Opening Balance</td>
                                    <td align="right"><fmt:formatNumber value='${report.opening}' pattern=',##0.00'/></td>
                                </tr>
                                <c:forEach items='${report.views}' var='view'>
                                <tr>
                                    <td align="left"><fmt:formatDate value='${view.date}' pattern='dd-MMM-yyyy'/></td>
                                    <td align="left">${view.code}${(view.ledgerType=='PYM')?view.payment.paymentInformation.reference:' '}</td>
                                    <td></td>
                                    <td align="right">
                                    	<fmt:formatNumber value="${(view.ledgerType=='PYM')?view.paidAmount:view.debet}" pattern=',##0.00'/>
                                        <c:if test="${view.ledgerType=='PYM'}">
                                        	<c:set var='t_debet' value='${t_debet+view.paidAmount}'/>
                                        	<c:set var='grand' value='${grand+view.credit-view.paidAmount}'/>
                                        </c:if>
                                        <c:if test="${view.ledgerType!='PYM'}"> 
                                        	<c:set var='t_debet' value='${t_debet+view.debet}'/>
                                        	  <c:set var='grand' value='${grand+view.credit-view.debet}'/>
                                        </c:if>
                                    </td>
                                    <td align="right">
                                    	<fmt:formatNumber value='${view.credit}' pattern=',##0.00'/>
                                        <c:set var='t_debet' value='${t_credit+view.credit}'/>
                                    </td>
                                    <td align="right"><fmt:formatNumber value='${grand}' pattern=',##0.00'/></td>
                                </tr>
                                </c:forEach>
                                <tr>
                                    <td align="left"><fmt:formatDate value='${report.end}' pattern='dd-MM-yyyy'/></td>
                                    <td></td>
                                    <td align="left">Closing Balance</td>
                                    <td align="right"><fmt:formatNumber value='${t_debet}' pattern=',##0.00'/></td>
                                    <td align="right"><fmt:formatNumber value='${t_credit}' pattern=',##0.00'/></td>
                                    <td align="right"><fmt:formatNumber value='${grand}' pattern=',##0.00'/></td>
                                </tr>
                                <c:set var='g_all' value='${g_all+grand}'/>

                                <c:remove var='grand'/>
                                <c:remove var='t_debet'/>
                                <c:remove var='t_credit'/>
                                <tr>
                                	<td>&nbsp;</td>
                                	<td>&nbsp;</td>
                                	<td>&nbsp;</td>
                                	<td>&nbsp;</td>
                                	<td>&nbsp;</td>
                                </tr>
                                </c:forEach>
                                <tr>
                                  	<td colspan="4" align="left" style="border-top:1px solid black;"><strong>Total</strong></td>
                                    <td align="left" style="border-top:1px solid black;"></td>
                                    <td align="right" width="17%" style="border-top:1px solid black;">
                                        <strong>
                                        <fmt:formatNumber value='${g_all}' pattern=',##0.00'/>
                                        <c:remove var='g_all'/>
                                   	  </strong>
                               	  	</td>
                                </tr>
                                </table>
                            </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

  	<%@ include file="/common/sirius-footer.jsp"%>

</div>
</body>
</html>
