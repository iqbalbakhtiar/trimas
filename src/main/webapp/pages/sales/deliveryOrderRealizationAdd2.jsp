<%@ include file="/common/sirius-general-top.jsp"%>

<div class="toolbar">
	<a class="item-button-list" href="<c:url value='/page/deliveryorderrealizationpreadd1.htm'/>"><span><spring:message code="sirius.list"/></span></a>
	<a class="item-button-rprev" href="<c:url value='/page/deliveryorderrealizationpreadd1.htm'/>"><span><spring:message code="sirius.back"/></span></a>
	<a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>

<div class="main-box">
<sesform:form id="addForm" name="addForm" method="post" modelAttribute="dor_form" enctype="multipart/form-data">
<%--	To Avoid Hibernate No Session trigger Currency and Tax--%>
	<input style="display: none" datafld="<c:out value="${dor_form.tax}"></c:out><c:out value="${dor_form.currency}"></c:out>">
	<table width="100%" border="0">
		<tr>
			<td width="60%">
				<table style="border:none" width="100%">
				<tr>
					<td width="34%" align="right"><spring:message code="sirius.id"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input class="inputbox input-disabled" value="Auto Number" disabled/></td>
					<td width="1%"><form:errors path="code"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="organization"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="org" path="organization" cssClass="combobox input-disabled" disabled="true">
							<c:if test='${not empty dor_form.deliveryOrder.organization}'>
								<form:option value='${dor_form.deliveryOrder.organization.id}' label='${dor_form.deliveryOrder.organization.fullName}'/>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryrealization.dor.date"/></td>
					<td width="1%" align="center">:</td>
					<td><input id="date" name="date" class="datepicker" value=""/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryrealization.do.no"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="deliveryOrderNo" name="deliveryOrderNo" class="inputbox input-disabled" value="${dor_form.deliveryOrder.code}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="customer"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="customerName" name="customerName" class="inputbox input-disabled" value="${dor_form.deliveryOrder.customer.fullName}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="salesorder.shipping.name"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="addressName" name="addressName" class="inputbox input-disabled" value="${dor_form.deliveryOrder.shippingAddress.addressName}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryorder.shippingaddress.detail"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="addressDetail" name="addressDetail" class="inputbox input-disabled" value="${dor_form.deliveryOrder.shippingAddress.address}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="postaladdress.postalcode"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="postalCode" name="postalCode" class="inputbox input-disabled" value="${dor_form.deliveryOrder.shippingAddress.postalCode}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="postaladdress.city"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="city" name="city" class="inputbox input-disabled" value="${dor_form.deliveryOrder.shippingAddress.city.name}" disabled/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryorder.issue.facility"/></td>
					<td width="1%" align="center">:</td>
					<td>
						<form:select id="facility" path="facility" cssClass="combobox input-disabled" disabled="true">
							<c:if test='${not empty dor_form.deliveryOrder.facility}'>
								<form:option value='${dor_form.deliveryOrder.facility.id}' label='${dor_form.deliveryOrder.facility.name}'/>
							</c:if>
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="deliveryrealization.driver.courier"/></td>
					<td width="1%" align="center">:</td>
					<td width="64%"><input id="driver" name="driver" class="inputbox"/></td>
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
								<legend><strong><spring:message code="deliveryrealization.do.real"/></strong></legend>
								<table width="100%" style="border: none">
									<tr>
										<td width="50%" align="right"><spring:message code="deliveryrealization.accept.date"/>:</td>
										<td width="50%">&nbsp;<input id="acceptanceDate" name="acceptanceDate" class="datepicker"/></td>
									</tr>
									<tr>
										<td width="50%" align="right"><spring:message code="deliveryrealization.return.date"/>:</td>
										<td width="50%">&nbsp;<input id="returnDate" name="returnDate" class="datepicker"/></td>
									</tr>
									<tr>
										<td width="50%" align="right"><spring:message code="sirius.note"/>:</td>
										<td width="50%">&nbsp;<textarea name="noteExt" id="noteExt" rows="4" cols="25"></textarea></td>
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
		<div id="productLineItem" dojoType="ContentPane" label="<spring:message code='salesorder.lineitem'/>" class="tab-pages" refreshOnShow="true" selected="true">
  			<div class="toolbar-clean">
		    	<table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
			    	<thead>
				    	<tr>
				    		<th width="1%" nowrap="nowrap">&nbsp;</th>
							<th width="20%" nowrap="nowrap"><spring:message code="product"/></th>
							<th width="5%" ><spring:message code="deliveryorder.soquantity"/></th>
							<th width="5%" ><spring:message code="deliveryorder.doquantity"/></th>
							<th width="5%" ><spring:message code="deliveryorder.accepted.qty"/></th>
							<th width="5%" ><spring:message code="deliveryorder.returned.qty"/></th>
							<th width="5%" ><spring:message code="deliveryorder.shrinkage.qty"/></th>
							<th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
							<th width="60%" nowrap="nowrap"><spring:message code="deliveryorder.note"/></th>
						</tr>
					</thead>
					<tbody id="lineItem">
					<c:forEach items="${dor_form.items}" var="item" varStatus="idx">
						<tr>
							<td>&nbsp;</td>
							<td><input id="product[${idx.index}]" size="40" value="${item.salesReferenceItem.product.name}" class="input-disabled productInput"
									   name="items[${idx.index}].product" index="${idx.index}" next="product" disabled/>
							</td>
							<td>
								<input id="soquantity[${idx.index}]" size="10" value="${item.salesReferenceItem.quantity}" class="input-disabled input-decimal"
									   name="items[${idx.index}].soquantity" index="${idx.index}" next="soquantity" disabled/>
							</td>
							<td>
								<input id="doquantity[${idx.index}]" size="10" value="${item.salesReferenceItem.delivered}" class="input-disabled input-decimal"
									   name="items[${idx.index}].doquantity" index="${idx.index}" next="doquantity" disabled/>
							</td>
							<td>
								<input id="accepted[${idx.index}]" size="10" value="${item.salesReferenceItem.delivered}" class="input-number" onchange="calculateQty(this)"
									   name="items[${idx.index}].accepted" index="${idx.index}" next="accepted"/>
							</td>
							<td>
								<input id="returned[${idx.index}]" size="10" value="0.00" class="input-number" onchange="calculateQty(this)"
									   name="items[${idx.index}].returned" index="${idx.index}" next="returned"/>
							</td>
							<td>
								<input id="shrinkage[${idx.index}]" size="10" value="0.00" class="input-number input-disabled"
									   name="items[${idx.index}].shrinkage" index="${idx.index}" next="shrinkage" readonly/>
							</td>
							<td>
								<input id="uom[${idx.index}]" size="6" value="${item.salesReferenceItem.product.unitOfMeasure.measureId}" class="input-disabled"
									   name="items[${idx.index}].uom" index="${idx.index}" next="uom" disabled/>
							</td>
							<td>
								<input id="note[${idx.index}]" type="text" size="60" name="items[${idx.index}].note"
									   index="${idx.index}" next="note"/>
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
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
$(function(){
	$('.item-button-save').click(function(){
		if(validateForm()) {
			save();
		}
	});
});

function validateForm() {
	// Validasi organisasi
	var organization = $('#org').val();
	if (organization == null || organization === "") {
		alert('<spring:message code="sirius.organization"/> <spring:message code="notif.empty"/> !');
		return false;
	}

	var date = $('#date').val();
	if (date == null || date === "") {
		alert('<spring:message code="deliveryrealization.dor.date"/> <spring:message code="notif.empty"/> !');
		return false;
	}

	var acceptanceDate = $('#acceptanceDate').val();
	if (acceptanceDate == null || acceptanceDate === "") {
		alert('<spring:message code="deliveryrealization.accept.date"/> <spring:message code="notif.empty"/> !');
		return false;
	}

	var returnDate = $('#returnDate').val();
	if (returnDate == null || returnDate === "") {
		alert('<spring:message code="deliveryrealization.return.date"/> <spring:message code="notif.empty"/> !');
		return false;
	}

	var driver = $('#driver').val();
	if (driver == null || driver.trim() === ''){
		alert('<spring:message code="deliveryrealization.driver.courier"/> <spring:message code="notif.empty"/> !');
		return false;
	}

	var facility = $('#facility').val();
	if (facility == null || facility === "") {
		alert('<spring:message code="facility"/> <spring:message code="notif.empty"/> !');
		return false;
	}

	// Validasi Line Items
	var isValid = true;
	$('#lineItem tr').each(function (index) {
		var $row = $(this);

		var productName = $row.find('input[id^="product["]').val();

		// Ambil nilai dari input yang sesuai di baris yang sama
		var doQty = parseFloat($row.find('input[id^="doquantity["]').val()) || 0;
		var accepted = parseFloat($row.find('input[id^="accepted["]').val()) || 0;
		var returned = parseFloat($row.find('input[id^="returned["]').val()) || 0;

		// Hitung jumlah shrinkage
		var shrinkQty = doQty - accepted - returned;

		// Validasi untuk memastikan tidak ada nilai negatif
		if (shrinkQty < 0) {
			alert('<strong>' + productName + '</strong> - <spring:message code="deliveryorder.shrinkage.qty"/> <spring:message code="notif.invalid"/>');
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
		url:"<c:url value='/page/deliveryorderrealizationadd.htm'/>",
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
					<%--window.location="<c:url value='/page/deliveryorderrealizationview.htm'/>";--%>
					// Or Can use This
					window.location="<c:url value='/page/deliveryorderrealizationpreedit.htm?id='/>"+json.id;
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

function calculateQty(element) {
	// Temukan baris yang terkait dengan elemen yang berubah
	var $row = $(element).closest('tr');

	var productName = $row.find('input[id^="product["]').val();

	// Ambil nilai dari input yang sesuai di baris yang sama
	var doQty = parseFloat($row.find('input[id^="doquantity["]').val()) || 0;
	var accepted = parseFloat($row.find('input[id^="accepted["]').val()) || 0;
	var returned = parseFloat($row.find('input[id^="returned["]').val()) || 0;

	// Hitung jumlah shrinkage
	var shrinkQty = doQty - accepted - returned;

	// Tampilkan hasil perhitungan pada input shrinkage
	$row.find('input[id^="shrinkage["]').val(shrinkQty.toFixed(2));

	// Validasi untuk memastikan tidak ada nilai negatif
	if (shrinkQty < 0) {
		alert('<strong>' + productName + '</strong> - <spring:message code="deliveryorder.shrinkage.qty"/> <spring:message code="notif.invalid"/>');
		$row.find('input[id^="shrinkage["]').val('0.00');
		$(element).val('0.00'); // Reset elemen yang memicu
	}
}

</script>