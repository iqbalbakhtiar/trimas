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
				<%-- <tr>
					<td align="right"><spring:message code="deliveryorder.driver"/></td>
					<td width="1%" align="center">:</td>
					<td><form:input path="driverName" cssClass="inputbox"/></td>
					<td>&nbsp;</td>
				</tr> --%>
				<tr>
					<td align="right"><spring:message code="deliveryorder.vehicle"/></td>
					<td width="1%" align="center">:</td>
					<td><form:input path="vehicle" cssClass="inputbox"/></td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryorder.plate"/></td>
					<td width="1%" align="center">:</td>
					<td><form:input path="plateNumber" cssClass="inputbox"/></td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryrealization.driver.courier"/></td>
					<td width="1%" align="center">:</td>
					<td><form:input path="driverName" cssClass="inputbox"/></td>
					<td>&nbsp;</td>
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
		    	<table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="2" align="center"  style="width:100%;">
		    	<thead>
			    	<tr>
			    		<th width="1%" nowrap="nowrap">&nbsp;</th>
						<th width="20%" nowrap="nowrap"><spring:message code="product"/></th>
						<th width="5%" nowrap="nowrap"><spring:message code="product.onhand"/></th>
						<th width="5%"><spring:message code="deliveryorder.reference.qty"/></th>
						<th width="5%"><spring:message code="deliveryorder.quantity"/></th>
						<th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
						<th width="5%" nowrap="nowrap"><spring:message code="product.lot"/></th>
						<th width="20%" nowrap="nowrap"><spring:message code="container"/></th>
						<th width="60%" nowrap="nowrap"><spring:message code="deliveryorder.note"/></th>
					</tr>
				</thead>
				<c:forEach items="${deliveryOrder_form.items}" var="item" varStatus="stat">
				<tbody id="lineItem${stat.index}" class="lineItem">
				<tr>
					<td>&nbsp;</td>
					<td>
						<select id="product[${stat.index}]" class="combobox-ext input-disabled productInput" name="items[${stat.index}].product" index="${stat.index}" next="product" disabled>
							<option value="${item.deliveryReferenceItem.product.id}">${item.deliveryReferenceItem.product.name}</option>
						</select>
					</td>
					<td><input id="onhand[${stat.index}]" size="12" value="0.00" class="input-disabled input-decimal" name="items[${stat.index}].onhand" index="${stat.index}" next="onhand" disabled/></td>
					<td><input id="referenceQuantity[${stat.index}]" size="11" value="<fmt:formatNumber value='${item.deliveryReferenceItem.quantity}' pattern=',##0.00'/>" class="input-disabled input-decimal" index="${stat.index}" next="referenceQuantity" disabled/></td>
					<%--Bukan Barang Serial--%>
					<c:if test="${!item.deliveryReferenceItem.product.serial}">
						<td><input id="delivered[${stat.index}]" size="10" value="0.00" class="input-decimal quantities" name="items[${stat.index}].quantity" index="${stat.index}" next="delivered" onchange="checkQuantity(${stat.index})" producttype="nonserial"/></td>
						<td><input id="uom[${stat.index}]" size="6" value="${item.deliveryReferenceItem.product.unitOfMeasure.measureId}" class="input-disabled" name="items[${stat.index}].uom" index="${stat.index}" next="uom" disabled/></td>
						<td>&nbsp;</td>
						<td><select id="container[${stat.index}]" name="items[${stat.index}].container" index="${stat.index}" onchange="updateOnHand(this, ${stat.index});" next="container" class="combobox"></select><a class="item-popup" onclick="openContainer(${stat.index});" title="Container"></a></td>
					</c:if>
					<%--Barang Serial--%>
					<c:if test="${item.deliveryReferenceItem.product.serial}">
						<td>
							<input id="qtySerial[${stat.index}]" size="10" value="0.00" class="input-number" index="${stat.index}" onChange="addBarcode(this);" reference="${item.deliveryReferenceItem.id}" next="qtySerial"/>
						</td>
						<td>
							<input id="uom[${stat.index}]" size="6" value="${item.deliveryReferenceItem.product.unitOfMeasure.measureId}" class="input-disabled" name="items[${stat.index}].uom" index="${stat.index}" next="uom" disabled style="display: none;"/>
							<input id="delivered[${stat.index}]" size="10" value="0.00" class="input-disabled input-decimal quantities" name="items[${stat.index}].quantity" index="${stat.index}" next="delivered" producttype="serial" readonly/>
						</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</c:if>
					<td>
						<input id="note[${stat.index}]" type="text" size="35" name="items[${stat.index}].note" index="${stat.index}" next="note"/>
						<input id="deliveryReferenceItem[${stat.index}]" type="text" size="5" name="items[${stat.index}].deliveryReferenceItem" value="${item.deliveryReferenceItem.id}" index="${stat.index}" style="display: none;" readonly="true"/>
						<input id="deliveryItemType[${stat.index}]" type="text" size="5" name="items[${stat.index}].deliveryItemType" value="BASE" index="${stat.index}" style="display: none;" readonly="true"/>
					</td>
				</tr>
				</tbody>
				</c:forEach>
				<tfoot>
					<tr class="end-table"><td colspan="9">&nbsp;</td></tr>
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

	
	if ($('.lineItem').length === 0) {
	    alert('<spring:message code="salesorder.lineitem"/> <spring:message code="notif.empty"/> !');
	    return false;
	}
	
	// Validasi delivered & qtySerial
	var valid = true;

	$('input[id^="delivered["]').each(function() {
	    var idx = $(this).attr('index');
	    var type = $(this).attr('producttype');
	    var delivered = parseFloat($(this).val()) || 0;

	    if (type === 'serial') {
	        var qtySerial = parseFloat($('#qtySerial\\[' + idx + '\\]').val()) || 0;
	        if (qtySerial <= 0) {
	        	alert('<spring:message code="deliveryorder.quantity"/> <spring:message code="notif.greater.zero"/> !');
	            valid = false;
	            return false; // break loop
	        }
	    } else {
	        if (delivered <= 0) {
	        	alert('<spring:message code="deliveryorder.quantity"/> <spring:message code="notif.greater.zero"/> !');
	            valid = false;
	            return false;
	        }
	    }
	});

	if (!valid) return false;
	
	// Validasi Line Items
	var isValid = true;
	$('.quantities').each(function(i, obj)
	{
        var idx = obj.getAttribute('index');
    	var product = $('#product\\['+idx+'\\]');
    	var productType = obj.getAttribute('producttype');
        var referenceQty = parseFloat($('#referenceQuantity\\['+idx+'\\]').val());
        var quantity = parseFloat($('#delivered\\['+idx+'\\]').val());
        
        if(productType == 'serial') {
			/* var productName = product.text();

            if (quantity !== referenceQty) {
                alert('<strong>' + productName + '</strong> - ' + '<spring:message code="deliveryorder.quantity"/> ' + '<spring:message code="notif.different"/> ' + '<spring:message code="deliveryorder.reference.qty"/> !');
                isValid = false;
                return false;
            } */
    	} else { // product type non serial
			var productName = product.text();
	
			if (quantity <= 0) {
				alert('<strong>' + productName + '</strong> - <spring:message code="deliveryorder.quantity"/> <spring:message code="notif.greater.zero"/> !');
				isValid = false;
				return false;
			}

			var container =  $('#container\\['+idx+'\\]').val();
	
			if (container == null || container.trim() === "") {
				alert('<strong>' + productName + '</strong> - <spring:message code="container"/> <spring:message code="notif.empty"/> !');
				$('#container\\['+idx+'\\]').focus();
				isValid = false;
				return false;
			}
		}
	});
	
	$('.barcodes').each(function(i, obj)
	{
        var idx = obj.getAttribute('index');
		var serial =  $('#serial\\['+idx+'\\]').val();
		
		if (serial == null || serial.trim() === "") {
			alert('<spring:message code="product.serial"/> <spring:message code="notif.empty"/> !');
			isValid = false;
			return false;
		}

		var container =  $('#container\\['+idx+'\\]').val();

		if (container == null || container.trim() === "") {
			alert('<strong>' + productName + '</strong> - <spring:message code="container"/> <spring:message code="notif.empty"/> !');
			$('#container\\['+idx+'\\]').focus();
			isValid = false;
			return false;
		}
	});

	if (!isValid) {
		return false;
	}

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

function openContainer(idx) {
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

var $index = '${fn:length(deliveryOrder_form.items)}';

function addBarcode(element) {
	let reference = $(element).attr('reference');
	let idxRef = $(element).attr('index');
	
	$('#lineItem' + idxRef + ' tr.barcode').remove();
	
	for (let i = 0; i < element.value; i++) { 
		addLine(idxRef, $index);
		$index++;
		$('#lineItem' + idxRef + ' tr:last').addClass('barcode');
	}
}

function addLine($idxRef, $index) {
	$productId = $('#product\\['+$idxRef+'\\]').val();
	$uom = $('#uom\\['+$idxRef+'\\]').val();
	$referenceId = $('#deliveryReferenceItem\\['+$idxRef+'\\]').val();
	
	$tbody = $('#lineItem'+$idxRef);
    $tr = $('<tr/>');
    
	$barcode = List.get('<select class="combobox barcodes" onchange="updateQuantity('+$idxRef+','+$referenceId+');"/>','serial['+$index+']');
	$barcodeImg = List.img('<spring:message code="barcode"/>', $index, 'openBarcode("'+$index+'","'+$productId+'")');
	
	$qty = List.get('<input type="text" class="input-number input-disabled qtyRef'+$referenceId+'" readonly="true" size="10" onchange="updateQuantity('+$idxRef+','+$referenceId+');"/>','quantity['+$index+']', '0.00');
	$lotCode = List.get('<input type="text" class="input-disabled" readonly="true" size="5"/>','lotCode['+$index+']');
	$uom = List.get('<input type="text" class="input-disabled" disabled="true" size="6"/>','uom'+$index, $uom);

	$container = List.get('<select class="combobox containers"/>','container['+$index+']');
	$containerImg = List.img('<spring:message code="container"/>', $index, 'openContainer("'+$index+'")');
	
	$reference = List.get('<input type="text" class="input-disabled" readonly="true" size="10" style="display:none;"/>','deliveryReferenceItem['+$index+']', $referenceId);
	$type = List.get('<input type="text" class="input-disabled" readonly="true" size="5" style="display:none;"/>','deliveryItemType['+$index+']', 'SERIAL');

	$tr.append(List.col(''));
	$tr.append(List.col(''));
	$tr.append(List.col([$barcode, $barcodeImg], '', 'text-align: right;').attr('colspan','2'));
	$tr.append(List.col([$qty]));
	$tr.append(List.col([$uom]));
	$tr.append(List.col([$lotCode]));
	$tr.append(List.col([$container]));
	$tr.append(List.col([$reference, $type]));
	
	$tbody.append($tr);
	
	$(".input-number").bind(inputFormat);
}

function checkQuantity(index) {
	var qtyRef = $('#referenceQuantity\\['+index+'\\]').val().toNumber();
	var qty = $('#delivered\\['+index+'\\]').val().toNumber();
	
	if(qty > qtyRef) {
		$('#delivered\\['+index+'\\]').val(qtyRef.numberFormat('#,##0.00'));
		alert('<spring:message code="deliveryorder.delivered"/> <spring:message code="notif.greater"/> <spring:message code="deliveryorder.reference.qty"/> !');
		return;
	}
}

function updateQuantity(indexRef, refId) {
	var qty = 0;
	$('.qtyRef'+refId).each(function()
	{
		qty = qty + $(this).val().toNumber();
	});
	
	$('#delivered\\['+indexRef+'\\]').val(qty);
}

var selectedBarcodes = [];
function openBarcode(index, productId)
{
	selectedBarcodes = [];
	$.each($(".barcodes"), function(i, obj)
    {
        var idx = obj.getAttribute('index');
        if (obj.value != '')
        	selectedBarcodes.push(obj.value);
    });

	var org = document.getElementById('org').value;
	var facilityId = document.getElementById('facility').value;
	
	const baseUrl = '<c:url value="/page/popupinventoryitemview.htm"/>';
	const params = {
		target: 'serial[' + index + ']',
		index: index,
		group: false,
		onHand: true,
		availableSales: true,
		ref: '4Serial',
		organization: org,
		facility: facilityId,
		productId: productId,
		barcodes: Array.from(new Set(selectedBarcodes))
	};
	
	openpopup(buildUrl(baseUrl, params));
}
</script>