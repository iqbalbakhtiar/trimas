<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-back" href="<c:url value='/page/salesorderpreedit.htm?id=${salesOrder_form.salesOrder.id}'/>"><span><spring:message code="sirius.back"/></span></a>
	<a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
	<a class="item-button-export-xls" download="salesorder.xls" href="#" onclick="return ExcellentExport.excel(this, 'size', 'Sales Order');"><span>Export</span></a>
</div>

<div class="main-box">

	<table border="0" width="100%" cellpadding="0" cellspacing="0" class="in">
		<tr>
			<td style="background: black" width="1%">&nbsp;</td>
			<td width="1%">&nbsp;</td>
			<td width="96%">&nbsp;</td>
			<td width="1%">&nbsp;</td>
			<td style="background: black" width="1%">&nbsp;</td>
		<tr>
	</table>
	<table border="0" width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td width="2%" colspan="2">&nbsp;</td>
			<td width="52%" colspan="3" class="CSS3" align="left">
				<strong><c:out value='${salesOrder_form.organization.salutation} ${salesOrder_form.organization.fullName}'/></strong>
			</td>
			<td width="44%" colspan="3" align="right" class="CSS3" valign="top"><strong>SALES ORDER</strong></td>
			<td width="2%" colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="10" height="15">&nbsp;</td>
		</tr>
	</table>
	<table border="0" width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td width="2%" colspan="2">&nbsp;</td>
			<td width="48%" colspan="3" valign="top">
				<table width="100%" cellpadding="0" cellspacing="0" align="left">
					<tr>
						<td width="15%" valign="top">Pelanggan</td>
						<td width="1%" valign="top">:</td>
						<td width="84%">
							<c:out value='${salesOrder_form.customer.fullName} ${salesOrder_form.customer.salutation}'/><br/>
							<c:out value="${salesOrder_form.shippingAddress.addressName}"/><br/>
							<c:out value="${salesOrder_form.shippingAddress.address}"/><br/>
							<c:out value="${salesOrder_form.shippingAddress.city.name}"/> - <c:out value="${salesOrder_form.shippingAddress.postalCode}"/><br/>
						</td>
					</tr>
				</table>
			</td>
			<td width="48%" colspan="3" valign="top">
				<table width="100%" cellpadding="0" cellspacing="0" align="left">
					<tr>
						<td width="40%">&nbsp;</td>
						<td width="20%">Sales Order No</td>
						<td width="1%">:</td>
						<td width="30%"><c:out value='${salesOrder_form.code}'/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>Tanggal Order</td>
						<td>:</td>
						<td><fmt:formatDate value='${salesOrder_form.date}' pattern='dd - MM - yyyy'/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>Note</td>
						<td>:</td>
						<td><c:out value='${salesOrder_form.note}'/></td>
					</tr>
				</table>
			</td>
			<td width="2%" colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="10">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td colspan="6">
				<table width="100%"  border="0" cellpadding="0" cellspacing="0">
					<thead>
                        <tr>
                            <th align="center" class="border-top border-bottom">No.</th>
                            <th align="center" class="border-top border-bottom">Nama Barang</th>
                            <th align="right" class="border-top border-bottom">Jumlah</th>
                            <th align="center" class="border-top border-bottom">Satuan</th>
                            <th align="right" class="border-top border-bottom">Harga Satuan</th>
                            <th align="right" class="border-top border-bottom">Diskon(%)</th>
                            <th align="right" class="border-top border-bottom">Harga Nett</th>
                            <th align="right" class="border-top border-bottom">Total</th>
                        </tr>
					</thead>
					<tbody>
					<c:forEach items='${adapter.items}' var='item' varStatus='status'>
						<tr>
							<td align="center" width="3%" align="center"><c:out value="${status.index+1}"/></td>
							<td align="left" width="30%" nowrap="nowrap">&nbsp;${item.product.name}</td>
							<td align="right" width="5%" style="padding-right:5px;"><fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/></td>
							<td align="center" width="5%">${item.product.unitOfMeasure.measureId}</td>
							<td align="right" width="12%" align="right" style="padding-right:5px;"><fmt:formatNumber value='${item.amount}' pattern=',##0.00'/></td>
							<td align="right" width="8%" align="right" style="padding-right:5px;"><fmt:formatNumber value='${item.discount}' pattern=',##0.00'/></td>
							<td width="15%" align="right" style="padding-right:5px;"><fmt:formatNumber value='${item.priceNett}' pattern=',##0.00'/></td>
							<td width="15%" align="right" style="padding-right:5px;"><fmt:formatNumber value='${item.subTotal}' pattern=',##0.00'/></td>
						</tr>
					</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<td class="border-top" colspan="7" align="right"><strong>Total :</strong></td>
							<td class="border-top" align="right" style="padding-right:5px;"><fmt:formatNumber value='${adapter.totalAmount}' pattern=',##0.00'/></td>
						</tr>
					</tfoot>
				</table>
			</td>
			<td colspan="2">&nbsp;</td>
		</tr>
	</table>

	<br>
	<table border="0" width="100%" cellpadding="0" cellspacing="0" class="out">
		<tr>
			<td style="background: black" width="1%">&nbsp;</td>
			<td width="1%">&nbsp;</td>
			<td width="96%">&nbsp;</td>
			<td width="1%">&nbsp;</td>
			<td style="background: black" width="1%">&nbsp;</td>
		</tr>
	</table>
	<%@include file="salesOrderExcel.jsp"%>
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>