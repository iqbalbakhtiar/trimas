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
	<a class="item-button-back" href="<c:url value='/page/billingpreedit.htm?id=${billing_form.billing.id}'/>"><span><spring:message code="sirius.back"/></span></a>
	<a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
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
			<td width="96%" colspan="6" align="center" class="CSS3" valign="top"><strong>KWITANSI</strong></td>
			<td width="2%" colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="10" height="15">&nbsp;</td>
		</tr>
	</table>
	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="test">
		<tr>
			<td width="2%" colspan="2">&nbsp;</td>
			<td width="96%" colspan="3" valign="top">
				<table width="100%" cellpadding="0" cellspacing="0" align="left">
					<tr>
						<td width="10%">No.</td>
						<td width="1%">:</td>
						<td width="30%"><!-- CODE TBD --></td>
					</tr>
					<tr>
						<td width="10%">Sudah Terima dari</td>
						<td width="1%">:</td>
						<td width="30%"><c:out value='${billing_form.billing.customer.fullName}'/></td>
					</tr>
					<tr>
						<td width="10%">Banyaknya Uang</td>
						<td width="1%">:</td>
						<td width="30%" class="border-top border-bottom border-left border-right"><c:out value='${saidId}'/> <c:out value="${billing_form.billing.money.currency.alias}"/></td>
					</tr>
					<tr>
						<td width="10%">Untuk Pembayaran</td>
						<td width="1%">:</td>
						<td width="30%"><c:out value='${billing_form.billing.code}'/></td>
					</tr>
				</table>
			</td>
			<td width="2%" colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="10">&nbsp;</td>
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
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr class="border-top border-bottom">
						<td class="border-top border-bottom" width="10%">Jumlah ${billing_form.money.currency.symbol}</td>
						<td class="border-top border-bottom" width="30%" align="right"><fmt:formatNumber value="${billing_edit.totalBillingAmount}" pattern=",##0.00"/>&nbsp;&nbsp;&nbsp;</td>
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
					<!-- <tr><td align="center">.............................................</td></tr>
					<tr>
						<td align="center"><u>Eddy Tjahjadi</u></td>
					</tr>
					<tr>
						<td align="center">Direktur</td>
					</tr> -->
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
</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>