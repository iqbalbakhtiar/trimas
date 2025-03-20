<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/deliveryorderview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-back" href="<c:url value='/page/deliveryorderpreadd1.htm'/>"><span><spring:message code="sirius.back"/></span></a>
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
							<c:if test='${not empty deliveryOrder_form.organization}'>
								<form:option value='${deliveryOrder_form.organization.id}' label='${deliveryOrder_form.organization.fullName}'/>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryorder.do.date"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="date" name="date" class="datepicker" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="customer"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%">
						<form:select id="customer" path="customer" cssClass="combobox-ext" onchange="updateShippingAddress(this)">
							<form:option value='${deliveryOrder_form.customer.id}' label='${deliveryOrder_form.customer.fullName}'/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryorder.issue.facility"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="facility" path="facility" cssClass="combobox">
								<form:option value='${deliveryOrder_form.facility.id}' label='${deliveryOrder_form.facility.name}'/>
						</form:select>
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
		<div id="address" dojoType="ContentPane" label="<spring:message code='postaladdress.detail'/>" class="tab-pages" refreshOnShow="true" selected="true">
			<table width="100%">
			<tr>
				<td align="right"><spring:message code="salesorder.shipping.name"/></td>
				<td width="1%" align="center">:</td>
				<td>
					<form:select id="shippingAddress" path="shippingAddress" cssClass="combobox-ext" onchange="updatedShippingAddressDetail(this.value)">
					</form:select>
				</td>
			</tr>
			<tr>
				<td align="right"><spring:message code="postaladdress.detail"/></td>
				<td width="1%" align="center">:</td>
				<td><input id="addressDetail" class="input-disabled" size="35" value="${deliveryOrder_form.shippingAddress.address}" disabled/></td>
			</tr>
 				<tr>
				<td align="right"><spring:message code="postaladdress.postalcode"/></td>
				<td width="1%" align="center">:</td>
				<td><input id="addressPostalCode" class="inputbox input-disabled" value="${deliveryOrder_form.shippingAddress.postalCode}" disabled/></td>
			</tr>
			<tr>
				<td align="right"><spring:message code="postaladdress.city"/></td>
				<td width="1%" align="center">:</td>
				<td><input id="addressCity" class="inputbox input-disabled" value="${deliveryOrder_form.shippingAddress.city.name}" disabled/></td>
			</tr>
        	</table>
		</div>
		<div id="productLineItem" dojoType="ContentPane" label="<spring:message code='salesorder.lineitem'/>" class="tab-pages" refreshOnShow="true" selected="true">
  			<div class="toolbar-clean">
		    	<table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
		    	<thead>
			    	<tr>
			    		<th width="1%" nowrap="nowrap">&nbsp;</th>
						<th width="20%" nowrap="nowrap"><spring:message code="product"/></th>
						<th width="5%" nowrap="nowrap"><spring:message code="product.onhand"/></th>
						<th width="5%"><spring:message code="deliveryorder.reference.qty"/></th>
						<th width="5%"><spring:message code="deliveryorder.quantity"/></th>
						<th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
						<th width="20%" nowrap="nowrap"><spring:message code="container"/></th>
						<th width="60%" nowrap="nowrap"><spring:message code="deliveryorder.note"/></th>
					</tr>
				</thead>
				<tbody id="lineItem">
				<c:forEach items="${deliveryOrder_form.items}" var="item" varStatus="idx">
				<tr>
					<td>&nbsp;</td>
					<td>
						<select id="product[${idx.index}]" class="combobox-ext input-disabled productInput" name="items[${idx.index}].product" index="${idx.index}" next="product" disabled>
							<option value="${item.deliveryReferenceItem.product.id}">${item.deliveryReferenceItem.product.name}</option>
						</select>
					</td>
					<td><input id="onhand[${idx.index}]" size="10" value="0.00" class="input-disabled input-decimal" name="items[${idx.index}].onhand" index="${idx.index}" next="onhand" disabled/></td>
					<td><input id="soquantity[${idx.index}]" size="10" value="${item.deliveryReferenceItem.quantity}" class="input-disabled input-decimal" name="items[${idx.index}].soquantity" index="${idx.index}" next="soquantity" disabled/></td>
					<td>
						<c:if test="${!item.deliveryReferenceItem.product.serial}"><input id="delivered[${idx.index}]" size="10" value="0.00" class="input-number" name="items[${idx.index}].quantity" index="${idx.index}" next="delivered"/></c:if>
						<c:if test="${item.deliveryReferenceItem.product.serial}">
							<input id="qtySerial[${idx.index}]" size="10" value="0.00" class="input-number" index="${idx.index}" onChange="addBarcode(this)" reference="${item.deliveryReferenceItem.id}" next="qtySerial"/>
							<input id="delivered[${idx.index}]" size="10" value="0.00" class="input-number" name="items[${idx.index}].quantity" index="${idx.index}" next="delivered" style="display: none;"/>
						</c:if>
					</td>
					<td><input id="uom[${idx.index}]" size="6" value="${item.deliveryReferenceItem.product.unitOfMeasure.measureId}" class="input-disabled" name="items[${idx.index}].uom" index="${idx.index}" next="uom" disabled/></td>
					<td><select id="container[${idx.index}]" name="items[${idx.index}].container" index="${idx.index}" onchange="updateOnHand(this, ${idx.index});" next="container" class="combobox"></select><a class="item-popup" onclick="openContainer(this, ${idx.index});" title="Container"></a></td>
					<td><input id="note[${idx.index}]" type="text" size="35" name="items[${idx.index}].note" index="${idx.index}" next="note"/></td>
				</tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr class="end-table"><td colspan="8">&nbsp;</td></tr>
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
	
	$('#customer').change();
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
			if (confirm('<spring:message code="deliveryorder.doquantity"/> <spring:message code="product"/> <spring:message code="notif.greater"/> <spring:message code="deliveryorder.soquantity"/>')) {
			  	return true;
			  	isValid = true;
			} else {
			 	isValid = false;
				return false;
			}
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

function updateShippingAddress(element) {
	let customerId = $('#customer').val();

	Party.load(customerId);

	let _shippingAddress = $('#shippingAddress');
	if (_shippingAddress.find('option').length > 0) { // Clear options if any
		_shippingAddress.empty();
	}

	let addresses = Party.data.partyAddresses;

	addresses.forEach(address => {
		// Periksa apakah postalTypes memiliki tipe 'SHIPPING' dengan enabled == true
		let hasShippingEnabled = address.postalTypes.some(postalType =>
				postalType.type === 'SHIPPING' && postalType.enabled === true
		);

		if (hasShippingEnabled) {
			let option = $('<option></option>')
					.val(address.postalId)
					.text(address.addressName);

			if (address.isDefault) { // Jika alamat ini adalah default, set sebagai selected
				option.attr('selected', 'selected');
			}

			_shippingAddress.append(option); // Tambahkan opsi ke elemen _shippingAddress
		}
	});

	updatedShippingAddressDetail(_shippingAddress.val());
}

function updatedShippingAddressDetail(selectedId) {
	// Clear Detail Address
	$('#addressDetail').val('');
	$('#addressPostalCode').val('');
	$('#addressCity').val('');

	if (!selectedId || selectedId.trim() === "") { // Cancel if customer does not have shipping address
		return;
	}

	PostalAddress.load(selectedId);

	if (PostalAddress.data) {
		var addressDetail = PostalAddress.data.postalAddress || '';
		var postalCode = PostalAddress.data.postalCode || '';
		var city = PostalAddress.data.postalCity ? PostalAddress.data.postalCity.name : '';

		// Update fields in the form
		$('#addressDetail').val(addressDetail);
		$('#addressPostalCode').val(postalCode);
		$('#addressCity').val(city);
	} else {
		// If no data is returned, clear the fields
		$('#addressDetail').val('');
		$('#addressPostalCode').val('');
		$('#addressCity').val('');
	}
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

let maxIdx = 0;
$('input[name^="details["][name$="[reference]"]').each(function(){
    let nameAttr = $(this).attr("name");
    let match = nameAttr.match(/details\[(\d+)\]/);
    if(match){
        let currentIdx = parseInt(match[1], 10);
        if(currentIdx > maxIdx){
            maxIdx = currentIdx;
        }
    }
});
let idx = maxIdx + 1;

function addBarcode(element) {
	let reference = $(element).data('reference');
	let idxRef = $(element).data('index');

	$('#lineItem' + idxRef + ' tr.barcode').remove();
	
	for (let i = 0; i < element.value; i++) { 
		addLine(i);		
		$('#lineItem' + idxRef + ' tr:last').addClass('barcode');
	}
	
	addRow($('#lineItem' + idxRef), idx++,
			'<select class="combobox-min barcodes" data-index="{{idx}}" name="details[{{idx}}][lot][id]"></select><a onclick="open_barcode(this, '+ idxRef +' )" data-index="{{idx}}" data-target="details[{{idx}}][lot]" class="item-popup item-tools"></a>',
			'<input onchange="load_serial();" name="details[{{idx}}][lot][serial]" type="hidden" value="" readonly/>',
			'&nbsp;',
			'<input data-index="{{idx}}" name="details[{{idx}}][quantity]" class="number-disabled quantities' + idxRef + '" size="8" readonly/>',
			'<input name="details[{{idx}}][reference]" type="hidden" value="' + reference + '" readonly/>',
			'<input type="hidden" name="details[{{idx}}][itemIndex]" value="' + idxRef + '"/>',
			'&nbsp;',
			'&nbsp;',
		)
}

function addLine($index) {
	$tbody = $('#lineItem');
    $tr = $('<tr/>');
    
	$product = List.get('<select class="combobox-ext productInput"/>','product['+$index+']');
	$barcode = List.get('<select class="combobox barcodes"/>','serial['+$index+']');
	
	$qty = List.get('<input type="text" class="input-number" size="10" onchange="updateQuantity();"/>','quantity['+$index+']', '0.00');
	$reference = List.get('<input type="text" size="30"/>','note['+$index+']');
	
	$tr.append(List.col([$barcode, $product]));
	$tr.append(List.col([$qty]));
	
	$tbody.append($tr);
	
	$(".input-number").bind(inputFormat);
}

function updateQuantity() {
	
}
</script>