<%@ include file="/pages/includes/sirius-head.jsp"%>

<div class="toolbar">
	<a class="item-button-back" href="<c:url value='/page/deliveryplanningpreedit.htm?id=${planning_form.deliveryPlanning.id}'/>"><span>Back</span></a>
	<a class="item-button-save" href="javascript:save();"><span>Save</span></a>
</div>

<div class="main-box">
	<sesform:form id="addForm" name="addForm" method="post" modelAttribute="planning_form">
		<table width="100%" style="border:none">
		<tr>
			<td width="58%">
				<table width="100%" style="border:none">
				<tr>
					<td width="20%" nowrap="nowrap" align="right"><spring:message code="deliverysequence.sequence"/> :</td>
					<td width="80%"><input class='input-disabled' size='5' value="${planning_form.no}"/></td>
				</tr>
				<tr>
					<td nowrap="nowrap" align="right"><spring:message code="deliverysequence.date"/> :</td>
					<td>
						<input id="date" name="date" value="<fmt:formatDate value='${planning_form.date}' pattern='dd-MM-yyyy'/>" class="datepicker"/>
					</td>
				</tr>
				<tr>
					<td nowrap="nowrap" align="right"><spring:message code="organization"/> :</td>
					<td>
						<select class="combobox-ext" disabled='true' id="org">
							<option value="${planning_form.deliveryPlanning.salesOrder.organization.id}"><c:out value='${planning_form.deliveryPlanning.salesOrder.organization.fullName}'/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td nowrap="nowrap" align="right"><spring:message code="customer"/> :</td>
					<td>
						<select class="combobox-ext" disabled='true' id="customer">
							<option value="${planning_form.deliveryPlanning.salesOrder.customer.id}"><c:out value='${planning_form.deliveryPlanning.salesOrder.customer.fullName}'/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="sirius.tax"/> :</td>
					<td>
						<select class="combobox-ext" disabled='true' id="tax">
							<option value="${planning_form.tax.id}"><c:out value='${planning_form.tax.taxName}'/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="facility"/> : </td>
					<td>
						<form:select id="warehouse" path="facility" cssClass="combobox-ext">
							<c:if test='${not empty planning_form.facility}'>
								<form:option value='${planning_form.facility.id}' label='${planning_form.facility.name}' />
							</c:if>
						</form:select>
						<img src="assets/icons/list_extensions.gif" onclick="javascript:openpopup('<c:url value='/page/popupfacilityview.htm?target=warehouse&organization=${planning_form.deliveryPlanning.salesOrder.organization.id}'/>');" style="CURSOR:pointer;" title="Warehouse" />
					</td>
				</tr>
				<tr>
					<td width="14%" align="right"><spring:message code="deliverysequence.shippingaddress"/> : </td>
					<td width="35%">
							<form:select id="postalAddress" path="postalAddress" cssClass="combobox-ext">
							<c:if test='${not empty planning_form.postalAddress}'>
								<form:option value='${planning_form.postalAddress.id}' label='${planning_form.postalAddress.address}' />
							</c:if>
						</form:select>
						<img src="assets/icons/list_extensions.gif" onclick="javascript:openpopup('<c:url value='/page/popuppostaladdressview.htm?target=postalAddress&party=${planning_form.deliveryPlanning.salesOrder.customer.id}'/>');" style="CURSOR:pointer;" title="Address" />
					</td>
				</tr>
				</table>
			</td>
			<td width="49%" valign="top">
				<fieldset>
					<legend><spring:message code="deliveryplanning.productinfo"/></legend>
					<table width="100%" style="border:none">
					<tr>
						<th width="15%" nowrap="nowrap"><spring:message code="sirius.name"/></th>
						<th width="8%" nowrap="nowrap">SO Qty</th>
						<th width="8%" nowrap="nowrap">DPS Qty</th>
						<th width="8%" nowrap="nowrap">DOB Qty</th>
						<th width="8%" nowrap="nowrap"><spring:message code="deliveryplanning.unassigned"/></th>
						<th></th>
					</tr>
					<c:forEach items='${salesItems}' var='item'>
					<tr>
						<td><c:out value='${item.product.name}'/></td>
						<td><input value="<fmt:formatNumber value='${item.quantity}' pattern=',##0'/>" class="number-disabled" disabled size='10'/></td>
						<td><input value="<fmt:formatNumber value='${item.assigned-item.delivered}' pattern=',##0'/>" class="number-disabled" disabled size='10'/></td>
						<td><input value="<fmt:formatNumber value='${item.delivered}' pattern=',##0'/>" class="number-disabled" disabled size='10'/></td>
						<td><input value="<fmt:formatNumber value='${item.quantity-item.assigned}' pattern=',##0'/>" class="number-disabled reference-bold" disabled size='10'/></td>
					</tr>
					</c:forEach>
					</table>
				</fieldset>
			</td>
		</tr>
		</table>
		<br/>
		<c:forEach items="${salesItems}" var="line" varStatus='status'>
			<fieldset style="border-color: #a6a6a6;border-width:1px;border-style:solid;border-radius:5px;padding:15px;background: #efefef;">
			<div class="toolbar-clean" style="background:#AEC4D2;padding-top:2px;margin-bottom:3px;">
				<a class="item-button-new" onclick="addLineItem('itemTable[${status.index}]','${line.id}','${line.product.id}','${status.index}');" target="itemTable[${status.index}]" sequenceItemId="${line.id}" productParentId="${line.product.id}" parentIndex="${status.index}" productPackagingId="${line.product.id}"><span>New Row</span></a>
				<a class="item-button-delete" style="cursor: pointer;" value="${line.product.id}"><span>Delete Row</span></a>
			</div>
			<table id="lineItemTable" width="100%" cellpadding="0" cellspacing="0" style="margin-bottom:3px;">
				<thead>
					<tr>
						<th width="9%" align="left"><spring:message code="product.code"/></th>
						<th width="20%" align="left"><spring:message code="product.name"/></th>
						<th width="12%" align="left"><spring:message code="productcategory"/></th>
						<th width="6%" align="center"><spring:message code="product.uom"/></th>
						<th width="9%">SO Qty</th>
						<th width="9%">DO Qty</th>
						<th width="9%"><spring:message code="deliveryplanning.undelivered"/></th>
						<th width="9%"><spring:message code="deliveryplanning.unassigned"/></th>
						<th width="9%"><spring:message code="deliveryplanning.assigned"/></th>
						<th width="1%">&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="qtyToBase" value="${line.product.qtyToBase}"/>
					<tr>
						<td><input data-index="${status.index}" name="sequenceCode" type="text" value="<c:out value='${line.product.code}'/>" disabled size="15" class="input-disabled"/></td>
						<td><input type="text" value="<c:out value='${line.product.name}'/>" disabled size="35" class="input-disabled"/></tsteakd>
						<td><input type="text" value="<c:out value='${line.product.productCategory.name}'/>" disabled size="15" class="input-disabled"/></td>
						<td><input type="text" value="<c:out value='${line.product.unitOfMeasure.measureId}'/>" disabled class="input-disabled" size="5"/></td>
						<td><input id="soQuantity[${status.index}]" value="${line.quantity / qtyToBase}" readonly="readonly" class="number-disabled" size="10"/></td>
						<td><input id="delivered" value="${line.delivered / qtyToBase}" readonly="readonly" class="number-disabled" size="10"/></td>
						<td><input id="undelivered[${status.index}]" value="${(line.quantity - line.delivered) / qtyToBase}" readonly="readonly" class="number-disabled" size="10"/></td>
						<td><input id="unassign[${status.index}]" value="${(line.quantity - line.assigned) / qtyToBase}" readonly="readonly" class="number-disabled" size="10"/></td>
						<td><input id="assigned[${status.index}]" size="10" class="number-disabled" disabled value="<fmt:formatNumber value='0' pattern=',##0.00'/>" /></td>
						<td>
							<form:input path='items[${status.index}].quantity' size='10' readonly="true" type="hidden"/>
							<input id="amount[${status.index}]" value="${line.money.amount}" type="hidden"/>
							<input id="discRupiah[${status.index}]" value="${line.discount}" type="hidden"/>
						</td>
					</tr>
				</tbody>
			</table>
			<table id="itemTable[${status.index}]" cellspacing="0" cellpadding="0" align="center" width="100%" class="table-list">
				<thead>
					<tr>
						<th width="1%"><input type="checkbox" class="checkall" value="${line.product.id}"/></th>
						<th nowrap="nowrap" align="left">Code</th>
						<th colspan="2" nowrap="nowrap" width="15%" align="left">Name</th>
						<th nowrap="nowrap" width="10%" align="left">Category</th>
						<th width="8%" align="center">UoM</th>
						<th width="8%" nowrap="nowrap" align="center">Qty Base</th>
						<th width="8%" nowrap="nowrap" align="center">On Hand</th>
						<th width="8%" nowrap="nowrap" align="center">Available</th>
						<th width="8%" align="center">Qty</th>
						<th width="10%" nowrap="nowrap" align="center">Grid</th>
						<th width="10%" nowrap="nowrap" align="center">Container</th>
						<th width="8%" nowrap="nowrap" align="center">Container Index</th>
						<th width="8%" nowrap="nowrap" align="center">Print Index</th>
						<th colspan="8"></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
				<tfoot>
					<tr class="end-table">
						<td colspan="22">&nbsp;</td>
					</tr>
				</tfoot>
			</table>
			</fieldset>
			<br/>
		</c:forEach>
	</sesform:form>
</div>

<%@ include file="/pages/includes/sirius-foot.jsp"%>
<script type="text/javascript">
	var index = 0;
	function save()
	{
		if(!$('#date').val())
		{
			alert('Please select Date first !!!');
			return;
		}

		if(document.getElementById('warehouse').value == null || document.getElementById('warehouse').value == '')
		{
			alert('Please select Warehouse first!');
			return;
		}

		if(document.getElementById('postalAddress').value == null || document.getElementById('postalAddress').value == '')
		{
			alert('Please select shipping address first!');
			return;
		}

		var qtys = document.getElementsByName("quantities");
		for(var idx=0;idx<qtys.length;idx++)
		{
			if(qtys[idx].value == "0")
			{
				alert("Quantity cant equal or less than zero, please delete the row if not used");
				qtys[idx].className="input-warning";
				qtys[idx].focus();
				return;
			}
		}
		if(qtys.length!=0){
			document.addForm.action = "<c:url value='/page/deliveryplanningsequenceadd.htm'/>";
			document.addForm.submit();
		}else{
			alert("Please add row sequence to save transaction");
		}
	}
    
    $(function(){
        $('#date').datepicker().on("change", function(){
            var dateNow = new Date();
            var date = $('#date').datepicker('getDate');

            if(date < dateNow)
                $('#date').datepicker('setDate','today');

		});
		
		$('.checkall').click(function () {
           	$('.check'+$(this).attr('value')).prop("checked", this.checked);
       	});

       	$('.item-button-delete').click(function () {
           	$('.check'+$(this).attr('value')).each(function(){
               	if(this.checked){
                   	this.checked = false;
                   	$(this).parent().parent().remove();
               	}
           	});
            $('.checkall').prop("checked", false);
		});
		
		$('#warehouse').focus(function(){
			$('.checkall').click();
			$('.item-button-delete').click();
		});

		loadAutoItem();

	});
	
	async function loadAutoItem() {
		const facilityDom = document.getElementById('warehouse').value;

		const facilityResult = await fetch("<c:url value='/page/facilityview.htm?id='/>" + facilityDom, {
			headers: {
				Accept: 'application/json'
			}
		});

		const facilityJson = await facilityResult.json();
	
		if(facilityJson.status == 'OK')
		{
			const defaultContainer = facilityJson.facilitys[0].grid.container.id;
			
			if(defaultContainer) 
			{
				
				const buttons = document.getElementsByClassName('item-button-new');

				for(const button of buttons)
				{
					const target = button.getAttribute('target');					
					const sequenceItemId = button.getAttribute('sequenceItemId');
					const productParentId = button.getAttribute('productParentId');
					const parentIndex = button.getAttribute('parentIndex');
					const productPackagingId = button.getAttribute('productPackagingId');

					const result = await fetch("<c:url value='/page/popupproductfordeliveryplanningview.htm?'/>" + new URLSearchParams({
						target: "", index: "", parentIndex: "",
						organization: document.getElementById('org').value,
						active: "true",
						productId: productPackagingId,
						facility: facilityDom
					}), {
						headers: {
							Accept: 'application/json'
						}
					});

					const json = await result.json();

					if(json.status == 'OK')
					{
						const inventoryItem = json.products[0];

						if(inventoryItem)
						{
							addLineItem(target, sequenceItemId, productParentId, parentIndex, productPackagingId)
							
							document.getElementById('codeProd[' + (index - 1) + ']').value = inventoryItem.product.code;

							const productOpt = document.createElement('option');
							productOpt.value = inventoryItem.product.id;
							productOpt.innerHTML = inventoryItem.product.name;

							document.getElementById('product[' + (index -1) + ']').appendChild(productOpt);
							document.getElementById('categoryProd[' + (index - 1) + ']').value = inventoryItem.product.productCategory.name;
							document.getElementById('uom[' + (index - 1) + ']').value = inventoryItem.product.unitOfMeasure.measureId;
							document.getElementById('qtyToBase[' + (index - 1) + ']').value = inventoryItem.product.qtyToBase.numberFormat('#,##0.00');
							document.getElementById('onHand[' + (index - 1) + ']').value = inventoryItem.onHand.numberFormat('#,##0.00');
							document.getElementById('available[' + (index - 1) + ']').value = inventoryItem.availableSale.numberFormat('#,##0.00');
							document.getElementById('grid[' + (index - 1) + ']').value = inventoryItem.grid.name;
							
							const containerOpt = document.createElement('option');
							containerOpt.value = inventoryItem.container.id;
							containerOpt.innerHTML = inventoryItem.container.name;

							document.getElementById('container[' + (index - 1) + ']').appendChild(containerOpt);
							if(inventoryItem.container.containerIndex) {
								document.getElementById('ctrIdx[' + (index - 1) + ']').value = inventoryItem.container.containerIndex;
								document.getElementById('prnIdx[' + (index - 1) + ']').value = inventoryItem.container.containerIndex;
							}

							const unassign = document.getElementById('unassign[' + parentIndex + ']').value.toNumber();

							const quantity = document.getElementById('quantity[' + (index - 1) + ']');

							if(unassign > inventoryItem.availableSale)
								quantity.value = inventoryItem.availableSale.numberFormat('#,##0.00');
							else
								quantity.value = unassign.numberFormat('#,##0.00');

							quantity.blur();

							var unitPrice = document.getElementById('amount['+parentIndex+']');
							var discOne = document.getElementById('discOne['+parentIndex+']');
							var discTwo = document.getElementById('discTwo['+parentIndex+']');
							var discThree = document.getElementById('discThree['+parentIndex+']');
							var discRp = document.getElementById('discRupiah['+parentIndex+']');
							var price = unitPrice.value.toNumber()*inventoryItem.product.qtyToBase;
							setPriceCustomer( (index - 1) , price, discOne.value, discTwo.value, discThree.value, discRp.value);
						} 
						
					}
					else
						alert(json.message);
				}
			}
		}
		else
			alert(facilityJson.message);

	}

	function addLineItem(target, sequenceItemId, productParentId, parentIndex, productPackagingId)
	{
		var tbl = document.getElementById(target);
		if(tbl)
		{
			var t_section = tbl.tBodies[0];
			if(t_section)
			{
				var row = t_section.insertRow(t_section.rows.length);
				row.insertCell(0).appendChild(genCheck(index,parentIndex, productParentId));
				row.insertCell(1).appendChild(genCodeProd(index));
				row.insertCell(2).appendChild(genProduct(index));
				row.insertCell(3).appendChild(genPopup(index, parentIndex));
				row.insertCell(4).appendChild(genCategoryProd(index));
				row.insertCell(5).appendChild(genUom(index));
				row.insertCell(6).appendChild(genQtyToBase(index));
				row.insertCell(7).appendChild(genOnHand(index));
				row.insertCell(8).appendChild(genAvailable(index));
				row.insertCell(9).appendChild(genQty(index, parentIndex));
				row.insertCell(10).appendChild(genGrid(index));
				row.insertCell(11).appendChild(genContainer(index));
				row.insertCell(12).appendChild(genCtrIndex(index));
				row.insertCell(13).appendChild(genPrintIndex(index, parentIndex));
				row.insertCell(14).appendChild(genSequenceItemId(index, sequenceItemId));
				row.insertCell(15).appendChild(genProductParentId(index, productParentId, productPackagingId));
				row.insertCell(16).appendChild(genHiddenIndex(index));
				row.insertCell(17).appendChild(genHiddenPrice(index));
				row.insertCell(18).appendChild(genHiddenDiscOne(index));
				row.insertCell(19).appendChild(genHiddenDiscTwo(index));
				row.insertCell(20).appendChild(genHiddenDiscThree(index));//;
				row.insertCell(21).appendChild(genHiddenDiscRp(index));
				index++;
			}
		}
	}

	function genCheck(index,parentIndex, productParentId)
	{
		var check = document.createElement("input");
		check.type = "checkbox";
		check.name = "check";
		check.id = "check["+index+"]";

		check.setAttribute('class', 'check'+productParentId);
		check.setAttribute("data-index",index);
		check.setAttribute("parent-index",parentIndex);
		return check;
	}

	function genProduct(index)
	{
		var itemx = document.createElement("select");
		itemx.id="product["+index+"]";
		itemx.name="products";
		itemx.setAttribute('class','combobox');

		return itemx;
	}

	function genPopup(index, parentIndex)
	{
		var _popup = document.createElement('img');
		_popup.id = '_popup_line['+index+']';
		_popup.src = 'assets/icons/list_extensions.gif';
		_popup.onclick = function()
		{
			var fac = document.getElementById('warehouse').value;
			var prodParentId = document.getElementById('productParentId['+index+']');
			var prodPackagingId = prodParentId.getAttribute('productPackagingId');
			var org = document.getElementById('org');
			if(org.value == null || org.value == '') {
				alert('Please select Organization first !!!');
				return;
			}

			var product="product["+index+"]";
			// openpopup("<c:url value='/page/popupproductfordeliveryplanningview.htm?target='/>"+product+"&index="+index+"&organization="+org.value+"&categoryId=2&active=true&productParentId="+prodParentId.value+"&parentIndex="+parentIndex+"&facility="+fac);
			openpopup("<c:url value='/page/popupproductfordeliveryplanningview.htm?target='/>"+product+"&index="+index+"&organization="+org.value+"&categoryId=2&active=true&productId="+prodPackagingId+"&parentIndex="+parentIndex+"&facility="+fac);
		}
		_popup.setAttribute('style','CURSOR:pointer;');
		_popup.title='Product';

		return _popup;

	}

	function genCodeProd(index)
	{
		var codePrd = document.createElement("input");
		codePrd.type = "text";
		codePrd.id = "codeProd["+index+"]";
		codePrd.setAttribute('size',"10");
		codePrd.setAttribute('class','input-disabled');
		codePrd.disabled = true;

		return codePrd;
	}

	function genCategoryProd(index)
	{
		var categoryPrd = document.createElement("input");
		categoryPrd.type = "text";
		categoryPrd.id = "categoryProd["+index+"]";
		categoryPrd.setAttribute('size',"10");
		categoryPrd.setAttribute('class','input-disabled');
		categoryPrd.disabled = true;

		return categoryPrd;
	}

	function genUom(index)
	{
		var uom = document.createElement("input");
		uom.type = "text";
		uom.id = "uom["+index+"]";
		uom.setAttribute('size',"3");
		uom.setAttribute('class','input-disabled');
		uom.disabled = true;

		return uom;
	}

	function genGrid(index)
	{
		var grid = document.createElement("input");
		grid.type = "text";
		grid.id = "grid["+index+"]";
		grid.setAttribute('size',"8");
		grid.setAttribute('class','input-disabled');
		grid.disabled = true;

		return grid;
	}


	function genCtrIndex(index)
	{
		var ctrIndex = document.createElement("input");
		ctrIndex.type = "text";
		ctrIndex.id = "ctrIdx["+index+"]";
		ctrIndex.setAttribute('size',"3");
		ctrIndex.setAttribute('class','input-disabled');
		ctrIndex.disabled = true;

		return ctrIndex;
	}

	function genPrintIndex(index, parentIndex)
	{
		var prnIndex = document.createElement("input");
		prnIndex.type = "text";
		prnIndex.value = 0;
		prnIndex.id = "prnIdx["+index+"]";
		prnIndex.name = "printIndex";
		prnIndex.setAttribute('size',"3");
		prnIndex.setAttribute('class','input-disabled');
		prnIndex.readOnly = true;

		return prnIndex;
	}

	function genContainer(index)
	{
		var container = document.createElement("select");
		container.type = "text";
		container.id = "container["+index+"]";
		container.name="containers";
		container.setAttribute('class','combobox-small');

		return container;
	}

	function genQtyToBase(index)
	{
		var baseQty = document.createElement("input");
		baseQty.type = "text";
		baseQty.id = "qtyToBase["+index+"]";
		baseQty.setAttribute('size',"6");
		baseQty.setAttribute('class','input-disabled');
		baseQty.value=0;
		baseQty.disabled = true;

		return baseQty;
	}

	function genOnHand(index)
	{
		var stock = document.createElement("input");
		stock.type = "text";
		stock.id = "onHand["+index+"]";
		stock.setAttribute('size',"6");
		stock.setAttribute('class','input-disabled');
		stock.value=0;
		stock.disabled = true;

		return stock;
	}
	function genAvailable(index)
	{
		var stock = document.createElement("input");
		stock.type = "text";
		stock.id = "available["+index+"]";
		stock.setAttribute('size',"6");
		stock.setAttribute('class','input-disabled');
		stock.disabled = true;
		stock.value=0;

		return stock;
	}

	function genQty(index, parentIndex)
	{
		var quantity = document.createElement("input");
		quantity.type = "text";
		quantity.name = "quantities";
		quantity.id = "quantity["+index+"]";
		quantity.setAttribute('size',"6");
		quantity.value = 0;
		quantity.setAttribute('onchange','checkOnHand();');
		//quantity.setAttribute('onkeyup',"separator('quantity["+index+"]');");

		return quantity;
	}

	function genSequenceItemId(index, sequenceItemId)
	{
		var sId = document.createElement("input");
		sId.type = "hidden";
		sId.id = "sequenceItem["+index+"]";
		sId.name = "item";
		sId.value = sequenceItemId;

		return sId;
	}

	function genProductParentId(index, productParentId, productPackagingId)
	{
		var pId = document.createElement("input");
		pId.type = "hidden";
		pId.id = "productParentId["+index+"]";
		pId.name = "productParentIds";
		pId.value = productParentId;
		pId.setAttribute('productPackagingId', productPackagingId);

		return pId;
	}

	function genHiddenIndex(index)
	{
		var pType = document.createElement("input");
		pType.type = "hidden";
		pType.id = "hiddenIndex["+index+"]";
		pType.name = "hiddenIndexes";
		pType.value = index;

		return pType;
	}

	function genHiddenPrice(index)
	{
		var amount = document.createElement("input");
		amount.type = "hidden";
		amount.id = "price["+index+"]";
		amount.name = "prices";
		amount.value = 0;

		return amount;
	}

	function genHiddenDiscOne(index)
	{
		var disc = document.createElement("input");
		disc.type = "hidden";
		disc.id = "disc1["+index+"]";
		disc.name = "discountOnes";
		disc.value = 0;

		return disc;
	}

	function genHiddenDiscTwo(index)
	{
		var disc = document.createElement("input");
		disc.type = "hidden";
		disc.id = "disc2["+index+"]";
		disc.name = "discountTwos";
		disc.value = 0;

		return disc;
	}

	function genHiddenDiscThree(index)
	{
		var disc = document.createElement("input");
		disc.type = "hidden";
		disc.id = "disc3["+index+"]";
		disc.name = "discountThrees";
		disc.value = 0;

		return disc;
	}

	function genHiddenDiscRp(index)
	{
		var disc = document.createElement("input");
		disc.type = "hidden";
		disc.id = "discRp["+index+"]";
		disc.name = "discountRupiahs";
		disc.value = 0;

		return disc;
	}

	function setPriceCustomer(index, product, amt)
	{
		var organization = $("#org").val();
		var customer = $("#customer").val();
		$.get("<c:url value='/page/productstandardpriceremote.getpricebycustomer.json'/>",{organizationId:organization, productId:product, customerId:customer, amount:amt},
		function(json){
			document.getElementById("price["+index+"]").value = json.amount;
			document.getElementById("disc1["+index+"]").value = json.discountOne;
			document.getElementById("disc2["+index+"]").value = json.discountTwo;
			document.getElementById("disc3["+index+"]").value = json.discountThree;
		});
	}

	function setPriceCustomer(index, unitPrice, discOne, discTwo, discThree, discRp)
	{
		document.getElementById("price["+index+"]").value = unitPrice;
		document.getElementById("disc1["+index+"]").value = discOne;
		document.getElementById("disc2["+index+"]").value = discTwo;
		document.getElementById("disc3["+index+"]").value = discThree;
		document.getElementById("discRp["+index+"]").value = discRp;
	}

	function checkOnHand()
	{
		var seqCode=$("input[name='sequenceCode']");
		for(var a=0;a<seqCode.length;a++){
			var parentIndex=$(seqCode[a]).attr("data-index");
			var soQty = parseFloat(document.getElementById('soQuantity['+parentIndex+']').value.replace(/,/g,''));
			var unassign = parseFloat(document.getElementById('unassign['+parentIndex+']').value.replace(/,/g,''));
			var assigned = parseFloat(document.getElementById('assigned['+parentIndex+']').value.replace(/,/g,''));
			var tbl = document.getElementById('itemTable['+parentIndex+']');

			var assignedQty=0;
			var checks=$("input[name='check']");
			for(var b=0;b<checks.length;b++){
				var checkParentIndex=$(checks[b]).attr("parent-index");
				if(parentIndex==checkParentIndex){
					var idx=$(checks[b]).attr("data-index");

					var available = parseFloat(document.getElementById('available['+idx+']').value.replace(/,/g,''));
					var qty = parseFloat(document.getElementById('quantity['+idx+']').value.replace(/,/g,''));
					if(qty > available) {
						alert('Assigned Quantity cannot greater than Available Quantity (Max: '+available+')!!!');
						document.getElementById('quantity['+idx+']').value = 0;
					}

					if(tbl) {
						var t_section = tbl.tBodies[0];
						var len = t_section.rows.length;

						var baseQty=parseFloat($("#qtyToBase\\["+idx+"\\]").val().replace(/,/g,''));
						var qty=parseFloat($("#quantity\\["+idx+"\\]").val().replace(/,/g,''));
						// var q=qty*baseQty;
						var q=qty;
						assignedQty+=q;
						if(assignedQty > unassign) {
							alert('Assigned Quantity ( '+assignedQty+' ) cannot greater than Unassign Quantity ( '+unassign+' ) !!!');
							document.getElementById('quantity['+idx+']').value = 0;
							assignedQty-=q;
						}/* else if(assignedQty > soQty) {
							alert('Assigned Quantity cannot greater than SO Quantity !!!');
							for(var idx=len-1;idx>=0;idx--)
								t_section.deleteRow(idx);
							document.getElementById('quantity['+idx+']').value = 0;
							assignedQty-=q;
						} */
						if(qty!=0)
							document.getElementById('quantity['+idx+']').className="";
					}
				}
			}
			//document.getElementById('assigned['+parentIndex+']').value = assignedQty;
		}
	}
</script>