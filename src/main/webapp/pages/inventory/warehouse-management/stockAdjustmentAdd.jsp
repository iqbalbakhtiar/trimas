<%@ include file="/pages/includes/sirius-head.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/stockadjustmentview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<c:if test='${access.add}'>
		<a class="item-button-save b_save"><span><spring:message code="sirius.save"/></span></a>
    </c:if>				
</div>

<div class="main-box">
	<sesform:form id="addForm" name="addForm" method="post" modelAttribute="adjustment_form">
		<table width="100%" style="border:none">
			<tr>
				<td width="20%" nowrap="nowrap" align="right"><spring:message code="sirius.id"/> :</td>
				<td width="80%"><input value="Auto Generated" class='input-disabled' size='25' disabled/></td>
			</tr>
			<tr>
				<td nowrap="nowrap" align="right"><spring:message code="organization"/> :</td>
				<td>
					<form:select id="org" path="organization" cssClass="combobox-ext">
						<c:if test='${not empty adjustment_form.organization}'>
							<form:option value='${adjustment_form.organization.id}' label='${adjustment_form.organization.fullName}' />
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
				<td nowrap="nowrap" align="right"><spring:message code="goodsreceipt.warehouse"/> :</td>
				<td>
					<form:select id="facility" path="facility" cssClass="combobox-ext">
					</form:select>
					<img src="assets/icons/list_extensions.gif" onclick="openfacility();" style="cursor: pointer;" title="Facility" />
				</td>
			</tr>
			<tr style="display: none;">
				<td align="right"><spring:message code="sirius.currency"/> :</td>
				<td>
					<form:select id='currency' path='currency' cssClass="rate">
						<form:options items="${currencies}" itemValue='id' itemLabel='symbol'/>
					</form:select>
					<form:select id='type' path='exchangeType'  cssClass="rate">
						<form:option value='SPOT' label='SPOT'/>
						<form:option value='MIDDLE' label='MIDDLE'/>
						<form:option value='TAX' label='TAX'/>
					</form:select>
					<form:input id="trxrate" path="rate" cssClass="input-decimal" value="1.00" size="12"/>
				</td>
			</tr>
			<tr>
				<td nowrap="nowrap" align="right"><spring:message code="sirius.note"/> :</td>
				<td><form:textarea path="reason" cols="55" rows="7"/></td>
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
					<th width="1%"><input id ="checkline" value="line" class="checkall" type="checkbox"/></th>
					<th width="5%" nowrap="nowrap"><spring:message code="container"/></th>
					<th width="5%" nowrap="nowrap"><spring:message code="sirius.code"/></th>
					<th width="10%" nowrap="nowrap"><spring:message code="product.name"/></th>
					<th width="5%" nowrap="nowrap"><spring:message code="product.onhand"/></th>
					<th width="5%" nowrap="nowrap"><spring:message code="product.uom"/></th>
					<th width="5%" nowrap="nowrap"><spring:message code="product.lot"/></th>
					<th width="5%" nowrap="nowrap"><spring:message code="stockadjustmentitem.adjust.qty"/>/<spring:message code="product.serial"/></th>
					<th width="20%" colspan="2"><spring:message code="stockdepreciation.adjustedprice"/></th>
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
				let $qty = $(this).val();
				let $price = $('#price\\['+$idx+'\\]');
				
				let $input = $('#quantity\\[' + $idx + '\\]');
				
				$prd = $('#productSerial\\['+$idx+'\\] option:selected').text();
				
	           	if(!$qty || parseFloat($qty) == 0) {
					alert('<spring:message code="stockadjustmentitem.adjqty"/> '+ $prd +' <spring:message code="notif.zero"/> !!!');
					return $status = false;
				}
	           	
	           	if(!$price.val() || parseFloat($price.val()) <= 0) {
					alert('<spring:message code="stockadjustmentitem.adjprice"/> '+ $prd +' <spring:message code="notif.zero"/> <spring:message code="notif.and"/> <spring:message code="notif.lower"/> 0 !!!');
					return $status = false;
				}
			});
			
			if($status) {

				$('[data-name="quantityDel"]').remove();
				$.ajax({
					url:"<c:url value='/page/stockadjustmentadd.htm'/>",
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
								window.location="<c:url value='/page/stockadjustmentpreedit.htm?id='/>"+json.id;
							}
							else
								afterFail($dialog, '<spring:message code="notif.profailed"/> :<br/>' + json.message);
						}
					}
				});	
			}
		});

		$('#facility').focus(function(){

			$('.barcode').remove();
			$('.barcodeDetail').remove();
			
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
		const container = List.get('<select class="combobox" onchange='+"checkOnHand("+index+");"+'/>','container['+index+']');
		const containerImg = List.img('Container', index, 'opencontainerpopup("'+index+'")');
		const product = List.get('<select class="combobox-ext product" onchange='+"checkOnHand("+index+");"+'/>','product['+index+']');
		const productImg = List.img('Product', index, 'openproduct("'+index+'")');
		const codeProduct = List.get('<input class="input-disabled" disabled="true" size="12"/>','productCode['+index+']');
		const onHand = List.get('<input class="number-disabled" disabled="true" size="12"/>','onHand['+index+']', '0.00');
		const uom = List.get('<input class="input-disabled" disabled="true" size="12"/>','uom['+index+']');
		const lotCode = List.get('<input size="12"/>','lotCode['+index+']');
		const quantity = List.get('<input class="input-number negative" onkeyup='+"checkQuantity(this);"+' size="12"/>','quantity['+index+']', '1');
		const cogs = List.get('<input class="input-number negative" size="12"/>','price['+index+']', '0.00');
		
		$tr.append(List.col([checkbox]));
		$tr.append(List.col([container, containerImg]));
		$tr.append(List.col([codeProduct]));
		$tr.append(List.col([product, productImg]));
		$tr.append(List.col([onHand]));
		$tr.append(List.col([uom]));
		$tr.append(List.col([lotCode]));
		$tr.append(List.col([quantity]));
		$tr.append(List.col([cogs]));

		$tbody.append($tr);
		$ttable.append($tbody);
		
		index++;
	}
					
	function opencontainerpopup(index)
	{
		const facility = document.getElementById('facility');
		if(!facility.value)
		{
			alert('<spring:message code="facility"/> <spring:message code="notif.empty"/> !');
			return;
		}
		
		openpopup("<c:url value='/page/popupcontainerview.htm?target=container['/>"+index+"]");
	}
	
	function openproduct(index)
	{
		const org = document.getElementById('org');
		if(!org.value)
		{
			alert('<spring:message code="organization"/> <spring:message code="notif.empty"/> !');
			return;
		}
		
		const con = document.getElementById('container['+index+']');
		if(!con.value)
		{
			alert('<spring:message code="container"/> <spring:message code="notif.empty"/> !');
			return;
		}
		
		openpopup("<c:url value='/page/popupproductview.htm?target=product['/>"+index+"]&index="+index+"&container="+con.value+"&organization="+org.value);
	}
	
	function checkOnHand(index) {

		let prodId = $('#product\\['+index+'\\]').val();
		if(prodId) {
			let conId = $('#container\\['+index+'\\]').val();
			let serial = $('#serial\\['+index+'\\]').val();
			let serialCheck = $('#product\\['+index+'\\]').attr('serial');
			
			$('#onhand\\['+index+'\\]').val(0.00);
			
			let requestData = {
	            productId: prodId,
	            containerId: conId,
	            serial:serial
	        };
	
		    if (prodId) {
		        $.ajax({
		            url: "<c:url value='/page/onhandquantityviewonhandjson.htm'/>",
		            data: requestData,
		            method: 'GET',
		            dataType: 'json',
		            success: function (json) {
		                if (json && json.status === 'OK' &&  json.onHand != null) {
		                	$('#onHand\\['+index+'\\]').val(parseFloat(json.onHand).numberFormat('#,##0.00'));
		                }else
		                	$('#onHand\\['+index+'\\]').val(parseFloat(0).numberFormat('#,##0.00'));
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
								if(amount) {
									if(json.product != null)
										amount.value = parseFloat(json.product.price).numberFormat('#,##0.00');
									else
										amount.value = parseFloat(0).numberFormat('#,##0.00');
								}
							}
						}
					}
				});
			}
			
			if(serialCheck.toLowerCase() === "true") {
				$('#iBody' + index + ' tr.barcodeGroup'+0).remove();
				
				addLine(index, 0);
				$('#iBody' + index + ' tr:last').addClass('barcode');
				
				$('#uom\\['+index+'\\]').attr("style", "display:none;");
				$('#lotCode\\['+index+'\\]').remove();
				$('#price\\['+index+'\\]').remove();
				$('#product\\[' + index + '\\]').prop('disabled', true);	
				$('#container\\[' + index + '\\]').prop('disabled', true);
				
				let $input = $('#quantity\\[' + index + '\\]');
				if ($input.length)
				    $input.attr('data-name', 'quantityDel');
			}
		}
	}
	
	var $index = parseInt('${fn:length(adjustment_form.items)}');
	
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
		const con = document.getElementById('container['+index+']');
		const product = document.getElementById('product['+idxRef+']');
		
		if(!product.value)
		{
			alert(' <spring:message code="notif.select1"/> <spring:message code="product.name"/> <spring:message code="notif.select2"/>!');
		    document.getElementById('quantity[' + idxRef + ']').value = 0;
			
			return;
		}
		
		var onHand = document.getElementById('onHand[' + idxRef + ']').value.toNumber();
		var qty = document.getElementById('quantity[' + idxRef + ']').value.toNumber();
		var serialCheck = $('#product\\['+idxRef+'\\]').attr('serial');
		
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
		
		if(serialCheck.toLowerCase() === "true") {
			$('#iBody' + idxRef + ' tr.barcodeGroup'+idxRef).remove();
			
			for (let i = 0; i < element.value; i++) { 
				addLine(idxRef, $index);
				$('#iBody' + idxRef + ' tr:last').addClass('barcode');
			}
			
			$('#uom\\['+idxRef+'\\]').attr("style", "display:none;");
			$('#price\\['+idxRef+'\\]').remove();
			$('#product\\[' + idxRef + '\\]').prop('disabled', true);	
			$('#container\\[' + idxRef + '\\]').prop('disabled', true);
			
			let $input = $('#quantity\\[' + idxRef + '\\]');
			if ($input.length)
			    $input.attr('data-name', 'quantityDel');
		}
	}
	
	function addLine($idxRef, $index) {
	
	    const $productId = $('#product\\[' + $idxRef + '\\]').val();
	    const $containerId = $('#container\\[' + $idxRef + '\\]').val();
	    const $uom = $('#uom\\[' + $idxRef + '\\]').val();

	    const $tbody = $('#iBody' + $idxRef);
	    const $tr = $('<tr/>').addClass('barcodeGroup' + $idxRef+' barcodeDetail');

	    if ($productId) {
	        $.ajax({
	            url: "<c:url value='/page/popupinventoryitemlistjson.htm'/>",
	            data: { productId: $productId, containerId: $containerId, available: false},
	            method: 'GET',
	            dataType: 'json',
	            success: function(json) {

	                const product = List.get('<input class="input" size="12" type="hidden" serial="true"/>', 'product[' + index + ']', $productId);
	                const container = List.get('<input class="input" size="12" type="hidden"/>', 'container[' + index + ']', $containerId);

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
	                        placeholder: 'Select Barcode',
	                        onchange: 'checkOnHand("' + index + '");'
	                    });

	                    const datalistEl = $('<datalist/>', { id: datalistId });

	                    // Simpan semua barcodes dalam cache JS
	                    const allBarcodes = json.barcodes;

	                    // Tampilkan hanya 10 pertama
	                    allBarcodes.slice(0, 10).forEach(barcode => {
	                        datalistEl.append($('<option/>', { value: barcode.serial }));
	                    });

	                    // Tambahkan event listener untuk filter manual
	                    input.on('input', function () {
	                        const keyword = $(this).val().toLowerCase();
	                        datalistEl.empty(); // hapus semua option
	                        allBarcodes
	                            .filter(b => b.serial.toLowerCase().includes(keyword))
	                            .slice(0, 20) // tampilkan hanya 20 hasil yang cocok
	                            .forEach(b => {
	                                datalistEl.append($('<option/>', { value: b.serial }));
	                            });
	                    });

	                    $barcode = $('<div/>').append(input, datalistEl);
	                }
	                
	                const $barcodeImg = List.img('<spring:message code="barcode"/>', index, 'openBarcode("' + index + '","' + $productId + '")').css('display', 'none');
	                const $qty = List.get('<input type="text" class="input-number input-disabled" readonly="true" size="12"/>', 'onHand[' + index + ']', '0.00');
	                const $uomField = List.get('<input type="text" class="input-disabled" disabled="true" size="12"/>', 'uom[' + index + ']', $uom);
	        		const $lotCode = List.get('<input size="12"/>','lotCode['+index+']');
	                const quantity = List.get('<input class="input-number negative totals' + $idxRef + '" size="12" onchange="calculateAdjust(\'' + index + '\', \'' + $idxRef + '\');"/>', 'quantity[' + index + ']', '1');
	                const cogs = List.get('<input class="input-number negative" size="12"/>', 'price[' + index + ']', '0.00');

	                $tr.append(List.col(product));
	                $tr.append(List.col(container));
	                $tr.append(List.col([$barcode, $barcodeImg], '', 'text-align: right;').attr('colspan', '2'));
	                $tr.append(List.col([$qty]));
	                $tr.append(List.col([$uomField]));
	                $tr.append(List.col([$lotCode]));
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
		
		var facility = document.getElementById('facility').value;
		var org = document.getElementById('org').value;
		
		openpopup("<c:url value='/page/popupinventoryitemview.htm?ref=4Serial&group=false&onHand=true&target=serial['/>"+index+"]&facility="+facility+"&index="+index+"&organization="+org+"&productId="+productId);
	}
	
	var originalonHandMap = {};
	var originalonHandMapBase = {};

	function calculateAdjust(index, idxRef) {
		
	    var barcode = $('#serial\\[' + index + '\\]').val();
	    var qty = parseFloat($('#quantity\\[' + index + '\\]').val().replace(/,/g, '')) || 0;

	    // Simpan original hanya sekali
	    if (originalonHandMap[index] === undefined) {
	        originalonHandMap[index] = parseFloat($('#onHand\\[' + index + '\\]').val().replace(/,/g, '')) || 0;
	    }
	    
	    if (originalonHandMapBase[idxRef] === undefined) {
	    	originalonHandMapBase[idxRef] = parseFloat($('#onHand\\[' + idxRef + '\\]').val().replace(/,/g, '')) || 0;
	    }

	    var originalonHand = originalonHandMap[index];
	    var originalonHandBase = originalonHandMapBase[idxRef];

	    if (!barcode && qty < 0) {
	        alert('<spring:message code="stockadjustmentitem.adjqty"/> <spring:message code="notif.lower"/> 0 !!!');
	        $('#adjustQuantity\\[' + index + '\\]').val("0.00");let $input = $('#quantity\\[' + idx + '\\]');
	        return;
	    }

	    $('#onHand\\[' + index + '\\]').val((qty + originalonHand).numberFormat('#,#.00'));

	    $.each($(".totals" + idxRef), function (i, obj) {
	        var val = parseFloat($(obj).val().replace(/,/g, '')) || 0;
	        originalonHandBase += val;
	    });

	   	$('#onHand\\[' + idxRef + '\\]').val(originalonHandBase.numberFormat('#,#.00'));
	}
	
</script>