<%@ include file="/common/sirius-general-top.jsp"%>

<style>
	tr.data-row td {
		padding: 1rem 0.25rem 0;
	}
	tr.total-row td {
		padding-bottom: 1rem;
	}
</style>
<style type="text/css" media="screen">
	@import url("<c:url value='/assets/sirius.css'/>");
	@import url("<c:url value='/assets/sirius-print-a4.css'/>");
</style>
<style type="text/css" media="print">
	@import url("<c:url value='/assets/sirius-print-a4.css'/>");
</style>

<div class="toolbar">
	<c:if test="${empty redirect}">
		<a class="item-button-back" href="<c:url value='/page/${billing_edit.billing.billingType.url}?id=${billing_form.billing.id}'/>"><span><spring:message code="sirius.back"/></span></a>
	</c:if>
	<c:if test="${not empty redirect}">
		<a class="item-button-back" href="<c:url value='/page/${redirect}?id=${redirectId}'/>"><span><spring:message code="sirius.back"/></span></a>
	</c:if>
	<a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
	<a class="item-button-export-xls" download="invoice.xls" href="#" onclick="return ExcellentExport.excel(this, 'size', 'Invoice');"><span>Export</span></a>
</div>

<div class="main-box">
	<div class="main_container">
		<center>
			<table border="0" width="100%" cellpadding="0" cellspacing="0" class="in">
			<tr>
				<td style="background: black" width="1%">&nbsp;</td>
				<td width="1%">&nbsp;</td>
				<td width="96%">&nbsp;</td>
				<td width="1%">&nbsp;</td>
				<td style="background: black" width="1%">&nbsp;</td>
			<tr>
			</table>
			<table border="0" width="100%" cellpadding="0" cellspacing="0" id="test">
			<tr>
				<td width="1%" colspan="2">&nbsp;</td>
				<td width="48%" colspan="3" valign="top">
	  				<img style="margin-left: -15px;" src="assets/images/shunhui-logo.png" width="350" height="40"/>
	  				</br>
	  				<font>
  					<c:forEach items='${billing_form.organization.postalAddresses}' var='address'>
					<c:if test='${address.selected}'>
						${address.address}</br>
						${address.city.name}, ${address.city.parent.name}</br>
					</c:if>
				    </c:forEach>
	  				</font>
				</td>
				<td width="48%" colspan="3" valign="top">
					<table width="100%" cellpadding="0" cellspacing="0" align="left">
						<tr>
							<td>&nbsp;</td>
							<td colspan="3"><strong>INVOICE</strong></td>
						</tr>
						<tr>
							<td width="20%">&nbsp;</td>
							<td width="25%">Invoice No</td>
							<td width="1%">:</td>
							<td width="30%"><c:out value='${billing_form.code}'/></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>Tanggal Invoice</td>
							<td>:</td>
							<td><fmt:formatDate value='${billing_form.date}' pattern='dd - MM - yyyy'/></td>
						</tr>
					</table>
				</td>
				<td width="2%" colspan="2">&nbsp;</td>
			</tr>
			<tr><td colspan="10">&nbsp;</td></tr>
			<tr>
				<td colspan="2">&nbsp;</td>
				<td width="10%">Nama</td>
				<td width="90%" colspan="7">: <c:out value='${billing_form.customer.salutation} ${billing_form.customer.fullName}'/></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
				<td>Alamat</td>
				<td width="90%" colspan="7">:
					<c:out value="${billing_form.shippingAddress.address}"/>
					<c:out value="${billing_form.shippingAddress.city.name}"/>
					<c:out value="${billing_form.shippingAddress.province.name}"/>
					<c:out value="${billing_form.shippingAddress.postalCode}"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
				<td width="10%">No. Tlp</td>
				<td width="90%" colspan="7">:
		  			<c:set var="phone" value=""/>
		  			<c:set var="fax" value=""/>
		  			<c:forEach items='${salesOrder_edit.customer.contactMechanisms}' var='cont'>
		  			<c:if test="${cont.active}">
						<c:if test='${cont.contactMechanismType eq "PHONE"}'>
				  			<c:set var="phone" value="${cont.contact}"/>
						</c:if>
						<c:if test='${cont.contactMechanismType eq "FAX"}'>
				  			<c:set var="fax" value="${cont.contact}"/>
						</c:if>
					</c:if>
				    </c:forEach>
				    <c:out value="${phone}"/>
				</td>
			</tr>
			</table>
			<br/>
			<br/>
			<table border="0" width="100%" cellpadding="2" cellspacing="0">
			<tr style="height: 30px;">
				<td colspan="2">&nbsp;</td>
				<td class="border-top border-left border-bottom" colspan="2" align="center" width="46%">Nama Barang</td>
				<td class="border-top border-left border-bottom" align="center" width="15%">Jumlah</td>
				<td class="border-top border-left border-bottom" align="center" width="15%">Harga Satuan</td>
				<td class="border-top border-left border-right border-bottom" colspan="2" align="center" width="30%">Total</td>
				<td colspan="2">&nbsp;</td>
			</tr>
			<c:forEach items='${billing_form.billing.items}' var='item' varStatus='status'>
			<tr>
				<td colspan="2">&nbsp;</td>
				<td class="border-left border-bottom" colspan="2">${item.billingReferenceItem.product.name}</td>
				<td class="border-left border-bottom" align="right"><fmt:formatNumber value='${item.billingReferenceItem.quantity}' pattern=',##0.00'/></td>
				<td class="border-left border-bottom" align="right"><fmt:formatNumber value='${item.billingReferenceItem.money.amount}' pattern=',##0.00'/></td>
				<td class="border-left border-bottom border-right" colspan="2" align="right"><fmt:formatNumber value='${item.billingReferenceItem.subtotal}' pattern=',##0.00'/></td>
				<td colspan="2">&nbsp;</td>
			</tr>
			</c:forEach>
			<tr style="height: 1px;"><td>&nbsp;</td></tr>
			<tr>
				<td colspan="2">&nbsp;</td>
				<td colspan="2">Untuk Pembayaran Transfer Melalui Rekening :</td>
				<td>&nbsp;</td>
				<td>Subtotal</td>
				<td>: Rp.</td>
				<td align="right"><fmt:formatNumber value='${billing_edit.totalLineAmount}' pattern=',##0.00'/></td>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
				<td colspan="2">BANK CENTRAL ASIA (BCA)</td>
				<td>&nbsp;</td>
				<td>Total Diskon</td>
				<td>: Rp.</td>
				<td align="right">-</td>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
				<td colspan="2">SHUN HUI ZHIYE PT</td>
				<td>&nbsp;</td>
				<td>PPN (<fmt:formatNumber value='${billing_form.tax.taxRate}' pattern=',##0'/>%)</td>
				<td>: Rp.</td>
				<td align="right"><fmt:formatNumber value='${billing_edit.taxAmount}' pattern=',##0.00'/></td>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
				<td colspan="2"><strong>7753788788</strong></td>
				<td>&nbsp;</td>
				<td>Total</td>
				<td>: Rp.</td>
				<td align="right"><fmt:formatNumber value='${billing_edit.totalBillingAmount}' pattern=',##0.00'/></td>
				<td colspan="2">&nbsp;</td>
			</tr>
			</table>
			<table border="0" width="100%" cellpadding="2" cellspacing="0" id="test">
			<tr>
				<td width="2%" colspan="2">&nbsp;</td>
				<td width="70%" colspan="4" valign="top">&nbsp;</td>
				<td width="48%" colspan="3" valign="top" align="center">
					<table width="100%" cellpadding="2" cellspacing="0" align="left">
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td align="center">
							Cimahi, <fmt:formatDate value='${billing_form.date}' pattern='dd MMMM yyyy'/>
						</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td align="center">.............................................</td></tr>
					<tr>
						<td align="center"><u>PT SHUN HUI ZHIYE INDONESIA</u></td>
					</tr>
					<!-- <tr>
						<td align="center">Direktur</td>
					</tr> -->
					</table>
				</td>
				<td width="2%" colspan="2">&nbsp;</td>
			</tr>
			</table>
			<table border="0" width="100%" cellpadding="0" cellspacing="0" class="out">
				<tr>
					<td style="background: black" width="1%">&nbsp;</td>
					<td width="1%">&nbsp;</td>
					<td width="96%">&nbsp;</td>
					<td width="1%">&nbsp;</td>
					<td style="background: black" width="1%">&nbsp;</td>
				</tr>
			</table>
		</center>
	</div>
	<%@include file="billingExcel.jsp"%>
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>