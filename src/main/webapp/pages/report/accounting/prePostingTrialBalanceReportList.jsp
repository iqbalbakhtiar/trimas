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
		<div id="se-navigator">
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
								<a class="item-button-back" href="<c:url value='/page/prepostingtrialbalancereportpre.htm'/>"><span><spring:message code="sirius.back"/></span></a>
								<a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
					  			<a class="item-button-export-xls" href="<c:url value='/page/prepostingtrialbalancereportexcelview.xls'/>"><span><spring:message code="sirius.export"/></span></a>
					  		</div>
						</div>
						<div class="main-box">
							<div class="main_container">
                                <div class="pageTitle"><strong class="margin-left">${reportTitle}</strong></div>
                                <br/>
                                <table width="481">
                                <tr >
                                    <td width="100"><spring:message code="accountingperiod"/></td>
                                    <td width="10">:</td>
                                    <td width="355">
                                        <c:forEach items='${criteria.accountingPeriods}' var='period'>
                                            <c:out value="${status.index > 0 ? ', ' : ''}"/><c:out value="${period.name}"/>
                                        </c:forEach>
                                    </td>
                            	</tr>
                            	<tr >			  
                                    <td><spring:message code="accreport.organization"/></td>
                                    <td>:</td>
                                    <td>
                                        <c:forEach items='${criteria.organizations}' var='org'>
                                            <c:out value="${status.index > 0 ? ', ' : ''}"/><c:out value="${org.fullName}"/>
                                        </c:forEach>
                                    </td>
                            	</tr>
                                </table>
                                <div class="clears">&nbsp;</div>
                                <table width="100%" border="0" cellpadding="3" cellspacing="0" class="report-table">
                                <thead>
                                <tr>
                                    <th rowspan="2" colspan="2" align="center" valign="middle" class="bordered"><spring:message code="glaccount"/></th>
                               	  	<th width="15%" rowspan="2" align="center" valign="middle" class="border-top border-bottom border-right"><spring:message code="accreport.opening"/></th>
                               	  	<th class="border-top border-bottom" colspan="2" align="center"><spring:message code="accreport.trial"/></th>
                                    <th width="15%" rowspan="2" align="center" valign="middle" class="bordered"><spring:message code="accreport.closing"/></td>
                                </tr>                               
                          		<tr>
                                    <th width="15%" align="center" class="border-bottom border-right"><spring:message code="accreport.debit"/></th>	
                                	<th width="15%" align="center" class="border-bottom"><spring:message code="accreport.credit"/></th>
                                </tr>
                                </thead>
                                <c:set var='opening' value='${0}'/>
                                <c:set var='ending' value='${0}'/>
                                <c:set var='debet' value='${0}'/>
                                <c:set var='credit' value='${0}'/>
                                <tbody>
                                <c:forEach items='${adapter.reports}' var='report'>
                                <tr>
                                    <td width="7%"><c:out value="${report.account.code}"/></td>
                                    <td width="33%"><c:out value="${report.account.name}"/></td>
                                    <td align="right" >
                                        <c:if test='${report.opening >= 0}'>
                                        	<fmt:formatNumber value='${report.opening}' groupingUsed='true' pattern=',##0.00'/>
                                        </c:if>
                                        <c:if test='${report.opening < 0}'>
                                        	(<fmt:formatNumber value='${report.opening*-1}' groupingUsed='true' pattern=',##0.00'/>)
                                        </c:if>
                                  	</td>	
                                    <td align="right" >
                                    	<fmt:formatNumber value='${report.debet}' groupingUsed='true' pattern=',##0.00'/>
                                      <c:set var='debet' value='${debet+report.debet}'/>
                                    </td>
                                    <td align="right" >
                                    	<fmt:formatNumber value='${report.credit}' groupingUsed='true' pattern=',##0.00'/>
                                       <c:set var='credit' value='${credit+report.credit}'/>
                                  	</td>
                                    <td align="right" >
                                        <c:if test='${report.closing >= 0}'>
                                        	<fmt:formatNumber value='${report.closing}' groupingUsed='true' pattern=',##0.00'/>
                                        </c:if>
                                        <c:if test='${report.closing < 0}'>
                                        	(<fmt:formatNumber value='${report.closing*-1}' groupingUsed='true' pattern=',##0.00'/>)                                        
                                        </c:if>
                                    </td>
                                </tr>
                                <c:set var='opening' value='${opening+report.opening}'/>
                                <c:set var='ending' value='${ending+report.closing}'/>
                                </c:forEach> 
                                </tbody>
                                <tfoot>                
                                <tr>
                                    <td height="29" align="right" colspan="2" class="border-top"><strong>Total :</strong></td>
                                    <td align="right" class="border-top">
                                    	<strong>
                                    	<c:if test='${opening >= 0}'>
                                        	<fmt:formatNumber value="${opening}" groupingUsed='true' pattern=',##0.00'/>
                                        </c:if>
                                        <c:if test='${opening < 0}'>
                                        	(<fmt:formatNumber value="${opening*-1}" groupingUsed='true' pattern=',##0.00'/>)
                                        </c:if>
                                        </strong>
                                    </td>
                                    <td align="right" class="border-top">
                                    	<strong>
                                    	<fmt:formatNumber value="${debet}" groupingUsed='true' pattern=',##0.00'/>
                                    	</strong>
                                    </td>	
                                    <td align="right" class="border-top">
                                    	<strong>
                                    	<fmt:formatNumber value="${credit}" groupingUsed='true' pattern=',##0.00'/>
                                    	</strong>
                                    </td>
                                    <td align="right" class="border-top">
                                    	<strong>
                                    	<c:if test='${ending >= 0}'>
                                        	<fmt:formatNumber value="${ending}" groupingUsed='true' pattern=',##0.00'/>
                                        </c:if>
                                        <c:if test='${ending < 0}'>
                                        	(<fmt:formatNumber value="${ending*-1}" groupingUsed='true' pattern=',##0.00'/>)
                                        </c:if>  
                                        </strong>                                  	
                                    </td>
                                </tr>
                                </tfoot>
                                </table>
                                <br>
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
