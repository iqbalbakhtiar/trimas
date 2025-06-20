<%@ include file="/pages/includes/sirius-head.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/goodsissuemanualview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<c:if test='${access.add}'>
		<a class="item-button-save b_save"><span><spring:message code="sirius.save"/></span></a>
    </c:if>				
</div>

<div class="main-box">
	<sesform:form id="addForm" name="addForm" method="post" modelAttribute="transaction_form">
		<table width="100%" style="border:none">
			<tr>
				<td width="20%" nowrap="nowrap" align="right"><spring:message code="sirius.id"/> :</td>
				<td width="80%"><input value="Auto Generated" class='input-disabled' size='25' disabled/></td>
			</tr>
			<tr>
				<td nowrap="nowrap" align="right"><spring:message code="organization"/> :</td>
				<td>
					<form:select id="org" path="organization" cssClass="combobox-ext" onchange="populateWarehouse()">
						<c:if test='${not empty transaction_form.organization}'>
							<form:option value='${transaction_form.organization.id}' label='${transaction_form.organization.fullName}' />
						</c:if>
					</form:select>
					<img src="assets/icons/list_extensions.gif" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" style="CURSOR:pointer;" title="Company Structure" />
				</td>
			</tr>
			<tr>
				<td nowrap="nowrap" align="right"><spring:message code="sirius.date"/> :</td>
				<td><input id="date" name="date" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>" class="datepicker"/></td>
			</tr>
			<tr>
				<td nowrap="nowrap" align="right"><spring:message code="sirius.type"/> :</td>
				<td>
					<form:select id="issueType" path="issueType" cssClass="combobox-min" onchange="changeType(this);">
						<c:forEach items="${types}" var="type">
							<form:option value="${type}" label="${type.capitalizedName}"/>
						</c:forEach>
					</form:select>
				</td>
			</tr>
			<tr>
				<td nowrap="nowrap" align="right"><spring:message code="goodsissue.recipient"/> :</td>
				<td>
					<form:select id="recipient" path="recipient" cssClass="combobox-ext">
					</form:select>
					<img src="assets/icons/list_extensions.gif" onclick="openRecipient();" style="CURSOR:pointer;" title="Recipient" />
				</td>
			</tr>
			<tr>
				<td nowrap="nowrap" align="right"><spring:message code="transferorder.warehousefrom"/> :</td>
				<td>
					<form:select id="source" path="source" cssClass="combobox-ext" onchange="populateGrid();">
					</form:select>
				</td>
			</tr>
			<tr>
				<td nowrap="nowrap" align="right"><spring:message code="transferorder.gridfrom"/> :</td>
				<td>
					<select id="gridFrom" class="combobox-ext">
					</select>
				</td>
			</tr>
			<tr>
				<td nowrap="nowrap" align="right"><spring:message code="sirius.note"/> :</td>
				<td><form:textarea path="note" cols="55" rows="7"/></td>
			</tr>
		</table>
		<div class="toolbar-clean">
			<div class="item-navigator">&nbsp;</div>
			<a class="item-button-new" href="javascript:addLineItem();"><span><spring:message code="sirius.row.new"/></span></a>
			<a class="item-button-delete" ><span><spring:message code="sirius.row.delete"/></span></a>
		</div>
		<table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" width="100%"> 
			<thead>
				<tr>
					<th width="2%"><input id ="checkline" value="line" class="checkall" type="checkbox"/></th>
					<th ><spring:message code="product.name"/></th>
					<th><spring:message code="transferorder.gridfrom"/></th>
					<th><spring:message code="transferorder.containerfrom"/></th>
<%--					<th><spring:message code="transferorder.gridto"/></th>--%>
<%--					<th><spring:message code="transferorder.containerto"/></th>--%>
					<th width="12%"><spring:message code="product.onhand"/></th>
					<th width="12%"><spring:message code="product.uom"/></th>
					<th width="12%"><spring:message code="transferorder.qty"/></th>
				</tr>
			</thead>
			<tfoot>
				<tr class="end-table"><td colspan="16">&nbsp;</td></tr>
			</tfoot>
		</table>
	</sesform:form>
</div>

<%@ include file="/pages/includes/sirius-foot.jsp"%>
<script type="text/javascript">
	$(function(){
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: '${title}',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});
    
		$('.b_save').click(function(e){
			$status = true;
			
			$('.quantities').each(function()
			{
				$idx = $(this).attr('index');
				$qty = $(this).val().toNumber();
				$prd = $('#productSerial\\['+$idx+'\\] option:selected').text();
				
	           	if(!$qty || parseFloat($qty) == 0) {
					alert('<spring:message code="transferorder.qty"/> '+ $prd +' <spring:message code="notif.zero"/> !!!');
					return $status = false;
				}
			});
			
			if($status) {
				$('[data-name="quantityDel"]').remove();
				$.ajax({
					url:"<c:url value='/page/goodsissuemanualadd.htm'/>",
					data:$('#addForm').serialize(),
					type : 'POST',
					dataType : 'json',
					beforeSend : beforeSend($dialog, '<spring:message code="notif.saving"/>'),
					success : function(json) {
						if(json)
						{
							if(json.status == 'OK')
							{
								$dialog.dialog('close');
								window.location="<c:url value='/page/goodsissuemanualpreedit.htm?id='/>"+json.id;
							}
							else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
						}
					}
				});	
			}
		});

		// Onclick Section
		$('.checkall').click(function () {
            $('.check').prop("checked", this.checked);
        });

        $('.item-button-delete').click(function () {
            $('.check').each(function(){
                if(this.checked){
                    this.checked = false;
                    
                    $(this).parent().parent().remove();
                    
                    let indexAttr = $(this).attr('index');
                    
                    document.querySelectorAll('.barcodeGroup'+indexAttr).forEach(e => e.remove());
                    
                }
            });
                
            $('.checkall').prop("checked", false);   
		});

		// OnFocus Section
		$('#source').focus(resetLineItems);

		// Onchange Section
		populateWarehouse().then(() => $('#source').change());
		$('#issueType').change();
	});

	async function populateWarehouse() {
		const orgId = $('#org').val();
		if (!orgId) {
			alert('<spring:message code="organization"/> <spring:message code="notif.empty"/> !');
			return;
		}

		try {
			const keys   = ['owner.id',        'facilityType.name'];
			const types  = ['long',             'string'];
			const values = [orgId,              'WAREHOUSE'];

			const facilities = await Generic.list(
					'com.siriuserp.sdk.dm.Facility',
					keys, types, values
			);

			const $s = $('#source').empty()
			facilities.forEach(f=>{
				$('<option>').val(f.facilityId).text(f.facilityName).appendTo($s)
			})
		}
		catch (err) {
			alert('Gagal mengambil facility:', err);
		}
	}

	async function populateGrid() {
		const facilityId = $('#source').val();
		if (!facilityId) {
			alert('<spring:message code="transferorder.warehousefrom"/> ' + '<spring:message code="notif.empty"/> !');
			return;
		}

		try {
			// ambil semua Grid di facility tertentu
			const grids = await Generic.list(
					'com.siriuserp.sdk.dm.Grid',
					['facility.id'],          // keys
					['long'],                 // types
					[facilityId]              // values
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

	function copySourceToDestination(el){
		const val = el.value;
		const txt = el.options[el.selectedIndex].text;
		$('#destination').empty().append(
				$('<option>').val(val).text(txt)
		);
	}

	function resetLineItems(){
		$('.barcode').remove();
		$('.check').prop('checked', true);
		$('.item-button-delete').click();
	}

	var index = 0;
	function addLineItem()
	{
		var facility = document.getElementById('source');
		if (!facility.value)
		{
			alert('<spring:message code="transferorder.warehousefrom"/> <spring:message code="notif.empty"/> !');
			return;
		}
		
		var grid = document.getElementById('gridFrom');
		if (!grid.value)
		{
			alert('<spring:message code="transferorder.gridfrom"/> <spring:message code="notif.empty"/> !');
			return;
		}
		
		$table = $('#lineItemTable');
		$tbody = $('<tbody id="iBody'+index+'"/>');
		$tr = $('<tr/>');
		
		const checkbox = List.get('<input type="checkbox" class="check"/>','check['+index +']');	
		const container = List.get('<select class="combobox"/>','container['+index+']');
		const containerImg = List.img('Container', index, 'opencontainerpopup("'+index+'")');
		const product = List.get('<select class="combobox-ext product"/>','product['+index+']');
		const productImg = List.img('Product', index, 'openproduct("'+index+'")');
		const gridFrom = List.get('<input class="input-disabled" disabled="true" size="12"/>','gridFrom['+index+']');
		const gridTo = List.get('<input class="input-disabled" disabled="true" size="12"/>','gridTo['+index+']');
		const source = List.get('<select class="input-disabled combobox" readonly="true"/>','source['+index+']'); 
		const onHand = List.get('<input class="number-disabled" disabled="true" size="12"/>','onhand['+index+']');
		const uom = List.get('<input class="input-disabled" disabled="true" size="12"/>','uom['+index+']');
		const quantity = List.get('<input class="input-number quantities" onchange='+"checkQuantity(this);"+' size="12"/>','quantity['+index+']', '0');
		const serial = List.get('<input disabled="true" size="12" type="hidden"/>','serialCheck['+index+']');
		
		$tr.append(List.col([checkbox]));
		$tr.append(List.col([product, productImg]));
		$tr.append(List.col([gridFrom]));
		$tr.append(List.col([source]));
		// $tr.append(List.col([gridTo]));
		// $tr.append(List.col([container, containerImg]));
		$tr.append(List.col([onHand, serial]));
		$tr.append(List.col([uom]));
		$tr.append(List.col([quantity]));

		$tbody.append($tr);
		$table.append($tbody);
		
		index++;
		
		$(".input-number").bind(inputFormat);
		$(".input-decimal").bind(inputFormat);
	}

	function opencontainerpopup(idx){
		const baseUrl = '<c:url value="/page/popupcontainerview.htm"/>';
		const params = {
			target: 'container[' + idx + ']',
			index: idx,
			facility: $('#destination').val()
		};
		openpopup(buildUrl(baseUrl, params));
	}

	function openproduct(index)
	{
		const org = document.getElementById('org');
		const grid = document.getElementById('gridFrom');
		
		openpopup("<c:url value='/page/popupproductfortransfer.htm?target=product['/>"+index+"]&index="+index+"&organization="+org.value+"&grid="+grid.value);
	}

	function checkQuantity(element) 
	{ 
		let idxRef = $(element).attr('index');
		let $input = $('#quantity\\[' + idxRef + '\\]');
		
/*		if (!$('#container\\[' + idxRef + '\\]').val())
		{
			alert('<spring:message code="transferorder.containerto"/> <spring:message code="notif.empty"/> !');
			$input.val("0");
			
			return;
		}*/
		
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
	    
	    $barcode = List.get('<select class="combobox barcodes" onchange="calculateAdjust(\'' + idx + '\');"></select>','serial[' + idx + ']');
		$barcodeImg = List.img('<spring:message code="barcode"/>', idx, 'openBarcode("'+idx+'","'+$productId+'","'+$sourceId+'")');
		
		$onhand = List.get('<input type="text" class="input-number input-disabled" disabled="true" size="12"/>','onHand['+idx+']', '0.00');
		$uom = List.get('<input type="text" class="input-disabled" disabled="true" size="12"/>','uom['+idx+']', $uom);

		$quantity = List.get('<input class="input-number quantities" size="12"/>','quantity['+idx+']', '0.00');
		
		$tr.append(List.col(['&nbsp;']));
		// $tr.append(List.col(['&nbsp;']));
		// $tr.append(List.col(['&nbsp;']));
		$tr.append(List.col([$product]));
		$tr.append(List.col([$source]));
		$tr.append(List.col([$barcode, $barcodeImg], '', 'text-align: right;'));
		$tr.append(List.col([$onhand]));
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
		
		var facility = document.getElementById('source');
		if(!facility.value)
		{
			alert('<spring:message code="transferorder.warehousefrom"/> <spring:message code="notif.empty"/> !');
			return;
		}
		
		var org = document.getElementById('org').value;
		
		openpopup("<c:url value='/page/popupinventoryitemview.htm?ref=4Serial&group=false&onHand=true&target=serial['/>"+index+"]&facility="+facility.value+"&index="+index+"&organization="+org+"&productId="+productId+"&container="+containerId);
	}
	
	function calculateAdjust(index) {
		var barcode = $('#serial\\['+index+'\\]').val();
		var qty = parseFloat($('#quantity\\['+index+'\\]').val().toNumber());
		var onHand = parseFloat($('#onHand\\['+index+'\\]').val().toNumber());
		
		if(!barcode && qty < 0) {
			alert('<spring:message code="transferorder.qty"/> <spring:message code="notif.lower"/> 0 !!!');
			$('#adjustQuantity\\['+index+'\\]').val(0.00);
			return;
		}
	}

	function changeType(el) {
		var type = el.value;
		var $row = $('#recipient').closest('tr');
		var $sel = $('#recipient');

		$sel.empty();

		if (type === 'STANDARD') {
			$row.hide();
			$sel.prop('disabled', true);
		} else {
			$row.show();
			$sel.prop('disabled', false);
		}
	}

	function openRecipient() {
		const org = $('#org').val()
		const type = $('#issueType').val();
		if (!org) {
			alert('<spring:message code="notif.select1"/> ' + '<spring:message code="organization"/> ' + '<spring:message code="notif.select2"/> !!!');
			return;
		}
		const base = '<c:url value="/page/popuppartyrelationview.htm"/>';
		const params = {
			target: 'recipient',
			organization: org,
			fromRoleType: type === 'SALES' ? 4 : 5,
			toRoleType:   type === 'SALES' ? 5 : 4,
			relationshipType: type === 'SALES' ? 3 : 4
		};
		openpopup(buildUrl(base, params));
	}
</script>