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
								<a class="item-button-back" href="<c:url value='/page/apledgerdetailpre.htm'/>"><span><spring:message code="sirius.back"/></span></a>
								<a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
								<a class="item-button-export-xls" download="apledgerdetailexcell.xls" href="#" onclick="return ExcellentExport.excel(this, 'apledgerdetail', 'AP Ledger Detail');"><span><spring:message code="sirius.export"/></span></a>
					  		</div>
						</div>
						<div class="main-box">
                            <div class="main_container">
                            	<div class="report"><strong>REPORT AP LEDGER DETAIL</strong></div>
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
                                <c:set var='g_all' value='${0}'/>
                                <table width="100%" cellpadding="5" cellspacing="0" class="report-table" id="apledgerdetail" name="apledgerdetail">
                                <tr>
                                  <th width="10%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="sirius.date"/></th>
                                  <th width="15%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="supplier"/></th>
                                  <th width="10%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;"></th>
                               	  <th width="17%" align="right" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="sirius.credit2"/></th>
                               	  <th width="17%" align="right" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="sirius.debit2"/></th>
                           	  	  <th width="17%" align="right" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code="sirius.amount"/></th>
                                </tr>
                                <c:forEach items='${reports}' var='report'>
                                <c:set var='grand' value='${report.opening}'/>
                                <c:set var='t_debet' value='${0}'/>
                                <c:set var='t_credit' value='${0}'/>
                                <tr>
                                    <td align="left" colspan="5"><strong><c:out value='${report.supplier.fullName}'/></strong></td>
                                </tr>
                                <tr>
                                    <td align="left"><fmt:formatDate value='${report.start}' pattern='dd-MMM-yyyy'/></td>
                                    <td></td>
                                    <td align="left" colspan="3"><spring:message code="accreport.opening"/></td>
                                    <td align="right"><fmt:formatNumber value='${report.opening}' pattern=',##0.00'/></td>
                                </tr>
                                <c:forEach items='${report.views}' var='view'>
                                <tr>
                                    <td align="left"><fmt:formatDate value='${view.date}' pattern='dd-MMM-yyyy'/></td>
                                    <td align="left"><a href="<c:url value='/page/${view.uri}?id=${view.referenceId}'/>">${view.code}</a></td>
                                    <td></td>
                                    <td align="right">
                                    	<fmt:formatNumber value="${view.debet}" pattern=',##0.00'/>
                                        <c:set var='t_debet' value='${t_debet+view.debet}'/>
                                       	<c:set var='grand' value='${grand+view.credit-view.debet}'/>
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
                                    <td align="left"><spring:message code="accreport.closing"/></td>
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
                                  	<td colspan="4" align="left" style="border-top:1px solid black;border-bottom:1px solid black;"><strong><spring:message code="sirius.total"/></strong></td>
                                    <td align="left" style="border-top:1px solid black;border-bottom:1px solid black;"></td>
                                    <td align="right" width="17%" style="border-top:1px solid black;border-bottom:1px solid black;">
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
