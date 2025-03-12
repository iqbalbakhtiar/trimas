<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
  <a class="item-button-list" href="<c:url value='/page/purchaseorderview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
  <a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
</div>
<div class="main-box">
  <sesform:form id="addForm" name="addForm" method="post" modelAttribute="purchase_form" enctype="multipart/form-data">
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
                <form:select id="org" path="organization" cssClass="combobox-ext" onchange="updateBillShipAddress(this);">
                  <c:if test='${not empty purchase_form.organization}'>
                    <form:option value='${purchase_form.organization.id}' label='${purchase_form.organization.fullName}'/>
                  </c:if>
                </form:select>
                <a class="item-popup" onclick="javascript:openpopup('<c:url value='/page/popupcompanystructurerolebasedview.htm?target=org'/>');" title="Company Structure" />
              </td>
            </tr>
            <tr>
              <td align="right"><spring:message code="purchaseorder.type"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="purchaseType" path="purchaseType">
	                <c:forEach var="type" items="${purchaseTypes}">
	                    <form:option value="${type}"><spring:message code='purchaseorder.type.${type.normalizedName.toLowerCase()}'/></form:option>
	                </c:forEach>
                </form:select>
              </td>
            </tr>
			<tr id="rowInvoice">
				<td align="right"><spring:message code="purchaseorder.invoicetype"/></td>
              	<td width="1%" align="center">:</td>
				<td>
					<form:radiobutton id="invoiceTrue" path="invoiceBeforeReceipt" value="true"/><spring:message code="purchaseorder.invoicetype.before"/>
					<form:radiobutton id="invoiceFalse" path="invoiceBeforeReceipt" value="false"/><spring:message code="purchaseorder.invoicetype.after"/>	
				</td>
			</tr>
            <tr>
              <td align="right"><spring:message code="sirius.date"/></td>
              <td width="1%" align="center">:</td>
              <td><input id="date" name="date" class="datepicker" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>"/></td>
            </tr>
            <tr>
              <td align="right"><spring:message code="sirius.date.delivery"/></td>
              <td width="1%" align="center">:</td>
              <td><input id="deliveryDate" name="deliveryDate" class="datepicker" value="<fmt:formatDate value='${now}' pattern='dd-MM-yyyy'/>"/></td>
            </tr>
            <tr>
              <td align="right"><spring:message code="supplier"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="supplier" path="supplier" cssClass="combobox-ext" onchange="updateSupplierAddress(this);">
                </form:select>
                <a class="item-popup" onclick="openSupplier()" title="Supplier" />
              </td>
            </tr>
            <tr id="rowApprover" style="display: none;">
              <td align="right"><spring:message code="approver"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="approver" path="approver" cssClass="combobox-ext">
                </form:select>
                <a class="item-popup" onclick="openApprover()" title="Approver" />
              </td>
            </tr>
            <tr>
              <td align="right"><spring:message code="purchaseorder.tax"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="tax" path="tax" onchange="updateDisplay();">
                  <c:forEach var="tax" items="${taxes}">
                    <option value="${tax.id}" data-taxrate="${tax.taxRate}">${tax.taxName}</option>
                  </c:forEach>
                </form:select>
                <input size="7" id="taxRate" class="input-number input-disabled" disabled />&nbsp;%
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
                  <legend><strong><spring:message code="purchaseorder.transaction"/></strong></legend>
                  <table width="100%" style="border: none">
                    <tr>
                      <td width="80%" align="right"><spring:message code="purchaseorder.purchase"/></td>
                      <td width="20%">:&nbsp;&nbsp;<input id="totalSales" value="0.00" class="number-disabled" readonly="readonly" size="20"/></td>
                    </tr>
                    <tr>
                      <td width="80%" align="right"><spring:message code="purchaseorder.tax"/></td>
                      <td width="20%">:&nbsp;&nbsp;<input id="totalTax" value="0.00" class="number-disabled" readonly="readonly" size="20"/></td>
                    </tr>
                    <tr>
                      <td width="80%" align="right"><strong><spring:message code="purchaseorder.total"/></strong></td>
                      <td width="20%">:&nbsp;&nbsp;<input id="totalTransaction" value="0.00" name="amount" class="number-disabled" readonly="readonly" size="20"/></td>
                    </tr>
                  </table>
                </fieldset>
              </td>
            </tr>
          </table>
        </td>
		</td>
      </tr>
    </table>
    <br/>
    <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 400px;">
      <div id="address" dojoType="ContentPane" label="<spring:message code='postaladdress.detail'/>" class="tab-pages" refreshOnShow="true" selected="true">
        <table width="100%">
          <tr>
            <td align="right"><spring:message code="purchaseorder.billto"/></td>
            <td width="1%" align="center">:</td>
            <td>
              <select id="billingToAddress" name="billTo" class="combobox-ext">
              </select>
            </td>
          </tr>
          <tr>
            <td align="right"><spring:message code="purchaseorder.shipto"/></td>
            <td width="1%" align="center">:</td>
            <td>
              <form:select id="shippingToAddress" path="shipTo" cssClass="combobox-ext">
              </form:select>
            </td>
          </tr>
        </table>
      </div>
      <div id="productLineItem" dojoType="ContentPane" label="<spring:message code='purchaseorder.line'/>" class="tab-pages" refreshOnShow="true" selected="true">
        <div class="toolbar-clean">
          <div class="toolbar-clean">
          	<div id="nonstandard" style="display: none;">
	            <a class="item-button-new newnonstandard"><span><spring:message code="sirius.row.new"/></span></a>
          	</div>
          	<div id="standard">
            	<a class="item-button-new newstandard" href="javascript:popupRequisitions();"><span><spring:message code="sirius.row.new"/></span></a>
            </div>
            <a class="item-button-delete"><span><spring:message code="sirius.row.delete"/></span></a>
            <div class="item-navigator">&nbsp;</div>
          </div>
          <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
            <thead>
            <tr>
              <th width="1%" nowrap="nowrap"><input class="checkall" type="checkbox" index=""/></th>
              <th width="8%" nowrap="nowrap"><spring:message code="product"/></th>
              <th width="5%" nowrap="nowrap"><spring:message code="sirius.qty"/></th>
              <th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
              <th width="8%" nowrap="nowrap"><spring:message code="purchaseorderitem.unitprice"/></th>
              <th width="8%" nowrap="nowrap"><spring:message code="purchaseorderitem.total"/></th>
              <th width="50%" nowrap="nowrap"><spring:message code="sirius.note"/></th>
            </tr>
            </thead>
            <tbody id="lineItem">
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
  updateDisplay();
  
  $('#supplier').change(function(){
	cleanItems();
  });
  
  $('#purchaseType').change(function(){
	cleanItems();
    var type = this.value;
    if(type == 'STANDARD') {
    	$('#standard').removeAttr('style');
    	$('#nonstandard').attr('style','display:none;');
    	$('#rowInvoice').removeAttr('style');
    } else if(type == 'DIRECT') {
    	$('#nonstandard').removeAttr('style');
    	$('#standard').attr('style','display:none;');
    	$('#rowInvoice').removeAttr('style');
    } else {
    	$('#nonstandard').removeAttr('style');
    	$('#standard').attr('style','display:none;');
    	$('#rowInvoice').attr('style','display:none;');
    	$("#invoiceTrue").prop("checked", true);
    }
  });

  $('.item-button-save').click(function(){
    if(validation())
      save();
  });
  
  $('.newnonstandard').click(function() {
    addLine();
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
  });

  $('#org').trigger('change');
});

function updateDisplay() {
  // Update Tax
  var taxRate = Number.parse($('#tax option:selected').data('taxrate')) || 0;
  $('#taxRate').val(taxRate ? taxRate.toFixed(2) : '');

  // Inisialisasi total
  var totalSales = 0;

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

    totalSales += amount;

    // Update Buying Price per-line Item
    $row.find('input[id^="totalAmount["]').val(amount.numberFormat('#,##0.00'));
  });

  // Menghitung totalTax dan totalTransaction
  var totalTax = totalSales * (taxRate / 100);
  var totalTransaction = totalSales + totalTax;

  // Memperbarui field recapitulation dengan nilai yang diformat
  $('#totalSales').val(totalSales.numberFormat('#,##0.00'));
  $('#totalTax').val(totalTax.numberFormat('#,##0.00'));
  $('#totalTransaction').val(totalTransaction.numberFormat('#,##0.00'));

  if(totalTransaction > new Number(1000000))
    $('#rowApprover').removeAttr('style');
  else {
	$('#approver').empty();
    $('#rowApprover').attr('style','display:none;');
  }
}

function updateBillShipAddress(element){
  // Populate (Bill To Address)
  Party.load(element.value);

  let _billingToAddress = $('#billingToAddress');
  if (_billingToAddress.find('option').length > 0) { // Clear options if any
    _billingToAddress.empty();
  }

  let addresses = Party.data.partyAddresses;

  addresses.forEach(address => {
    // Periksa apakah postalTypes memiliki tipe 'OFFICE' dengan enabled == true
    let hasShippingEnabled = address.postalTypes.some(postalType =>
            postalType.type === 'OFFICE' && postalType.enabled === true
    );

    if (hasShippingEnabled) {
      let option = $('<option></option>')
              .val(address.postalId)
              .text(address.addressName);

      if (address.isDefault) { // Jika alamat ini adalah default, set sebagai selected
        option.attr('selected', 'selected');
      }

      _billingToAddress.append(option); // Tambahkan opsi ke elemen _shippingAddress
    }
  });

  //Populate Facilites (Ship To Address)
  Facility.loadByOrg(element.value);

  let _shippingToAddress = $('#shippingToAddress');
  if (_shippingToAddress.find('option').length > 0) { // Clear options if any
    _shippingToAddress.empty();
  }

  Facility.data.forEach(facility => {
    let option = $('<option></option>')
            .val(facility.facilityId)
            .text(facility.facilityName);

    _shippingToAddress.append(option);
  });
}

function validation() {
  var organization = $('#org').val();
  if (organization == null || organization === "") {
    alert('<spring:message code="sirius.organization"/> <spring:message code="notif.empty"/> !');
    return false;
  }

  var date = $('#date').val();
  if (date == null || date === "") {
    alert('<spring:message code="salesorder.date"/> <spring:message code="notif.empty"/> !');
    return false;
  }

  var supplier = $('#supplier').val();
  if (supplier == null || supplier === "") {
    alert('<spring:message code="supplier"/> <spring:message code="notif.empty"/> !');
    return false;
  }

  if($('#totalTransaction').val().toNumber() > new Number(1000000)) {
    var approver = $('#approver').val();
    if (approver == null || approver === "") {
      alert('<spring:message code="sirius.approver"/> <spring:message code="notif.empty"/> !');
      return false;
    }
  }

  if ($('#lineItem tr').length === 0) {
    alert('<spring:message code="notif.add"/> <spring:message code="salesorder.lineitem"/> <spring:message code="notif.select2"/> !');
    return false;
  }

  var isValid = true;
  $('#lineItem tr').each(function(index){
    var $row = $(this);

    var product = $row.find('select[id^="reference["]').val();
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
    }

    var priceStr = $row.find('input[id^="amount["]').val().replace(/,/g, '');
    var price = parseFloat(priceStr);
    if (isNaN(price) || price <= 0) {
      alert('<spring:message code="sirius.unitprice"/> <spring:message code="notif.empty"/> ! (<spring:message code="salesorder.lineitem"/> ' + (index + 1) + ')');
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
    url:"<c:url value='/page/purchaseorderadd.htm'/>",
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
          window.location="<c:url value='/page/purchaseorderpreedit.htm?id='/>"+json.id;
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


var selectedRequisitions = [];
var index = 0;

function cleanItems() {
	$('.checkall').click();
	$('.item-button-delete').click();
	index = 0;
}

function popupRequisitions()
{
  if (!$('#org').val()) {
    alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
    return;
  }

  selectedRequisitions = [];
  openpopup("<c:url value='/page/popuppurchaserequisitionitemview.htm?organization='/>"+org.value+"&index="+index);
}

function addLine() {
  $purchaseType = $('#purchaseType').val();
  $tbody = $('#lineItem');
  $tr = $('<tr/>');

  $cbox = List.get('<input type="checkbox" class="check"/>','check'+index);
  //value is product id for non standard purchase type and requisition item id for standard purchase type
  $reference = List.get('<select class="combobox-ext references" onchange="checkDuplicate(this);updateDisplay();"/>','reference['+index+']');
  $productImg = List.img('<spring:message code="product"/>', index, 'openProduct("'+index+'")');
  
  $qty = List.get('<input type="text" class="input-number" size="6" onchange="updateDisplay();"/>','quantity['+index+']', '0.00');
  $uom = List.get('<input type="text" class="input-disabled" disabled size="6" />','uom['+index+']');
  $price = List.get('<input type="text" class="input-number" size="12" onchange="updateDisplay()"/>','amount['+index+']', '0.00');
  $totalAmount = List.get('<input type="text" class="input-number input-disabled" disabled size="12"/>','totalAmount['+index+']', '0.00');
  $packNote = List.get('<input type="text" size="40"/>','note['+index+']');

  $tr.append(List.col([$cbox]));
  
  if($purchaseType == 'STANDARD')
  	$tr.append(List.col([$reference]));
  else
  	$tr.append(List.col([$reference, $productImg]));
  
  $tr.append(List.col([$qty]));
  $tr.append(List.col([$uom]));
  $tr.append(List.col([$price]));
  $tr.append(List.col([$totalAmount]));
  $tr.append(List.col([$packNote]));

  $tbody.append($tr);
  index++;
  $(".input-number").bind(inputFormat);
}

function openProduct(index) {
  var cat = "";
  var purchaseType = $('#purchaseType').val();
  
  if(purchaseType == 'DIRECT')
	  cat = "STOCK";
  else
	  cat = "NONSTOCK&serial=false";
  
  openpopup("<c:url value='/page/popupproductview.htm?&target=reference['/>"+index+"]&index="+index+"&productCategoryType="+cat);
}

function checkDuplicate(element) {
  // Memanggil String.duplicate untuk mengecek duplikasi pada kelas 'references'
  var isDuplicated = String.duplicate('references');

  if (isDuplicated) {
    alert('<spring:message code="product"/>  <strong>'+ $(element).find('option:selected').text() +'</strong> <spring:message code="notif.duplicate"/> !');
    $(element).closest('tr').remove();
  }
}

function openSupplier() {
  if (!$('#org').val()) {
    alert('<spring:message code="notif.select1"/> <spring:message code="organization"/> <spring:message code="notif.select2"/> !!!');
    return;
  }

  const orgId = $('#org').val();
  const baseUrl = '<c:url value="/page/popuppartyrelationview.htm"/>';
  const params = {
    target: 'supplier', // Id Dropdown (Select) element
    organization: orgId, // Org (PartyTo)
    fromRoleType: 5, // Supplier
    toRoleType: 4, // Customer
    relationshipType: 4 // Supplier Relationship
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
    fromRoleType: 9, // Purchase Approver
    toRoleType: 2, // Company
    relationshipType: 2, // Employment Relationship
  };

  openpopup(buildUrl(baseUrl, params));
}

function updateSupplierAddress(element) {
  // Un-comment this if Party.data is not loaded
  // Party.load(element.value);

  let _supplierAddress = $('#supplierAddress');
  if (_supplierAddress.find('option').length > 0) { // Clear options if any
    _supplierAddress.empty();
  }

  let addresses = Party.data.partyAddresses;

  addresses.forEach(address => {
    // Periksa apakah postalTypes memiliki tipe 'OFFICE' atau 'TAX' dengan enabled == true
    let hasShippingEnabled = address.postalTypes.some(postalType =>
            (postalType.type === 'OFFICE' || postalType.type === 'TAX') && postalType.enabled === true
    );

    if (hasShippingEnabled) {
      let option = $('<option></option>')
              .val(address.postalId)
              .text(address.addressName);

      if (address.isDefault) { // Jika alamat ini adalah default, set sebagai selected
        option.attr('selected', 'selected');
      }

      _supplierAddress.append(option);
    }
  });

  let _supplierPhone = $('#supplierPhone');
  if (_supplierPhone.find('option').length > 0) {
    _supplierPhone.empty();
  }

  let contacts = Party.data.contactMechanises;

  contacts.forEach(contact => {
    if (contact.contactType === 'PHONE' && contact.active === true) {
      let option = $('<option></option>')
              .val(contact.contactId)
              .text(contact.contactName);

      _supplierPhone.append(option);
    }
  });
}
</script>