<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/salesorderview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
<sesform:form id="addForm" name="addForm" method="post" modelAttribute="salesOrder_form" enctype="multipart/form-data">
	<table width="100%" border="0">
		<tr>
			<td width="60%">
				<table style="border:none" width="100%">
				<tr>
					<td width="34%" align="right">Sales Order ID</td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input class="inputbox input-disabled" value="Auto Number" disabled/></td>
					<td width="1%"><form:errors path="code"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="organization"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="org" path="organization" cssClass="combobox-ext">
							<c:if test='${not empty salesOrder_form.organization}'>
								<form:option value='${salesOrder_form.organization.id}' label='${salesOrder_form.organization.fullName}'/>
							</c:if>
						</form:select>
						<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title="Company Structure" />
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="sirius.date"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="date" name="date" class="datepicker" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="salesorder.expired.date"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="expDate" name="expDate" class="datepicker" value="<fmt:formatDate value='${twoMonth}' pattern='dd-MM-yyyy'/>"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="customer"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="customer" path="customer" cssClass="combobox-ext" onchange="updateShippingAddress(this)">
						</form:select>
						<a class="item-popup" onclick="openCustomer()" title="Customer" />
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="salesorder.shipping.date"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="shippingDate" name="shippingDate" class="datepicker"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="salesorder.tax.type"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="tax" path="tax" onchange="updateDisplay();">
							<c:forEach var="tax" items="${taxes}">
								<option value="${tax.id}" data-taxrate="${tax.taxRate}">${tax.taxName}</option>
							</c:forEach>
						</form:select>
						<spring:message code="salesorder.tax.rate"/>
						<input size="7" id="taxRate" class="input-disabled" disabled />
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="approver"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="approver" path="approver" cssClass="combobox-ext">
						</form:select>
						<a class="item-popup" onclick="openApprover()" title="Approver" />
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
			
			<td width="40%" valign="top">
				<table width="100%" style="border: none">
					<tr>
						<td>
							<fieldset>
								<legend><strong><spring:message code="salesorder.recapitulation"/></strong></legend>
								<table width="100%" style="border: none">
									<tr>
										<td width="80%" align="right"><spring:message code="salesorder.total"/></td>
										<td width="20%">:&nbsp;&nbsp;<input id="totalSales" value="0.00" class="number-disabled" readonly="readonly" size="20"/></td>
									</tr>
									<tr>
										<td width="80%" align="right"><spring:message code="salesorder.tax.amount"/></td>
										<td width="20%">:&nbsp;&nbsp;<input id="totalTax" value="0.00" class="number-disabled" readonly="readonly" size="20"/></td>
									</tr>
									<tr>
										<td width="80%" align="right"><strong><spring:message code="salesorder.total.transaction"/></strong></td>
										<td width="20%">:&nbsp;&nbsp;<input id="totalTransaction" value="0.00" name="amount" class="number-disabled" readonly="readonly" size="20"/></td>
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
					<td><input id="addressDetail" class="inputbox input-disabled" disabled/></td>
				</tr>
  				<tr>
					<td align="right"><spring:message code="postaladdress.postalcode"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="addressPostalCode" class="inputbox input-disabled" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="postaladdress.city"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="addressCity" class="inputbox input-disabled" disabled/></td>
				</tr>
  			</table>
		</div>
		
		<div id="productLineItem" dojoType="ContentPane" label="<spring:message code='salesorder.lineitem'/>" class="tab-pages" refreshOnShow="true" selected="true">
  			<div class="toolbar-clean">
  				<div class="toolbar-clean">
  					<a class="item-button-new"><span><spring:message code="sirius.row.new"/></span></a>
  					<a class="item-button-delete"><span><spring:message code="sirius.row.delete"/></span></a>
		    		<div class="item-navigator">&nbsp;</div>
		    	</div>
		    	<table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
			    	<thead>
				    	<tr>
				    		<th width="1%" nowrap="nowrap"><input class="checkall" type="checkbox"/></th>
							<th width="8%" nowrap="nowrap"><spring:message code="product"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="sirius.qty"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
							<th width="8%" nowrap="nowrap"><spring:message code="sirius.unitprice"/></th>
							<th width="8%" nowrap="nowrap"><spring:message code="sirius.total"/> <spring:message code="sirius.amount"/></th>
							<th width="8%" nowrap="nowrap"><spring:message code="salesorder.packing.note"/></th>
						</tr>
					</thead>
					<tbody id="lineItem">
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
	var $index = 0; // For Line Item Index

	updateDisplay();
	
	$('.item-button-save').click(function(){
		if(validateForm()) {
			save();
		}
	});
	
	$('.item-button-new').click(function() {
		addLine($index);
		$index++;
	});
	
	$('.checkall').click(function () {
        $('.check').prop("checked", this.checked);
    });
	
	$('.item-button-delete').click(function () {
        $('.check').each(function(){
            if(this.checked){
                this.checked = false;
                $(this).parent().parent().remove();
                updateDisplay();
            }
        });
        $('.checkall').prop("checked", false);
    });
});

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
        alert('<spring:message code="salesorder.date"/> <spring:message code="notif.empty"/> !');
        return false;
    }

	// Validasi expired date
	var date = $('#expDate').val();
	if (date == null || date === "") {
		alert('<spring:message code="salesorder.expired.date"/> <spring:message code="notif.empty"/> !');
		return false;
	}

    // Validasi customer
    var customer = $('#customer').val();
    if (customer == null || customer === "") {
        alert('<spring:message code="customer"/> <spring:message code="notif.empty"/> !');
        return false;
    }

    // Validasi shippingDate
    var shippingDate = $('#shippingDate').val();
    if (shippingDate == null || shippingDate === "") {
        alert('<spring:message code="salesorder.shipping.date"/> <spring:message code="notif.empty"/> !');
        return false;
    }

    // Validasi tax
    var tax = $('#tax').val();
    if (tax == null || tax === "") {
        alert('<spring:message code="salesorder.tax.type"/> <spring:message code="notif.empty"/> !');
        return false;
    }

    // Validasi approver
    var approver = $('#approver').val();
    if (approver == null || approver === "") {
        alert('<spring:message code="approver"/> <spring:message code="notif.empty"/> !');
        return false;
    }
    
 	// **Tambahkan validasi untuk memastikan setidaknya ada satu line item**
    if ($('#lineItem tr').length === 0) {
        alert('<spring:message code="notif.add"/> <spring:message code="salesorder.lineitem"/> <spring:message code="notif.select2"/> !');
        return false;
    }

    // Validasi Line Items
    var isValid = true;
    $('#lineItem tr').each(function(index){
        var $row = $(this);
        
        // Validasi product
        var product = $row.find('select[id^="product["]').val();
        if (product == null || product === "") {
            alert('<spring:message code="product"/> <spring:message code="notif.empty"/> ! (<spring:message code="salesorder.lineitem"/> ' + (index + 1) + ')');
            isValid = false;
            return false; // Keluar dari each
        }

        // Validasi quantity
        var qtyStr = $row.find('input[id^="quantity["]').val().replace(/,/g, '');
        var qty = parseFloat(qtyStr);
        if (isNaN(qty) || qty <= 0) {
            alert('<spring:message code="sirius.qty"/> <spring:message code="notif.empty"/> ! (<spring:message code="salesorder.lineitem"/> ' + (index + 1) + ')');
            isValid = false;
            return false;
        }

        // Validasi price
        var priceStr = $row.find('input[id^="amount["]').val().replace(/,/g, '');
        var price = parseFloat(priceStr);
        if (isNaN(price) || price <= 0) {
            alert('<spring:message code="sirius.unitprice"/> <spring:message code="notif.empty"/> ! (<spring:message code="salesorder.lineitem"/> ' + (index + 1) + ')');
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
		url:"<c:url value='/page/salesorderadd.htm'/>",
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
					window.location="<c:url value='/page/salesorderpreedit.htm?id='/>"+json.id;
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

		var amount = qty * price;
		var totalAmount = amount;

		totalSales += amount;
		totalBeforeTax += totalAmount;

		// Mengatur nilai terformat menggunakan numberFormat
		$row.find('input[id^="amountInput["]').val(amount.numberFormat('#,##0.00'));
		$row.find('input[id^="totalAmount["]').val(totalAmount.numberFormat('#,##0.00'));
	});

	// Menghitung totalTax dan totalTransaction
	var totalTax = totalBeforeTax * (taxRate / 100);
	var totalTransaction = totalBeforeTax + totalTax;

	$('#totalSales').val(totalSales.numberFormat('#,##0.00'));
	$('#totalBeforeTax').val(totalBeforeTax.numberFormat('#,##0.00'));
	$('#totalTax').val(totalTax.numberFormat('#,##0.00'));
	$('#totalTransaction').val(totalTransaction.numberFormat('#,##0.00'));
}

function addLine($index) {
	$tbody = $('#lineItem');
    $tr = $('<tr/>');
    
	$cbox = List.get('<input type="checkbox" class="check"/>','check'+$index);
	
	$product = List.get('<select class="combobox productInput" onchange="checkDuplicate(this);updateDisplay();"/>','product['+$index+']');
	$productImg = List.img('<spring:message code="product"/>', $index, 'openProduct("'+$index+'")');
	
	$qty = List.get('<input type="text" class="input-number" size="6" onchange="updateDisplay();"/>','quantity['+$index+']', '0.00');
	
	$uom = List.get('<input type="text" class="input-disabled" disabled size="6" />','uom['+$index+']');
	
	$price = List.get('<input type="text" class="input-number" size="12" onchange="updateDisplay()"/>','amount['+$index+']', '0.00');

	$totalAmount = List.get('<input type="text" class="input-number input-disabled" disabled size="12"/>','totalAmount['+$index+']', '0.00');
	
	$packNote = List.get('<input type="text"/>','note['+$index+']');
	
	$tr.append(List.col([$cbox]));
	$tr.append(List.col([$product, $productImg]));
	$tr.append(List.col([$qty]));
	$tr.append(List.col([$uom]));
	$tr.append(List.col([$price]));
	$tr.append(List.col([$totalAmount]));
	$tr.append(List.col([$packNote]));
	
	$tbody.append($tr);
	
	$(".input-number").bind(inputFormat);
}

function openProduct(index) {
	const baseUrl = '<c:url value="/page/popupproductview.htm"/>';
	const params = {
		target: 'product[' + index + ']', // Id Dropdown (Select) element
		index: index,
		status: true // Filter Only Active Products
	};
	openpopup(buildUrl(baseUrl, params));
}

function checkDuplicate(element) {
	var productId = $(element).val();
	var index = element.getAttribute('index');

	var isDuplicated = String.duplicate('productInput');

	if (isDuplicated) {
		alert('<spring:message code="product"/>  <strong>'+ $(element).find('option:selected').text() +'</strong> <spring:message code="notif.duplicate"/> !');
		$(element).closest('tr').remove();
	}

	if(productId) {
		$.ajax({
			url:"<c:url value='/page/salesorderbyproductjson.htm'/>",
			data:{productId:productId},
			method : 'GET',
			dataType : 'json',
			success : function(json) {
				if(json)
				{
					if(json.status == 'OK'){
						let amount = document.getElementsByName('items['+index+'].amount')[0];
						if(amount && json.product != null) {
							amount.value = parseFloat(json.product.price).numberFormat('#,##0.00');
						}
					}
				}
			}
		});
	}
}

function openCustomer() {
	if (!$('#org').val()) {
		alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
		return;
	}

	const orgId = $('#org').val();
	const baseUrl = '<c:url value="/page/popuppartyrelationview.htm"/>';
	const params = {
		target: 'customer', // Id Dropdown (Select) element
		organization: orgId, // Org (PartyTo)
		fromRoleType: 4, // Customer
		toRoleType: 5, // Supplier
		relationshipType: 3, // Customer Relationship
		base: false // Filter Only Customer (Not Group)
	};

	openpopup(buildUrl(baseUrl, params));
}

function openApprover() {
	if (!$('#org').val()) {
		alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
		return;
	}

	const orgId = $('#org').val();
	const baseUrl = '<c:url value="/page/popuppartyrelationview.htm"/>';
	const params = {
		target: 'approver', // Id Dropdown (Select) element
		organization: orgId, // Org (PartyTo)
		fromRoleType: 8, // Sales Approver
		toRoleType: 2, // Company
		relationshipType: 2, // Employment Relationship
	};

	openpopup(buildUrl(baseUrl, params));
}
</script>