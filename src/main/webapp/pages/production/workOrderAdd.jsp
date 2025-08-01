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
				<tr>
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
						<input id="workStart" name="workStart" class="clockpicker" value="07:00" size="4" />
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="workorder.finish.date"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<input id="finishDate" name="finishDate" class="datepicker" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>"/>
						<input id="workEnd" name="workEnd" class="clockpicker" value="17:00" size="4" />
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
		<div id="convert" dojoType="ContentPane" label="Convert From" class="tab-pages" refreshOnShow="true">
  			<div class="toolbar-clean">
  				<div class="toolbar-clean">
  					<a class="item-button-new" target="Convert"><span><spring:message code="sirius.row.new"/></span></a>
  					<a class="item-button-delete" target="Convert"><span><spring:message code="sirius.row.delete"/></span></a>
		    		<div class="item-navigator">&nbsp;</div>
		    	</div>
		    	<table class="table-list" id="lineItemConvert" cellspacing="0" cellpadding="0" align="center" style="width:100%;">
		    	<thead>
		    	<tr>
					<th width="1%"><input id="checklineConvert" value="lineConvert" class="checkallConvert" type="checkbox"/></th>
					<th><spring:message code="product.name"/></th>
                   	<th width="8%"><spring:message code='container'/></th>
					<th width="5%"><spring:message code="product.onhand"/></th>
					<th width="5%"><spring:message code="product.lot"/></th>
					<th width="50%"><spring:message code="product.uom"/></th>
                    <th width="5%"><spring:message code='sirius.qty'/></th>
				</tr>
				</thead>
				<tbody id="lineItemConvert">
				</tbody>
				<tfoot>
					<tr class="end-table"><td colspan="7">&nbsp;</td></tr>
				</tfoot>
				</table>
			</div>
		</div>
		<div id="result" dojoType="ContentPane" label="Convert From" class="tab-pages" refreshOnShow="true">
  			<div class="toolbar-clean">
  				<div class="toolbar-clean">
  					<a class="item-button-new" target="Result"><span><spring:message code="sirius.row.new"/></span></a>
  					<a class="item-button-delete" target="Result"><span><spring:message code="sirius.row.delete"/></span></a>
		    		<div class="item-navigator">&nbsp;</div>
		    	</div>
		    	<table class="table-list" id="lineItemResult" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
		    	<thead>
		    	<tr>
			    	<th colspan="2"><spring:message code='sirius.code'/></th>
                     	<th colspan="2"><spring:message code='sirius.name'/></th>
                     	<th width="9%"><spring:message code='category'/></th>
                       <th width="9%"><spring:message code='sirius.uom'/></th>
                       <th width="10%"><spring:message code='facility'/></th>
                       <th width="10%"><spring:message code='grid'/></th>
                       <th width="10%"><spring:message code='container'/></th>
                       <th width="11%"><spring:message code='plafon.available'/></th>
                       <th width="10%"><spring:message code='sirius.qty'/></th>
				</tr>
				</thead>
				<tbody id="lineItemResult">
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
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
$(function(){
	
	$('.item-button-save').click(function(){
		if(validateForm()) {
			save();
		}
	});
	
	$('.item-button-new').click(function() {
		addLineItem($(this).attr('target'));
	});
	
	$('.checkall').click(function () {
        $('.checkConvert').prop("checked", this.checked);
    });
	
	$('.delconvert').click(function () {
        $('.checkConvert').each(function(){
            if(this.checked){
            	this.checked = false;
                $(this).parent().parent().remove();
                
                let indexAttr = $(this).attr('index');
                document.querySelectorAll('.barcodeGroup'+indexAttr).forEach(e => e.remove());
            }
        });
        
        $('.checkallConvert').prop("checked", false);
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
		target: 'operator', // Id Dropdown (Select) element
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
		fromRoleType: 8, // Operator
		toRoleType: 2, // Company
		relationshipType: 2, // Employment Relationship
	};

	openpopup(buildUrl(baseUrl, params));
}

async function populateGrid() {
	const facilityId = $('#source').val();
	if (!facilityId) {
		alert('<spring:message code="transferorder.warehousefrom"/> ' + '<spring:message code="notif.empty"/> !');
		return;
	}

	try {
		const filters = [
			{ key: 'facility.id', type: 'long', operator: 'EQUAL', value: facilityId }
		];

		const grids = await Generic.list(
			'com.siriuserp.sdk.dm.Grid',
			filters,
			[{ key: 'name', direction: 'ASC' }]
		);

		const $grid = $('#gridFrom').empty();
		grids.forEach(g => {
			$('<option>')
				.val(g.gridId)
				.text(g.gridName)
				.appendTo($grid);
		});
	} catch (err) {
		console.error('Gagal mengambil data grid:', err);
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
	
	const checkbox = List.get('<input type="checkbox" class="check"/>','check['+index +']');	
	const container = List.get('<select class="combobox"/>','container['+index+']');
	const containerImg = List.img('Container', index, 'opencontainerpopup("'+index+'")');
	const product = List.get('<select class="combobox-ext product"/>','product['+index+']');
	const productImg = List.img('Product', index, 'openProduct("'+index+'")');
	const gridFrom = List.get('<input class="input-disabled" disabled="true" size="12"/>','gridFrom['+index+']');
	const gridTo = List.get('<input class="input-disabled" disabled="true" size="12"/>','gridTo['+index+']');
	const source = List.get('<select class="input-disabled combobox" readonly="true"/>','source['+index+']'); 
	const onHand = List.get('<input class="number-disabled" disabled="true" size="12"/>','onhand['+index+']');
	const uom = List.get('<input class="input-disabled" disabled="true" size="12"/>','uom['+index+']');
	const quantity = List.get('<input class="input-decimal quantities" onchange='+"checkQuantity(this);"+' size="12"/>','quantity['+index+']', '0');
	const serial = List.get('<input disabled="true" size="12" type="hidden"/>','serialCheck['+index+']');
	
	$tr.append(List.col([checkbox]));
	$tr.append(List.col([product, productImg]));
	$tr.append(List.col([source]));
	$tr.append(List.col([onHand, serial]));
	$tr.append(List.col(['&nbsp;']));
	$tr.append(List.col([uom]));
	$tr.append(List.col([quantity]));

	$tbody.append($tr);
	$table.append($tbody);
	
	index++;
	
	$(".input-number").bind(inputFormat);
	$(".input-decimal").bind(inputFormat);
}

function openProduct(index) {
	  
	const baseUrl = '<c:url value="/page/popupproductfortransfer.htm"/>';
	const params = {
		target: 'product[' + index + ']',
		index: index,
		organization: $('#org').val(),
	};
	openpopup(buildUrl(baseUrl, params));
}

function checkQuantity(element) 
{ 
	let idxRef = $(element).attr('index');
	let $input = $('#quantity\\[' + idxRef + '\\]');
	
	var onHand = document.getElementById('onhand[' + idxRef + ']').value.toNumber();
	var qty = document.getElementById('quantity[' + idxRef + ']').value.toNumber();
	var serialCheck = document.getElementById('serialCheck[' + idxRef + ']').value;
	
	if(onHand < qty) 
	{
		alert('<spring:message code="transferorder.qty"/> <spring:message code="notif.greater"/> <spring:message code="product.onhand"/> !!!');
		document.getElementById('quantity[' + idxRef + ']').value = onHand;
		
		return;
	}

	if(serialCheck.toLowerCase() === "true") {
		$('#iBody' + idxRef + ' tr.barcode').remove();

		for (let i = 0; i < element.value; i++) {
			addLine(idxRef, index);
			$('#iBody' + idxRef + ' tr:last').addClass('barcode');
		}

		$('#product\\[' + idxRef + '\\]').prop('disabled', true);
		$('#container\\[' + idxRef + '\\]').prop('disabled', true);
		$('#source\\['    + idxRef + '\\]').prop('disabled', true);

		$input.attr('data-name', 'quantityDel');
	}
}

function addLine($idxRef, idx)
{
	$productId = $('#product\\['+$idxRef+'\\]').val();
	$sourceId = $('#source\\['+$idxRef+'\\]').val();
	$containerId = $('#container\\['+$idxRef+'\\]').val();
	$uom = $('#uom\\['+$idxRef+'\\]').val();
	
	$tbody = $('#iBody'+$idxRef);
	$tr = $('<tr/>').addClass('barcodeGroup' + $idxRef);
    
    $product = List.get('<input class="input" size="12" type="hidden"/>','product['+idx+']', $productId);
    $source = List.get('<input class="input" size="12" type="hidden"/>','source['+idx+']', $sourceId);
    $container = List.get('<input class="input" size="12" type="hidden"/>','container['+idx+']', $containerId);
    $lotCode = List.get('<input class="inputbox-small input-disabled" size="12" type="text" readonly/>','lotCode['+idx+']');

    $barcode = List.get('<select class="combobox barcodes" onchange="calculateAdjust(\'' + idx + '\');"></select>','serial[' + idx + ']');
	$barcodeImg = List.img('<spring:message code="barcode"/>', idx, 'openBarcode("'+idx+'","'+$productId+'","'+$sourceId+'")');
	
	$onhand = List.get('<input type="text" class="input-decimal input-disabled" disabled="true" size="12"/>','onHand['+idx+']', '0.00');
	$uom = List.get('<input type="text" class="input-disabled" disabled="true" size="12"/>','uom['+idx+']', $uom);

	$quantity = List.get('<input class="input-decimal quantities" size="12"/>','quantity['+idx+']', '0.00');
	
	$tr.append(List.col([$product]));
	$tr.append(List.col([$source]));
	$tr.append(List.col([$barcode, $barcodeImg], '', 'text-align: right;'));
	$tr.append(List.col([$onhand]));
	$tr.append(List.col([$lotCode]));
	$tr.append(List.col([$uom]));
	$tr.append(List.col([$quantity]));
	
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

</script>