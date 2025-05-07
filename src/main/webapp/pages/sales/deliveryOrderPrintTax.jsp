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
	<title><spring:message code="deliveryorder"/></title>
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
								<a class="item-button-back" href="<c:url value="/page/deliveryorderpreedit.htm?id=${deliveryOrder_edit.id}"/>"><span><spring:message code="sirius.back"/></span></a>
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
					  		</table>
					  		<table cellpadding="1" cellspacing="1" width="100%" align="center">
							<tr>
								<td valign="top" align="center" colspan="2"><h2>SURAT JALAN</h2></td>
							</tr>
							<tr>
								<td style="width: 50%;">
									<table>
										<tr style="font-weight: bold;font-size: large;">
											<td valign="top" nowrap="nowrap" style="width: 15px;">No </td>
											<td nowrap="nowrap" colspan="2">: <c:out value="${fn:replace(deliveryOrder_edit.code, 'SJ', '')}" /></td>
										</tr>
										<tr>
											<td valign="top" nowrap="nowrap" colspan="3">
											Bersama ini kendaraan No <strong style="text-transform: uppercase;">${deliveryOrder_edit.plateNumber}</strong></br>
											Kami kirimkan barang tersebut dibawah ini</br>
											Harap diterima dengan baik.
											</td>
										</tr>
									</table>
								</td>
								<td style="width: 50%;">
									<table cellpadding="1" cellspacing="1" width="100%">
										<tr>
											<td valign="top" colspan="2">Sumedang, <fmt:formatDate value='${deliveryOrder_edit.date}' pattern='dd MMMM yyyy'/></td>
										</tr>
										<tr>
											<td valign="top" colspan="2">Kepada Yth.</td>
										</tr>
										<tr>
											<td style="width: 50px;">&nbsp;</td>
											<td class="word-wrap">
												<strong><c:out value='${deliveryOrder_edit.customer.salutation}'/> <c:out value='${deliveryOrder_edit.customer.fullName}' /></strong>
												</br>
												<c:out value='${deliveryOrder_edit.shippingAddress.address}'/></br>
												<c:out value='${deliveryOrder_edit.shippingAddress.city.name}'/>
								    		</td>
										</tr>
									</table>
								</td>
							</tr>
							</table>
					  		<br/>
					  		<table border="0" cellpadding="1" cellspacing="1" width="100%" align="center" style="table-layout:fixed;border-width: 1px medium medium; border-style: solid none none; border-color: black -moz-use-text-color -moz-use-text-color;">
					  			<tr>
					  				<th style="border-bottom:1px solid black;border-left:1px solid black;width: 20%"><spring:message code="sirius.qty"/></th>
					  				<th style="border-bottom:1px solid black;border-left:1px solid black;width: 35%"><spring:message code="product"/></th>
					  				<th style="border-bottom:1px solid black;border-right:1px solid black;border-left:1px solid black;width: 30%"><spring:message code="sirius.note2"/></th>
					  			</tr>
								<c:forEach items="${deliveryOrder_edit.items}" var="item" varStatus="idx">
									<c:if test="${item.deliveryItemType eq 'BASE'}">
									<c:set var="bale" value="${item.quantity / 181.44}"/>
									<c:set var="count" value="${fn:length(item.serials)}"/>
						  			<tr>
	                               		<c:set var="pattern" value=",##0.00" />
						  				<td align="right" style="border-bottom:1px solid black;border-left:1px solid black;text-transform: uppercase;" valign="top">
							  				<table border="0" cellpadding="1" cellspacing="5" width="100%">
							  				<tr>
							  					<td align="right" style="width: 50%"><fmt:formatNumber value='${count}' pattern=',##0.00'/></td>
							  					<td style="width: 50%">Karung</td>
						  					</tr>
						  					<tr>
							  					<td align="right"><fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/></td>
							  					<td>${item.product.unitOfMeasure.measureId}</td>
						  					</tr>
						  					<tr>
							  					<td align="right"><fmt:formatNumber value='${bale}' pattern=',##0.00'/></td>
							  					<td>Bale</td>
						  					</tr>
							  				</table>
						  				</td>
						  				<td style="border-bottom:1px solid black;border-left:1px solid black;text-transform: uppercase;" valign="top" nowrap="nowrap">
						  					<table border="0" cellpadding="1" cellspacing="5" width="100%">
							  				<tr>
							  					<td align="right" style="width: 20%;" nowrap="nowrap"><spring:message code="salesorder.contract.no"/></td>
							  					<td style="width: 80%"nowrap="nowrap">: ${deliveryOrder_edit.referenceCode}</td>
						  					</tr>
						  					<tr>
							  					<td align="right"><spring:message code="product"/></td>
							  					<td>: ${item.product.name} ${deliveryOrder_edit.referenceLot}</td>
						  					</tr>
							  				</table>
						  				</td>
						  				<td style="border-bottom:1px solid black;border-right:1px solid black;border-left:1px solid black;text-transform: uppercase;" valign="top">
						  					<table border="0" cellpadding="1" cellspacing="5" width="100%" style="table-layout: fixed;">
						  					<tr>
							  					<td class="word-wrap">${item.note}</td>
						  					</tr>
							  				</table>
						  				</td>
						  			</tr>
						  			</c:if>
					  			</c:forEach>
					  		</table>
					  		<br/>
					  		<table border="0" cellpadding="1" cellspacing="0" width="100%" align="center" style="table-layout: fixed; width: 100%">
				  			<tr>
				  				<td width="20%" align="center" valign="top">
					  				<table border="0" cellpadding="0" cellspacing="0" width="100%">
				  					<tr>
				  						<td align="center" colspan="3">Penerima,</td>
				  					</tr>
				  					<tr colspan="3" style="height: 100px">
				  						<td align="center" colspan="3">&nbsp;</td>
				  					</tr>
									<tr style="text-transform: uppercase;font-weight: bold;">
										<td style="width: 1px;">(</td>
										<td align="center">&nbsp;</td>
										<td style="width: 1px;">)</td>
									</tr>
									</table>
				  				</td>
					  			<td width="20%" align="center" valign="top">
					  				<table border="0" cellpadding="0" cellspacing="0" width="100%">
				  					<tr>
				  						<td align="center" colspan="3">Gudang,</td>
				  					</tr>
				  					<tr style="height: 100px">
				  						<td align="center" colspan="3">&nbsp;</td>
				  					</tr>
									<tr style="text-transform: uppercase;font-weight: bold;">
										<td style="width: 1px;">(</td>
										<td align="center">&nbsp;</td>
										<td style="width: 1px;">)</td>
									</tr>
									</table>
								</td>
				  				<td width="20%" align="center" valign="top">
					  				<table border="0" cellpadding="0" cellspacing="0" width="100%">
				  					<tr>
				  						<td align="center" colspan="3">Produksi,</td>
				  					</tr>
				  					<tr style="height: 100px">
				  						<td align="center" colspan="3">&nbsp;</td>
				  					</tr>
									<tr style="text-transform: uppercase;font-weight: bold;">
										<td style="width: 1px;">(</td>
										<td align="center">&nbsp;</td>
										<td style="width: 1px;">)</td>
									</tr>
									</table>
				  				</td>
					  			<td width="20%" align="center" valign="top">
					  				<table border="0" cellpadding="0" cellspacing="0" width="100%">
				  					<tr>
				  						<td align="center" colspan="3">Penjualan,</td>
				  					</tr>
				  					<tr style="height: 100px">
				  						<td align="center" colspan="3">&nbsp;</td>
				  					</tr>
									<tr style="text-transform: uppercase;font-weight: bold;">
										<td style="width: 1px;">(</td>
										<td align="center">&nbsp;</td>
										<td style="width: 1px;">)</td>
									</tr>
									</table>
								</td>
				  				<td width=" 20%" align="center" valign="top">
					  				<table border="0" cellpadding="0" cellspacing="0" width="100%">
				  					<tr>
				  						<td align="center" colspan="3">Mengetahui,</td>
				  					</tr>
				  					<tr style="height: 100px">
				  						<td align="center" colspan="3">&nbsp;</td>
				  					</tr>
									<tr style="text-transform: uppercase;font-weight: bold;">
										<td style="width: 1px;">(</td>
										<td align="center">&nbsp;</td>
										<td style="width: 1px;">)</td>
									</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="5">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="5"><strong>*MOHON UNTUK TTD PENERIMA DILENGKAPI NAMA LENGKAP DAN CAP PERUSAHAAN</strong></td>
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
