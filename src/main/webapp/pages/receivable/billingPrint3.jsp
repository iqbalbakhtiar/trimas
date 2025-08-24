<%@ include file="/common/sirius-general-top.jsp"%>

<style type="text/css" media="screen">
	@import url("<c:url value='/assets/sirius.css'/>");
	@import url("<c:url value='/assets/sirius-print-a4.css'/>");
</style>
<style type="text/css" media="print">
	@import url("<c:url value='/assets/sirius-print-a4.css'/>");
</style>
<style>
	tr.body-print td {
		padding-top: 0.75rem;
	}
</style>

<div class="toolbar">
	<a class="item-button-back" href="<c:url value='/page/${billing_edit.billing.billingType.url}?id=${billing_form.billing.id}'/>"><span><spring:message code="sirius.back"/></span></a>
	<a class="item-button-print" href="javascript:print();"><span><spring:message code="sirius.print"/></span></a>
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
			<table border="0" width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td width="2%" colspan="2">&nbsp;</td>
  				<td colspan="6">
  				<img style="margin-left: -15px;" src="assets/images/shunhui-logo.png" width="350" height="40"/>
  				</br><font size="2"></font>
  				</td>
				<td width="2%" colspan="2">&nbsp;</td>
  			</tr>
  			<tr>
				<td width="2%" colspan="2">&nbsp;</td>
  				<td colspan="6" style="text-transform: uppercase;">
  					<c:forEach items='${billing_form.organization.postalAddresses}' var='address'>
					<c:if test='${address.selected}'>
						${address.address}</br>
						${address.city.name}, ${address.city.parent.name}</br>
						Tlp. (022) 0000000</br>
						Fax. (022) 0000000</br>
					</c:if>
				    </c:forEach>
  				</td>
				<td width="2%" colspan="2">&nbsp;</td>
  			</tr>
				<tr>
					<td width="100%" colspan="10">&nbsp;</td>
				</tr>
				<tr class="body-print">
					<td width="2%" colspan="2">&nbsp;</td>
					<td width="96%" colspan="6" align="left">Kepada Yth.</td>
					<td width="2%" colspan="2">&nbsp;</td>
				</tr>
				<tr class="body-print">
					<td width="2%" colspan="2">&nbsp;</td>
					<td width="96%" colspan="6" align="left">${billing_form.customer.salutation} <c:out value="${billing_form.billing.customer.fullName}"/></td>
					<td width="2%" colspan="2">&nbsp;</td>
				</tr>
				<tr class="body-print">
					<td width="2%" colspan="2">&nbsp;</td>
					<td width="96%" colspan="6" align="left">
						<c:out value="${billing_form.shippingAddress.address}"/>
						<c:out value="${billing_form.shippingAddress.city.name}"/>
						<c:out value="${billing_form.shippingAddress.province.name}"/>
						<c:out value="${billing_form.shippingAddress.postalCode}"/>
					</td>
					<td width="2%" colspan="2">&nbsp;</td>
				</tr>
				<tr class="body-print">
					<td colspan="10" height="15">&nbsp;</td>
				</tr>
				<tr class="body-print">
					<td width="2%" colspan="2">&nbsp;</td>
					<td width="96%" colspan="6" align="left">Dengan Hormat,</td>
					<td width="2%" colspan="2">&nbsp;</td>
				</tr>
				<tr class="body-print">
					<td width="2%" colspan="2">&nbsp;</td>
					<td width="96%" colspan="6" align="left">Bersama ini harap diterima dokumen-dokumen dibawah ini:</td>
					<td width="2%" colspan="2">&nbsp;</td>
				</tr>
				<tr class="body-print">
					<td width="2%" colspan="2">&nbsp;</td>
					<td width="2%">&nbsp;</td>
					<td width="96%" colspan="6" align="left">1. Kwitansi Asli Senilai ${billing_form.billing.money.currency.symbol} <fmt:formatNumber value="${billing_edit.totalBillingAmount}" pattern="#,##0.00"/></td>
					<td width="2%" colspan="2">&nbsp;</td>
				</tr>
				<tr class="body-print">
					<td width="2%" colspan="2">&nbsp;</td>
					<td width="2%">&nbsp;</td>
					<td width="96%" colspan="6" align="left">2. Faktur Pajak Asli No. <c:out value="${billing_form.billing.invoiceTaxHeader}${billing_form.billing.invoiceTaxNo}"/></td>
					<td width="2%" colspan="2">&nbsp;</td>
				</tr>
				<tr class="body-print">
					<td width="2%" colspan="2">&nbsp;</td>
					<td width="2%">&nbsp;</td>
					<td width="96%" colspan="6" align="left">3. Faktur Penjualan No. ${billing_form.billing.code}</td>
					<td width="2%" colspan="2">&nbsp;</td>
				</tr>
				<tr class="body-print">
					<td width="2%" colspan="2">&nbsp;</td>
					<td width="2%">&nbsp;</td>
					<td width="96%" colspan="6" align="left">4. Surat Jalan Asli</td>
					<td width="2%" colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="10" height="15">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="10" height="15">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="10" height="15">&nbsp;</td>
				</tr>
				<tr>
					<td width="2%" colspan="2">&nbsp;</td>
					<td width="96%" colspan="6" align="left">Atas perhatiannya saya ucapkan terima kasih.</td>
					<td width="2%" colspan="2">&nbsp;</td>
				</tr>
			</table>
			<br/>
			<table border="0" width="100%" cellpadding="0" cellspacing="0" id="test">
				<tr>
					<td width="2%" colspan="2">&nbsp;</td>
					<td width="48%" colspan="3" valign="top">
						<table width="100%" cellpadding="0" cellspacing="0" align="left">
							<tr><td>&nbsp;</td></tr>
							<tr><td>&nbsp;</td></tr>
							<tr>
								<td align="left">
									Cimahi,
									<span style="display:inline-block; width:2em"></span>
									<fmt:formatDate value='${billing_form.date}' pattern='dd MMMM yyyy'/>
								</td>
							</tr>
							<tr><td align="left">Hormat Kami,</td></tr>
							<tr><td>&nbsp;</td></tr>
							<tr><td>&nbsp;</td></tr>
							<tr><td>&nbsp;</td></tr>
							<tr><td align="left">.............................................</td></tr>
							<tr>
								<td align="left"><u>PT SHUN HUI ZHIYE INDONESIA</u></td>
							</tr>
						</table>
					</td>
					<td width="48%" colspan="3" valign="top">
						<table width="100%" cellpadding="0" cellspacing="0" align="left">
							<tr><td>&nbsp;</td></tr>
							<tr><td align="center">Penerima,</td></tr>
							<tr>
								<td align="center">
									Nama & Tanda Tangan
								</td>
							</tr>
							<tr align="center"><td>Tanggal</td></tr>
							<tr><td>&nbsp;</td></tr>
							<tr><td>&nbsp;</td></tr>
							<tr><td>&nbsp;</td></tr>
							<tr><td align="center">.............................................</td></tr>
							<tr><td>&nbsp;</td></tr>
						</table>
					</td>
					<td width="2%" colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="10">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="10">&nbsp;</td>
				</tr>
				<tr>
					<td width="2%" colspan="2">&nbsp;</td>
					<td width="96%" colspan="6" align="left">*) Tanda Terima Mohon Ditanda Tangan dan Dicap</td>
					<td width="2%" colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td width="2%" colspan="2">&nbsp;</td>
					<td width="96%" colspan="6" align="left">*) Lembar Pertama Tanda Terima Harap Dikembalikan atau Difax</td>
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
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>