<table border="0" width="100%" cellpadding="0" cellspacing="0" id="size" style="display: none">
	<tr>
		<td>
			<strong><c:out value='${billing_form.organization.salutation} ${billing_form.organization.fullName}'/></strong>
		</td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td align="right">
			<strong>INVOICE</strong>
		</td>
	</tr>
	<tr>
		<td colspan="10">&nbsp;</td>
	</tr>
	<tr>
		<td>Pelanggan&nbsp;:</td>
		<td colspan="2">
			<c:out value='${billing_form.customer.salutation} ${billing_form.customer.fullName}'/>
		</td>
		<td></td>
		<td></td>
		<td></td>
		<td colspan="2">Invoice No</td>
		<td colspan="2">:&nbsp;<c:out value='${billing_form.code}'/></td>
	</tr>
	<tr>
		<td></td>
		<td colspan="2">
			<c:out value="${billing_form.shippingAddress.addressName}"/>
		</td>
		<td></td>
		<td></td>
		<td></td>
		<td colspan="2">Tanggal Invoice</td>
		<td colspan="2">:&nbsp;<fmt:formatDate value='${billing_form.date}' pattern='dd - MM - yyyy'/></td>
	</tr>
	<tr>
		<td></td>
		<td colspan="2">
			<c:out value="${billing_form.shippingAddress.address}"/>
		</td>
		<td></td>
		<td></td>
		<td></td>
		<td colspan="2">Tanggal Jatuh Tempo</td>
		<td colspan="2">:&nbsp;<fmt:formatDate value='${billing_form.dueDate}' pattern='dd - MM - yyyy'/></td>
	</tr>
	<tr>
		<td></td>
		<td colspan="2">
			<c:out value="${billing_form.shippingAddress.city.name}"/> - <c:out value="${billing_form.shippingAddress.postalCode}"/>
		</td>
		<td></td>
		<td></td>
		<td></td>
		<td colspan="2">Delivery Order No</td>
		<td colspan="2">:&nbsp;<c:out value='${doCode}'/></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td colspan="2">Note</td>
		<td colspan="2" rowspan="2" valign="top">:&nbsp;<c:out value='${billing_form.note}'/></td>
	</tr>
	<tr>
		<td colspan="8">&nbsp;</td>
	</tr>
	<tr>
		<td align="center"><strong>No.</strong></td>
		<td colspan="3"><strong>Nama Barang</strong></td>
		<td><strong>Jumlah</strong></td>
		<td><strong>Satuan</strong></td>
		<td><strong>Harga Satuan</strong></td>
		<td><strong>Diskon(%)</strong></td>
		<td><strong>Harga Nett</strong></td>
		<td><strong>Total</strong></td>
	</tr>
	<c:forEach items='${billing_form.billing.items}' var='item' varStatus='status'>
		<tr>
			<td align="center" width="3%" align="center"><c:out value="${status.index+1}"/></td>
			<td colspan="3" align="left" width="30%" nowrap="nowrap">&nbsp;${item.billingReferenceItem.product.name}</td>
			<td align="right" width="5%" style="padding-right:5px;"><fmt:formatNumber value='${item.billingReferenceItem.quantity}' pattern=',##0.00'/></td>
			<td align="center" width="5%">${item.billingReferenceItem.product.unitOfMeasure.measureId}</td>
			<td align="right" width="12%" align="right" style="padding-right:5px;"><fmt:formatNumber value='${item.billingReferenceItem.money.amount}' pattern=',##0.00'/></td>
			<td align="right" width="8%" align="right" style="padding-right:5px;"><fmt:formatNumber value='${item.billingReferenceItem.discount}' pattern=',##0.00'/></td>
			<td width="15%" align="right" style="padding-right:5px;"><fmt:formatNumber value='${item.billingReferenceItem.discountedPricePerItem}' pattern=',##0.00'/></td>
			<td width="15%" align="right" style="padding-right:5px;"><fmt:formatNumber value='${item.billingReferenceItem.totalAmountPerItemDiscounted}' pattern=',##0.00'/></td>
		</tr>
	</c:forEach>
	<tr>
		<td colspan="9" align="right"><strong>Total :</strong></td>
		<td align="right" style="padding-right:5px;"><strong><fmt:formatNumber value='${billing_edit.totalLineItemAmountForPrint}' pattern=',##0.00'/></strong></td>
	</tr>
	<tr>
		<td colspan="10">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="5">- Barang belum lunas adalah barang titipan</td>
	</tr>
	<tr>
		<td colspan="5">- Tidak menerima pembayaran Cash dan Giro</td>
	</tr>
	<tr>
		<td colspan="5">- Transfer dan BG Giro melalui rekening di Invoice</td>
		<td></td>
		<td>Pengirim:</td>
		<td></td>
		<td colspan="2">Penerima:</td>
	</tr>
	<tr>
		<td colspan="5">- Transfer dan BG Giro dianggap lunas apabila sudah dicairkan ke Bank</td>
		<td></td>
		<td></td>
		<td></td>
		<td colspan="2">Tanggal Terima:</td>
	</tr>
	<tr>
		<td colspan="5">- Komplain hanya dilayani di tanggal Invoice</td>
	</tr>
	<tr>
		<td colspan="5">- Dengan segala hormat kami tidak melayani retur yang melewati</td>
	</tr>
	<tr>
		<td colspan="5">&nbsp;&nbsp;batas hari retur</td>
	</tr>
	<tr>
		<td colspan="5">- Nomor rekening:</td>
		<td></td>
		<td>(&nbsp;<c:out value='${billing_form.organization.salutation} ${billing_form.organization.fullName}'/>&nbsp;</td>
		<td>)</td>
		<td>(</td>
		<td>)</td>
	</tr>
	<tr>
		<td colspan="5">
			&nbsp;&nbsp;${bankAccount.bankName}: ${bankAccount.accountNo}
		</td>
	</tr>
	<tr>
		<td colspan="5">
			&nbsp;&nbsp;A/N ${bankAccount.holder.fullName}
		</td>
	</tr>
</table>