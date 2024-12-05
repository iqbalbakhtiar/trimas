<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/deliveryorderview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
	<a class="item-button-print"  href="<c:url value='/page/deliveryorderprint.htm?id=${deliveryOrder_form.deliveryOrder.id}'/>"><span><spring:message code="sirius.print"/></span></a>
	<c:if test="${deliveryOrder_form.status == 'SENT'}">
		<a class="item-button-print"  href="<c:url value='/page/deliveryorderinvoiceprint.htm?id=${deliveryOrder_form.deliveryOrder.id}'/>"><span><spring:message code="sirius.print.invoice"/></span></a>
	</c:if>
	<c:if test="${deliveryOrder_form.status != 'SENT' && deliveryOrder_form.status != 'DELIVERED'}">
		<a class="item-button-sent">
			<span><spring:message code="deliveryorder.sent"/></span>
		</a>
	</c:if>
</div>

<div class="main-box">
<sesform:form id="addForm" name="addForm" method="post" modelAttribute="deliveryOrder_form" enctype="multipart/form-data">
	<table width="100%" border="0">
		<tr>
			<td width="60%" valign="top">
				<table style="border:none" width="100%">
				<tr>
					<td width="34%" align="right">Sales Order ID</td>
					<td width="1%" align="center">:</td>
					<td width="64%"><form:input class="inputbox input-disabled" path="code" disabled="true"/></td>
					<td width="1%"><form:errors path="code"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="organization"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="org" path="organization" cssClass="combobox input-disabled" disabled="true">
							<c:if test='${not empty deliveryOrder_form.organization}'>
								<form:option value='${deliveryOrder_form.organization.id}' label='${deliveryOrder_form.organization.fullName}'/>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryorder.do.date"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<c:if test="${deliveryOrder_form.status != 'SENT'}">
							<input id="date" name="date" class="datepicker" value="<fmt:formatDate value='${deliveryOrder_form.date}' pattern='dd-MM-yyyy'/>"/>
						</c:if>
						<c:if test="${deliveryOrder_form.status == 'SENT'}">
							<input id="date" size="10" name="date" class="input-disabled" disabled value="<fmt:formatDate value='${deliveryOrder_form.date}' pattern='dd-MM-yyyy'/>"/>
						</c:if>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryorder.salesorder.no"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="salesOrderNo" name="salesOrderNo" class="inputbox input-disabled" value="${referenceCode}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="customer"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="customerName" name="customerName" class="inputbox input-disabled" value="${deliveryOrder_form.customer.fullName}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="salesorder.shipping.name"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="addressName" name="addressName" class="inputbox input-disabled" value="${deliveryOrder_form.shippingAddress.addressName}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryorder.shippingaddress.detail"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="addressDetail" name="addressDetail" class="inputbox input-disabled" value="${deliveryOrder_form.shippingAddress.address}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="postaladdress.postalcode"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="postalCode" name="postalCode" class="inputbox input-disabled" value="${deliveryOrder_form.shippingAddress.postalCode}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="postaladdress.city"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="city" name="city" class="inputbox input-disabled" value="${deliveryOrder_form.shippingAddress.city.name}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryorder.issue.facility"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="facility" path="facility" cssClass="combobox input-disabled" disabled="true">
							<c:if test='${not empty deliveryOrder_form.facility}'>
								<form:option value="${deliveryOrder_form.facility.id}">${deliveryOrder_form.facility.name}</form:option>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="sirius.note"/></td>
					<td width="1%" align="center">:</td>
					<td><form:textarea path="note" rows="6" cols="45" value="${deliveryOrder_form.note}"/></td>
					<td>&nbsp;</td>
				</tr>
				</table>
			</td>

			<td width="30%" valign="top">
				<table width="100%" style="border: none">
					<tr>
						<td>
							<fieldset>
								<legend><strong><spring:message code="sirius.status"/></strong></legend>
								<table width="100%" style="border: none">
									<tr>
										<td colspan="2" align="right">
											<spring:message code="deliveryorder.status"/>&nbsp;:&nbsp;<strong><c:out value="${deliveryOrder_form.status}"/></strong>
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
		<div id="productLineItem" dojoType="ContentPane" label="<spring:message code='salesorder.lineitem'/>" class="tab-pages" refreshOnShow="true" selected="true">
  			<div class="toolbar-clean">
		    	<table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
			    	<thead>
				    	<tr>
				    		<th width="1%" nowrap="nowrap">&nbsp;</th>
							<th width="20%" nowrap="nowrap"><spring:message code="product"/></th>
							<th width="5%" ><spring:message code="deliveryorder.soquantity"/></th>
							<th width="5%" ><spring:message code="deliveryorder.doquantity"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="container"/></th>
							<th width="60%" nowrap="nowrap"><spring:message code="deliveryorder.note"/></th>
						</tr>
					</thead>
					<tbody id="lineItem">
					<c:forEach items="${deliveryOrder_form.deliveryOrder.items}" var="item" varStatus="idx">
						<tr>
							<td></td>
							<td><input id="product[${idx.index}]" size="40" value="${item.salesReferenceItem.product.name}" class="input-disabled productInput"
									   name="items[${idx.index}].product" index="${idx.index}" next="product" disabled/>
							</td>
							<td>
								<input id="quantity[${idx.index}]" size="10" value="${item.salesReferenceItem.quantity}" class="input-disabled input-decimal"
									   name="items[${idx.index}].soquantity" index="${idx.index}" next="soquantity" disabled/>
							</td>
							<td>
								<input id="quantity[${idx.index}]" size="10" value="${item.salesReferenceItem.delivered}" class="input-disabled input-decimal"
									   name="items[${idx.index}].quantity" index="${idx.index}" next="quantity" disabled/>
							</td>
							<td>
								<input id="uom[${idx.index}]" size="6" value="${item.salesReferenceItem.product.unitOfMeasure.measureId}" class="input-disabled"
									   name="items[${idx.index}].uom" index="${idx.index}" next="uom" disabled/>
							</td>
							<td>
								<input id="uom[${idx.index}]" size="30" value="${item.container.name}" class="input-disabled"
									   name="items[${idx.index}].uom" index="${idx.index}" next="uom" disabled/>
							</td>
							<td>
								<input id="note[${idx.index}]" type="text" size="60" value="${item.note}" name="deliveryOrder.items[${idx.index}].note"
									   index="${idx.index}" next="note"/>
							</td>
						</tr>
					</c:forEach>
					</tbody>
					<tfoot>
						<tr class="end-table"><td colspan="10">&nbsp;</td></tr>
					</tfoot>
				</table>
			</div>
		</div>
 	</div>
</sesform:form>
</div>
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${deliveryOrder_form.createdBy.fullName}'/> (<fmt:formatDate value='${deliveryOrder_form.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${deliveryOrder_form.updatedBy.fullName}'/> (<fmt:formatDate value='${deliveryOrder_form.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
$(function(){
	$('.item-button-save').click(function(){
		if(validateForm()) {
			save();
		}
	});

	$('.item-button-sent').click(function(){
		sent();
	});
});

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

    // Validasi facility
    var facility = $('#facility').val();
    if (facility == null || facility === "") {
        alert('<spring:message code="facility"/> <spring:message code="notif.empty"/> !');
        return false;
    }

    return true;
}

function save() {
	$.ajax({
		url:"<c:url value='/page/deliveryorderedit.htm'/>",
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
					<%--window.location="<c:url value='/page/deliveryorderview.htm'/>";--%>
					// Or Can use This
					window.location="<c:url value='/page/deliveryorderpreedit.htm?id='/>"+json.id;
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

function sent() {
	$.ajax({
		url:"<c:url value='/page/deliveryordersent.htm?id=${deliveryOrder_form.deliveryOrder.id}'/>",
		// data:$('#addForm').serialize(),
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
					<%--window.location="<c:url value='/page/deliveryorderview.htm'/>";--%>
					// Or Can use This
					window.location="<c:url value='/page/deliveryorderpreedit.htm?id='/>"+json.id;
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

function updateDisplay() {
	// Update Tax
	var taxRate = Number.parse($('#tax option:selected').data('taxrate')) || 0;
	$('#taxRate').val(taxRate ? taxRate.toFixed(2) : '');

	// Inisialisasi total
	var totalSales = 0, totalDiscount = 0, totalBeforeTax = 0;

	// Fungsi helper untuk mendapatkan nilai numerik
	function getNumericValue($input) {
		return $input.val().toNumber() || 0;
	}

	// Update setiap line item
	$('#lineItem tr').each(function(){
		var $row = $(this);

		var qty = getNumericValue($row.find('input[id^="quantity["]'));
		var price = getNumericValue($row.find('input[id^="amount["]'));
		var disc = getNumericValue($row.find('input[id^="discount["]'));

		var amount = qty * price;
		var totalDisc = amount * (disc / 100);
		var totalAmount = amount - totalDisc;

		totalSales += amount;
		totalDiscount += totalDisc;
		totalBeforeTax += totalAmount;

		// Mengatur nilai terformat menggunakan numberFormat
		$row.find('input[id^="amountInput["]').val(amount.numberFormat('#,##0.00'));
		$row.find('input[id^="totalDisc["]').val(totalDisc.numberFormat('#,##0.00'));
		$row.find('input[id^="totalAmount["]').val(totalAmount.numberFormat('#,##0.00'));
	});

	// Menghitung totalTax dan totalTransaction
	var totalTax = totalBeforeTax * (taxRate / 100);
	var totalTransaction = totalBeforeTax + totalTax;

	// Memperbarui field recapitulation dengan nilai yang diformat
	$('#totalSales').val(totalSales.numberFormat('#,##0.00'));
	$('#totalDiscount').val(totalDiscount.numberFormat('#,##0.00'));
	$('#totalBeforeTax').val(totalBeforeTax.numberFormat('#,##0.00'));
	$('#totalTax').val(totalTax.numberFormat('#,##0.00'));
	$('#totalTransaction').val(totalTransaction.numberFormat('#,##0.00'));
}
</script>