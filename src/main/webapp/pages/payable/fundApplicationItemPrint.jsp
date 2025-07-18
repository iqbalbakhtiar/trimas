<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/common/tld-common.jsp"%>
<%@ include file="/common/tld-spring.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
	<title>${title}</title>
	<link rel="icon" type="image/png" href="<c:url value='/assets/images/title-logo.png'/>">
	<style type="text/css" media="screen">
		@import url("<c:url value='/assets/sirius.css'/>");
		@import url("assets/sirius-print.css");
	</style>
	<style type="text/css" media="print">
		@import url("assets/sirius-print.css");
	</style>
	<script type="text/javascript">
			function printPage()
			{
	   			print();
			}
	</script>
</head>
<body>
<div class="area" dojoType="Container" id="quick_link_container">
	<%@ include file="/common/sirius-header.jsp"%>
</div>
<div id="se-r00">
	<div id="se-r01">&nbsp;</div>
	<div id="se-r02">&nbsp;</div>
</div>
<div id="se-containers">
	<div class="area" dojoType="Container" id="quick_link_container">
		<%@ include file="/common/sirius-menu.jsp"%>
	</div>
	<div id="se-navigator">
		<div class="area" dojoType="Container" id="quick_link_container">
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
					  		<div class="toolbar">
								<a class="item-button-back" href="<c:url value='/page/fundapplicationpreedit.htm?id=${applicationItem.fundApplication.id}'/>"><span><spring:message code="sirius.back"/></span></a>
								<a class="item-button-print" href="javascript:window.print();"><span><spring:message code="sirius.print"/></span></a>
					  		</div>
						</div>
						<div class="main-box">
							<div class="main_container">
								<table border="0" width="100%" cellpadding="0" cellspacing="0" class="in">
								<tr>
									<td style="background: black" width="1%">&nbsp;</td>
									<td width="1%">&nbsp;</td>
									<td width="96%">&nbsp;</td>
									<td width="1%">&nbsp;</td>
									<td style="background: black" width="1%">&nbsp;</td>
								</tr>
								</table>
								<table border="0" width="100%" cellpadding="3" cellspacing="0">
								<tr>
									<td><strong>PENGAJUAN DANA</strong></td>
								</tr>
								<tr>
									<td class="border-top border-bottom border-left border-right" style="padding: 0;">
										<table border="0" width="100%" cellpadding="3" cellspacing="0">
										<tr style="height: 30px;">
											<td colspan="2" class="border-bottom border-right">Pembayaran Kepada :</td>
											<td colspan="3" class="border-bottom border-right">${applicationItem.supplier.fullName}</td>
											<td class="border-bottom">&nbsp;</td>
										</tr>
										<tr style="height: 10px;">
											<td width="15%" class="border-bottom">&nbsp;</td>
											<td width="15%" class="border-bottom">&nbsp;</td>
											<td width="15%" class="border-bottom">&nbsp;</td>
											<td width="15%" class="border-bottom">&nbsp;</td>
											<td width="15%" class="border-bottom">&nbsp;</td>
											<td width="20%" class="border-bottom">&nbsp;</td>
										</tr>
										<tr style="height: 20px;font-weight: bold;">
											<td class="border-bottom border-right" align="center">No. Perk :</td>
											<td colspan="4" class="border-bottom border-right" align="center">Nama Perkiraan / Uraian Penerimaan</td>
											<td class="border-bottom" align="center">Jumlah Uang</td>
										</tr>
										<tr style="height: 220px;">
											<td class="border-bottom border-right"></td>
											<td class="border-bottom border-right" colspan="4" valign="middle">
												<table border="0" width="100%" cellpadding="3" cellspacing="0">
												<c:if test="${not empty applicationItem.fundApplication.note}">
												<tr>
													<td width="5%">&nbsp;</td>
													<td width="95%" style="padding-left: 10px;">${applicationItem.fundApplication.note}</td>
												</tr>
												<tr style="height: 50px;">
													<td colspan="2">&nbsp;</td>
												</tr>
												</c:if>
												<tr>
													<td width="5%" nowrap="nowrap">No. Acc</td>
													<td width="95%" style="text-transform: uppercase;">: <strong>${bankAccount.bankName}, ${bankAccount.accountNo}</strong></td>
												</tr>
												<tr>
													<td>A/N</td>
													<td style="text-transform: uppercase;">: <strong>${bankAccount.accountName}</strong></td>
												</tr>
												</table>
											</td>
											<td class="border-bottom" valign="middle" align="center"><fmt:formatNumber value="${applicationItem.amount}" pattern=",##0.00"/></td>
										</tr>
										<tr>
											<td class="border-bottom border-right">Terbilang</td>
											<td class="border-bottom border-right" colspan="4"><c:out value='${saidId}'/> Rupiah</td>
											<td class="border-bottom" align="center"><strong>Rp <fmt:formatNumber value="${applicationItem.amount}" pattern=",##0.00"/></strong></td>
										</tr>
										<tr style="height:20px;">
											<td class="border-bottom border-right" align="center">Tanggal</td>
											<td class="border-bottom border-right" align="center">Pembuat</td>
											<td class="border-bottom border-right" align="center">Kep. Bagian</td>
											<td class="border-bottom border-right" align="center">Mengetahui</td>
											<td class="border-bottom" colspan="2">&nbsp;</td>
										</tr>
										<tr style="height: 100px;">
											<td class="border-right">&nbsp;</td>
											<td class="border-right">&nbsp;</td>
											<td class="border-right">&nbsp;</td>
											<td class="border-right">&nbsp;</td>
											<td colspan="2">&nbsp;</td>
										</tr>
										<tr style="height: 20px;">
											<td class="border-right">&nbsp;</td>
											<td class="border-right">&nbsp;</td>
											<td class="border-right">&nbsp;</td>
											<td class="border-right" style="text-align: center;">ARVIN</td>
											<td colspan="2" align="center">(<div style="display: inline-block;width: 200px;">&nbsp;</div>)</td>
										</tr>
										</table>
									</td>
								</tr>
								</table>
								<table border="0" width="100%" cellpadding="0" cellspacing="0" class="out">
								<tr>
									<td style="background: black" width="1%">&nbsp;</td>
									<td width="1%">&nbsp;</td>
									<td width="96%" colspan="2">&nbsp;</td>
									<td width="1%">&nbsp;</td>
									<td style="background: black" width="1%">&nbsp;</td>
								</tr>
								</table>
							</div>
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