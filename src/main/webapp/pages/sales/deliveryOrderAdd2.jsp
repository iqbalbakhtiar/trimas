<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/deliveryorderview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-rprev" href="<c:url value='/page/deliveryorderpreadd1.htm'/>"><span><spring:message code="sirius.back"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
<sesform:form id="addForm" name="addForm" method="post" modelAttribute="deliveryOrder_form" enctype="multipart/form-data">
	<table width="100%" border="0">
		<tr>
			<td width="40%">
				<table style="border:none" width="100%">
				<tr>
					<td width="34%" align="right"><spring:message code="sirius.id"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input class="inputbox input-disabled" value="Auto Number" disabled/></td>
					<td width="1%"><form:errors path="code"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="organization"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="org" path="organization" cssClass="combobox input-disabled" disabled="true">
							<c:if test='${not empty deliveryOrder_form.salesOrder.organization}'>
								<form:option value='${deliveryOrder_form.salesOrder.organization.id}' label='${deliveryOrder_form.salesOrder.organization.fullName}'/>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryorder.do.date"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="date" name="date" class="datepicker" value=""/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryorder.salesorder.no"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="salesOrderNo" name="salesOrderNo" class="inputbox input-disabled" value="${deliveryOrder_form.salesOrder.code}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="customer"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="customerName" name="customerName" class="inputbox input-disabled" value="${deliveryOrder_form.salesOrder.customer.fullName}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="salesorder.shipping.name"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="addressName" name="addressName" class="inputbox input-disabled" value="${deliveryOrder_form.salesOrder.shippingAddress.addressName}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryorder.shippingaddress.detail"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="addressDetail" name="addressDetail" class="inputbox input-disabled" value="${deliveryOrder_form.salesOrder.shippingAddress.address}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="postaladdress.postalcode"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="postalCode" name="postalCode" class="inputbox input-disabled" value="${deliveryOrder_form.salesOrder.shippingAddress.postalCode}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="postaladdress.city"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="city" name="city" class="inputbox input-disabled" value="${deliveryOrder_form.salesOrder.shippingAddress.city.name}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryorder.issue.facility"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="facility" path="facility" cssClass="combobox">
						</form:select>
						<a class="item-popup" onclick="openpopup('<c:url value='/page/popupfacilityview.htm?target=facility'/>');" title="Facility"></a>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="sirius.note"/></td>
					<td width="1%" align="center">:</td>
					<td><form:textarea path="note" rows="6" cols="45"/></td>
					<td>&nbsp;</td>
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
							<th width="20%" nowrap="nowrap"><spring:message code="product.onhand"/></th>
							<th width="5%" ><spring:message code="deliveryorder.soquantity"/></th>
							<th width="5%" ><spring:message code="deliveryorder.doquantity"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
							<th width="20%" nowrap="nowrap"><spring:message code="container"/></th>
							<th width="60%" nowrap="nowrap"><spring:message code="deliveryorder.note"/></th>
						</tr>
					</thead>
					<tbody id="lineItem">
					<c:forEach items="${deliveryOrder_form.items}" var="item" varStatus="idx">
						<tr>
							<td></td>
							<td>
								<select id="product[${idx.index}]" class="combobox-ext input-disabled productInput"
										name="items[${idx.index}].product" index="${idx.index}" next="product" disabled>
										<option value="${item.salesReferenceItem.product.id}">${item.salesReferenceItem.product.name}</option>
								</select>
							</td>
							<td>
								<input id="onhand[${idx.index}]" size="10" value="0.00" class="input-disabled input-decimal"
									   name="items[${idx.index}].onhand" index="${idx.index}" next="onhand" disabled/>
							</td>
							<td>
								<input id="soquantity[${idx.index}]" size="10" value="${item.salesReferenceItem.quantity}" class="input-disabled input-decimal"
									   name="items[${idx.index}].soquantity" index="${idx.index}" next="soquantity" disabled/>
							</td>
							<td>
								<input id="delivered[${idx.index}]" size="10" value="0.00" class="input-number"
									   name="items[${idx.index}].delivered" index="${idx.index}" next="delivered"/>
							</td>
							<td>
								<input id="uom[${idx.index}]" size="6" value="${item.salesReferenceItem.product.unitOfMeasure.measureId}" class="input-disabled"
									   name="items[${idx.index}].uom" index="${idx.index}" next="uom" disabled/>
							</td>
							<td>
								<select id="container[${idx.index}]" name="items.[${idx.index}].container" index="${idx.index}"
										onchange="updateOnHand(this, ${idx.index});" next="container" class="combobox">
								</select>
								<a class="item-popup" onclick="openContainer(this, ${idx.index});" title="Container"></a>
							</td>
							<td>
								<input id="note[${idx.index}]" type="text" size="60" name="items[${idx.index}].note"
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
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
$(function(){
	$('.item-button-save').click(function(){
		if(validateForm()) {
			save();
		}
	});
});

function validateForm() {
	// Validasi organisasi
	var organization = $('#org').val();
	if (organization == null || organization === "") {
		alert('<spring:message code="sirius.organization"/> <spring:message code="notif.empty"/> !');
		return false;
	}

	// Validasi date
	var date = $('#date').val();
	if (date == null || date === "") {
		alert('<spring:message code="deliveryorder.do.date"/> <spring:message code="notif.empty"/> !');
		return false;
	}

	// Validasi facility
	var facility = $('#facility').val();
	if (facility == null || facility === "") {
		alert('<spring:message code="facility"/> <spring:message code="notif.empty"/> !');
		return false;
	}

	// Validasi Line Items
	var isValid = true;
	$('#lineItem tr').each(function(index){
		var $row = $(this);

		// Ambil input product
		var $productInput = $row.find('select[id^="product["]');
		var productName = $productInput.text();

		// Ambil input quantity dan soquantity
		var $quantityInput = $row.find('input[id^="delivered["]');
		var $soQuantityInput = $row.find('input[id^="soquantity["]');

		// Ambil nilai dari input
		var quantityStr = $quantityInput.val();
		var soQuantityStr = $soQuantityInput.val();

		// Validasi bahwa quantity tidak kosong
		if (quantityStr == null || quantityStr.trim() === '') {
			alert('<strong>' + productName + '</strong> - <spring:message code="deliveryorder.doquantity"/> <spring:message code="notif.empty"/>');
			isValid = false;
			return false;
		}

		// Konversi nilai ke angka
		var quantity = parseFloat(quantityStr.replace(/,/g, ''));
		var soQuantity = parseFloat(soQuantityStr.replace(/,/g, ''));

		// Validasi bahwa quantity adalah angka dan lebih besar dari 0
		if (isNaN(quantity) || quantity <= 0) {
			alert('<strong>' + productName + '</strong> - <spring:message code="deliveryorder.doquantity"/> <spring:message code="notif.greater.zero"/>');
			isValid = false;
			return false;
		}

		// Validasi bahwa doQuantity tidak lebih besar dari soQuantity
		if (quantity > soQuantity) {
			alert('<strong>' + productName + '</strong> - <spring:message code="deliveryorder.doquantity"/> <spring:message code="notif.greater"/> <spring:message code="deliveryorder.soquantity"/>');
			isValid = false;
			return false;
		}

		// Cek Container
		var $containerSelect = $row.find('select[id^="container["]');
		var containerValue = $containerSelect.val();

		// Validasi bahwa container tidak kosong
		if (containerValue == null || containerValue.trim() === "") {
			alert('<strong>' + productName + '</strong> - <spring:message code="container"/> <spring:message code="notif.empty"/>');
			$containerSelect.focus();
			isValid = false;
			return false;
		}


		var $onhandInput = $row.find('input[id^="onhand["]');
		var $doQuantityInput = $row.find('input[id^="delivered["]');

		var onhandStr = $onhandInput.val().replace(/,/g, '');

		var onhand = parseFloat(onhandStr) || 0;

		if (quantity > onhand) {
			alert('<spring:message code="deliveryorder.doquantity"/> <spring:message code="notif.greater"/> <spring:message code="product.onhand"/>');
			$doQuantityInput.focus();
			isValid = false;
			return false;
		}
	});

	if (!isValid) {
		return false;
	}

	// Jika semua validasi lolos
	return true;
}

function save() {
	$.ajax({
		url:"<c:url value='/page/deliveryorderadd.htm'/>",
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
					window.location="<c:url value='/page/deliveryorderpreedit.htm?id='/>"+json.data.id;
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

function openContainer(element, idx) {
	if (!$('#facility').val()) {
		alert('<spring:message code="notif.select1"/> <spring:message code="facility"/> <spring:message code="notif.select2"/> !!!');
		return;
	}

	const facilityId = $('#facility').val();
	const baseUrl = '<c:url value="/page/popupcontainerview.htm"/>';
	const params = {
		target: 'container[' + idx + ']',
		index: idx,
		facility: facilityId
	}

	openpopup(buildUrl(baseUrl, params));
}

function updateOnHand(element, index) {
	const productId = $('#product\\['+index+'\\]').val();
	const containerId = $('#container\\['+index+'\\]').val();

	$('#onhand\\['+index+'\\]').val(0.00);
	if(productId) {
		$.ajax({
			url:"<c:url value='/page/onhandquantityviewonhandjson.htm'/>",
			data:{product:productId, container:containerId},
			method : 'GET',
			dataType : 'json',
			success : function(json) {
				if(json)
				{
					if(json.status == 'OK')
						$('#onhand\\['+index+'\\]').val(parseFloat(json.onHand).numberFormat('#,##0.00'));
				}
			}
		});
	}
}
</script>