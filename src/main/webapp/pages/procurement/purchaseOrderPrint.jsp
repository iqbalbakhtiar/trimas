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
	<title><spring:message code="purchaseorder"/></title>
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
								<a class="item-button-back" href="<c:url value="/page/purchaseorderpreedit.htm?id=${purchase_edit.id}"/>"><span><spring:message code="sirius.back"/></span></a>
								<a class="item-button-print" href="javascript: window.print();"><span><spring:message code="sirius.print"/></span></a>
						  	</div>
					  	</div>
					  	<div class="main-box">
					  		<table border="0" width="100%" align="center">
				  			<tr>
				  				<td colspan="2" align="center" style="text-transform: uppercase;"><h2><spring:message code="purchaseorder"/></h2></td>
				  			</tr>
				  			<tr valign="top">
				  				<td align="left" style="text-transform: uppercase;width: 60%;" class="word-wrap">
				  					<strong>Vendor</strong></br>
				  					<c:out value='${purchase_edit.supplier.salutation}'/> <c:out value='${purchase_edit.supplier.fullName}'/>
				  					</br>
				  					<c:forEach items='${purchase_edit.supplier.postalAddresses}' var='address'>
									<c:if test='${address.selected}'>
										${address.address}</br>
										${address.city.name}
									</c:if>
								    </c:forEach>
				  				</td>
				  				<td align="left" style="text-transform: uppercase;width: 40%;" class="word-wrap">
				  					<strong><spring:message code="purchaseorder.shipto"/></strong></br>
				  					<c:out value='${purchase_edit.organization.salutation}'/> <c:out value='${purchase_edit.organization.fullName}'/>
				  					</br>
				  					<c:forEach items='${purchase_edit.organization.postalAddresses}' var='address'>
									<c:if test='${address.selected}'>
										${address.address}</br>
										SUMEDANG - JAWA BARAT
									</c:if>
								    </c:forEach>
				  				</td>
				  			</tr>
					  		</table>
					  		<br/>
					  		<table border="0" cellpadding="5" cellspacing="0" width="100%" align="center" style="border-width: 1px medium medium; border-style: solid none none; border-color: black -moz-use-text-color -moz-use-text-color;">
					  			<tr>
					  				<th rowspan="2" style="border-bottom:1px solid black;border-left:1px solid black;width: 1%;"><spring:message code="sirius.number"/></th>
					  				<th rowspan="2" style="border-bottom:1px solid black;border-left:1px solid black;width: 20%"><spring:message code="product"/></th>
					  				<th rowspan="2" style="border-bottom:1px solid black;border-left:1px solid black;width: 10%"><spring:message code="sirius.qty"/></th>
					  				<th rowspan="2" style="border-bottom:1px solid black;border-left:1px solid black;width: 10%"><spring:message code="sirius.uom"/></th>
					  				<th colspan="4" style="border-bottom:1px solid black;border-right:1px solid black;border-left:1px solid black;width: 40%"><spring:message code="sirius.amount"/></th>
					  			</tr>
					  			<tr>
					  				<th style="border-bottom:1px solid black;border-left:1px solid black;width: 20%"><spring:message code="sirius.unitprice"/></th>
					  				<th style="border-bottom:1px solid black;border-left:1px solid black;width: 5%"><spring:message code="purchaseorderitem.discount"/></th>
					  				<th style="border-bottom:1px solid black;border-left:1px solid black;width: 15%"><spring:message code="purchaserequisition.discount"/></th>
					  				<th style="border-bottom:1px solid black;border-right:1px solid black;border-left:1px solid black;width: 20%"><spring:message code="sirius.total"/></th>
					  			</tr>
					            <c:forEach items="${purchase_edit.items}" var="item" varStatus="stat">
					            <c:if test="${item.purchaseItemType eq 'BASE'}">
					  			<tr>
					  				<td style="border-bottom:1px solid black;border-left:1px solid black;">${stat.index+1}</td>
					  				<td style="border-bottom:1px solid black;border-left:1px solid black;">${item.product.name}</td>
					  				<td align="right" style="border-bottom:1px solid black;border-left:1px solid black;"><fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/></td>
					  				<td align="center" style="border-bottom:1px solid black;border-left:1px solid black;">${item.product.unitOfMeasure.measureId}</td>
					  				<td align="right" style="border-bottom:1px solid black;border-left:1px solid black;"><fmt:formatNumber value='${item.money.amount}' pattern='${pattern}'/></td>
					  				<td align="right" style="border-bottom:1px solid black;border-left:1px solid black;"><fmt:formatNumber value='${item.discountPercent}' pattern='${pattern}'/></td>
					  				<td align="right" style="border-bottom:1px solid black;border-left:1px solid black;"><fmt:formatNumber value='${item.discount}' pattern='${pattern}'/></td>
					  				<td align="right" style="border-bottom:1px solid black;border-right:1px solid black;border-left:1px solid black;"><fmt:formatNumber value='${item.totalAmount-item.discount}' pattern='${pattern}'/></td>
					  			</tr>
					  			</c:if>
					  			</c:forEach>
					  		</table>
					  		<br/>
					  		<table border="0" cellpadding="1" cellspacing="2" width="100%" align="center" style="table-layout: fixed;">
				  			<tr>
				  				<td width="20%" align="center" valign="top"></td>
					  			<td width="20%" align="center" valign="top">
					  				<table border="0" cellpadding="0" cellspacing="0" width="100%">
				  					<tr>
				  						<td align="center" colspan="3"><spring:message code="purchaseorder.approvedby"/></td>
				  					</tr>
				  					<tr colspan="3" style="height: 100px">
				  						<td align="center" colspan="3">&nbsp;</td>
				  					</tr>
									<tr style="text-transform: uppercase;font-weight: bold;">
										<td style="width: 1px;">(</td>
										<td align="center">${purchase_edit.supplier.salutation}&nbsp;${purchase_edit.supplier.fullName}</td>
										<td style="width: 1px;">)</td>
									</tr>
									</table>
								</td>
				  				<td width="20%" align="center" valign="top"></td>
					  			<td width="20%" align="center" valign="top">
					  				<table border="0" cellpadding="0" cellspacing="0" width="100%">
				  					<tr>
				  						<td align="center" colspan="3"><spring:message code="purchaseorder.approvedby"/></td>
				  					</tr>
				  					<tr colspan="3" style="height: 100px">
				  						<td align="center" colspan="3">&nbsp;</td>
				  					</tr>
									<tr style="text-transform: uppercase;font-weight: bold;">
										<td style="width: 1px;">(</td>
										<td align="center">&nbsp;${purchase_edit.organization.salutation}&nbsp;${purchase_edit.organization.fullName}&nbsp;</td>
										<td style="width: 1px;">)</td>
									</tr>
									</table>
								</td>
				  				<td width=" 20%" align="center" valign="top"></td>
							</tr>
				  			<tr>
								<td colspan="5">&nbsp;</td>
							</tr>
				  			<tr>
								<td colspan="5"><spring:message code="sirius.note"/> : ${purchase_edit.note}</td>
							</tr>
				  			<tr style="overflow: hidden;">
				  				<td colspan="5" nowrap="nowrap">
					  				PO yang sudah di tandatangani dan di cap, mohon dikirim ulang ke email : ngati.ssm@gmail.com
				  				</td>
				  			</tr>
				  			<tr>
				  				<td colspan="5" align="right"><fmt:formatDate value="${now}" pattern="dd MMMM yyyy"/> &nbsp;<span id="printTime"></span></td>
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
