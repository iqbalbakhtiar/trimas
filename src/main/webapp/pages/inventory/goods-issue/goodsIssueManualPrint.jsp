<%@ include file="/pages/includes/sirius-head.jsp"%>

<style>
	tr.data-row td {
		padding: 1rem 0.25rem 0;
	}
</style>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/goodsissuemanualview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-back" href="<c:url value='/page/goodsissuemanualpreedit.htm?id=${transaction_form.id}'/>"><span><spring:message code="sirius.back"/></span></a>
	<a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
</div>

<div class="main-box">
	<div class="main_container">
		<center>
			<table border="0" width="100%" cellpadding="0" cellspacing="0" class="in">
				<tr>
					<td style="background: black" width="1%">&nbsp;</td>
					<td width="96%">&nbsp;</td>
					<td style="background: black" width="1%">&nbsp;</td>
				<tr>
			</table>

			<table border="0" width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td width="2%">&nbsp;</td>
					<td class="print-title CSS3" valign="top" colspan="5"><strong>PT. SANSTAR MANUNGGAL</strong></td>
					<td width="2%">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="10" height="15">&nbsp;</td>
				</tr>
				<tr>
					<td width="2%">&nbsp;</td>
					<td class="print-title CSS3" valign="top" align="center"><strong>SURAT JALAN</strong></td>
					<td width="2%">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="10" height="15">&nbsp;</td>
				</tr>
			</table>

			<table border="0" width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td width="2%">&nbsp;</td>
					<td width="48%" colspan="3" valign="top">
						<table width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td width="10%">NO&nbsp;:</td>
								<td><c:out value="${transaction_form.code}"/></td>
							</tr>
							<tr>
								<td width="60%" colspan="2">Bersama ini kendaraan</td>
								<td>No.</td>
							</tr>
							<tr>
								<td colspan="2">Kami kirimkan barang trersebut dibawah ini</td>
							</tr>
							<tr>
								<td colspan="2">Harap diterima dengan baik.</td>
							</tr>
						</table>
					</td>
					<td width="48%" colspan="3" valign="top">
						<table width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td width="20%" colspan="2">Sumedang,</td>
								<td width="80%"><fmt:formatDate value='${transaction_form.date}' pattern='dd-MM-yyyy'/></td>
							</tr>
							<tr>
								<td width="20%">Kepada Yth.</td>
								<td width="80%" colspan="2"><c:out value="${transaction_form.recipient.fullName}"/></td>
							</tr>
						</table>
					</td>
					<td width="2%">&nbsp;</td>
				</tr>
			</table>
			<br/>
			<br/>
			<br/>
			<table border="0" width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td width="2%">&nbsp;</td>
					<td width="20%" align="center" class="border-top border-left border-bottom">Banyaknya</td>
					<td width="38%" align="center" class="border-top border-left border-bottom">Nama Barang</td>
					<td width="38%"align="center" class="border-top border-left border-right border-bottom">Keterangan</td>
					<td width="2%">&nbsp;</td>
				</tr>
				<c:forEach items='${transaction_edit.items}' var='item' varStatus='status'>
					<tr class="data-row">
						<td width="2%">&nbsp;</td>
						<td class="border-left" align="center">
							<fmt:formatNumber value='${item.issued}' pattern=',##0'/>
							<span style="margin-left:1rem"></span>
							<c:out value="${item.product.unitOfMeasure.measureId}"/>
						</td>
						<td class="border-left">
							<span style="margin-left:1rem"></span>
							<c:out value="${item.product.name}"/>&nbsp;-&nbsp;<c:out value="${item.product.code}"/>
						</td>
						<td class="border-left border-right">
							<span style="margin-left:1rem"></span>
							<c:out value="${item.note}"/></td>
						<td width="2%">&nbsp;</td>
					</tr>
				</c:forEach>
				<tr class="data-row">
					<td width="2%">&nbsp;</td>
					<td class="border-left"></td>
					<td class="border-left"></td>
					<td class="border-left border-right"></td>
					<td width="2%">&nbsp;</td>
				</tr>
				<tr>
					<td width="2%">&nbsp;</td>
					<td colspan="3" class="border-top"></td>
					<td width="2%">&nbsp;</td>
				</tr>
				<tr>
					<td width="2%">&nbsp;</td>
					<td>Penerima, </td>
					<td align="center">Mengetahui, </td>
					<td align="center">Hormat Kami, </td>
					<td width="2%">&nbsp;</td>
				</tr>
			</table>
			<br/>
			<br/>
			<br/>
			<br/>
			<br/>
			<br/>
			<table border="0" width="100%" cellpadding="0" cellspacing="0" class="out">
				<tr>
					<td style="background: black" width="1%">&nbsp;</td>
					<td width="96%">&nbsp;</td>
					<td style="background: black" width="1%">&nbsp;</td>
				</tr>
			</table>
		</center>
	</div>

</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>