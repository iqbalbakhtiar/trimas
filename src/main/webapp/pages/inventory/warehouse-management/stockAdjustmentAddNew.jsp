<%@ include file="/pages/includes/sirius-head.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/stockadjustmentview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-save"><span><spring:message code="sirius.save"/></span></a>						
</div>

<div class="main-box">
	<sesform:form id="addForm" name="addForm" method="post" modelAttribute="adjustment_add">
		<table width="100%" style="border:none">
		<tr>
			<td width="20%" nowrap="nowrap" align="right"><spring:message code="sirius.id"/> :</td>
			<td width="80%"><input value="Auto Generated" class='input-disabled' size='25' disabled/></td>
		</tr>
		<tr>
			<td nowrap="nowrap" align="right"><spring:message code="sirius.date"/> :</td>
			<td><input id="date" name="date" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>" class="datepicker"/></td>
		</tr>
		<tr>
			<td nowrap="nowrap" align="right"><spring:message code="organization"/> :</td>
			<td>
				<form:select id="org" path="organization" cssClass="combobox-ext">
					<c:if test='${not empty adjustment_add.organization}'>
						<form:option value='${adjustment_add.organization.id}' label='${adjustment_add.organization.fullName}' />
					</c:if>
				</form:select>
				<img src="assets/icons/list_extensions.gif" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" style="CURSOR:pointer;" title="Company Structure" />
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap" align="right"><spring:message code="facility"/> :</td>
			<td>
				<form:select id="facility" path="facility" cssClass="combobox-ext">
				</form:select>
				<img src="assets/icons/list_extensions.gif" onclick="openfacility();" style="cursor: pointer;" title="Facility" />
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap" align="right"><spring:message code="sirius.reason"/> :</td>
			<td><form:textarea path="reason" cols="55" rows="7"/></td>
		</tr>
	</table>
		<div class="toolbar-clean">
			<div class="item-navigator">&nbsp;</div>
			<a class="item-button-new" href="javascript:addLineItem();"><span><spring:message code="sirius.row.new"/></span></a>
			<a class="item-button-delete" ><span><spring:message code="sirius.row.delete"/></span></a>
		</div>
		<table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center" width="100%">
		<thead>
			<tr>
				<th width="2%"><input id ="checkline" value="line" class="checkall" type="checkbox"/></th>
				<th ><spring:message code="container"/></th>
				<th width="12"><spring:message code="sirius.code"/></th>
				<th ><spring:message code="product.name"/></th>
				<th width="16%"><spring:message code="product.category"/></th>
				<th width="8%"><spring:message code="product.uom"/></th>
				<th width="8%"><spring:message code="product.lot"/></th>
				<th width="12%"><spring:message code="product.onhand"/></th>
				<th width="12%"><spring:message code="product.bale"/></th>
				<th width="12%"><spring:message code="product.quantity"/></th>
				<th width="14%" colspan="2"><spring:message code="sirius.price"/></th>
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
    
		$('.item-button-save').click(function(){
			
			if ($('#lineItemTable tbody').length === 0) {
				alert('<spring:message code="notif.line"/> !');
				return;
			}
			
		    $('#lineItemTable tbody').each(function(i, tbody) {
		        let $tbody = $(tbody);
		        
		        const idx = $tbody.attr('id').replace('iBody', '');

		       	const productVal = $tbody.find('[name="items['+idx+'].product"]').val();
		        if (!productVal) {
		            alert("Produk pada baris ke-" + (i+1) + " belum dipilih!");
		            return; 
		        }
		    });
		    
		    let allBarcodes = document.querySelectorAll('input.barcode-input');
		    for (let i = 0; i < allBarcodes.length; i++) {
		        if (!allBarcodes[i].value.trim()) {
		            alert("Barcode tidak boleh kosong di baris ke-" + (i + 1));
		            allBarcodes[i].focus();
		            return false;
		        }
		    }
		    
			$('[data-name="quantityDel"]').remove();
			
			$.ajax({
				url:"<c:url value='/page/stockadjustmentadd.htm'/>",
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
						if(json.status == 'OK')
						{
							$dialog.dialog('close');
							window.location="<c:url value='/page/stockadjustmentview.htm'/>";
						}
						else
						{
							$dialog.empty();
        					$dialog.html('<spring:message code="notif.profailed"/> :<br/>'+json.message);
						}
					}
				}
			});	
		});

		$('#facility').focus(function(){
			$('.check').prop("checked", true);
			$('.item-button-delete').click();
		});

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
	});
		
	function openfacility()
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('<spring:message code="organization"/> <spring:message code="notif.empty"/> !');
			return;
		}
		
		openpopup("<c:url value='/page/popupfacilityview.htm?target=facility&organization='/>"+org.value);
	}

	var index = 0;
	function addLineItem()
	{
		$ttable = $('#lineItemTable');
		$tbody = $('<tbody id="iBody'+index+'"/>');
		$tr = $('<tr/>');
		
		const checkbox = List.get('<input type="checkbox" class="check"/>','check['+index +']');	
		const container = List.get('<select class="combobox-min" onchange='+"checkOnHand("+index+");"+'/>','container['+index+']');
		const containerImg = List.img('Container', index, 'opencontainerpopup("'+index+'")');
		const product = List.get('<select class="combobox-ext product" onchange='+"checkOnHand("+index+");"+'/>','product['+index+']');
		const productImg = List.img('Product', index, 'openproduct("'+index+'")');
		const codeProduct = List.get('<input class="input-disabled" disabled="true" size="10"/>','codeprod['+index+']');
		const category = List.get('<input class="input-disabled" disabled="true" size="16"/>','category['+index+']');
		const uom = List.get('<input class="input-disabled" disabled="true" size="5"/>','uom['+index+']');
		const onHand = List.get('<input class="number-disabled" disabled="true" size="12"/>','onhand['+index+']', '0.00');
		const bale = List.get('<input class="number-disabled" disabled="true" size="12"/>','bale['+index+']', '0.00');
		const quantity = List.get('<input class="input-number negative" onkeyup='+"checkQuantity(this);"+' size="12"/>','quantity['+index+']', '0');
		const price = List.get('<input class="input-number" size="12" colspan="2"/>','price['+index+']', '0.00');
		const serial = List.get('<input disabled="true" size="12" type="hidden"/>','serialCheck['+index+']');
		/* const currency = List.get('<select hidden="hidden"/>','currency['+index+']');
		const currencies = JSON.parse('${jsonUtil:toJson(currencies)}');

		for(const curr of currencies)
			currency.append('<option value="'+curr.id+'">'+curr.symbol+'</option>');

		const type = List.get('<select hidden="hidden"/>','info['+index+']');
		type.append('<option value="MIDDLE">MIDDLE</option>');
		type.append('<option value="SPOT">SPOT</option>');
		type.append('<option value="TAX">TAX</option>'); */
		
		$tr.append(List.col([checkbox]));
		$tr.append(List.col([container, containerImg]));
		$tr.append(List.col([codeProduct]));
		$tr.append(List.col([product, productImg]));
		$tr.append(List.col([category]));
		$tr.append(List.col([uom]));
		$tr.append(List.col($("<span>&nbsp;</span>")));
		$tr.append(List.col([onHand]));
		$tr.append(List.col([bale]));
		$tr.append(List.col([quantity]));
		$tr.append(List.col([price]));
		$tr.append(List.col([serial]));

		$tbody.append($tr);
		$ttable.append($tbody);
		
		index++;

		$(".input-number").bind(inputFormat);
		$(".input-decimal").bind(inputFormat);
	}
					
	function opengridpopup(index)
	{
		const fac = document.getElementById('facility');
		
		if(!fac.value)
		{
			alert('<spring:message code="facility"/> <spring:message code="notif.empty"/> !');
			return;
		}
		
		openpopup("<c:url value='/page/popupgridview.htm?target=grid['/>"+index+"]&facility="+fac.value);
	}
	
	function opencontainerpopup(index)
	{
		openpopup("<c:url value='/page/popupcontainerview.htm?target=container['/>"+index+"]");
	}
	
	function openproduct(index)
	{
		const con = document.getElementById('container['+index+']');
		if(!con.value)
		{
			alert('<spring:message code="container"/> <spring:message code="notif.empty"/> !');
			return;
		}
		
		const org = document.getElementById('org');
		if(!org.value)
		{
			alert('<spring:message code="organization"/> <spring:message code="notif.empty"/> !');
			return;
		}
		
		openpopup("<c:url value='/page/popupproductforadjustmentview.htm?target=product['/>"+index+"]&index="+index+"&container="+con.value+"&organization="+org.value);
	}
	
	function changeGrid(index) {
		document.getElementById("container["+index+"]").innerHTML = "";
		$('#onhand\\['+index+'\\]').val(0.00);
	}
	
	function checkOnHand(index) {
		let prodId = $('#product\\['+index+'\\]').val();
		let conId = $('#container\\['+index+'\\]').val();
		let serial = $('#serial\\['+index+'\\]').val();
		let serialCheckEl = document.getElementById('serialCheck[' + index + ']');
		let serialCheck = serialCheckEl ? serialCheckEl.value : "false";
		
		$('#onhand\\['+index+'\\]').val(0.00);
		
		let requestData = {
            productId: prodId,
            containerId: conId,
            serial:serial
        };
		
		if (serial) {
	        $.ajax({
	            url: "<c:url value='/page/inventoryitembyserialjson.htm'/>",
	            data: {barcode:serial},
	            method: 'GET',
	            dataType: 'json',
	            success: function (json) {
	                if (json && json.status === 'OK' &&  json.inventory != null) {
	                	$('#onHand\\['+index+'\\]').val(parseFloat(json.inventory.onHand).numberFormat('#,##0.00'));
	                	$('#onhand\\['+index+'\\]').val(parseFloat(json.inventory.onHand).numberFormat('#,##0.00'));
	                	$('#lotCode\\['+index+'\\]').val(json.inventory.lotCode);
	                	$('#uom\\['+index+'\\]').val(json.inventory.product.unitOfMeasure.name);
	                	console.log(json.inventory);
	                }else{
	                	$('#onHand\\['+index+'\\]').val(parseFloat(0).numberFormat('#,##0.00'));
	                	$('#onhand\\['+index+'\\]').val(parseFloat(0).numberFormat('#,##0.00'));
	                }
	            }
	        });
	    }
		
		if (prodId) {
	        $.ajax({
	            url: "<c:url value='/page/onhandquantityviewonhandjson.htm'/>",
	            data: requestData,
	            method: 'GET',
	            dataType: 'json',
	            success: function (json) {
	                if (json && json.status === 'OK' &&  json.onHand != null) {
	                	$('#onhand\\['+index+'\\]').val(parseFloat(json.onHand).numberFormat('#,##0.00'));
	                	$('#bale\\['+index+'\\]').val(parseFloat(json.onHand/181.44).numberFormat('#,##0.00'));
	                }else{
	                	$('#onhand\\['+index+'\\]').val(parseFloat(0).numberFormat('#,##0.00'));
	                	$('#bale\\['+index+'\\]').val(parseFloat(0).numberFormat('#,##0.00'));
	                }
	            }
	        });
	    }
		
		if(prodId) {
			$.ajax({
				url:"<c:url value='/page/stockadjustmentbyproductjson.htm'/>",
				data:requestData,
				method : 'GET',
				dataType : 'json',
				success : function(json) {
					if(json)
					{
						if(json.status == 'OK'){
							let amount = document.getElementsByName('items['+index+'].price')[0];
							if(amount && json.product != null)
								amount.value = parseFloat(json.product.price).numberFormat('#,##0.00');
							else
								amount.value = parseFloat(0).numberFormat('#,##0.00');
						}
					}
				}
			});
		}
		
		
		if(serialCheck.toLowerCase() === "true"){
			$('#iBody' + index + ' tr.barcodeGroup'+0).remove();
			
			addLine(index, 0);
			$('#iBody' + index + ' tr:last').addClass('barcode');
			
			$('#uom\\['+index+'\\]').remove();
			$('#price\\['+index+'\\]').remove();
			$('#quantity\\['+index+'\\]').val(1);
			$('#product\\[' + index + '\\]').prop('disabled', true);	
			$('#container\\[' + index + '\\]').prop('disabled', true);
			
			let $input = $('#quantity\\[' + index + '\\]');
			if ($input.length) {
			    $input.attr('data-name', 'quantityDel');
			}
		}

	}
	
	var $index = parseInt('${fn:length(adjustment_add.items)}');
	
	function checkQuantityItem(element){
		let idxRef = $(element).attr('index');
		
		var onHand = document.getElementById('onHand[' + idxRef + ']').value.toNumber();
		var qty = document.getElementById('quantity[' + idxRef + ']').value.toNumber();
		
		if(qty < 0)
			$("#cogs\\["+idxRef+"\\]").attr("class","input-disabled");
		
		if(qty > 0)
			$("#cogs\\["+idxRef+"\\]").attr("class","input-decimal");
		
		if((onHand+qty) < 0) 
		{
			alert('Qty Adjustment (-) cannot greater than On Hand !!!');
			document.getElementById('quantity[' + idxRef + ']').value = 0.00;
			
			return;
		}
	}
	
	
	function checkQuantity(element)
	{
		let idxRef = $(element).attr('index');
		
		var onHand = document.getElementById('onhand[' + idxRef + ']').value.toNumber();
		var qty = document.getElementById('quantity[' + idxRef + ']').value.toNumber();
		var serialCheck = document.getElementById('serialCheck[' + idxRef + ']').value;
		
		if((onHand+qty) < 0) 
		{
			alert('Qty Adjustment (-) cannot greater than On Hand !!!');
			document.getElementById('quantity[' + idxRef + ']').value = 0.00;
			return;
		}
		
		if(serialCheck.toLowerCase() === "true"){
			$('#iBody' + idxRef + ' tr.barcodeGroup'+idxRef).remove();
			
			for (let i = 0; i < element.value; i++) { 
				addLine(idxRef, $index);
				$('#iBody' + idxRef + ' tr:last').addClass('barcode');
			}
			
			$('#uom\\['+idxRef+'\\]').remove();
			$('#price\\['+idxRef+'\\]').remove();
			$('#product\\[' + idxRef + '\\]').prop('disabled', true);	
			$('#container\\[' + idxRef + '\\]').prop('disabled', true);
			
			let $input = $('#quantity\\[' + idxRef + '\\]');
			if ($input.length) {
			    $input.attr('data-name', 'quantityDel');
			}
		}
	}
	
	var index = 0;
	function addLine($idxRef, $index) {
		
	    const $productId = $('#product\\[' + $idxRef + '\\]').val();
	    const $containerId = $('#container\\[' + $idxRef + '\\]').val();
	    const $gridId = $('#grid\\[' + $idxRef + '\\]').val();
	    const $uom = $('#uom\\[' + $idxRef + '\\]').val();

	    $('#iBody' + $idxRef).addClass('barcodeGroup' + $idxRef);
	    const $tr = $('<tr/>').addClass('barcodeGroup' + $idxRef);

	    if ($productId) {
	        $.ajax({
	            url: "<c:url value='/page/popupinventoryitemlistjson.htm'/>",
	            data: {productId: $productId, containerId: $containerId },
	            method: 'GET',
	            dataType: 'json',
	            success: function(json) {

	                const container = List.get('<input class="input" size="12" type="hidden"/>', 'container[' + index + ']', $containerId);
	                const product = List.get('<input class="input" size="12" type="hidden"/>', 'product[' + index + ']', $productId);
	                
	                let input = '';
	                let datalist = '';
	                let $barcode = '';

	                if (Array.isArray(json.barcodes)) {
	                    const datalistId = 'barcodeList_' + index;
	                    const input = $('<input/>', {
	                        list: datalistId,
	                        class: 'barcode-input',
	                        name: 'items[' + index + '].serial',
	                        id: 'serial[' + index + ']',
	                        placeholder: 'Select Barcode'
	                    });

	                    const datalistEl = $('<datalist/>', { id: datalistId });

	                    // Simpan semua barcodes dalam cache JS
	                    const allBarcodes = json.barcodes;

	                    // Tampilkan hanya 10 pertama
	                    allBarcodes.slice(0, 10).forEach(barcode => {
	                        datalistEl.append($('<option/>', { value: barcode.serial }));
	                    });

	                    // Event filter saat mengetik
	                    input.on('input', function () {
	                        const keyword = $(this).val().toLowerCase();
	                        datalistEl.empty();
	                        allBarcodes
	                            .filter(b => b.serial.toLowerCase().includes(keyword))
	                            .slice(0, 20)
	                            .forEach(b => {
	                                datalistEl.append($('<option/>', { value: b.serial }));
	                            });
	                    });

	                    // Validasi saat blur atau change
	                    input.on('change blur', function () {
	                        const val = $(this).val();
	                        const isValid = allBarcodes.some(b => b.serial === val);
	                        if (!isValid) {
	                            $(this).val(''); // atau bisa juga beri pesan error
	                        } else {
	                            // Barcode valid, bisa lanjut proses misalnya checkOnHand
	                            checkOnHand(index);
	                        }
	                    });

	                    $barcode = $('<div/>').append(input, datalistEl);
	                }
	                
	                const $barcodeImg = List.img('<spring:message code="barcode"/>', index, 'openBarcode("' + index + '","' + $productId + '")').css('display', 'none');
	                const $qty = List.get('<input type="text" class="input-number input-disabled" readonly="true" size="12"/>', 'onHand[' + index + ']', '0.00');
	                const $uomField = List.get('<input type="text" class="input-disabled" disabled="true" size="12"/>', 'uom[' + index + ']', $uom);
	        		const lotCode = List.get('<input type="text" class="input-disabled" readonly="true" size="5"/>','lotCode[' + index + ']');
	                const quantity = List.get('<input class="input-number negative totals' + $idxRef + '" size="12" onchange="calculateAdjust(\'' + index + '\', \'' + $idxRef + '\');"/>', 'quantity[' + index + ']', '1');
	                const cogs = List.get('<input class="input-number negative" size="12"/>', 'price[' + index + ']', '0.00');

	                $tr.append(List.col(container));
	                $tr.append(List.col(product));
	                $tr.append(List.col($("<span>&nbsp;</span>")));
	                $tr.append(List.col([$barcode, $barcodeImg], '', 'text-align: right;').attr('colspan', '2'));
	                $tr.append(List.col([$uomField]));
	                $tr.append(List.col([lotCode]));
	                $tr.append(List.col([$qty]));
	                $tr.append(List.col($("<span>&nbsp;</span>")));
	                $tr.append(List.col([quantity]));
	                $tr.append(List.col([cogs]));

	                $tbody.append($tr);
	                index++;

	                $(".input-number").on('input', inputFormat);
	            },
	            error: function(xhr, status, error) {
	                console.error("Gagal ambil barcode:", status, error);
	            }
	        });
	    }
	}
	
</script>