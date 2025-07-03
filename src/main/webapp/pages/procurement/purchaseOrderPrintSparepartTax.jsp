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
					  	<div class="main-box" style="overflow-x: hidden;">
					  		<table border="0" width="100%" align="center">
					  			<tr>
					  				<td style="width: 70%">
					  				<img src="assets/images/ssm-logo.png"  width="250" height="40"/>
					  				</br><font size="2"></font>
					  				</td>
					  				<td style="width: 30%" valign="top">
					  				</td>
					  			</tr>
					  			<tr>
					  				<td style="text-transform: uppercase;">
					  					<c:forEach items='${purchase_edit.organization.postalAddresses}' var='address'>
										<c:if test='${address.selected}'>
											${address.address}</br>
											SUMEDANG - JAWA BARAT</br>
											Tlp. (022) 7798320</br>
											Fax. (022) 7790185</br>
										</c:if>
									    </c:forEach>
					  				</td>
					  				<td>&nbsp;</td>
					  			</tr>
					  		</table>
					  		</br>
					  		<table border="0" width="100%" align="center">
				  			<tr>
				  				<td style="width: 2%;">No PO</td>
				  				<td style="width: 5px;" align="center">:</td>
				  				<td align="left" style="width: 40%;">${purchase_edit.code}</td>
				  				<td align="right" colspan="2"><spring:message code="sirius.date"/> : <fmt:formatDate value='${purchase_edit.date}' pattern='dd MMMM yyyy'/></td>
				  			</tr>
				  			<tr>
				  				<td style="width: 1%;">Kepada</td>
				  				<td style="width: 5px;" align="center">:</td>
				  				<td align="left" style="width: 40%;">${purchase_edit.supplier.fullName}</td>
			  				</tr>
				  			<tr>
				  				<td style="width: 1%;">Attn</td>
				  				<td style="width: 5px;" align="center">:</td>
				  				<td align="left" style="width: 40%;">${purchase_edit.contactMechanism.contactName}</td>
			  				</tr>
				  			<tr>
				  				<td style="width: 1%;" valign="top">Alamat</td>
				  				<td style="width: 5px;" align="center">:</td>
				  				<td align="left" colspan="2" class="word-wrap">
				  					<c:forEach items='${purchase_edit.supplier.postalAddresses}' var='address'>
										<c:if test='${address.selected}'>
											${address.address}</br>
										</c:if>
								    </c:forEach>
				  				</td>
			  				</tr>
				  			<c:set var="phone" value="${purchase_edit.contactMechanism.contact}"/>
				  			<c:set var="fax" value=""/>
				  			<c:forEach items='${purchase_edit.supplier.contactMechanisms}' var='cont'>
				  			<c:if test="${cont.active}">
								<c:if test='${cont.contactMechanismType eq "FAX"}'>
						  			<c:set var="fax" value="${cont.contact}"/>
								</c:if>
							</c:if>
						    </c:forEach>
				  			<tr>
				  				<td><spring:message code="contactmechanism.phone"/></td>
				  				<td style="width: 5px;" align="center">:</td>
				  				<td align="left">${phone}</td>
				  				<td align="left"></td>
				  			</tr>
				  			<tr>
				  				<td><spring:message code="contactmechanism.fax"/></td>
				  				<td style="width: 5px;" align="center">:</td>
				  				<td align="left">${fax}</td>
				  				<td align="left"></td>
				  			</tr>
				  			<tr>
				  				<td colspan="4" align="center" style="text-transform: uppercase;"><h2><spring:message code="purchaseorder"/></h2></td>
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
					  				<th style="border-bottom:1px solid black;border-left:1px solid black;width: 20%"><spring:message code="purchaserequisition.discount"/></th>
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
					  			<tr style="font-weight: bold;">
					  				<td align="right" colspan="6">&nbsp;</td>
					  				<td align="right"><spring:message code="purchaseorderitem.subtotal"/></td>
					  				<td align="right" style="border-bottom:1px solid black;border-right:1px solid black;border-left:1px solid black;"><fmt:formatNumber value='${adapter.totalItemAmount-adapter.totalDiscount}' pattern='${pattern}'/></td>
					  			</tr>
					  			<c:if test="${adapter.totalDiscount gt 0}">
						  			<tr style="font-weight: bold;display: none;">
						  				<td align="right" colspan="6">&nbsp;</td>
						  				<td align="right"><spring:message code="purchaseorder.discount"/></td>
						  				<td align="right" style="border-bottom:1px solid black;border-right:1px solid black;border-left:1px solid black;"><fmt:formatNumber value='${adapter.totalDiscount}' pattern='${pattern}'/></td>
						  			</tr>
						  			<tr style="font-weight: bold;display: none;">
						  				<td align="right" colspan="6">&nbsp;</td>
						  				<td align="right"><spring:message code="purchaseorder.afterdiscount"/></td>
						  				<td align="right" style="border-bottom:1px solid black;border-right:1px solid black;border-left:1px solid black;"><fmt:formatNumber value='${adapter.totalAfterDiscount}' pattern='${pattern}'/></td>
						  			</tr>
					  			</c:if>
					  			<tr style="font-weight: bold;">
					  				<td align="right" colspan="6">&nbsp;</td>
					  				<td align="right"><spring:message code="salesorder.tax.amount"/></td>
					  				<td align="right" style="border-bottom:1px solid black;border-right:1px solid black;border-left:1px solid black;"><fmt:formatNumber value='${adapter.taxAmount}' pattern='${pattern}'/></td>
					  			</tr>
					  			<tr style="font-weight: bold;">
					  				<td align="right" colspan="6">&nbsp;</td>
					  				<td align="right"><spring:message code="purchaseorderitem.total"/></td>
					  				<td align="right" style="border-bottom:1px solid black;border-right:1px solid black;border-left:1px solid black;"><fmt:formatNumber value='${adapter.totalTransaction}' pattern='${pattern}'/></td>
					  			</tr>
					  		</table>
					  		<br/>
					  		<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
				  			<tr>	
				  				<td align="left" colspan="4"><strong><spring:message code="sirius.note"/> : ${purchase_edit.note}</strong>
				  					<ul>
				  						<li>Barang yang dikirim sesuai dengan pesanan.</li>
				  						<li>Barang yang tidak dapat digunakan, dapat diretur.</li>
				  						<li>Nomor PO jangan dicantumkan di Surat Jalan / Faktur.</li>
				  						<li>Kontra Bon dilakukan berurutan sesuai dengan tanggal pengiriman barang.</li>
				  						<li>Di dalam Invoice / Penagihan dicantumkan alamat transfer untuk pembayaran.</li>
				  					</ul>
				  				</td>
				  			</tr>
				  			<tr>
				  				<td width="25%" align="center" valign="top"></td>
					  			<td width="25%" align="center" valign="top" style="border-top:1px solid black;border-bottom:1px solid black;border-right:1px solid black;border-left:1px solid black;">
					  				<table border="0" cellpadding="0" cellspacing="0" width="100%">
				  					<tr>
				  						<td align="center" colspan="3" style="border-bottom:1px solid black;height: 25px;"><spring:message code="salesorder.contract.seller"/></td>
				  					</tr>
				  					<tr colspan="3" style="height: 100px">
				  						<td align="center" colspan="3" nowrap="nowrap">&nbsp;</td>
				  					</tr>
				  					<tr style="height: 20px;">
				  						<td align="center" colspan="3" nowrap="nowrap" style="text-transform: uppercase;">${purchase_edit.supplier.salutation} ${purchase_edit.supplier.fullName}</td>
				  					</tr>
									</table>
								</td>
					  			<td width="25%" align="center" valign="top" style="border-top:1px solid black;border-bottom:1px solid black;border-right:1px solid black;;">
					  				<table border="0" cellpadding="0" cellspacing="0" width="100%">
				  					<tr>
				  						<td align="center" colspan="3" style="border-bottom:1px solid black;height: 25px;"><spring:message code="salesorder.contract.buyer"/></td>
				  					</tr>
				  					<tr colspan="3" style="height: 100px">
				  						<td align="center" colspan="3" nowrap="nowrap">&nbsp;</td>
				  					</tr>
				  					<tr style="height: 20px;">
				  						<td align="center" colspan="3" nowrap="nowrap" style="text-transform: uppercase;">${purchase_edit.organization.salutation} ${purchase_edit.organization.fullName}</td>
				  					</tr>
									</table>
								</td>
				  				<td width="25%" align="center" valign="top"></td>
							</tr>
				  			<tr>
								<td colspan="4">&nbsp;</td>
							</tr>
							</table>
					  		<br/>
							<table border="0" cellpadding="1" cellspacing="2" width="100%" align="center" style="overflow: hidden;table-layout: fixed;">
				  			<tr>
				  				<td nowrap="nowrap" style="width: 25%;">Batas Tgl Penerimaan Barang</td>
				  				<td colspan="3" nowrap="nowrap">: ${purchase_edit.deliveryTerm}</td>
				  			</tr>
				  			<tr>
				  				<td nowrap="nowrap">Term of Payment</td>
				  				<td colspan="3" nowrap="nowrap">: ${purchase_edit.paymentTerm}</td>
				  			</tr>
				  			<tr style="overflow: hidden;">
				  				<td colspan="5" nowrap="nowrap">
					  				Setelah ditandatangani dan dicap perusahaan mohon difax ke <strong>022 7790185</strong> atau email ke <strong>ngati.ssm@gmail.com</strong>
				  				</td>
				  			</tr>
				  			<tr style="overflow: hidden;">
				  				<td colspan="5" nowrap="nowrap" align="right"><fmt:formatDate value="${now}" pattern="dd MMMM yyyy"/> &nbsp;<span id="printTime"></span></td>
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
