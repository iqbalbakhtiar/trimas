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
	<title><spring:message code="salesorder.contract"/></title>
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
								<a class="item-button-back" href="<c:url value="/page/salesorderpreedit.htm?id=${salesOrder_edit.id}"/>"><span><spring:message code="sirius.back"/></span></a>
								<a class="item-button-print" href="javascript: window.print();"><span><spring:message code="sirius.print"/></span></a>
						  	</div>
					  	</div>
					  	<div class="main-box">
					  		<table border="0" width="100%" align="center">
					  			<tr>
					  				<td>
					  				<img src="assets/images/ssm-logo.png"  width="250" height="40"/>
					  				</br><font size="2"></font>
					  				</td>
					  			</tr>
					  			<tr>
					  				<td style="text-transform: uppercase;">
					  					<c:forEach items='${salesOrder_edit.organization.postalAddresses}' var='address'>
										<c:if test='${address.selected}'>
											${address.address}</br>
											SUMEDANG - JAWA BARAT</br>
											Tlp. (022) 7798320</br>
											Fax. (022) 7790185</br>
										</c:if>
									    </c:forEach>
					  				</td>
					  			</tr>
					  		</table>
					  		<table border="0" width="100%" align="center">
				  			<tr>
				  				<td colspan="3" align="center"><h2>KONTRAK PENJUALAN</h2></td>
				  			</tr>
				  			<tr>
				  				<td width="10%"><spring:message code="sirius.date"/></td>
				  				<td align="left">: <fmt:formatDate value='${salesOrder_edit.date}' pattern='dd-MM-yyyy'/></td>
				  				<td></td>
				  			</tr>
				  			<tr>
				  				<td width="10%"><spring:message code="salesorder.expired.date"/></td>
				  				<td align="left">: <fmt:formatDate value='${salesOrder_edit.expDate}' pattern='dd-MM-yyyy'/></td>
				  				<td></td>
				  			</tr>
				  			<tr>
				  				<td><spring:message code="salesorder.contract.no"/></td>
				  				<td align="left">: ${salesOrder_edit.code}</td>
				  				<td align="left"></td>
				  			</tr>
				  			<tr>
				  				<td width="10%">No PO</td>
				  				<td align="left">: ${salesOrder_edit.poCode}</td>
				  				<td></td>
				  			</tr>
				  			<tr valign="top">
				  				<td nowrap="nowrap"><spring:message code="customer"/></td>
				  				<td align="left" style="text-transform: uppercase;">:
				  					<c:out value='${salesOrder_edit.customer.salutation}'/> <c:out value='${salesOrder_edit.customer.fullName}'/>
				  					</br>
				  					<c:forEach items='${salesOrder_edit.customer.postalAddresses}' var='address'>
									<c:if test='${address.selected}'>
										&nbsp;&nbsp;<c:out value='${address.address}'/></br>
										&nbsp;&nbsp;<c:out value='${address.city.name}'/>
									</c:if>
								    </c:forEach>
				  				</td>
				  				<td>&nbsp;</td>
				  			</tr>
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
				  			<tr>
				  				<td><spring:message code="contactmechanism.phone"/></td>
				  				<td align="left">: ${phone}</td>
				  				<td align="left"></td>
				  			</tr>
				  			<tr>
				  				<td><spring:message code="contactmechanism.fax"/></td>
				  				<td align="left">: ${fax}</td>
				  				<td align="left"></td>
				  			</tr>
				  			<tr>
				  				<td><spring:message code="salesorder.contract.payment"/></td>
				  				<c:if test="${salesOrder_edit.term gt 0}">
				  					<td align="left">: ${salesOrder_edit.term} hari setelah barang diterima</td>
				  				</c:if>
				  				<c:if test="${salesOrder_edit.term eq 0}">
				  					<td align="left">: CASH</td>
				  				</c:if>
				  				<td align="left"></td>
				  			</tr>
					  		</table>
					  		<br/>
					  		<table border="0" cellpadding="1" cellspacing="1" width="100%" align="center" style="border-width: 1px medium medium; border-style: solid none none; border-color: black -moz-use-text-color -moz-use-text-color;">
					  			<tr>
					  				<th style="border-bottom:1px solid black;border-left:1px solid black;width: 30%"><spring:message code="product"/></th>
					  				<th style="border-bottom:1px solid black;border-left:1px solid black;width: 10%"><spring:message code="sirius.qty"/></th>
					  				<th style="border-bottom:1px solid black;border-left:1px solid black;width: 10%"><spring:message code="sirius.uom"/></th>
					  				<th style="border-bottom:1px solid black;border-left:1px solid black;width: 20%"><spring:message code="sirius.unitprice"/></th>
					  				<th style="border-bottom:1px solid black;border-right:1px solid black;border-left:1px solid black;width: 20%"><spring:message code="sirius.total"/></th>
					  			</tr>
								<c:forEach items="${adapter.items}" var="item" varStatus="idx">
					  			<tr style="height: 25px;">
                               		<c:set var="pattern" value=",##0.00" />
					  				<td style="border-bottom:1px solid black;border-left:1px solid black;">${item.product.name}</td>
					  				<td align="right" style="border-bottom:1px solid black;border-left:1px solid black;"><fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/></td>
					  				<td align="center" style="border-bottom:1px solid black;border-left:1px solid black;">${item.product.unitOfMeasure.measureId}</td>
					  				<td align="right" style="border-bottom:1px solid black;border-left:1px solid black;"><fmt:formatNumber value='${item.amount}' pattern='${pattern}'/></td>
					  				<td align="right" style="border-bottom:1px solid black;border-right:1px solid black;border-left:1px solid black;"><fmt:formatNumber value='${item.subTotal}' pattern='${pattern}'/></td>
					  			</tr>
					  			</c:forEach>
					  			<tr style="font-weight: bold;">
					  				<td align="right" colspan="3">&nbsp;</td>
					  				<td align="right"><spring:message code="salesorder.total"/></td>
					  				<td align="right" style="border-bottom:1px solid black;border-right:1px solid black;border-left:1px solid black;"><fmt:formatNumber value='${adapter.totalItemAmount}' pattern='${pattern}'/></td>
					  			</tr>
					  			<tr style="font-weight: bold;">
					  				<td align="right" colspan="3">&nbsp;</td>
					  				<td align="right"><spring:message code="salesorder.tax.amount"/></td>
					  				<td align="right" style="border-bottom:1px solid black;border-right:1px solid black;border-left:1px solid black;"><fmt:formatNumber value='${adapter.taxAmount}' pattern='${pattern}'/></td>
					  			</tr>
					  			<tr style="font-weight: bold;">
					  				<td align="right" colspan="3">&nbsp;</td>
					  				<td align="right"><spring:message code="salesorder.total.transaction"/></td>
					  				<td align="right" style="border-bottom:1px solid black;border-right:1px solid black;border-left:1px solid black;"><fmt:formatNumber value='${adapter.totalTransaction}' pattern='${pattern}'/></td>
					  			</tr>
					  		</table>
					  		<br/>
					  		<table border="0" cellpadding="1" cellspacing="2" width="100%" align="center" style="table-layout: fixed;">
				  			<tr>
				  				<td colspan="5"><spring:message code="salesorder.contract.term"/> :</td>
				  			</tr>
				  			<tr>	
				  				<td align="left" colspan="5">
				  					<ul>
				  						<li>Toleransi penyerahan barang (+/-) 5% dari quantity yang tertera dalam order.</li>
				  						<li>Pembayaran dengan check,  Bilyet Giro, L/C atau Transfer dianggap terlaksana setelah dana masuk ke rekening
				  							</br>perusahaan.
				  						</li>
				  						<li>Penjual berhak membebankan denda bunga kepada Pembeli atas pembayaran yang di lakukan melebihi tanggal
				  							</br>jatuh tempo.
				  						</li>
				  						<li>Pengembalian atau claim atas mutu produk yang di beli harus di sampaikan secara tertulis dan tidak melebihi 30 hari.</li>
				  						<li>Masa berlaku order ini sesuai dengan tanggal valid yang tercantum di dalamnya atau selambat-lambatnya 2(dua) bulan 
				  							</br>dari tanggal di terbitkan, kecuali ada persetujuan dan selisih harga akan kami bebankan. Atau penjual dapat
											</br>memilih untuk membatalkan penjualan.
				  						</li>
				  						<li>Jika ada perselisihan yang timbul, akan tunduk pada yurisdiksi eksklusif pengadilan Indonesia.</li>
				  						<li><spring:message code="salesorder.contract.payment"/>
				  							<div>&nbsp;</div>
					  						<table border="0" cellpadding="1" cellspacing="0">
					  						<tr style="font-weight: bold;">
					  							<td style="width: 50px">&nbsp;</td>
					  							<td>
					  							PT SAN STAR MANUNGGAL</br>
					  							OCBC NISP</br>
					  							ASIA AFRIKA - BANDUNG</br>
					  							<strong style="font-size: 11px;">060800006227</strong>
					  							</td>
					  							<td style="width: 50px" align="center"><spring:message code="notif.or"/></td>
					  							<td>
					  							PT SAN STAR MANUNGGAL</br>
					  							BANK CENTRAL ASIA</br>
					  							ASIA AFRIKA - BANDUNG</br>
					  							<strong style="font-size: 11px;">0089908088</strong>
					  							</td>
				  							</tr>
				  							</table>
				  						</li>
				  					</ul>
				  				</td>
				  			</tr>
				  			<tr>
				  				<td width="20%" align="center" valign="top"></td>
					  			<td width="20%" align="center" valign="top">
					  				<table border="0" cellpadding="0" cellspacing="0" width="100%">
				  					<tr>
				  						<td align="center" colspan="3"><spring:message code="salesorder.contract.buyer"/></td>
				  					</tr>
				  					<tr colspan="3" style="height: 100px">
				  						<td align="center" colspan="3">&nbsp;</td>
				  					</tr>
									<tr style="text-transform: uppercase;font-weight: bold;">
										<td style="width: 1px;">(</td>
										<td align="center">${salesOrder_edit.customer.salutation}&nbsp;${salesOrder_edit.customer.fullName}</td>
										<td style="width: 1px;">)</td>
									</tr>
									</table>
								</td>
				  				<td width="20%" align="center" valign="top"></td>
					  			<td width="20%" align="center" valign="top">
					  				<table border="0" cellpadding="0" cellspacing="0" width="100%">
				  					<tr>
				  						<td align="center" colspan="3"><spring:message code="salesorder.contract.seller"/></td>
				  					</tr>
				  					<tr colspan="3" style="height: 100px">
				  						<td align="center" colspan="3">&nbsp;</td>
				  					</tr>
									<tr style="text-transform: uppercase;font-weight: bold;">
										<td style="width: 1px;">(</td>
										<td align="center">&nbsp;${salesOrder_edit.organization.salutation}&nbsp;${salesOrder_edit.organization.fullName}&nbsp;</td>
										<td style="width: 1px;">)</td>
									</tr>
									</table>
								</td>
				  				<td width=" 20%" align="center" valign="top"></td>
							</tr>
				  			<tr>
								<td colspan="5">&nbsp;</td>
							</tr>
				  			<tr style="overflow: hidden;">
				  				<td colspan="5" nowrap="nowrap">
					  				Harap segera kirim kembali kontrak penjualan yang sudah di tandatangani dan di cap perusahaan melalui email atau
					  				</br>Faks ke kantor perwakilan kami sebagai bukti persetujuan. Jika hal di atas tidak terpenuhi maka penjual dapat memilih
					  				</br>untuk membatalkan penjualan.
				  				</td>
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
