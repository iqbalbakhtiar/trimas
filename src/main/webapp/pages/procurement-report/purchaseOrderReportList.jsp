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
	<style type="text/css" media="screen">
		<!-- @import url("assets/sirius.css"); -->
		<!-- @import url("assets/sirius-print.css"); -->
    </style>
    
    <style type="text/css" media="print">
		<!-- @import url("assets/sirius-print.css"); -->
    </style>
	
    
	<script type="text/javascript">
		function printPage()
		{
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
								<a class="item-button-back" href="<c:url value='/page/purchaseorderreportpre.htm'/>"><span>Back</span></a>
								<a class="item-button-print" href="javascript:window.print();"><span>Print</span></a>
                            	<a class="item-button-export-xls" download="purchaseorderreport.xls" href="#" onclick="return ExcellentExport.excel(this, 'main_container', 'Purchase Order Report');"><span>Export</span></a>
					  		</div>
						</div>
						
						<div class="main-box">
                            <div id="main_container" class="main_container">
                            	<div class="report"><strong>REPORT PURCHASE ORDER</strong></div>
                            	<div><strong>&nbsp;</strong></div>
                                <div class="report">
                                    <table width="498">	
                                    <tr>	
                                        <td width="100">Company</td>
                                        <td width="11">:&nbsp;&nbsp;</td>
                                        <td width="344"><c:out value='${organization.fullName}'/></td>			  
                                    </tr>
                                    <tr>	
                                        <td width="100">Period</td>
                                        <td width="11">:&nbsp;&nbsp;</td>
                                        <td width="344"><fmt:formatDate value='${criteria.dateFrom}' pattern='dd MMM yyyy' /> &nbsp;-&nbsp; <fmt:formatDate value='${criteria.dateTo}' pattern='dd MMM yyyy' /></td>			  
                                    </tr>
                                     <tr>	
                                        <td width="100">Sort by</td>
                                        <td width="11">:&nbsp;&nbsp;</td>
                                        <td width="344">
                                        	<c:if test='${criteria.sortBy == "supplier.fullName"}'>
	                                       		Supplier
	                                    	</c:if>
	                                       	<c:if test='${criteria.sortBy == "date"}'>
	                                       		Date
	                                       	</c:if>
                                        </td>
                                    </tr>
                                    </table>
                                </div>
                                <div class="clears">&nbsp;</div>
                                <table width="100%"  cellpadding="5" cellspacing="0" class="report-table" border="0">
                                <thead>
                                <tr>
                                	<th width="5%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;">NO</th>
                                    <th width="8%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;">Date</th>
                                  	<th width="15%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;">Supplier Name</th>
                                  	<th width="20%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;">No PO/Line Item</th>
                                  	<th width="10%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;">Purchase Price</th>
                                  	<th width="5%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;">Qty</th>
                                  	<th width="7%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;">Amount</th>
                                  	<th width="7%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;">Delivery Date</th>
                                  	<th width="7%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;">Purchase</th>
                                  	<th width="7%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;">Tax</th>
                                  	<th width="9%" align="left" style="border-bottom:1px solid black;border-top:1px solid black;">Total Amount</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:set var="tQty" value="0"/>
                                <c:set var="tPurchase" value="0"/>
                                <c:set var="tTax" value="0"/>
                                <c:set var="tTotal" value="0"/>
                                <c:forEach items='${reports}' var='report' varStatus="status">
                                <c:set var="tPurchase" value="${tPurchase+report.purchase}"/>
                               	<c:set var="tTax" value="${tTax+report.tax}"/>
                                <c:set var="tTotal" value="${tTotal+report.total}"/>
                                    <tr>
                                        <td><c:out value='${status.index+1})'/></td>
                                        <td><fmt:formatDate value='${report.purchaseOrder.date}' pattern='dd-MM-yyyy'/></td>
                                        <td><c:out value='${report.purchaseOrder.supplier.fullName}'/></td>
                                      	<c:if test="${fn:contains(report.purchaseOrder.code, 'SPO')}">
                                        	<td><a href="<c:url value='/page/standardpurchaseorderpreedit.htm?id=${report.purchaseOrder.id}'/>"><c:out value='${report.purchaseOrder.code}'/></a></td>
                                        </c:if>
                                       	<c:if test="${fn:contains(report.purchaseOrder.code, 'DPO')}">
                                        	<td><a href="<c:url value='/page/directpurchaseorderpreedit.htm?id=${report.purchaseOrder.id}'/>"><c:out value='${report.purchaseOrder.code}'/></a></td>
                                        </c:if>
                                        <td colspan="4"></td>
                                        <td><fmt:formatNumber value='${report.purchase}' pattern=',##0.00'/></td>
                                        <td><fmt:formatNumber value='${report.tax}' pattern=',##0.00'/></td>
                                        <td><fmt:formatNumber value='${report.total}' pattern=',##0.00'/></td>
                                        <c:forEach items='${report.purchaseOrder.items}' var='item' varStatus="stat">
                                        	<c:if test="${item.purchaseItemType == 'BASE'}">
	                                        	<c:set var="tQty" value="${tQty + item.quantity}"/>
	                                            <tr>
	                                                <td colspan="3"></td>
	                                                <td><c:out value='(${stat.index+1}) ${item.product.name}'/></td>
	                                                <td><fmt:formatNumber value='${item.quantity*item.money.amount}' pattern=',##0.00'/></td>
	                                                <td><fmt:formatNumber value='${item.quantity}' pattern=',##0'/></td>
	                                                <td><fmt:formatNumber value='${item.money.amount}' pattern=',##0.00'/></td>
	                                                <td nowrap="nowrap"><fmt:formatDate value='${item.deliveryDate}' pattern='dd-MM-yyyy'/></td>
	                                                <td colspan="3"></td>
	                                            </tr>
	                                        </c:if>
                                        </c:forEach>
                                        <tr>
                                            <td colspan="11" style="border-top:1px solid black;">&nbsp;</td>
                                        </tr> 
                                    </tr>
                                </c:forEach>
                                    <tr>
                                        <td colspan="3" style="border-top:1px solid black;">&nbsp;</td>
                                        <td colspan="1" align="right" style="border-top:1px solid black;"><strong>Grand Total : </strong></td>
                                        <td style="border-top:1px solid black;"></td>
                                        <td style="border-top:1px solid black;"><strong><fmt:formatNumber value='${tQty}' pattern=',##0'/></strong></td>
                                        <td style="border-top:1px solid black;">&nbsp;</td>
                                        <td style="border-top:1px solid black;"><strong>&nbsp;</strong></td>
                                        <td style="border-top:1px solid black;"><strong><fmt:formatNumber value='${tPurchase}' pattern=',##0.00'/></strong></td>
                                        <td style="border-top:1px solid black;"><strong><fmt:formatNumber value='${tTax}' pattern=',##0.00'/></strong></td>
                                        <td style="border-top:1px solid black;"><strong><fmt:formatNumber value='${tTotal}' pattern=',##0.00'/></strong></td>
                                    </tr>
                                </tbody>
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
