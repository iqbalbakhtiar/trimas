<%--suppress ALL --%>
<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/salesorderview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
	<a class="item-button-print"  href="<c:url value='/page/salesorderprint.htm?id=${salesOrder_edit.id}'/>"><span><spring:message code="sirius.print"/></span></a>
	<c:if test="${salesOrder_edit.soStatus != 'CLOSE'}">
		<a class="item-button-close"><span><spring:message code="sirius.close"/></span></a>
	</c:if>
</div>

<div class="main-box">
<sesform:form id="addForm" name="addForm" method="post" modelAttribute="salesOrder_form" enctype="multipart/form-data">
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
						<form:select id="org" path="organization" cssClass="combobox-ext input-disabled" disabled="true">
							<c:if test='${not empty salesOrder_edit.organization}'>
								<form:option value='${salesOrder_edit.organization.id}' label='${salesOrder_edit.organization.fullName}'/>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="sirius.date"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="date" name="date" size="10" class="input-disabled" disabled value="<fmt:formatDate value='${salesOrder_edit.date}' pattern='dd-MM-yyyy'/>"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="salesorder.expired.date"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="date" name="date" size="10" class="input-disabled" disabled value="<fmt:formatDate value='${salesOrder_edit.expDate}' pattern='dd-MM-yyyy'/>"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="customer"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="customer" path="customer" cssClass="combobox-ext input-disabled" disabled="true">
							<c:if test='${not empty salesOrder_edit.customer}'>
								<form:option value="${salesOrder_edit.customer.id}">${salesOrder_edit.customer.code} ${salesOrder_edit.customer.fullName}</form:option>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td width="19%" align="right"><spring:message code="creditterm.term"/> :</td>
					<td width="1%" align="center">:</td>
					<td>
						<form:input id='term' path='term' cssClass='number-disabled' size="5" disabled="true"/>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="salesorder.shipping.date"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="shippingDate" name="shippingDate" size="10" class="input-disabled" disabled value="<fmt:formatDate value='${salesOrder_edit.shippingDate}' pattern='dd-MM-yyyy'/>"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="salesorder.tax.type"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="tax" path="tax" onchange="updateDisplay();" cssClass="input-disabled" disabled="true">
							<c:if test='${not empty salesOrder_edit.tax}'>
								<option value="${salesOrder_edit.tax.id}" data-taxrate="${salesOrder_edit.tax.taxRate}">${salesOrder_edit.tax.taxName}</option>
							</c:if>
						</form:select>
						<input size="5" id="taxRate" class="number-disabled" disabled />&nbsp;%
					</td>
				</tr>
            	<c:if test="${not empty salesOrder_edit.approvable}">
				<tr>
					<td align="right"><spring:message code="approver"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="approver" path="approver" cssClass="combobox-ext input-disabled" disabled="true">
							<c:if test='${not empty salesOrder_edit.approver}'>
								<form:option value="${approvalDecision.forwardTo.id}">${approvalDecision.forwardTo.code} ${approvalDecision.forwardTo.fullName}</form:option>
							</c:if>
						</form:select>
					</td>
				</tr>
				</c:if>
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
										<td width="20%">:&nbsp;&nbsp;<input id="totalTransaction" value="0.00" class="number-disabled" readonly="readonly" size="20"/></td>
									</tr>
								</table>
							</fieldset>
						</td>
					</tr>
		            <tr>
		            	<td>
		                <c:if test="${not empty salesOrder_edit.approvable}">
			                <%@ include file="/pages/sales/approval-history.jsp" %>
			                <%@ include file="/pages/sales/approval.jsp" %>
		                </c:if>
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
						<form:select id="shippingAddress" path="shippingAddress" cssClass="combobox-ext input-disabled" disabled="true">
							<c:if test='${not empty salesOrder_edit.shippingAddress}'>
								<form:option value="${salesOrder_edit.shippingAddress.id}">${salesOrder_edit.shippingAddress.addressName}</form:option>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="postaladdress.detail"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="addressDetail" class="inputbox input-disabled" disabled value="${salesOrder_edit.shippingAddress.address}"/></td>
				</tr>
  				<tr>
					<td align="right"><spring:message code="postaladdress.postalcode"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="addressPostalCode" class="inputbox input-disabled" disabled value="${salesOrder_edit.shippingAddress.postalCode}"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="postaladdress.city"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="addressCity" class="inputbox input-disabled" disabled value="${salesOrder_edit.shippingAddress.city.name}"/></td>
				</tr>
  			</table>
		</div>
		<div id="productLineItem" dojoType="ContentPane" label="<spring:message code='salesorder.lineitem'/>" class="tab-pages" refreshOnShow="true" selected="true">
  			<div class="toolbar-clean">
		    	<table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
			    	<thead>
				    	<tr>
              				<th width="1%" nowrap="nowrap">&nbsp;</th>
							<th width="8%" nowrap="nowrap"><spring:message code="product"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="sirius.qty"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
							<th width="8%" nowrap="nowrap"><spring:message code="sirius.unitprice"/></th>
							<th width="8%" nowrap="nowrap"><spring:message code="sirius.total"/> <spring:message code="sirius.amount"/></th>
							<th width="50%" nowrap="nowrap"><spring:message code="salesorder.packing.note"/></th>
						</tr>
					</thead>
					<tbody id="lineItem">
					<c:forEach items="${adapter.items}" var="item" varStatus="idx">
						<tr>
                			<td>&nbsp;</td>
			                <td><input id="product[${idx.index}]" size="26" value="${item.product.name}" class="input-disabled" name="items[${idx.index}].product" index="${idx.index}" next="product" disabled/></td>
			                <td><input id="quantity[${idx.index}]" size="6" value="${item.quantity}" class="input-disabled input-decimal" name="items[${idx.index}].quantity" index="${idx.index}" next="quantity" disabled/></td>
			                <td><input id="uom[${idx.index}]" size="6" value="${item.product.unitOfMeasure.measureId}" class="input-disabled" name="items[${idx.index}].uom" index="${idx.index}" next="uom" disabled/></td>
			                <td><input id="amount[${idx.index}]" size="12" value="<fmt:formatNumber value='${item.amount}' pattern=',##0.00'/>" class="input-disabled input-decimal" name="items[${idx.index}].amount" index="${idx.index}" next="amount" disabled/></td>
			                <td><input id="totalAmount[${idx.index}]" size="12" class="input-number input-disabled" disabled value="<fmt:formatNumber value='${item.subTotal}' pattern=',##0.00'/>"/></td>
			                <td><input id="note[${idx.index}]" type="text" value="${item.note}" name="purchaseOrder.items[${idx.index}].note"index="${idx.index}" next="note" size="40"/></td>
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
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${salesOrder_edit.createdBy.fullName}'/> (<fmt:formatDate value='${salesOrder_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${salesOrder_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${salesOrder_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
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

	$('.item-button-close').click(function(){
		closeSo();
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

	// Validasi exp date
	var date = $('#expDate').val();
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

    // Validasi shippingAddress
    var shippingAddress = $('#shippingAddress').val();
    if (shippingAddress == null || shippingAddress === "") {
        alert('<spring:message code="salesorder.shipping.name"/> <spring:message code="notif.empty"/> !');
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
        var product = $row.find('input[id^="product["]').val();
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

        // Validasi discount (opsional jika perlu)
        var discStr = $row.find('input[id^="discount["]').val().replace(/,/g, '');
        var disc = parseFloat(discStr);
        if (isNaN(disc) || disc < 0 || disc > 100) {
            alert('<spring:message code="salesorder.disc"/> <spring:message code="notif.invalid"/> ! (<spring:message code="salesorder.lineitem"/> ' + (index + 1) + ')');
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
		url:"<c:url value='/page/salesorderedit.htm'/>",
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
	openpopup("<c:url value='/page/popupproductview.htm?&target=product['/>"+index+"]&index="+index);
}

function openapprover() {
	if (!$('#org').val()) {
		alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
		return;
	}

	const orgId = $('#org').val();
	const approver = $('#approver').val();
	const baseUrl = '<c:url value="/page/popuppartyrelationview.htm"/>';
	const params = {
		target: 'approver', // Id Dropdown (Select) element
		organization: orgId, // Org (PartyTo)
		fromRoleType: 8, // Sales Approver
		toRoleType: 2, // Company
		relationshipType: 2, // Employment Relationship
		except: approver // Except this approver
	};

	openpopup(buildUrl(baseUrl, params));
}

function closeSo() {
	$.ajax({
		url:"<c:url value='/page/salesorderclose.htm?id=${salesOrder_edit.id}'/>",
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
</script>