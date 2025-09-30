<%@ include file="/common/sirius-general-top.jsp"%>
<div class="toolbar">
  <a class="item-button-list" href="<c:url value='/page/purchaseorderview.htm'/>"><span><spring:message code="sirius.list"/></span></a>
  <a class="item-button-save" ><span><spring:message code="sirius.save"/></span></a>
  <a class="item-button-print"  href="javascript:printDocument();"><span><spring:message code="sirius.print"/></span></a>
  <c:if test="${access.add and purchase_edit.barcodeable}">
  	<a class="item-button-doc" href="javascript:createBarcode(${purchase_edit.id});"><span><spring:message code="sirius.create"/>&nbsp;<spring:message code="barcode"/></span></a>
  </c:if>
  <c:if test="${purchase_edit.status != 'CLOSED' and (approvalDecision == null or approvalDecision.approvalDecisionStatus == 'APPROVE_AND_FINISH')}">
    <a class="item-button-close"><span><spring:message code="sirius.close"/></span></a>
  </c:if>
</div>

<div class="main-box">
  <sesform:form id="addForm" name="addForm" method="post" modelAttribute="purchase_form" enctype="multipart/form-data">
    <table width="100%" border="0">
      <tr valign="top">
        <td width="60%">
          <table style="border:none" width="100%">
            <input type="hidden" id="status" name="status" value="${purchase_edit.status}"/>
            <tr>
              <td width="34%" align="right"><spring:message code="sirius.id"/></td>
              <td width="1%" align="center">:</td>
              <td width="64%"><input class="inputbox input-disabled" value="${purchase_edit.code}" disabled/></td>
              <td width="1%"><form:errors path="code"/></td>
            </tr>
            <tr>
              <td align="right"><spring:message code="organization"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="org" path="organization" cssClass="combobox-ext input-disabled" onchange="updateBillShipAddress(this);" disabled="true">
                  <c:if test='${not empty purchase_edit.organization}'>
                    <form:option value='${purchase_edit.organization.id}' label='${purchase_edit.organization.fullName}'/>
                  </c:if>
                </form:select>
              </td>
            </tr>
            <tr>
              <td align="right"><spring:message code="purchaseorder.type"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="purchaseType" path="purchaseType" readonly="true" cssClass="input-disabled combobox-min">
                    <form:option value="${purchase_edit.purchaseType}"><spring:message code='purchaseorder.type.${purchase_edit.purchaseType.messageName}'/></form:option>
                </form:select>
              </td>
            </tr>
            <tr style="display: none;">
              <td align="right"><spring:message code="sirius.document.type"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="purchaseDocumentType" path="purchaseDocumentType" readonly="true" cssClass="input-disabled combobox-min">
                    <form:option value="${purchase_edit.purchaseDocumentType}">${purchase_edit.purchaseDocumentType.normalizedName}</form:option>
                </form:select>
              </td>
            </tr>
			<tr>
				<td align="right"><spring:message code="purchaseorder.invoicetype"/></td>
              	<td width="1%" align="center">:</td>
				<td>
					<form:radiobutton id="invoiceTrue" path="invoiceBeforeReceipt" value="true" disabled="true"/><spring:message code="purchaseorder.invoicetype.before"/>
					<form:radiobutton id="invoiceFalse" path="invoiceBeforeReceipt" value="false" disabled="true"/><spring:message code="purchaseorder.invoicetype.after"/>	
				</td>
			</tr>
            <tr>
              <td align="right"><spring:message code="sirius.date"/></td>
              <td width="1%" align="center">:</td>
              <td><input id="date" name="date" size="10" class="input-disabled" value="<fmt:formatDate value='${purchase_edit.date}' pattern='dd-MM-yyyy'/>"/></td>
            </tr>
            <tr>
              <td align="right"><spring:message code="sirius.date.delivery"/></td>
              <td width="1%" align="center">:</td>
              <td><input id="deliveryDate" name="deliveryDate" size="10" class="input-disabled" value="<fmt:formatDate value='${purchase_edit.shippingDate}' pattern='dd-MM-yyyy'/>"/></td>
            </tr>
            <tr>
              <td align="right"><spring:message code="supplier"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="supplier" path="supplier" cssClass="combobox-ext input-disabled" disabled="true">
                  <c:if test='${not empty purchase_edit.supplier}'>
                    <form:option value="${purchase_edit.supplier.id}">${purchase_edit.supplier.fullName}</form:option>
                  </c:if>
                </form:select>
              </td>
            </tr>
            <tr>
              <td align="right"><spring:message code="purchaseorder.term.delivery"/></td>
              <td width="1%" align="center">:</td>
              <td><form:input id='deliveryTerm' path='deliveryTerm' size="25"/></td>
            </tr>
            <tr>
              <td align="right"><spring:message code="purchaseorder.term.payment"/></td>
              <td width="1%" align="center">:</td>
              <td><form:input id='paymentTerm' path='paymentTerm' size="25"/></td>
            </tr>
			<tr style="display: none;">
				<td align="right"><spring:message code="invoiceverification.currency"/></td>
                <td width="1%" align="center">:</td>
				<td>
					<form:select id='currency' path='currency' disabled='true' cssClass="input-disabled">
						<form:option value='${purchase_edit.money.currency.id}' label='${purchase_edit.money.currency.symbol}'/>
					</form:select>
					<form:select id='type' path='exchangeType' disabled='true' cssClass="input-disabled">
						<form:option value='SPOT' label='SPOT' onclick='display();'/>
						<form:option value='MIDDLE' label='MIDDLE' onclick='display();'/>
						<form:option value='TAX' label='TAX' onclick='display();'/>
					</form:select>
					<input size="10" class="input-disabled" disabled value="<fmt:formatNumber value='${purchase_edit.money.rate}' pattern=',##0.00'/>"/>
				</td>
			</tr>
            <tr>
              <td align="right"><spring:message code="purchaseorder.tax"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="tax" path="tax" cssClass="input-disabled" disabled="true">
                  <c:if test='${not empty purchase_edit.tax}'>
                    <form:option value="${purchase_edit.tax.id}" data-taxrate="${purchase_edit.tax.taxRate}">${purchase_edit.tax.taxName}</form:option>
                  </c:if>
                </form:select>
                <input size="5" id="taxRate" class="input-disabled" disabled value="<fmt:formatNumber value='${purchase_edit.tax.taxRate}' pattern=',##0.00'/>"/>
              </td>
            </tr>
            <c:if test="${not empty purchase_edit.approvable}">
            <tr>
              <td align="right"><spring:message code="approver"/></td>
              <td width="1%" align="center">:</td>
              <td>
                <form:select id="approver" path="approver" cssClass="combobox-ext input-disabled" disabled="true">
                  <c:if test='${not empty purchase_edit.approver}'>
                    <form:option value="${purchase_edit.approver.id}">${purchase_edit.approver.fullName}</form:option>
                  </c:if>
                </form:select>
              </td>
            </tr>
            </c:if>
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
                      <td width="20%">:&nbsp;&nbsp;<input id="totalSales" value="<fmt:formatNumber value='${adapter.totalItemAmount}' pattern=',##0.00'/>" class="number-disabled" readonly="readonly" size="20"/></td>
                    </tr>
                    <tr>
                      <td width="80%" align="right"><spring:message code="purchaseorder.discount"/></td>
                      <td width="20%">:&nbsp;&nbsp;<input id="totalDiscount" value="<fmt:formatNumber value='${adapter.totalDiscount}' pattern=',##0.00'/>" class="number-disabled" readonly="readonly" size="20"/></td>
                    </tr>
                    <tr>
                      <td width="80%" align="right"><spring:message code="purchaseorder.afterdiscount"/></td>
                      <td width="20%">:&nbsp;&nbsp;<input id="totalAfterDiscount" value="<fmt:formatNumber value='${adapter.totalAfterDiscount}' pattern=',##0.00'/>" class="number-disabled" readonly="readonly" size="20"/></td>
                    </tr>
                    <tr>
                      <td width="80%" align="right"><spring:message code="purchaseorder.tax"/></td>
                      <td width="20%">:&nbsp;&nbsp;<input id="totalTax" value="<fmt:formatNumber value='${adapter.taxAmount}' pattern=',##0.00'/>" class="number-disabled" readonly="readonly" size="20"/></td>
                    </tr>
                    <tr>
                      <td width="80%" align="right"><strong><spring:message code="purchaseorder.total"/></strong></td>
                      <td width="20%">:&nbsp;&nbsp;<input id="totalTransaction" value="<fmt:formatNumber value='${adapter.totalTransaction}' pattern=',##0.00'/>" name="amount" class="number-disabled" readonly="readonly" size="20"/></td>
                    </tr>
                  </table>
                </fieldset>
              </td>
            </tr>
            <tr>
                <td>
                    <fieldset>
                        <legend><strong><spring:message code="sirius.reference"/></strong></legend>
                        <table width="100%" style="border: none">
                        <c:if test="${not empty purchase_edit.receipts}">
                        <tr>
                            <td width="80%" align="right" valign="top"><spring:message code="goodsreceipt"/>:&nbsp;</td>
                            <td width="20%" align="right" nowrap="nowrap">
                                <c:forEach items="${purchase_edit.receipts}" var="rec" varStatus="status">
                                    <a href="<c:url value='/page/goodsreceiptpreedit.htm?id=${rec.id}'/>"><c:out value="${rec.code}"></c:out></a>
                                    <br>
                                </c:forEach>
                            </td>
                        </tr>
                        </c:if>
                        <c:if test="${not empty purchase_edit.invoiceVerifications}">
                        <tr>
                            <td width="80%" align="right" valign="top"><spring:message code="invoiceverification"/>:&nbsp;</td>
                            <td width="20%" align="right" nowrap="nowrap">
                                <c:forEach items="${purchase_edit.invoiceVerifications}" var="ver" varStatus="status">
                                    <a href="<c:url value='/page/invoiceverificationpreedit.htm?id=${ver.id}'/>"><c:out value="${ver.code}"></c:out></a>
                                    <br>
                                </c:forEach>
                            </td>
                        </tr>
                        </c:if>
                        </table>
                    </fieldset>
                </td>
            </tr>
            <tr>
            	<td>
                <c:if test="${not empty purchase_edit.approvable}">
	                <%@ include file="/pages/sales/approval-history.jsp" %>
	                <%@ include file="/pages/sales/approval.jsp" %>
                </c:if>
                </td>
            </tr>
          </table>
        </td>

      </tr>
    </table>
    <br/>
    <div id="mainTab" dojoType="TabContainer" style="width:100% ; height: 400px;">
      <div id="address" dojoType="ContentPane" label="<spring:message code='postaladdress.detail'/>" class="tab-pages" refreshOnShow="true" selected="true">
        <table width="100%">
          <tr>
            <td align="right"><spring:message code="purchaseorder.supplieraddress"/></td>
            <td width="1%" align="center">:</td>
            <td>
              <select id="supplierAddress" name="supplierAddress" class="combobox-ext input-disabled" disabled>
                <c:if test="${purchase_edit.supplierAddress != null}">
                  <option value="${purchase_edit.supplierAddress.id}">${purchase_edit.supplierAddress.addressName}</option>
                </c:if>
              </select>
            </td>
          </tr>
          <tr>
            <td align="right"><spring:message code="supplier.contact"/></td>
            <td width="1%" align="center">:</td>
            <td>
              <select id="supplierPhone" name="supplierPhone" class="combobox-ext input-disabled" disabled>
                <c:if test="${purchase_edit.supplierPhone != null}">
                  <option value="${purchase_edit.supplierPhone.id}">${purchase_edit.supplierPhone.contact}</option>
                </c:if>
              </select>
            </td>
          </tr>
          <tr>
            <td align="right"><spring:message code="dpo.billto"/></td>
            <td width="1%" align="center">:</td>
            <td>
              <select id="billingToAddress" name="billTo" class="combobox-ext input-disabled" disabled>
                <option value="${purchase_edit.billTo.id}">${purchase_edit.billTo.addressName}</option>
              </select>
            </td>
          </tr>
          <tr>
            <td align="right"><spring:message code="dpo.shipto"/></td>
            <td width="1%" align="center">:</td>
            <td>
              <form:select id="shippingToAddress" path="shipTo" cssClass="combobox-ext input-disabled" disabled="true">
                <form:option value="${purchase_edit.shipTo.id}">${purchase_edit.shipTo.name}</form:option>
              </form:select>
            </td>
          </tr>
        </table>
      </div>
      <div id="productLineItem" dojoType="ContentPane" label="<spring:message code='salesorder.lineitem'/>" class="tab-pages" refreshOnShow="true">
        <div class="toolbar-clean">
          <c:set var="isApprover" value="${not empty person and person.id == approvalDecision.forwardTo.id and approvalDecision.approvalDecisionStatus != 'APPROVE_AND_FINISH' and approvalDecision.approvalDecisionStatus != 'REJECTED'}" />
          <c:if test="${isApprover and fn:length(purchase_edit.items) >= 2}">
            <div class="item-navigator">
              <a class="item-button-delete"><span><spring:message code="sirius.row.delete"/></span></a>
            </div>
          </c:if>
          <table class="table-list" id="lineItemTable" cellspacing="0" cellpadding="0" align="center"  style="width:100%;">
            <thead>
            <tr>
              <th width="1%" nowrap="nowrap">&nbsp;</th>
              <th width="8%" nowrap="nowrap"><spring:message code="product"/></th>
              <th width="5%" nowrap="nowrap"><spring:message code="sirius.qty"/></th>
              <th width="5%" nowrap="nowrap"><spring:message code="sirius.uom"/></th>
              <th width="8%" nowrap="nowrap"><spring:message code="purchaseorder.amount"/></th>
              <th width="8%" nowrap="nowrap"><spring:message code="purchaseorderitem.discount"/></th>
              <th width="8%" nowrap="nowrap"><spring:message code="purchaserequisition.discount"/></th>
              <th width="8%" nowrap="nowrap"><spring:message code="purchaseorder.total"/></th>
              <th width="10%" nowrap="nowrap"><spring:message code="sirius.note"/></th>
              <th width="50%" nowrap="nowrap"><spring:message code="sirius.reference"/></th>
            </tr>
            </thead>
            <tbody id="lineItem">
            <c:forEach items="${purchase_edit.items}" var="item" varStatus="idx">
            <c:if test="${item.purchaseItemType eq 'BASE'}">
              <tr style="">
                <td>
                  <c:if test="${fn:length(purchase_edit.items) >= 2}">
                    <input type="checkbox" class="check" id="check${idx.index}">
                  </c:if>
                  <input name="items[${idx.index}].reference" id="reference[${idx.index}]" value="${item.id}" size="5" class="input-disabled" disabled type="hidden">
                </td>
                <td><input id="product[${idx.index}]" size="26" value="${item.product.name}" class="input-disabled" name="items[${idx.index}].product" index="${idx.index}" next="product" disabled/></td>
                <td>
                  <input
                          id="quantity[${idx.index}]"
                          size="6"
                          value="${item.quantity}"
                          name="purchaseOrder.items[${idx.index}].quantity"
                          index="${idx.index}"
                          next="quantity"
                          class="${isApprover ? 'input-decimal' : 'input-disabled input-decimal'}"
                    ${isApprover ? '' : 'disabled="disabled"'} />
                </td>
                <td><input id="uom[${idx.index}]" size="6" value="${item.product.unitOfMeasure.measureId}" class="input-disabled" name="items[${idx.index}].uom" index="${idx.index}" next="uom" disabled/></td>
                <td><input id="amount[${idx.index}]" size="12" value="<fmt:formatNumber value='${item.money.amount}' pattern=',##0.00'/>" class="input-disabled input-decimal" name="items[${idx.index}].amount" index="${idx.index}" next="amount" disabled/></td>
                <td><input id="discountPercent[${idx.index}]" size="8" value="<fmt:formatNumber value='${item.discountPercent}' pattern=',##0.00'/>" class="input-disabled input-decimal" name="items[${idx.index}].discountPercent" index="${idx.index}" next="discountPercent" disabled/></td>
                <td><input id="discount[${idx.index}]" size="12" value="<fmt:formatNumber value='${item.discount}' pattern=',##0.00'/>" class="input-disabled input-decimal" name="items[${idx.index}].discount" index="${idx.index}" next="discount" disabled/></td>
                <td><input id="totalAmount[${idx.index}]" size="12" class="input-number input-disabled" disabled value="<fmt:formatNumber value='${item.totalAmount-item.discount}' pattern=',##0.00'/>"/></td>
                <td><input id="note[${idx.index}]" type="text" value="${item.note}" name="purchaseOrder.items[${idx.index}].note"index="${idx.index}" next="note" size="40"/></td>
                <td><a href="<c:url value='/page/purchaserequisitionpreedit.htm?id=${item.requisitionItem.purchaseRequisition.id}'/>"><c:out value="${item.requisitionItem.purchaseRequisition.code}"></c:out></a></td>
              </tr>
            </c:if>
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
<div class="info"><spring:message code="sirius.createdby"/> : <c:out value='${purchase_edit.createdBy.fullName}'/> (<fmt:formatDate value='${purchase_edit.createdDate}' pattern='dd-MM-yyyy HH:mm:ss'/>) | <spring:message code="sirius.updatedby"/> : <c:out value='${purchase_edit.updatedBy.fullName}'/> (<fmt:formatDate value='${purchase_edit.updatedDate}' pattern='dd-MM-yyyy HH:mm:ss'/>)</div>
<%@ include file="/common/sirius-general-bottom.jsp"%>
<script type="text/javascript">
  $(function(){
    $('.item-button-save').click(function(){
        save();
    });

    $('.item-button-close').click(function(){
      confirmDialog("<spring:message code="notif.proceed"/> (<spring:message code="sirius.close"/> <spring:message code="purchaseorder"/>) ?", function(){
        $('#status').val("CLOSED");
        save();
      }, function(){
        return false;
      });
    });

    $('.item-button-delete').click(function () {
      // Hitung jumlah baris yang masih terlihat di tabel (misalnya baris item)
      var totalVisibleRows = $('#lineItem tr:visible').length;
      // Hitung jumlah baris yang dicentang
      var selectedCount = $('.check:checked').length;

      // Jika setelah dihapus tidak ada item tersisa (minimal harus 1)
      if (totalVisibleRows - selectedCount < 1) {
        alert('<spring:message code="manualbilling.line.item"/> <spring:message code="notif.lower"/> 1');
        return false; // Batalkan proses delete
      }

      // Jika memenuhi syarat, lakukan penghapusan pada baris yang dicentang
      $('.check:checked').each(function () {
        var $tr = $(this).closest('tr');
        $tr.css('display', 'none'); // Sembunyikan baris
        $tr.find('input[id^="reference"]').prop('disabled', false); // Hilangkan atribut disabled khusus pada input reference
      });
    });
  });

  function save() {
    $.ajax({
      url:"<c:url value='/page/purchaseorderedit.htm'/>",
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
  
  function createBarcode(id)
  {
	const confirmDialog = $('<div><spring:message code="notif.proceed"/> (<spring:message code="sirius.create"/> <spring:message code="barcode"/>) ?</div>').dialog(
	{
		autoOpen: false, title: '<spring:message code="notif.confirmation"/>', modal:true,
		buttons: {
			'<spring:message code="sirius.yes"/>': function() {
				$(this).dialog('close');

				var url = "<c:url value='/page/barcodegrouppreadd1.htm?barcodeType=PURCHASE_ORDER&referenceId='/>"+id;
				window.location = url;
			},
			'<spring:message code="sirius.no"/>': function() {
				$(this).dialog('close');
			}
		}
	});

	confirmDialog.dialog('open');
  }
  
  function printDocument()
  {
	  var documentType = '${purchase_edit.purchaseDocumentType}';
	  var taxRate = $('#taxRate').val().toNumber();
	  var printType = 1;
	  
	  if(documentType == 'BAHAN_BAKU') {
		  if(taxRate > 0)
			  printType = 2;
		  else
			  printType = 1;
	  } else {
		  if(taxRate > 0)
			  printType = 4;
		  else
			  printType = 3;
	  }
	  
	  var url = "<c:url value='/page/purchaseorderprint.htm?id=${purchase_edit.id}&printType='/>"+printType;
	  window.location = url;
  }
</script>