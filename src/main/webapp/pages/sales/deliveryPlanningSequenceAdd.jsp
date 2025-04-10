<%@ include file="/pages/includes/sirius-head.jsp"%>

<div class="toolbar">
	<a class="item-button-back" href="<c:url value='/page/deliveryplanningpreedit.htm?id=${planning_form.deliveryPlanning.id}'/>"><span>Back</span></a>
	<a class="item-button-save" ><span>Save</span></a>
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
						<select class="combobox-ext" disabled='true' id="customer" onchange="updateShippingAddress(this)">
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
						<form:select id="facility" path="facility" cssClass="combobox-ext">
							<c:if test='${not empty planning_form.facility}'>
								<form:option value='${planning_form.facility.id}' label='${planning_form.facility.name}' />
							</c:if>
						</form:select>
						<img src="assets/icons/list_extensions.gif" onclick="javascript:openpopup('<c:url value='/page/popupfacilityview.htm?target=facility&organization=${planning_form.deliveryPlanning.salesOrder.organization.id}'/>');" style="CURSOR:pointer;" title="Warehouse" />
					</td>
				</tr>
				<tr>
					<td width="14%" align="right"><spring:message code="deliverysequence.shippingaddress"/> : </td>
					<td width="35%">
							<form:select id="shippingAddress" path="postalAddress" cssClass="combobox-ext" onchange="updatedShippingAddressDetail(this.value)">
							<c:if test='${not empty planning_form.postalAddress}'>
								<form:option value='${planning_form.postalAddress.id}' label='${planning_form.postalAddress.address}' />
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="postaladdress.detail"/> : </td>
					<td><input id="addressDetail" size="45" class="input-disabled" disabled/></td>
				</tr>
				</table>
			</td>
			<td width="49%" valign="top" style="display: none;">
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
		<div id="mainTab" dojoType="TabContainer" style="width: 100%; height: 300px;">
			<div id="item" dojoType="ContentPane" label="Item" class="tab-pages" refreshOnShow="true">
				<fieldset style="border-color: #a6a6a6;border-width:1px;border-style:solid;border-radius:5px;padding:10px;background: #efefef;">
				<table id="lineItemTable" width="100%" cellpadding="0" cellspacing="0" style="margin-bottom:3px;" class="table-list">
					<thead>
					<tr>
						<th width="1%"><div style="width: 10px;">&nbsp;</div></th>
						<th width="8%" align="left"><spring:message code="product.code"/></th>
						<th width="20%" align="left"><spring:message code="product.name"/></th>
						<th width="10%" align="left"><spring:message code="productcategory"/></th>
						<th width="5%" align="center"><spring:message code="product.uom"/></th>
						<th width="8%">SO Qty</th>
						<th width="8%"><spring:message code="deliveryplanning.delivered"/></th>
						<th width="8%"><spring:message code="deliveryplanning.undelivered"/></th>
						<th width="8%"><spring:message code="deliveryplanning.unassigned"/></th>
						<th width="40%"><spring:message code="deliverysequence.quantity"/></th>
						<th width="1%">&nbsp;</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${items}" var="line" varStatus='status'>
					<tr>
						<td>&nbsp;</td>
						<td><input data-index="${status.index}" name="sequenceCode" type="text" value="<c:out value='${line.product.code}'/>" disabled size="15" class="input-disabled"/></td>
						<td><input type="text" value="<c:out value='${line.product.name}'/>" disabled size="35" class="input-disabled"/></tsteakd>
						<td><input type="text" value="<c:out value='${line.product.productCategory.name}'/>" disabled size="15" class="input-disabled"/></td>
						<td><input type="text" value="<c:out value='${line.product.unitOfMeasure.measureId}'/>" disabled class="input-disabled" size="5"/></td>
						<td><input id="soQuantity[${status.index}]" value="${line.quantity}" readonly="readonly" class="number-disabled" size="10"/></td>
						<td><input id="delivered" value="${line.delivered}" readonly="readonly" class="number-disabled" size="10"/></td>
						<td><input id="undelivered[${status.index}]" value="${line.quantity - line.delivered}" readonly="readonly" class="number-disabled" size="10"/></td>
						<td><input id="unassigned[${status.index}]" value="${line.quantity - line.assigned}" readonly="readonly" class="number-disabled" size="10"/></td>
						<td><input id="assigned[${status.index}]" name="items[${status.index}].quantity" size="10" class="inpt-decimal quantities" next="assigned" value="<fmt:formatNumber value='${line.quantity - line.assigned}' pattern=',##0.00'/>" index="${status.index}" onchange="checkQuantity(${status.index});"/></td>
						<td>
							<form:input path='items[${status.index}].reference' size='10' value="${line.reference}" readonly="true" type="hidden" index="${status.index}"/>
						</td>
					</tr>
					</c:forEach>
					</tbody>
					<tfoot><tr class="end-table"><td colspan="10">&nbsp;</td></tr>
				</table>
				</fieldset>
			</div>
		</div>
	</sesform:form>
</div>
<%@ include file="/pages/includes/sirius-foot.jsp"%>
<script type="text/javascript">
$(document).ready(function()
{
    $('#customer').change();
    
	$(".item-button-save").click(function(){
		if(validation()) {
			$.ajax({
	           url:"<c:url value='/page/deliveryplanningsequenceadd.htm'/>",
	           data:$('#addForm').serialize(),
	           method : 'POST',
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
	                       window.location="<c:url value='/page/deliveryplanningpreedit.htm?id='/>"+json.id;
	                   }
	                   else
	                   {
	                       $dialog.empty();
	                       $dialog.html('Proccess fail,reason :'+json.message);
	                   }
	               }
	           }
	       });
		}
    });
});
	    
function validation()
{
	if(!$('#date').val())
	{
        alert('<spring:message code="deliverysequence.date"/> <spring:message code="notif.empty"/> !');
		return false;
	}

	if(document.getElementById('facility').value == null || document.getElementById('facility').value == '')
	{
        alert('<spring:message code="facility"/> <spring:message code="notif.empty"/> !');
		return false;
	}

	if(document.getElementById('shippingAddress').value == null || document.getElementById('shippingAddress').value == '')
	{
        alert('<spring:message code="deliverysequence.shippingaddress"/> <spring:message code="notif.empty"/> !');
		return false;
	}

	let totalQuantity = 0;
    $.each($(".quantities"), function(i, obj)
    {
    	totalQuantity = totalQuantity + obj.value.toNumber();
    });

	if(totalQuantity == 0)
	{
        alert('<spring:message code="deliverysequence.quantity"/> <spring:message code="notif.empty"/> !!!');
		return false;
	}
	
	return true;
}

function updateShippingAddress(element) {
	let customerId = $('#customer').val();
	let addressId = "${planning_form.postalAddress.id}";
	
	Party.load(customerId);

	let _shippingAddress = $('#shippingAddress');
	if (_shippingAddress.find('option').length > 0) {
		_shippingAddress.empty();
	}

	let addresses = Party.data.partyAddresses;

	addresses.forEach(address => {
		let hasShippingEnabled = address.postalTypes.some(postalType =>
				postalType.type === 'SHIPPING' && postalType.enabled === true
		);

		if (hasShippingEnabled) {
			let option = $('<option></option>')
					.val(address.postalId)
					.text(address.addressName);

			if (address.postalId == addressId) {
				option.attr('selected', 'selected');
			}

			_shippingAddress.append(option);
		}
	});

	updatedShippingAddressDetail(_shippingAddress.val());
}

function updatedShippingAddressDetail(selectedId) {
	$('#addressDetail').val('');

	if (!selectedId || selectedId.trim() === "") {
		return;
	}

	PostalAddress.load(selectedId);

	if (PostalAddress.data) {
		var addressDetail = PostalAddress.data.postalAddress || '';
		var postalCode = PostalAddress.data.postalCode || '';
		var city = PostalAddress.data.postalCity ? PostalAddress.data.postalCity.name : '';

		$('#addressDetail').val(addressDetail);
	} else {
		$('#addressDetail').val('');
	}
}

function checkQuantity(index) {
	let unassigned = document.getElementById('unassigned['+index+']').value.toNumber();
	let assigned = document.getElementById('assigned['+index+']').value.toNumber();
	
	if(assigned > unassigned) {
        alert('<spring:message code="deliverysequence.quantity"/> <spring:message code="notif.greater"/> <spring:message code="deliveryplanning.unassigned"/> !');
        document.getElementById('assigned['+index+']').value = unassigned.numberFormat('#,##0.00');
		return;
	}
}
</script>