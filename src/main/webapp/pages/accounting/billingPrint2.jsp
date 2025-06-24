<%@ include file="/common/sirius-general-top.jsp"%>

<style type="text/css" media="screen">
	@import url("<c:url value='/assets/sirius.css'/>");
	@import url("<c:url value='/assets/sirius-print-a4.css'/>");
</style>
<style type="text/css" media="print">
	@import url("<c:url value='/assets/sirius-print-a4.css'/>");
</style>

<div class="toolbar">
	<a class="item-button-back" href="<c:url value='/page/${billing_edit.billing.billingType.url}?id=${billing_form.billing.id}'/>"><span><spring:message code="sirius.back"/></span></a>
	<a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
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
			<br/>
			<table border="0" width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td class="border-top border-bottom border-left border-right">
						<table border="0" width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td width="2%" colspan="2">&nbsp;</td>
								<td class="print-title" style="text-align: center" width="96%" colspan="6" align="center" valign="top"><strong>KWITANSI</strong></td>
								<td width="2%" colspan="2">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="10" height="15">&nbsp;</td>
							</tr>
						</table>
						<table border="0" width="100%" cellpadding="3" cellspacing="0" id="test">
							<tr>
								<td width="2%" colspan="2">&nbsp;</td>
								<td width="96%" colspan="3" valign="top">
									<table width="100%" cellpadding="3" cellspacing="0" align="left">
										<tr>
											<td width="10%"><strong>No.</strong></td>
											<td width="1%"><strong>:</strong></td>
											<td width="30%">
												<strong><c:out value="${billing_form.billing.code}"/></strong>
											</td>
										</tr>
										<tr>
											<td width="10%"><strong>Sudah Terima dari</strong></td>
											<td width="1%"><strong>:</strong></td>
											<td width="30%"><strong><c:out value='${billing_form.billing.customer.fullName}'/></strong></td>
										</tr>
										<tr><td colspan="3" style="height: 10px;">&nbsp;</td></tr>
										<tr>
											<td width="10%" style="vertical-align: top;"><strong>Banyaknya Uang</strong></td>
											<td width="1%" style="vertical-align: top;"><strong>:</strong></td>
											<td width="30%" class="border-top border-bottom border-left border-right" style="padding: 0.5rem;">
												<c:out value='${saidId}'/> <c:out value="${billing_form.billing.money.currency.alias}"/>
											</td>
										</tr>
										<tr><td colspan="3" style="height: 10px;">&nbsp;</td></tr>

										<tr>
											<td width="10%" style="vertical-align: top;"><strong>Untuk Pembayaran</strong></td>
											<td width="1%" style="vertical-align: top;"><strong>:</strong></td>
											<td width="30%" style="vertical-align: top;"><strong><c:out value='${doCode}'/></strong></td>
										</tr>
									</table>
								</td>
								<td width="2%" colspan="2">&nbsp;</td>
							</tr>
						</table>
						<table border="0" width="100%" cellpadding="0" cellspacing="0" id="test">
							<tr>
								<td width="2%" colspan="2">&nbsp;</td>
								<td width="48%" colspan="3" valign="top">
									<table width="100%" cellpadding="0" cellspacing="0" align="left">
										<tr><td>&nbsp;</td></tr>
										<tr><td>&nbsp;</td></tr>
										<tr><td>&nbsp;</td></tr>
										<tr><td>&nbsp;</td></tr>
										<tr><td>&nbsp;</td></tr>
										<tr><td>&nbsp;</td></tr>
										<tr><td>&nbsp;</td></tr>
										<tr><td>&nbsp;</td></tr>
										<tr class="border-top border-bottom">
											<td class="border-top border-bottom" width="10%"><strong>Jumlah ${billing_form.money.currency.symbol}</strong></td>
											<td class="border-top border-bottom" width="30%" align="right"><strong><fmt:formatNumber value="${billing_edit.totalBillingAmount}" pattern=",##0.00"/>&nbsp;&nbsp;&nbsp;</strong></td>
										</tr>
									</table>
								</td>
								<td width="48%" colspan="3" valign="top">
									<table width="100%" cellpadding="0" cellspacing="0" align="left">
										<tr><td>&nbsp;</td></tr>
										<tr><td>&nbsp;</td></tr>
										<tr>
											<td align="center">
												<strong>
													Sumedang, <fmt:formatDate value='${billing_form.date}' pattern='dd MMMM yyyy'/>
												</strong>
											</td>
										</tr>
										<tr><td align="center">PT. SAN STAR MANUNGGAL</td></tr>
										<tr><td>&nbsp;</td></tr>
										<tr><td>&nbsp;</td></tr>
									</table>
								</td>
								<td width="2%" colspan="2">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="10">&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<br/>
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