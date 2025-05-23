<%@ include file="/common/sirius-general-top.jsp"%>

<style>
	tr.data-row td {
		padding: 1rem 0.25rem 0;
	}
	tr.total-row td {
		padding-bottom: 1rem;
	}
</style>

<div class="toolbar">
	<a class="item-button-back" href="<c:url value='/page/${billing_edit.billing.billingType.url}?id=${billing_form.billing.id}'/>"><span><spring:message code="sirius.back"/></span></a>
	<a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
	<a class="item-button-export-xls" download="invoice.xls" href="#" onclick="return ExcellentExport.excel(this, 'size', 'Invoice');"><span>Export</span></a>
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
			<td width="52%" colspan="3" class="CSS3" align="left">&nbsp;</td>
			<td width="44%" colspan="3" align="right" class="CSS3" valign="top"><strong>INVOICE</strong></td>
			<td width="2%" colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="10" height="15">&nbsp;</td>
		</tr>
	</table>
	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="test">
		<tr>
			<td width="2%" colspan="2">&nbsp;</td>
			<td width="48%" colspan="3" valign="top">
				&nbsp;
			</td>
			<td width="48%" colspan="3" valign="top">
				<table width="100%" cellpadding="0" cellspacing="0" align="left">
					<tr>
						<td width="25%">&nbsp;</td>
						<td width="20%">Invoice No</td>
						<td width="1%">:</td>
						<td width="30%"><c:out value='${billing_form.code}'/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>Tanggal Invoice</td>
						<td>:</td>
						<td><fmt:formatDate value='${billing_form.date}' pattern='dd - MM - yyyy'/></td>
					</tr>
<%--					<tr>--%>
<%--						<td>&nbsp;</td>--%>
<%--						<td>Tanggal Jatuh Tempo</td>--%>
<%--						<td>:</td>--%>
<%--						<td><fmt:formatDate value='${billing_form.dueDate}' pattern='dd - MM - yyyy'/></td>--%>
<%--					</tr>--%>
					<tr>
						<td>&nbsp;</td>
						<td>Delivery Order No</td>
						<td>:</td>
						<td><c:out value='${doCode}'/></td>
					</tr>
<%--					<tr>--%>
<%--						<td>&nbsp;</td>--%>
<%--						<td valign="top">Note</td>--%>
<%--						<td valign="top">:</td>--%>
<%--						<td style="white-space: normal"><c:out value='${billing_form.note}'/></td>--%>
<%--					</tr>--%>
				</table>
			</td>
			<td width="2%" colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="10">&nbsp;</td>
		</tr>
	</table>

	<table border="0" width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2">&nbsp;</td>
			<td colspan="6" align="center"><font size="4">PT. SAN STAR MANUNGGAL</font></td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td colspan="6" width="55%" align="center"><hr/></td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td colspan="6" align="center"><font size="3">Kantor : Jl. Raya Rancaekek Km.27 Rt.01/01 Sumedang Jabar</font></td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td colspan="6" align="center"><font size="3">Telp. (022) 7998320, Fax. (022) 7798320</font></td>
			<td colspan="2">&nbsp;</td>
		</tr>
	</table>
	<br/>

	<table border="0" width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2">&nbsp;</td>
			<td colspan="6">Kepada Yth :</td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td colspan="4" width="10%"><span style="display:inline-block; width:2em"></span>Nama</td>
			<td width="5%" align="center">:</td>
			<td width="81%"><c:out value='${billing_form.customer.salutation} ${billing_form.customer.fullName}'/></td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td colspan="4" width="10%"><span style="display:inline-block; width:2em"></span>Alamat</td>
			<td width="5%" align="center">:</td>
			<td width="81%">
				<c:out value="${billing_form.shippingAddress.address}"/>
				<c:out value="${billing_form.shippingAddress.city.name}"/>
				<c:out value="${billing_form.shippingAddress.province.name}"/>
				<c:out value="${billing_form.shippingAddress.postalCode}"/>
			</td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td colspan="4" width="10%"><span style="display:inline-block; width:2em"></span>NPWP</td>
			<td width="5%" align="center">:</td>
			<td width="81%"><c:out value="${billing_form.customer.coreTax.npwp}"/></td>
			<td colspan="2">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table border="0" width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2">&nbsp;</td>
			<td class="border-top border-left border-bottom" align="center" width="5%">No.<br/>Urut</td>
			<td class="border-top border-left border-bottom" colspan="2" align="center" width="46%">Nama Barang Kena Pajak/Jasa Kena Pajak</td>
			<td class="border-top border-left border-bottom" align="center" width="15%">Kwantum</td>
			<td class="border-top border-left border-bottom" align="center" width="15%">Harga Satuan</td>
			<td class="border-top border-left border-right border-bottom" align="center" width="15%">Jumlah<br/>(<c:out value="${billing_form.money.currency.symbol}"/>.)</td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<c:forEach items='${billing_form.billing.items}' var='item' varStatus='status'>
			<tr class="data-row">
				<td colspan="2">&nbsp;</td>
				<td class="border-left" align="center"><c:out value="${status.index+1}"/></td>
				<td class="border-left" colspan="2">${item.billingReferenceItem.product.name}&nbsp;${item.billingReferenceItem.product.unitOfMeasure.measureId}</td>
				<td class="border-left" align="right"><fmt:formatNumber value='${item.billingReferenceItem.quantity}' pattern=',##0.00'/></td>
				<td class="border-left" align="right"><fmt:formatNumber value='${item.billingReferenceItem.money.amount}' pattern=',##0.00'/></td>
				<td class="border-left border-right" align="right"><fmt:formatNumber value='${item.billingReferenceItem.subtotal}' pattern=',##0.00'/></td>
				<td colspan="2">&nbsp;</td>
			</tr>
		</c:forEach>
		<tr class="data-row total-row">
			<td colspan="2">&nbsp;</td>
			<td class="border-left" align="center"></td>
			<td class="border-left" colspan="2">
				Jumlah Piutang&emsp;<c:out value="${billing_form.money.currency.symbol}"/>.
				<span style="display:inline-block; width:2em"></span>
				<fmt:formatNumber value='${billing_edit.totalBillingAmount}' pattern=',##0.00'/>
			</td>
			<td class="border-left"></td>
			<td class="border-left"></td>
			<td class="border-left border-right"></td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td class="border-left border-top" colspan="4">
				<span style="display:inline-block; width:2em"></span>
				Harga Jual<s>/Penggantian/Uang Muka/Termin</s> **)
			</td>
			<td class="border-top"><c:out value="${billing_form.money.currency.symbol}"/>.</td>
			<td class="border-left border-top border-right" align="right">
				<fmt:formatNumber value='${billing_edit.totalLineAmount}' pattern=',##0.00'/>
			</td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td class="border-left border-top" colspan="4">
				<span style="display:inline-block; width:2em"></span>
				Dikurangi Potongan Harga
			</td>
			<td class="border-top"></td>
			<td class="border-left border-top border-right" align="right">
				<fmt:formatNumber value='${billing_edit.totalDiscountAmount}' pattern=',##0.00'/>
			</td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td class="border-left border-top" colspan="4">
				<span style="display:inline-block; width:2em"></span>
				Dikurangi Uang Muka yang telah diterima
			</td>
			<td class="border-top"></td>
			<td class="border-left border-top border-right" align="right">
			</td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td class="border-left border-top" colspan="4">
				<span style="display:inline-block; width:2em"></span>
				Dasar Pengenaan Pajak
			</td>
			<td class="border-top"><c:out value="${billing_form.money.currency.symbol}"/>.</td>
			<td class="border-left border-top border-right" align="right">
				<fmt:formatNumber value='${billing_edit.totalAfterDiscount}' pattern=',##0.00'/>
			</td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td class="border-left border-top border-bottom" colspan="4">
				<span style="display:inline-block; width:2em"></span>
				PPN = <fmt:formatNumber value='${billing_form.tax.taxRate}' pattern=',##0'/>% x Dasar Pengenaan Pajak
			</td>
			<td class="border-top border-bottom"><c:out value="${billing_form.money.currency.symbol}"/>.</td>
			<td class="border-left border-top border-right border-bottom" align="right">
				<fmt:formatNumber value='${billing_edit.taxAmount}' pattern=',##0.00'/>
			</td>
			<td colspan="2">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="test">
		<tr>
			<td width="2%" colspan="2">&nbsp;</td>
			<td width="48%" colspan="3" valign="top">
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td width="96%">Untuk Pembayaran Giro / Cek / Transfer ke Rek :</td>
					</tr>
					<tr>
						<td width="96%">BANK OCBC NISP (Cab. Asia Afrika)</td>
					</tr>
					<tr>
						<td width="96%">A/C : 0608.0000.6227 (IDR)</td>
					</tr>
					<tr>
						<td width="96%">A/N : PT. SAN STAR MANUNGGAL</td>
					</tr>
				</table>
			</td>
			<td width="48%" colspan="3" valign="top">
				<table width="100%" cellpadding="0" cellspacing="0" align="left">
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td align="center">
							Sumedang,
							<span style="display:inline-block; width:2em"></span>
							<fmt:formatDate value='${billing_form.date}' pattern='dd MMMM yyyy'/>
						</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td align="center">.............................................</td></tr>
					<tr>
						<td align="center"><u>Eddy Tjahjadi</u></td>
					</tr>
					<tr>
						<td align="center">Direktur</td>
					</tr>
				</table>
			</td>
			<td width="2%" colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="10">&nbsp;</td>
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
	<%@include file="billingExcel.jsp"%>
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>