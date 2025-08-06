<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/workorderview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>
<div class="main-box">
<sesform:form id="addForm" name="addForm" method="post" modelAttribute="workOrder_edit" enctype="multipart/form-data">
	<table width="100%" border="0">
		<tr>
			<td width="60%">
				<table style="border:none" width="100%">
				<tr>
					<td width="34%" align="right"><spring:message code="sirius.code"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input class="inputbox input-disabled" value="${workOrder_edit.code}" disabled/></td>
					<td width="1%"><form:errors path="code"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="organization"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="org" path="organization" cssClass="combobox-ext">
							<form:option value='${workOrder_edit.organization.id}' label='${workOrder_edit.organization.fullName}'/>
						</form:select>
						<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title="<spring:message code='organization'/>" />
					</td>
				</tr>
				<tr style="display: none;">
					<td align="right"><spring:message code="facility"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="facility" path="facility" cssClass="combobox-ext">
						<c:if test='${not empty workOrder_edit.facility}'>
							<form:option value='${workOrder_edit.facility.id}' label='${workOrder_edit.facility.fullName}'/>
						</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="sirius.date"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="date" name="date" size="10" class="input-disabled" disabled value="<fmt:formatDate value='${workOrder_edit.date}' pattern='dd-MM-yyyy'/>"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="workorder.start.date"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<input id="startDate" name="startDate" size="10" class="input-disabled" disabled value="<fmt:formatDate value='${workOrder_edit.startDate}' pattern='dd-MM-yyyy'/>"/>
						&nbsp;
						<input id="workStart" name="workStart" size="4" class="input-disabled" disabled value="${workOrder_edit.workStart}"/>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="workorder.finish.date"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<input id="finishDate" name="finishDate" size="10" class="input-disabled" disabled value="<fmt:formatDate value='${workOrder_edit.finishDate}' pattern='dd-MM-yyyy'/>"/>
						&nbsp;
						<input id="workEnd" name="workEnd" size="4" class="input-disabled" disabled value="${workOrder_edit.workEnd}"/>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="sirius.operator"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="operator" path="operator" cssClass="combobox-ext">
							<form:option value='${workOrder_edit.operator.id}' label='${workOrder_edit.operator.fullName}'/>
						</form:select>
					</td>
				</tr>
				<c:if test="${not empty workOrder_edit.approver}">
				<tr>
					<td align="right"><spring:message code="approver"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="approver" path="approver" cssClass="combobox-ext">
							<form:option value='${workOrder_edit.approver.id}' label='${workOrder_edit.approver.fullName}'/>
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
		<div id="convert" dojoType="ContentPane" label="<spring:message code='workorder.convert'/>" class="tab-pages" refreshOnShow="true">
  			<div class="toolbar-clean">
  				<div class="toolbar-clean">
		    		<div class="item-navigator">&nbsp;</div>
		    	</div>
		    	<table class="table-list" id="lineItemConvert" cellspacing="0" cellpadding="0" align="center" style="width:100%;">
		    	<thead>
		    	<tr>
					<th width="1%">&nbsp;</th>
					<th><spring:message code="product.name"/></th>
                   	<th width="8%"><spring:message code='container'/></th>
					<th width="5%"><spring:message code="product.serial"/></th>
					<th width="5%"><spring:message code="product.uom"/></th>
                    <th width="5%"><spring:message code='sirius.qty'/></th>
                    <th width="50%"><spring:message code='sirius.note'/></th>
				</tr>
				</thead>
				<tbody id="lineItemConvert">
				<c:forEach items="${workOrder_edit.convertItems}" var="item" varStatus="stat">
				<tr>
					<td>&nbsp;</td>
	                <td><input size="26" value="${item.product.name}" class="input-disabled" disabled/></td>
	                <td><input size="26" value="${item.container.name}" class="input-disabled" disabled/></td>
	                <td><input size="12" value="${item.lot.serial}" class="input-disabled" disabled/></td>
	                <td><input size="12" value="${item.product.unitOfMeasure.measureId}" class="input-disabled" disabled/></td>
	                <td><input size="12" value="<fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/>" class="input-disabled" disabled/></td>
	                <td><input size="30" value="${item.note}" class="input-disabled" disabled/></td>
                </tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr class="end-table"><td colspan="7">&nbsp;</td></tr>
				</tfoot>
				</table>
			</div>
		</div>
		<div id="result" dojoType="ContentPane" label="<spring:message code='workorder.result'/>" class="tab-pages" refreshOnShow="true">
  			<div class="toolbar-clean">
  				<div class="toolbar-clean">
		    		<div class="item-navigator">&nbsp;</div>
		    	</div>
		    	<table class="table-list" id="lineItemResult" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
		    	<thead>
		    	<tr>
					<th width="1%">&nbsp;</th>
					<th><spring:message code="product.name"/></th>
                   	<th width="8%"><spring:message code='container'/></th>
					<th width="5%"><spring:message code="product.serial"/></th>
					<th width="5%"><spring:message code="product.uom"/></th>
                    <th width="5%"><spring:message code='sirius.qty'/></th>
                    <th width="50%"><spring:message code='sirius.note'/></th>
				</tr>
				</thead>
				<tbody id="lineItemResult">
				<c:forEach items="${workOrder_edit.resultItems}" var="item" varStatus="stat">
				<tr>
					<td>&nbsp;</td>
	                <td><input size="26" value="${item.product.name}" class="input-disabled" disabled/></td>
	                <td><input size="26" value="${item.container.name}" class="input-disabled" disabled/></td>
	                <td><input size="12" value="${item.lot.serial}" class="input-disabled" disabled/></td>
	                <td><input size="12" value="${item.product.unitOfMeasure.measureId}" class="input-disabled" disabled/></td>
	                <td><input size="12" value="<fmt:formatNumber value='${item.quantity}' pattern=',##0.00'/>" class="input-disabled" disabled/></td>
	                <td><input size="30" value="${item.note}" class="input-disabled" disabled/></td>
                </tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr class="end-table"><td colspan="7">&nbsp;</td></tr>
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

	$('.item-button-print').click(function(){
		printSO();
	});

	$('.close').click(function(){
		const confirmDialog = $('<div><spring:message code="notif.proceed"/> (<spring:message code="sirius.close"/>) ?</div>').dialog(
		{
			autoOpen: false, title: '<spring:message code="sirius.close"/>', modal:true,
			buttons: {
				'<spring:message code="sirius.yes"/>': function() {
					$(this).dialog('close');
					closeSO('CLOSE');
				},
				'<spring:message code="sirius.no"/>': function() {
					$(this).dialog('close');
				}
			}
		});

		confirmDialog.dialog('open');
	});

	$('.canceled').click(function(){
		const confirmDialog = $('<div><spring:message code="notif.proceed"/> (<spring:message code="sirius.canceled"/>) ?</div>').dialog(
		{
			autoOpen: false, title: '<spring:message code="sirius.canceled"/>', modal:true,
			buttons: {
				'<spring:message code="sirius.yes"/>': function() {
					$(this).dialog('close');
					closeSO('CANCELED');
				},
				'<spring:message code="sirius.no"/>': function() {
					$(this).dialog('close');
				}
			}
		});

		confirmDialog.dialog('open');
	});
});

function validateForm() {
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

function printSO() {
	var taxRate = parseFloat($('#taxRate').val().toNumber());
	if(taxRate > 0)
		window.location="<c:url value='/page/salesorderprint.htm?id=${salesOrder_edit.id}&printType=1'/>";
	else
		window.location="<c:url value='/page/salesorderprint.htm?id=${salesOrder_edit.id}&printType=2'/>";
}

function closeSO(soStatus) {
	$.ajax({
		url:"<c:url value='/page/salesorderclose.htm?id=${salesOrder_edit.id}&soStatus='/>"+soStatus,
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