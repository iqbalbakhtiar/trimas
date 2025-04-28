<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" +request.getServerPort()+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
	<title><spring:message code="salesorder.contract"/></title>
	<link rel="icon" type="image/png" href="<c:url value='/assets/images/title-logo.png'/>">
	<style type="text/css" media="screen">
		@import url("assets/sirius.css");
		@import url("assets/sirius-print.css");
	</style>
	<style type="text/css" media="print">
		@import url("assets/sirius-print.css");
	</style>
</head>
<body>
<div class="area" dojoType="Container" id="quick_link_container">
	<%@ include file="/common/sirius-header.jsp" %>
</div>
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
							<div class="clears">&nbsp;</div>
							<div class="toolbar">
								<a class="item-button-back" href="<c:url value="/page/purchaserequisitionpreedit.htm?id=${requisition_edit.id}"/>"><span><spring:message code="sirius.back"/></span></a>
								<a class="item-button-print" href="javascript: window.print();"><span><spring:message code="sirius.print"/></span></a>
						  	</div>
					  	</div>
					  	<div class="main-box">
					  		<table border="0" width="100%" align="center">
				  			<tr>
				  				<td colspan="4" align="center"><h2 style="text-transform: uppercase;"><spring:message code="purchaserequisition"/></h2></td>
				  			</tr>
				  			<tr>
				  				<td width="10%;">&nbsp;</td>
				  				<td width="60%;">&nbsp;</td>
				  				<td width="10%"><spring:message code="sirius.number"/></td>
				  				<td align="left">: ${requisition_edit.code}</td>
				  			</tr>
				  			<tr>
				  				<td><spring:message code="contactmechanism.department"/></td>
				  				<td align="left">: ${requisition_edit.department.name}</td>
				  				<td width="10%"><spring:message code="sirius.date"/></td>
				  				<td align="left">: <fmt:formatDate value='${requisition_edit.date}' pattern='dd-MM-yyyy'/></td>
				  			</tr>
					  		</table>
					  		<br/>
					  		<table border="0" cellpadding="2" cellspacing="1" width="100%" align="center" style="border-width: 1px medium medium; border-style: solid none none; border-color: black -moz-use-text-color -moz-use-text-color;">
					  			<tr style="height: 25px;">
					  				<th style="border-bottom:1px solid black;border-left:1px solid black;width: 1%;"><div style="width: 35px;"><spring:message code="sirius.number"/></div></th>
					  				<th style="border-bottom:1px solid black;border-left:1px solid black;width: 10%"><spring:message code="product.code"/></th>
					  				<th style="border-bottom:1px solid black;border-left:1px solid black;width: 30%"><spring:message code="product.name"/></th>
					  				<th style="border-bottom:1px solid black;border-left:1px solid black;width: 10%"><spring:message code="sirius.qty"/></th>
					  				<th style="border-bottom:1px solid black;border-left:1px solid black;width: 10%"><spring:message code="sirius.uom"/></th>
					  				<th style="border-bottom:1px solid black;border-right:1px solid black;border-left:1px solid black;width: 20%"><spring:message code="sirius.note2"/></th>
					  			</tr>
           						 <c:forEach items="${requisition_edit.items}" var="item" varStatus="idx">
					  			<tr style="height: 20px;">
					  				<td style="border-bottom:1px solid black;border-left:1px solid black;">${idx.index+1}</td>
					  				<td style="border-bottom:1px solid black;border-left:1px solid black;">${item.product.code}</td>
					  				<td style="border-bottom:1px solid black;border-left:1px solid black;">${item.product.name}</td>
					  				<td align="right" style="border-bottom:1px solid black;border-left:1px solid black;"><fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/></td>
					  				<td style="border-bottom:1px solid black;border-left:1px solid black;">${item.product.unitOfMeasure.measureId}</td>
					  				<td style="border-bottom:1px solid black;border-right:1px solid black;border-left:1px solid black;">${item.note}</td>
					  			</tr>
					  			</c:forEach>
					  		</table>
					  		<br/>
					  		<table border="0" cellpadding="0" cellspacing="2" width="100%">
				  			<tr>
				  				<td width="40%" valign="top"></td>
					  			<td width="60%" valign="top" align="right">
						  			<table border="0" cellpadding="0" cellspacing="0" width="100%" style="table-layout: fixed;border-width: 1px medium medium; border-style: solid none none; border-color: black -moz-use-text-color -moz-use-text-color;">
						  			<tr style="height: 25px;">
						  				<th width="25%" style="border-bottom:1px solid black;border-left:1px solid black;">Pembelian</th>
						  				<th width="25%" style="border-bottom:1px solid black;border-left:1px solid black;">Kasie. Dept</th>
						  				<th width="25%" style="border-bottom:1px solid black;border-left:1px solid black;">Kabag. Dept</th>
						  				<th width="25%" style="border-bottom:1px solid black;border-left:1px solid black;border-right: 1px solid black;">Gudang</th>
									</tr>
									<tr style="height: 80px;">
						  				<td style="border-bottom:1px solid black;border-left:1px solid black;"></td>
						  				<td style="border-bottom:1px solid black;border-left:1px solid black;"></td>
						  				<td style="border-bottom:1px solid black;border-left:1px solid black;"></td>
						  				<td style="border-bottom:1px solid black;border-left:1px solid black;border-right: 1px solid black;"></td>
									</tr>
									<tr style="height: 25px;">
						  				<td style="border-bottom:1px solid black;border-left:1px solid black;"></td>
						  				<td style="border-bottom:1px solid black;border-left:1px solid black;"></td>
						  				<td style="border-bottom:1px solid black;border-left:1px solid black;"></td>
						  				<td style="border-bottom:1px solid black;border-left:1px solid black;border-right: 1px solid black;"></td>
									</tr>
									</table>
					  			</td>
							</tr>
				  			<tr>
								<td colspan="5">&nbsp;</td>
							</tr>
					  		</table>
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
