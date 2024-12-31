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
							<h1 class="page-title">${pageTitle}<br></h1>
							<div class="toolbar">
								<a class="item-button-back" href="<c:url value='/page/trialbalancereportpre.htm'/>"><span><spring:message code='sirius.back'/></span></a>
								<a class="item-button-print" href="javascript:window.print();"><span><spring:message code='sirius.print'/></span></a>
								<a class="item-button-export-xls" href="<c:url value='/page/trialbalancereportexcelview.xls'/>"><span><spring:message code='sirius.export'/></span></a>
					  		</div>
						</div>
						<div class="main-box">
							<div class="main_container">
                                <div class="pageTitle"><strong class="margin-left">${reportTitle}</strong></div>
                                <div class="clears">&nbsp;</div>
                                <table width="481">
                                <tr >
                            		<td width="100"><spring:message code='sirius.period'/></td>
                                	<td width="10">:</td>
                                	<td width="355">
                                        <c:forEach items='${criteria.accountingPeriods}' var='period'>
                                            <c:out value="${status.index > 0 ? ', ' : ''}"/><c:out value="${period.name}"/>
                                         </c:forEach>
                                     </td>
                            	</tr>
                            	<tr >			  
                                	<td><spring:message code='organization'/></td>
                                	<td>:</td>
                                	<td>
                                		<c:forEach items='${criteria.organizations}' var='org'>
                                    		<c:out value="${status.index > 0 ? ', ' : ''}"/><c:out value="${org.fullName}"/>
                                    	</c:forEach>
                                	</td>
                            	</tr>
                                </table>
                                <br/>

                                <table width="100%" border="0" cellpadding="3" cellspacing="0" class="report-table">
                              	<tr>
                                    <td rowspan="2" colspan="2" align="center" valign="top" style="border:1px solid black;"><spring:message code='work.account'/></td>
           	  	  	    	  	  	<td align="center" style="border-right:1px solid black;border-top:1px solid black;"><spring:message code='work.begining.balance'/></td>
                                    <td colspan="2" align="center" style="border-bottom:1px solid black; border-right:1px solid black;border-top:1px solid black;"><spring:message code='work.trial.balance'/></td>
                                    <td colspan="2" align="center" style="border-bottom:1px solid black;border-top:1px solid black;"><spring:message code='work.Adjustment'/></td>				
                                	<td align="center" style="border-left:1px solid black;border-top:1px solid black;border-right:1px solid black;"><spring:message code='work.ending.balance'/></td>
                                </tr>
                                <tr>
                                	<td width="13%" align="center" style="border-bottom:1px solid black; border-right:1px solid black;">&nbsp;</td>	
                           	  	  	<td width="12%" align="center" style="border-bottom:1px solid black; border-right:1px solid black;"><spring:message code='sirius.debit'/></td>	
                                  	<td width="12%" align="center" style="border-bottom:1px solid black; border-right:1px solid black;"><spring:message code='sirius.credit'/></td>
                               	  	<td width="12%" align="center" style="border-bottom:1px solid black; border-right:1px solid black;"><spring:message code='sirius.debit'/></td>
                                    <td width="12%" align="center" style="border-bottom:1px solid black;"><spring:message code='sirius.credit'/></td>
                           	  	  	<td width="13%" align="center" style="border-bottom:1px solid black;border-left:1px solid black;border-right:1px solid black;">&nbsp;</td>
                                </tr>       
                                <c:set var='__opening__' value='0'/>
                                <c:set var='d_non' value='0'/>
                                <c:set var='c_non' value='0'/>
                                <c:set var='d_adj' value='0'/>
                                <c:set var='c_adj' value='0'/>
                                <c:set var='__closing__' value='0'/>
                                <c:forEach items='${adapter.reports}' var='report'>                                
                                <tr>
                               	  	<td width="6%"><c:out value='${report.account.code}'/></td>
                               	  	<td width="20%"><c:out value='${report.account.name}'/></td>	
                                    <td align="right">
                                        <c:if test='${report.opening >= 0}'>
                                          <fmt:formatNumber value='${report.opening}' pattern=",##0.00"/>
                                        </c:if>
                                        <c:if test='${report.opening < 0}'>
                                          (<fmt:formatNumber value='${report.opening*-1}' pattern=",##0.00"/>)
                                        </c:if>
                                    </td>
                                    <td align="right">
                                        <fmt:formatNumber value='${report.debet}' pattern=",##0.00"/>
                                        <c:set var='d_non' value='${d_non+report.debet}'/>
                                    </td>
                                    <td align="right">
                                    	<fmt:formatNumber value='${report.credit}' pattern=",##0.00"/>
                                        <c:set var='c_non' value='${c_non+report.credit}'/>
                                    </td>
                                    <td align="right">
                                    	<fmt:formatNumber value='${report.adjustmentdebet}' pattern=",##0.00"/>
                                        <c:set var='d_adj' value='${d_adj+report.adjustmentdebet}'/>
                                    </td>
                                    <td align="right">
                                    	<fmt:formatNumber value='${report.adjustmentcredit}' pattern=",##0.00"/>
                                        <c:set var='c_adj' value='${c_adj+report.adjustmentcredit}'/>
                                    </td>
                                    <td align="right">
                                    	<c:if test='${report.closing >= 0}'>
	                                    	<fmt:formatNumber value='${report.closing}' pattern=",##0.00"/>
                                        </c:if>
                                    	<c:if test='${report.closing < 0}'>
	                                    	(<fmt:formatNumber value='${report.closing*-1}' pattern=",##0.00"/>)
                                      </c:if>
                                    </td>                                    
                                    <c:set var='__opening__' value='${__opening__ + report.opening}'/>
                                    <c:set var='__closing__' value='${__closing__+ report.closing}'/>
                                </tr>
                                </c:forEach>         
                                <tr>
                                    <td align="right" colspan="2"><strong>Total :</strong></td>
                                    <td align="right"><strong><fmt:formatNumber value="${__opening__}" pattern=",##0.00"/></strong></td>	
                                    <td align="right"><strong><fmt:formatNumber value="${d_non}" pattern=",##0.00"/></strong></td>	
                                    <td align="right"><strong><fmt:formatNumber value="${c_non}" pattern=",##0.00"/></strong></td>
                                    <td align="right"><strong><fmt:formatNumber value="${d_adj}" pattern=",##0.00"/></strong></td>
                                    <td align="right"><strong><fmt:formatNumber value="${c_adj}" pattern=",##0.00"/></strong></td>
                                    <td align="right"><strong><fmt:formatNumber value="${__closing__}" pattern=",##0.00"/></strong></td>	
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
