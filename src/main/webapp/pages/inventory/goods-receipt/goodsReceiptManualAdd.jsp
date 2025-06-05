<%@ include file="/pages/includes/sirius-head.jsp"%>

<div class="toolbar">
	<a class="item-button-list"
		href="<c:url value='/page/goodsreceiptmanualview.htm'/>"><span>List</span></a>
	<c:if test="${access.add}">
		<a class="item-button-save"><span>Save</span></a>
	</c:if>
</div>

<div class="main-box">
	<sesform:form id="addForm" name="addForm" method="post"
		modelAttribute="receiptManual_form">
		<table width="100%" style="border: none">
			<tr>
				<td width="53%">
					<table width="100%" style="border: none">
						<tr>
							<td width="26%" nowrap="nowrap" align="right"><spring:message
									code="goodsreceipt.code" /> :</td>
							<td width="74%"><input value="Auto Generated"
								class='input-disabled' disabled="true" size='25' /></td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message
									code="goodsreceipt.organization" /> :</td>
							<td><form:select id="org" path="organization"
									cssClass="combobox-ext">
									<c:if test='${not empty receiptManual_form.organization}'>
										<form:option value='${receiptManual_form.organization.id}'
											label='${receiptManual_form.organization.fullName}' />
									</c:if>
								</form:select> &nbsp; <img src="assets/icons/list_extensions.gif"
								onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');"
								style="CURSOR: pointer;" title="Company Structure" /></td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message
									code="goodsreceipt.receive.date" /> :</td>
							<td><input id="date" name="date" class="datepicker"
								value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>" /></td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message
									code="facility" /> :</td>
							<td><select id='facility' name="facility"
								class="combobox-ext">
							</select> &nbsp; <img src="assets/icons/list_extensions.gif"
								onclick="javascript:openfacility();" style="CURSOR: pointer;"
								title="Facility" /></td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message
									code="sirius.reference" /> :</td>
							<td><select id="transactionSource" name="transactionSource"
								class="combobox-ext input-disabled">
									<option value="GOODS_RECEIPT_MANUAL">GOODS RECEIPT
										MANUAL</option>
							</select></td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message code="goodsreceipt.receive.from"/> :</td>
							<td><select id="supplier" name="supplier"
								class="combobox-ext">
							</select> <img src="assets/icons/list_extensions.gif"
								onclick="javascript:opensupplier();"
								style="CURSOR: pointer;" title="Supplier" /></td>
						</tr>
						<tr>
							<td nowrap="nowrap" align="right"><spring:message code="sirius.note" /> :</td>
							<td><form:textarea path="note" cols="55" rows="7" /></td>
						</tr>
					</table>
				</td>
				<td width="47%" valign="top" align="left">
					<fieldset>
						<legend>
							<strong><spring:message code="sirius.transaction.recap"/></strong>
						</legend>
						<table width="100%">
							<tr>
								<th width="50%">&nbsp;</th>
								<th width="25%">Qty</th>
								<th width="25%">Qty (Pcs)</th>
							</tr>
							<tr>
								<td align="right"><spring:message code="sirius.total"/> :</td>
								<td><input id="totalQty" value="0.00"
									class="number-disabled" disabled size="15" /></td>
								<td><input id="totalQtyPcs" value="0.00"
									class="number-disabled" disabled size="15" /></td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
		</table>
		<br />
		<div class="toolbar-clean">
			<div class="item-navigator">&nbsp;</div>
			<a class="item-button-new" href="javascript:addLineItem();"><span><spring:message
						code="sirius.row.new" /></span></a> <a class="item-button-delete"><span><spring:message
						code="sirius.row.delete" /></span></a>
		</div>
		<table id="lineItemTable" width="100%" cellpadding="0" cellspacing="0"
			class="table-list">
			<thead>
				<tr>
					<th width="2%"><input class="checkall" type="checkbox" /></th>
					<th width="15%"><spring:message code="grid" /></th>
					<th width="15%"><spring:message code="container" /></th>
					<th width="20%"><spring:message code="product" /></th>
					<th width="10%"><spring:message code="barcode" /></th>
					<th width="15%"><spring:message code="goodsreceiptitem.uom" /></th>
					<th width="10%"
						style="white-space: normal; line-height: 10px; text-align: center"><spring:message
							code="invoiceverificationitem.receivedqty" /></th>
					<th width="15%"><spring:message code="sirius.qty"/></th>
					<th width="15%"><spring:message code="sirius.price" /></th>
					<th width="15%"><spring:message code="goodsreceiptitem.priceadjust" /></th>
				</tr>
			</thead>
			<tbody id="tbody">

			</tbody>
			<tfoot>
				<tr class="end-table">
					<td colspan="15">&nbsp;</td>
				</tr>
			</tfoot>
		</table>
	</sesform:form>
</div>

<%@ include file="/pages/includes/sirius-foot.jsp"%>
<script type="text/javascript">
	$(function() {
		var $dialog = $('<div></div>').dialog({autoOpen: false,title: 'Goods Receipt Manual',modal:true,buttons: {Close: function() {$(this).dialog('close');}}});

		$('.item-button-save').click(function(){

            if(!$('#facility').val())
            {
                alert('Please select Facility first !!!');
                return;
            }

            const checks = document.getElementsByClassName('check');

            if(checks.length == 0)
            {
                alert('You need to provide Line Item first before saving the transaction!');
                return;
            }

            for(const check of checks)
            {
                const index = check.getAttribute('index');

                /*  if(!$('#product\\[' + index + '\\]').val())
                {
                    alert('Please select Container first !!!');
                    return;
                }

                if($('#receipted\\[' + index + '\\]').val().toNumber() == 0)
                {
                    alert('Quantity must be greather than zero !!!');
                    return;
                } 

                if($('#price\\[' + index + '\\]').val().toNumber() == 0)
                {
                    alert('Amount must be greather than zero !!!');
                    return;
                }*/
            }

            $.ajax({
                url:"<c:url value='/page/goodsreceiptmanualadd.htm'/>",
                data:$('#addForm').serialize(),
                type : 'POST',
                dataType : 'json',
                beforeSend:function()
                {
                    $dialog.empty();
                    $dialog.html('Saving Goods Receipt Manual data......');
                    $dialog.dialog('open');
                },
                success : function(json) {
                    if(json)
                    {
                        if(json.status == 'OK')
                        {
                            $dialog.dialog('close');
                            window.location="<c:url value='/page/goodsreceiptmanualview.htm'/>";
                        }
                        else
                        {
                            $dialog.empty();
                            $dialog.html('Proccess fail,reason:<br/>'+json.message);
                        }
                    }
                }
            });
		});

        $('.checkall').click(function () {
            $('.check').prop("checked", this.checked);
        });

        $('.item-button-delete').click(function () {
            $('.check').each(function(){
                if(this.checked){
                    this.checked = false;
                    $(this).parent().parent().remove();
                }
            });
            $('.checkall').prop("checked", false);

            display();
        });

        $('#container\\[-1\\]').change(function(){
            $('.grids').empty();
            $('.containers').empty();

            $('#grid\\[-1\\] option').clone().appendTo($('.grids'));
            $('#container\\[-1\\] option').clone().appendTo($('.containers'));
        });

        $('#org').change(function(){
            $('#facility').empty();

            document.getElementById('facility').dispatchEvent(event);
        });

        $('#facility').change(function(){
            $('#grid\\[-1\\]').empty();
            $('#container\\[-1\\]').empty();

            var event = new Event('change');
            document.getElementById('container[-1]').dispatchEvent(event);
        });

    });
	
	var index = 0;
	function addLineItem()
	{
		$tbody = $('#lineItemTable');
		$tr = $('<tr/>');
		
		const checkbox = List.get('<input type="checkbox" class="check"/>','check['+index +']');	
		//const grid = List.get('<select class="combobox-min" onchange='+"changeGrid("+index+");"+'/>','grid['+index+']');
		//const gridImg = List.img('Grid', index, 'opengridpopup("'+index+'")');
		const container = List.get('<select class="combobox" onchange='+"checkOnHand("+index+");"+'/>','container['+index+']');
		const containerImg = List.img('Container', index, 'opencontainerpopup("'+index+'")');
		const grid = List.get('<select class="combobox" onchange='+"changeGrid("+index+");"+'/>','grid['+index+']');
		const gridImg = List.img('Grid', index, 'opengridpopup("'+index+'")');
		const product = List.get('<select class="combobox-ext product" onchange='+"checkOnHand("+index+");"+'/>','product['+index+']');
		const productImg = List.img('Product', index, 'openproduct("'+index+'")');
		const uom = List.get('<input class="input-disabled" disabled="true" size="12"/>','uom['+index+']');
		const receipted = List.get('<input class="input-decimal negative qty" onchange='+"checkQuantity("+index+");"+' size="12"/>','receipted['+index+']', '0.00');
		const qtyPcs = List.get('<input class="number-disabled qtyPcs" disabled="true" size="8"/>','qtyPcs['+index+']', '0.00000');
        const amountPcs = List.get('<input class="number-disabled" readonly="true" size="12"/>','amountPcs['+index+']', '0.00');
        const amount = List.get('<input class="input-number" size="12"/>','price['+index+']', '0.00');
        amount.change(function(){
            const index = this.getAttribute('index');
            const qtyBase = $('#baseQty\\[' + index + '\\]').val().toNumber();
            const amountPcs = $('#amountPcs\\[' + index + '\\]');

            amountPcs.val((this.value.toNumber() / qtyBase).numberFormat('#,##0.00'));
        });
        
        const $input = $('<input>', {
       	 	class: 'input',
       	  	size: 16,
       	  	id: 'serial[' + index + ']',
       	  	name: 'items[' + index + '].serial',
       	  	list: 'barcodeList_' + index,
       	 	 index: index,
       	 	 next: 'serial'
       	});

       	const $datalist = $('<datalist>', {
       	  	id: 'barcodeList_' + index
       	});
        
     	const barcode = [$input, $datalist];

		
		$tr.append(List.col([checkbox]));
		$tr.append(List.col([grid, gridImg]));
		$tr.append(List.col([container, containerImg]));
		$tr.append(List.col([product, productImg]));
		$tr.append(List.col([barcode]));
		$tr.append(List.col([uom]));
		$tr.append(List.col([receipted]));
		$tr.append(List.col([qtyPcs]));
		$tr.append(List.col([amountPcs]));
		$tr.append(List.col([amount]));
		//$tr.append(List.col([grid, gridImg]));
		
		$tbody.append($tr);
		
		index++;

		$(".input-number").bind(inputNumber);
		$(".input-decimal").bind(inputNumber);
	}
    
    function display()
    {
        let totalQuantity = 0;
        let totalQuantityPcs =  0;

        const quantities = document.getElementsByClassName('qty');
        for(const quantity of quantities)
        {
            const index = quantity.getAttribute('index');
            totalQuantity += quantity.value.toNumber();
        }
        
        const quantitiesPcs = document.getElementsByClassName('qtyPcs');
        for(const quantity of quantitiesPcs)
        {
            const index = quantity.getAttribute('index');
            totalQuantityPcs += quantity.value.toNumber();
        }

        document.getElementById('totalQty').value = totalQuantity.numberFormat('#,##0.00');
        document.getElementById('totalQtyPcs').value = totalQuantityPcs.numberFormat('#,##0.00');
    }

    function openfacility()
	{
		const org = document.getElementById('org');
		if(org.value == '')
		{
			alert('Please select Organization first!');
			return;
		}
		
		openpopup("<c:url value='/page/popupfacilityview.htm?target=facility&organization='/>"+org.value);
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
		var grid = document.getElementById('grid['+index+']');
		if(grid.value == null || grid.value == '')
		{
			alert('<spring:message code="grid"/> <spring:message code="notif.empty"/> !');
			return;
		}
		
		openpopup("<c:url value='/page/popupcontainerview.htm?target=container['/>"+index+"]&grid="+grid.value);
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
		
		openpopup("<c:url value='/page/popupinventoryitemview.htm?target=product['/>"+index+"]&serial=false&index="+index+"&container="+con.value+"&organization="+org.value);
	}
	
	function openproductfrom(index)
	{
		openpopup("<c:url value='/page/popupproductview.htm?target=fromProduct['/>"+index+"]&ref=4AdjustProduction&index="+index);
	}
	
    function opencolour(target,index)
	{
		openpopup("<c:url value='/page/popupcolourview.htm?target='/>"+target+"&index="+index);
	}
    
    function convertProcess() {
    	const processes = document.getElementsByClassName('process');

		for(const process of processes)
		{
			let processValue = process.value;
			processValue = processValue.replaceAll('[','').replaceAll(']','').replaceAll('"','');
			process.value = processValue;
			
			checkOnHand(process.getAttribute('index'));
		}
    }
	
	function checkOnHand(index) {

		let prodId = $('#product\\['+index+'\\]').val();
		let prodFromId = $('#fromProduct\\['+index+'\\]').val();
		let colId = $('#colour\\['+index+'\\]').val();
		let proc = $('#process\\['+index+'\\]') ? $('#process\\['+index+'\\]').val():'';
		let conId = $('#container\\['+index+'\\]').val();
		
		$('#av\\['+index+'\\]').val(0.00);
		$('#quantity\\['+index+'\\]').val(0.00);
		//$('#qtyPcs\\['+index+'\\]').val(0.00);
		
		let requestData = {
            productId: prodId,
            containerId: conId
        };
		
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
							let amountPcs = document.getElementsByName('items['+index+'].amountPcs')[0];
							if(amount && json.product != null){
								amount.value = parseFloat(json.product.price).numberFormat('#,##0.00');
								amountPcs.value = parseFloat(json.product.price).numberFormat('#,##0.00');
							}
							else
								amount.value = parseFloat(0).numberFormat('#,##0.00');
						}
					}
				}
			});
			
			$.ajax({
				  url: '<c:url value="/page/popupinventoryitemlistjson.htm"/>',
				  data:requestData,
				  method: 'GET',
				  dataType: 'json',
				  success: function(json) {
				    const datalistId = 'barcodeList_' + index;
				    const $datalist = $('#' + datalistId);

				    $datalist.empty();

				    (json.barcodes || []).slice(0, 10).forEach(b => {
				      if (b.serial) {
				        $datalist.append($('<option>', { value: b.serial }));
				      }
				    });
				 }
			});
	    
		}
		
        display();
	}
	
	function opensupplier()
	{
		var org = document.getElementById('org');
		if(org.value == '')
		{
			alert('Please select Organization first!');
			return;
		}
		
		openpopup("<c:url value='/page/popupsupplierview.htm?target=supplier&organization="+org.value+"&active=true'/>");
	}
	
	function checkQuantity(index) {
		/*var onHand = document.getElementById('av[' + index + ']').value.toNumber();
		var qty = document.getElementById('receipted[' + index + ']').value.toNumber();
		
		if(qty > onHand) {
			alert('Issued Quantity cannot greater than On Hand !!!');
			document.getElementById('quantity[' + index + ']').value = 0.00;
			document.getElementById('qtyPcs[' + index + ']').value = 0.00;
			return;
		}
		
		var qtyBase = document.getElementById('baseQty[' + index + ']').value.toNumber();
		var qtyPcs = document.getElementById('qtyPcs[' + index + ']');
        qtyPcs.value = (qtyBase * qty).numberFormat('#,##0.00000');*/

        display();
	}
	
</script>