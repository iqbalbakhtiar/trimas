<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/billingview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
	<a class="item-button-print"  href="<c:url value='/page/billingprintoption.htm?id=${billing_form.billing.id}'/>"><span><spring:message code="sirius.print"/></span></a>
</div>

<div class="main-box">
<sesform:form id="addForm" name="addForm" method="post" modelAttribute="billing_form" enctype="multipart/form-data">
	<table width="100%" border="0">
		<tr>
			<td width="60%" valign="top">
				<table style="border:none" width="100%">
				<tr>
					<td width="34%" align="right"><spring:message code="sirius.id"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><form:input class="inputbox input-disabled" path="code" disabled="true"/></td>
					<td width="1%"><form:errors path="code"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="organization"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="org" path="organization" cssClass="combobox-ext input-disabled" disabled="true">
							<c:if test='${not empty billing_form.organization}'>
								<form:option value='${billing_form.organization.id}' label='${billing_form.organization.fullName}'/>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="billing.date"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="date" name="date" size="10" class="input-disabled" disabled value="<fmt:formatDate value='${billing_form.date}' pattern='dd-MM-yyyy'/>"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="customer"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="customer" path="customer" cssClass="combobox-ext input-disabled" disabled="true">
							<c:if test='${not empty billing_form.customer}'>
								<form:option value="${billing_form.customer.id}">${billing_form.customer.code} ${billing_form.customer.fullName}</form:option>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="salesorder.shipping.name"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="shippingAddress" path="shippingAddress" cssClass="combobox-ext input-disabled" disabled="true">
							<c:if test='${not empty billing_form.shippingAddress}'>
								<form:option value="${billing_form.shippingAddress.id}">${billing_form.shippingAddress.addressName}</form:option>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="salesorder.tax.type"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="tax" path="tax" cssClass="input-disabled" disabled="true">
							<c:if test='${not empty billing_form.tax}'>
								<option value="${billing_form.tax.id}" data-taxrate="${billing_form.tax.taxRate}">${billing_form.tax.taxName}</option>
							</c:if>
						</form:select>
						<spring:message code="billing.rate.percent"/>
						<input size="7" id="taxRate" class="input-disabled" disabled value="<fmt:formatNumber value='${billing_form.tax.taxRate}' pattern=',##0.00'/>"/>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="billing.credit.term"/></td>
					<td width="1%" align="center">:</td>
					<td><input size="7" id="taxRate" class="input-disabled" disabled value="${billing_form.term}"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="billing.duedate"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="dueDate" name="dueDate" size="10" class="input-disabled" disabled value="<fmt:formatDate value='${billing_form.dueDate}' pattern='dd-MM-yyyy'/>"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="billing.paiddate"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="paidDate" name="paidDate" size="10" class="input-disabled" disabled value="<fmt:formatDate value='${billing_form.paidDate}' pattern='dd-MM-yyyy'/>"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="billing.invoice.tax.no"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:input size="7" id="invoiceTaxHeader" path="billing.invoiceTaxHeader" />
						<form:input size="28" id="invoiceTaxNo" path="billing.invoiceTaxNo" />
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="sirius.note"/></td>
					<td width="1%" align="center">:</td>
					<td><form:textarea path="billing.note" rows="6" cols="45"/></td>
					<td>&nbsp;</td>
				</tr>
				</table>
			</td>
			
			<td width="40%" valign="top">
				<table width="100%" style="border: none">
					<tr>
						<td>
							<fieldset>
								<legend><strong><spring:message code="billing.receiptinformation"/></strong></legend>
								<table width="100%" style="border: none">
									<tr>
										<td colspan="2" align="right">
											<c:if test="${billing_edit.billing.financialStatus eq 'UNPAID'}"><h2 style="color: red;"><c:out value="${billing_edit.billing.financialStatus.formattedName}"/></h2></c:if>
											<c:if test="${billing_edit.billing.financialStatus eq 'PAID'}"><h2 style="color: green;"><c:out value="${billing_edit.billing.financialStatus.formattedName}"/></h2></c:if>
										</td>
									</tr>
									<tr>
										<td width="80%" align="right"><spring:message code="billing.unpaidamount"/>:&nbsp;</td>
										<td width="20%">
											<input id="unpaid" value="<fmt:formatNumber value='${billing_edit.billing.unpaid}' pattern=',##0.00'/>"
															   class="number-disabled" readonly="readonly" size="20" disabled/>
										</td>
									</tr>
									<tr>
										<td width="80%" align="right" valign="top"><spring:message code="receipt.references"/>:&nbsp;</td>
										<td width="20%" align="right">
											<c:forEach items="${billing_edit.billing.receipts}" var="receipt" varStatus="status">
												<a href="<c:url value='/page/receiptpreedit.htm?id=${receipt.receipt.id}'/>"><c:out value="${receipt.receipt.code}"></c:out></a>
												<br>
											</c:forEach>
										</td>
									</tr>
									<tr>
										<td width="80%" align="right" valign="top"><spring:message code="billing.creditmemo.reference"/>:&nbsp;</td>
										<td width="20%" align="right"></td>
									</tr>
								</table>
							</fieldset>
						</td>
					</tr>
					<tr>
						<td>
							<fieldset>
								<legend><strong><spring:message code="billing.receiptinformation"/></strong></legend>
								<table width="100%" style="border: none">
									<tr>
										<td width="80%" align="right"><spring:message code="billing.total.line.amount"/>:&nbsp;</td>
										<td width="20%">
											<input id="unpaid" value="<fmt:formatNumber value='${billing_edit.totalLineAmount}' pattern=',##0.00'/>"
												   class="number-disabled" readonly="readonly" size="20" disabled/>
										</td>
									</tr>
									<tr>
										<td width="80%" align="right"><spring:message code="billing.discount"/>:&nbsp;</td>
										<td width="20%">
											<input id="discount" value="<fmt:formatNumber value='${billing_edit.totalDiscountAmount}' pattern=',##0.00'/>"
												   class="number-disabled" readonly="readonly" size="20" disabled/>
										</td>
									</tr>
									<tr>
										<td width="80%" align="right"><spring:message code="billing.afterdiscount"/>:&nbsp;</td>
										<td width="20%">
											<input id="afterDiscount" value="<fmt:formatNumber value='${billing_edit.totalAfterDiscount}' pattern=',##0.00'/>"
												   class="number-disabled" readonly="readonly" size="20" disabled/>
										</td>
									</tr>
									<tr>
										<td width="80%" align="right"><spring:message code="tax.amount"/>:&nbsp;</td>
										<td width="20%">
											<input id="taxAmount" value="<fmt:formatNumber value='${billing_edit.taxAmount}' pattern=',##0.00'/>"
												   class="number-disabled" readonly="readonly" size="20" disabled/>
										</td>
									</tr>
									<tr>
										<td width="80%" align="right"><spring:message code="billing.totalafter.tax"/>:&nbsp;</td>
										<td width="20%">
											<input id="totalAfterTax" value="<fmt:formatNumber value='${billing_edit.totalAfterTax}' pattern=',##0.00'/>"
												   class="number-disabled" readonly="readonly" size="20" disabled/>
										</td>
									</tr>
									<tr>
										<td width="80%" align="right"><spring:message code="manualbilling.creditmemo"/>:&nbsp;</td>
										<td width="20%">
											<input id="totalCreditMemo" value="<fmt:formatNumber value='${billing_edit.totalCreditMemo}' pattern=',##0.00'/>"
												   class="number-disabled" readonly="readonly" size="20" disabled/>
										</td>
									</tr>
									<tr>
										<td width="80%" align="right"><strong><spring:message code="billing.total.amount"/></strong>:&nbsp;</td>
										<td width="20%">
											<input id="totalBillingAmount" value="<fmt:formatNumber value='${billing_edit.totalBillingAmount}' pattern=',##0.00'/>"
												   class="number-disabled" readonly="readonly" size="20" disabled/>
										</td>
									</tr>
								</table>
							</fieldset>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<br/>
	<div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 400px;">
  		<div id="billingAddressPane" dojoType="ContentPane" label="<spring:message code='billing.address'/>" class="tab-pages" refreshOnShow="true" selected="true">
  			<table width="100%">
  				<tr>
					<td align="right"><spring:message code="billing.address.name"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="billingAddress" path="billingAddress" cssClass="combobox-ext input-disabled" disabled="true" onchange="updateShippingAddress(this)">
							<c:if test='${not empty billing_form.billingAddress}'>
								<form:option value="${billing_form.billingAddress.id}">${billing_form.billingAddress.addressName}</form:option>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="postaladdress.detail"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="billingAddressDetail" class="inputbox input-disabled" disabled value="${billing_form.billingAddress.address}"/></td>
				</tr>
  				<tr>
					<td align="right"><spring:message code="postaladdress.postalcode"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="billingAddressPostalCode" class="inputbox input-disabled" disabled value="${billing_form.billingAddress.postalCode}"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="postaladdress.city"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="billingAddressCity" class="inputbox input-disabled" disabled value="${billing_form.billingAddress.city.name}"/></td>
				</tr>
  			</table>
		</div>

		<div id="productLineItem" dojoType="ContentPane" label="<spring:message code='salesorder.lineitem'/>" class="tab-pages" refreshOnShow="true" selected="true">
  			<div class="toolbar-clean">
		    	<table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
			    	<thead>
				    	<tr>
				    		<th width="1%" nowrap="nowrap"></th>
							<th width="12%" nowrap="nowrap"><spring:message code="product"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="deliveryorder.accepted.qty"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="sirius.unitprice"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="salesorder.disc"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="sirius.amount"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="salesorder.total.disc"/></th>
							<th width="30%" nowrap="nowrap"><spring:message code="sirius.total"/> <spring:message code="sirius.amount"/></th>
						</tr>
					</thead>
					<tbody id="lineItem">
					<c:forEach items="${billing_form.billing.items}" var="item" varStatus="idx">
						<c:if test="${item.billingReferenceItem.quantity > 0}">
						<tr>
							<td></td>
							<td><input size="30" value="${item.billingReferenceItem.product.name}" class="input-disabled productInput" disabled/></td>
							<td><input size="10" value="${item.billingReferenceItem.quantity}" class="input-disabled input-decimal" disabled/></td>
							<td><input size="5" value="${item.billingReferenceItem.product.unitOfMeasure.measureId}" class="input-disabled" disabled/></td>
							<td><input size="12" value="<fmt:formatNumber value='${item.billingReferenceItem.money.amount}' pattern=',##0.0000'/>" class="input-disabled input-decimal" disabled/></td>
							<td><input size="10" value="${item.billingReferenceItem.discount}" class="input-disabled input-decimal" disabled/></td>
							<td><input size="15" type="text" value="<fmt:formatNumber value='${item.billingReferenceItem.subtotal}' pattern=',##0.00'/>" class="input-disabled input-decimal" disabled/></td>
							<td><input size="15" type="text" value="<fmt:formatNumber value='${item.billingReferenceItem.totalDiscount}' pattern=',##0.00'/>" class="input-disabled input-decimal" disabled/></td>
							<td><input size="15" type="text" value="<fmt:formatNumber value='${item.billingReferenceItem.totalAfterDiscount}' pattern=',##0.00'/>" class="input-disabled input-decimal" disabled/></td>
						</tr>
						</c:if>
					</c:forEach>
					</tbody>
					<tfoot>
						<tr class="end-table"><td colspan="9">&nbsp;</td></tr>
					</tfoot>
				</table>
			</div>
		</div>
		<div id="collectingStatusPane" dojoType="ContentPane" label="<spring:message code='billing.collectingstatus'/>" class="tab-pages" refreshOnShow="true" selected="true">
			<table width="100%">
				<tr>
					<td width="41%" nowrap="nowrap" align="right">Collecting Status :</td>
					<td width="59%">
						<form:select path='billing.collectingStatus.status'>
							<form:option value='CREATED' label='Created'/>
							<form:option value='FINANCE' label='On Finance'/>
							<form:option value='COLLECTOR' label='On Collector'/>
							<form:option value='ACCEPTED' label='Accepted'/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td width="41%" nowrap="nowrap" align="right">Billing On Finance Date :</td>
					<td width="59%"><input id="onFinanceDate" name="billing.collectingStatus.onFinanceDate" value="<fmt:formatDate value='${billing_form.billing.collectingStatus.onFinanceDate}' pattern='dd-MM-yyyy'/>" size="10" class="datepicker"/></td>
				</tr>
				<tr>
					<td width="41%" nowrap="nowrap" align="right">Collector Name :</td>
					<td width="59%">
						<form:select id="collector" path='billing.collectingStatus.collector' cssClass='combobox-ext'>
							<c:if test='${not empty billing_form.billing.collectingStatus.collector}'>
								<form:option value='${billing_form.billing.collectingStatus.collector.id}' label='${billing_form.billing.collectingStatus.collector.fullName}'/>
							</c:if>
						</form:select>
						<a class="item-popup" onclick="openCollector()"  title="Collector" />
					</td>
				</tr>
				<tr>
					<td width="41%" nowrap="nowrap" align="right">Billing On Collector Date :</td>
					<td width="59%"><input id="collectingDate" name="billing.collectingStatus.collectingDate" value="<fmt:formatDate value='${billing_form.billing.collectingStatus.collectingDate}' pattern='dd-MM-yyyy'/>" size="10" class="datepicker"/></td>
				</tr>
				<tr>
					<td width="41%" nowrap="nowrap" align="right">Billing Accepted Date :</td>
					<td width="59%"><input id="acceptanceDate" name="billing.collectingStatus.acceptanceDate" value="<fmt:formatDate value='${billing_form.billing.collectingStatus.acceptanceDate}' pattern='dd-MM-yyyy'/>" size="10" class="datepicker"/></td>
				</tr>
			</table>
		</div>
 	</div>
</sesform:form>
</div>
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${billing_form.createdBy.fullName}'/> (<fmt:formatDate value='${billing_form.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${billing_form.updatedBy.fullName}'/> (<fmt:formatDate value='${billing_form.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
$(function(){
	var $index = 0; // For Line Item Index

	// updateDisplay();
	
	$('.item-button-save').click(function(){
		if(validateForm()) {
			save();
		}
	});
});

// TODO: Perlu dicek field yang ingin divalidasi
function validateForm() {
    // Validasi organisasi (sudah ada sebelumnya)
    var organization = $('#org').val();
    if (organization == null || organization === "") {
        alert('<spring:message code="sirius.organization"/> <spring:message code="notif.empty"/> !');
        return false;
    }

    // Validasi date
    var date = $('#date').val();
    if (date == null || date === "") {
        alert('<spring:message code="salesorder.date"/> <spring:message code="notif.empty"/> !');
        return false;
    }

    // Validasi customer
    var customer = $('#customer').val();
    if (customer == null || customer === "") {
        alert('<spring:message code="customer"/> <spring:message code="notif.empty"/> !');
        return false;
    }

    // Jika semua validasi lolos
    return true;
}

function save() {
	$.ajax({
		url:"<c:url value='/page/billingedit.htm'/>",
		data:$('#addForm').serialize(),
		type : 'POST',
		dataType : 'json',
		beforeSend:function()
		{
			$dialog.empty();
			$dialog.html('<spring:message code="notif.saving"/>');
			$dialog.dialog('open');
		},
		success : function(json) {
			if(json)
			{
				if(json.status === 'OK')
				{
					$dialog.dialog('close');
					window.location="<c:url value='/page/billingpreedit.htm?id='/>"+json.id;
				}
				else
				{
					$dialog.empty();
					$dialog.html('<spring:message code="notif.profailed"/> :<br/>'+json.message);
				}
			}
		}
	});
}

/*
 TODO: Master data Collector
*/
function openCollector() {
	alert("Master data Collector not implemented yet")
}
</script>