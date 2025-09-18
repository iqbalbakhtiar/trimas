<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/workorderview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>
<div class="main-box">
<sesform:form id="addForm" name="addForm" method="post" modelAttribute="workOrder_form" enctype="multipart/form-data">
	<table width="100%" border="0">
		<tr>
			<td width="60%">
				<table style="border:none" width="100%">
				<tr>
					<td width="34%" align="right"><spring:message code="sirius.code"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input class="inputbox input-disabled" value="Auto Number" disabled/></td>
					<td width="1%"><form:errors path="code"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="organization"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="org" path="organization" cssClass="combobox-ext">
							<c:if test='${not empty workOrder_form.organization}'>
								<form:option value='${workOrder_form.organization.id}' label='${workOrder_form.organization.fullName}'/>
							</c:if>
						</form:select>
						<a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title="<spring:message code='organization'/>" />
					</td>
				</tr>
				<tr style="display: none;">
					<td align="right"><spring:message code="facility"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="facility" path="facility" cssClass="combobox-ext">
							<c:if test='${not empty workOrder_form.facility}'>
								<form:option value='${workOrder_form.facility.id}' label='${workOrder_form.facility.fullName}'/>
							</c:if>
						</form:select>
						<a class="item-popup" onclick="openfacility();" title="<spring:message code='organization'/>"/>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="sirius.date"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="date" name="date" class="datepicker" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="workorder.start.date"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<input id="startDate" name="startDate" class="datepicker" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>"/>
						&nbsp;
						<input id="workStart" name="workStart" class="clockpicker" value="07:00" size="4" />
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="workorder.finish.date"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<input id="finishDate" name="finishDate" class="datepicker" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>"/>
						&nbsp;
						<input id="workEnd" name="workEnd" class="clockpicker" value="17:00" size="4" />
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="machine"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="machine" path="machine" cssClass="combobox-ext">
						</form:select>
						<a class="item-popup" onclick="openMachine()" title="<spring:message code='machine'/>" />
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="sirius.operator"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="operator" path="operator" cssClass="combobox-ext">
						</form:select>
						<a class="item-popup" onclick="openOperator()" title="<spring:message code='sirius.operator'/>" />
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="approver"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="approver" path="approver" cssClass="combobox-ext">
						</form:select>
						<a class="item-popup" onclick="openApprover()" title="<spring:message code='approver'/>" />
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
  					<a class="item-button-new" target="Convert"><span><spring:message code="sirius.row.new"/></span></a>
  					<a class="item-button-delete" target="Convert"><span><spring:message code="sirius.row.delete"/></span></a>
		    		<div class="item-navigator">&nbsp;</div>
		    	</div>
		    	<table class="table-list" id="lineItemConvert" cellspacing="0" cellpadding="0" align="center" style="width:100%;">
		    	<thead>
		    	<tr>
					<th width="1%"><input id="checklineConvert" target="Convert" class="checkall" type="checkbox"/></th>
					<th><spring:message code="product.name"/></th>
                   	<th width="8%"><spring:message code='container'/></th>
					<th width="5%"><spring:message code="product.onhand"/></th>
					<th width="5%"><spring:message code="product.lot"/></th>
					<th width="5%"><spring:message code="product.uom"/></th>
                    <th width="5%"><spring:message code='sirius.qty'/></th>
                    <th width="50%"><spring:message code='sirius.note'/></th>
				</tr>
				</thead>
				<tbody id="lineItemConvert">
				</tbody>
				<tfoot>
					<tr class="end-table"><td colspan="8">&nbsp;</td></tr>
				</tfoot>
				</table>
			</div>
		</div>
		<div id="result" dojoType="ContentPane" label="<spring:message code='workorder.result'/>" class="tab-pages" refreshOnShow="true">
  			<div class="toolbar-clean">
  				<div class="toolbar-clean">
  					<a class="item-button-new" target="Result"><span><spring:message code="sirius.row.new"/></span></a>
  					<a class="item-button-delete" target="Result"><span><spring:message code="sirius.row.delete"/></span></a>
		    		<div class="item-navigator">&nbsp;</div>
		    	</div>
		    	<table class="table-list" id="lineItemResult" cellspacing="0" cellpadding="0" align="center" style="width:100%;">
		    	<thead>
		    	<tr>
					<th width="1%"><input id="checklineConvert" target="Result" class="checkall" type="checkbox"/></th>
					<th><spring:message code="product.name"/></th>
                   	<th width="8%"><spring:message code='container'/></th>
					<th width="5%"><spring:message code="product.onhand"/></th>
					<th width="5%"><spring:message code="product.lot"/></th>
					<th width="5%"><spring:message code="product.uom"/></th>
                    <th width="5%"><spring:message code='sirius.qty'/></th>
                    <th width="50%"><spring:message code='sirius.note'/></th>
				</tr>
				</thead>
				<tbody id="lineItemResult">
				</tbody>
				<tfoot>
					<tr class="end-table"><td colspan="8">&nbsp;</td></tr>
				</tfoot>
				</table>
			</div>
		</div>
		<div id="waste" dojoType="ContentPane" label="<spring:message code='workorder.waste'/>" class="tab-pages" refreshOnShow="true">
  			<div class="toolbar-clean">
  				<div class="toolbar-clean">
  					<a class="item-button-new" target="Waste"><span><spring:message code="sirius.row.new"/></span></a>
  					<a class="item-button-delete" target="Waste"><span><spring:message code="sirius.row.delete"/></span></a>
		    		<div class="item-navigator">&nbsp;</div>
		    	</div>
		    	<table class="table-list" id="lineItemWaste" cellspacing="0" cellpadding="0" align="center" style="width:100%;">
		    	<thead>
		    	<tr>
					<th width="1%"><input id="checklineWaste" target="Waste" class="checkall" type="checkbox"/></th>
					<th><spring:message code="product.name"/></th>
                   	<th width="8%"><spring:message code='container'/></th>
					<th width="5%"><spring:message code="product.onhand"/></th>
					<th width="5%"><spring:message code="product.lot"/></th>
					<th width="5%"><spring:message code="product.uom"/></th>
                    <th width="5%"><spring:message code='sirius.qty'/></th>
                    <th width="50%"><spring:message code='sirius.note'/></th>
				</tr>
				</thead>
				<tbody id="lineItemWaste">
				</tbody>
				<tfoot>
					<tr class="end-table"><td colspan="8">&nbsp;</td></tr>
				</tfoot>
				</table>
			</div>
		</div>
		<div id="reject" dojoType="ContentPane" label="<spring:message code='workorder.reject'/>" class="tab-pages" refreshOnShow="true">
  			<div class="toolbar-clean">
  				<div class="toolbar-clean">
  					<a class="item-button-new" target="Reject"><span><spring:message code="sirius.row.new"/></span></a>
  					<a class="item-button-delete" target="Reject"><span><spring:message code="sirius.row.delete"/></span></a>
		    		<div class="item-navigator">&nbsp;</div>
		    	</div>
		    	<table class="table-list" id="lineItemReject" cellspacing="0" cellpadding="0" align="center" style="width:100%;">
		    	<thead>
		    	<tr>
					<th width="1%"><input id="checklineReject" target="Reject" class="checkall" type="checkbox"/></th>
					<th><spring:message code="product.name"/></th>
                   	<th width="8%"><spring:message code='container'/></th>
					<th width="5%"><spring:message code="product.onhand"/></th>
					<th width="5%"><spring:message code="product.lot"/></th>
					<th width="5%"><spring:message code="product.uom"/></th>
                    <th width="5%"><spring:message code='sirius.qty'/></th>
                    <th width="50%"><spring:message code='sirius.note'/></th>
				</tr>
				</thead>
				<tbody id="lineItemWaste">
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
		if(validation())
			save();
	});
	
	$('.item-button-new').click(function() {
		addLineItem($(this).attr('target'));
	});
	
	$('.checkall').click(function () {
        $('.check'+$(this).attr('target')).prop("checked", this.checked);
    });
	
	$('.item-button-delete').click(function () {
		var target = $(this).attr('target');
        $('.check'+target).each(function(){
            if(this.checked){
            	this.checked = false;
                $(this).parent().parent().remove();
                
                let indexAttr = $(this).attr('index');
                document.querySelectorAll('.barcodeGroup'+indexAttr).forEach(e => e.remove());
            }
        });
        
        $('.checkall').prop("checked", false);
    });
});

function openFacility()
{
	var org = document.getElementById('org');
	if(org.value == '')
	{
		alert('<spring:message code="goodsreceipt.organization"/> <spring:message code="notif.empty"/> !');
		return;
	}
	
	openpopup("<c:url value='/page/popupfacilityview.htm?target=facility&active=true&organization='/>"+org.value);
}

function openOperator() {
	if (!$('#org').val()) {
		alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
		return;
	}

	const orgId = $('#org').val();
	const baseUrl = '<c:url value="/page/popuppartyrelationview.htm"/>';
	const params = {
		target: 'operator',
		organization: orgId,
		fromRoleType: 3,
		toRoleType: 2,
		relationshipType: 2,
		base: false
	};

	openpopup(buildUrl(baseUrl, params));
}

function openMachine() {
	const baseUrl = '<c:url value="/page/popupmachineview.htm"/>';
	const params = {
		target: 'machine',
		enabled: true
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
		fromRoleType: 8, // Operator
		toRoleType: 2, // Company
		relationshipType: 2, // Employment Relationship
	};

	openpopup(buildUrl(baseUrl, params));
}

function validation() {
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

    // Validasi operator
    var operator = $('#operator').val();
    if (operator == null || operator === "") {
        alert('<spring:message code="sirius.operator"/> <spring:message code="notif.empty"/> !');
        return false;
    }

    // Validasi approver
    /* var approver = $('#approver').val();
    if (approver == null || approver === "") {
        alert('<spring:message code="approver"/> <spring:message code="notif.empty"/> !');
        return false;
    } */
    
    if ($('#lineItemConvert tr').length === 0) {
        alert('<spring:message code="notif.add"/> <spring:message code="salesorder.lineitem"/> <spring:message code="notif.select2"/> !');
        return false;
    }
    
    if ($('#lineItemResult tr').length === 0) {
        alert('<spring:message code="notif.add"/> <spring:message code="salesorder.lineitem"/> <spring:message code="notif.select2"/> !');
        return false;
    }

    var isValid = true;
    $('#lineItemConvert tr').each(function(index){
        var $row = $(this);
        
        /* var product = $row.find('select[id^="product["]').val();
        if (product == null || product === "") {
            alert('<spring:message code="product"/> <spring:message code="notif.empty"/> ! (<spring:message code="salesorder.lineitem"/> ' + (index + 1) + ')');
            isValid = false;
            return false;
        }

        var qtyStr = $row.find('input[id^="quantity["]').val().replace(/,/g, '');
        var qty = parseFloat(qtyStr);
        if (isNaN(qty) || qty <= 0) {
            alert('<spring:message code="sirius.qty"/> <spring:message code="notif.empty"/> ! (<spring:message code="salesorder.lineitem"/> ' + (index + 1) + ')');
            isValid = false;
            return false;
        } */
    });

    if (!isValid)
        return false;

    return true;
}

function save() {
	$.ajax({
		url:"<c:url value='/page/workorderadd.htm'/>",
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
					window.location="<c:url value='/page/workorderpreedit.htm?id='/>"+json.id;
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

var index = 0;
function cleanItems(target) {
	$('.checkall'+target).click();
	$('.del'+target).click();
	index = 0;
}

function addLineItem(target)
{
	$table = $('#lineItem'+target);
	$tbody = $('<tbody id="iBody'+index+'"/>');
	$tr = $('<tr/>');
	$type = 'CONVERT';
	$containerAttr = 'class="input-disabled combobox"';
	
	if(target.toLowerCase() === 'result') {
		$type = 'RESULT';
		$containerAttr = 'class="combobox"';
	}
	else if(target.toLowerCase() === 'waste') {
		$type = 'WASTE';
		$containerAttr = 'class="combobox"';
	}
	else if(target.toLowerCase() === 'reject') {
		$type = 'REJECT';
		$containerAttr = 'class="combobox"';
	}
	
	const checkbox = List.get('<input type="checkbox" class="check'+target+'"/>','check['+index +']');	
	const product = List.get('<select class="combobox-ext product"/>','product['+index+']');
	const productImg = List.img('Product', index, 'openProduct("'+index+'","'+$type+'")');
	const gridFrom = List.get('<input class="input-disabled" disabled="true" size="12"/>','gridFrom['+index+']');
	const gridTo = List.get('<input class="input-disabled" disabled="true" size="12"/>','gridTo['+index+']');
	const source = List.get('<select readonly="true"'+$containerAttr+'/>','container['+index+']'); 
	const sourceImg = List.img('Container', index, 'openContainer("'+index+'")');
	const onHand = List.get('<input class="number-disabled" disabled="true" size="12"/>','onhand['+index+']');
	const serial = List.get('<input disabled="true" size="12" type="hidden"/>','serialCheck['+index+']');
	const uom = List.get('<input class="input-disabled" disabled="true" size="12"/>','uom['+index+']');
	const quantity = List.get('<input class="input-decimal quantities" conversionType="'+$type+'" onchange='+"checkQuantity(this);"+' size="12"/>','quantity['+index+']', '0');
	const note = List.get('<input size="30"/>','itemNote['+index+']');
	const conversionType = List.get('<input readonly="true" style="display: none;"/>','conversionType['+index+']', $type);
	
	$tr.append(List.col([checkbox]));
	$tr.append(List.col([product, productImg]));

	if($type == 'CONVERT')
		$tr.append(List.col([source]));
	else
		$tr.append(List.col([source, sourceImg]));
	
	$tr.append(List.col([onHand, serial]));
	$tr.append(List.col(['&nbsp;']));
	$tr.append(List.col([uom]));
	$tr.append(List.col([quantity]));
	$tr.append(List.col([note, conversionType]));

	$tbody.append($tr);
	$table.append($tbody);
	
	index++;
	
	$(".input-number").bind(inputFormat);
	$(".input-decimal").bind(inputFormat);
}

function openProduct(index, type) {
	  
	var baseUrl = '<c:url value="/page/popupproductfortransfer.htm"/>';
	
	if(type != 'CONVERT')
		baseUrl = '<c:url value="/page/popupproductview.htm"/>';
	
	const params = {
		target: 'product[' + index + ']',
		index: index,
		status: true,
		organization: $('#org').val(),
	};
	openpopup(buildUrl(baseUrl, params));
}

function openContainer(index){
	const baseUrl = '<c:url value="/page/popupcontainerview.htm"/>';
	const params = {
		target: 'container[' + index + ']',
		index: index
	};
	openpopup(buildUrl(baseUrl, params));
}

function checkQuantity(element) 
{ 
	let idxRef = $(element).attr('index');
	let conversionType = $(element).attr('conversionType');
	let container = $('#container\\[' + idxRef + '\\]');
	let $input = $('#quantity\\[' + idxRef + '\\]');
	
	var onHand = document.getElementById('onhand[' + idxRef + ']').value.toNumber();
	var qty = document.getElementById('quantity[' + idxRef + ']').value.toNumber();
	var serialCheck = document.getElementById('serialCheck[' + idxRef + ']').value;
	
	if(!container.val())
	{
		alert('<spring:message code="notif.select1"/> <spring:message code="container"/> <spring:message code="notif.select2"/> !!!');
		document.getElementById('quantity[' + idxRef + ']').value = 0.00;
		
		return;
	}
	
	if(conversionType == 'CONVERT' && onHand < qty) 
	{
		alert('<spring:message code="sirius.qty"/> <spring:message code="notif.greater"/> <spring:message code="product.onhand"/> !!!');
		document.getElementById('quantity[' + idxRef + ']').value = onHand;
		
		return;
	}

	if(serialCheck.toLowerCase() === "true") {
		$('#iBody' + idxRef + ' tr.barcode').remove();

		for (let i = 0; i < element.value; i++) {
			addLine(idxRef, index, conversionType);
			$('#iBody' + idxRef + ' tr:last').addClass('barcode');
		}

		$('#product\\[' + idxRef + '\\]').prop('disabled', true);
		$('#container\\[' + idxRef + '\\]').prop('disabled', true);
		$('#itemNote\\[' + idxRef + '\\]').prop('disabled', true);
		$('#itemNote\\[' + idxRef + '\\]').prop('style', 'display: none;');

		$input.attr('data-name', 'quantityDel');
	}
}

function addLine($idxRef, idx, convType)
{
	$productId = $('#product\\['+$idxRef+'\\]').val();
	$productName = $('#product\\['+$idxRef+'\\]').text();
	$sourceId = $('#container\\['+$idxRef+'\\]').val();
	$uom = $('#uom\\['+$idxRef+'\\]').val();
	
	$qtyAttr = 'class="input-decimal quantities"';
	$lotCodeAttr = '';
	
	if(convType == 'CONVERT') {
		$qtyAttr = 'class="input-decimal quantities input-disabled" readonly="true"';
		$lotCodeAttr = 'class="input-disabled" readonly="true"';
	}
	
	$tbody = $('#iBody'+$idxRef);
	$tr = $('<tr/>').addClass('barcodeGroup' + $idxRef);
    
    $product = List.get('<input class="input" size="12" type="hidden"/>','product['+idx+']', $productId);
    $container = List.get('<input class="input" size="12" type="hidden"/>','container['+idx+']', $sourceId);
    $lotCode = List.get('<input size="5" type="text" '+$lotCodeAttr+'/>','lotCode['+idx+']');
    $barcode = List.get('<select class="combobox barcodes" onchange="calculateAdjust(\'' + idx + '\');"></select>','serial[' + idx + ']');
	$barcodeImg = List.img('<spring:message code="barcode"/>', idx, 'openBarcode("'+idx+'","'+$productId+'","'+$sourceId+'")');
	$onhand = List.get('<input type="text" class="input-decimal input-disabled" disabled="true" size="12"/>','onHand['+idx+']', '0.00');
	$uom = List.get('<input type="text" class="input-disabled" disabled="true" size="12"/>','uom['+idx+']', $uom);
	$quantity = List.get('<input size="12" '+$qtyAttr+'/>','quantity['+idx+']', '0.00');
	$note = List.get('<input size="30"/>','itemNote['+idx+']');
	$conversionType = List.get('<input readonly="true" style="display: none;"/>','conversionType['+index+']', convType);
	
	$tr.append(List.col([$product]));
	$tr.append(List.col([$container]));
	$tr.append(List.col([$barcode, convType == 'CONVERT' ? $barcodeImg : ''], '', 'text-align: right;'));
	$tr.append(List.col([$onhand]));
	$tr.append(List.col([$lotCode]));
	$tr.append(List.col([$uom]));
	$tr.append(List.col([$quantity]));
	$tr.append(List.col([$note, $conversionType]));
	
	$tbody.append($tr);
	index++;
	
	$(".input-number").bind(inputFormat);
}

var selectedBarcodes = [];
function openBarcode(index, productId, containerId)
{
	selectedBarcodes = [];
	$.each($(".barcodes"), function(i, obj)
    {
        var idx = obj.getAttribute('index');
        if (obj.value != '')
        	selectedBarcodes.push(obj.value);
    });

	const baseUrl = '<c:url value="/page/popupinventoryitemview.htm"/>';
	const params = {
		target: 'serial[' + index + ']',
		index: index,
		group: false,
		onHand: true,
		availableSales: true,
		ref: '4Serial',
		container: containerId,
		productId: productId,
		barcodes: Array.from(new Set(selectedBarcodes))
	};
	
	openpopup(buildUrl(baseUrl, params));
}

function calculateAdjust(index) {
	var barcode = $('#serial\\['+index+'\\]').val();
	var qty = parseFloat($('#quantity\\['+index+'\\]').val().toNumber());
	var onHand = parseFloat($('#onHand\\['+index+'\\]').val().toNumber());
	
	if(!barcode && qty < 0) {
		alert('<spring:message code="sirius.qty"/> <spring:message code="notif.lower"/> 0 !!!');
		$('#quantity\\['+index+'\\]').val(0.00);
		return;
	}
}

function createBarcode(id)
{
	const confirmDialog = $('<div><spring:message code="notif.proceed"/> (<spring:message code="sirius.create"/> <spring:message code="barcode"/>) ?</div>').dialog(
	{
		autoOpen: false, title: '<spring:message code="notif.confirmation"/>', modal:true,
		buttons: {
			'<spring:message code="sirius.yes"/>': function() {
				$(this).dialog('close');

				var url = "<c:url value='/page/barcodegrouppreadd1.htm?barcodeType=WORK_ORDER&referenceId='/>"+id;
				window.location = url;
			},
			'<spring:message code="sirius.no"/>': function() {
				$(this).dialog('close');
			}
		}
	});

	confirmDialog.dialog('open');
}
</script>